package com.sayanthrock.colorosthemes.lsposed;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.File;
import java.util.Locale;

/**
 * Device and setup guidance for root module users.
 *
 * This class is intentionally conservative. It does not write system files,
 * execute root commands, or enable hooks by itself. It prepares clear guidance,
 * ROM profile text, and support report details that users can verify first.
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
        builder.append("\nAfter changing scopes, force stop System UI only if you know the device recovers cleanly. The safer path is reboot.");
        return builder.toString();
    }

    public static String romModuleProfile(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("ROM Module Profile\n");
        builder.append("Profile ID: ").append(moduleProfileId()).append('\n');
        builder.append("ROM family: ").append(romFamily()).append('\n');
        builder.append("Root manager: ").append(rootManagerStatus(context)).append('\n');
        builder.append("LSPosed: ").append(lsposedStatus(context)).append('\n');
        builder.append("Brand: ").append(safe(Build.BRAND)).append('\n');
        builder.append("Manufacturer: ").append(safe(Build.MANUFACTURER)).append('\n');
        builder.append("Model: ").append(safe(Build.MODEL)).append('\n');
        builder.append("Device: ").append(safe(Build.DEVICE)).append('\n');
        builder.append("Product: ").append(safe(Build.PRODUCT)).append('\n');
        builder.append("Build display: ").append(safe(Build.DISPLAY)).append('\n');
        builder.append("Android: ").append(safe(Build.VERSION.RELEASE)).append(" / SDK ").append(Build.VERSION.SDK_INT).append('\n');
        builder.append("Module target path: /system_ext/media/themeInner/\n");
        builder.append("Primary LSPosed targets: android, com.android.systemui, com.android.settings\n");
        builder.append("UI focus: lock screen, status bar, quick settings, launcher, System UI, supported weather lock screen\n");
        return builder.toString();
    }

    public static String moduleInstallChecklist(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("ColorOS Themes Rock install checklist\n");
        builder.append("1. Generate module ZIP using profile: ").append(moduleProfileId()).append('\n');
        builder.append("2. Flash ZIP in Magisk, KernelSU, APatch, or a compatible manager.\n");
        builder.append("3. Enable the helper APK in LSPosed or a compatible Xposed framework.\n");
        builder.append("4. Scope only required packages:\n").append(requiredScopesText()).append('\n');
        builder.append("5. Reboot once after flashing or changing scope.\n");
        builder.append("6. Reopen the app, copy the support report, and test UI areas one by one.\n");
        builder.append("\nDetected state:\n").append(report(context));
        return builder.toString();
    }

    public static String report(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Root Setup Report\n");
        builder.append("Root manager: ").append(rootManagerStatus(context)).append('\n');
        builder.append("Root manager package: ").append(valueOrNone(firstInstalledPackage(context, ROOT_MANAGER_PACKAGES))).append('\n');
        builder.append("Root binary: ").append(rootBinaryStatus()).append('\n');
        builder.append("LSPosed manager: ").append(lsposedStatus(context)).append('\n');
        builder.append("LSPosed package: ").append(valueOrNone(firstInstalledPackage(context, LSPOSED_MANAGER_PACKAGES))).append('\n');
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
                // Package visibility can vary on Android 11+.
            }
        }
        return null;
    }

    private static String valueOrNone(String value) {
        return value == null || value.trim().isEmpty() ? "none detected" : value;
    }

    private static String safe(String value) {
        return value == null || value.trim().isEmpty() ? "unknown" : value.trim();
    }
}
