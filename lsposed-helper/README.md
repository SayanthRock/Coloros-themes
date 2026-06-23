# ColorOS Battery Helper APK

Safe customer helper APK for OPPO, OnePlus, and realme theme and battery support.

## Current version

`0.2.0`

## What changed

- Added Android dashboard source code.
- Added OPPO, OnePlus, and realme device detection.
- Added battery optimization status checking.
- Added shortcuts to public Android battery settings pages.
- Added support report copy button for troubleshooting.
- Added LSPosed entry class.
- Added GitHub Actions APK build and artifact upload.

## Battery approach

This APK uses public Android settings and clear guidance. It is designed to be reversible and customer-safe.

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

Open **Actions > Build Battery Helper APK > Run workflow**.

The workflow uploads the APK as a GitHub Actions artifact. Enable release publishing during manual workflow runs when you want the APK attached to a public release.
