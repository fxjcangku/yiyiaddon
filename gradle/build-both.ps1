<#
  构建「另一条版本线」的个人版，并把 jar 拷回本线 build/libs/（文件名带 MC 版本后缀，两份不会互相覆盖）。

  由 `.\gradlew.bat buildBoth` 调起（见 build.gradle 的 buildOtherLine 任务），也可单独跑：
    powershell -NoProfile -ExecutionPolicy Bypass -File gradle\build-both.ps1

  设计要点（别改）：
    · 全程不碰当前工作区：在系统临时目录建 git worktree，构建完撤掉；
    · 用 --detach 而不是 checkout 分支 —— 「另一条线」的分支可能正被主工作区占着
      （在 port/26.2 上跑时 master 就是这种情况），detached 签出不占分支，构建也不关心分支名；
    · 本线不在这里构建：由外层 gradle 自己来，避免在这条脚本里再调一次 gradle 去等自己的项目锁。
#>
[CmdletBinding()]
param(
    [string]$RepoRoot = '',
    [string]$LineVersion = '',
    [string]$OutDir = '',
    [string]$WorktreeDir = (Join-Path $env:TEMP 'yiyiaddon-buildboth')
)

# 外部命令的 stderr 不当异常处理，一律看退出码
$ErrorActionPreference = 'Continue'

# Gradle 用 UTF-8 读子进程输出，这里必须显式对齐，否则本脚本的中文日志在 gradlew 里全是乱码
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

if (-not $RepoRoot) { $RepoRoot = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path }
if (-not $OutDir)   { $OutDir   = Join-Path $RepoRoot 'build\libs' }

function Write-Step([string]$Message) { Write-Host "[build-both] $Message" }

function Invoke-Git {
    param([string[]]$GitArgs)
    $null = & git -C $RepoRoot @GitArgs 2>&1
    return $LASTEXITCODE
}

function Read-MinecraftVersion {
    param([string]$Root)
    $hit = Select-String -Path (Join-Path $Root 'gradle.properties') -Pattern '^minecraft_version=(.+)$'
    if (-not $hit) { throw "读不到 $Root\gradle.properties 里的 minecraft_version" }
    return $hit.Matches[0].Groups[1].Value.Trim()
}

