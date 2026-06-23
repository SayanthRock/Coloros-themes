# Theme Module Builder

This guide explains how to build the ColorOS Themes Rock module ZIP with owned customization assets.

## Build locally

```bash
python3 scripts/inspect-theme-package.py /path/to/theme.theme --pretty
bash scripts/validate-module.sh
bash scripts/package.sh
```

Output:

```text
dist/ColorOS-Themes-Rock-v0.4.0.zip
```

## Uploaded sample metadata

| File | Detected root | Main contents | Resolution |
|---|---|---|---|
| `aquatic_design.theme` | `OplusSmartPhoneThemeInfo` | launcher, icons, picture, wallpaper | 2400x1080 |
| `1-Simplicity.theme` | `OppoSmartPhoneThemeInfo` | OPPO launcher, wallpaper, previews | 2340x1080 |

## GitHub build

1. Open **Actions**.
2. Select **Build Theme Module**.
3. Tap **Run workflow**.
4. Enter a version like `v0.4.0`.
5. Download the module ZIP from artifacts or release assets.

Only share files that you created, own, or have permission to share.
