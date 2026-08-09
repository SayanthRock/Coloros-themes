package com.sayanthrock.colorosthemes.lsposed;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Rootd system customizer for safe systemless profiles, target visibility,
 * rollback planning, and customer support reports.
 */
public class RootdSystemCustomizerActivity extends Activity {

    private static final int COLOR_BG = 0xFF0F0F10;
    private static final int COLOR_CARD = 0xB81A1A1D;
    private static final int COLOR_CARD_SOFT = 0xAA242429;
    private static final int COLOR_ACCENT = 0xFFE2B884;
    private static final int COLOR_ACCENT_SOFT = 0xFFF0D2A8;
    private static final int COLOR_TEXT = 0xFFF5F2EA;
    private static final int COLOR_MUTED = 0xFFB9B1A3;
    private static final int COLOR_BORDER = 0x4DFFFFFF;
    private static final int COLOR_SUCCESS = 0xFF8FD694;
    private static final int COLOR_WARNING = 0xFFFFCC66;
    private static final int COLOR_DANGER = 0xFFFF8A80;

    private static final String PREFS = "rootd_system_customizer";

    private static final String[][] ROOTD_PROFILES = new String[][] {
            {"systemless_mode", "Systemless-only mode", "Mount module files through Magisk, KernelSU, or APatch paths only.", "required"},
            {"overlay_staging", "Overlay staging", "Prepare overlay status labels before any customer release.", "required"},
            {"device_match", "Device profile matching", "Keep OPPO, OnePlus, realme, ROM, SDK, and build checks visible.", "required"},
            {"safe_disable", "Safe-disable before OTA", "Remind the customer to disable the module before major OTA updates.", "required"},
            {"rollback_ready", "Rollback ready", "Keep uninstall and recovery notes visible for support.", "required"},
            {"lsposed_scope", "LSPosed scope check", "Show LSPosed status without pretending every hook works on every ROM.", "optional"},
            {"log_export", "Rootd report export", "Copy and share customer support reports from inside the app.", "required"},
            {"direct_write_block", "Block direct partition writes", "Do not write directly to system, vendor, product, or system_ext from the APK.", "required"}
    };

