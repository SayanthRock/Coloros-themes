package com.sayanthrock.colorosthemes.lsposed;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import java.io.File;

/**
 * Read-only permission and root status helper.
 *
 * It detects capability state and never attempts to gain root automatically.
 */
public final class RootPermissionManager {

    private static final String[] SU_PATHS = new String[] {
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/system/bin/.ext/su",
            "/system/usr/we-need-root/su",
            "/data/adb/magisk/busybox"
    };

    private RootPermissionManager() {
        // Utility class.
    }

    public static boolean isRootDetected() {
        for (String path : SU_PATHS) {
            if (new File(path).exists()) {
                return true;
            }
        }
        return false;
    }

    public static boolean canManageAllFiles() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return true;
    }

    public static boolean canInstallUnknownApps(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return context.getPackageManager().canRequestPackageInstalls();
        }
        return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 1;
    }

    public static String rootStatusMessage() {
        if (isRootDetected()) {
            return "Root permission detected. Advanced system-file options can be enabled after user confirmation.";
        }
        return "No Root permission\nRoot permission is not detected, some functions will not be available!";
    }

    public static String permissionReport(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Root detected: ").append(isRootDetected()).append('\n');
        builder.append("Manage all files: ").append(canManageAllFiles()).append('\n');
        builder.append("Allow from this source: ").append(canInstallUnknownApps(context)).append('\n');
        builder.append("Android SDK: ").append(Build.VERSION.SDK_INT).append('\n');
        return builder.toString();
    }
}
