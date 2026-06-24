# Customer Overlay Assets

This folder defines a safe, systemless overlay planning structure for **ColorOS Themes Rock**.

Supported customer target groups:

- `android`, framework-level visual resources.
- `com.android.systemui`, status bar, quick settings, notifications, and lock-screen surface styling.
- `com.android.settings`, Settings screen styling, cards, icons, and support/diagnostic entries.

## Safety model

- Keep this folder as assets, templates, presets, and status metadata only.
- Use only assets created by the project owner or assets with sharing permission.
- Keep runtime changes disabled by default.
- Show clear customer labels for anything requiring root, LSPosed scope, or device testing.
- Keep rollback visible before any advanced option is enabled.

## Customer flow

1. The APK reads `assets/Overlays/targets.json`.
2. The customer selects a package target and visual layer.
3. The Rootd status screen checks package availability, overlay readiness, LSPosed scope status, and rollback readiness.
4. The APK shows safe actions first, and advanced actions only when marked tested.

## Folder purpose

```text
assets/Overlays/
├── targets.json
├── customer-overlay-preset.json
├── android/
├── com.android.systemui/
├── com.android.settings/
└── templates/
```

This gives the project a clean customer-facing foundation for premium UI customization while staying reversible and device-test friendly.