    private static final String[][] SYSTEM_TARGETS = new String[][] {
            {"android", "Android Framework", "Framework visual resource target", "high"},
            {"com.android.systemui", "System UI", "Status bar, quick settings, notifications, lock surface", "high"},
            {"com.android.settings", "Settings UI", "Settings cards, About phone, diagnostics", "medium"},
            {"com.android.launcher", "Launcher", "Home screen, icons, app drawer, wallpaper preview", "medium"},
            {"com.oplus.uxdesign", "OPlus UX Design", "Color tokens, cards, surfaces, typography", "medium"},
            {"com.oplus.battery", "OPlus Battery", "Battery cards and guidance", "medium"},
            {"com.oplus.eyeprotect", "OPlus Eye Comfort", "themeInner eye protection assets", "medium"},
            {"com.oplus.notificationmanager", "OPlus Notification Manager", "Notification controls and permission guidance", "medium"},
            {"com.oplus.ota", "OPlus OTA", "OTA warning and safe-disable guidance only", "high"},
            {"com.android.wallpaper.livepicker", "Live Wallpaper Picker", "Live wallpaper shortcut target", "low"},
            {"org.lsposed.manager", "LSPosed Manager", "Hook manager visibility", "medium"},
            {"com.topjohnwu.magisk", "Magisk Manager", "Root manager visibility", "medium"},
            {"me.weishu.kernelsu", "KernelSU Manager", "Root manager visibility", "medium"},
            {"me.bmax.apatch", "APatch Manager", "Root manager visibility", "medium"}
    };

    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(COLOR_BG);
        getWindow().setNavigationBarColor(COLOR_BG);
        render();
    }

    private void render() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(COLOR_BG);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(22), dp(18), dp(24));
        scrollView.addView(root, new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.WRAP_CONTENT
        ));

        addHeroCard();
        addSectionTitle("Rootd profiles", "Customer-visible Rootd controls for safe systemless customization.");
        addRootdProfiles();
        addSectionTitle("System target scan", "Package detection and risk labels for ColorOS, realme UI, OxygenOS, and Android targets.");
        addSystemTargets();
        addSectionTitle("Systemless paths", "Only staged module paths are shown. Runtime direct partition writes remain disabled.");
        addPlainCard(DeviceCompatibility.mountTargetSummary());
        addPlainCard(DeviceCompatibility.moduleSafetyPolicy());
        addSectionTitle("Rootd reports", "Export the whole Rootd state for customer support and device-by-device testing.");
        addButton("Copy Rootd system report", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyText("ColorOS Themes Rock Rootd System Report", rootdSystemReport());
            }
        });
        addButton("Share Rootd system report", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareReport();
            }
        });
        addButton("Open Android settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_SETTINGS));
            }
        });
        addButton("Open advanced dashboard", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RootdSystemCustomizerActivity.this, MainActivity.class));
            }
        });

        setContentView(scrollView);
    }

    private void addHeroCard() {
        LinearLayout card = card(true);
        card.addView(badge("Rootd • Systemless • Safe-disable", COLOR_SUCCESS));
        TextView title = text("Rootd System Customizer", 30, COLOR_TEXT, true);
        title.setPadding(0, dp(14), 0, dp(6));
        card.addView(title);
        card.addView(cardBody("Improve the Rootd layer with systemless profiles, target visibility, rollback planning, OTA guard labels, LSPosed checks, and support reports for OPPO, OnePlus, and realme phones."));
        card.addView(cardButton("Copy full Rootd report", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyText("ColorOS Themes Rock Rootd System Report", rootdSystemReport());
            }
        }));
        root.addView(card, cardParams());
    }

    private void addRootdProfiles() {
        for (String[] profile : ROOTD_PROFILES) {
            boolean required = "required".equals(profile[3]);
            addProfileSwitch(profile[1], profile[2], profile[0], true, required);
        }
    }

    private void addSystemTargets() {
        for (String[] target : SYSTEM_TARGETS) {
            String packageName = target[0];
            String title = target[1];
            String description = target[2];
            String risk = target[3];
            boolean installed = isPackageInstalled(packageName);
            String status = installed ? "Detected" : "Needs test";
            int color = installed ? COLOR_SUCCESS : COLOR_WARNING;
            if ("high".equals(risk) && !installed) {
                color = COLOR_DANGER;
            }
            addTargetCard(title, packageName + "\n" + description + "\nRisk: " + risk + " • Status: " + packageStatus(packageName), status, color);
        }
    }

    private void addProfileSwitch(String title, String subtitle, final String key, boolean defaultValue, boolean required) {
        LinearLayout card = card(false);
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.addView(text(title, 19, COLOR_TEXT, true), new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        header.addView(badge(required ? "Required" : "Optional", required ? COLOR_ACCENT : COLOR_WARNING));
        card.addView(header);

        TextView body = cardBody(subtitle);
        body.setPadding(0, dp(10), 0, dp(12));
        card.addView(body);

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.addView(text(required ? "Keep enabled" : "Enable for testing", 15, COLOR_MUTED, false), new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        Switch option = new Switch(this);
        option.setChecked(required || prefBool(key, defaultValue));
        option.setEnabled(true);
        option.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveBool(key, isChecked);
                toast(isChecked ? "Enabled" : "Disabled");
            }
        });
        row.addView(option);
        card.addView(row);
        root.addView(card, cardParams());
    }

    private void addTargetCard(String title, String body, String status, int statusColor) {
        LinearLayout card = card(false);
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.addView(text(title, 19, COLOR_TEXT, true), new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        header.addView(badge(status, statusColor));
        card.addView(header);
        TextView bodyView = cardBody(body);
        bodyView.setPadding(0, dp(10), 0, 0);
        card.addView(bodyView);
        root.addView(card, cardParams());
    }

    private String rootdSystemReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("ColorOS Themes Rock Rootd System Report\n");
        builder.append(DeviceCompatibility.supportReport(this)).append('\n');
        builder.append(DeviceCompatibility.rootdChecklist(this)).append('\n');
        builder.append("Rootd profiles\n");
        for (String[] profile : ROOTD_PROFILES) {
            boolean required = "required".equals(profile[3]);
            builder.append("- ").append(profile[1]).append(": ")
                    .append(required || prefBool(profile[0], true) ? "enabled" : "disabled")
                    .append(required ? " • required" : " • optional")
                    .append('\n');
        }
        builder.append('\n').append("System target scan\n");
        for (String[] target : SYSTEM_TARGETS) {
            builder.append("- ").append(target[1]).append(" • ").append(target[0]).append(" • ")
                    .append(packageStatus(target[0])).append(" • risk: ").append(target[3]).append('\n');
        }
        builder.append('\n').append("Blocked actions\n");
        builder.append("- direct system partition writes\n");
        builder.append("- OTA integrity changes\n");
        builder.append("- fake Working labels without real device testing\n");
        builder.append("- irreversible changes without rollback\n");
        return builder.toString();
    }

    private String packageStatus(String packageName) {
        return isPackageInstalled(packageName) ? "installed on this phone" : "not detected / OEM variant may differ";
    }

    private boolean isPackageInstalled(String packageName) {
        if (packageName == null || packageName.trim().isEmpty()) {
            return false;
        }
        if ("android".equals(packageName)) {
            return true;
        }
        try {
            getPackageManager().getPackageInfo(packageName.toLowerCase(Locale.US), 0);
            return true;
        } catch (PackageManager.NameNotFoundException ignored) {
            return false;
        }
    }

    private void openSettings(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException firstFailure) {
            try {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            } catch (ActivityNotFoundException secondFailure) {
                toast("Settings page not available on this phone");
            }
        }
    }

    private void copyText(String label, String value) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(label, value));
            toast("Report copied");
        }
    }

    private void shareReport() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ColorOS Themes Rock Rootd System Report");
        shareIntent.putExtra(Intent.EXTRA_TEXT, rootdSystemReport());
        try {
            startActivity(Intent.createChooser(shareIntent, "Share Rootd report"));
        } catch (ActivityNotFoundException failure) {
            toast("No share app found");
        }
    }

    private void addSectionTitle(String title, String subtitle) {
        TextView titleView = text(title, 15, COLOR_ACCENT, true);
        titleView.setPadding(0, dp(10), 0, dp(4));
        root.addView(titleView, matchWrap());
        TextView subtitleView = text(subtitle, 14, COLOR_MUTED, false);
        subtitleView.setPadding(0, 0, 0, dp(14));
        root.addView(subtitleView, matchWrap());
    }

    private void addButton(String label, boolean primary, View.OnClickListener listener) {
        Button button = createButton(label, primary);
        button.setOnClickListener(listener);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(4), 0, dp(10));
        root.addView(button, params);
    }

    private Button cardButton(String label, boolean primary, View.OnClickListener listener) {
        Button button = createButton(label, primary);
        button.setOnClickListener(listener);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(14), 0, 0);
        button.setLayoutParams(params);
        return button;
    }

    private Button createButton(String label, boolean primary) {
        Button button = new Button(this);
        button.setAllCaps(false);
        button.setText(label);
        button.setTextSize(15);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setPadding(dp(16), dp(12), dp(16), dp(12));
        button.setTextColor(primary ? COLOR_BG : COLOR_TEXT);
        button.setBackground(rounded(primary ? COLOR_ACCENT : COLOR_CARD_SOFT, dp(22), primary ? COLOR_ACCENT_SOFT : COLOR_BORDER));
        return button;
    }

    private LinearLayout card(boolean elevated) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(18), dp(18), dp(18));
        card.setBackground(rounded(elevated ? COLOR_CARD_SOFT : COLOR_CARD, dp(26), elevated ? COLOR_ACCENT : COLOR_BORDER));
        return card;
    }

    private void addPlainCard(String body) {
        LinearLayout card = card(false);
        card.addView(cardBody(body));
        root.addView(card, cardParams());
    }

    private TextView badge(String label, int color) {
        TextView view = text(label, 12, COLOR_BG, true);
        view.setGravity(Gravity.CENTER);
        view.setPadding(dp(10), dp(5), dp(10), dp(5));
        view.setBackground(rounded(color, dp(999), color));
        return view;
    }

    private TextView cardBody(String body) {
        TextView view = text(body, 16, COLOR_MUTED, false);
        view.setLineSpacing(0, 1.08f);
        return view;
    }

    private TextView text(String value, int sp, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(sp);
        view.setTextColor(color);
        view.setTypeface(Typeface.DEFAULT, bold ? Typeface.BOLD : Typeface.NORMAL);
        view.setIncludeFontPadding(true);
        return view;
    }

    private GradientDrawable rounded(int fillColor, int radius, int strokeColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(fillColor);
        drawable.setCornerRadius(radius);
        drawable.setStroke(dp(1), strokeColor);
        return drawable;
    }

    private LinearLayout.LayoutParams cardParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, dp(16));
        return params;
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private boolean prefBool(String key, boolean defaultValue) {
        SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, defaultValue);
    }

    private void saveBool(String key, boolean value) {
        getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    private void toast(String value) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
