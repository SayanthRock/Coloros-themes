# ColorOS Themes Rock

Customer-safe theme module and **ColorOS Customizer APK** starter for **OPPO, OnePlus, and realme** devices running ColorOS, OxygenOS, or realme UI.

This project is for legal customization using assets that you created, own, or have permission to share.

## What this project builds

- A flashable Magisk, KernelSU, or APatch-style theme module ZIP.
- A safe theme inspection pipeline for ColorOS, OPlus, and realme `.theme` files.
- A systemless overlay target for `/system_ext/media/themeInner/`.
- A clean customization structure for wallpapers, icons, fonts, sounds, previews, customer support, and rollback notes.
- A ColorOS Customizer helper APK for customer-facing wallpaper, device report, support, permission status, root status, LSPosed status, performance level status, and safe toggles.
- GitHub Actions workflows for APK builds, module ZIP builds, artifacts, checksums, provenance, and optional GitHub Releases.
- Release-channel metadata for Stable, Beta, and Nightly customer update checks.
- A Rootd UI feature map for status-first APK behavior.
- A Performance Level configuration for safe Off, Balanced, Battery Saver, Smooth, Performance, and Custom presets.
- A working-only policy so problematic APK features stay hidden, blocked, or marked as Needs testing.

## Supported targets

| Brand | Android skin | Status |
|---|---|---|
| OPPO | ColorOS | Starter support |
| OnePlus | OxygenOS / ColorOS based builds | Starter support |
| realme | realme UI | Starter support |
| Android 15 | Modern supported target | Planned testing |
| Android 16 | Modern supported target | Planned testing |
| Android 17 | Latest target | Device-by-device testing required |

Read the full compatibility plan in [`docs/compatibility.md`](docs/compatibility.md).

## Customer feature labels

Every feature shown in the APK should use a clear status label.

| Label | Meaning |
|---|---|
| Working | Tested on a real matching device. |
| Safe guidance | Opens Android settings or gives safe instructions only. |
| Limited | Works only on specific ROMs, Android versions, or device models. |
| Needs testing | Built but not verified enough for normal customers. |
| Blocked | Hidden or disabled because the feature is problematic. |
| Not available | Unsupported on the current device or ROM. |
| Root required | Needs Magisk, KernelSU, APatch, or equivalent root access. |
| LSPosed scope required | Needs LSPosed/Xposed scope for the target package. |

Free customer tools should stay visible. Advanced or untested items must show clear labels so customers know what works, what needs testing, and what is not available on their device.

## Uploaded theme package metadata

The uploaded packages were inspected as ZIP-based theme archives with `themeInfo.xml` metadata.

| File | Detected theme root | Main contents | Resolution from metadata |
|---|---|---|---|
| `aquatic_design.theme` | `OplusSmartPhoneThemeInfo` | launcher, icons, picture, wallpaper | 2400x1080 |
| `1-Simplicity.theme` | `OppoSmartPhoneThemeInfo` | OPPO launcher, wallpaper, previews | 2340x1080 |

Use these only when sharing rights are clear.

## Uploaded Rock Theme ZIP note

The uploaded `rock theme .zip` was inspected as a private sample. It appears to be an extracted Android APK/module bundle, not a clean ColorOS `.theme` package. It contains compiled Android files, native binaries, Xposed metadata, and signing/test-key style files, so it was **not copied into this repo**.

Read the report in [`docs/uploaded-rock-theme-analysis.md`](docs/uploaded-rock-theme-analysis.md).

## APK Rootd, Performance Level, working-only, and release automation

| Guide | Purpose |
|---|---|
| [`docs/RELEASE_AUTOMATION.md`](docs/RELEASE_AUTOMATION.md) | Explains APK/module release upload automation, version input, channel input, artifacts, and GitHub Release upload. |
| [`docs/WORKING_ONLY_APK_POLICY.md`](docs/WORKING_ONLY_APK_POLICY.md) | Defines what stays enabled, what is hidden, and how problematic APK/ZIP features are blocked. |
| [`docs/APK_ROOTD_IMPROVEMENT_PLAN.md`](docs/APK_ROOTD_IMPROVEMENT_PLAN.md) | Status-first Rootd dashboard, root manager checks, LSPosed checks, scope checks, rollback, and support report. |
| [`docs/PERFORMANCE_LEVEL_PLAN.md`](docs/PERFORMANCE_LEVEL_PLAN.md) | Safe Performance Level design for Off, Battery Saver, Balanced, Smooth, Performance, and Custom presets. |
| [`docs/THEMES_UI_DESIGN_2026.md`](docs/THEMES_UI_DESIGN_2026.md) | 2026 APK UI style for themes, permissions, Rootd, Performance Level, compatibility, and customer-safe labels. |
| [`customer-options/working-only-policy.json`](customer-options/working-only-policy.json) | Machine-readable working-only policy for future APK screens. |
| [`customer-options/rootd-ui-feature-map.json`](customer-options/rootd-ui-feature-map.json) | Machine-readable feature map for future Rootd APK screens. |
| [`customer-options/performance-levels.json`](customer-options/performance-levels.json) | Machine-readable Performance Level configuration for future APK screens. |

## Folder structure

