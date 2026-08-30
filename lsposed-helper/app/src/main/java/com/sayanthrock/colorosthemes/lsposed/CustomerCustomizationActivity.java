package com.sayanthrock.colorosthemes.lsposed;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
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
 * Customer-facing customization center.
 * Every option exposes the same lifecycle: Enable, Configure, Preview, Apply,
 * Reset, and Status. Only verified safe operations perform a real change.
 */
public class CustomerCustomizationActivity extends Activity {

    private static final int COLOR_BG = 0xFF0F0F10;
    private static final int COLOR_NAV_BG = 0xE6151517;
    private static final int COLOR_CARD = 0xB81A1A1D;
    private static final int COLOR_CARD_SOFT = 0xAA242429;
    private static final int COLOR_ACCENT = 0xFFE2B884;
    private static final int COLOR_TEXT = 0xFFF5F2EA;
    private static final int COLOR_MUTED = 0xFFB9B1A3;
    private static final int COLOR_BORDER = 0x4DFFFFFF;
    private static final int COLOR_SUCCESS = 0xFF8FD694;
    private static final int COLOR_WARNING = 0xFFFFCC66;

    private static final String PREFS = "customer_customization_center";

    private static final String[][] OPTIONS = new String[][] {
            {"wallpaper", "Wallpaper and Lock Screen", "Owned wallpaper assets through the standard Android Wallpaper API", "", "apply"},
            {"icons", "Icons and Launcher", "Preview launcher assets and open the public launcher settings", "com.android.launcher", "preview"},
            {"fonts", "System Fonts", "Preview owned fonts and open supported OEM font settings", "com.oplus.uxdesign", "preview"},
            {"sounds", "Ringtones and UI Sounds", "Preview owned sound assets and open Android sound settings", "android.media", "shortcut"},
            {"system_ui", "System UI", "Status bar, quick settings, notifications, and lock-surface preview", "com.android.systemui", "preview"},
            {"settings_ui", "Settings UI", "Settings labels and cards preview with safe settings shortcuts", "com.android.settings", "preview"},
            {"oplus_uxdesign", "OPlus UX Design", "Owned color tokens, surfaces, typography, and motion preview", "com.oplus.uxdesign", "preview"},
            {"oplus_battery", "OPlus Battery", "Battery settings and honest diagnostics guidance; no fake booster changes", "com.oplus.battery", "guidance"},
            {"colors_xml", "Theme colors.xml", "Edit and preview owned color tokens, then validate before use", "colors.xml", "preview"},
            {"android_launcher", "Android Launcher", "Launcher settings and icon preview using the detected launcher package", "com.android.launcher", "preview"},
            {"oplus_eyeprotect", "OPlus Eye Comfort", "Preview owned assets for the systemless themeInner target", "com.oplus.eyeprotect", "preview"},
            {"oplus_notificationmanager", "OPlus Notification Manager", "Notification settings and permission guidance", "com.oplus.notificationmanager", "guidance"},
            {"oplus_ota", "OPlus OTA", "Update warning and safe-disable reminder only; OTA integrity is never modified", "com.oplus.ota", "protected"},
            {"android_wallpaper_livepicker", "Live Wallpaper Picker", "Open the standard live wallpaper picker and preview owned assets", "com.android.wallpaper.livepicker", "shortcut"}
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
        root.setPadding(dp(18), dp(22), dp(18), dp(28));
        scrollView.addView(root, new ScrollView.LayoutParams(-1, -2));

        addHeroCard();
        addSectionTitle("All customer options", "Every option now follows the same Enable → Configure → Preview → Apply → Reset flow.");
        for (String[] option : OPTIONS) addOption(option);
        addSectionTitle("System settings", "Shortcuts never apply hidden changes. They only open public Android/OEM settings.");
        addSettingsShortcuts();
        addSectionTitle("Device and safety", "Unsupported or unverified targets stay status-first and cannot perform unsafe writes.");
        addPlainCard(DeviceCompatibility.supportReport(this));
        addPlainCard(DeviceCompatibility.rootdChecklist(this));
        addSectionTitle("Support", "The report includes every option's current state and device detection.");
        addButton("Copy customer report", true, v -> copyText("ColorOS Themes Rock Customer Report", customerReport()));
        addButton("Share customer report", false, v -> shareReport());
        addButton("Open advanced dashboard", false, v -> startActivity(new Intent(this, MainActivity.class)));
        setContentView(scrollView);
    }

