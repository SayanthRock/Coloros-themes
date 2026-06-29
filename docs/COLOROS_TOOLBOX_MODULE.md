# ColorOS Toolbox Module

ColorOS Toolbox is the LSPosed/Xposed-facing helper app inside this project.

## Goals

- Keep the project free and community-focused.
- Provide a lightweight settings screen for practical tweaks.
- Make scope requirements clear before users enable anything risky.
- Keep refresh and re-optimization steps visible after system updates.
- Avoid fake premium wording, ads, or forced unlocks.

## Main tweak groups

- Lock screen tweaks
- Status bar tweaks
- Quick settings tweaks
- Launcher tweaks
- System UI tweaks
- Weather lock screen helpers where the ROM exposes compatible packages

## Safe refresh flow

When a ROM update breaks a tweak, the helper app should not silently force changes. Instead it should:

1. Mark that the user requested a refresh.
2. Show a checklist for reopening LSPosed.
3. Ask the user to verify the module is still enabled.
4. Ask the user to recheck package scopes.
5. Recommend a reboot after scope changes.
6. Let the user copy a support report.

## Update and changelog support

The helper app includes a basic update checker that reads `latestStable.json` from the public repository. The metadata can include:

- version
- versionCode
- channel
- releaseLevel
- safetyMessage
- changelog entries

## Localization

The main toolbox screen now uses Android string resources and includes a Malayalam scaffold in `values-ml`. Future UI work should move older hardcoded text into resources as well.

## Hook policy

The Xposed hook entry remains conservative on purpose. ColorOS, OxygenOS, and realme UI private classes can change across builds. The module should clearly label tweaks that need real-device testing before they are treated as stable.
