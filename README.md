# ColorOS Themes Rock

Customer-safe theme module and **ColorOS Customizer APK** starter for **OPPO, OnePlus, and realme** devices running ColorOS, OxygenOS, or realme UI.

This project is for legal customization using assets that you created, own, or have permission to share.

## What this project builds

- A flashable Magisk, KernelSU, or APatch-style theme module ZIP.
- A safe theme inspection pipeline for ColorOS, OPlus, and realme `.theme` files.
- A systemless overlay target for `/system_ext/media/themeInner/`.
- A clean customization structure for wallpapers, icons, fonts, sounds, previews, customer support, and rollback notes.
- A Rootd customer foundation for status-first package targets: `android`, `com.android.systemui`, and `com.android.settings`.
- A ColorOS Customizer helper APK for wallpaper, device reports, Android 15/16/17 labels, Rootd system-health reports, permission status, root-manager visibility, LSPosed manager visibility, and safe toggles.
- GitHub Actions workflows for APK builds, module ZIP builds, artifacts, checksums, provenance, and optional GitHub Releases.
- Release-channel metadata for Stable, Beta, and Nightly customer update checks.

## Current release line

| Area | Status |
|---|---|
| Module version | `v0.6.0` |
| Helper APK version | `0.6.0` / versionCode `12` |
| Android 15 | Supported modern target |
| Android 16 | Forward-compatible, device testing required |
| Android 17 | Preview/future-safe, device testing required |
| OPPO / OnePlus / realme | Status-first support |
| Rootd/system file work | Systemless-only policy |

## Supported targets

| Brand | Android skin | Status |
|---|---|---|
| OPPO | ColorOS | Starter support |
| OnePlus | OxygenOS / ColorOS based builds | Starter support |
| realme | realme UI | Starter support |
| Android 15 | SDK 35 | Supported target |
| Android 16 | SDK 36 | Forward-compatible testing |
| Android 17 | SDK 37 | Device-by-device testing required |

Read the compatibility plan in [`docs/compatibility.md`](docs/compatibility.md) and the Android support policy in [`docs/ANDROID_15_16_17_SUPPORT.md`](docs/ANDROID_15_16_17_SUPPORT.md).

## Rootd systemless support

The project now uses a strict systemless-only policy for system-file-related theme work.

| Area | Policy |
|---|---|
| Theme mount target | `system_ext/media/themeInner` inside the module package |
| Direct `/system` writes from APK | Not allowed |
| Direct `/vendor`, `/product`, `/system_ext` writes from APK | Not allowed |
| Rollback | Safe-disable file and uninstall marker |
| Reports | `/data/local/tmp/coloros-themes-rock/` |

Read [`docs/ROOTD_SYSTEM_FILE_SUPPORT.md`](docs/ROOTD_SYSTEM_FILE_SUPPORT.md) for the systemless Rootd support guide.

## Customer feature labels

Every feature shown in the APK should use a clear status label.

| Label | Meaning |
|---|---|
| Working | Tested on a real matching device. |
| Limited | Works only on specific ROMs, Android versions, or device models. |
| Needs testing | Built but not verified enough for normal customers. |
| Not available | Unsupported on the current device or ROM. |
| Root required | Needs Magisk, KernelSU, APatch, or equivalent root access. |
| LSPosed scope required | Needs LSPosed/Xposed scope for the target package. |

Free customer tools should stay visible. Advanced or untested items must show clear labels so customers know what works, what needs testing, and what is not available on their device.

## Rootd customer overlay targets

The Rootd customer layer is status-first and safe by default.

| Target package | Customer area | Default status | Folder |
|---|---|---|---|
| `android` | Framework visual resources | Needs testing | `assets/Overlays/android/` |
| `com.android.systemui` | Status bar, quick settings, notifications, lock surface previews | Needs testing | `assets/Overlays/systemui/` |
| `com.android.settings` | Settings cards, About phone, support and diagnostics previews | Needs testing | `assets/Overlays/settings/` |

