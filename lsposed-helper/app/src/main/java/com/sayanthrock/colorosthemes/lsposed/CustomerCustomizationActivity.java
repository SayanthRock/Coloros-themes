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
 * Customer-facing customization center for OPPO, OnePlus, and realme phones.
 *
 * This screen stores preferences, opens public settings pages, and exports
 * support reports. It does not perform direct system partition writes.
 */
public class CustomerCustomizationActivity extends Activity {

    private static final int COLOR_BG = 0xFF0F0F10;
    private static final int COLOR_NAV_BG = 0xE6151517;
    private static final int COLOR_CARD = 0xB81A1A1D;
    private static final int COLOR_CARD_SOFT = 0xAA242429;
    private static final int COLOR_ACCENT = 0xFFE2B884;
    private static final int COLOR_ACCENT_SOFT = 0xFFF0D2A8;
    private static final int COLOR_TEXT = 0xFFF5F2EA;
    private static final int COLOR_MUTED = 0xFFB9B1A3;
    private static final int COLOR_BORDER = 0x4DFFFFFF;
    private static final int COLOR_SUCCESS = 0xFF8FD694;
    private static final int COLOR_WARNING = 0xFFFFCC66;

    private static final String PREFS = "customer_customization_center";

    private static final String[][] CUSTOMER_TARGETS = new String[][] {
            {"wallpaper", "Wallpaper and lock screen", "Safe public wallpaper/settings flow", ""},
            {"icons", "Icons and launcher", "Launcher icon pack or theme package preview", "com.android.launcher"},
            {"fonts", "System fonts", "Theme-owned font package preview and settings shortcut", "com.oplus.uxdesign"},
            {"sounds", "Ringtones and UI sounds", "Owned sound assets and Android sound settings", ""},
            {"system_ui", "System UI", "Status bar, quick settings, notifications, and lock surface labels", "com.android.systemui"},
            {"settings_ui", "Settings UI", "Settings cards, About phone labels, and diagnostics preview", "com.android.settings"},
            {"oplus_ux", "OPlus UX Design", "Color tokens, surfaces, typography, and motion labels", "com.oplus.uxdesign"},
            {"battery", "Battery and performance", "Battery settings shortcut, honest diagnostics, no fake booster claims", "com.oplus.battery"},
            {"eye_comfort", "Eye Comfort themeInner", "Systemless module path preview for eye comfort assets", "com.oplus.eyeprotect"},
            {"notifications", "Notification Manager", "Notification controls and permission guidance", "com.oplus.notificationmanager"},
            {"ota_guard", "OTA safe guard", "Update warning and safe-disable reminder only", "com.oplus.ota"},
            {"live_wallpaper", "Live Wallpaper Picker", "Live wallpaper picker shortcut where available", "com.android.wallpaper.livepicker"},
            {"rootd_systemless", "Rootd systemless mode", "Magisk, KernelSU, or APatch module flow only", ""},
            {"lsposed_preview", "LSPosed preview", "Scope/status checks only until tested on the exact ROM", "org.lsposed.manager"}
    };

    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(COLOR_BG);
        getWindow().setNavigationBarColor(COLOR_NAV_BG);
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
        addSectionTitle("Customer options", "Toggle customer-safe options, then use shortcuts, previews, and reports.");
        addCustomerOptions();
        addSectionTitle("System settings shortcuts", "Open public Android/OEM settings pages for customer customization.");
        addSettingsShortcuts();
        addSectionTitle("Rootd and device status", "Read-only checks for OPPO, OnePlus, realme, root managers, LSPosed, and Android 15/16/17 labels.");
        addPlainCard(DeviceCompatibility.supportReport(this));
        addPlainCard(DeviceCompatibility.rootdChecklist(this));
        addSectionTitle("Support", "Copy or share the selected options when customers report problems.");
        addButton("Copy customer report", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyText("ColorOS Themes Rock Customer Report", customerReport());
            }
        });
        addButton("Share customer report", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareReport();
            }
        });
        addButton("Open advanced dashboard", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerCustomizationActivity.this, MainActivity.class));
            }
        });

        setContentView(scrollView);
    }

    private void addHeroCard() {
        LinearLayout card = card(true);
        card.addView(badge("Free • OPPO • OnePlus • realme", COLOR_SUCCESS));
        TextView title = text("Customization Center", 30, COLOR_TEXT, true);
        title.setPadding(0, dp(14), 0, dp(6));
        card.addView(title);
        card.addView(cardBody("Customer-safe customization for wallpapers, icons, fonts, sounds, System UI labels, Settings UI labels, Rootd reports, and OEM settings shortcuts. Advanced overlay work stays systemless and status-first."));
        card.addView(cardButton("Open system settings", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_SETTINGS));
            }
        }));
        root.addView(card, cardParams());
    }

    private void addCustomerOptions() {
        for (String[] target : CUSTOMER_TARGETS) {
            addOptionSwitch(target[1], optionSubtitle(target[2], target[3]), target[0], true, target[3]);
        }
    }

    private String optionSubtitle(String base, String packageName) {
        if (packageName == null || packageName.trim().isEmpty()) {
            return base + "\nStatus: Safe option stored in customer report";
        }
        return base + "\nPackage: " + packageName + " • " + packageStatus(packageName);
    }

    private void addSettingsShortcuts() {
        addButton("Wallpaper settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent("android.settings.WALLPAPER_SETTINGS"));
            }
        });
        addButton("Display and refresh rate", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
            }
        });
        addButton("Home / launcher settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_HOME_SETTINGS));
            }
        });
        addButton("Notification settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS));
            }
        });
        addButton("Battery settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS));
            }
        });
        addButton("Apps settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
            }
        });
        addButton("Developer animation settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
            }
        });
    }

    private void addOptionSwitch(String title, String subtitle, final String key, boolean defaultValue, String packageName) {
        LinearLayout card = card(false);
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.addView(text(title, 19, COLOR_TEXT, true), new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        header.addView(badge(statusLabel(packageName), statusColor(packageName)));
        card.addView(header);

        TextView body = cardBody(subtitle);
        body.setPadding(0, dp(10), 0, dp(12));
        card.addView(body);

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.addView(text("Enable for customer profile", 15, COLOR_MUTED, false), new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        Switch option = new Switch(this);
        option.setChecked(prefBool(key, defaultValue));
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

    private String customerReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("ColorOS Themes Rock Customer Customization Report\n");
        builder.append("Device: ").append(DeviceCompatibility.brand()).append(' ')
                .append(DeviceCompatibility.model()).append('\n');
        builder.append("Skin: ").append(DeviceCompatibility.skinLabel()).append('\n');
        builder.append("Android: ").append(DeviceCompatibility.androidGenerationLabel()).append('\n');
        builder.append("Root manager: ").append(DeviceCompatibility.rootManagerLabel(this)).append('\n');
        builder.append("LSPosed: ").append(DeviceCompatibility.lsposedLabel(this)).append('\n');
        builder.append("Policy: systemless only, no direct system partition writes\n\n");
        builder.append("Selected customer options\n");
        for (String[] target : CUSTOMER_TARGETS) {
            builder.append("- ").append(target[1]).append(": ")
                    .append(prefBool(target[0], true) ? "enabled" : "disabled");
            if (target[3] != null && !target[3].trim().isEmpty()) {
                builder.append(" • ").append(target[3]).append(" • ").append(packageStatus(target[3]));
            }
            builder.append('\n');
        }
        builder.append('\n').append(CustomizationManager.report(this));
        return builder.toString();
    }

    private String packageStatus(String packageName) {
        return isPackageInstalled(packageName) ? "installed on this phone" : "not detected / OEM variant may differ";
    }

    private String statusLabel(String packageName) {
        if (packageName == null || packageName.trim().isEmpty()) {
            return "Safe";
        }
        return isPackageInstalled(packageName) ? "Detected" : "Needs test";
    }

    private int statusColor(String packageName) {
        if (packageName == null || packageName.trim().isEmpty()) {
            return COLOR_SUCCESS;
        }
        return isPackageInstalled(packageName) ? COLOR_SUCCESS : COLOR_WARNING;
    }

    private boolean isPackageInstalled(String packageName) {
        if (packageName == null || packageName.trim().isEmpty()) {
            return false;
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
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ColorOS Themes Rock Customer Report");
        shareIntent.putExtra(Intent.EXTRA_TEXT, customerReport());
        try {
            startActivity(Intent.createChooser(shareIntent, "Share customer report"));
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
