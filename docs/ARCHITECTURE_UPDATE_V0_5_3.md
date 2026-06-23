# Architecture Update v0.5.3

This update moves ColorOS Themes Rock toward a safer Hook and Overlay architecture.

## Main changes

- Device profile detection added during install.
- Safe-disable flag support added.
- Early boot config directory preparation added.
- Late service now exits when safe-disable is active.
- Customer options now declare hook-overlay-safe architecture.
- Package strategy is documented for safe customer support.
- Module version bumped to v0.5.3.

## Safe mode

If a file named `disable` exists inside the installed module directory, the late service exits without applying options.

## Device profile

The installer writes a device profile to:

```text
config/device-profile.conf
```

The profile stores brand, model, device name, Android release, SDK version, OPlus build version, and customer support status.

## Package strategy

| Area | Strategy |
|---|---|
| System UI | UI-only after device testing |
| Theme app | Owned assets and customer guidance |
| OTA app | No update integrity changes |
| Battery app | Guidance and labels only |
| Wallpaper picker | Standard wallpaper APIs and owned assets |

## Customer rule

Unsupported devices must default to `needs_device_test` until tested.
