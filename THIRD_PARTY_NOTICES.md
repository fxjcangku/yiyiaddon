yiyiaddon — THIRD PARTY NOTICES
===============================

yiyiaddon itself (the original code authored for this project) is distributed
under the PVPUtils Source-Available Non-Commercial License v1.0, because it is a
derivative work of PVPUtils. See LICENSE and NOTICE.

The components listed below are third-party works. They keep their own licences
and are not relicensed by yiyiaddon.

----------------------------------------------------------------------
1. PVPUtils — PVPUtils Source-Available Non-Commercial License v1.0
----------------------------------------------------------------------

    Project:             PVPUtils
    Original Author:     Nachoneko_miao
    Contributors:        PVPUtils contributors
    Original Repository: https://github.com/bakabaicai/PVPUtils

Source code incorporated into yiyiaddon: the ClickGUI framework (screen, pages,
widgets, themes, Skija render helpers, font renderer, UI keybind manager).
Details and modification disclosure: NOTICE, section 2. Full licence text:
LICENSE.

----------------------------------------------------------------------
2. Baritone — GNU Lesser General Public License v3.0
----------------------------------------------------------------------

    Project:             Baritone
    Upstream Repository: https://github.com/cabaletta/baritone
    Branch used:         26.1
    Commit used:         016cc210434894f371baf0c327ac94907d12dcd3
    Upstream version:    1.18.0
    Licence:             LGPL-3.0
    Licence text:        src/main/resources/LICENSE-baritone.txt

Baritone is NOT modified by yiyiaddon. Upstream sources were built with the
project's own `:fabric:remapJar` task and the resulting artifact was placed in
the in-repository local Maven repository:

    maven-repo/baritone/baritone-fabric/1.18.0/baritone-fabric-1.18.0.jar
    size   5,042,583 bytes
    sha256 48AC8DB16848390B569880519496B4BEEB59F189FA83C398F6776EF1202E8B44

    src/main/resources/LICENSE-baritone.txt
    size   7,815 bytes
    sha256 F831E7EED577481687A9BC0B48024E5E40B6F655FCDE073EDE964B50BE5D55D9
    (byte-identical to the upstream Baritone LICENSE file)

Gradle declares it as `compileOnly` + `runtimeOnly` + `include`. The `include`
configuration puts Baritone into the published jar as an unmodified nested jar
(Fabric Jar-in-Jar) without shading or renaming, so the LGPL-3.0 obligations are
met as follows:

1. yiyiaddon declares that it uses Baritone and ships the full LGPL-3.0 text
   (`LICENSE-baritone.txt`, also present inside the nested jar).
2. Baritone remains a separate, replaceable module: users can drop their own
   build of the same version into the mods folder, or replace the nested jar.
3. Baritone itself is unmodified, so no new source-disclosure obligation arises.
   If it is ever modified, the modified Baritone sources must be published under
   LGPL-3.0.
4. No Baritone copyright or licence notice is removed.

The nested jar also carries Baritone's own runtime dependency `nether-pathfinder`
(`dev_babbaj_nether-pathfinder 1.6`). It is an upstream Baritone dependency, not
an additional component introduced by yiyiaddon, and is covered by Baritone's
distribution. It extracts a native library into the system temporary directory
at runtime, which is upstream behaviour.

yiyiaddon's Baritone integration code (`com.yiyiaddon.integration.baritone` and
`com.yiyiaddon.mixin.baritone`) is yiyiaddon's own work. It does not copy
Baritone source; it only rewrites user-visible text through Mixin. Baritone API
references are confined to those two packages.

----------------------------------------------------------------------
3. Skija — Apache License 2.0
----------------------------------------------------------------------

    Project:             Skija
    Repository:          https://github.com/HumbleUI/Skija
    Licence:             Apache-2.0
    Coordinates:
        io.github.humbleui:skija-shared:0.143.16
        io.github.humbleui:skija-windows-x64:0.143.16
        io.github.humbleui:skija-windows-arm64:0.143.16
        io.github.humbleui:types:0.2.0

Bundled as unchanged nested jars via `include`. Each jar retains its own
`META-INF` licence metadata. Skija is used as the ClickGUI 2D rendering backend
(drawing directly to the Minecraft main framebuffer).

----------------------------------------------------------------------
4. Bundled font files
----------------------------------------------------------------------

    assets/yiyiaddon/fonts/harmony.ttf
        HarmonyOS Sans — default interface typeface,
        © Huawei Device Co., Ltd., distributed under its own font licence.

    assets/yiyiaddon/fonts/MaterialSymbolsRounded.ttf
        Google Material Symbols (Rounded) — icon glyphs,
        © Google LLC, Apache-2.0.

    assets/yiyiaddon/fonts/icon.ttf
        Client icon glyphs, © PVPUtils contributors.

These files are redistributed unmodified for interface rendering only.

----------------------------------------------------------------------
5. Minecraft
----------------------------------------------------------------------

Minecraft is a trademark of Mojang Synergies AB. yiyiaddon is an unofficial
client-side modification and is not approved by, associated with, or endorsed by
Mojang Studios or Microsoft. No Minecraft code or asset is redistributed by this
project.
