# Customer Support Guide

Use this guide when helping customers install or test ColorOS Themes Rock.

## Before install

Tell the customer:

1. This is a theme helper module, not a guaranteed universal OEM theme unlocker.
2. They must use their own device at their own risk.
3. They should know how to disable the module if a problem happens.
4. They should keep a backup of important files.
5. They should install only the latest public GitHub Release.

## Basic install steps

1. Download the latest ZIP from the GitHub **Releases** page.
2. Verify the SHA256 checksum when available.
3. Install the module ZIP with the supported module manager.
4. Reboot.
5. Check lock screen, home screen, wallpaper, icon, and theme behavior.
6. Report problems with device details and screenshots.

## New update improvement plan

New updates should focus on measurable fixes, safer installs, better compatibility, and clearer rollback steps. Do not promise that every device problem is solved automatically. Each OPPO, OnePlus, and realme firmware must be tested separately.

## Support message template

```text
Device brand:
Device model:
Android version:
ColorOS/OxygenOS/realme UI version:
Module manager:
Module version:
Problem:
Screenshot/video:
Did the phone boot normally after install:
Theme path used:
Wallpaper/icon/lockscreen affected:
```

## Common problems

| Problem | Action |
|---|---|
| Phone boots but theme not applied | Check if the device accepts files from the themeInner path |
| Theme visible but incomplete | Replace placeholder assets with a complete theme package |
| Wallpaper not applied | Check wallpaper folder structure and image format |
| Icons not applied | Check icons folder structure and launcher/theme support |
| Lock screen not applied | Check lockscreen assets and firmware compatibility |
| Boot issue | Disable the module and restore stock behavior |
| Customer expects speed boost | Explain that safe performance work means cleanup guidance, diagnostics, and settings, not fake speed claims |

## Support rule

Do not sell this as a guaranteed fix for every OPPO, OnePlus, or realme phone. Sell it as tested per device model and firmware.
