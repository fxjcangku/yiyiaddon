#Requires -Version 5.1
<#
  237 · 真实用户环境（100+ Mods + Iris / Sodium + 光影）兼容 smoke 驱动器。

  为什么要单独跑一次：236 改过渲染器（多矿物快照）、Worker / runtime 与维度语义，
  「开发者自己的 100+ Mods 环境还能不能正常起来」是一条独立证据（236 报告的限制第 7 条）。

  铁律（本脚本只读源目录、只写验收目录）：
    · 源：D:\mcaddon\yiyiaddon\run-26.1.2 —— 开发者的真实客户端，**只读**；
    · 目标：<仓库>\.dev-runs\26.1.2\seed\final-user-env —— 只写这里；
    · 跑完后核对源目录「最新写入时间」没有变化，否则直接判不通过。

  跑的装置：SeedOreMatrixRegression（多矿物 / 多维度矩阵）——
  它一次覆盖「能启动 / Seed ESP / 维度切换 / Worker 退出」四件事，正好是本轮要验的。

  用法：powershell -NoProfile -ExecutionPolicy Bypass -File gradle/final-user-env.ps1
#>
[CmdletBinding()]
param(
    [string]$SourceRunDir = 'D:\mcaddon\yiyiaddon\run-26.1.2',
    [int]$ClientTimeoutSeconds = 1800
)

$ErrorActionPreference = 'Stop'
$repoRoot = Split-Path -Parent $PSScriptRoot
$target = Join-Path $repoRoot '.dev-runs\26.1.2\seed\final-user-env'
$logDir = Join-Path $target 'userenv-logs'
New-Item -ItemType Directory -Force -Path $target, $logDir | Out-Null

function Write-Step([string]$text) { Write-Host "[USER-ENV] $text" }

if (-not (Test-Path -LiteralPath $SourceRunDir)) { throw "源运行目录不存在：$SourceRunDir" }

function Get-NewestWrite([string]$dir) {
    $item = Get-ChildItem -LiteralPath $dir -Recurse -File -ErrorAction SilentlyContinue |
        Sort-Object LastWriteTime -Descending | Select-Object -First 1
    if ($item) { return $item.LastWriteTime } else { return [datetime]::MinValue }
}

$sourceStampBefore = Get-NewestWrite $SourceRunDir
Write-Step "源目录最新写入（复制前）：$sourceStampBefore"

$failures = New-Object System.Collections.Generic.List[string]

# ── 1. 只读复制真实用户环境 ───────────────────────────────────────────────────
$copiedMods = 0
foreach ($name in @('mods')) {
    $from = Join-Path $SourceRunDir $name
    if (-not (Test-Path -LiteralPath $from)) { continue }
    $to = Join-Path $target $name
    New-Item -ItemType Directory -Force -Path $to | Out-Null
    foreach ($file in Get-ChildItem -LiteralPath $from -File) {
        # 本模组自己不在 mods 里（开发运行由 classpath 加载）；若用户目录里有同 id 的 jar 必须排除，
        # 否则 Fabric 会报重复 mod id
        if ($file.Name -like 'yiyiaddon*') { continue }
        Copy-Item -LiteralPath $file.FullName -Destination $to -Force
        if ($file.Extension -eq '.jar') { $copiedMods++ }
    }
}
Write-Step "mods 已复制：$copiedMods 个 jar"
if ($copiedMods -lt 100) { $failures.Add("真实环境 mods 只有 $copiedMods 个，达不到 100+ Mods 口径") }

foreach ($name in @('config', 'shaderpacks')) {
    $from = Join-Path $SourceRunDir $name
    if (-not (Test-Path -LiteralPath $from)) { continue }
    $to = Join-Path $target $name
    New-Item -ItemType Directory -Force -Path $to | Out-Null
    Copy-Item -LiteralPath (Join-Path $from '*') -Destination $to -Recurse -Force -ErrorAction SilentlyContinue
    Write-Step "$name 已复制"
}

# 必要 options：真实用户的 options.txt（渲染距离 / 光影开关 / 视角等），只补两个「自动装置必须的」开关
$sourceOptions = Join-Path $SourceRunDir 'options.txt'
$targetOptions = Join-Path $target 'options.txt'
$lines = if (Test-Path -LiteralPath $sourceOptions) { @(Get-Content -LiteralPath $sourceOptions -Encoding UTF8) } else { @() }
$wanted = @{ 'onboardAccessibility' = 'false'; 'skipMultiplayerWarning' = 'true' }
$seen = @{}
$patched = foreach ($line in $lines) {
    $colon = $line.IndexOf(':')
    if ($colon -gt 0) {
        $key = $line.Substring(0, $colon)
        if ($wanted.ContainsKey($key)) { $seen[$key] = $true; "$key`:$($wanted[$key])"; continue }
    }
    $line
}
foreach ($key in $wanted.Keys) { if (-not $seen.ContainsKey($key)) { $patched += "$key`:$($wanted[$key])" } }
Set-Content -LiteralPath $targetOptions -Encoding UTF8 -Value $patched
Write-Step "options.txt 已复制并落下自动装置必需的开关"

