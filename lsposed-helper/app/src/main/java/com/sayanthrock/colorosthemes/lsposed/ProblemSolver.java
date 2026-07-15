package com.sayanthrock.colorosthemes.lsposed;

import android.content.Context;
import android.os.Build;

/**
 * Root setup and problem-solving guidance for the toolbox.
 *
 * This class avoids unsafe claims. It gives the user a repeatable recovery path
 * for common module issues after root setup, LSPosed scope changes, or ROM updates.
 */
public final class ProblemSolver {

    private ProblemSolver() {
        // Utility class.
    }

    public static String healthSummary(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Problem Solver Health\n");
        builder.append("Root manager: ").append(RootSetupAssistant.rootManagerStatus(context)).append('\n');
        builder.append("Root binary: ").append(RootSetupAssistant.rootBinaryStatus()).append('\n');
        builder.append("LSPosed manager: ").append(RootSetupAssistant.lsposedStatus(context)).append('\n');
        builder.append("ROM family: ").append(RootSetupAssistant.romFamily()).append('\n');
        builder.append("Android SDK: ").append(Build.VERSION.SDK_INT).append('\n');
        builder.append("Scope refresh: ").append(ScopeRefreshAdvisor.lastRefreshLabel(context)).append('\n');
        builder.append("Mode: safe guided repair\n");
        return builder.toString();
    }

    public static String rootSetupPlan(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Root setup plan\n");
        builder.append("1. Install or open a trusted root manager.\n");
        builder.append("2. Install the module ZIP using Magisk, KernelSU, APatch, or a compatible module manager.\n");
        builder.append("3. Open LSPosed or a compatible Xposed manager.\n");
        builder.append("4. Enable ColorOS Toolbox.\n");
        builder.append("5. Enable only the required scopes listed by the app.\n");
        builder.append("6. Reboot after changing module state or scope.\n");
        builder.append("7. Run Refresh affected scope if a tweak stops working after an update.\n\n");
        builder.append(RootSetupAssistant.supportSummary(context));
        return builder.toString();
    }

    public static String fixAllChecklist(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Fix current matters checklist\n");
        builder.append("This does not claim impossible auto-repair. It guides the safe steps that usually solve module issues.\n\n");
        builder.append("1. Confirm the root manager is installed and active.\n");
        builder.append("2. Confirm the module is installed and enabled in the root manager.\n");
        builder.append("3. Confirm LSPosed detects ColorOS Toolbox.\n");
        builder.append("4. Refresh scope after ROM updates or app package changes.\n");
        builder.append("5. Reboot after changing root module state or LSPosed scope.\n");
        builder.append("6. Disable unsupported tweaks and test one group at a time.\n");
        builder.append("7. Copy the support report before reporting a broken feature.\n\n");
        builder.append("Recommended scopes:\n");
        builder.append(RootSetupAssistant.requiredScopesText()).append('\n');
        return builder.toString();
    }

    public static String optimizationPlan(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Optimization and refresh plan\n");
        builder.append("- Use safe mode first after any OTA update.\n");
        builder.append("- Refresh LSPosed scopes when System UI, launcher, settings, or weather package names change.\n");
        builder.append("- Keep System UI tweaks scoped only to System UI packages.\n");
        builder.append("- Keep launcher tweaks scoped only to launcher packages.\n");
        builder.append("- Keep weather lock screen tweaks disabled unless the ROM exposes a compatible weather package.\n");
        builder.append("- Reboot once after scope changes.\n");
        builder.append("- If a tweak fails, turn off that one group and keep the rest enabled.\n\n");
        builder.append(ScopeRefreshAdvisor.refreshChecklist(context));
        return builder.toString();
    }

    public static String fullReport(Context context) {
        return healthSummary(context)
                + "\n" + rootSetupPlan(context)
                + "\n" + fixAllChecklist(context)
                + "\n" + optimizationPlan(context)
                + "\n" + ToolboxPrefs.featureReport(context);
    }
}
