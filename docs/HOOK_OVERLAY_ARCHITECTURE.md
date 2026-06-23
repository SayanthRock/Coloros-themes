# Hook and Overlay Architecture

ColorOS Themes Rock uses a safe **Hook and Overlay** model for modern OPPO, OnePlus, and realme devices.

## Goal

Build a customer-ready customization module without replacing protected system APKs.

## Architecture

```text
Companion APK
  customer UI and toggles
      ↓
Config files
  safe options and device profile
      ↓
Module service
  reversible settings only
      ↓
Overlay layer
  owned theme and wallpaper assets
      ↓
Optional hook layer
  UI-only behavior where tested
```

## Core stack

| Layer | Role |
|---|---|
| Module engine | Magisk, KernelSU, or APatch style module format |
| Companion APK | Customer UI, toggles, support report, backup guidance |
| Config | Device profile and user settings |
| Overlay | Owned wallpapers, previews, and theme assets |
| Hook layer | Optional UI-only customization for tested packages |

## Package strategy

| Package area | Safe strategy |
|---|---|
| System UI | UI-only changes after device testing |
| Theme app | Owned themes, previews, and customer guidance only |
| OTA app | Do not modify update integrity or partition logic |
| Battery app | Use guidance and UI labels only |
| Wallpaper picker | Use standard wallpaper APIs and owned assets |

## Project boundaries

This project should stay focused on legal, reversible, customer-safe customization.

Do not add features that modify protected system APK files directly, interfere with system updates, change core battery or thermal control logic, hide customer warnings, or make unsupported claims.

## Config model

The module stores safe install information here:

```text
config/device-profile.conf
```

The user settings template is here:

```text
config/settings.conf.example
```

Recommended customer status labels:

```text
Safe
Ready
Needs test
Needs permission
Required
Not supported
Experimental
```

## Safe mode

The module supports a safe-disable flag named:

```text
disable
```

When this file exists inside the installed module directory, the late service exits without applying options.

## Device detection

The install script records brand, model, device name, Android release, SDK version, OPlus build version, display build ID, and customer support status.

This keeps unsupported devices in a safe, test-required state.

## Performance rules

- Keep boot scripts lightweight.
- Avoid heavy work during startup.
- Prefer reversible settings.
- Prefer transparent UI guidance over hidden behavior.
- Test each device and software version before marking a feature as supported.

## Customer promise

The module should be honest, recoverable, and device-aware. It should give customers safe options and clear support labels instead of promising that every feature works on every phone.
