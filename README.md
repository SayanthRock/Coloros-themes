# ColorOS Themes Rock

Customer-safe theme module and **ColorOS Customizer APK** starter for **OPPO, OnePlus, and realme** devices running ColorOS, OxygenOS, or realme UI.

This project is for legal customization using assets that you created, own, or have permission to share.

## What this project builds

- A flashable Magisk / KernelSU / APatch-style theme module ZIP.
- A safe theme inspection pipeline for ColorOS/OPlus/realme `.theme` files.
- A systemless overlay target for `/system_ext/media/themeInner/`.
- A clean customization structure for wallpapers, icons, fonts, sounds, previews, customer support, and rollback notes.
- A ColorOS Customizer helper APK for customer-facing wallpaper, device report, support, and safe toggles.
- GitHub Actions workflows for APK builds, module ZIP builds, artifacts, checksums, provenance, and optional GitHub Releases.

## Supported targets

| Brand | Android skin | Status |
|---|---|---|
| OPPO | ColorOS | Starter support |
| OnePlus | OxygenOS / ColorOS based builds | Starter support |
| realme | realme UI | Starter support |
| Android 15 | Modern supported target | Planned testing |
| Android 16 | Modern supported target | Planned testing |
| Android 17 | Latest target | Device-by-device testing required |

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
├── customer-options/options.json
├── scripts/
│   ├── package.sh
│   ├── validate-module.sh
│   ├── check-theme-size.sh
│   └── inspect-theme-package.py
├── docs/
│   ├── THEME_MODULE_BUILDER.md
│   ├── DEFAULT_THEME_CUSTOMER_GUIDE.md
│   ├── UI_DESIGN_SYSTEM.md
│   ├── LAG_FIX_GUIDE.md
│   └── PROBLEM_SOLVER_MATRIX.md
├── lsposed-helper/
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
dist/ColorOS-Themes-Rock-v0.4.0.zip
```

## Build from GitHub Actions

1. Open **Actions**.
2. Select **Build Theme Module**.
3. Tap **Run workflow**.
4. Enter a version like `v0.4.0`.
5. Keep release publishing enabled to upload the module ZIP to a public GitHub Release.

The workflow also runs on pushes that change module, theme, script, or workflow files.

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
| Battery/performance | Diagnostics, shortcuts, lag-fix guide | Safe, no fake booster claims |
| Rollback | Uninstall script and customer guide | Documented |

## Install module ZIP

1. Download the latest module ZIP artifact or GitHub Release asset.
2. Open Magisk, KernelSU, or APatch.
3. Flash the module ZIP.
4. Reboot.
5. Test on one device before sharing with customers.

## Safety rule

Only distribute themes, wallpapers, fonts, icons, sounds, previews, and UI files that you created, own, or have permission to share.
