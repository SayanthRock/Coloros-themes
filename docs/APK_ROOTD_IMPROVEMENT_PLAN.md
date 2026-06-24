# APK Rootd Improvement Plan

This plan defines how the ColorOS Customizer APK should improve root-related features safely for customers.

## Goal

The APK should not blindly apply root changes. It should first show status, compatibility, scope, and rollback information.

## Main Rootd dashboard

```text
Rootd Dashboard
├── Device status
├── Root manager status
├── Module status
├── LSPosed/Xposed status
├── Scope status
├── Theme engine status
├── Safe mode / rollback
└── Logs and support report
```

## Status cards

| Card | Shows | Action |
|---|---|---|
| Device | Brand, model, Android version, build fingerprint, ROM skin | Copy support report |
| Root manager | Magisk, KernelSU, APatch, or unavailable | Open root manager guide |
| Module | Installed, enabled, disabled, missing | Open install guide |
| LSPosed/Xposed | Active, inactive, unavailable | Open LSPosed guide |
| Scope | Selected packages and missing packages | Open scope checklist |
| Theme engine | Theme scan result and supported targets | Open theme scanner |
| Rollback | Disable file, uninstall guide, safe-mode state | Create safe-mode marker guide |

## Feature gating

Use strict gating before showing advanced actions.

| Feature | Required status | UI label if missing |
|---|---|---|
| Wallpaper tools | No root required | Working |
| Theme package inspection | No root required | Working |
| Module ZIP install guide | Root manager app installed | Root required |
| Systemless overlay | Root manager and module path ready | Limited |
| SystemUI hook | LSPosed active and target package scoped | LSPosed scope required |
| Launcher hook | LSPosed active and launcher package scoped | LSPosed scope required |
| Android 17 support | Real device verified | Needs testing |

## Safe mode behavior

The app should explain safe mode before any root-only action.

Recommended markers:

```text
/data/adb/modules/coloros_themes_rock/disable
/sdcard/ColorOS-Themes-Rock/safe_mode
/sdcard/ColorOS-Themes-Rock/disable_hooks
```

When safe mode is detected:

- Hide hook actions.
- Keep wallpaper and documentation tools visible.
- Show rollback steps.
- Show support report export.

## Customer support report

The APK should generate a plain text report without personal files.

Include:

- App version.
- Module version.
- Device brand and model.
- Android version.
- ROM skin detection result.
- Root manager status.
- LSPosed/Xposed status.
- Scope status.
- Enabled features.
- Last safe-mode state.

Do not include:

- Personal photos.
- Private theme files.
- Signing keys.
- Account tokens.
- Full app data dumps.

## Rootd UI wording

Use honest wording:

- Working: tested on this device profile.
- Limited: may work only on selected ROM versions.
- Needs testing: available for testers only.
- Not available: blocked on this device.
- Root required: root manager must approve access.
- LSPosed scope required: target app must be scoped before hooks can run.

Avoid wording such as:

- Works on every phone.
- Solves all problems.
- Guaranteed lag fix.
- Guaranteed battery boost.
- Bootloop proof.

## Implementation order

1. Build Device Status screen.
2. Build Permission Status screen.
3. Build Rootd Dashboard.
4. Build Theme Scanner screen.
5. Add feature labels to every advanced option.
6. Add support report export.
7. Add safe-mode and rollback guide.
8. Test on OPPO, OnePlus, and realme devices before stable release.
