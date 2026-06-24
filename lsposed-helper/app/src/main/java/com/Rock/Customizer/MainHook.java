package com.Rock.Customizer;

import android.os.Build;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Safe LSPosed entry point for Rock Customizer.
 *
 * This class intentionally does not bypass payments, DRM, signatures, carrier
 * checks, DND controls, or private security gates. It only verifies module
 * loading, routes supported packages, and leaves placeholders for tested,
 * customer-safe UI customization hooks.
 */
public final class MainHook implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    private static final String TAG = "Rock-Customizer";
    private static String modulePath = "";

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) {
        if (startupParam != null && startupParam.modulePath != null) {
            modulePath = startupParam.modulePath;
        }
        log("zygote init, modulePath=" + modulePath);
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (loadPackageParam == null || loadPackageParam.packageName == null) {
            return;
        }

        final String packageName = loadPackageParam.packageName;
        if ("com.sayanthrock.colorosthemes.lsposed".equals(packageName)
                || "com.sayanthrock.colorosthemes.lsposed.debug".equals(packageName)) {
            return;
        }

        if (!isSupportedPackage(packageName)) {
            return;
        }

        runSafe("route " + packageName, new SafeAction() {
            @Override
            public void run() {
                log("loaded for " + packageName + " on api=" + Build.VERSION.SDK_INT);
                routeSafeHooks(packageName, loadPackageParam);
            }
        });
    }

    private static void routeSafeHooks(String packageName, XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (Build.VERSION.SDK_INT >= 36) {
            hookApi36Plus(packageName, loadPackageParam);
        } else {
            hookApi35(packageName, loadPackageParam);
        }
    }

    private static void hookApi35(String packageName, XC_LoadPackage.LoadPackageParam loadPackageParam) {
        log("api35 safe hook placeholder for " + packageName);
    }

    private static void hookApi36Plus(String packageName, XC_LoadPackage.LoadPackageParam loadPackageParam) {
        log("api36+ safe hook placeholder for " + packageName);
    }

    private static boolean isSupportedPackage(String packageName) {
        return "android".equals(packageName)
                || "system".equals(packageName)
                || "com.oplus.themestore".equals(packageName)
                || "com.oplus.uxdesign".equals(packageName)
                || "com.oplus.ota".equals(packageName)
                || "com.oplus.battery".equals(packageName)
                || "com.oplus.notificationmanager".equals(packageName)
                || "com.android.wallpaper.livepicker".equals(packageName)
                || "com.android.settings".equals(packageName)
                || "com.android.systemui".equals(packageName)
                || packageName.startsWith("com.realme.")
                || packageName.startsWith("com.oneplus.");
    }

    private static void runSafe(String label, SafeAction action) {
        try {
            action.run();
        } catch (Throwable throwable) {
            log("hook miss: " + label + " -> " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage());
        }
    }

    private static void log(String message) {
        XposedBridge.log("[" + TAG + "] " + message);
    }

    private interface SafeAction {
        void run() throws Throwable;
    }
}
