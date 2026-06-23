package com.sayanthrock.colorosthemes.lsposed.hook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * LSPosed entry point.
 *
 * Current behavior is intentionally safe: it verifies loading and logs supported
 * packages without changing private OEM code paths. Real hooks should be added
 * only after testing on the target ColorOS/OxygenOS/realme UI build.
 */
public final class ColorOsThemeHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (loadPackageParam == null || loadPackageParam.packageName == null) {
            return;
        }

        String packageName = loadPackageParam.packageName;
        if (isRelevantPackage(packageName)) {
            XposedBridge.log("ColorOS Themes Rock loaded safely for " + packageName);
        }
    }

    private boolean isRelevantPackage(String packageName) {
        return "android".equals(packageName)
                || "com.android.systemui".equals(packageName)
                || packageName.startsWith("com.coloros")
                || packageName.startsWith("com.oplus")
                || packageName.startsWith("com.heytap")
                || packageName.startsWith("com.oneplus")
                || packageName.startsWith("com.realme");
    }
}
