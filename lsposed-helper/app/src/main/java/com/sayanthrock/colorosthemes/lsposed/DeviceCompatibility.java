package com.sayanthrock.colorosthemes.lsposed;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.Locale;

/**
 * Runtime compatibility helper for OPPO, OnePlus, realme, Android 15/16/17,
 * and customer-safe Rootd/systemless module status.
 */
public final class DeviceCompatibility {

    public static final int API_ANDROID_15 = 35;
    public static final int API_ANDROID_16 = 36;
    public static final int API_ANDROID_17 = 37;

    private static final String[] ROOT_MANAGER_PACKAGES = new String[] {
            "com.topjohnwu.magisk",
            "me.weishu.kernelsu",
            "me.bmax.apatch"
    };

    private static final String[] LSPOSED_PACKAGES = new String[] {
            "org.lsposed.manager",
            "org.lsposed.manager.debug"
    };

    private DeviceCompatibility() {
        // Utility class.
    }

    public static String brand() {
        return safe(Build.BRAND);
    }

    public static String manufacturer() {
        return safe(Build.MANUFACTURER);
    }

    public static String model() {
        return safe(Build.MODEL);
    }

    public static String device() {
        return safe(Build.DEVICE);
    }

    public static String release() {
        return safe(Build.VERSION.RELEASE);
    }

    public static int sdkInt() {
        return Build.VERSION.SDK_INT;
    }

    public static boolean isOplusFamilyDevice() {
        String brand = brand().toLowerCase(Locale.US);
        String manufacturer = manufacturer().toLowerCase(Locale.US);
        return containsAny(brand, "oppo", "oneplus", "realme")
                || containsAny(manufacturer, "oppo", "oneplus", "realme", "oplus");
    }

    public static String skinLabel() {
        if (!isOplusFamilyDevice()) {
            return "Generic Android fallback";
        }
        String brand = brand().toLowerCase(Locale.US);
        if (brand.contains("oneplus")) {
            return "OxygenOS / ColorOS-based OnePlus";
        }
        if (brand.contains("realme")) {
            return "realme UI / ColorOS-based realme";
        }
        if (brand.contains("oppo")) {
            return "ColorOS OPPO";
        }
        return "OPlus family ROM";
    }

    public static String androidGenerationLabel() {
        int sdk = sdkInt();
        if (sdk >= API_ANDROID_17) {
            return "Android 17+ / SDK " + sdk;
        }
        if (sdk == API_ANDROID_16) {
            return "Android 16 / SDK 36";
        }
        if (sdk == API_ANDROID_15) {
            return "Android 15 / SDK 35";
        }
        if (sdk > API_ANDROID_17) {
            return "New Android SDK " + sdk;
        }
        return "Android " + release() + " / SDK " + sdk;
    }

    public static String androidSupportStatus() {
        int sdk = sdkInt();
        if (sdk == API_ANDROID_15) {
            return "Supported modern target. Use normal customer-safe features first.";
        }
        if (sdk == API_ANDROID_16) {
            return "Forward-compatible support. Test OEM theme, overlay, and LSPosed features per device.";
        }
        if (sdk >= API_ANDROID_17) {
            return "Future/preview-safe support. Keep advanced features labelled Needs testing until verified.";
        }
        if (sdk >= Build.VERSION_CODES.S) {
            return "Modern fallback support. Some Android 15+ labels will not apply.";
        }
        return "Legacy fallback. Use wallpaper, support report, and documentation features only.";
    }

    public static boolean hasRootManager(Context context) {
        return firstInstalledPackage(context, ROOT_MANAGER_PACKAGES).length() > 0;
    }

    public static boolean hasLsposedManager(Context context) {
        return firstInstalledPackage(context, LSPOSED_PACKAGES).length() > 0;
    }

    public static String rootManagerLabel(Context context) {
        String installed = firstInstalledPackage(context, ROOT_MANAGER_PACKAGES);
        if (installed.length() == 0) {
            return "No Magisk, KernelSU, or APatch manager package detected.";
        }
        return "Detected root manager: " + installed;
    }

    public static String lsposedLabel(Context context) {
        String installed = firstInstalledPackage(context, LSPOSED_PACKAGES);
        if (installed.length() == 0) {
            return "LSPosed manager not detected. Hook features must stay labelled Needs testing.";
        }
        return "Detected LSPosed manager: " + installed;
    }

    public static String moduleSafetyPolicy() {
        return "Systemless-only policy: mount replacement files through the module path, keep rollback simple, and never directly rewrite /system, /vendor, /product, or /system_ext from the APK.";
    }

    public static String mountTargetSummary() {
        return "Theme mount target: /system_ext/media/themeInner\n"
                + "Overlay targets: android, com.android.systemui, com.android.settings\n"
                + "Mode: status-first, safe-disable ready, rollback documented";
    }

    public static String rootdChecklist(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Rootd System Health\n");
        builder.append(rootManagerLabel(context)).append('\n');
        builder.append(lsposedLabel(context)).append('\n');
        builder.append("OPlus family device: ").append(isOplusFamilyDevice()).append('\n');
        builder.append("Android status: ").append(androidSupportStatus()).append('\n');
        builder.append(moduleSafetyPolicy()).append('\n');
        builder.append(mountTargetSummary()).append('\n');
        return builder.toString();
    }

    public static String supportReport(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Device Compatibility Report\n");
        builder.append("Brand: ").append(brand()).append('\n');
        builder.append("Manufacturer: ").append(manufacturer()).append('\n');
        builder.append("Model: ").append(model()).append('\n');
        builder.append("Device: ").append(device()).append('\n');
        builder.append("Android: ").append(androidGenerationLabel()).append('\n');
        builder.append("ROM skin: ").append(skinLabel()).append('\n');
        builder.append("OPlus family detected: ").append(isOplusFamilyDevice()).append('\n');
        builder.append("Support status: ").append(androidSupportStatus()).append('\n');
        builder.append(rootManagerLabel(context)).append('\n');
        builder.append(lsposedLabel(context)).append('\n');
        return builder.toString();
    }

    private static String firstInstalledPackage(Context context, String[] packageNames) {
        PackageManager packageManager = context.getPackageManager();
        for (String packageName : packageNames) {
            if (isPackageInstalled(packageManager, packageName)) {
                return packageName;
            }
        }
        return "";
    }

    private static boolean isPackageInstalled(PackageManager packageManager, String packageName) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0));
            } else {
                packageManager.getPackageInfo(packageName, 0);
            }
            return true;
        } catch (PackageManager.NameNotFoundException ignored) {
            return false;
        }
    }

    private static boolean containsAny(String value, String... needles) {
        for (String needle : needles) {
            if (value.contains(needle)) {
                return true;
            }
        }
        return false;
    }

    private static String safe(String value) {
        return value == null || value.trim().isEmpty() ? "unknown" : value.trim();
    }
}
