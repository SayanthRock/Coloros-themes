# Rootd System File Support

ColorOS Themes Rock uses a systemless-only rule for system file related theme work.

## Policy

The helper APK must not directly rewrite Android system partitions. All advanced theme work should be handled through a reversible module package, status labels, and rollback notes.

## Supported module target

```text
/system_ext/media/themeInner
```

Inside the module package this maps to:

```text
system_ext/media/themeInner
```

## Overlay target labels

| Target | Area | Default status |
|---|---|---|
| `android` | Framework visual resources | Needs device test |
| `com.android.systemui` | Status bar, quick settings, notifications, lock surface previews | Needs device test |
| `com.android.settings` | Settings cards, About phone, diagnostics previews | Needs device test |

## Safe-disable flow

Create this file in the installed module folder to stop late service work:

```text
disable
```

After reboot, the service exits without applying optional settings and writes a support marker under:

```text
/data/local/tmp/coloros-themes-rock/
```

## Customer support report

The helper APK and service generate reports for:

- Brand, model, device name, Android version, and SDK.
- OPlus-family brand detection.
- Android 15/16/17 support label.
- Systemless policy status.
- Module theme mount target.
- Safe-disable state.

## Release rule

Do not label a system UI, Settings, lock screen, or framework theme feature as `Working` until it has been tested on the exact OPPO, OnePlus, or realme device and ROM version.
