# APK-Side Helpers Implementation Blueprint

This document defines the source files to add under the Android helper app once source-file creation is available.

## Target package

```text
lsposed-helper/app/src/main/java/com/sayanthrock/colorosthemes/lsposed/rootd/
```

## 1. Status helpers

### File targets

```text
rootd/core/RootFeatureStatus.java
rootd/core/LsposedFeatureStatus.java
rootd/core/DeviceDetector.java
rootd/core/SafetyManager.java
```

### Purpose

- Show clear customer-facing status labels.
- Keep advanced actions disabled until requirements are met.
- Avoid silent failure.
- Avoid unsafe automatic actions.

### Required labels

- Ready
- Disabled
- Not available
- Needs testing
- Scope required
- Advanced

## 2. Settings helpers

### File targets

```text
rootd/settings/SettingsStore.java
rootd/settings/FeatureFlags.java
```

### Purpose

- Store safe user preferences.
- Keep experimental features disabled by default.
- Separate normal customer tools from advanced options.

### Required flags

- theme_engine_enabled
- icon_pack_enabled
- wallpaper_module_enabled
- lockscreen_module_enabled
- systemui_module_enabled
- advanced_features_enabled
- experimental_features_enabled

## 3. Diagnostics helpers

### File targets

```text
rootd/support/DiagnosticsReport.java
rootd/support/CustomerSupportHelper.java
```

### Purpose

- Generate a customer support report.
- Help users share device and app state when something fails.
- Avoid private user data.

### Report fields

- App version
- Device model
- Android version
- Brand
- Current theme
- Last apply result
- Feature status labels
- Last error message

## 4. Theme safety helpers

### File targets

```text
rootd/theme/ThemeValidator.java
rootd/theme/ThemeRollback.java
rootd/system/SystemFileGuard.java
rootd/system/SafePathManager.java
```

### Purpose

- Validate theme packages before apply.
- Block unsupported paths.
- Keep backup and restore available.
- Prevent partial changes after failure.

## Build rules

- Add one module at a time.
- Build after each step.
- Do not connect risky actions before validation and rollback exist.
- Customer-visible labels must remain clear.

## Next manual code step

Start with status helpers first, then settings, then diagnostics. These are safe because they do not perform modifications by themselves.
