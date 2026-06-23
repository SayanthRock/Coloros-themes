package com.sayanthrock.colorosthemes.lsposed;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Free-first premium dark glass customer dashboard for ColorOS Themes Rock.
 */
public class MainActivity extends Activity {

    private static final int REQUEST_PICK_IMAGE = 1001;

    private static final int TAB_DASHBOARD = 0;
    private static final int TAB_THEME = 1;
    private static final int TAB_TOOLS = 2;
    private static final int TAB_SUPPORT = 3;
    private static final int TAB_SETTINGS = 4;

    private static final int COLOR_BG = 0xFF0F0F10;
    private static final int COLOR_NAV_BG = 0xFF151517;
    private static final int COLOR_CARD = 0xFF1A1A1D;
    private static final int COLOR_CARD_SOFT = 0xFF242429;
    private static final int COLOR_ACCENT = 0xFFE2B884;
    private static final int COLOR_ACCENT_SOFT = 0xFFF0D2A8;
    private static final int COLOR_TEXT = 0xFFF5F2EA;
    private static final int COLOR_MUTED = 0xFFB9B1A3;
    private static final int COLOR_BORDER = 0x33FFFFFF;
    private static final int COLOR_SUCCESS = 0xFF8FD694;
    private static final int COLOR_WARNING = 0xFFFFCC66;
    private static final int COLOR_DANGER = 0xFFFF7A7A;

    private static final String PREFS_UI = "coloros_customizer_ui";

