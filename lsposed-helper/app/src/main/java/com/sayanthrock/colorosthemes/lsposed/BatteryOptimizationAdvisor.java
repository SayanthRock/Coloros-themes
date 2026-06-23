package com.sayanthrock.colorosthemes.lsposed;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Safe, reversible battery guidance for OPPO, OnePlus, and realme devices.
 *
 * This class intentionally avoids hidden APIs, forced app killing, wakelock abuse,
 * private OEM services, or root-only battery hacks. The APK should guide users to
 * public Android/OEM settings instead of making fake performance claims.
 */
public final class BatteryOptimizationAdvisor {

    private BatteryOptimizationAdvisor() {
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

    public static String androidVersion() {
        return "Android " + safe(Build.VERSION.RELEASE) + " / SDK " + Build.VERSION.SDK_INT;
    }

    public static boolean isOppoFamilyDevice() {
        String brand = brand().toLowerCase(Locale.US);
        String manufacturer = manufacturer().toLowerCase(Locale.US);
        return containsAny(brand, "oppo", "oneplus", "realme")
                || containsAny(manufacturer, "oppo", "oneplus", "realme", "oplus");
    }

    public static boolean isIgnoringBatteryOptimizations(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return powerManager != null && powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
    }

    public static String optimizationStatus(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return "Battery optimization whitelist is not required on this Android version.";
        }
        if (isIgnoringBatteryOptimizations(context)) {
            return "This helper APK is already excluded from Android battery optimization.";
        }
        return "This helper APK is still controlled by Android battery optimization.";
    }

    public static List<String> recommendations(Context context) {
        List<String> items = new ArrayList<>();
        items.add("Use Battery Saver only when needed. Keeping it always enabled can reduce smoothness and background sync.");
        items.add("For important apps, open App info > Battery and allow normal background activity only when required.");
        items.add("Keep auto-start/background permissions enabled only for trusted apps that must notify you instantly.");
        items.add("Remove unused live wallpapers, heavy launchers, and always-on overlays if standby drain is high.");
        items.add("Reboot after installing or updating the module, then test battery drain for one full charge cycle.");
        items.add("Do not use random task-killer apps. They often increase battery drain by forcing apps to restart repeatedly.");

        if (isOppoFamilyDevice()) {
            items.add("ColorOS/realme UI/OxygenOS: check Settings > Battery > More settings for sleep standby, optimized night charging, and app battery management.");
        } else {
            items.add("This device is not detected as OPPO, OnePlus, or realme. Use generic Android battery settings only.");
        }

        if (!isIgnoringBatteryOptimizations(context)) {
            items.add("Optional: exclude this helper APK from battery optimization only if LSPosed guidance or support report features must run reliably.");
        }

        return items;
    }

    public static String supportReport(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("ColorOS Themes Rock Battery Report\n");
        builder.append("Brand: ").append(brand()).append('\n');
        builder.append("Manufacturer: ").append(manufacturer()).append('\n');
        builder.append("Model: ").append(model()).append('\n');
        builder.append("Android: ").append(androidVersion()).append('\n');
        builder.append("OPPO family detected: ").append(isOppoFamilyDevice()).append('\n');
        builder.append("Optimization status: ").append(optimizationStatus(context)).append('\n');
        builder.append("Package: ").append(context.getPackageName()).append('\n');
        return builder.toString();
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
