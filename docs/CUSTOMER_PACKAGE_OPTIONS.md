# Customer Package Options

This file lists customer-facing package options for ColorOS Themes Rock. These options are status-first. They are not universal apply buttons.

## Requested package targets

| Target | Customer area | Default status | Customer-safe actions |
|---|---|---|---|
| `com.oplus.uxdesign` | UX colors, cards, typography, surfaces, motion tokens | Needs testing | Preview color tokens, view design tokens, export support report |
| `com.oplus.battery` | Battery cards, power saver labels, diagnostics | Safe guidance | Open battery settings, show guidance, export report |
| `colors.xml` | Theme color tokens | Template ready | Edit owned tokens, preview palette, validate XML |
| `com.android.launcher` | Home screen, launcher cards, icons, wallpaper preview | Needs testing | Open home settings, preview icon assets, export report |
| `/system_ext/media/themeInner/com.oplus.eyeprotect` | Eye comfort themeInner assets | Needs testing | Preview owned assets, status report, systemless module path only |
| `com.oplus.notificationmanager` | Notification controls, cards, permission guidance | Safe guidance | Open notification settings, show permission help, export report |
| `com.oplus.ota` | OTA labels, update warnings, safe-disable guidance | Safe guidance only | Show update warning, suggest safe-disable before update, export report |
| `com.android.wallpaper.livepicker` | Live wallpaper picker | Safe shortcut | Open picker when available, preview wallpaper assets |

## Customer rules

- Keep all normal customer tools visible and free.
- Mark advanced targets as `Needs testing` until verified on the exact OPPO, OnePlus, or realme ROM.
- Use systemless module paths only.
- Do not change update integrity for OTA packages.
- Do not mark a feature `Working` without a real-device test.
- Keep rollback and safe-disable instructions visible.

## colors.xml status

The repository includes a customer color-token reference in:

```text
assets/Overlays/templates/customer-colors.md
```

The exact `colors.xml` resource file should be generated or copied from that token list during real overlay packaging, then validated before release.

## Release labels

| Label | Meaning |
|---|---|
| Safe shortcut | Opens a public system screen when available. |
| Safe guidance | Shows instructions and reports only. |
| Template ready | Can be edited and previewed, but must be validated. |
| Needs testing | Requires exact ROM/device testing before customer release. |
| Safe guidance only | No runtime modification; warning/report flow only. |
