#Requires -Version 5.1
<#
  237 · 下界真正多人（Dedicated Multiplayer）验收驱动器（26.2 线）。

  它按开发习惯第 232/233 条的既定做法起两台进程：
    ① 专用服务器：.\gradlew.bat runSeedNetherMultiplayerServer（端口 25865，level-seed=20260922，
       allow-nether=true，创造模式；服务端不加载 yiyiaddon —— 本模组 fabric.mod.json 声明
       environment=client，专用服务端由 Fabric Loader 直接不加载它）
    ② 客户端：.\gradlew.bat runClientSeedNetherMultiplayerTest（--quickPlayMultiplayer 127.0.0.1:25865，
       自动连接、自动跑装置、自动退出）

  退出后它做客户端自己做不到的那一条取证：盘点残留的 Worker 进程（孤儿 = 0）。

  用法：
    powershell -NoProfile -ExecutionPolicy Bypass -File gradle/nether-mp.ps1
    （可选 -ClientTimeoutSeconds 900 / -ServerPort 25865 / -AlternatePorts 25866,25867）

  产物：<客户端运行目录>\seedpoc-237-下界多人验收.txt（装置报告）
        <客户端运行目录>\nether-mp-judgement.txt（本脚本的判决与残留进程盘点）
#>
[CmdletBinding()]
param(
    [int]$ServerPort = 25865,
    [int[]]$AlternatePorts = @(25866, 25867),
    [int]$ServerReadyTimeoutSeconds = 300,
    [int]$ClientTimeoutSeconds = 900
)

$ErrorActionPreference = 'Stop'
$repoRoot = Split-Path -Parent $PSScriptRoot
$runRoot = Join-Path $repoRoot '.dev-runs\26.2\seed\nether-mp'
$serverDir = Join-Path $runRoot 'server'
$clientDir = Join-Path $runRoot 'client'
$logDir = Join-Path $runRoot 'logs'
New-Item -ItemType Directory -Force -Path $logDir | Out-Null

function Write-Step([string]$text) { Write-Host "[NETHER-MP] $text" }

function Test-PortFree([int]$port) {
    $busy = @(Get-NetTCPConnection -State Listen -ErrorAction SilentlyContinue |
        Where-Object { $_.LocalPort -eq $port })
    return $busy.Count -eq 0
}

# ── 0. 端口占用检查（口径给了 25865 / 25866 / 25867，任选未占用端口）───────────
if (-not (Test-PortFree $ServerPort)) {
    $chosen = 0
    foreach ($candidate in $AlternatePorts) {
        if (Test-PortFree $candidate) { $chosen = $candidate; break }
    }
    if ($chosen -eq 0) { throw "ServerPort and alternates are all busy" }
    Write-Step "ServerPort $ServerPort is busy, switching to $chosen"
    $ServerPort = $chosen
}

$failures = New-Object System.Collections.Generic.List[string]

# ── 1. 专用服务器 ─────────────────────────────────────────────────────────────
Write-Step "prepare dedicated server dir (port $ServerPort)"
& (Join-Path $repoRoot 'gradlew.bat') --offline -q "-PnetherMpPort=$ServerPort" prepareSeedNetherMultiplayerServer | Out-Null

$serverOut = Join-Path $logDir 'server-stdout.txt'
$serverErr = Join-Path $logDir 'server-stderr.txt'