    private void addHeroCard() {
        LinearLayout card = card(true);
        card.addView(badge("FREE • SYSTEMLESS • REVERSIBLE", COLOR_SUCCESS));
        TextView title = text("Customization Center", 30, COLOR_TEXT, true);
        title.setPadding(0, dp(14), 0, dp(6));
        card.addView(title);
        card.addView(cardBody("One predictable control surface for every customer option. Opening Configure or Preview never changes the phone. Apply only performs an operation when it is actually implemented and verified safe."));
        card.addView(cardButton("Open system settings", true, v -> openSettings(new Intent(Settings.ACTION_SETTINGS))));
        root.addView(card, cardParams());
    }

    private void addOption(final String[] option) {
        final String id = option[0];
        final String title = option[1];
        final String description = option[2];
        final String packageName = option[3];
        final String capability = option[4];
        LinearLayout card = card(false);

        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.addView(text(title, 19, COLOR_TEXT, true), new LinearLayout.LayoutParams(0, -2, 1f));
        TextView status = badge(statusLabel(id), statusColor(id));
        header.addView(status);
        card.addView(header);

        TextView body = cardBody(description + (packageName.isEmpty() ? "" : "\nTarget: " + packageName));
        body.setPadding(0, dp(10), 0, dp(10));
        card.addView(body);

        LinearLayout enableRow = new LinearLayout(this);
        enableRow.setOrientation(LinearLayout.HORIZONTAL);
        enableRow.setGravity(Gravity.CENTER_VERTICAL);
        enableRow.addView(text("Enable option", 15, COLOR_MUTED, false), new LinearLayout.LayoutParams(0, -2, 1f));
        Switch toggle = new Switch(this);
        toggle.setChecked(CustomizationManager.isOptionEnabled(this, id));
        toggle.setOnCheckedChangeListener((buttonView, checked) -> {
            CustomizationManager.setOptionEnabled(this, id, checked);
            refreshOptionStatus(status, id);
            toast(checked ? "Enabled" : "Disabled");
        });
        enableRow.addView(toggle);
        card.addView(enableRow);

        LinearLayout actions = new LinearLayout(this);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        actions.setGravity(Gravity.CENTER_VERTICAL);
        actions.addView(smallButton("Configure", v -> configureOption(id, capability)), new LinearLayout.LayoutParams(0, -2, 1f));
        actions.addView(smallButton("Preview", v -> previewOption(id, title, description)), new LinearLayout.LayoutParams(0, -2, 1f));
        actions.addView(smallButton("Apply", v -> applyOption(id, title, capability)), new LinearLayout.LayoutParams(0, -2, 1f));
        actions.addView(smallButton("Reset", v -> {
            CustomizationManager.resetOption(this, id);
            refreshOptionStatus(status, id);
            toast("Reset to Ready");
        }), new LinearLayout.LayoutParams(0, -2, 1f));
        card.addView(actions);
        root.addView(card, cardParams());
    }

    private void configureOption(String id, String capability) {
        if (!CustomizationManager.isOptionEnabled(this, id)) {
            toast("Enable this option first");
            return;
        }
        if ("wallpaper".equals(id)) {
            startActivityForResult(CustomizationManager.imagePickerIntent(), 1001);
            return;
        }
        if ("shortcut".equals(capability) || "guidance".equals(capability)) {
            openSettingsForOption(id);
            CustomizationManager.configureOption(this, id);
            toast("Configured safely");
            render();
            return;
        }
        CustomizationManager.configureOption(this, id);
        toast("Configuration saved; no phone changes made");
        render();
    }

