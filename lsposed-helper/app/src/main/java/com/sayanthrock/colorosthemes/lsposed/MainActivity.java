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
import java.util.List;

/**
 * Light, card-based customer dashboard inspired by modern ColorOS module tools.
 */
public class MainActivity extends Activity {

    private static final int REQUEST_PICK_IMAGE = 1001;

    private static final int TAB_OTHER = 0;
    private static final int TAB_FUNCTION = 1;
    private static final int TAB_HOME = 2;
    private static final int TAB_LOGS = 3;
    private static final int TAB_SETTINGS = 4;

    private static final int COLOR_BG = 0xFFF9F9FF;
    private static final int COLOR_NAV_BG = 0xFFEFF2FF;
    private static final int COLOR_CARD = 0x00FFFFFF;
    private static final int COLOR_CARD_SOFT = 0xFFF7F8FF;
    private static final int COLOR_TEXT = 0xFF20232B;
    private static final int COLOR_MUTED = 0xFF626777;
    private static final int COLOR_FAINT = 0xFF8A8FA0;
    private static final int COLOR_BORDER = 0xFFC9CCD8;
    private static final int COLOR_BLUE = 0xFF075DD8;
    private static final int COLOR_BLUE_DARK = 0xFF084FB4;
    private static final int COLOR_BLUE_SOFT = 0xFFE8EDFF;
    private static final int COLOR_SUCCESS = 0xFF34A853;

    private static final String PREFS_UI = "coloros_customizer_ui";