$failed = $false
try {
    $currentBranch = (& git -C $RepoRoot rev-parse --abbrev-ref HEAD 2>$null).Trim()
    if (-not $LineVersion) { $LineVersion = Read-MinecraftVersion $RepoRoot }

    $otherBranch = $null
    if     ($currentBranch -eq 'master')    { $otherBranch = 'port/26.2' }
    elseif ($currentBranch -eq 'port/26.2') { $otherBranch = 'master' }
    else { throw "当前分支「$currentBranch」既不是 master 也不是 port/26.2，判断不出另一条线" }

    Write-Step "本线 $currentBranch（MC $LineVersion）→ 接着构建另一条线 $otherBranch"

    # 清掉上次可能残留的临时目录（构建中断时会留下）
    if (Test-Path $WorktreeDir) {
        Write-Step "清理上次残留的临时目录：$WorktreeDir"
        [void](Invoke-Git @('worktree', 'remove', '--force', $WorktreeDir))
        if (Test-Path $WorktreeDir) { Remove-Item $WorktreeDir -Recurse -Force -ErrorAction SilentlyContinue }
        [void](Invoke-Git @('worktree', 'prune'))
    }

    if ((Invoke-Git @('worktree', 'add', '--detach', '--force', $WorktreeDir, $otherBranch)) -ne 0) {
        throw "git worktree 建不出来（$WorktreeDir ← $otherBranch）"
    }

    $otherVersion = Read-MinecraftVersion $WorktreeDir
    Write-Step "另一条线已签出：$WorktreeDir（MC $otherVersion）"

    Write-Step '开始构建另一条线（首次要配置 Loom，会慢一些）…'
    # 这段写法由三条约束叠出来，别简化：
    #   1) 不能让 worktree 里 gradle 拉起的后台进程继承本脚本的 stdout 管道 —— 否则外层 gradle 永不结束；
    #   2) 所以子构建用 --no-daemon（一次性 JVM，结束即退出），代价是慢几十秒；
    #   3) PowerShell 的 Start-Process -PassThru 在这里拿不到 ExitCode（实测始终为空），
    #      于是退出码交给 cmd 写进文件，不依赖 Process 对象。
    $stdoutFile = Join-Path ([System.IO.Path]::GetTempPath()) 'yiyiaddon-buildboth.out'
    $stderrFile = Join-Path ([System.IO.Path]::GetTempPath()) 'yiyiaddon-buildboth.err'
    $codeFile   = Join-Path ([System.IO.Path]::GetTempPath()) 'yiyiaddon-buildboth.code'
    $runnerFile = Join-Path ([System.IO.Path]::GetTempPath()) 'yiyiaddon-buildboth-runner.bat'
    Remove-Item $stdoutFile, $stderrFile, $codeFile -Force -ErrorAction SilentlyContinue

    @"
@echo off
cd /d "$WorktreeDir"
call "$WorktreeDir\gradlew.bat" buildPersonal --no-daemon --console=plain > "$stdoutFile" 2> "$stderrFile"
echo %ERRORLEVEL% > "$codeFile"
"@ | Set-Content -LiteralPath $runnerFile -Encoding ASCII

    Write-Step "构建中（一次性 JVM，多花几十秒是正常的），实时日志：$stdoutFile"
    $proc = Start-Process -FilePath $runnerFile -WorkingDirectory $WorktreeDir -WindowStyle Hidden -PassThru

    # 兜底超时：这一步绝不能没上限，否则任何异常都会让 buildBoth 永远挂着
    $timeoutSeconds = 1200
    $deadline = (Get-Date).AddSeconds($timeoutSeconds)
    while (-not $proc.HasExited) {
        if ((Get-Date) -gt $deadline) {
            try { $proc.Kill() } catch { }
            throw "另一条线构建超时（超过 $timeoutSeconds 秒），已强杀"
        }
        Start-Sleep -Seconds 3
    }

    $exitCode = 1
    if (Test-Path $codeFile) { [void][int]::TryParse((Get-Content $codeFile -Raw).Trim(), [ref]$exitCode) }

    foreach ($file in @($stdoutFile, $stderrFile)) {
        if (Test-Path $file) {
            Get-Content $file -Tail 12 -Encoding UTF8 | ForEach-Object { Write-Host "  | $_" }
        }
    }
    if ($exitCode -ne 0) { throw "另一条线构建失败（gradlew 退出码 $exitCode）" }

    $builtDir = Join-Path $WorktreeDir 'build\libs'
    $jars = @(Get-ChildItem $builtDir -Filter '*.jar' -ErrorAction SilentlyContinue |
              Where-Object { $_.Name -notmatch 'sources|javadoc' })
    if ($jars.Count -eq 0) { throw "构建完成但 $builtDir 下没有 jar" }

    New-Item -ItemType Directory -Force -Path $OutDir | Out-Null
    foreach ($jar in $jars) {
        $target = Join-Path $OutDir ("$([System.IO.Path]::GetFileNameWithoutExtension($jar.Name))+$otherVersion.jar")
        Copy-Item $jar.FullName $target -Force
        Write-Step ('已产出 {0}（{1:N1} MB）' -f $target, ($jar.Length / 1MB))
    }
    Write-Step '两条线的个人版都齐了：带 +<版本> 后缀的是本次另一条线的，本线那份是老名字（由 gradle 直接构建）。'
} catch {
    Write-Step "构建失败：$($_.Exception.Message)"
    $failed = $true
} finally {
    if (Test-Path $WorktreeDir) {
        [void](Invoke-Git @('worktree', 'remove', '--force', $WorktreeDir))
        if (Test-Path $WorktreeDir) { Remove-Item $WorktreeDir -Recurse -Force -ErrorAction SilentlyContinue }
        [void](Invoke-Git @('worktree', 'prune'))
        Write-Step '临时工作目录已撤掉'
    }
}

# 显式归零退出码：不然 gradle 会拿 finally 里最后那条 git 命令的退出码当任务结果，
# 出现「产物明明出来了、gradle 却报 FAILED」的假失败。
if ($failed) { exit 1 }
exit 0
