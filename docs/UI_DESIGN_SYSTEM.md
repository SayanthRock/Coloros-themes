# UI Design System

Use this design system for the default theme, customer helper app, screenshots, and future website.

## Design direction

ColorOS Themes Rock uses a **free-first premium utility UI**. The app should feel modern, clean, fast, and helpful. Important tools should be easy to find, clearly labeled, and never hidden behind confusing paid screens.

## Visual style

- Premium dark UI
- Soft glass cards
- Desert Sand accent
- Rounded modern panels
- Clean spacing
- Strong title hierarchy
- Status badges for every feature
- Mobile-first layout
- Fast loading screens
- Minimal motion
- Clear free-mode messaging

## Free-first rule

The customer app should support a clear free mode:

- No forced payment screen
- No locked premium wording
- No confusing trial labels
- No fake booster claims
- Optional support message only if added later
- Every feature must show its real support status

## Colors

| Token | Value | Usage |
|---|---|---|
| Background | `#0f0f10` | Main dark background |
| Background elevated | `#151517` | Top bars and large containers |
| Surface | `#1a1a1d` | Cards and panels |
| Surface soft | `#242429` | Secondary cards |
| Accent | `#e2b884` | Buttons, active states, highlights |
| Accent soft | `#f0d2a8` | Subtle highlight and icon glow |
| Text primary | `#f5f2ea` | Main text |
| Text muted | `#b9b1a3` | Notes and descriptions |
| Warning | `#ffcc66` | Needs test or warning labels |
| Success | `#8fd694` | Safe or supported labels |
| Danger | `#ff7a7a` | Unsupported or high-risk labels |

## Shape and spacing

| Element | Rule |
|---|---|
| Screen padding | 16dp minimum |
| Card radius | 20dp to 28dp |
| Primary button radius | 18dp to 24dp |
| Chip radius | Full pill shape |
| Card padding | 16dp to 20dp |
| Section gap | 20dp to 28dp |
| Icon size | 22dp to 28dp |

## Typography

| Style | Usage |
|---|---|
| Display title | App hero title and dashboard heading |
| Section title | Feature groups |
| Body | Description text and guidance |
| Caption | Notes, build info, and device support text |
| Badge | Status labels |

## Customer screen order

1. Dashboard
2. Free mode
3. Default theme
4. Wallpaper setup
5. Lock screen setup
6. Home screen setup
7. Icon pack setup
8. Display controls
9. Animation controls
10. Battery and performance help
11. Backup and restore
12. Device support status
13. Lag fix checklist
14. Support report
15. Safe disable and rollback help

## Dashboard cards

| Card | Purpose |
|---|---|
| Free Mode | Shows that the app is free-first and not paywalled |
| Theme Setup | Default theme, import theme, and apply guidance |
| Wallpaper | Home and lock wallpaper actions |
| Performance | Display, animation, and lag checklist |
| Safety | Backup, restore, rollback, and disable guidance |
| Device Status | Brand, model, Android version, and support label |

## Feature status badges

Every feature must show one badge:

| Badge | Meaning |
|---|---|
| Safe | Can be used normally |
| Needs test | Depends on phone model or software version |
| Needs permission | Requires an extra user-approved permission |
| Experimental | May behave differently by device |
| Not supported | Hide apply button and show explanation |

## Interaction rules

- Put the safest action first.
- Show warnings before advanced options.
- Keep reset actions away from normal actions.
- Use confirm dialogs for clear, restore, reset, disable, and reboot actions.
- Always show a result message after applying a change.
- Show a support report button when a feature fails.

## Loading and empty states

- Use skeleton cards for loading.
- Use clear empty-state messages.
- Never show a blank screen.
- Show a support report button when a feature fails.

## Motion rule

Animations should feel premium, but never make the app slower. Use short fade or slide transitions only.

| Motion | Duration |
|---|---|
| Card fade in | 120ms to 180ms |
| Sheet open | 180ms to 220ms |
| Button press | 80ms to 120ms |
| Warning dialog | 160ms to 220ms |

Avoid heavy blur and long animations on low-end phones.

## New model style summary

```text
Dark glass background
Rounded feature cards
Gold accent highlights
Clear free-mode label
Status badges everywhere
Fast mobile-first navigation
Backup and rollback always visible
```
