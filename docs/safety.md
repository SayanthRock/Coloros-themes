# Root, LSPosed, and Customer Safety Guide

ColorOS Themes Rock must be safe-first. Root and LSPosed customization can be powerful, but mistakes can cause bootloops, broken SystemUI, launcher crashes, or unsupported device behavior.

## Core safety rules

1. Do not modify `/system`, `/system_ext`, `/product`, or `/vendor` directly.
2. Prefer systemless overlays through Magisk, KernelSU, or APatch module structure.
3. Keep a rollback path for every risky feature.
4. Keep free customer tools available even when root-only features are disabled.
5. Never label a feature as Working until it is tested on a real matching device.
6. Never hide bootloop, root, LSPosed, or unsupported-ROM warnings.
7. Do not copy GPL-licensed source code unless this project follows compatible GPL licensing.

## Required status checks

The APK should show these checks before enabling advanced features:

| Check | Good state | Blocked state |
|---|---|---|
| Root manager | Magisk, KernelSU, or APatch detected | Root not detected |
| Module status | Module installed and enabled | Module missing or disabled |
| LSPosed/Xposed | Framework active when hooks are needed | Framework inactive |
| Scope status | Target package selected | Target package not selected |
| ROM match | Device family and Android skin match a supported profile | Unknown or unsupported ROM |
| Safe rollback | Disable, uninstall, or safe-mode step available | No rollback path |

## Kill-switch design

Add a safe-disable check before applying risky logic.

Recommended disable markers:

```text
/data/adb/modules/coloros_themes_rock/disable
/sdcard/ColorOS-Themes-Rock/disable_hooks
/sdcard/ColorOS-Themes-Rock/safe_mode
```

If a marker exists, the module should skip hooks, overlays, and risky startup actions.

## Customer warning text

Use this warning before root-only tools:

> This feature needs root or LSPosed/Xposed support. It may not work on every OPPO, OnePlus, or realme device. Keep rollback ready before enabling it. Use only features marked Working for your device.

## Release safety levels

| Release level | Use case | Public message |
|---|---|---|
| Stable | Tested customer release | Recommended for normal users. |
| Beta | Mostly working, needs broader testing | Use only if you can test and report issues. |
| Nightly | Automatic or experimental build | Advanced users only. Bugs are expected. |
| Experimental | Android 17 or unknown ROM work | Not for normal customers. |

## What not to promise

Avoid unsafe claims such as:

- Solves all problems.
- Works on every phone.
- Guaranteed lag fix.
- Guaranteed battery boost.
- Bootloop proof.
- Full root access without user approval.

Better wording:

- Improves supported customization flows.
- Shows clear device status.
- Keeps rollback guidance visible.
- Labels tested and untested features clearly.
- Provides safer customer tools where supported.
