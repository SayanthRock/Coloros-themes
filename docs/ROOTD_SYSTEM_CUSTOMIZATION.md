# Rootd System Customization Plan

This plan defines how the uploaded `rock theme .zip` can be used safely as a reference for Rootd system customization.

## Safety decision

The uploaded ZIP must not be copied directly into the repository or flashed directly as a system module.

It was identified as an APK/module-style bundle, not a clean ColorOS `.theme` package. Because of that, only verified, owned, non-code customization assets may be imported later.

## Rootd placement model

Rootd system customization should use a systemless structure:

```text
rootd/
├── README.md
├── rootd-system-map.json
├── system_ext/
│   └── media/
│       └── themeInner/
│           └── .gitkeep
├── overlays/
│   └── .gitkeep
├── customer-overrides/
│   └── .gitkeep
└── reports/
    └── .gitkeep
```

## What Rootd can customize

| Area | Rootd status | Customer label |
|---|---|---|
| Wallpaper | Safe when user selects image | Working |
| Theme preview | Safe when asset ownership is clear | Working |
| Icons | Device and launcher dependent | Needs testing |
| Lock screen | ROM dependent | Needs testing |
| Fonts | Only owned or permitted fonts | Needs testing |
| Sounds | Only owned or permitted sounds | Needs testing |
| Status labels | APK-only UI labels | Working |
| Performance Level | Balanced and guidance only | Working / Limited |
| SystemUI hooks | Root and LSPosed scope required | Not available until tested |
| Launcher hooks | Root and LSPosed scope required | Not available until tested |

## Uploaded ZIP import rule

Allowed only after manual verification:

- Owned wallpapers.
- Owned preview images.
- Owned icons.
- Owned fonts.
- Owned sound files.
- Plain JSON metadata that does not contain secrets.

Blocked by default:

- Compiled app code.
- Xposed hook entry metadata.
- Native binaries.
- Signing or test-key files.
- Unknown module files.
- Any file without clear ownership or permission.

## Customer APK options

The APK should show these Rootd customization options:

1. Device status.
2. Root status.
3. LSPosed status.
4. Scope status.
5. Theme scanner.
6. Wallpaper tools.
7. Preview tools.
8. Performance Level.
9. Backup and restore.
10. Rollback.
11. Support report.

Advanced Rootd actions must remain hidden or disabled until the required status checks pass.

## Required checks before applying Rootd changes

1. Device brand and model.
2. Android version.
3. ROM skin.
4. Root manager availability.
5. Module enabled state.
6. LSPosed availability, when hooks are needed.
7. Target package scope, when hooks are needed.
8. Safe mode marker.
9. Rollback path.
10. Asset ownership.

## Apply behavior

The APK should never apply all changes blindly.

Recommended behavior:

```text
Scan asset → Verify type → Show compatibility → Show risk label → Allow preview → Apply only safe supported action → Keep rollback visible
```

## Stable release rule

Only mark a Rootd option as Working after it has been tested on a real matching OPPO, OnePlus, or realme device.
