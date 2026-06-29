package com.sayanthrock.colorosthemes.lsposed;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.File;
import java.util.Locale;

/**
 * Device and setup guidance for root module users.
 */
public final class RootSetupAssistant {

    public static final String[] ROOT_MANAGER_PACKAGES = new String[] {
            "com.topjohnwu.magisk",
            "me.weishu.kernelsu",
            "io.github.rifsxd.ksunext",
            "me.bmax.apatch"
    };

    public static final String[] LSPOSED_MANAGER_PACKAGES = new String[] {
            "org.lsposed.manager",
            "io.github.lsposed.manager",
            "io.github.libxposed.manager"
    };

    private static final String[] COMMON_SU_PATHS = new String[] {
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/debug_ramdisk/su",
            "/data/adb/ksu/bin/su",
            "/data/adb/ap/bin/su",
            "/data/adb/magisk/busybox"
    };

    private RootSetupAssistant() {
        // Utility class.
    }

    public static boolean hasKnownRootManager(Context context) {
        return firstInstalledPackage(context, ROOT_MANAGER_PACKAGES) != null;
    }

    public static boolean hasLsposedManager(Context context) {
        return firstInstalledPackage(context, LSPOSED_MANAGER_PACKAGES) != null;
    }

    public static String rootManagerStatus(Context context) {
        String packageName = firstInstalledPackage(context, ROOT_MANAGER_PACKAGES);
        if (packageName == null) {
            return "Needs setup";
        }
        if ("com.topjohnwu.magisk".equals(packageName)) {
            return "Magisk found";
        }
        if ("me.weishu.kernelsu".equals(packageName) || "io.github.rifsxd.ksunext".equals(packageName)) {
            return "KernelSU found";
        }
        if ("me.bmax.apatch".equals(packageName)) {
            return "APatch found";
        }
        return "Root manager found";
    }

    public static String lsposedStatus(Context context) {
        return hasLsposedManager(context) ? "Manager found" : "Needs LSPosed";
    }

    public static boolean hasRootBinary() {
        for (String path : COMMON_SU_PATHS) {
            if (new File(path).exists()) {
                return true;
            }
        }
        return false;
    }

    public static String rootBinaryStatus() {
        return hasRootBinary() ? "su path found" : "No su path found";
    }

    public static String romFamily() {
        String text = (safe(Build.BRAND) + " "
                + safe(Build.MANUFACTURER) + " "
                + safe(Build.DISPLAY) + " "
                + safe(Build.PRODUCT)).toLowerCase(Locale.US);

        if (text.contains("realme")) {
            return "realme UI";
        }
        if (text.contains("oneplus")) {
            return "OxygenOS or ColorOS based";
        }
        if (text.contains("oppo") || text.contains("oplus") || text.contains("coloros")) {
            return "ColorOS";
        }
        return "Generic Android";
    }

    public static String moduleProfileId() {
        String family = romFamily().toLowerCase(Locale.US);
        String base;
        if (family.contains("realme")) {
            base = "realme-ui";
        } else if (family.contains("oxygenos") || family.contains("oneplus")) {
            base = "oneplus-oxygenos-coloros";
        } else if (family.contains("coloros")) {
            base = "oplus-coloros";
        } else {
            base = "generic-android";
        }
        return base + "-sdk" + Build.VERSION.SDK_INT;
    }

    public static boolean maybeSupportsWeather() {
        String text = (safe(Build.BRAND) + " "
                + safe(Build.MANUFACTURER) + " "
                + safe(Build.DISPLAY) + " "
                + safe(Build.PRODUCT)).toLowerCase(Locale.US);
        return text.contains("oppo")
                || text.contains("oplus")
                || text.contains("realme")
                || text.contains("oneplus")
                || text.contains("coloros");
    }

    public static String weatherSupportStatus() {
        return maybeSupportsWeather() ? "ROM check needed" : "Not detected";
    }

    public static String requiredScopesText() {
        StringBuilder builder = new StringBuilder();
        builder.append("Enable this module only for packages that exist on the device:\n");
        builder.append("- android\n");
        builder.append("- com.android.systemui\n");
        builder.append("- com.android.settings\n");
        builder.append("- com.oplus.systemui or com.coloros.systemui when present\n");
        builder.append("- com.oplus.launcher, com.coloros.launcher, com.realme.launcher, or com.android.launcher3 when present\n");
        builder.append("- com.heytap.weather, com.coloros.weather2, com.oplus.weather, or com.realme.weather when lock screen weather is supported\n");
        builder.append("\nAfter changing scopes, the safer path is reboot.");
        return builder.toString();
    }

    public static String supportSummary(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Root manager: ").append(rootManagerStatus(context)).append('\n');
        builder.append("Root binary: ").append(rootBinaryStatus()).append('\n');
        builder.append("LSPosed: ").append(lsposedStatus(context)).append('\n');
        builder.append("ROM family: ").append(romFamily()).append('\n');
        builder.append("Module profile: ").append(moduleProfileId()).append('\n');
        builder.append("Weather lock screen: ").append(weatherSupportStatus()).append('\n');
        return builder.toString();
    }

    private static String firstInstalledPackage(Context context, String[] packageNames) {
        PackageManager packageManager = context.getPackageManager();
        for (String packageName : packageNames) {
            try {
                packageManager.getPackageInfo(packageName, 0);
                return packageName;
            } catch (PackageManager.NameNotFoundException ignored) {
                // Try next known package.
            } catch (RuntimeException ignored) {
                // Package visibility can vary.
            }
        }
        return null;
    }

    private static String safe(String value) {
        return value == null || value.trim().isEmpty() ? "unknown" : value.trim();
    }
}
