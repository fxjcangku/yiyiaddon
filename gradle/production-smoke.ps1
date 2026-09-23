#Requires -Version 5.1
<#
  232-P 生产发行冒烟启动器（只用于验收，不参与正式产物）。

  它回答的问题：普通用户拿到正式产物、用真实启动器运行时，本机隔离 Worker 到底能不能起来。
  所以它刻意不用 Gradle / Loom / runClient，而是自己按版本 JSON 组装一条「启动器等价」的命令：

    <官方运行时 java.exe> -cp <库 + 游戏 jar> net.fabricmc.loader.impl.launch.knot.KnotClient
        --gameDir <一次性冒烟实例> --assetsDir <真实 assets> ...

  yiyiaddon 只以「最终 build 产物 jar」的形式放进 <冒烟实例>/mods/，
  绝不把 src / build/classes / Gradle 开发输出挂到任何 classpath 上。

  运行期间它会盯着 Worker 子进程，记录：
    - 真实命令行（用来断言没有开发目录泄漏）
    - PID 生命周期与 RSS 采样
  结束后检查是否有残留 Worker 进程。

  用法示例：
    powershell -NoProfile -File gradle/production-smoke.ps1 `
        -ModJar build/release/yiyiaddon-1.0-beta2-26.1.2.jar `
        -SeedPocJvmArgs '-Dyiyiaddon.seedpoc.enabled=1','-Dyiyiaddon.seedpoc.service=1','-Dyiyiaddon.seedpoc.exit=1'
#>
[CmdletBinding()]
param(
    [string]$MinecraftDir = 'C:\Users\Administrator\Desktop\MC\.minecraft',
    [string]$VersionId = 'LunarFox',
    [string]$GameDir = 'D:\mcaddon\种子 Worker Production Test',
    [Parameter(Mandatory = $true)][string]$ModJar,
    [string]$FabricApiJar = '',
    [string[]]$ExtraModJars = @(),
    [string]$ExtraModsDir = '',
    [string]$JavaExe = '',
    [string[]]$SeedPocJvmArgs = @(),
    [string[]]$ExtraJvmArgs = @(),
    [string[]]$ExtraGameArgs = @(),
    # 离线模式服务器按「名字派生的离线 UUID」判管理员；233 观察回归要发 /setblock 与 /tp，
    # 因此必须用与专用服务器 ops.json 一致的名字（默认 SmokeTester 只适合不需要 OP 的用例）。
    [string]$Username = 'SmokeTester',
    [string]$ClientHeap = '-Xmx2G',
    [int]$TimeoutSeconds = 900
)

$ErrorActionPreference = 'Stop'
$osName = 'windows'
$script:failures = New-Object System.Collections.Generic.List[string]

function Write-Smoke([string]$text) { Write-Host "[SMOKE] $text" }

function Test-LibraryRules($rules) {
    if (-not $rules) { return $true }
    $allowed = $false
    foreach ($rule in $rules) {
        $hit = $true
        if ($rule.os -and $rule.os.name -and $rule.os.name -ne $osName) { $hit = $false }
        if ($hit) { $allowed = ($rule.action -eq 'allow') }
    }
    return $allowed
}

# ── 1. 输入校验 ───────────────────────────────────────────────────────────────
$ModJar = (Resolve-Path -LiteralPath $ModJar).Path
if (-not (Test-Path -LiteralPath $MinecraftDir)) { throw "Minecraft 目录不存在：$MinecraftDir" }
$versionDir = Join-Path $MinecraftDir "versions\$VersionId"
$versionJson = Join-Path $versionDir "$VersionId.json"
if (-not (Test-Path -LiteralPath $versionJson)) { throw "版本 JSON 不存在：$versionJson" }

$modJarName = Split-Path $ModJar -Leaf
Write-Smoke "正式产物：$ModJar（$([math]::Round((Get-Item -LiteralPath $ModJar).Length / 1MB, 1)) MB）"

# ── 2. 一次性冒烟实例 ─────────────────────────────────────────────────────────
$modsDir = Join-Path $GameDir 'mods'
$evidenceDir = Join-Path $GameDir 'evidence'
New-Item -ItemType Directory -Force -Path $modsDir, $evidenceDir | Out-Null
Get-ChildItem -LiteralPath $modsDir -File | Remove-Item -Force
Get-ChildItem -LiteralPath $evidenceDir -File | Remove-Item -Force
Get-ChildItem -LiteralPath $GameDir -File -Filter 'seedpoc-*.txt' | Remove-Item -Force

