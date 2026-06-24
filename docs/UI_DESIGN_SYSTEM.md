# UI Design System

Use this design system for the default theme, customer helper app, screenshots, and future website.

## Design direction

ColorOS Themes Rock uses a **working-only customer UI**. The app should be simple, fast, safe, and clear. Tools that work should stay visible. Problematic, unsupported, risky, or untested tools should be hidden or clearly marked as unavailable.

## Visual style

- Dark customer dashboard
- Clean rounded cards
- Soft translucent surfaces only when they do not slow startup
- Desert Sand accent
- Clear spacing
- Strong title hierarchy
- Status badges for every feature
- Mobile-first layout
- Fast startup screens
- Minimal motion
- Clear working-only messaging

## Working-only rule

The customer app should support a clear working-only mode:

- Keep only tested or safe tools visible by default
- Hide problematic actions
- Do not show fake premium locks
- Do not show demo-only options
- Do not show unsupported root actions as working
- Do not show unsupported LSPosed actions as working
- Do not claim guaranteed lag fix or battery boost
- Every feature must show its real support status

## Colors

| Token | Value | Usage |
|---|---|---|
| Background | `#0f0f10` | Main dark background |
| Background elevated | `#151517` | Top bars and navigation dock |
| Card surface | `#1a1a1d` | Card base |
| Card surface soft | `#242429` | Elevated cards |
| Card border | `#4dffffff` | Soft card outline |
| Accent | `#e2b884` | Buttons, active states, highlights |
| Accent soft | `#f0d2a8` | Subtle highlight |
| Text primary | `#f5f2ea` | Main text |
| Text muted | `#b9b1a3` | Notes and descriptions |
| Warning | `#ffcc66` | Needs testing or warning labels |
| Success | `#8fd694` | Working or safe labels |
| Danger | `#ff7a7a` | Unsupported or high-risk labels |

## Shape and spacing

| Element | Rule |
|---|---|
| Screen padding | 18dp |
| Card radius | 22dp to 26dp |
| Primary button radius | 22dp |
| Chip radius | Full pill shape |
| Card padding | 18dp |
| Section gap | 16dp to 24dp |
| Bottom navigation height | 90dp |

## Navigation model

The app uses simple customer pages:

1. Home
2. Themes
3. Performance
4. Support
5. More

Navigation rules:

- Keep page changes lightweight.
- Keep the bottom navigation visible.
- Keep customer actions one tap away.
- Use swipe only if it remains stable and does not conflict with scrolling.

## Layer customization model

All layer customization should be organized clearly:

| Layer | Purpose | Default status |
|---|---|---|
| Wallpaper Layer | Home and lock wallpaper actions | Working |
| Theme Scanner Layer | Inspect safe theme packages before import | Working |
| Icon Layer | Launcher-supported icon options | Needs testing |
| Lock Layer | Lock screen guidance and supported actions | Needs testing |
| Status Layer | Feature badges and support labels | Working |
| Support Layer | Report, backup, restore, rollback help | Working |

## Customer screen order

1. Home dashboard
2. Themes
3. Performance Level
4. Support
5. More

## Feature status badges

Every feature must show one badge:

| Badge | Meaning |
|---|---|
| Working | Tested or safe to use |
| Safe guidance | Opens Android settings or shows instructions only |
| Limited | Device-specific or ROM-specific |
| Needs testing | Built but not ready for normal customers |
| Needs permission | Requires extra user approval |
| Root required | Needs root manager approval |
| LSPosed scope required | Needs target app scope |
| Not available | Hide apply button and show explanation |

## Performance rules

- Keep startup screen lightweight.
- Avoid heavy live blur.
- Use simple rounded cards and subtle borders.
- Avoid long animations.
- Keep card text short.
- Keep customer actions one tap away.
- Use Balanced as the default Performance Level.
- Mark Performance and Custom as Needs testing until verified on real devices.

## System improvement rule

APK system improvement must be status-first:

1. Show device status.
2. Show root status.
3. Show LSPosed status.
4. Show scope status.
5. Show what works.
6. Hide what is problematic.
7. Keep rollback visible.
8. Export a support report when a feature fails.

## Clean model summary

```text
Working-only dashboard
Fast startup
Rounded cards
Clear feature labels
Safe Android settings shortcuts
Theme scanner before import
Backup and rollback visible
No unsupported features shown as working
No fake booster claims
```
