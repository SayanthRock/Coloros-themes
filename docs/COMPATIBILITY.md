# Compatibility Guide

This project targets OPPO, OnePlus, and realme devices using ColorOS, OxygenOS, or realme UI.

## Android versions

| Android version | Status | Notes |
|---|---|---|
| Android 15 | Supported target | Needs testing across each brand skin |
| Android 16 | Supported target | Expect stronger storage and system restrictions |
| Android 17 | Experimental target | Test before customer release |

## Brand support

| Brand | Expected behavior |
|---|---|
| OPPO | Best target for ColorOS theme testing |
| realme | Similar path expectations, but realme UI changes by region |
| OnePlus | OxygenOS/ColorOS base differs by device and region |

## Customer test checklist

Ask every tester to send:

- Device brand
- Device model
- Android version
- ColorOS/OxygenOS/realme UI version
- Root solution: Magisk, KernelSU, APatch, or none
- Boot success after install
- Theme folder detected or not
- Screenshots of lock screen, home screen, wallpaper, and About phone

## Safe compatibility rule

Do not promise full theme support before testing on that exact model and firmware. OEM theme paths can change between updates, regions, and Android versions.
