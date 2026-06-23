# ColorOS Customizer APK

Safe customer helper APK for OPPO, OnePlus, and realme theme, wallpaper, and battery support.

## Current version

`0.3.0`

## What changed

- Added Customization Center.
- Added open image picker option.
- Added selected image preview.
- Added Home screen wallpaper button.
- Added Lock screen wallpaper button.
- Added Home + Lock wallpaper button.
- Added About phone helper label.
- Added custom display name setting with `Sayanth Rock` default.
- Added on/off switches for custom name and image background preview.
- Added full support report copy.
- Kept battery status and settings shortcuts.
- Updated GitHub Actions APK artifact name to `ColorOS-Customizer`.

## Safe approach

The APK uses public Android image picking and wallpaper APIs. Custom text values are stored as helper settings and shown in reports first.

## Build APK locally

```bash
cd lsposed-helper
gradle :app:assembleDebug --no-daemon --stacktrace
```

Output:

```text
lsposed-helper/app/build/outputs/apk/debug/app-debug.apk
```

## Build APK on GitHub

Open **Actions > Build ColorOS Customizer APK > Run workflow**.

The workflow uploads the APK as a GitHub Actions artifact. Enable release publishing during manual workflow runs when you want the APK attached to a public release.
