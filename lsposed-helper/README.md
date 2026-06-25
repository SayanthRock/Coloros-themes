# ColorOS Customizer APK

Safe customer helper APK for OPPO, OnePlus, and realme theme, wallpaper, permission, root-status, LSPosed-status, and support guidance.

## Current version

`0.5.4`

## What changed

- Aligned APK version with the module release version.
- Kept polished liquid-glass UI update.
- Kept status bar blur increase and decrease controls.
- Kept fallback mode for phones without native blur support.
- Kept blur visibility switch and blur amount display.
- Kept Customization Center.
- Kept open image picker option.
- Kept selected image preview.
- Kept Home screen wallpaper button.
- Kept Lock screen wallpaper button.
- Kept Home + Lock wallpaper button.
- Kept About phone helper label.
- Kept custom display name setting with `Sayanth Rock` default.
- Kept on/off switches for custom name and image background preview.
- Kept full support report copy.
- Kept battery status and settings shortcuts.
- Kept GitHub Actions APK artifact name as `ColorOS-Customizer`.

## Safe approach

The APK uses public Android image picking and wallpaper APIs. Custom text values are stored as helper settings and shown in reports first. Theme Store behavior remains customer-safe.

Root, LSPosed, and overlay features must remain status-first: detect support, show clear labels, and avoid hidden or risky system changes until tested on the exact device and ROM.

## Build APK locally

```bash
cd lsposed-helper
gradle :app:testDebugUnitTest :app:assembleDebug --no-daemon --stacktrace
```

Output:

```text
lsposed-helper/app/build/outputs/apk/debug/app-debug.apk
```

## Build APK on GitHub

Open **Actions > Build ColorOS Customizer APK > Run workflow**.

The workflow uploads the APK as a GitHub Actions artifact. Enable release publishing during manual workflow runs when you want the APK attached to a public release.
