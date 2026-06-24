# Working-Only APK Policy

This policy defines how the ColorOS Customizer APK should handle features when some items are not working.

## Main rule

Only keep what works.

Features that are problematic, unsupported, unsafe, untested, or copied from unknown APK/ZIP assets must not be shown as working.

## Default APK behavior

| Feature type | Default customer state | Reason |
|---|---|---|
| Wallpaper picker | Visible | Safe Android picker flow. |
| Wallpaper apply | Visible | Uses Android wallpaper API where supported. |
| Theme package scanner | Visible | Inspection only, no risky apply action. |
| Theme package import | Disabled | Must verify ownership, format, and device compatibility first. |
| Root status | Visible | Status display only. |
| LSPosed status | Visible | Status display only. |
| System hooks | Hidden or disabled | Must be device-tested first. |
| Performance Level Balanced | Visible | Safe default profile. |
| Battery Saver guidance | Visible | Opens supported Android settings or guidance. |
| Smooth guidance | Limited | Device-specific display settings. |
| Performance / Custom | Needs testing | Not for normal customers until verified. |
| APK/ZIP binary imports | Blocked | Compiled code, Xposed metadata, native binaries, and signing files are unsafe to import blindly. |

## What to remove or hide

Remove or hide:

- Unsupported root actions.
- Unsupported LSPosed hooks.
- Untested SystemUI changes.
- Untested launcher hooks.
- Theme import actions that do not verify ownership and format.
- Any copied compiled APK assets.
- Any signing keys, test keys, or private keys.
- Any feature that causes crashes, bootloop risk, or unclear customer behavior.
- Any marketing text that claims unsupported improvements.

## What to keep

Keep:

- Wallpaper selection.
- Wallpaper preview.
- Android settings shortcuts.
- Device status.
- Root status display.
- LSPosed status display.
- Support report.
- Rollback guide.
- Backup and restore guide.
- Theme scanner.
- Compatibility labels.
- Performance Level Balanced.

## ZIP file rule

The uploaded ZIP can be used as a private inspection sample only.

Do not import from it directly if it contains:

- `classes.dex`
- `resources.arsc`
- `AndroidManifest.xml`
- `assets/xposed_init`
- `lib/*.so`
- `META-INF/*.RSA`
- `META-INF/*.DSA`
- test-key or signing files

If these files are detected, the APK should show:

```text
Blocked
This ZIP contains APK/module binaries or signing files. It will not be imported into the customer build.
```

## Customer wording

Use this wording in the APK:

> Only working and safe tools are enabled. Problematic or untested features are hidden, blocked, or marked as Needs testing until verified on real devices.

## Release checklist

Before any APK release:

1. Remove broken UI buttons.
2. Hide untested root hooks.
3. Hide untested LSPosed actions.
4. Keep support report visible.
5. Keep rollback visible.
6. Keep only safe Android settings shortcuts.
7. Confirm the APK opens without crash.
8. Confirm wallpaper picker works.
9. Confirm support report copy/share works.
10. Confirm problematic ZIP import is blocked.
