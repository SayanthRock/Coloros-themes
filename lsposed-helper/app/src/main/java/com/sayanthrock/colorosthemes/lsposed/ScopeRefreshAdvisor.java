package com.sayanthrock.colorosthemes.lsposed;

import android.content.Context;
import android.text.format.DateFormat;

import java.util.Date;

/**
 * Safe guidance for re-optimizing after ROM updates or broken scopes.
 *
 * This class never attempts to modify LSPosed scope automatically.
 * It stores a refresh marker and gives the user a clean checklist.
 */
public final class ScopeRefreshAdvisor {

    private ScopeRefreshAdvisor() {
        // Utility class.
    }

    public static void markRefreshRequested(Context context, String reason) {
        ToolboxPrefs.markScopeRefresh(context, reason);
    }

    public static String refreshChecklist(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Refresh affected scope\n");
        builder.append("1. Open LSPosed or your compatible manager.\n");
        builder.append("2. Check this module is still enabled.\n");
        builder.append("3. Recheck scopes for android, System UI, Settings, launcher, and supported weather packages.\n");
        builder.append("4. If the ROM updated package names, refresh the selected scope list.\n");
        builder.append("5. Reboot after changing scope or after a system update.\n");
        builder.append("6. Reopen ColorOS Toolbox and copy the support report if a tweak still fails.\n\n");
        builder.append("Last refresh: ").append(lastRefreshLabel(context)).append('\n');
        builder.append("Reason: ").append(ToolboxPrefs.lastScopeReason(context));
        return builder.toString();
    }

    public static String lastRefreshLabel(Context context) {
        long value = ToolboxPrefs.lastScopeRefresh(context);
        if (value <= 0L) {
            return "never";
        }
        return DateFormat.format("yyyy-MM-dd kk:mm", new Date(value)).toString();
    }
}
