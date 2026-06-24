package com.sayanthrock.colorosthemes.lsposed;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

/**
 * Central appearance preference and palette resolver for the helper APK.
 *
 * MainActivity should use this class instead of hardcoded color constants so
 * Dark, Light, and System color modes actually repaint the app.
 */
public final class AppAppearanceManager {
    public static final String MODE_SYSTEM = "system";
    public static final String MODE_DARK = "dark";
    public static final String MODE_LIGHT = "light";

    public static final String ACCENT_DESERT_SAND = "desert_sand";
    public static final String ACCENT_OPLUS_GREEN = "oplus_green";
    public static final String ACCENT_OXYGEN_BLUE = "oxygen_blue";
    public static final String ACCENT_ROCK_GOLD = "rock_gold";

    private static final String PREFS = "coloros_customizer_ui";
    private static final String KEY_MODE = "appearance_mode";
    private static final String KEY_ACCENT = "appearance_accent";

    private AppAppearanceManager() {
        // Utility class.
    }

    public static String mode(Context context) {
        return prefs(context).getString(KEY_MODE, MODE_SYSTEM);
    }

    public static void setMode(Context context, String mode) {
        String safeMode = MODE_SYSTEM;
        if (MODE_DARK.equals(mode) || MODE_LIGHT.equals(mode) || MODE_SYSTEM.equals(mode)) {
            safeMode = mode;
        }
        prefs(context).edit().putString(KEY_MODE, safeMode).apply();
    }

    public static String accent(Context context) {
        return prefs(context).getString(KEY_ACCENT, ACCENT_DESERT_SAND);
    }

    public static void setAccent(Context context, String accent) {
        String safeAccent = ACCENT_DESERT_SAND;
        if (ACCENT_OPLUS_GREEN.equals(accent)
                || ACCENT_OXYGEN_BLUE.equals(accent)
                || ACCENT_ROCK_GOLD.equals(accent)
                || ACCENT_DESERT_SAND.equals(accent)) {
            safeAccent = accent;
        }
        prefs(context).edit().putString(KEY_ACCENT, safeAccent).apply();
    }

    public static boolean isDarkResolved(Context context) {
        String mode = mode(context);
        if (MODE_DARK.equals(mode)) {
            return true;
        }
        if (MODE_LIGHT.equals(mode)) {
            return false;
        }
        int flags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return flags == Configuration.UI_MODE_NIGHT_YES;
    }

    public static Palette palette(Context context) {
        boolean dark = isDarkResolved(context);
        int accent = accentColor(accent(context));
        if (dark) {
            return new Palette(
                    0xFF0F0F10,
                    0xE6151517,
                    0xB81A1A1D,
                    0xAA242429,
                    accent,
                    0xFFF0D2A8,
                    0x44E2B884,
                    0xFFF5F2EA,
                    0xFFB9B1A3,
                    0x4DFFFFFF,
                    0xFF8FD694,
                    0xFFFFCC66
            );
        }
        return new Palette(
                0xFFF7F3EA,
                0xF2FFF9F0,
                0xEFFFFFFF,
                0xFFEFE7D8,
                accent,
                0xFF5E4524,
                0x33E2B884,
                0xFF17130D,
                0xFF675D4F,
                0x33937A4F,
                0xFF2E7D32,
                0xFF936300
        );
    }

    public static String report(Context context) {
        return "Appearance\n"
                + "Mode: " + mode(context) + "\n"
                + "Resolved dark: " + isDarkResolved(context) + "\n"
                + "Accent: " + accent(context) + "\n";
    }

    private static int accentColor(String accent) {
        if (ACCENT_OPLUS_GREEN.equals(accent)) {
            return 0xFF22C55E;
        }
        if (ACCENT_OXYGEN_BLUE.equals(accent)) {
            return 0xFF3B82F6;
        }
        if (ACCENT_ROCK_GOLD.equals(accent)) {
            return 0xFFF5C451;
        }
        return 0xFFE2B884;
    }

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public static final class Palette {
        public final int bg;
        public final int navBg;
        public final int card;
        public final int cardSoft;
        public final int accent;
        public final int accentSoft;
        public final int accentGlow;
        public final int text;
        public final int muted;
        public final int border;
        public final int success;
        public final int warning;

        private Palette(int bg, int navBg, int card, int cardSoft, int accent,
                        int accentSoft, int accentGlow, int text, int muted,
                        int border, int success, int warning) {
            this.bg = bg;
            this.navBg = navBg;
            this.card = card;
            this.cardSoft = cardSoft;
            this.accent = accent;
            this.accentSoft = accentSoft;
            this.accentGlow = accentGlow;
            this.text = text;
            this.muted = muted;
            this.border = border;
            this.success = success;
            this.warning = warning;
        }
    }
}