    private LinearLayout root;
    private int currentTab = TAB_DASHBOARD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(COLOR_BG);
        getWindow().setNavigationBarColor(COLOR_NAV_BG);
        render();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            int readFlag = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
            if (readFlag != 0) {
                try {
                    getContentResolver().takePersistableUriPermission(uri, readFlag);
                } catch (SecurityException | IllegalArgumentException ignored) {
                    // Some picker apps do not grant persistable access.
                }
            }
            CustomizationManager.saveImageUri(this, uri);
            toast("Image selected");
            currentTab = TAB_THEME;
            render();
        }
    }

    private void render() {
        LinearLayout app = new LinearLayout(this);
        app.setOrientation(LinearLayout.VERTICAL);
        app.setBackgroundColor(COLOR_BG);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(COLOR_BG);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(22), dp(18), dp(22));
        scrollView.addView(root, new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.WRAP_CONTENT
        ));

        addTopBar();
        renderCurrentPage();

        app.addView(scrollView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
        ));
        addBottomNavigation(app);
        setContentView(app);
    }

    private void renderCurrentPage() {
        if (currentTab == TAB_THEME) {
            renderThemePage();
        } else if (currentTab == TAB_TOOLS) {
            renderToolsPage();
        } else if (currentTab == TAB_SUPPORT) {
            renderSupportPage();
        } else if (currentTab == TAB_SETTINGS) {
            renderSettingsPage();
        } else {
            renderDashboardPage();
        }
    }

    private void addTopBar() {
        LinearLayout bar = new LinearLayout(this);
        bar.setOrientation(LinearLayout.HORIZONTAL);
        bar.setGravity(Gravity.CENTER_VERTICAL);
        bar.setPadding(0, dp(4), 0, dp(22));

        LinearLayout titleBox = new LinearLayout(this);
        titleBox.setOrientation(LinearLayout.VERTICAL);

        TextView title = text(titleForTab(), currentTab == TAB_DASHBOARD ? 30 : 26, COLOR_TEXT, true);
        TextView subtitle = text(subtitleForTab(), 14, COLOR_MUTED, false);
        titleBox.addView(title);
        titleBox.addView(subtitle);
        bar.addView(titleBox, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        addHeaderPill(bar, "Free", COLOR_SUCCESS, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTab = TAB_DASHBOARD;
                render();
            }
        });
        addHeaderPill(bar, "↻", COLOR_ACCENT, refreshClick());

        root.addView(bar, matchWrap());
    }

    private String titleForTab() {
        if (currentTab == TAB_THEME) return "Theme Setup";
        if (currentTab == TAB_TOOLS) return "Tools";
        if (currentTab == TAB_SUPPORT) return "Support";
        if (currentTab == TAB_SETTINGS) return "Settings";
        return "ColorOS Rock";
    }

    private String subtitleForTab() {
        if (currentTab == TAB_THEME) return "Wallpaper, lock screen, and icons";
        if (currentTab == TAB_TOOLS) return "Display, performance, and shortcuts";
        if (currentTab == TAB_SUPPORT) return "Report, backup, and rollback help";
        if (currentTab == TAB_SETTINGS) return "Free mode and local preferences";
        return "Free-first premium customization dashboard";
    }

    private void renderDashboardPage() {
        addHeroCard();
        addFreeModeCard();
        addSectionTitle("Customer options", "Every feature shows a clear support status");
        addFeatureCard("Theme Setup", "Default theme, wallpaper, lock screen, and icon guidance.", "Safe", COLOR_SUCCESS, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTab = TAB_THEME;
                render();
            }
        });
        addFeatureCard("Performance", "Display controls, animation settings, and lag-fix checklist.", "Needs test", COLOR_WARNING, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTab = TAB_TOOLS;
                render();
            }
        });
        addFeatureCard("Safety", "Backup, restore, support report, and rollback guidance.", "Safe", COLOR_SUCCESS, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTab = TAB_SUPPORT;
                render();
            }
        });
        addDeviceCard();
    }

    private void renderThemePage() {
        addSectionTitle("Wallpaper manager", "Choose one image and apply it where supported");
        addActionButton("Select image", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });
        addActionButton("Apply to Home + Lock screen", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySelectedWallpaper(WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
            }
        });
        addActionButton("Apply to Lock screen only", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySelectedWallpaper(WallpaperManager.FLAG_LOCK);
            }
        });
        addActionButton("Open Android wallpaper settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent("android.settings.WALLPAPER_SETTINGS"));
            }
        });
        addSelectedImagePreview();

        addSectionTitle("Theme and icons", "Device support may vary by ColorOS, OxygenOS, and realme UI version");
        addFeatureCard("Default Rock Premium Theme", "Use owned theme assets and previews only.", "Safe", COLOR_SUCCESS, toastClick("Default theme guide ready"));
        addFeatureCard("Icon Pack Apply", "Use launcher-supported icon options where available.", "Needs test", COLOR_WARNING, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_HOME_SETTINGS));
            }
        });
        addFeatureCard("Lock Screen Style", "Wallpaper and lock screen behavior depends on the device skin.", "Needs test", COLOR_WARNING, toastClick("Lock screen support requires device testing"));
    }

    private void renderToolsPage() {
        addSectionTitle("Display controls", "Use safe shortcuts first");
        addFeatureCard("Refresh Rate", "Open display settings for Auto, High, or Standard refresh rate options.", "Needs test", COLOR_WARNING, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
            }
        });
        addFeatureCard("Animation Speed", "Use system developer animation options only when the user enables them.", "Needs permission", COLOR_WARNING, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
            }
        });
        addFeatureCard("Battery Help", "Use honest guidance, no fake booster claims.", "Safe", COLOR_SUCCESS, toastClick("Battery checklist ready"));

        addSectionTitle("Quick entries", "Useful settings shortcuts for customers");
        addSmallCard("System settings", "Open Android Settings", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_SETTINGS));
            }
        });
        addSmallCard("App settings", "Open installed app settings", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
            }
        });
        addSmallCard("Home settings", "Open launcher or default home options", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_HOME_SETTINGS));
            }
        });
    }

    private void renderSupportPage() {
        addSectionTitle("Support report", "Copy or share this when a customer reports a problem");
        addPlainCard("Latest report\n" + BatteryOptimizationAdvisor.supportReport(this) + "\n" + CustomizationManager.report(this));
        addActionButton("Copy support report", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyReport();
            }
        });
        addActionButton("Share support report", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareReport();
            }
        });

        addSectionTitle("Safety", "Make backup and rollback easy to find");
        addFeatureCard("Backup and Restore", "Keep customer data and theme settings easy to recover.", "Required", COLOR_ACCENT, toastClick("Backup and restore guide ready"));
        addFeatureCard("Safe Disable", "Show customers how to turn off advanced features if something fails.", "Required", COLOR_ACCENT, toastClick("Safe disable guide ready"));
        addFeatureCard("Rollback Help", "Use clear steps instead of risky hidden changes.", "Safe", COLOR_SUCCESS, toastClick("Rollback help ready"));
    }

    private void renderSettingsPage() {
        addSectionTitle("Free mode", "Normal customer tools stay free and easy to use");
        addSwitchRow("Free Mode Enabled", "Show free-first labels across the app", "free_mode", true);
        addSwitchRow("Hide paid labels", "Avoid premium lock wording", "hide_paid_labels", true);
        addSwitchRow("Show status badges", "Display Safe, Needs test, Experimental, or Not supported", "status_badges", true);

        addDivider();
        addSectionTitle("UI style", "New model premium dark glass");
        addSwitchRow("Soft glass cards", "Use rounded dark cards with subtle borders", "soft_glass", true);
        addSwitchRow("Minimal motion", "Use short transitions only", "minimal_motion", true);
        addSwitchRow("Compact dashboard", "Keep the home screen focused", "compact_dashboard", true);

        addDivider();
        addSectionTitle("Customization preview", "Safe local labels for reports and previews");
        addStatusBarBlurSettingsCard();
        addAboutAndOtaCard();
    }

    private void addHeroCard() {
        LinearLayout card = card(true);
        card.setPadding(dp(22), dp(22), dp(22), dp(22));
        card.addView(badge("Free Mode Enabled", COLOR_SUCCESS));
        TextView title = text("New Model Premium UI", 27, COLOR_TEXT, true);
        title.setPadding(0, dp(14), 0, dp(6));
        card.addView(title);
        card.addView(text("Dark glass dashboard for OPPO, OnePlus, and realme customization. Built for free customer tools, clear status labels, and safe rollback guidance.", 16, COLOR_MUTED, false));
        card.addView(cardButton("Open Theme Setup", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTab = TAB_THEME;
                render();
            }
        }));
        root.addView(card, cardParams());
    }

    private void addFreeModeCard() {
        LinearLayout card = card(false);
        card.addView(cardTitle("Free-first policy"));
        card.addView(cardBody("No forced payment screen. No fake premium locks. No confusing trial labels. Optional support can stay separate from core customer tools."));
        root.addView(card, cardParams());
    }

    private void addDeviceCard() {
        addPlainCard(BatteryOptimizationAdvisor.supportReport(this));
    }

    private void addSelectedImagePreview() {
        Uri imageUri = CustomizationManager.selectedImageUri(this);
        if (imageUri == null) {
            addPlainCard("No image selected yet. Tap Select image to choose a wallpaper.");
            return;
        }
        LinearLayout card = card(false);
        card.addView(cardTitle("Selected image preview"));
        ImageView preview = new ImageView(this);
        preview.setAdjustViewBounds(true);
        preview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        preview.setImageURI(imageUri);
        preview.setBackground(rounded(COLOR_CARD_SOFT, dp(22), COLOR_BORDER));
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(230)
        );
        imageParams.setMargins(0, dp(10), 0, dp(12));
        card.addView(preview, imageParams);
        card.addView(cardBody(CustomizationManager.selectedImageLabel(this)));
        root.addView(card, cardParams());
    }

    private void addStatusBarBlurSettingsCard() {
        LinearLayout card = card(false);
        card.addView(cardTitle("Status bar blur preview"));
        card.addView(cardBody("Blur strength: " + CustomizationManager.statusBarBlurAmount(this) + "%\n" + CustomizationManager.statusBarBlurModeLabel(this)));
        card.addView(cardButton("Decrease blur", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomizationManager.decreaseStatusBarBlur(MainActivity.this);
                render();
            }
        }));
        card.addView(cardButton("Increase blur", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomizationManager.increaseStatusBarBlur(MainActivity.this);
                render();
            }
        }));
        root.addView(card, cardParams());
    }

    private void addAboutAndOtaCard() {
        LinearLayout card = card(false);
        card.addView(cardTitle("About phone and OTA labels"));
        card.addView(cardBody("Local helper labels are saved for preview and customer support reports."));

        final EditText about = input("About phone label", CustomizationManager.aboutPhoneName(this));
        card.addView(about);
        final EditText ota = input("OTA display name", CustomizationManager.otaName(this));
        card.addView(ota);

        card.addView(cardButton("Save style labels", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomizationManager.setAboutPhoneName(MainActivity.this, about.getText().toString());
                CustomizationManager.setOtaName(MainActivity.this, ota.getText().toString());
                toast("Style labels saved");
                render();
            }
        }));
        root.addView(card, cardParams());
    }

    private void addFeatureCard(String title, String subtitle, String status, int statusColor, View.OnClickListener listener) {
        LinearLayout card = card(false);
        card.setOnClickListener(listener);

        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.addView(text(title, 20, COLOR_TEXT, true), new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        header.addView(badge(status, statusColor));
        card.addView(header);

        TextView body = cardBody(subtitle);
        body.setPadding(0, dp(10), 0, 0);
        card.addView(body);
        root.addView(card, cardParams());
    }

    private void addSmallCard(String title, String subtitle, View.OnClickListener listener) {
        LinearLayout card = card(false);
        card.setOnClickListener(listener);
        card.addView(cardTitle(title));
        card.addView(cardBody(subtitle));
        root.addView(card, cardParams());
    }

    private void addPlainCard(String body) {
        LinearLayout card = card(false);
        card.addView(cardBody(body));
        root.addView(card, cardParams());
    }

    private void addActionButton(String text, boolean primary, View.OnClickListener listener) {
        Button button = createButton(text, primary);
        button.setOnClickListener(listener);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(4), 0, dp(10));
        root.addView(button, params);
    }

    private Button cardButton(String text, boolean primary, View.OnClickListener listener) {
        Button button = createButton(text, primary);
        button.setOnClickListener(listener);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(14), 0, 0);
        button.setLayoutParams(params);
        return button;
    }

    private Button createButton(String text, boolean primary) {
        Button button = new Button(this);
        button.setText(text);
        button.setAllCaps(false);
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
        card.setBackground(rounded(elevated ? COLOR_CARD_SOFT : COLOR_CARD, dp(24), COLOR_BORDER));
        return card;
    }

    private TextView badge(String label, int color) {
        TextView view = text(label, 12, COLOR_BG, true);
        view.setGravity(Gravity.CENTER);
        view.setPadding(dp(10), dp(5), dp(10), dp(5));
        view.setBackground(rounded(color, dp(999), color));
        return view;
    }

    private TextView cardTitle(String text) {
        TextView view = text(text, 20, COLOR_TEXT, true);
        view.setPadding(0, 0, 0, dp(8));
        return view;
    }

    private TextView cardBody(String body) {
        TextView view = text(body, 16, COLOR_MUTED, false);
        view.setLineSpacing(0, 1.08f);
        return view;
    }

    private EditText input(String hint, String value) {
        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setHint(hint);
        input.setText(value);
        input.setTextSize(16);
        input.setTextColor(COLOR_TEXT);
        input.setHintTextColor(COLOR_MUTED);
        input.setBackground(rounded(COLOR_CARD_SOFT, dp(16), COLOR_BORDER));
        input.setPadding(dp(14), dp(12), dp(14), dp(12));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(10), 0, 0);
        input.setLayoutParams(params);
        return input;
    }

    private void addBottomNavigation(LinearLayout app) {
        LinearLayout nav = new LinearLayout(this);
        nav.setOrientation(LinearLayout.HORIZONTAL);
        nav.setGravity(Gravity.CENTER);
        nav.setPadding(dp(8), dp(8), dp(8), dp(8));
        nav.setBackgroundColor(COLOR_NAV_BG);

        addNavItem(nav, TAB_DASHBOARD, "⌂", "Home");
        addNavItem(nav, TAB_THEME, "◈", "Theme");
        addNavItem(nav, TAB_TOOLS, "⚙", "Tools");
        addNavItem(nav, TAB_SUPPORT, "▣", "Support");
        addNavItem(nav, TAB_SETTINGS, "●", "More");

        app.addView(nav, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(96)
        ));
    }

    private void addNavItem(LinearLayout nav, final int tab, String icon, String label) {
        boolean selected = currentTab == tab;
        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setGravity(Gravity.CENTER);
        item.setPadding(dp(4), dp(4), dp(4), dp(4));
        item.setBackground(selected ? rounded(COLOR_CARD_SOFT, dp(28), COLOR_BORDER) : null);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTab = tab;
                render();
            }
        });

        TextView iconView = text(icon, 24, selected ? COLOR_ACCENT : COLOR_MUTED, true);
        iconView.setGravity(Gravity.CENTER);
        TextView labelView = text(label, 12, selected ? COLOR_TEXT : COLOR_MUTED, true);
        labelView.setGravity(Gravity.CENTER);
        item.addView(iconView);
        item.addView(labelView);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        params.setMargins(dp(2), 0, dp(2), 0);
        nav.addView(item, params);
    }

    private void addHeaderPill(LinearLayout bar, String label, int color, View.OnClickListener listener) {
        TextView action = text(label, label.length() > 2 ? 12 : 18, COLOR_BG, true);
        action.setGravity(Gravity.CENTER);
        action.setPadding(dp(10), 0, dp(10), 0);
        action.setBackground(rounded(color, dp(999), color));
        action.setOnClickListener(listener);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(label.length() > 2 ? dp(62) : dp(44), dp(36));
        params.setMargins(dp(8), 0, 0, 0);
        bar.addView(action, params);
    }

    private View.OnClickListener refreshClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("Page refreshed");
                render();
            }
        };
    }

    private View.OnClickListener toastClick(final String message) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast(message);
            }
        };
    }

    private void addDivider() {
        View line = new View(this);
        line.setBackgroundColor(COLOR_BORDER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(1)
        );
        params.setMargins(0, dp(18), 0, dp(20));
        root.addView(line, params);
    }

    private void addSwitchRow(String title, String subtitle, final String key, boolean defaultValue) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(0, dp(16), 0, dp(16));

        LinearLayout left = new LinearLayout(this);
        left.setOrientation(LinearLayout.VERTICAL);
        left.addView(text(title, 18, COLOR_TEXT, true));
        if (subtitle != null && !subtitle.trim().isEmpty()) {
            TextView sub = text(subtitle, 14, COLOR_MUTED, false);
            sub.setPadding(0, dp(3), 0, 0);
            left.addView(sub);
        }
        row.addView(left, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        Switch option = new Switch(this);
        option.setChecked(prefBool(key, defaultValue));
        option.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveBool(key, isChecked);
                toast("Saved");
            }
        });
        row.addView(option);
        root.addView(row, matchWrap());
    }

    private void addSectionTitle(String title, String subtitle) {
        TextView titleView = text(title, 15, COLOR_ACCENT, true);
        titleView.setPadding(0, dp(10), 0, dp(4));
        root.addView(titleView, matchWrap());
        if (subtitle != null) {
            TextView subtitleView = text(subtitle, 14, COLOR_MUTED, false);
            subtitleView.setPadding(0, 0, 0, dp(14));
            root.addView(subtitleView, matchWrap());
        }
    }

    private void openImagePicker() {
        try {
            startActivityForResult(CustomizationManager.imagePickerIntent(), REQUEST_PICK_IMAGE);
        } catch (ActivityNotFoundException failure) {
            toast("No image picker found");
        }
    }

    private void applySelectedWallpaper(int target) {
        try {
            String message = CustomizationManager.applyWallpaper(this, target);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } catch (IOException failure) {
            Toast.makeText(this, "Wallpaper failed: image cannot be read", Toast.LENGTH_LONG).show();
        } catch (RuntimeException failure) {
            Toast.makeText(this, "Wallpaper failed on this device", Toast.LENGTH_LONG).show();
        }
    }

    private void openSettings(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException firstFailure) {
            try {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            } catch (ActivityNotFoundException secondFailure) {
                toast("Settings page not available on this device");
            }
        }
    }

    private void copyReport() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(
                    "ColorOS Themes Rock Support Report",
                    BatteryOptimizationAdvisor.supportReport(this) + "\n" + CustomizationManager.report(this)
            ));
            toast("Support report copied");
        }
    }

    private void shareReport() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ColorOS Themes Rock Support Report");
        shareIntent.putExtra(Intent.EXTRA_TEXT, BatteryOptimizationAdvisor.supportReport(this) + "\n" + CustomizationManager.report(this));
        try {
            startActivity(Intent.createChooser(shareIntent, "Share report"));
        } catch (ActivityNotFoundException failure) {
            toast("No share app found");
        }
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
        SharedPreferences prefs = getSharedPreferences(PREFS_UI, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, defaultValue);
    }

    private void saveBool(String key, boolean value) {
        getSharedPreferences(PREFS_UI, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    private void toast(String value) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
