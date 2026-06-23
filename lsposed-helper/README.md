# ColorOS Themes LSPosed Helper

Experimental customer helper APK for OPPO, OnePlus, and realme theme testing.

## Current version

`0.1.0`

## What it does now

- Opens a simple customer dashboard
- Shows device information
- Opens safe Android settings screens
- Includes LSPosed module metadata
- Includes a safe hook entry that logs only when safe hooks are enabled at build time

## What it does not do yet

- It does not force system theme changes
- It does not modify third-party apps
- It does not apply paid/protected theme files
- It does not promise universal Android 15/16/17 support

## Build APK

From this folder:

```bash
gradle :app:assembleDebug
```

Output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

GitHub Actions can build it automatically using `.github/workflows/build-lsposed-apk.yml`.