# 无障碍引导界面会让开发装置停在标题界面之前（options.txt 缺失时原版会先弹引导）
Set-Content -LiteralPath (Join-Path $GameDir 'options.txt') -Encoding UTF8 -Value @(
    'onboardAccessibility:false',
    'skipMultiplayerWarning:true',
    "version:$VersionId"
)

Copy-Item -LiteralPath $ModJar -Destination $modsDir -Force
if ($FabricApiJar) {
    Copy-Item -LiteralPath $FabricApiJar -Destination $modsDir -Force
} else {
    $found = Get-ChildItem -LiteralPath (Join-Path $versionDir 'mods') -File -ErrorAction SilentlyContinue |
        Where-Object { $_.Name -like 'fabric-api-*.jar' } | Select-Object -First 1
    if (-not $found) { throw '找不到 fabric-api jar，请用 -FabricApiJar 指定（yiyiaddon 依赖它）' }
    Copy-Item -LiteralPath $found.FullName -Destination $modsDir -Force
    Write-Smoke "fabric-api：$($found.Name)（取自真实实例）"
}
foreach ($extra in $ExtraModJars) { Copy-Item -LiteralPath $extra -Destination $modsDir -Force }
# 真实用户 Mod 目录整体复制（排除本次要验的正式产物本身，避免同名覆盖）：
# 用途 = 「普通用户把新 jar 丢进自己那 100 个 Mod 的 mods 目录」这一真实场景。
if ($ExtraModsDir) {
    $copied = 0
    foreach ($file in Get-ChildItem -LiteralPath $ExtraModsDir -File -Filter '*.jar') {
        if ($file.Name -eq $modJarName) { continue }
        Copy-Item -LiteralPath $file.FullName -Destination $modsDir -Force
        $copied++
    }
    Write-Smoke "真实 Mod 目录：$ExtraModsDir → 额外复制 $copied 个 jar"
}

$modsInSmoke = (Get-ChildItem -LiteralPath $modsDir -File | Select-Object -ExpandProperty Name) -join ', '
Write-Smoke "冒烟实例 mods（$((Get-ChildItem -LiteralPath $modsDir -File).Count) 个文件）：$modsInSmoke"

