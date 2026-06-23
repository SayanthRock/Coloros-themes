# Battery Guide

The helper APK now includes a simple customer dashboard for OPPO, OnePlus, and realme devices.

## Features

- Device report
- Battery status message
- Settings shortcuts
- Support report copy button
- Safe LSPosed entry class

## Use

1. Build or download the APK.
2. Open **ColorOS Battery Helper**.
3. Review the status card.
4. Open the battery settings buttons when needed.
5. Copy the support report for troubleshooting.

## Build

```bash
cd lsposed-helper
gradle :app:assembleDebug --no-daemon --stacktrace
```

GitHub Actions workflow:

```text
.github/workflows/build-helper-apk.yml
```
