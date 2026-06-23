# UI Design System

Use this design system for the default theme, customer helper app, screenshots, and future website.

## Design direction

ColorOS Themes Rock uses a **free-first sliding liquid-glass UI**. The app should feel transparent, modern, layered, fast, and helpful. Important tools should be easy to find, clearly labeled, and never hidden behind confusing paid screens.

## Visual style

- Transparent liquid glass effect
- Premium dark UI
- Soft translucent cards
- Desert Sand accent
- Rounded modern panels
- Clean spacing
- Strong title hierarchy
- Status badges for every feature
- Sliding app navigation
- Mobile-first layout
- Fast startup screens
- Minimal motion
- Clear free-mode messaging

## Free-first rule

The customer app should support a clear free mode:

- No forced payment screen
- No locked premium wording
- No confusing trial labels
- No fake booster claims
- No troll or demo-only options
- Optional support message only if added later
- Every feature must show its real support status

## Colors

| Token | Value | Usage |
|---|---|---|
| Background | `#0f0f10` | Main dark background |
| Background elevated | `#151517` | Top bars and slider dock |
| Glass surface | `#1a1a1d` | Transparent card base |
| Glass surface soft | `#242429` | Elevated liquid cards |
| Glass border | `#4dffffff` | Soft card outline |
| Accent | `#e2b884` | Buttons, active states, highlights |
| Accent soft | `#f0d2a8` | Subtle highlight and icon glow |
| Accent glow | `#44e2b884` | Liquid glass glow layer |
| Text primary | `#f5f2ea` | Main text |
| Text muted | `#b9b1a3` | Notes and descriptions |
| Warning | `#ffcc66` | Needs test or warning labels |
| Success | `#8fd694` | Safe or supported labels |
| Danger | `#ff7a7a` | Unsupported or high-risk labels |

## Shape and spacing

| Element | Rule |
|---|---|
| Screen padding | 18dp |
| Card radius | 26dp |
| Primary button radius | 22dp |
| Chip radius | Full pill shape |
| Card padding | 18dp |
| Section gap | 16dp to 24dp |
| Bottom slider height | 90dp |

## Sliding app navigation

The app uses five sliding pages:

1. Home
2. Theme Layers
3. Performance
4. Support
5. More

Navigation rules:

- Swipe left or right to change pages.
- Bottom slider remains visible.
- Page indicator shows current page.
- Left and right arrow controls are available.
- Keep page changes lightweight.

## Layer customization model

All layer customization should be organized clearly:

| Layer | Purpose |
|---|---|
| Base Layer | Background, liquid glass card system, spacing |
| Wallpaper Layer | Home and lock wallpaper actions |
| Icon Layer | Launcher-supported icon options |
| Lock Layer | Lock screen guidance and supported actions |
| Status Layer | Feature badges and support labels |
| Support Layer | Report, backup, restore, rollback help |

## Customer screen order

1. Home dashboard
2. Theme Layers
3. Performance
4. Support
5. More

## Feature status badges

Every feature must show one badge:

| Badge | Meaning |
|---|---|
| Safe | Can be used normally |
| Ready | UI and guidance are available |
| Fast | Optimized for startup and smooth use |
| Needs test | Depends on phone model or software version |
| Needs permission | Requires extra user approval |
| Required | Important safety or support step |
| Not supported | Hide apply button and show explanation |

## Performance rules

- Keep startup screen lightweight.
- Avoid heavy live blur.
- Use translucent cards and subtle borders for the blur-style look.
- Avoid long animations.
- Keep card text short.
- Keep customer actions one tap away.

## New model style summary

```text
Transparent liquid glass effect
Sliding page navigation
Fast startup dashboard
Rounded translucent cards
Gold accent highlights
Clear free-mode label
All layer customization
Status badges everywhere
Backup and rollback always visible
No troll/demo-only options
```
