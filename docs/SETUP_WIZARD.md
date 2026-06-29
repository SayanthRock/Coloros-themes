# Setup Wizard and UI Tweak Flow

ColorOS Themes Rock should feel safe for people using Magisk, KernelSU, APatch, LSPosed, or a compatible Xposed-style framework. The app should not make users guess which switch matters.

## Main app experience

The APK should use a setup-first flow:

1. Setup Guide
2. UI Tweaks
3. Module
4. Support
5. More

The first screen should walk users through root manager detection, root availability, LSPosed or compatible framework setup, required scopes, ROM-specific module generation, and reboot verification.

## Root manager friendly behavior

The app supports guidance for:

- Magisk
- KernelSU
- KernelSU Next
- APatch
- compatible systemless module managers

The APK does not write protected system files by itself. It prepares status reports, profiles, and module installation guidance.

## LSPosed or compatible framework setup

The setup guide tells the user to:

1. Install LSPosed or a compatible framework.
2. Enable ColorOS Themes Rock as a module.
3. Scope only required packages.
4. Reboot after changing scope.

Recommended scopes:

```text
android
com.android.systemui
com.android.settings
com.oplus.systemui, when present
com.coloros.systemui, when present
com.oplus.launcher, when present
com.coloros.launcher, when present
com.realme.launcher, when present
com.android.launcher3, when present
com.heytap.weather, when lock screen weather is supported
com.coloros.weather2, when lock screen weather is supported
com.oplus.weather, when lock screen weather is supported
com.realme.weather, when lock screen weather is supported
```

Keep the scope tight. Do not scope every app.

## ROM-specific module generation

The APK generates a readable ROM Module Profile using:

- Brand
- Manufacturer
- Model
- Device
- Product
- Build display
- Android release
- Android SDK
- Detected ROM family
- Detected root manager
- Detected LSPosed manager
- UI focus areas

Profile examples:

```text
oplus-coloros-sdk35
oneplus-oxygenos-coloros-sdk35
realme-ui-sdk35
generic-android-sdk35
```

Use this profile when creating the flashable module ZIP with `scripts/package.sh` or GitHub Actions.

## Module install flow

The user-facing module flow should stay simple:

1. Generate the ROM-specific module ZIP.
2. Flash it in Magisk, KernelSU, APatch, or a compatible root manager.
3. Enable this APK in LSPosed or a compatible framework.
4. Enable only required scopes.
5. Reboot.
6. Open the app and copy the support report if anything fails.

## Main UI tweak areas

The app should focus on these visible customizations:

| Area | User-facing purpose | Status behavior |
|---|---|---|
| Lock screen | Clock, wallpaper, media surface, weather where supported | Needs testing |
| Status bar | Icon spacing, blur preference, carrier/status labels | Profiled |
| Quick settings | Tile shape, header surface, brightness area | Needs testing |
| Launcher | Grid, folder preview, icon-layer compatibility | Limited |
| System UI | Notifications, panels, rounded surfaces, fallback labels | LSPosed scope required |
| Lock screen weather | Weather surface only on compatible OPlus/ColorOS/realme packages | ROM check needed |

Every tweak must show a status label. Apply actions should stay conservative until a device and ROM build are tested.

## Safety rules

- Do not claim unsupported tweaks work.
- Do not enable hooks silently.
- Do not scope every package by default.
- Reboot after flashing a module or changing LSPosed scope.
- Keep rollback and support report visible.
- Use user-owned or licensed theme assets only.
