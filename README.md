# ColorOS Themes Rock

> **Modern, customer-safe theme customization for ColorOS, OxygenOS, and realme UI.**
>
> A free-first toolkit for theme packages, systemless overlays, visual customization, diagnostics, rollback, and a ColorOS Customizer companion APK.

[![Android 15](https://img.shields.io/badge/Android-15%2B-3ddc84?style=flat-square&logo=android&logoColor=white)](docs/ANDROID_15_16_17_SUPPORT.md)
[![Systemless](https://img.shields.io/badge/Root-Systemless-e2b884?style=flat-square)](docs/ROOTD_SYSTEM_FILE_SUPPORT.md)
[![License](https://img.shields.io/badge/License-GPL--compatible%20reference-blue?style=flat-square)](docs/safety.md)

---

## ✦ 2026 Design Direction

ColorOS Themes Rock is moving toward a **free-first, liquid-glass-inspired customer experience**: premium-looking without hiding essential tools, clear about compatibility, and safe by default.

### Design principles

- **Liquid Glass** — layered translucent surfaces, soft borders, rounded cards, and subtle depth.
- **Dark premium UI** — `#0f0f10` foundation with warm Desert Sand accents.
- **Fast first** — lightweight startup screens and minimal motion.
- **Status first** — every customer-facing feature explains whether it is safe, ready, limited, or needs testing.
- **Free first** — no forced payment screens, fake trials, troll options, or fake performance claims.
- **Rollback always** — advanced customization must have a clear recovery path.
- **Systemless only** — APK-driven system-file changes never write directly into `/system`, `/vendor`, `/product`, or `/system_ext`.

See [`docs/UI_DESIGN_SYSTEM.md`](docs/UI_DESIGN_SYSTEM.md) for the complete visual and interaction specification.

---

## 🚀 What this project provides

| Capability | Purpose |
|---|---|
| **Theme modules** | Flashable Magisk, KernelSU, or APatch-style ZIP packages |
| **Theme inspection** | Safely inspect ZIP-based ColorOS/OPlus/realme `.theme` packages |
| **Systemless overlays** | Target `system_ext/media/themeInner/` from the module package |
| **Theme layers** | Wallpapers, icons, fonts, sounds, previews, and supported UI resources |
| **ColorOS Customizer** | Companion APK for customization, reports, permissions, and support |
| **Rootd foundation** | Status-first system-file support with safe defaults |
| **LSPosed visibility** | Show manager/scope status without pretending unsupported features work |
| **Diagnostics** | Device reports, permission status, health information, and troubleshooting |
| **Rollback** | Safe-disable and uninstall markers plus documented recovery guidance |
| **Release channels** | Stable, Beta, and Nightly metadata for customer update checks |
| **CI automation** | APK/module builds, checksums, provenance, artifacts, and optional Releases |

---

## 📱 Supported platforms

| Platform | Target | Status |
|---|---:|---|
| OPPO | ColorOS | Starter support |
| OnePlus | OxygenOS / ColorOS-based builds | Starter support |
| realme | realme UI | Starter support |
| Android | 15 / SDK 35 | Supported modern target |
| Android | 16 / SDK 36 | Forward-compatible; device testing required |
| Android | 17 / SDK 37 | Preview/future-safe; device-by-device testing required |

Compatibility is intentionally **device- and ROM-aware**. A feature is not presented as universally supported simply because its UI exists.

Read [`docs/compatibility.md`](docs/compatibility.md) and [`docs/ANDROID_15_16_17_SUPPORT.md`](docs/ANDROID_15_16_17_SUPPORT.md).

---

## 📦 Current release line

| Component | Version / status |
|---|---|
| Theme module | `v0.6.0` |
| Helper APK | `0.6.0` · versionCode `12` |
| Android 15 | Supported modern target |
| Android 16 | Forward-compatible · device testing required |
| Android 17 | Preview/future-safe · device testing required |
| Rootd system-file work | Systemless-only |

---

## 🧩 Customer experience

The companion app follows a five-page sliding model:

**Home → Theme Layers → Performance → Support → More**

### Theme Layers

Customization is organized into clear layers rather than one overloaded settings screen:

- **Base** — background, glass surfaces, spacing, and visual foundation
- **Wallpaper** — home and lock-screen wallpaper actions
- **Icon** — launcher-supported icon options
- **Lock** — lock-screen guidance and supported actions
- **Status** — compatibility and feature state
- **Support** — reports, backup, restore, and rollback help

### Feature status

Every customer-facing feature should communicate its real state:

| Status | Meaning |
|---|---|
| 🟢 **Safe** | Can be used normally |
| **Ready** | UI and guidance are available |
| **Fast** | Optimized for startup/smooth use |
| 🟡 **Needs test** | Depends on device, ROM, or Android version |
| **Needs permission** | Requires additional user approval |
| **Required** | Important safety/support step |
| 🔴 **Not supported** | Do not offer an apply action |

The older compatibility labels remain documented for technical/reporting contexts: **Working, Limited, Needs testing, Not available, Root required, LSPosed scope required**.

---

## 🛡️ Systemless Rootd support

System-file-related customization follows a strict systemless-only policy.

| Area | Policy |
|---|---|
| Theme mount target | `system_ext/media/themeInner` inside the module package |
| APK direct `/system` writes | **Not allowed** |
| APK direct `/vendor` writes | **Not allowed** |
| APK direct `/product` writes | **Not allowed** |
| APK direct `/system_ext` writes | **Not allowed** |
| Rollback | Safe-disable file + uninstall marker |
| Reports | `/data/local/tmp/coloros-themes-rock/` |

Read [`docs/ROOTD_SYSTEM_FILE_SUPPORT.md`](docs/ROOTD_SYSTEM_FILE_SUPPORT.md) and [`docs/ROOTD_CUSTOMER_FOUNDATION.md`](docs/ROOTD_CUSTOMER_FOUNDATION.md).

---

## 🎨 Theme and overlay targets

The Rootd customer layer currently maps these package targets:

| Target | Customer area | Default state | Package folder |
|---|---|---|---|
| `android` | Framework visual resources | Needs testing | `assets/Overlays/android/` |
| `com.android.systemui` | Status bar, quick settings, notifications, lock-surface previews | Needs testing | `assets/Overlays/systemui/` |
| `com.android.settings` | Settings cards, About phone, support/diagnostics previews | Needs testing | `assets/Overlays/settings/` |

The authoritative package map is [`assets/Overlays/targets.json`](assets/Overlays/targets.json), with customer-safe defaults in [`customer-options/safe-defaults.conf`](customer-options/safe-defaults.conf).

---

## 🔍 Inspected theme packages

Uploaded packages were inspected as ZIP-based theme archives containing `themeInfo.xml` metadata.

| Package | Detected root | Main contents | Metadata resolution |
|---|---|---|---|
| `aquatic_design.theme` | `OplusSmartPhoneThemeInfo` | launcher, icons, picture, wallpaper | 2400×1080 |
| `1-Simplicity.theme` | `OppoSmartPhoneThemeInfo` | OPPO launcher, wallpaper, previews | 2340×1080 |

Only redistribute packages and assets when you have the required rights.

---

## 📁 Project structure

```text
.
├── module.prop
├── customize.sh
├── post-fs-data.sh
├── service.sh
├── uninstall.sh
├── system_ext/media/themeInner/
├── themes/default/
├── themes/theme-pack-catalog.json
├── theme-packs/
├── customer-options/
│   ├── options.json
│   └── safe-defaults.conf
├── assets/Overlays/
├── scripts/
│   ├── package.sh
│   ├── validate-module.sh
│   ├── check-theme-size.sh
│   └── inspect-theme-package.py
├── docs/
│   ├── compatibility.md
│   ├── permissions.md
│   ├── safety.md
│   ├── ANDROID_15_16_17_SUPPORT.md
│   ├── ROOTD_SYSTEM_FILE_SUPPORT.md
│   ├── ROOTD_CUSTOMER_FOUNDATION.md
│   ├── UI_DESIGN_SYSTEM.md
│   ├── LAG_FIX_GUIDE.md
│   └── PROBLEM_SOLVER_MATRIX.md
├── lsposed-helper/
├── latestStable.json
├── latestBeta.json
├── latestNightly.json
└── .github/workflows/
    ├── build-theme-module.yml
    └── publish-github-release.yml
```

---

## 🛠️ Build the theme module

Inspect a theme package:

```bash
python3 scripts/inspect-theme-package.py /path/to/theme.theme --pretty
```

Validate and package:

```bash
bash scripts/validate-module.sh
bash scripts/package.sh
```

Expected output:

```text
dist/ColorOS-Themes-Rock-v0.6.0.zip
```

## 📲 Build the ColorOS Customizer APK

```bash
cd lsposed-helper
gradle :app:assembleDebug --no-daemon --stacktrace
```

APK output:

```text
lsposed-helper/app/build/outputs/apk/debug/app-debug.apk
```

Combined release automation is provided by `.github/workflows/publish-github-release.yml`.

---

## 📡 Release channels

| Channel | Metadata | Purpose |
|---|---|---|
| **Stable** | `latestStable.json` | Tested public customer builds |
| **Beta** | `latestBeta.json` | Wider pre-release testing |
| **Nightly** | `latestNightly.json` | Experimental development builds |

---

## 🧪 Customer customization matrix

| Area | Supported approach | Status |
|---|---|---|
| Wallpaper | User-selected image or owned theme package | Safe |
| Home screen | Wallpaper API or OEM theme package | Device testing required |
| Lock screen | Wallpaper API or OEM theme package | Device testing required |
| Icons | Theme icon asset or launcher icon pack | Device testing required |
| Fonts | Owned font/theme asset | Device testing required |
| Sounds | Owned ringtone/UI sound asset | Device testing required |
| Preview images | Catalog previews and screenshots | Ready |
| System UI | Tested overlay workflow and status-first preview | Needs testing |
| Settings UI | Settings shortcuts/support + tested overlay workflow | Needs testing |
| Android framework | Framework visual preview + tested overlay workflow | Needs testing |
| Battery/performance | Diagnostics and troubleshooting guidance | Safe; no fake booster claims |
| Rootd system files | Systemless-only module path + support reports | Safe by default; advanced overlays need testing |
| Rollback | Uninstall script + customer guide | Documented |

---

## 📥 Install a module ZIP

1. Download a module ZIP from a trusted artifact or GitHub Release.
2. Open **Magisk**, **KernelSU**, or **APatch**.
3. Flash the module ZIP.
4. Reboot.
5. Test on one matching device before wider distribution.
6. If something fails, use the documented rollback/recovery path before attempting further changes.

---

## ⚠️ Safety & distribution

Only distribute themes, wallpapers, fonts, icons, sounds, previews, and UI resources that you **created, own, or have permission to share**.

- Do not claim a feature works on devices where it has not been tested.
- Do not make fake battery, RAM, thermal, or performance-boost claims.
- Do not perform direct system partition writes from the APK.
- Keep rollback and recovery guidance available for advanced customization.

See [`docs/safety.md`](docs/safety.md) and [`docs/permissions.md`](docs/permissions.md).

---

## 📚 Documentation

- [Compatibility](docs/compatibility.md)
- [Android 15 / 16 / 17 support](docs/ANDROID_15_16_17_SUPPORT.md)
- [UI Design System](docs/UI_DESIGN_SYSTEM.md)
- [Rootd System File Support](docs/ROOTD_SYSTEM_FILE_SUPPORT.md)
- [Rootd Customer Foundation](docs/ROOTD_CUSTOMER_FOUNDATION.md)
- [Permissions](docs/permissions.md)
- [Safety](docs/safety.md)
- [Lag Fix Guide](docs/LAG_FIX_GUIDE.md)
- [Problem Solver Matrix](docs/PROBLEM_SOLVER_MATRIX.md)

---

## 📜 GPL reference rule

Oxygen Customizer may be used as an open-source reference for structure, release channels, LSPosed/root flows, and customer-warning patterns. Do **not** copy GPL-3.0 source code into this project unless the project's license and distribution method are GPL-compatible.

---

<div align="center">

**ColorOS Themes Rock · 2026**  
*Customize safely. Know what works. Keep a rollback path.*

</div>
