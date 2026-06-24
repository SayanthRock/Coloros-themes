# UI 2026 Standard and Premium Style

This document defines the next APK interface style for ColorOS Themes Rock.

## Goal

Transform the APK into a quick, easy, customer-friendly dashboard for OPPO, realme, and OnePlus users.

## Style direction

- 2026 standard mobile layout
- Premium liquid-glass cards
- Large readable titles
- Rounded sections
- Clear status badges
- One-tap actions
- Bottom navigation for fast movement
- Dark, Light, and System appearance support
- Desert Sand accent as the default brand color

## Main pages

| Page | Purpose | Status |
|---|---|---|
| Home | Quick customer dashboard | Working, improve layout |
| Theme Layers | Wallpaper and visual layer tools | Working, improve labels |
| Performance | Battery, display, and quick settings guidance | Working, improve cards |
| Support | Reports, backup notes, rollback guidance | Working, improve AfterSale flow |
| More | Appearance and app preferences | Working, connect UI mode fully |

## Quick and easy customer flow

1. Open app.
2. See device and feature status.
3. Choose a working tool.
4. Use one-tap action.
5. Copy or share report if something fails.
6. Use Safe Mode guidance when needed.

## Working features to improve

- Wallpaper picker
- Home and lock wallpaper apply
- Support report copy
- Support report share
- Settings shortcuts
- Display settings shortcut
- App settings shortcut
- Feature status labels
- Free customer tools

## New cards to add

- Root status
- LSPosed status
- Scope status
- Battery level
- CPU core count
- Memory status
- Display refresh-rate target
- Performance level
- Safe Mode
- Feedback
- AfterSale support

## Status labels

Use these labels everywhere:

- Working
- Limited
- Needs testing
- Not available

## Premium does not mean paid lock

Premium means better design quality, cleaner spacing, stronger typography, and smoother customer flow. Normal customer tools stay free.

## UI rules

- Do not hide unfinished options.
- Show every important feature with a clear status label.
- Keep first screen fast.
- Avoid heavy animation.
- Use safe settings shortcuts when direct customization is not reliable.
- Make support and rollback easy to find.

## Next APK implementation tasks

- Add a Home status grid.
- Add 2026 hero card copy.
- Add Feedback and AfterSale cards to Support.
- Add Safe Mode card to Support or More.
- Connect AppAppearanceManager to the main UI.
- Rename weak labels such as `Needs test` to `Needs testing`.
- Use `Working`, `Limited`, `Needs testing`, and `Not available` consistently.
