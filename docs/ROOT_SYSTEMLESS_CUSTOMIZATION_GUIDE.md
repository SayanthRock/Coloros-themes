# Root Systemless Customization Guide

This guide explains how ColorOS Themes Rock should handle root access, system files, Magisk overlays, and LSPosed customization safely.

## Main goal

Give customers powerful customization options without directly modifying real Android partitions.

Use this architecture:

```text
Android APK UI
    ↓
User selects options
    ↓
Settings saved to module config
    ↓
Root command applies reversible settings
    ↓
Magisk module applies systemless overlays
    ↓
LSPosed hooks modify selected app behavior only when enabled
```

## Important rule

Do not directly write to these real partitions:

```text
/system
/product
/vendor
/system_ext
```

Use a Magisk, KernelSU, or APatch-style systemless module structure instead. The real system files should stay untouched.

## Recommended module structure

```text
ColorOS-Themes-Rock/
├── module.prop
├── customize.sh
├── post-fs-data.sh
├── service.sh
├── uninstall.sh
├── config/
│   └── settings.conf.example
├── system/
│   └── product/
│       └── overlay/
├── system_ext/
│   └── media/
│       └── themeInner/
├── customer-options/
│   └── options.json
├── docs/
└── lsposed-helper/
```

## APK root bridge

The Android APK should not request root at launch. Request root only when the user taps a root-only option.

Recommended Kotlin pattern with libsu:

```kotlin
import com.topjohnwu.superuser.Shell

fun hasRootAccess(): Boolean {
    return Shell.cmd("id").exec().isSuccess
}

fun setRefreshRate(rate: Int): Boolean {
    if (rate !in listOf(60, 90, 120, 144)) return false

    val result = Shell.cmd(
        "settings put system peak_refresh_rate $rate",
        "settings put system min_refresh_rate $rate"
    ).exec()

    return result.isSuccess
}
```

## Safe config values

Use `config/settings.conf.example` as the base configuration.

```properties
REFRESH_RATE=auto
ANIMATION_SCALE=default
BATTERY_MODE=balanced
```

Supported values:

| Key | Values | Risk |
|---|---|---|
| `REFRESH_RATE` | `auto`, `60`, `90`, `120`, `144` | Medium |
| `ANIMATION_SCALE` | `default`, `fast`, `off` | Low |
| `BATTERY_MODE` | `balanced`, `saver`, `performance` | Low to medium |

Unsupported values must be ignored, not force-applied.

## Customer-facing features

### Theme customization

- Default theme backup
- Theme restore
- Wallpaper manager
- Icon pack apply
- Lock screen customization
- Home screen customization
- Theme Store extra options

### Performance options

- Battery backup improvement guidance
- Balanced mode
- Battery saver mode
- Gaming/performance information panel
- Animation speed control
- Refresh rate Auto / High / Standard
- Background process tips
- Thermal-safe warnings

### System tools

- Backup module data
- Restore module data
- Clear module data
- Restart support instructions
- Safe disable guide
- Reboot reminder after module changes
- Customer support report

## LSPosed mode

LSPosed should be treated as experimental until every target package is tested.

Possible hook targets:

```text
Theme Store
Launcher
SystemUI
Settings
Lock screen
Wallpaper service
```

Rules:

1. Keep LSPosed hooks optional.
2. Never hide safety warnings.
3. Avoid heavy runtime hooks that can cause lag.
4. Add device and ROM checks before enabling a hook.
5. Provide a disable path if a hook causes problems.

## Bootloop protection

Before adding risky root features, keep these safety options documented and visible:

- Disable module from Magisk recovery mode if boot fails.
- Keep `service.sh` lightweight.
- Do not restart SystemUI automatically at boot.
- Do not overwrite real `/system` files.
- Test on one device before public release.
- Keep a rollback guide for every feature.

## Customer promise

The module should give the best available customization path for each device. It should not claim every feature works on every phone automatically. Use these labels:

- Works
- Partial
- Needs manual setup
- Needs root
- Needs device test
- Not supported
- Experimental

## Release checklist

Before publishing a GitHub Release:

- Validate `module.prop` version.
- Run module packaging workflow.
- Confirm the ZIP artifact is uploaded.
- Confirm README install steps are correct.
- Check `customer-options/options.json` is valid JSON.
- Confirm `service.sh` keeps safe defaults.
- Test install, reboot, disable, and uninstall behavior.
