# Important Testing Checklist

Use this checklist before calling any feature complete.

## APK build

- [ ] Debug APK builds successfully.
- [ ] Release APK output is created.
- [ ] APK installs on Android 15 or newer.
- [ ] App opens without crash.
- [ ] Wallpaper picker opens.
- [ ] Home wallpaper apply works.
- [ ] Lock wallpaper apply works.
- [ ] Support report copy works.
- [ ] Support report share works.

## App UI

- [ ] Dark mode option is visible.
- [ ] Light mode option is visible.
- [ ] System mode option is visible.
- [ ] Accent color options are visible.
- [ ] Background changes correctly.
- [ ] Cards change correctly.
- [ ] Text remains readable.
- [ ] Bottom navigation remains clear.
- [ ] Buttons and badges use the selected accent.

## Feature status

- [ ] Working features are marked `Working`.
- [ ] Settings links are marked `Safe shortcut`.
- [ ] Preview-only features are marked `Preview only`.
- [ ] Advanced features are marked `Needs testing`.
- [ ] Unavailable features are marked `Not available`.
- [ ] No unfinished option is hidden without explanation.

## Device testing

| Brand | System | Status |
|---|---|---|
| OPPO | ColorOS 15+ | Needs testing |
| OnePlus | OxygenOS / ColorOS based | Needs testing |
| realme | realme UI 6+ | Needs testing |

## Important current items

- [ ] Connect `AppAppearanceManager` to `MainActivity`.
- [ ] Connect `FreeCoreFeatures` to a visible app page.
- [ ] Apply final module wiring patch.
- [ ] Run GitHub Actions build.
- [ ] Install and test APK on a real phone.
- [ ] Check every visible feature card.
- [ ] Keep unfinished options visible with `Needs testing` status.

## Release rule

Do not publish a feature as complete until it passes real-device testing. If it is not tested, keep it visible and label it `Needs testing`.
