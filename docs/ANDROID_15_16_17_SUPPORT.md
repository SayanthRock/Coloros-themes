# Android 15, Android 16, and Android 17 Support

ColorOS Themes Rock uses a status-first compatibility model for OPPO, OnePlus, and realme devices.

## Runtime support labels

| Android version | SDK | Project status | Customer label |
|---|---:|---|---|
| Android 15 | 35 | Supported modern target | Supported target |
| Android 16 | 36 | Forward-compatible runtime support | Needs device test |
| Android 17 | 37 | Preview/future-safe runtime support | Needs device test |
| Newer Android | 38+ | Safe fallback until verified | Safe mode |

The helper APK reads `Build.VERSION.SDK_INT` at runtime, so it can label Android 15, Android 16, Android 17, and newer devices while keeping the build configuration conservative.

## Safe by default

- Wallpaper selection and wallpaper application through Android public APIs.
- Device report generation.
- Theme/module status labels.
- Settings shortcuts.
- Support report sharing.
- Safe-disable and rollback guidance.

## Requires real device testing

- ColorOS, realme UI, or OxygenOS theme package behavior.
- Lock screen theme behavior.
- Launcher icon replacement behavior.
- Status bar, quick settings, notification, and Settings overlay behavior.
- Overlay package targets for framework, System UI, or Settings.

## Build policy

Do not mark Android 16 or Android 17 features as `Working` until the APK and module ZIP are tested on the exact ROM and device model.
