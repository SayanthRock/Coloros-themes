# Public APK Update Problem Solver Plan

This plan defines the next public beta update for the ColorOS Customizer APK.

## Goal

Prepare a safer public APK beta that helps customers understand what works, what needs testing, and what is blocked.

The APK should not promise that every phone problem is fixed automatically. Instead, it should solve public release problems with clear status, safe actions, fallback guidance, and rollback.

## Public update focus

```text
v0.5.9-beta
Public APK problem solver and release readiness
```

## Problems to solve before public sharing

| Problem | Public fix | Status |
|---|---|---|
| Confusing feature labels | Use Working, Limited, Needs testing, Blocked, Not available | Required |
| Broken buttons | Hide or disable until tested | Required |
| Risky root actions | Show status first, keep action disabled until checks pass | Required |
| LSPosed scope confusion | Show LSPosed status and scope status separately | Required |
| Unknown ZIP/theme imports | Scan first, block unsafe import, show reason | Required |
| APK release clutter | Upload APK and module ZIP only | Done |
| Customer support difficulty | Keep support report copy/share visible | Required |
| Rollback confusion | Keep rollback guidance visible on every advanced screen | Required |
| Performance claims | Use safe guidance and avoid guaranteed boost claims | Required |
| Stable release risk | Keep as beta until real-device testing passes | Required |

## Public APK screens

1. Home status dashboard.
2. Working tools.
3. Rootd customization.
4. Performance Level.
5. Theme scanner.
6. Permissions.
7. Support report.
8. Rollback.
9. About and release channel.

## Customer-visible rules

The APK should show:

- What works now.
- What needs testing.
- What is not available on the current device.
- What needs root.
- What needs LSPosed scope.
- What can be safely opened in Android Settings.
- What is blocked until verified.

The APK should not show:

- Broken apply buttons.
- Untested system changes as Working.
- Unsupported device features as available.
- Fake lag-fix or battery-boost claims.
- Direct import for unknown package bundles.

## Default public beta behavior

| Feature | Default public beta behavior |
|---|---|
| Wallpaper picker | Enabled |
| Wallpaper apply | Enabled where Android API allows |
| Theme scanner | Enabled |
| Root status | Visible |
| LSPosed status | Visible |
| Scope status | Visible |
| Rootd customization | Visible with status labels |
| SystemUI hooks | Disabled |
| Launcher hooks | Disabled |
| Performance Balanced | Enabled |
| Battery Saver guidance | Enabled |
| Smooth guidance | Limited |
| Performance and Custom | Needs testing |
| Support report | Enabled |
| Rollback | Enabled |

## Release checklist

Before uploading the public beta APK:

1. Build debug APK.
2. Build release APK output.
3. Build module ZIP.
4. Upload APK and module ZIP only.
5. Confirm `BUILD_INFO.txt` is not uploaded.
6. Confirm `SHA256SUMS.txt` is not uploaded.
7. Test APK open on a real device.
8. Test wallpaper picker.
9. Test support report copy/share.
10. Test rollback guide visibility.
11. Check root and LSPosed labels.
12. Keep release as beta.

## Public wording

Use this wording in the APK:

> Public beta update: only working and safe tools are enabled. Advanced features remain hidden, blocked, or marked as Needs testing until verified on real devices.
