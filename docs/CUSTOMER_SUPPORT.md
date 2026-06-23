# Customer Support Guide

Use this guide when helping customers install or test ColorOS Themes Rock.

## Before install

Tell the customer:

1. This is a theme helper module, not a guaranteed universal OEM theme unlocker.
2. They must use their own device at their own risk.
3. They should know how to disable modules from recovery or root manager.
4. They should keep a backup of important files.

## Basic install steps

1. Download the latest ZIP from GitHub Actions artifacts.
2. Open Magisk, KernelSU, or APatch.
3. Install the module ZIP.
4. Reboot.
5. Check lock screen, home screen, wallpaper, and theme behavior.

## Support message template

```text
Device brand:
Device model:
Android version:
ColorOS/OxygenOS/realme UI version:
Root tool:
Module version:
Problem:
Screenshot/video:
Did the phone boot normally after install:
```

## Common problems

| Problem | Action |
|---|---|
| Phone boots but theme not applied | Check if the device accepts files from the themeInner path |
| Theme visible but incomplete | Replace placeholder assets with a complete theme package |
| Boot issue | Disable the module from recovery/root manager |
| Customer expects speed boost | Explain that safe performance work means cleanup guidance, diagnostics, and settings, not fake speed claims |

## Support rule

Do not sell this as a guaranteed fix for every OPPO, OnePlus, or realme phone. Sell it as tested per device model and firmware.
