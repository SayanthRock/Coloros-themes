# Feature Parking and UI Mode Plan

This document parks every customer-facing option in one visible place so unfinished or device-specific features are not hidden inside Settings.

## Why some options did not work

The helper app has a working sliding UI, wallpaper picker, support report, and settings shortcuts. Several other options are currently previews only because they save local values but do not yet connect to tested LSPosed hooks or OEM APIs.

The main app UI is also hardcoded to a dark palette, so Dark / Light / System color cannot fully switch until the app palette is centralized.

## Safe feature status model

Use these status labels in the APK:

| Status | Meaning |
|---|---|
| Working | Runs inside the helper APK now. |
| Safe shortcut | Opens a supported Android or ColorOS settings page. |
| Preview only | Saves locally and appears in support reports. |
| Needs root scope | Requires LSPosed scope selection, root, and device testing. |
| Needs device test | Depends on OPPO / realme / OnePlus class names. Never crash on miss. |
| Not supported | Show explanation, do not execute. |

## Appearance modes to add to the Settings page

Add a new **Appearance** card under Settings:

- System mode
- Dark mode
- Light mode
- Accent preset: Desert Sand, OPlus Green, Oxygen Blue, Rock Gold

Expected behavior:

1. Save mode and accent in shared preferences.
2. Re-render the activity immediately.
3. Apply palette to background, cards, text, muted text, buttons, bottom navigation, status bar, and navigation bar.
4. Keep startup lightweight. Do not add heavy live blur.

## Feature parking page

Add a new page between **Performance** and **Support** named **Features**.

Recommended cards:

- Working now
- Safe shortcuts
- Preview only
- Needs root scope
- Needs device test
- Not supported

Each card should explain why the feature is parked and what must be done before enabling it.

## What must not be added

Do not add code that unlocks paid assets, weakens update integrity, overrides security checks, or bypasses user privacy controls. Those items should stay visible as **Not supported** instead of being hidden or executed.

## Next code changes

1. Add `AppAppearanceManager.java`.
2. Replace hardcoded color constants in `MainActivity.java` with a resolved palette object.
3. Add `FeatureParkingRegistry.java`.
4. Add a sixth tab called `Features`.
5. Update support report to include appearance mode and parked feature counts.
6. Keep LSPosed hooks safe, wrapped, and optional.
