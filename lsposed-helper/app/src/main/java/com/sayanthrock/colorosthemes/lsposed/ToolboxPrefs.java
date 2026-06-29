package com.sayanthrock.colorosthemes.lsposed;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Shared preference storage for toolbox feature flags and refresh status.
 */
public final class ToolboxPrefs {

    public static final String PREFS = "coloros_toolbox";
    public static final String KEY_LOCKSCREEN = "tweak_lockscreen";
    public static final String KEY_STATUS_BAR = "tweak_status_bar";
    public static final String KEY_QUICK_SETTINGS = "tweak_quick_settings";
    public static final String KEY_LAUNCHER = "tweak_launcher";
    public static final String KEY_SYSTEM_UI = "tweak_system_ui";
    public static final String KEY_WEATHER = "tweak_weather";
    public static final String KEY_LAST_SCOPE_REFRESH = "last_scope_refresh";
    public static final String KEY_LAST_SCOPE_REASON = "last_scope_reason";

    private ToolboxPrefs() {
        // Utility class.
    }

    public static boolean isEnabled(Context context, String key, boolean defaultValue) {
        return prefs(context).getBoolean(key, defaultValue);
    }

    public static void setEnabled(Context context, String key, boolean enabled) {
        prefs(context).edit().putBoolean(key, enabled).apply();
    }

    public static long lastScopeRefresh(Context context) {
        return prefs(context).getLong(KEY_LAST_SCOPE_REFRESH, 0L);
    }

    public static String lastScopeReason(Context context) {
        return prefs(context).getString(KEY_LAST_SCOPE_REASON, "none");
    }

    public static void markScopeRefresh(Context context, String reason) {
        prefs(context).edit()
                .putLong(KEY_LAST_SCOPE_REFRESH, System.currentTimeMillis())
                .putString(KEY_LAST_SCOPE_REASON, reason == null ? "manual" : reason)
                .apply();
    }

    public static String featureReport(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Toolbox features\n");
        builder.append("Lock screen: ").append(isEnabled(context, KEY_LOCKSCREEN, true)).append('\n');
        builder.append("Status bar: ").append(isEnabled(context, KEY_STATUS_BAR, true)).append('\n');
        builder.append("Quick settings: ").append(isEnabled(context, KEY_QUICK_SETTINGS, true)).append('\n');
        builder.append("Launcher: ").append(isEnabled(context, KEY_LAUNCHER, true)).append('\n');
        builder.append("System UI: ").append(isEnabled(context, KEY_SYSTEM_UI, true)).append('\n');
        builder.append("Weather lock screen: ").append(isEnabled(context, KEY_WEATHER, false)).append('\n');
        builder.append("Last scope refresh: ").append(lastScopeRefresh(context)).append('\n');
        builder.append("Last refresh reason: ").append(lastScopeReason(context)).append('\n');
        return builder.toString();
    }

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
}
