# New Module Fixes

This file explains what the new module release is meant to fix.

## Fixed areas

| Area | New fix |
|---|---|
| Missing module files | Validator checks required files |
| Missing theme folders | Validator checks required folders |
| Broken shell scripts | Validator checks shell syntax |
| Broken JSON files | Validator checks JSON syntax |
| Default theme not clear | Default Rock Premium theme is documented |
| Customer options unclear | Customer options catalog is included |
| Lag complaints | Lag guide and asset size check are included |
| UI style unclear | UI design system and design tokens are included |
| Background size confusion | Default theme preview and wallpaper folders are documented |
| Build release risk | GitHub Actions validates before packaging |

## What the module can fix directly

- Bad repository structure
- Missing files
- Missing folders
- Broken script syntax
- Broken JSON syntax
- Missing customer option catalog
- Oversized theme assets warning
- Confusing customer setup docs

## What needs real device testing

- Lock screen theme behavior
- OEM theme folder behavior
- Icon behavior
- Sound behavior
- LSPosed mode
- Android 15/16/17 device compatibility

## Release rule

The module is ready for customer testing only after GitHub Actions passes and at least one real device test is completed.