Write-Step "start dedicated server"
$serverProc = Start-Process -FilePath (Join-Path $repoRoot 'gradlew.bat') `
    -ArgumentList '--offline', "-PnetherMpPort=$ServerPort", 'runSeedNetherMultiplayerServer' `
    -WorkingDirectory $repoRoot -PassThru -NoNewWindow `
    -RedirectStandardOutput $serverOut -RedirectStandardError $serverErr

$deadline = (Get-Date).AddSeconds($ServerReadyTimeoutSeconds)
$serverReady = $false
while ((Get-Date) -lt $deadline) {
    Start-Sleep -Seconds 2
    if (Test-Path -LiteralPath $serverOut) {
        $text = Get-Content -LiteralPath $serverOut -Raw -ErrorAction SilentlyContinue
        if ($text -and ($text -match 'Done \(' -or $text -match '"Done"')) { $serverReady = $true; break }
    }
    if ($serverProc.HasExited) { break }
}
if (-not $serverReady) {
    $failures.Add('dedicated server not ready within timeout (see logs/server-stdout.txt)')
    Write-Step 'server NOT ready'
}

$serverJavaPids = @()
if ($serverReady) {
    $serverJavaPids = @(Get-CimInstance Win32_Process -Filter "Name='java.exe' OR Name='javaw.exe'" -ErrorAction SilentlyContinue |
        Where-Object { $_.CommandLine -like '*KnotServer*' -or $_.CommandLine -like '*nether-mp\server*' } |
        Select-Object -ExpandProperty ProcessId)
    Write-Step "server ready; server java pids: $($serverJavaPids -join ',')"
}

# ── 2. 客户端 ─────────────────────────────────────────────────────────────────
$clientOut = Join-Path $logDir 'client-stdout.txt'
if ($serverReady) {
    Write-Step "start client"
    $clientProc = Start-Process -FilePath (Join-Path $repoRoot 'gradlew.bat') `
        -ArgumentList '--offline', "-PnetherMpPort=$ServerPort", 'runClientSeedNetherMultiplayerTest' `
        -WorkingDirectory $repoRoot -PassThru -NoNewWindow `
        -RedirectStandardOutput $clientOut -RedirectStandardError (Join-Path $logDir 'client-stderr.txt')

    $clientDeadline = (Get-Date).AddSeconds($ClientTimeoutSeconds)
    while (-not $clientProc.HasExited -and (Get-Date) -lt $clientDeadline) { Start-Sleep -Seconds 3 }
    if (-not $clientProc.HasExited) {
        $failures.Add("client timeout ($ClientTimeoutSeconds s), killed")
        Write-Step 'client TIMEOUT, killing'
        Stop-Process -Id $clientProc.Id -Force -ErrorAction SilentlyContinue
    } else {
        # $clientProc.ExitCode 在 -PassThru 的进程对象上偶尔读到空值，因此只把「明确的非零退出码」
        # 当作失败；取不到值时以装置报告 + Worker 残留两项判据为准（那两项才是本次要证的）。
        $exitCode = -1
        try { $exitCode = [int]$clientProc.ExitCode } catch { $exitCode = -1 }
        Write-Step "client exited with code $exitCode"
        if ($exitCode -gt 0) { $failures.Add("client exit code $exitCode != 0") }
    }
} else {
    $failures.Add('server not ready, client not started')
}

# ── 3. 回收服务器 ─────────────────────────────────────────────────────────────
foreach ($pidValue in $serverJavaPids) {
    Stop-Process -Id $pidValue -Force -ErrorAction SilentlyContinue
}
if (-not $serverProc.HasExited) {
    Stop-Process -Id $serverProc.Id -Force -ErrorAction SilentlyContinue
}
Start-Sleep -Seconds 3

# ── 4. 残留 Worker 进程盘点（孤儿 = 0）───────────────────────────────────────
$leftover = @(Get-CimInstance Win32_Process -Filter "Name='java.exe' OR Name='javaw.exe'" -ErrorAction SilentlyContinue |
    Where-Object { $_.CommandLine -like '*com.yiyiaddon.seedworker.SeedWorkerMain*' })
Write-Step "leftover worker processes: $($leftover.Count)"
if ($leftover.Count -gt 0) {
    $failures.Add("leftover worker processes: $($leftover.Count) (pids $($leftover.ProcessId -join ','))")
}

# ── 5. 装置报告与判决 ─────────────────────────────────────────────────────────
$report = Join-Path $clientDir 'seedpoc-237-下界多人验收.txt'
$allPass = $false
if (Test-Path -LiteralPath $report) {
    $reportLines = Get-Content -LiteralPath $report -Encoding UTF8
    $final = $reportLines | Where-Object { $_ -like '全部判定*' } | Select-Object -Last 1
    $allPass = ($final -ne $null) -and ($final -like '*通过*') -and ($final -notlike '*不通过*')
    Write-Step "device report: $final"
    if (-not $allPass) { $failures.Add("device report is not all-pass: $final") }
} else {
    $failures.Add('device report not generated')
}

$judgement = Join-Path $clientDir 'nether-mp-judgement.txt'
$verdictText = if ($allPass) { 'PASS' } else { 'FAIL-or-missing' }
$judgementLines = @(
    '《237 · 下界真正多人验收 · 驱动器判决（26.2 线）》',
    "端口：$ServerPort（备选 $($AlternatePorts -join ', ')）",
    "服务器就绪：$serverReady",
    "残留 Worker 进程：$($leftover.Count)",
    "装置报告：$(if (Test-Path -LiteralPath $report) { $report } else { '（无）' })",
    "装置全部判定：$verdictText"
)
if ($failures.Count -gt 0) {
    $judgementLines += '驱动器发现的问题：'
    $judgementLines += ($failures | ForEach-Object { "  - $_" })
    $judgementLines += '判决：不通过'
} else {
    $judgementLines += '判决：通过'
}
Set-Content -LiteralPath $judgement -Encoding UTF8 -Value $judgementLines
Get-Content -LiteralPath $judgement | ForEach-Object { Write-Step "  $_" }

if ($failures.Count -gt 0) { exit 3 }
exit 0
