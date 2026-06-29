package com.sayanthrock.colorosthemes.lsposed.hook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * LSPosed entry point.
 *
 * The current behavior stays intentionally conservative. It verifies package
 * loading and logs relevant scope targets for the toolbox UI. Real OEM hooks
 * should stay behind device testing because ColorOS, OxygenOS, and realme UI
 * change private classes frequently.
 */
public final class ColorOsThemeHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (loadPackageParam == null || loadPackageParam.packageName == null) {
            return;
        }

        String packageName = loadPackageParam.packageName;
        if (isRelevantPackage(packageName)) {
            XposedBridge.log("ColorOS Toolbox active for " + packageName);
        }
    }

    private boolean isRelevantPackage(String packageName) {
        return "android".equals(packageName)
                || "com.android.systemui".equals(packageName)
                || "com.android.settings".equals(packageName)
                || packageName.startsWith("com.coloros")
                || packageName.startsWith("com.oplus")
                || packageName.startsWith("com.heytap")
                || packageName.startsWith("com.oneplus")
                || packageName.startsWith("com.realme")
                || packageName.contains("launcher")
                || packageName.contains("weather");
    }
}
