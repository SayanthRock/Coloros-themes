# Rootd System Structure

Rootd is the safe systemless customization area for ColorOS Themes Rock.

## Purpose

Use this folder to plan and organize verified customization assets for OPPO, OnePlus, and realme devices.

## Structure

```text
rootd/
├── rootd-system-map.json
├── system_ext/media/themeInner/
├── overlays/
├── customer-overrides/
└── reports/
```

## Working-only rule

Only keep what works.

- Wallpaper tools can stay enabled.
- Preview tools can stay enabled.
- Theme scanner can stay enabled.
- Root status display can stay enabled.
- LSPosed status display can stay enabled.
- Advanced system options must stay disabled until tested.
- Unknown bundle imports must stay blocked.

## Rootd apply order

```text
Detect device
Detect Android version
Detect ROM skin
Detect root status
Detect LSPosed and scope if needed
Scan asset
Show compatibility label
Preview safe asset
Apply only supported action
Keep rollback visible
```

## Uploaded rock theme ZIP

The uploaded ZIP is treated as an inspection sample. It can help design Rootd options, but only verified and permitted customization assets should be used later.
