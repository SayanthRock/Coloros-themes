# Status, Settings, and Diagnostics Code Pack

The GitHub tool currently blocks direct writes into the Android source path. This document contains the first safe APK-side helper classes to copy into the helper app manually.

Target base package:

```text
lsposed-helper/app/src/main/java/com/sayanthrock/colorosthemes/lsposed/rootd/
```

---

## 1. Status Helper

Target file:

```text
rootd/core/FeatureStatus.java
```

```java
package com.sayanthrock.colorosthemes.lsposed.rootd.core;

public final class FeatureStatus {
    private final String name;
    private final String status;
    private final String message;
    private final boolean ready;

    public FeatureStatus(String name, String status, String message, boolean ready) {
        this.name = name;
        this.status = status;
        this.message = message;
        this.ready = ready;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isReady() {
        return ready;
    }

    public static FeatureStatus ready(String name, String message) {
        return new FeatureStatus(name, "Ready", message, true);
    }

    public static FeatureStatus disabled(String name, String message) {
        return new FeatureStatus(name, "Disabled", message, false);
    }

    public static FeatureStatus needsTesting(String name, String message) {
        return new FeatureStatus(name, "Needs testing", message, false);
    }

    public static FeatureStatus notAvailable(String name, String message) {
        return new FeatureStatus(name, "Not available", message, false);
    }
}
```

---

## 2. Settings Helper

Target file:

```text
rootd/settings/FeatureFlags.java
```

```java
package com.sayanthrock.colorosthemes.lsposed.rootd.settings;

public final class FeatureFlags {
    public static final String THEME_ENGINE = "theme_engine_enabled";
    public static final String ICON_PACK = "icon_pack_enabled";
    public static final String WALLPAPER_MODULE = "wallpaper_module_enabled";
    public static final String LOCKSCREEN_MODULE = "lockscreen_module_enabled";
    public static final String SYSTEMUI_MODULE = "systemui_module_enabled";
    public static final String ADVANCED_FEATURES = "advanced_features_enabled";
    public static final String EXPERIMENTAL_FEATURES = "experimental_features_enabled";

    private FeatureFlags() {
    }

    public static boolean defaultValue(String key) {
        if (EXPERIMENTAL_FEATURES.equals(key)) return false;
        if (ADVANCED_FEATURES.equals(key)) return false;
        return true;
    }
}
```

Target file:

```text
rootd/settings/SettingsStore.java
```

```java
package com.sayanthrock.colorosthemes.lsposed.rootd.settings;

import android.content.Context;
import android.content.SharedPreferences;

public final class SettingsStore {
    private static final String PREFS = "rootd_customer_settings";

    private SettingsStore() {
    }

    public static boolean getFlag(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, FeatureFlags.defaultValue(key));
    }

    public static void setFlag(Context context, String key, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(key, value).apply();
    }
}
```

---

## 3. Diagnostics Helper

Target file:

```text
rootd/support/DiagnosticsReport.java
```

```java
package com.sayanthrock.colorosthemes.lsposed.rootd.support;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;

public final class DiagnosticsReport {
    private DiagnosticsReport() {
    }

    public static String build(Context context, String currentTheme, String lastApplyResult, String lastError) {
        StringBuilder report = new StringBuilder();
        report.append("Rootd Diagnostics\n");
        report.append("App version: ").append(appVersion(context)).append('\n');
        report.append("Brand: ").append(Build.BRAND).append('\n');
        report.append("Model: ").append(Build.MODEL).append('\n');
        report.append("Android: ").append(Build.VERSION.RELEASE).append(" / API ").append(Build.VERSION.SDK_INT).append('\n');
        report.append("Current theme: ").append(nullSafe(currentTheme)).append('\n');
        report.append("Last apply result: ").append(nullSafe(lastApplyResult)).append('\n');
        report.append("Last error: ").append(nullSafe(lastError)).append('\n');
        return report.toString();
    }

    private static String appVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName + " (" + info.versionCode + ")";
        } catch (Exception ignored) {
            return "Unknown";
        }
    }

    private static String nullSafe(String value) {
        return value == null || value.trim().isEmpty() ? "None" : value;
    }
}
```

---

## Implementation order

1. Copy `FeatureStatus.java` first.
2. Build APK.
3. Copy `FeatureFlags.java` and `SettingsStore.java`.
4. Build APK.
5. Copy `DiagnosticsReport.java`.
6. Build APK.
7. Connect the helpers to the existing Support and Settings pages.

## Safety note

These classes are APK-side only. They do not modify system files and do not perform privileged actions by themselves.
