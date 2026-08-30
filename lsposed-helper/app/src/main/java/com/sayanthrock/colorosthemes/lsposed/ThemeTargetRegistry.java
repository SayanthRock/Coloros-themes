package com.sayanthrock.colorosthemes.lsposed;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry of repository-owned, verified customization targets.
 *
 * The customer can select a target from this registry, but cannot supply an
 * arbitrary filesystem path. Paths are relative module/overlay destinations
 * only; direct system-partition writes are never exposed by this API.
 */
public final class ThemeTargetRegistry {
    public static final String THEME_STORE_PACKAGE = "com.oplus.themestore";
    public static final String DEFAULT_TARGET_ID = "theme_store_overlay";

    private static final String PREFS = "theme_target_registry";
    private static final String KEY_SELECTED_TARGET = "selected_target";

    public static final class Target {
        public final String id;
        public final String label;
        public final String packageName;
        public final String relativePath;
        public final String capability;
        public final boolean verified;

        Target(String id, String label, String packageName, String relativePath,
               String capability, boolean verified) {
            this.id = id;
            this.label = label;
            this.packageName = packageName;
            this.relativePath = relativePath;
            this.capability = capability;
            this.verified = verified;
        }
    }

    private static final Map<String, Target> TARGETS;
    static {
        Map<String, Target> map = new LinkedHashMap<>();
        map.put(DEFAULT_TARGET_ID, new Target(
                DEFAULT_TARGET_ID,
                "Theme Store overlay",
                THEME_STORE_PACKAGE,
                "system_ext/media/themeInner",
                "owned_assets_overlay",
                true));
        map.put("theme_store_colors", new Target(
                "theme_store_colors",
                "Theme Store colors.xml",
                THEME_STORE_PACKAGE,
                "system_ext/media/themeInner/colors.xml",
                "owned_asset_file",
                true));
        map.put("theme_store_assets", new Target(
                "theme_store_assets",
                "Theme Store assets",
                THEME_STORE_PACKAGE,
                "system_ext/media/themeInner/assets",
                "owned_asset_directory",
                true));
        TARGETS = Collections.unmodifiableMap(map);
    }

    private ThemeTargetRegistry() { }

    public static List<Target> all() {
        return Collections.unmodifiableList(Arrays.asList(TARGETS.values().toArray(new Target[0])));
    }

    public static Target get(String id) {
        return id == null ? null : TARGETS.get(id);
    }

    public static boolean isAllowed(String id) {
        Target target = get(id);
        return target != null && target.verified;
    }

    public static Target selected(Context context) {
        String id = prefs(context).getString(KEY_SELECTED_TARGET, DEFAULT_TARGET_ID);
        Target target = get(id);
        return target != null && target.verified ? target : get(DEFAULT_TARGET_ID);
    }

    public static boolean select(Context context, String id) {
        if (!isAllowed(id)) return false;
        prefs(context).edit().putString(KEY_SELECTED_TARGET, id).apply();
        return true;
    }

    public static String selectedId(Context context) {
        return selected(context).id;
    }

    public static String selectedLabel(Context context) {
        return selected(context).label;
    }

    public static String selectedPackage(Context context) {
        return selected(context).packageName;
    }

    public static String selectedRelativePath(Context context) {
        return selected(context).relativePath;
    }

    public static String status(Context context) {
        Target target = selected(context);
        return target.label + " | " + target.packageName + " | "
                + target.relativePath + " | "
                + (target.verified ? "Verified" : "Needs device test");
    }

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
}
