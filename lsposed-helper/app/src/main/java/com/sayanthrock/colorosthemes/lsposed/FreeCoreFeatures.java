package com.sayanthrock.colorosthemes.lsposed;

/**
 * Customer-facing free core feature list.
 *
 * The APK can use this list to show all options with honest status labels.
 */
public final class FreeCoreFeatures {
    private FreeCoreFeatures() {
        // Utility class.
    }

    public static String[] workingNow() {
        return new String[] {
                "Wallpaper picker",
                "Wallpaper preview",
                "Home wallpaper apply",
                "Lock wallpaper apply",
                "Device report",
                "Support report copy",
                "Support report share",
                "Settings shortcuts",
                "Battery guidance",
                "Rollback guidance"
        };
    }

    public static String[] readyForUiWiring() {
        return new String[] {
                "Dark mode",
                "Light mode",
                "System mode",
                "Accent presets"
        };
    }

    public static String[] needsTesting() {
        return new String[] {
                "Clock style",
                "Battery style",
                "Quick tiles layout",
                "AOD style",
                "Navigation style",
                "Theme asset layer",
                "Icon layer",
                "Lock layer"
        };
    }

    public static String report() {
        StringBuilder builder = new StringBuilder();
        builder.append("Free Core Features\n");
        builder.append("Working now: ").append(workingNow().length).append('\n');
        builder.append("Ready for UI wiring: ").append(readyForUiWiring().length).append('\n');
        builder.append("Needs testing: ").append(needsTesting().length).append('\n');
        return builder.toString();
    }
}
