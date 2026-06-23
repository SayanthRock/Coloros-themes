# Lag Fix Guide

This guide keeps the module and customer theme smooth.

## Module lag fixes

- Keep boot scripts small
- Do not run background loops
- Do not scan large folders on boot
- Do not copy large files on every boot
- Do not use heavy animation files inside the module
- Keep preview images compressed
- Keep wallpapers optimized for mobile

## Theme asset rules

| Asset | Recommended |
|---|---|
| Wallpaper | JPG or WebP, mobile optimized |
| Preview image | WebP or compressed PNG |
| Icon preview | PNG/WebP under reasonable size |
| Sound | Short OGG files |
| Documentation | Small markdown files |

## Customer lag checklist

Ask the customer to check:

- Free storage space
- Battery saver mode
- Animation scale settings
- Recently installed heavy apps
- Phone temperature
- Theme preview image size
- Whether lag started after module install
- Whether lag remains after module removal

## Release rule

If a device becomes slow after install, mark that device result as Partial or Needs retest until the cause is known.