# ── 3. 启动器等价命令 ─────────────────────────────────────────────────────────
$librariesDir = Join-Path $MinecraftDir 'libraries'
$json = Get-Content -LiteralPath $versionJson -Raw -Encoding UTF8 | ConvertFrom-Json
$classpathEntries = New-Object System.Collections.Generic.List[string]
$skipped = 0
$derived = 0
foreach ($lib in $json.libraries) {
    if (-not (Test-LibraryRules $lib.rules)) { $skipped++; continue }
    $rel = $lib.downloads.artifact.path
    if (-not $rel) {
        # Fabric / 安装器加进去的库没有 downloads 元数据，启动器按 maven 坐标约定拼路径
        # （group:artifact:version[:classifier][@ext]）。这里照做，否则 fabric-loader 不在 classpath 上。
        $coordinate = $lib.name
        $extension = 'jar'
        if ($coordinate -match '@(.+)$') {
            $extension = $Matches[1]
            $coordinate = $coordinate.Substring(0, $coordinate.Length - $Matches[0].Length)
        }
        $parts = $coordinate -split ':'
        if ($parts.Count -lt 3) { throw "无法解析库坐标：$($lib.name)" }
        $file = "$($parts[1])-$($parts[2])"
        if ($parts.Count -ge 4) { $file = "$file-$($parts[3])" }
        $rel = (($parts[0] -replace '\.', '/')) + "/$($parts[1])/$($parts[2])/$file.$extension"
        $derived++
    }
    $full = Join-Path $librariesDir ($rel -replace '/', '\')
    if (-not (Test-Path -LiteralPath $full)) { throw "缺少库文件：$full" }
    $classpathEntries.Add($full)
}
$gameJar = Join-Path $versionDir "$VersionId.jar"
if (-not (Test-Path -LiteralPath $gameJar)) { throw "缺少游戏 jar：$gameJar" }
$classpathEntries.Add($gameJar)
Write-Smoke "classpath：$($classpathEntries.Count) 个条目（库 $($classpathEntries.Count - 1) + 游戏 jar；按规则跳过 $skipped 条，按坐标推导 $derived 条）"

if (-not $JavaExe) {
    $candidates = Get-ChildItem -Path "$env:APPDATA\.minecraft\runtime\*\bin\java.exe" -ErrorAction SilentlyContinue
    $preferred = $candidates | Where-Object { $_.FullName -like '*java-runtime-epsilon*' } | Select-Object -First 1
    if ($preferred) { $JavaExe = $preferred.FullName }
    elseif ($candidates) { $JavaExe = ($candidates | Select-Object -First 1).FullName }
    else { throw '找不到官方运行时 java.exe，请用 -JavaExe 指定' }
}
Write-Smoke "Java：$JavaExe"

$natives = Join-Path $versionDir "$VersionId-natives"
$jvmArgs = @(
    "-Djava.library.path=`"$natives`"",
    "-Djna.tmpdir=`"$natives`"",
    "-Dorg.lwjgl.system.SharedLibraryExtractPath=`"$natives`"",
    "-Dio.netty.native.workdir=`"$natives`"",
    '-DFabricMcEmu=net.minecraft.client.main.Main',
    '-Dlog4j2.formatMsgNoLookups=true',
    '-Dstdout.encoding=utf-8',
    '-Dstderr.encoding=utf-8',
    '-Dminecraft.launcher.brand=production-smoke',
    $ClientHeap
) + $ExtraJvmArgs + $SeedPocJvmArgs

$gameArgs = @(
    '-cp', "`"$($classpathEntries -join ';')`"",
    'net.fabricmc.loader.impl.launch.knot.KnotClient',
    '--username', $Username,
    '--version', $VersionId,
    '--gameDir', "`"$GameDir`"",
    '--assetsDir', "`"$(Join-Path $MinecraftDir 'assets')`"",
    '--assetIndex', "$($json.assetIndex.id)",
    '--uuid', '00000000000000000000000000000001',
    '--accessToken', '0',
    '--clientId', '0',
    '--xuid', '0',
    '--width', '1280',
    '--height', '720'
) + $ExtraGameArgs

$psi = New-Object System.Diagnostics.ProcessStartInfo
$psi.FileName = $JavaExe
$psi.Arguments = (($jvmArgs + $gameArgs) -join ' ')
$psi.WorkingDirectory = $GameDir
$psi.UseShellExecute = $false
$psi.RedirectStandardOutput = $true
$psi.RedirectStandardError = $true

$startedAt = Get-Date
$client = [System.Diagnostics.Process]::Start($psi)
Write-Smoke "客户端已启动 PID=$($client.Id)，gameDir=$GameDir"

# ── 4. 盯 Worker 子进程 ───────────────────────────────────────────────────────
$stdoutTask = $client.StandardOutput.ReadToEndAsync()
$stderrTask = $client.StandardError.ReadToEndAsync()
$deadline = $startedAt.AddSeconds($TimeoutSeconds)
$workerCommand = ''
$workerLifecycle = New-Object System.Collections.Generic.List[string]
$rssSamples = New-Object System.Collections.Generic.List[string]
$lastPid = -1
$peakRss = 0

while (-not $client.HasExited -and (Get-Date) -lt $deadline) {
    $elapsed = [int]((Get-Date) - $startedAt).TotalSeconds
    $workers = @(Get-CimInstance Win32_Process -Filter "Name='java.exe' OR Name='javaw.exe'" -ErrorAction SilentlyContinue |
        Where-Object { $_.CommandLine -like '*com.yiyiaddon.seedworker.SeedWorkerMain*' })
    foreach ($worker in $workers) {
        $pidValue = [int]$worker.ProcessId
        if ($pidValue -ne $lastPid) {
            $lastPid = $pidValue
            $workerLifecycle.Add("t=${elapsed}s 出现 Worker PID=$pidValue 可执行文件=$($worker.Name)")
            if (-not $workerCommand) { $workerCommand = $worker.CommandLine }
        }
        $proc = Get-Process -Id $pidValue -ErrorAction SilentlyContinue
        if ($proc) {
            $rss = [int]($proc.WorkingSet64 / 1MB)
            if ($rss -gt $peakRss) { $peakRss = $rss }
            $rssSamples.Add("t=${elapsed}s pid=$pidValue rssMB=$rss")
        }
    }
    if ($lastPid -ne -1 -and -not ($workers | Where-Object { [int]$_.ProcessId -eq $lastPid })) {
        $workerLifecycle.Add("t=${elapsed}s Worker PID=$lastPid 已消失")
        $lastPid = -1
    }
    Start-Sleep -Milliseconds 1000
}

$timedOut = -not $client.HasExited
if ($timedOut) {
    Write-Smoke "**客户端超时（$TimeoutSeconds 秒），强制结束**"
    $client.Kill()
}
$client.WaitForExit()
Set-Content -LiteralPath (Join-Path $evidenceDir 'client-stdout.txt') -Encoding UTF8 -Value $stdoutTask.Result
Set-Content -LiteralPath (Join-Path $evidenceDir 'client-stderr.txt') -Encoding UTF8 -Value $stderrTask.Result

$exitCode = $client.ExitCode
$clientSeconds = [int]((Get-Date) - $startedAt).TotalSeconds
Write-Smoke "客户端退出：退出码 $exitCode，耗时 ${clientSeconds}s"

# ── 5. 断言：Worker 真实命令行不得含开发目录 / 必须用正式产物 ──────────────────
if (-not $workerCommand) {
    $script:failures.Add('没有观察到任何 Worker 进程（正式产物没能启动 Worker）')
} else {
    Set-Content -LiteralPath (Join-Path $evidenceDir 'worker-command.txt') -Encoding UTF8 -Value $workerCommand
    $forbidden = @('\build\classes', '\build\resources', '\build\devlaunch', '\src\main\java',
        '\src\main\resources', 'fabric-loom', '\run-26.1.2')
    foreach ($needle in $forbidden) {
        if ($workerCommand -like "*$needle*") {
            $script:failures.Add("Worker 命令行包含开发目录：$needle")
        }
    }
    if ($workerCommand -notlike "*$modJarName*") {
        $script:failures.Add("Worker 命令行里没有正式产物 $modJarName")
    }
    Write-Smoke "Worker 命令行已记录（$($workerCommand.Length) 字符）→ evidence\worker-command.txt"
}

Set-Content -LiteralPath (Join-Path $evidenceDir 'worker-lifecycle.txt') -Encoding UTF8 -Value $workerLifecycle
Set-Content -LiteralPath (Join-Path $evidenceDir 'worker-rss-samples.txt') -Encoding UTF8 -Value $rssSamples
if ($rssSamples.Count -gt 0) {
    $values = $rssSamples | ForEach-Object { [int]($_ -replace '.*rssMB=', '') }
    $peak = ($values | Measure-Object -Maximum).Maximum
    $last = $values[-1]
    Write-Smoke "Worker RSS：峰值 ${peak} MB，末次 ${last} MB，采样 $($rssSamples.Count) 次"
    Set-Content -LiteralPath (Join-Path $evidenceDir 'worker-rss-summary.txt') -Encoding UTF8 -Value @(
        "峰值RSS_MB=$peak", "末次RSS_MB=$last", "采样次数=$($rssSamples.Count)"
    )
}
Get-Content -LiteralPath (Join-Path $evidenceDir 'worker-lifecycle.txt') | ForEach-Object { Write-Smoke "  $_" }

# ── 6. 残留进程 ───────────────────────────────────────────────────────────────
Start-Sleep -Seconds 3
$leftover = @(Get-CimInstance Win32_Process -Filter "Name='java.exe' OR Name='javaw.exe'" -ErrorAction SilentlyContinue |
    Where-Object { $_.CommandLine -like '*com.yiyiaddon.seedworker.SeedWorkerMain*' })
Write-Smoke "退出后残留 Worker 进程：$($leftover.Count)"
if ($leftover.Count -gt 0) {
    $script:failures.Add("退出后仍有 $($leftover.Count) 个 Worker 残留进程（PID $($leftover.ProcessId -join ',')）")
}

# ── 7. 结论 ───────────────────────────────────────────────────────────────────
$reports = @(Get-ChildItem -LiteralPath $GameDir -File -Filter 'seedpoc-*.txt' | Select-Object -ExpandProperty Name)
if ($reports.Count -gt 0) { Write-Smoke "装置报告：$($reports -join ', ')" } else { Write-Smoke '装置报告：无（未驱动到装置）' }

if ($script:failures.Count -gt 0) {
    Write-Smoke '结论：不通过'
    foreach ($failure in $script:failures) { Write-Smoke "  - $failure" }
    exit 3
}
Write-Smoke '结论：通过（正式产物 Worker 已启动、命令行无开发目录、退出后无残留）'
exit 0