    private LinearLayout root;
    private int currentTab = TAB_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            currentTab = TAB_HOME;
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
        root.setPadding(dp(30), dp(28), dp(30), dp(28));
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
        if (currentTab == TAB_OTHER) {
            renderOtherPage();
        } else if (currentTab == TAB_FUNCTION) {
            renderFunctionPage();
        } else if (currentTab == TAB_LOGS) {
            renderLogsPage();
        } else if (currentTab == TAB_SETTINGS) {
            renderSettingsPage();
        } else {
            renderHomePage();
        }
    }

    private void addTopBar() {
        LinearLayout bar = new LinearLayout(this);
        bar.setOrientation(LinearLayout.HORIZONTAL);
        bar.setGravity(Gravity.CENTER_VERTICAL);
        bar.setPadding(0, dp(8), 0, dp(32));

        TextView back = new TextView(this);
        back.setText("‹");
        back.setTextColor(COLOR_TEXT);
        back.setTextSize(42);
        back.setGravity(Gravity.CENTER);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTab = TAB_HOME;
                render();
            }
        });
        bar.addView(back, new LinearLayout.LayoutParams(dp(54), dp(54)));

        TextView title = new TextView(this);
        title.setText(titleForTab());
        title.setTextColor(COLOR_TEXT);
        title.setTextSize(currentTab == TAB_HOME ? 31 : 30);
        title.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        title.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, dp(54), 1f);
        titleParams.setMargins(dp(14), 0, 0, 0);
        bar.addView(title, titleParams);

        if (currentTab == TAB_FUNCTION) {
            addHeaderAction(bar, "⌕", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toast("Search ready for function list");
                }
            });
            addHeaderAction(bar, "↻", refreshClick());
            addHeaderAction(bar, "✚", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toast("Add custom function coming soon");
                }
            });
        } else if (currentTab == TAB_LOGS) {
            addHeaderAction(bar, "↻", refreshClick());
            addHeaderAction(bar, "≡", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toast("Log filter ready");
                }
            });
            addHeaderAction(bar, "▣", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    copyReport();
                }
            });
            addHeaderAction(bar, "⌯", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareReport();
                }
            });
        } else if (currentTab == TAB_HOME) {
            addHeaderAction(bar, "↻", refreshClick());
            addHeaderAction(bar, "i", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentTab = TAB_LOGS;
                    render();
                }
            });
        } else {
            addHeaderAction(bar, currentTab == TAB_OTHER ? "✚" : "↻", refreshClick());
        }

        root.addView(bar, matchWrap());
    }

    private String titleForTab() {
        if (currentTab == TAB_OTHER) return "Other";
        if (currentTab == TAB_FUNCTION) return "Function";
        if (currentTab == TAB_LOGS) return "Logs";
        if (currentTab == TAB_SETTINGS) return "Settings";
        return "ColorOS Rock";
    }

    private void renderHomePage() {
        addStatusCard();
        addDeviceCard();
        addSupportCard();
        addSectionTitle("Quick actions", "Wallpaper, settings, and customer support tools");
        addActionButton("Open image picker", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });
        addActionButton("Apply image to Home + Lock screen", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySelectedWallpaper(WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
            }
        });
        addActionButton("Open Android wallpaper settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent("android.settings.WALLPAPER_SETTINGS"));
            }
        });
        addSelectedImagePreview();
    }

    private void addStatusCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(28), dp(26), dp(28), dp(26));
        card.setBackground(rounded(COLOR_BLUE, dp(16), COLOR_BLUE));

        TextView check = new TextView(this);
        check.setText("✓");
        check.setTextSize(30);
        check.setTypeface(Typeface.DEFAULT_BOLD);
        check.setTextColor(0xFFFFFFFF);
        check.setGravity(Gravity.CENTER);
        card.addView(check, new LinearLayout.LayoutParams(dp(54), dp(54)));

        LinearLayout textBox = new LinearLayout(this);
        textBox.setOrientation(LinearLayout.VERTICAL);
        textBox.setPadding(dp(18), 0, 0, 0);
        TextView title = text("Module ready", 21, 0xFFFFFFFF, true);
        TextView body = text("APK version: 0.4.1 RELEASE\nFramework: LSPosed compatible\nMode: Safe customization dashboard", 15, 0xFFE8F0FF, false);
        textBox.addView(title);
        textBox.addView(body);
        card.addView(textBox, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        root.addView(card, cardParams());
    }

    private void addDeviceCard() {
        addPlainCard(BatteryOptimizationAdvisor.supportReport(this));
    }

    private void addSupportCard() {
        addPlainCard("Support Development by: Sayanth Rock\nColorOS Themes Rock provides safe theme, wallpaper, battery, settings, and support tools for OPPO, OnePlus, and realme devices.");
    }

    private void renderFunctionPage() {
        addFunctionRow("A", "Android System", "Allow trusted touch options\nSet LTPO refresh rate mode", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
            }
        });
        addFunctionRow("S", "StatusBar Related", "StatusBar notifications, icon, clock, and blur helper options", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTab = TAB_SETTINGS;
                render();
            }
        });
        addFunctionRow("D", "Desktop Related", "App badge, folder layout, wallpaper, and home layout shortcuts", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_HOME_SETTINGS));
            }
        });
        addFunctionRow("AOD", "Aod Related", "AOD music and notification icon compatibility notes", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("AOD options require device testing");
            }
        });
        addFunctionRow("L", "Lockscreen Related", "Lock screen clock, wallpaper, and bottom shortcut guidance", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySelectedWallpaper(WallpaperManager.FLAG_LOCK);
            }
        });
        addFunctionRow("APP", "Application related", "App scan helper, startup notes, and package settings shortcuts", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
            }
        });
        addFunctionRow("M", "Miscellaneous", "Difficult or experimental categories will be placed here", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("Miscellaneous tools ready");
            }
        });
    }

    private void renderOtherPage() {
        addSmallCard("System Quick Entry", "eg. System UI reconciliation tools, process management, engineering mode, etc.", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_SETTINGS));
            }
        });
        addSmallCard("Module built-in tile list", "quickly add tile to the control center", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("Tile list helper ready");
            }
        });
        addSmallCard("Set module shortcuts", "Long press on module icon to show enabled shortcuts", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("Shortcut setup ready");
            }
        });
        addSmallCard("Force refresh rate", "Conflict with other dynamic refresh rate processes, such as dpf/sfps", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
            }
        });
        addSmallCard("Set the touch sampling rate tile level", "Test by yourself whether each gear can be triggered normally. Game assistant may override sampling rate.", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("Touch sampling tile note saved");
            }
        });
        addSmallCard("Remote ADB debugging", "Please close it when not in use", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
            }
        });
    }

    private void renderLogsPage() {
        SpaceBlock(dp(260));
        TextView message = text("重新规划中......", 20, COLOR_MUTED, false);
        message.setGravity(Gravity.CENTER);
        root.addView(message, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        SpaceBlock(dp(40));
        addPlainCard("Latest report\n" + BatteryOptimizationAdvisor.supportReport(this) + "\n" + CustomizationManager.report(this));
    }

    private void renderSettingsPage() {
        addSectionTitle("Theme", "Page will be automatically refreshed");
        addSwitchRow("Dynamic colors", "Use dynamic colors", "dynamic_colors", true);
        addSwitchRow("Dark theme", "Follow system", "dark_theme", false);

        addDivider();
        addSectionTitle("Other settings", null);
        addSwitchRow("Automatically check for updates", "Automatically check for updates when opening the module home page", "auto_update", true);
        addSwitchRow("Enable biometric unlock verification", "Require biometric verification before sensitive options", "biometric", false);
        addSwitchRow("Allow built-in tiles to start automatically", "Enabled tile features will be triggered after the screen is unlocked. If the trigger fails, restart the scope and check permissions.", "auto_tiles", true);

        addSectionTitle("Customization", "Safe local options");
        addStatusBarBlurSettingsCard();
        addAboutAndOtaCard();
    }

    private void addStatusBarBlurSettingsCard() {
        LinearLayout card = card(false);
        card.addView(cardTitle("Status bar blur"));
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
        card.addView(cardTitle("About phone and OTA style"));
        card.addView(cardBody("Local helper labels are saved for preview and support reports."));

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

    private void addFunctionRow(String icon, String title, String subtitle, View.OnClickListener listener) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(0, dp(12), 0, dp(18));
        row.setOnClickListener(listener);

        TextView iconView = new TextView(this);
        iconView.setText(icon);
        iconView.setTextSize(icon.length() > 1 ? 14 : 23);
        iconView.setTypeface(Typeface.DEFAULT_BOLD);
        iconView.setGravity(Gravity.CENTER);
        iconView.setTextColor(0xFFFFFFFF);
        iconView.setBackground(rounded(icon.equals("A") ? 0xFF88C253 : COLOR_BLUE, dp(12), icon.equals("A") ? 0xFF88C253 : COLOR_BLUE));
        row.addView(iconView, new LinearLayout.LayoutParams(dp(92), dp(92)));

        LinearLayout texts = new LinearLayout(this);
        texts.setOrientation(LinearLayout.VERTICAL);
        texts.setPadding(dp(18), 0, 0, 0);
        texts.addView(text(title, 21, COLOR_TEXT, true));
        texts.addView(text(subtitle, 17, COLOR_MUTED, false));
        row.addView(texts, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        root.addView(row, matchWrap());
    }

    private void addSmallCard(String title, String subtitle, View.OnClickListener listener) {
        LinearLayout card = card(false);
        card.setOnClickListener(listener);
        card.setPadding(dp(18), dp(16), dp(18), dp(16));
        card.addView(text(title, 19, COLOR_MUTED, false));
        TextView body = text(subtitle, 15, COLOR_MUTED, false);
        body.setPadding(0, dp(4), 0, 0);
        card.addView(body);
        root.addView(card, cardParams());
    }

    private void addSwitchRow(String title, String subtitle, final String key, boolean defaultValue) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(0, dp(18), 0, dp(18));

        LinearLayout left = new LinearLayout(this);
        left.setOrientation(LinearLayout.VERTICAL);
        left.addView(text(title, 21, COLOR_TEXT, true));
        if (subtitle != null && !subtitle.trim().isEmpty()) {
            TextView sub = text(subtitle, 16, COLOR_MUTED, false);
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
            }
        });
        row.addView(option);
        root.addView(row, matchWrap());
    }

    private void addSectionTitle(String title, String subtitle) {
        TextView titleView = text(title, 18, COLOR_FAINT, true);
        titleView.setPadding(0, dp(10), 0, dp(4));
        root.addView(titleView, matchWrap());
        if (subtitle != null) {
            TextView subtitleView = text(subtitle, 16, COLOR_MUTED, false);
            subtitleView.setPadding(0, 0, 0, dp(18));
            root.addView(subtitleView, matchWrap());
        }
    }

    private void addSelectedImagePreview() {
        Uri imageUri = CustomizationManager.selectedImageUri(this);
        if (imageUri == null) {
            return;
        }
        LinearLayout card = card(false);
        card.addView(cardTitle("Selected image preview"));
        ImageView preview = new ImageView(this);
        preview.setAdjustViewBounds(true);
        preview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        preview.setImageURI(imageUri);
        preview.setBackground(rounded(COLOR_CARD_SOFT, dp(18), COLOR_BORDER));
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(220)
        );
        imageParams.setMargins(0, dp(8), 0, dp(10));
        card.addView(preview, imageParams);
        card.addView(cardBody(CustomizationManager.selectedImageLabel(this)));
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
        params.setMargins(0, dp(10), 0, 0);
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
        button.setTextColor(primary ? 0xFFFFFFFF : COLOR_TEXT);
        button.setBackground(rounded(primary ? COLOR_BLUE : COLOR_BLUE_SOFT, dp(18), primary ? COLOR_BLUE_DARK : COLOR_BLUE_SOFT));
        return button;
    }

    private LinearLayout card(boolean elevated) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(18), dp(18), dp(18));
        card.setBackground(rounded(elevated ? COLOR_CARD_SOFT : COLOR_CARD, dp(18), COLOR_BORDER));
        return card;
    }

    private TextView cardTitle(String text) {
        TextView view = text(text, 20, COLOR_TEXT, true);
        view.setPadding(0, 0, 0, dp(8));
        return view;
    }

    private TextView cardBody(String body) {
        TextView view = text(body, 17, COLOR_MUTED, false);
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
        input.setHintTextColor(COLOR_FAINT);
        input.setBackground(rounded(COLOR_CARD_SOFT, dp(14), COLOR_BORDER));
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
        nav.setPadding(dp(10), dp(10), dp(10), dp(10));
        nav.setBackgroundColor(COLOR_NAV_BG);

        addNavItem(nav, TAB_OTHER, "▦", "Other");
        addNavItem(nav, TAB_FUNCTION, "✚", "Function");
        addNavItem(nav, TAB_HOME, "⌂", "Home page");
        addNavItem(nav, TAB_LOGS, "▣", "Logs");
        addNavItem(nav, TAB_SETTINGS, "⚙", "Settings");

        app.addView(nav, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(118)
        ));
    }

    private void addNavItem(LinearLayout nav, final int tab, String icon, String label) {
        boolean selected = currentTab == tab;
        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setGravity(Gravity.CENTER);
        item.setPadding(dp(6), dp(4), dp(6), dp(4));
        item.setBackground(selected ? rounded(COLOR_BLUE_SOFT, dp(28), COLOR_BLUE_SOFT) : null);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTab = tab;
                render();
            }
        });

        TextView iconView = text(icon, 28, selected ? COLOR_TEXT : COLOR_MUTED, true);
        iconView.setGravity(Gravity.CENTER);
        TextView labelView = text(label, 13, selected ? COLOR_TEXT : COLOR_MUTED, true);
        labelView.setGravity(Gravity.CENTER);
        item.addView(iconView);
        item.addView(labelView);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        params.setMargins(dp(2), 0, dp(2), 0);
        nav.addView(item, params);
    }

    private void addHeaderAction(LinearLayout bar, String label, View.OnClickListener listener) {
        TextView action = new TextView(this);
        action.setText(label);
        action.setTextColor(0xFF000000);
        action.setTextSize(label.length() == 1 ? 29 : 24);
        action.setTypeface(Typeface.DEFAULT_BOLD);
        action.setGravity(Gravity.CENTER);
        action.setOnClickListener(listener);
        bar.addView(action, new LinearLayout.LayoutParams(dp(52), dp(54)));
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

    private void addDivider() {
        View line = new View(this);
        line.setBackgroundColor(0xFFE2E4EE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(1)
        );
        params.setMargins(0, dp(20), 0, dp(24));
        root.addView(line, params);
    }

    private void SpaceBlock(int height) {
        View view = new View(this);
        root.addView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                height
        ));
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
        params.setMargins(0, 0, 0, dp(20));
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
