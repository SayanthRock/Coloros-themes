# Android Permission Guide

This guide explains the permission groups that the customer-facing APK should show.

## Permission groups to show in the APK

| UI group | Android permission examples | When needed | Safety note |
|---|---|---|---|
| Notifications | `POST_NOTIFICATIONS` | Build status, update alerts, support messages, safe-mode warnings. | Ask only when notification features are enabled. |
| Photos and videos | `READ_MEDIA_IMAGES`, `READ_MEDIA_VIDEO` | Selecting wallpapers, previews, icons, and user-owned theme images. | Use the Android photo picker when possible. |
| Music and audio | `READ_MEDIA_AUDIO` | User-selected ringtones, notification sounds, and UI sounds. | Do not request unless audio customization is enabled. |
| Storage | `READ_EXTERNAL_STORAGE`, app-specific storage | Importing theme packages, exporting backups, reading customer-selected files. | Avoid broad storage access unless absolutely required. |
| Root access | Not a normal Android manifest permission | Flashing modules, applying systemless overlays, reading root status. | Request through Magisk, KernelSU, APatch, or equivalent root manager only. |
| LSPosed/Xposed scope | Not a normal Android manifest permission | Hook-based SystemUI, launcher, or framework customization. | Show scope status before enabling hooks. |

## Recommended customer UI

The APK permission screen should use clean Android-style permission groups:

```text
Permissions
├── Notifications
├── Photos and videos
├── Music and audio
├── Storage
├── Root access
└── LSPosed scope
```

Each row should show:

- Status: Granted, Not granted, Not needed, Needs setup, Unsupported.
- Reason: Short explanation of why the permission is needed.
- Risk: Safe, Limited, Root risk, Needs testing.
- Action: Open settings, request permission, open LSPosed, open root manager, or learn more.

## Permission policy

1. Request permissions only when the related feature is used.
2. Keep wallpaper and preview tools usable without root where possible.
3. Do not force broad storage permissions for simple image picking.
4. Do not claim root is granted unless the app actually detects root access.
5. Do not claim LSPosed scope is active unless the module can verify it.
6. Advanced features must stay hidden or labeled as Needs testing when permissions are missing.

## Customer-safe message

Use this wording inside the APK:

> Some features need Android permissions, root access, or LSPosed scope. Free tools stay available. Advanced features are clearly labeled so you know what works, what needs testing, and what is not available on your device.
