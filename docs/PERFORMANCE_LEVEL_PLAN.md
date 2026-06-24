# APK Performance Level Plan

This plan defines a safe **Performance Level** feature for the ColorOS Customizer APK.

## Goal

Performance Level should help customers choose simple, understandable device behavior presets. It must not promise fake speed boosts, fake battery boosts, or universal lag fixes.

The APK should show:

- Current device profile.
- Current selected performance level.
- What each level changes.
- Whether root is required.
- Whether LSPosed scope is required.
- Whether the feature is Working, Limited, Needs testing, or Not available.
- A rollback option before advanced changes are enabled.

## Performance levels

| Level | Customer purpose | Root required | Safety label |
|---|---|---:|---|
| Off | Do not apply any performance preset. | No | Working |
| Battery Saver | Prefer lower power usage and background control. | Optional | Limited |
| Balanced | Daily use, normal smoothness, safer defaults. | No | Working |
| Smooth | Better UI smoothness where supported. | Optional | Limited |
| Performance | Gaming or heavy use profile. | Optional / device-specific | Needs testing |
| Custom | Advanced user-controlled settings. | Yes for root-only controls | Needs testing |

## APK UI layout

```text
Performance Level
├── Current status
│   ├── Device profile
│   ├── Android version
│   ├── ROM skin
│   ├── Root status
│   └── LSPosed status
├── Presets
│   ├── Off
│   ├── Battery Saver
│   ├── Balanced
│   ├── Smooth
│   ├── Performance
│   └── Custom
├── What will change
├── Safety label
├── Apply button
└── Rollback button
```

## Safe preset behavior

### Off

- No preset applied.
- Shows current device status only.
- Keeps rollback available.

### Battery Saver

- Shows battery-related Android settings shortcuts.
- Shows background usage guidance.
- Shows thermal and refresh-rate warnings where supported.
- Does not force-kill apps blindly.

### Balanced

- Default recommended profile.
- Keeps free customer tools available.
- Avoids risky root-only actions.
- Best option for normal customers.

### Smooth

- Shows display and refresh-rate guidance.
- Allows supported non-root shortcuts first.
- Root-only tweaks must be clearly labeled.

### Performance

- Intended for gaming and heavy use.
- Must show heat, battery drain, and device-specific warnings.
- Must not auto-enable root hooks without status checks.

### Custom

- Advanced controls only.
- Requires clear warnings.
- Must include reset to Balanced.

## Required status checks

Before applying any advanced performance option, check:

1. Device brand and model.
2. Android version.
3. ROM skin.
4. Root manager status.
5. Module status.
6. LSPosed/Xposed status when hooks are needed.
7. Scope status when target packages are needed.
8. Safe-mode state.
9. Rollback path.

## Do not do this

Avoid unsafe behavior:

- Do not force-close customer apps without permission.
- Do not disable system services blindly.
- Do not claim guaranteed lag fix.
- Do not claim guaranteed battery backup.
- Do not apply root hooks when LSPosed scope is missing.
- Do not hide thermal or battery-drain warnings.
- Do not mark Performance as Working until tested on real devices.

## Customer wording

Use this wording in the APK:

> Performance Level helps you choose a simple device behavior preset. Results depend on your phone, Android version, ROM, root status, and enabled modules. Use Balanced for normal daily use.

## Recommended first release

For the first APK update, ship only:

- Off.
- Balanced.
- Battery Saver guidance.
- Smooth guidance.
- Device status.
- Root and LSPosed status.
- Rollback button.

Keep Performance and Custom marked as **Needs testing** until real-device verification is complete.