```text
.
├── module.prop
├── customize.sh
├── post-fs-data.sh
├── service.sh
├── uninstall.sh
├── system_ext/media/themeInner/
├── themes/default/
├── themes/theme-pack-catalog.json
├── theme-packs/
├── customer-options/
│   ├── options.json
│   ├── working-only-policy.json
│   ├── rootd-ui-feature-map.json
│   └── performance-levels.json
├── scripts/
│   ├── package.sh
│   ├── validate-module.sh
│   ├── check-theme-size.sh
│   └── inspect-theme-package.py
├── docs/
│   ├── compatibility.md
│   ├── permissions.md
│   ├── safety.md
│   ├── uploaded-rock-theme-analysis.md
│   ├── RELEASE_AUTOMATION.md
│   ├── WORKING_ONLY_APK_POLICY.md
│   ├── APK_ROOTD_IMPROVEMENT_PLAN.md
│   ├── PERFORMANCE_LEVEL_PLAN.md
│   ├── THEMES_UI_DESIGN_2026.md
│   ├── THEME_MODULE_BUILDER.md
│   ├── DEFAULT_THEME_CUSTOMER_GUIDE.md
│   ├── UI_DESIGN_SYSTEM.md
│   ├── LAG_FIX_GUIDE.md
│   └── PROBLEM_SOLVER_MATRIX.md
├── lsposed-helper/
├── latestStable.json
├── latestBeta.json
├── latestNightly.json
└── .github/workflows/
    ├── build-theme-module.yml
    ├── validate-apk.yml
    └── publish-github-release.yml
```

## Build Theme Module

Inspect a theme package:

```bash
python3 scripts/inspect-theme-package.py /path/to/theme.theme --pretty
```

Validate and build:

```bash
bash scripts/validate-module.sh
bash scripts/package.sh
```

Output:

```text
dist/ColorOS-Themes-Rock-v0.5.4.zip
```

## Build from GitHub Actions

1. Open **Actions**.
2. Select **Build Theme Module**.
3. Tap **Run workflow**.
4. Enter a version like `v0.5.4`.
5. Keep release publishing enabled to upload the module ZIP to a public GitHub Release.

The workflow also runs on pull requests and pushes that change module, theme, script, docs, release metadata, or workflow files.

## Build ColorOS Customizer APK

Manual local build:

```bash
cd lsposed-helper
gradle :app:assembleDebug --no-daemon --stacktrace
```

Output:

```text
lsposed-helper/app/build/outputs/apk/debug/app-debug.apk
```

Validate APK automation is handled by:

```text
.github/workflows/validate-apk.yml
```

APK and combined release automation is handled by:

```text
.github/workflows/publish-github-release.yml
```

## Publish APK Release

Manual release upload:

1. Open **Actions**.
2. Select **Publish GitHub Release**.
3. Tap **Run workflow**.
4. Choose channel: `beta`, `stable`, or `nightly`.
5. Enter version, for example `v0.5.6-beta`.
6. Keep **publish** enabled.
7. Run the workflow.

The workflow uploads APK files, module ZIP, `BUILD_INFO.txt`, and `SHA256SUMS.txt` to the GitHub Release.

## Release channels

| Channel | File | Intended use |
|---|---|---|
| Stable | `latestStable.json` | Tested public customer builds. |
| Beta | `latestBeta.json` | Broader testing before stable. |
| Nightly | `latestNightly.json` | Experimental development builds. |

These files can be used later by the APK update screen, GitHub release automation, or a simple in-app update checker.

## Customer customization matrix

| Area | Supported approach | Safety status |
|---|---|---|
| Wallpaper | User-selected image or owned theme package | Safe |
| Home screen | Wallpaper API or OEM theme package | Device testing required |
| Lock screen | Wallpaper API or OEM theme package | Device testing required |
| Icons | Theme scanner and launcher-supported guidance only | Needs testing |
| Fonts | Owned font/theme asset | Device testing required |
| Sounds | Owned ringtone/UI sound asset | Device testing required |
| Preview images | Catalog previews and screenshots | Ready |
| Battery/performance | Performance Level presets, diagnostics, shortcuts, and lag-fix guide | Safe, no fake booster claims |
| APK system improvement | Working-only dashboard, status checks, safe settings shortcuts, and support report | Working-only |
| Rollback | Uninstall script and customer guide | Documented |

## Install module ZIP

1. Download the latest module ZIP artifact or GitHub Release asset.
2. Open Magisk, KernelSU, or APatch.
3. Flash the module ZIP.
4. Reboot.
5. Test on one device before sharing with customers.

## Safety rule

Only distribute themes, wallpapers, fonts, icons, sounds, previews, and UI files that you created, own, or have permission to share.

Read the safety guide in [`docs/safety.md`](docs/safety.md), the Android permission guide in [`docs/permissions.md`](docs/permissions.md), and the working-only APK policy in [`docs/WORKING_ONLY_APK_POLICY.md`](docs/WORKING_ONLY_APK_POLICY.md).

## GPL reference rule

Oxygen Customizer is useful as an open-source reference for structure, release channels, LSPosed/root flow, and customer warning style. Do not copy GPL-3.0 source code into this project unless the project license and distribution method are GPL-compatible.
