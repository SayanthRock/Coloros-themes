# ColorOS Themes Rock

Customer-safe theme module and **ColorOS Customizer APK** starter for **OPPO, OnePlus, and realme** devices running ColorOS, OxygenOS, or realme UI on Android 15, Android 16, and Android 17.

This repo is built for legal theme customization, wallpaper packs, device compatibility checks, safe battery guidance, image-based customization, and support reports. It does **not** convert protected Theme Store assets or make fake performance claims.

## What this project does

- Provides a Magisk/KernelSU/APatch-style module structure.
- Adds a systemless overlay target for `/system_ext/media/themeInner/`.
- Gives a clean place to package your own lock screen, home screen, wallpaper, and UI assets.
- Adds install-time device checks for OPPO, OnePlus, and realme.
- Adds a GitHub Actions workflow to build a flashable module ZIP.
- Adds a ColorOS Customizer APK for wallpaper, lock screen, home screen, battery, and support options.
- Adds an open image picker so customers can choose any image they like.
- Adds local About phone helper label and OTA-style name/background switches.
- Adds a GitHub Actions workflow to build and upload the APK.
- Publishes public GitHub Releases from tags or manual workflow runs.
- Includes refreshed workflow automation for the latest module and APK release flow.

## Supported targets

| Brand | Android skin | Status |
|---|---|---|
| OPPO | ColorOS | Starter support |
| OnePlus | OxygenOS / ColorOS based builds | Starter support |
| realme | realme UI | Starter support |
| Android 15 | Modern supported target | Planned testing |
| Android 16 | Modern supported target | Planned testing |
| Android 17 | Latest target | Needs device-by-device testing |

## Included ColorOS Customizer APK

The APK is in:

```text
lsposed-helper/
```

APK features:

- Device status dashboard
- Battery optimization status message
- Open image picker option
- Selected image preview
- Apply image to Home screen wallpaper
- Apply image to Lock screen wallpaper
- Apply image to Home + Lock screen wallpaper
- About phone helper label
- OTA display name option with `Sayanth Rock` default
- OTA name on/off switch
- OTA background on/off switch using the selected image
- Customer support report copy button
- Safe LSPosed entry class

## What it cannot safely do

- It cannot silently change lock screen or home screen on non-root phones without user action.
- It cannot guarantee every OEM Theme Store package will accept external theme files.
- It cannot convert paid or protected Theme Store assets.
- It cannot honestly promise magical performance boosts.
- It does not directly spoof system identity by default.

## Folder structure

```text
.
├── module.prop
├── customize.sh
├── post-fs-data.sh
├── service.sh
├── uninstall.sh
├── system_ext/media/themeInner/
├── themes/sample/theme.json
├── docs/
│   ├── BATTERY_GUIDE.md
│   ├── CUSTOMIZATION_CENTER.md
│   ├── COMPATIBILITY.md
│   ├── CUSTOMER_SUPPORT.md
│   └── ROADMAP.md
├── lsposed-helper/
└── .github/workflows/
    ├── build-module.yml
    └── build-helper-apk.yml
```

## Build module ZIP

The module ZIP workflow builds automatically on every push to `main`.

Manual local build:

```bash
bash scripts/package.sh
```

Output:

```text
dist/ColorOS-Themes-Rock-v0.3.0.zip
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

GitHub build:

1. Open **Actions**.
2. Select **Build ColorOS Customizer APK**.
3. Tap **Run workflow**.
4. Use version `v0.3.0` or newer.
5. Enable release publishing only when you want the APK uploaded to a public GitHub Release.

## Public releases

Public GitHub Releases are automated through:

```text
.github/workflows/build-module.yml
.github/workflows/build-helper-apk.yml
```

### Release from a tag

```bash
git tag v0.3.0
git push origin v0.3.0
```

### Release from GitHub Actions

1. Open **Actions**.
2. Select the build workflow.
3. Tap **Run workflow**.
4. Enter a version like `v0.3.0`.
5. Enable public release publishing.
6. Run the workflow.

## Install module ZIP

1. Open the latest GitHub Release.
2. Download the module ZIP.
3. Open Magisk, KernelSU, or APatch.
4. Flash the module ZIP.
5. Reboot.
6. Test on one device first before giving it to customers.

## Install helper APK

1. Download the APK artifact or release APK.
2. Install it on the test phone.
3. Open **ColorOS Customizer**.
4. Tap **Open image picker** and choose any image.
5. Apply it to Home screen, Lock screen, or both.
6. Configure About phone helper label and OTA-style options.
7. Copy the support report if testing or reporting problems.

## Customer-safe feature plan

| Feature | Safe approach |
|---|---|
| Lock screen | User-selected wallpaper through Android public API |
| Home screen | User-selected wallpaper through Android public API |
| Wallpapers | Open image picker and selected image preview |
| About phone | Helper label and support report first |
| OTA name | Local `Sayanth Rock` display option with on/off switch |
| OTA background | Local selected-image background option with on/off switch |
| Battery | Diagnostics, settings shortcuts, support report, no fake booster claims |
| Customer support | Device report template, compatibility table, rollback steps |

## Legal and safety rule

Only distribute themes, wallpapers, fonts, icons, sounds, and UI files that you created, own, or have permission to redistribute. Keep the project clean so customers can trust it.