# ── 2. 跑客户端 ───────────────────────────────────────────────────────────────
$clientOut = Join-Path $logDir 'client-stdout.txt'
Write-Step "启动客户端（真实用户环境）：gradlew runClientSeedFinalUserEnvTest"
$clientProc = Start-Process -FilePath (Join-Path $repoRoot 'gradlew.bat') `
    -ArgumentList '--offline', 'runClientSeedFinalUserEnvTest' `
    -WorkingDirectory $repoRoot -PassThru -NoNewWindow `
    -RedirectStandardOutput $clientOut -RedirectStandardError (Join-Path $logDir 'client-stderr.txt')

$deadline = (Get-Date).AddSeconds($ClientTimeoutSeconds)
while (-not $clientProc.HasExited -and (Get-Date) -lt $deadline) { Start-Sleep -Seconds 5 }
if (-not $clientProc.HasExited) {
    $failures.Add("client timeout ($ClientTimeoutSeconds s), killed")
    Write-Step 'client TIMEOUT, killing'
    Stop-Process -Id $clientProc.Id -Force -ErrorAction SilentlyContinue
} else {
    $exitCode = -1
    try { $exitCode = [int]$clientProc.ExitCode } catch { $exitCode = -1 }
    Write-Step "client exited with code $exitCode"
    if ($exitCode -gt 0) { $failures.Add("client exit code $exitCode != 0") }
}

# ── 3. 残留 Worker 进程 ───────────────────────────────────────────────────────
Start-Sleep -Seconds 3
$leftover = @(Get-CimInstance Win32_Process -Filter "Name='java.exe' OR Name='javaw.exe'" -ErrorAction SilentlyContinue |
    Where-Object { $_.CommandLine -like '*com.yiyiaddon.seedworker.SeedWorkerMain*' })
Write-Step "leftover worker processes: $($leftover.Count)"
if ($leftover.Count -gt 0) { $failures.Add("leftover worker processes: $($leftover.Count)") }

# ── 4. 日志读数：Mod 数 / Iris / Sodium / yiyiaddon 异常 ─────────────────────
$text = if (Test-Path -LiteralPath $clientOut) { Get-Content -LiteralPath $clientOut -Raw -Encoding UTF8 } else { '' }
$modCount = 0
if ($text -match 'Loading (\d+) mods') { $modCount = [int]$Matches[1] }
$hasIris = $text -match '(?m)^\s*- iris\b'
$hasSodium = $text -match '(?m)^\s*- sodium\b'
$seedpocMounted = $text -match '开发期实验已挂载'
$crash = $text -match 'Crash report|A crash has been detected|Unexpected error'
# yiyiaddon 自己的 ERROR 行（排除崩溃报告里的正常堆栈提示）
$yiyiErrors = @($text -split "`r?`n" | Where-Object { $_ -match '\(yiyiaddon' -and $_ -match 'ERROR' })
Write-Step "已加载 mods：$modCount（Iris=$hasIris / Sodium=$hasSodium）；装置挂载=$seedpocMounted；崩溃=$crash"
if ($modCount -lt 100) { $failures.Add("已加载 mods 数 $modCount < 100") }
if (-not $hasIris) { $failures.Add('日志里没有 iris（光影管线未加载）') }
if (-not $hasSodium) { $failures.Add('日志里没有 sodium') }
if (-not $seedpocMounted) { $failures.Add('装置没有挂载（seedpoc 未生效）') }
if ($crash) { $failures.Add('客户端日志出现崩溃痕迹') }
if ($yiyiErrors.Count -gt 0) { $failures.Add("yiyiaddon 出现 $($yiyiErrors.Count) 条 ERROR 日志") }

# ── 5. 装置报告 ───────────────────────────────────────────────────────────────
$report = Join-Path $target 'seedpoc-236-矿物矩阵.txt'
$final = ''
if (Test-Path -LiteralPath $report) {
    $final = Get-Content -LiteralPath $report -Encoding UTF8 | Where-Object { $_ -like '全部判定*' } | Select-Object -Last 1
    $dimSwitch = Select-String -LiteralPath $report -Pattern '切换清空' -SimpleMatch | Select-Object -First 1
    Write-Step "装置报告：$final"
    if ($dimSwitch) { Write-Step "维度切换读数：$($dimSwitch.Line.Trim())" }
} else {
    $failures.Add('真实用户环境里没有生成装置报告')
}
if ($final -and ($final -like '*不通过*')) { $failures.Add('真实用户环境里装置存在不通过项') }

# ── 6. 源目录零写入核对 ───────────────────────────────────────────────────────
$sourceStampAfter = Get-NewestWrite $SourceRunDir
Write-Step "源目录最新写入（复制后）：$sourceStampAfter"
if ($sourceStampAfter -ne $sourceStampBefore) {
    $failures.Add("源运行目录被写入过：$sourceStampBefore → $sourceStampAfter")
}

# ── 7. 判决 ───────────────────────────────────────────────────────────────────
$judgement = Join-Path $target 'final-user-env-judgement.txt'
$judgementLines = @(
    '《237 · 真实用户环境（100+ Mods + Iris / Sodium）兼容 smoke 判决》',
    "源目录（只读）：$SourceRunDir",
    "源目录最新写入：$sourceStampBefore → $sourceStampAfter",
    "复制到：$target",
    "mods jar：$copiedMods 个；客户端报告已加载 mods：$modCount",
    "Iris=$hasIris；Sodium=$hasSodium；装置挂载=$seedpocMounted；崩溃痕迹=$crash",
    "yiyiaddon ERROR 行：$($yiyiErrors.Count)",
    "残留 Worker 进程：$($leftover.Count)",
    "装置报告：$report",
    "装置全部判定：$final"
)
if ($failures.Count -gt 0) {
    $judgementLines += '发现的问题：'
    $judgementLines += ($failures | ForEach-Object { "  - $_" })
    $judgementLines += '判决：不通过'
} else {
    $judgementLines += '判决：通过'
}
Set-Content -LiteralPath $judgement -Encoding UTF8 -Value $judgementLines
Get-Content -LiteralPath $judgement | ForEach-Object { Write-Step "  $_" }

if ($failures.Count -gt 0) { exit 3 }
exit 0