The package target map is stored in [`assets/Overlays/targets.json`](assets/Overlays/targets.json). Customer-safe defaults are stored in [`customer-options/safe-defaults.conf`](customer-options/safe-defaults.conf).

## Uploaded theme package metadata

The uploaded packages were inspected as ZIP-based theme archives with `themeInfo.xml` metadata.

| File | Detected theme root | Main contents | Resolution from metadata |
|---|---|---|---|
| `aquatic_design.theme` | `OplusSmartPhoneThemeInfo` | launcher, icons, picture, wallpaper | 2400x1080 |
| `1-Simplicity.theme` | `OppoSmartPhoneThemeInfo` | OPPO launcher, wallpaper, previews | 2340x1080 |

Use these only when sharing rights are clear.

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
│   └── safe-defaults.conf
├── assets/Overlays/
├── scripts/
│   ├── package.sh
│   ├── validate-module.sh
│   ├── check-theme-size.sh
│   └── inspect-theme-package.py
├── docs/
│   ├── compatibility.md
│   ├── permissions.md
│   ├── safety.md
│   ├── ANDROID_15_16_17_SUPPORT.md
│   ├── ROOTD_SYSTEM_FILE_SUPPORT.md
│   ├── ROOTD_CUSTOMER_FOUNDATION.md
│   ├── UI_DESIGN_SYSTEM.md
│   ├── LAG_FIX_GUIDE.md
│   └── PROBLEM_SOLVER_MATRIX.md
├── lsposed-helper/
├── latestStable.json
├── latestBeta.json
├── latestNightly.json
└── .github/workflows/
    ├── build-theme-module.yml
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
dist/ColorOS-Themes-Rock-v0.6.0.zip
```

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

APK and combined release automation is handled by:

```text
.github/workflows/publish-github-release.yml
```

## Release channels

| Channel | File | Intended use |
|---|---|---|
| Stable | `latestStable.json` | Tested public customer builds. |
| Beta | `latestBeta.json` | Broader testing before stable. |
| Nightly | `latestNightly.json` | Experimental development builds. |

## Customer customization matrix

| Area | Supported approach | Safety status |
|---|---|---|
| Wallpaper | User-selected image or owned theme package | Safe |
| Home screen | Wallpaper API or OEM theme package | Device testing required |
| Lock screen | Wallpaper API or OEM theme package | Device testing required |
| Icons | Theme icon asset or launcher icon pack | Device testing required |
| Fonts | Owned font/theme asset | Device testing required |
| Sounds | Owned ringtone/UI sound asset | Device testing required |
| Preview images | Catalog previews and screenshots | Ready |
| System UI | Status-first target preview and tested overlay workflow | Needs testing |
| Settings UI | Settings shortcuts, support screen, and tested overlay workflow | Needs testing |
| Android framework | Framework visual preview and tested overlay workflow | Needs testing |
| Battery/performance | Diagnostics, shortcuts, lag-fix guide | Safe, no fake booster claims |
| Rootd system files | Systemless-only module path and support reports | Safe by default, needs device testing for advanced overlays |
| Rollback | Uninstall script and customer guide | Documented |

## Install module ZIP

1. Download the latest module ZIP artifact or GitHub Release asset.
2. Open Magisk, KernelSU, or APatch.
3. Flash the module ZIP.
4. Reboot.
5. Test on one device before sharing with customers.

## Safety rule

Only distribute themes, wallpapers, fonts, icons, sounds, previews, and UI files that you created, own, or have permission to share.

Read the safety guide in [`docs/safety.md`](docs/safety.md) and the Android permission guide in [`docs/permissions.md`](docs/permissions.md).

## GPL reference rule

Oxygen Customizer is useful as an open-source reference for structure, release channels, LSPosed/root flow, and customer warning style. Do not copy GPL-3.0 source code into this project unless the project license and distribution method are GPL-compatible.
