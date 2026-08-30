package com.sayanthrock.colorosthemes.lsposed;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Read-only Rootd capability and permission helper.
 *
 * This class never attempts to gain root and never writes to system partitions.
 * A root binary existing on disk is not treated as usable root: the helper also
 * verifies that a root command can return uid=0.
 */
public final class RootPermissionManager {

    private static final String[] SU_PATHS = new String[] {
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/system/bin/.ext/su",
            "/system/usr/we-need-root/su",
            "/data/adb/magisk/magisk",
            "/data/adb/ksu/bin/ksud",
            "/data/adb/ap/bin/apd"
    };

    private RootPermissionManager() {
        // Utility class.
    }

    public static boolean isRootDetected() {
        return findRootBinary() != null || hasRootProviderProperty();
    }

    /**
     * True only when a root shell actually returns uid=0.
     * The command is read-only.
     */
    public static boolean canExecuteRoot() {
        String[] commands = new String[] {"/system/bin/su", "su"};
        for (String command : commands) {
            Process process = null;
            try {
                process = new ProcessBuilder(command, "-c", "id -u")
                        .redirectErrorStream(true)
                        .start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String output = reader.readLine();
                int exitCode = process.waitFor();
                if (exitCode == 0 && output != null && "0".equals(output.trim())) {
                    return true;
                }
            } catch (IOException | InterruptedException ignored) {
                if (ignored instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
            } finally {
                if (process != null) process.destroy();
            }
        }
        return false;
    }

    public static String rootProvider() {
        if (new File("/data/adb/magisk/magisk").exists()) return "Magisk";
        if (new File("/data/adb/ksu/bin/ksud").exists()) return "KernelSU";
        if (new File("/data/adb/ap/bin/apd").exists()) return "APatch";
        if (isRootDetected()) return "Root provider detected";
        return "Not detected";
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

    /** Rootd is ready only when root is detected and executable. */
    public static boolean isRootdReady() {
        return isRootDetected() && canExecuteRoot();
    }

    public static String rootStatusMessage() {
        if (isRootdReady()) {
            return "Rootd ready — " + rootProvider() + " root is executable. Systemless-only policy remains active.";
        }
        if (isRootDetected()) {
            return "Root detected, but executable root was not verified. Advanced Rootd actions remain locked.";
        }
        return "No usable root detected. Rootd remains in safe compatibility mode.";
    }

    public static String permissionReport(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Root detected: ").append(isRootDetected()).append('\n');
        builder.append("Root executable: ").append(canExecuteRoot()).append('\n');
        builder.append("Rootd ready: ").append(isRootdReady()).append('\n');
        builder.append("Root provider: ").append(rootProvider()).append('\n');
        builder.append("Manage all files: ").append(canManageAllFiles()).append('\n');
        builder.append("Allow from this source: ").append(canInstallUnknownApps(context)).append('\n');
        builder.append("Android SDK: ").append(Build.VERSION.SDK_INT).append('\n');
        builder.append("Systemless-only: true").append('\n');
        builder.append("Direct system writes: blocked").append('\n');
        return builder.toString();
    }

    private static String findRootBinary() {
        for (String path : SU_PATHS) {
            if (new File(path).exists()) return path;
        }
        return null;
    }

    private static boolean hasRootProviderProperty() {
        return hasProperty("ro.magisk.version", null);
    }

    private static boolean hasProperty(String key, String expected) {
        try {
            Process process = new ProcessBuilder("getprop", key)
                    .redirectErrorStream(true)
                    .start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String value = reader.readLine();
            int exitCode = process.waitFor();
            if (exitCode != 0 || value == null || value.trim().isEmpty()) return false;
            return expected == null || expected.equalsIgnoreCase(value.trim());
        } catch (IOException | InterruptedException ignored) {
            if (ignored instanceof InterruptedException) Thread.currentThread().interrupt();
            return false;
        }
    }
}
