# Themes UI Design 2026

This guide defines the customer-facing UI style for the ColorOS Customizer APK.

## Design direction

Use a modern Android 2026 style:

- Dark-theme friendly.
- Rounded cards.
- Clean spacing.
- Strong typography hierarchy.
- Liquid glass surfaces only where useful.
- Clear status chips for every feature.
- No hidden root risk.
- No fake performance promises.

## Main navigation

```text
ColorOS Customizer
├── Home
├── Themes
├── Wallpapers
├── Rootd
├── Permissions
├── Compatibility
├── Backup / Restore
├── Logs
└── About
```

## Home screen layout

```text
[Hero Card]
ColorOS Themes Rock
OPPO, OnePlus, realme customization
Status: Device check required

[Quick Actions]
- Scan theme package
- Set wallpaper
- Check root status
- Check LSPosed scope
- Open rollback guide

[Feature Sections]
- Free tools
- Root tools
- LSPosed tools
- Experimental tools
```

## Theme screen

| Section | Purpose |
|---|---|
| Theme scanner | Inspect `.theme` or ZIP packages before import. |
| Theme catalog | Show only owned or permitted assets. |
| Preview | Show wallpaper, icon, font, and lock-screen previews. |
| Compatibility | Show OPPO, OnePlus, realme, Android 15, Android 16, Android 17 labels. |
| Risk label | Safe, Limited, Needs testing, Not available. |

## Rootd screen

The Rootd screen must be status-first, not action-first.

```text
Rootd
├── Root manager: Not checked / Available / Not available
├── Module: Installed / Disabled / Missing
├── LSPosed: Active / Inactive / Not required
├── Scope: Ready / Missing package scope
├── Safe mode: Off / On
└── Support report: Export
```

## Permission screen

Use Android-style grouped permission cards:

```text
Permissions
├── Notifications
├── Photos and videos
├── Music and audio
├── Storage
├── Root access
└── LSPosed scope
```

Each permission card must show:

- Status.
- Why it is needed.
- Safety level.
- Action button.

## Status chip colors

| Chip | Meaning |
|---|---|
| Working | Tested and available. |
| Limited | Works only on selected builds. |
| Needs testing | Not ready for normal customers. |
| Not available | Unsupported or blocked. |
| Root required | Needs root manager approval. |
| LSPosed scope required | Needs target package scope. |

## Visual style tokens

```text
Corner radius: 20dp to 28dp
Card spacing: 16dp
Page padding: 20dp
Primary accent: Desert Sand #E2B884
Background: near-black or dynamic Material background
Surface: frosted dark glass with low opacity
Text: high contrast, simple hierarchy
Animation: short, smooth, only for state change
```

## Screens that should be built first

1. Home.
2. Permissions.
3. Rootd.
4. Theme Scanner.
5. Compatibility.
6. Backup / Restore.
7. Logs.
8. About.

## Customer message

Use this text in the APK:

> Free customization tools stay open. Advanced features show clear labels so you know what works, what needs testing, and what is not available on your device.
