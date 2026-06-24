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
├── Performance Level
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
- Choose performance level
- Open rollback guide

[Feature Sections]
- Free tools
- Root tools
- LSPosed tools
- Performance tools
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

## Performance Level screen

Performance Level should use safe presets and clear labels.

```text
Performance Level
├── Current level: Balanced
├── Device profile: OPPO / OnePlus / realme
├── Root status: Available / Not available
├── LSPosed status: Active / Not required
├── Presets
│   ├── Off
│   ├── Battery Saver
│   ├── Balanced
│   ├── Smooth
│   ├── Performance
│   └── Custom
├── Safety label
├── What will change
├── Apply
└── Reset to Balanced
```

Recommended first release behavior:

- Off, Balanced, Battery Saver guidance, and Smooth guidance can be shown to normal customers.
- Performance and Custom must stay marked as Needs testing until real-device verification is complete.
- Root-only controls must show root, module, scope, and rollback checks first.

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
4. Performance Level.
5. Theme Scanner.
6. Compatibility.
7. Backup / Restore.
8. Logs.
9. About.

## Customer message

Use this text in the APK:

> Free customization tools stay open. Advanced features show clear labels so you know what works, what needs testing, and what is not available on your device.
