# ColorOS Themes Rock

Customer-safe theme module and helper APK starter for **OPPO, OnePlus, and realme** devices running ColorOS, OxygenOS, or realme UI on Android 15, Android 16, and Android 17.

This repo is built for legal theme customization, wallpaper packs, device compatibility checks, safe battery guidance, and support reports. It does **not** convert protected Theme Store assets or make fake performance claims.

## What this project does

- Provides a Magisk/KernelSU/APatch-style module structure.
- Adds a systemless overlay target for `/system_ext/media/themeInner/`.
- Gives a clean place to package your own lock screen, home screen, wallpaper, and UI assets.
- Adds install-time device checks for OPPO, OnePlus, and realme.
- Adds a GitHub Actions workflow to build a flashable module ZIP.
- Adds a helper APK for battery status, settings shortcuts, and customer support reports.
- Adds a GitHub Actions workflow to build and upload the helper APK.
- Publishes public GitHub Releases from tags or manual workflow runs.

## Supported targets

| Brand | Android skin | Status |
|---|---|---|
| OPPO | ColorOS | Starter support |
| OnePlus | OxygenOS / ColorOS based builds | Starter support |
| realme | realme UI | Starter support |
| Android 15 | Modern supported target | Planned testing |
| Android 16 | Modern supported target | Planned testing |
| Android 17 | Latest target | Needs device-by-device testing |

## Included battery helper APK

The APK is in:

```text
lsposed-helper/
```

APK features:

- Device status dashboard
- Battery optimization status message
- OPPO, OnePlus, realme, and OPlus-family detection
- Public Android battery settings shortcuts
- Customer support report copy button
- Safe LSPosed entry class

## What it cannot safely do

- It cannot silently change lock screen or home screen on non-root phones.
- It cannot guarantee every OEM Theme Store package will accept external theme files.
- It cannot convert paid or protected Theme Store assets.
- It cannot honestly promise magical performance boosts.
- It should not spoof About phone identity unless the user fully understands root/system risks.

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
dist/ColorOS-Themes-Rock-v0.2.0.zip
```

## Build helper APK

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
2. Select **Build Battery Helper APK**.
3. Tap **Run workflow**.
4. Use version `v0.2.0` or newer.
5. Enable release publishing only when you want the APK uploaded to a public GitHub Release.

## Public releases

Public GitHub Releases are automated through:

```text
.github/workflows/build-module.yml
.github/workflows/build-helper-apk.yml
```

### Release from a tag

```bash
git tag v0.2.0
git push origin v0.2.0
```

### Release from GitHub Actions

1. Open **Actions**.
2. Select the build workflow.
3. Tap **Run workflow**.
4. Enter a version like `v0.2.0`.
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
3. Open **ColorOS Battery Helper**.
4. Review the battery status and device report.
5. Use the settings shortcuts only when needed.

## Customer-safe feature plan

| Feature | Safe approach |
|---|---|
| Lock screen themes | Own assets only, root module or OEM-supported theme import |
| Home screen style | Wallpapers, icon packs, launcher-safe instructions |
| Wallpapers | Companion app using Android `WallpaperManager` later |
| About phone | Device info dashboard first, no unsafe spoofing by default |
| Battery | Diagnostics, settings shortcuts, support report, no fake booster claims |
| Customer support | Device report template, compatibility table, rollback steps |

## Legal and safety rule

Only distribute themes, wallpapers, fonts, icons, sounds, and UI files that you created, own, or have permission to redistribute. Keep the project clean so customers can trust it.
