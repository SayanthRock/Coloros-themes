# ColorOS Themes Rock

Customer-safe theme module starter for **OPPO, OnePlus, and realme** devices running ColorOS, OxygenOS, or realme UI on Android 15, Android 16, and Android 17.

This repo is built for legal theme customization, wallpaper packs, device compatibility checks, and safe setup guidance. It does **not** bypass paid themes, Theme Store DRM, private OEM protections, or region locks.

## What this project does

- Provides a Magisk/KernelSU/APatch-style module structure.
- Adds a systemless overlay target for `/system_ext/media/themeInner/`.
- Gives a clean place to package your own lock screen, home screen, wallpaper, and UI assets.
- Adds install-time device checks for OPPO, OnePlus, and realme.
- Adds a GitHub Actions workflow to build a flashable module ZIP.
- Publishes public GitHub Releases automatically from tags or manual workflow runs.
- Documents customer support steps so users know what is safe and what is not.

## Supported targets

| Brand | Android skin | Status |
|---|---|---|
| OPPO | ColorOS | Starter support |
| OnePlus | OxygenOS / ColorOS based builds | Starter support |
| realme | realme UI | Starter support |
| Android 15 | Modern supported target | Planned testing |
| Android 16 | Modern supported target | Planned testing |
| Android 17 | Latest target | Needs device-by-device testing |

## What it cannot safely do

- It cannot silently change lock screen or home screen on non-root phones.
- It cannot guarantee every OEM Theme Store package will accept external theme files.
- It cannot convert paid/protected Theme Store assets.
- It cannot honestly promise magical performance boosts. Performance features must be safe, reversible, and device-specific.
- It should not spoof About phone identity unless the user fully understands root/system risks.

## Folder structure

```text
.
├── module.prop
├── customize.sh
├── post-fs-data.sh
├── service.sh
├── uninstall.sh
├── system_ext/media/themeInner/
├── themes/sample/theme.json
├── docs/
│   ├── COMPATIBILITY.md
│   ├── CUSTOMER_SUPPORT.md
│   └── ROADMAP.md
└── .github/workflows/build-module.yml
```

## Build module ZIP

The GitHub workflow builds the module ZIP automatically on every push to `main`.

Manual local build:

```bash
bash scripts/package.sh
```

Output:

```text
dist/ColorOS-Themes-Rock-v0.1.0.zip
```

## Public releases

Public GitHub Releases are automated through `.github/workflows/build-module.yml`.

### Option 1: Release from a tag

```bash
git tag v0.1.1
git push origin v0.1.1
```

The workflow will build the module ZIP and publish a public release with the ZIP attached.

### Option 2: Release from GitHub Actions

1. Open **Actions**.
2. Select **Build Theme Module**.
3. Tap **Run workflow**.
4. Enter a version like `v0.1.1`.
5. Enable **Create a public GitHub Release**.
6. Run the workflow.

## Install

1. Open the latest GitHub Release.
2. Download the module ZIP.
3. Open Magisk, KernelSU, or APatch.
4. Flash the module ZIP.
5. Reboot.
6. Test on one device first before giving it to customers.

## Customer-safe feature plan

| Feature | Safe approach |
|---|---|
| Lock screen themes | Own assets only, root module or OEM-supported theme import |
| Home screen style | Wallpapers, icon packs, launcher-safe instructions |
| Wallpapers | Companion app using Android `WallpaperManager` later |
| About phone | Device info dashboard first, no unsafe spoofing by default |
| Performance | Diagnostics, battery settings guide, no fake RAM/speed claims |
| Customer support | Device report template, compatibility table, rollback steps |

## Legal and safety rule

Only distribute themes, wallpapers, fonts, icons, sounds, and UI files that you created, own, or have permission to redistribute. Keep the project clean so customers can trust it.