    private void previewOption(String id, String title, String description) {
        if (!CustomizationManager.isOptionEnabled(this, id)) {
            toast("Enable this option first");
            return;
        }
        CustomizationManager.previewOption(this, id);
        new AlertDialog.Builder(this)
                .setTitle(title + " • Preview")
                .setMessage(description + "\n\nStatus: " + CustomizationManager.optionStateLabel(this, id) + "\n\nPreview mode does not modify system files or partitions.")
                .setPositiveButton("Done", null)
                .show();
    }

    private void applyOption(String id, String title, String capability) {
        if (!CustomizationManager.isOptionEnabled(this, id)) {
            toast("Enable this option first");
            return;
        }
        if ("wallpaper".equals(id)) {
            try {
                String result = CustomizationManager.applyWallpaper(this, WallpaperManager.FLAG_SYSTEM);
                toast(result);
                render();
            } catch (Exception failure) {
                toast("Wallpaper could not be applied: " + failure.getMessage());
            }
            return;
        }
        if ("protected".equals(capability)) {
            new AlertDialog.Builder(this)
                    .setTitle(title + " is protected")
                    .setMessage("Apply is intentionally blocked. OTA integrity and update files are never modified. Use the warning and safe-disable guidance instead.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("Safe apply gate")
                .setMessage("This option is not yet verified for the exact device/ROM. No unsafe change will be made. The option remains in its current state until device testing is completed.")
                .setPositiveButton("OK", null)
                .show();
        CustomizationManager.setOptionState(this, id, CustomizationManager.OptionState.NEEDS_DEVICE_TEST);
        render();
    }

    private void openSettingsForOption(String id) {
        if ("wallpaper".equals(id) || "android_wallpaper_livepicker".equals(id)) {
            openSettings(new Intent("android.settings.WALLPAPER_SETTINGS"));
        } else if ("icons".equals(id) || "android_launcher".equals(id)) {
            openSettings(new Intent(Settings.ACTION_HOME_SETTINGS));
        } else if ("fonts".equals(id) || "oplus_uxdesign".equals(id)) {
            openSettings(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
        } else if ("oplus_battery".equals(id)) {
            openSettings(new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS));
        } else if ("oplus_notificationmanager".equals(id)) {
            openSettings(new Intent("android.settings.APP_NOTIFICATION_SETTINGS"));
        } else {
            openSettings(new Intent(Settings.ACTION_SETTINGS));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (SecurityException ignored) { }
            CustomizationManager.saveImageUri(this, data.getData());
            CustomizationManager.configureOption(this, "wallpaper");
            toast("Wallpaper configured");
            render();
        }
    }

    private void addSettingsShortcuts() {
        addButton("Wallpaper settings", false, v -> openSettings(new Intent("android.settings.WALLPAPER_SETTINGS")));
        addButton("Display settings", false, v -> openSettings(new Intent(Settings.ACTION_DISPLAY_SETTINGS)));
        addButton("Home / launcher settings", false, v -> openSettings(new Intent(Settings.ACTION_HOME_SETTINGS)));
        addButton("Notification settings", false, v -> openSettings(new Intent("android.settings.APP_NOTIFICATION_SETTINGS")));
        addButton("Battery settings", false, v -> openSettings(new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS)));
        addButton("Apps settings", false, v -> openSettings(new Intent(Settings.ACTION_APPLICATION_SETTINGS)));
        addButton("Developer animation settings", false, v -> openSettings(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)));
    }

