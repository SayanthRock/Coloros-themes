# Compatibility Matrix

This document defines what can be shown as working, limited, untested, or unavailable inside the ColorOS Themes Rock module and APK.

## Supported device families

| Device family | Android skin | Support level | Notes |
|---|---|---|---|
| OPPO | ColorOS 15 | Planned testing | Test wallpaper, theme package, rollback, and boot safety before marking as Working. |
| OPPO | ColorOS 16 | Planned testing | Keep SystemUI and launcher hooks behind clear device checks. |
| OnePlus | OxygenOS 15 | Planned testing | OxygenOS builds can differ by region, so device reports are required. |
| OnePlus | OxygenOS 16 | Planned testing | Mark advanced hooks as Needs testing until real device verification is complete. |
| realme | realme UI 15 | Planned testing | Test launcher, lock screen, wallpaper, icons, and rollback separately. |
| Android 17 builds | ColorOS / OxygenOS / realme UI | Experimental | Do not mark as customer-ready until the target device is tested. |

## Feature status labels

Use these labels everywhere in the APK UI, README, release notes, and customer guides.

| Label | Meaning | Customer message |
|---|---|---|
| Working | Tested on a real device and confirmed safe for normal use. | Safe to use on listed devices. |
| Limited | Works only on specific ROMs, Android versions, or device models. | Read compatibility notes before enabling. |
| Needs testing | Built but not verified on enough devices. | Test carefully and keep rollback ready. |
| Not available | The device, ROM, or Android version does not support this feature. | Do not force-enable. |
| Root required | Requires Magisk, KernelSU, APatch, or equivalent root access. | Root risk warning must be visible. |
| LSPosed scope required | Requires LSPosed/Xposed scope for a target package. | Scope status must be shown before enabling. |

## Minimum customer-safe rule

A feature must not be marked as **Working** until all checks pass:

1. Root manager status is detected or the feature does not need root.
2. LSPosed/Xposed status is detected when hooks are needed.
3. Target package scope is confirmed.
4. A safe rollback path exists.
5. The feature has been tested on at least one real matching device.
6. The UI explains the device and ROM limitation clearly.

## Recommended module groups

```text
modules/
├── coloros_15/
├── coloros_16/
├── oxygenos_15/
├── oxygenos_16/
├── realmeui_15/
└── experimental_android_17/
```

Each module group should keep its own notes for target packages, tested devices, disabled features, and rollback steps.

## Do not copy GPL code blindly

Oxygen Customizer can be used as a public reference for architecture, safety messaging, release channels, and README structure. Do not copy GPL-3.0 source code into this project unless the project license and distribution method are GPL-compatible.
