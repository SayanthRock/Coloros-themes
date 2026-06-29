# ColorOS Themes Rock

Customer-safe theme module and **ColorOS Customizer APK** starter for **OPPO, OnePlus, and realme** devices running ColorOS, OxygenOS, or realme UI.

This project is for legal customization using assets that you created, own, or have permission to share.

## What this project builds

- A flashable Magisk, KernelSU, or APatch-style theme module ZIP.
- A safe theme inspection pipeline for ColorOS, OPlus, and realme `.theme` files.
- A systemless overlay target for `/system_ext/media/themeInner/`.
- A clean customization structure for wallpapers, icons, fonts, sounds, previews, customer support, and rollback notes.
- A Rootd customer foundation for status-first package targets: `android`, `com.android.systemui`, and `com.android.settings`.
- Customer overlay assets under `assets/Overlays/` for safe previews, presets, templates, support reports, and future tested overlay APK work.
- A ColorOS Customizer helper APK for customer-facing wallpaper, device report, support, permission status, root status, LSPosed status, and safe toggles.
- A setup-first APK experience for Magisk, KernelSU, APatch, LSPosed scopes, ROM-specific module profiles, module flashing guidance, and reboot verification.
- Main UI tweak focus areas: lock screen, status bar, quick settings, launcher, System UI, and supported lock screen weather features.
- GitHub Actions workflows for APK builds, module ZIP builds, artifacts, checksums, provenance, and optional GitHub Releases.
- Release-channel metadata for Stable, Beta, and Nightly customer update checks.

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
| Limited | Works only on specific ROMs, Android versions, or device models. |
| Needs testing | Built but not verified enough for normal customers. |
| Not available | Unsupported on the current device or ROM. |
| Root required | Needs Magisk, KernelSU, APatch, or equivalent root access. |
| LSPosed scope required | Needs LSPosed/Xposed scope for the target package. |

Free customer tools should stay visible. Advanced or untested items must show clear labels so customers know what works, what needs testing, and what is not available on their device.

## Setup-first customer flow

The ColorOS Customizer APK now starts with a guided setup path:

1. Detect a root manager such as Magisk, KernelSU, KernelSU Next, APatch, or a compatible module manager.
2. Check safe root indicators and show a readable status report.
3. Open LSPosed or a compatible manager when installed.
4. Tell users exactly which scopes to enable.
5. Generate a ROM Module Profile from brand, model, device, product, build display, Android release, Android SDK, ROM family, and detected managers.
6. Guide the user to flash the generated module ZIP.
7. Ask for a reboot after flashing or changing LSPosed scope.
8. Verify with the support report after boot.

Full details are in [`docs/SETUP_WIZARD.md`](docs/SETUP_WIZARD.md).

## Main APK focus

| Area | APK experience |
|---|---|
| Lock screen | Clock, wallpaper, media surface, and weather guidance where supported. |
| Status bar | Icon spacing, blur preference, status label support, and safe profile export. |
| Quick settings | Tile shape, transparency preference, header and brightness surface guidance. |
| Launcher | Grid, folder preview, icon-layer compatibility, and supported launcher notes. |
| System UI | SystemUI scope labels, notification surface guidance, and fallback status. |
| Weather lock screen | OPlus, ColorOS, realme, or HeyTap weather package guidance where supported. |

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
│   ├── README.md
│   ├── targets.json
│   ├── customer-overlay-preset.json
│   ├── android/
│   ├── systemui/
│   ├── settings/
│   └── templates/
├── scripts/
│   ├── package.sh
│   ├── validate-module.sh
│   ├── check-theme-size.sh
│   └── inspect-theme-package.py
├── docs/
│   ├── compatibility.md
│   ├── permissions.md
│   ├── safety.md
│   ├── SETUP_WIZARD.md
│   ├── THEME_MODULE_BUILDER.md
│   ├── DEFAULT_THEME_CUSTOMER_GUIDE.md
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
dist/ColorOS-Themes-Rock-v0.5.4.zip
```

## Build from GitHub Actions

1. Open **Actions**.
2. Select **Build Theme Module**.
3. Tap **Run workflow**.
4. Enter a version like `v0.5.4`.
5. Keep release publishing enabled to upload the module ZIP to a public GitHub Release.

The workflow also runs on pushes that change module, theme, script, docs, overlay asset, release metadata, or workflow files.

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
|---|---|
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
| Icons | Theme icon asset or launcher icon pack | Device testing required |
| Fonts | Owned font/theme asset | Device testing required |
| Sounds | Owned ringtone/UI sound asset | Device testing required |
| Preview images | Catalog previews and screenshots | Ready |
| System UI | Status-first target preview and tested overlay workflow | Needs testing |
| Settings UI | Settings shortcuts, support screen, and tested overlay workflow | Needs testing |
| Android framework | Framework visual preview and tested overlay workflow | Needs testing |
| Battery/performance | Diagnostics, shortcuts, lag-fix guide | Safe, no fake booster claims |
| Rollback | Uninstall script and customer guide | Documented |

## Install module ZIP

1. Download the latest module ZIP artifact or GitHub Release asset.
2. Open Magisk, KernelSU, APatch, or a compatible module manager.
3. Flash the generated ROM-specific module ZIP.
4. Enable the ColorOS Customizer APK in LSPosed or a compatible framework.
5. Scope only the required packages shown in the setup guide.
6. Reboot.
7. Open the app and copy the support report if something fails.

## Safety rule

Only distribute themes, wallpapers, fonts, icons, sounds, previews, and UI files that you created, own, or have permission to share.

Read the safety guide in [`docs/safety.md`](docs/safety.md) and the Android permission guide in [`docs/permissions.md`](docs/permissions.md).

## GPL reference rule

Oxygen Customizer is useful as an open-source reference for structure, release channels, LSPosed/root flow, and customer warning style. Do not copy GPL-3.0 source code into this project unless the project license and distribution method are GPL-compatible.