    private String customerReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("ColorOS Themes Rock Customer Customization Report\n");
        builder.append("Device: ").append(DeviceCompatibility.brand()).append(' ').append(DeviceCompatibility.model()).append('\n');
        builder.append("Skin: ").append(DeviceCompatibility.skinLabel()).append('\n');
        builder.append("Android: ").append(DeviceCompatibility.androidGenerationLabel()).append('\n');
        builder.append("Root manager: ").append(DeviceCompatibility.rootManagerLabel(this)).append('\n');
        builder.append("LSPosed: ").append(DeviceCompatibility.lsposedLabel(this)).append('\n');
        builder.append("Policy: systemless only, no direct system partition writes\n\n");
        builder.append("Option lifecycle\n");
        for (String[] option : OPTIONS) {
            builder.append("- ").append(option[1]).append(": ")
                    .append(CustomizationManager.optionStateLabel(this, option[0]));
            if (!option[3].isEmpty()) builder.append(" • ").append(option[3]);
            builder.append('\n');
        }
        builder.append('\n').append(CustomizationManager.report(this));
        return builder.toString();
    }

    private String statusLabel(String id) { return CustomizationManager.optionStateLabel(this, id); }
    private int statusColor(String id) {
        CustomizationManager.OptionState state = CustomizationManager.getOptionState(this, id);
        return state == CustomizationManager.OptionState.APPLIED || state == CustomizationManager.OptionState.READY || state == CustomizationManager.OptionState.CONFIGURED || state == CustomizationManager.OptionState.PREVIEW ? COLOR_SUCCESS : COLOR_WARNING;
    }
    private void refreshOptionStatus(TextView view, String id) {
        view.setText(statusLabel(id));
        view.setBackground(rounded(statusColor(id), dp(999), statusColor(id)));
    }

    private void openSettings(Intent intent) {
        try { startActivity(intent); }
        catch (ActivityNotFoundException firstFailure) {
            try { startActivity(new Intent(Settings.ACTION_SETTINGS)); }
            catch (ActivityNotFoundException secondFailure) { toast("Settings page not available on this phone"); }
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
        try { startActivity(Intent.createChooser(shareIntent, "Share customer report")); }
        catch (ActivityNotFoundException failure) { toast("No share app found"); }
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, dp(4), 0, dp(10));
        root.addView(button, params);
    }

    private Button cardButton(String label, boolean primary, View.OnClickListener listener) {
        Button button = createButton(label, primary);
        button.setOnClickListener(listener);
        button.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        return button;
    }

    private Button smallButton(String label, View.OnClickListener listener) {
        Button button = createButton(label, false);
        button.setTextSize(12);
        button.setPadding(dp(5), dp(8), dp(5), dp(8));
        button.setOnClickListener(listener);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -2, 1f);
        params.setMargins(dp(2), dp(4), dp(2), 0);
        button.setLayoutParams(params);
        return button;
    }

    private Button createButton(String label, boolean primary) {
        Button button = new Button(this);
        button.setAllCaps(false);
        button.setText(label);
        button.setTextSize(15);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setPadding(dp(16), dp(12), dp(16), dp(12));
        button.setTextColor(primary ? COLOR_BG : COLOR_TEXT);
        button.setBackground(rounded(primary ? COLOR_ACCENT : COLOR_CARD_SOFT, dp(22), primary ? COLOR_ACCENT : COLOR_BORDER));
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
        TextView view = text(label, 11, COLOR_BG, true);
        view.setGravity(Gravity.CENTER);
        view.setPadding(dp(8), dp(5), dp(8), dp(5));
        view.setBackground(rounded(color, dp(999), color));
        return view;
    }

    private TextView cardBody(String body) {
        TextView view = text(body, 15, COLOR_MUTED, false);
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 0, 0, dp(12));
        return params;
    }

    private LinearLayout.LayoutParams matchWrap() { return new LinearLayout.LayoutParams(-1, -2); }
    private int dp(int value) { return Math.round(value * getResources().getDisplayMetrics().density); }
    private void toast(String message) { Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); }
}
