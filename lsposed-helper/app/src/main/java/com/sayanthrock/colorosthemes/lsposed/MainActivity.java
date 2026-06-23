package com.sayanthrock.colorosthemes.lsposed;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
 * Customer dashboard for safe battery, wallpaper, OTA, and theme support guidance.
 */
public class MainActivity extends Activity {

    private static final int REQUEST_PICK_IMAGE = 1001;

    private static final int COLOR_BG = 0xFF090B10;
    private static final int COLOR_SURFACE = 0xFF141922;
    private static final int COLOR_SURFACE_ALT = 0xFF1A2130;
    private static final int COLOR_SURFACE_ELEVATED = 0xFF101621;
    private static final int COLOR_ACCENT = 0xFFE2B884;
    private static final int COLOR_ACCENT_SOFT = 0x26E2B884;
    private static final int COLOR_TEXT = 0xFFFFFFFF;
    private static final int COLOR_TEXT_SECONDARY = 0xFFD7DAE0;
    private static final int COLOR_TEXT_MUTED = 0xFF98A1B3;
    private static final int COLOR_DARK_BUTTON_TEXT = 0xFF101319;

    private LinearLayout root;

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
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
            render();
        }
    }

    private void render() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(COLOR_BG);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(20), dp(18), dp(28));
        scrollView.addView(root);

        addHeroCard();
        addQuickStatsRow();

        addSectionHeader("Status Overview", "Device details, optimization state, and support basics");
        addCard("Device status", BatteryOptimizationAdvisor.supportReport(this));
        addCard("Optimization status", BatteryOptimizationAdvisor.optimizationStatus(this));
        addRecommendationCard(BatteryOptimizationAdvisor.recommendations(this));

        addSectionHeader("Customization Center", "Wallpapers, About phone label, and OTA style options");
        addThemeStoreSafetyCard();
        addCustomizationCenter();

        addSectionHeader("Battery Tools", "Quick access to the important Android battery screens");
        addBatteryButtons();

        addSectionHeader("Support", "Copy a full report or refresh everything after a change");
        addButton("Copy full support report", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyReport();
            }
        });

        addButton("Refresh status", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                render();
            }
        });

        addFooter();
        setContentView(scrollView);
    }

    private void addHeroCard() {
        LinearLayout hero = cardContainer(true);

        TextView overline = new TextView(this);
        overline.setText("SAYANTH ROCK EDITION");
        overline.setTextColor(COLOR_ACCENT);
        overline.setTextSize(12);
        overline.setTypeface(Typeface.DEFAULT_BOLD);
        overline.setLetterSpacing(0.08f);
        hero.addView(overline);

        TextView title = new TextView(this);
        title.setText("ColorOS Customizer");
        title.setTextColor(COLOR_TEXT);
        title.setTextSize(31);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setPadding(0, dp(8), 0, dp(6));
        hero.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("New UI polish update with cleaner sections, stronger hierarchy, smoother card layout, and a more premium safe customization dashboard.");
        subtitle.setTextColor(COLOR_TEXT_SECONDARY);
        subtitle.setTextSize(14);
        subtitle.setLineSpacing(0, 1.14f);
        hero.addView(subtitle);

        LinearLayout badges = new LinearLayout(this);
        badges.setOrientation(LinearLayout.HORIZONTAL);
        badges.setPadding(0, dp(14), 0, 0);
        badges.addView(createBadge("v0.3.2"));
        badges.addView(createBadge("UI Polish"));
        badges.addView(createBadge("Safe Mode"));
        hero.addView(badges);

        root.addView(hero);
    }

    private void addQuickStatsRow() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setWeightSum(3f);

        row.addView(createStatCard("Wallpaper", "Home / Lock"), weightedCardParams());
        row.addView(createStatCard("OTA", "Sayanth Rock"), weightedCardParams());
        row.addView(createStatCard("Mode", "Customer Safe"), weightedCardParams());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, dp(14));
        row.setLayoutParams(params);
        root.addView(row);
    }

    private LinearLayout createStatCard(String label, String value) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(14), dp(14), dp(14), dp(14));
        card.setBackground(roundedDrawable(COLOR_SURFACE_ELEVATED, dp(18), 0x24E2B884));

        TextView labelView = new TextView(this);
        labelView.setText(label);
        labelView.setTextColor(COLOR_TEXT_MUTED);
        labelView.setTextSize(12);
        labelView.setTypeface(Typeface.DEFAULT_BOLD);
        card.addView(labelView);

        TextView valueView = new TextView(this);
        valueView.setText(value);
        valueView.setTextColor(COLOR_TEXT);
        valueView.setTextSize(15);
        valueView.setTypeface(Typeface.DEFAULT_BOLD);
        valueView.setPadding(0, dp(6), 0, 0);
        card.addView(valueView);

        return card;
    }

    private LinearLayout.LayoutParams weightedCardParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        );
        params.setMargins(0, 0, dp(8), 0);
        return params;
    }

    private void addSectionHeader(String title, String subtitle) {
        TextView titleView = new TextView(this);
        titleView.setText(title);
        titleView.setTextColor(COLOR_TEXT);
        titleView.setTextSize(20);
        titleView.setTypeface(Typeface.DEFAULT_BOLD);
        titleView.setPadding(0, dp(10), 0, dp(2));
        root.addView(titleView, matchWrap());

        TextView subtitleView = new TextView(this);
        subtitleView.setText(subtitle);
        subtitleView.setTextColor(COLOR_TEXT_MUTED);
        subtitleView.setTextSize(13);
        subtitleView.setPadding(0, 0, 0, dp(10));
        root.addView(subtitleView, matchWrap());
    }

    private void addThemeStoreSafetyCard() {
        LinearLayout card = cardContainer(false);
        card.addView(cardTitle("Theme Store compatibility"));
        card.addView(cardBody("This update improves style and usability, but it does not unlock paid or protected Theme Store features. Use your own assets and OEM-supported options only."));
        root.addView(card);
    }

    private void addCustomizationCenter() {
        addCard(
                "Customization Center",
                "Open any image from your phone and apply it as Home screen, Lock screen, or both. About phone and OTA options are saved as safe helper settings with a cleaner UI flow."
        );

        Uri imageUri = CustomizationManager.selectedImageUri(this);
        if (imageUri != null) {
            LinearLayout imageCard = cardContainer(false);
            imageCard.addView(cardTitle("Selected image preview"));

            ImageView preview = new ImageView(this);
            preview.setAdjustViewBounds(true);
            preview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            preview.setImageURI(imageUri);
            preview.setBackground(roundedDrawable(COLOR_SURFACE_ALT, dp(18), 0x33E2B884));

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp(220)
            );
            imageParams.setMargins(0, 0, 0, dp(12));
            imageCard.addView(preview, imageParams);
            imageCard.addView(cardBody(CustomizationManager.selectedImageLabel(this)));
            root.addView(imageCard);
        }

        addButton("Open image picker", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        addButton("Apply image to Home screen", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySelectedWallpaper(WallpaperManager.FLAG_SYSTEM);
            }
        });

        addButton("Apply image to Lock screen", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySelectedWallpaper(WallpaperManager.FLAG_LOCK);
            }
        });

        addButton("Apply image to Home + Lock screen", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySelectedWallpaper(WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
            }
        });

        addButton("Open Android wallpaper settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent("android.settings.WALLPAPER_SETTINGS"));
            }
        });

        addAboutPhoneOptions();
        addOtaOptions();
    }

    private void addAboutPhoneOptions() {
        LinearLayout card = cardContainer(false);
        card.addView(cardTitle("About phone customization"));
        card.addView(cardBody("Save a polished helper label for preview and support reports. System identity changes are still disabled by default for safety."));

        final EditText input = createInput("About phone label", CustomizationManager.aboutPhoneName(this));
        card.addView(input);

        card.addView(cardButton("Save About phone label", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomizationManager.setAboutPhoneName(MainActivity.this, input.getText().toString());
                Toast.makeText(MainActivity.this, "About phone label saved", Toast.LENGTH_SHORT).show();
                render();
            }
        }));

        card.addView(cardButton("Open About phone settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS));
            }
        }));

        root.addView(card);
    }

    private void addOtaOptions() {
        LinearLayout card = cardContainer(false);
        card.addView(cardTitle("OTA customization"));
        card.addView(cardBody("Use this clean settings card for local OTA branding. The default OTA name is Sayanth Rock and can be turned on or off."));

        final EditText otaName = createInput("OTA display name", CustomizationManager.otaName(this));
        card.addView(otaName);

        Switch otaNameSwitch = optionSwitch("Turn on OTA name", CustomizationManager.otaBrandingEnabled(this));
        otaNameSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CustomizationManager.setOtaBrandingEnabled(MainActivity.this, isChecked);
            }
        });
        card.addView(otaNameSwitch);

        Switch otaBackgroundSwitch = optionSwitch("Use selected image as OTA background", CustomizationManager.otaBackgroundEnabled(this));
        otaBackgroundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CustomizationManager.setOtaBackgroundEnabled(MainActivity.this, isChecked);
            }
        });
        card.addView(otaBackgroundSwitch);

        card.addView(cardButton("Save OTA customization", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomizationManager.setOtaName(MainActivity.this, otaName.getText().toString());
                Toast.makeText(MainActivity.this, "OTA customization saved", Toast.LENGTH_SHORT).show();
                render();
            }
        }));

        root.addView(card);
        addCard("Current customization report", CustomizationManager.report(this));
    }

    private void addBatteryButtons() {
        addButton("Open Battery Saver settings", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS));
            }
        });

        addButton("Open Battery Optimization list", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS));
            }
        });

        addButton("Open this app info", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                openSettings(intent);
            }
        });

        addButton("Open advanced app battery page", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.APP_BATTERY_SETTINGS");
                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                openSettings(intent);
            }
        });
    }

    private void openImagePicker() {
        try {
            startActivityForResult(CustomizationManager.imagePickerIntent(), REQUEST_PICK_IMAGE);
        } catch (ActivityNotFoundException failure) {
            Toast.makeText(this, "No image picker found", Toast.LENGTH_SHORT).show();
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

    private void addCard(String title, String body) {
        LinearLayout card = cardContainer(false);
        card.addView(cardTitle(title));
        card.addView(cardBody(body));
        root.addView(card);
    }

    private void addRecommendationCard(List<String> recommendations) {
        LinearLayout card = cardContainer(false);
        card.addView(cardTitle("Safe battery fixes"));
        for (int i = 0; i < recommendations.size(); i++) {
            TextView bullet = cardBody("• " + recommendations.get(i));
            if (i < recommendations.size() - 1) {
                bullet.setPadding(0, 0, 0, dp(10));
            }
            card.addView(bullet);
        }
        root.addView(card);
    }

    private LinearLayout cardContainer(boolean hero) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(hero ? 18 : 16), dp(18), dp(hero ? 18 : 16));
        card.setBackground(roundedDrawable(hero ? COLOR_SURFACE_ALT : COLOR_SURFACE, dp(hero ? 24 : 20), 0x33E2B884));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, dp(12));
        card.setLayoutParams(params);
        return card;
    }

    private GradientDrawable roundedDrawable(int fillColor, int radius, int strokeColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(fillColor);
        drawable.setCornerRadius(radius);
        drawable.setStroke(dp(1), strokeColor);
        return drawable;
    }

    private TextView createBadge(String text) {
        TextView badge = new TextView(this);
        badge.setText(text);
        badge.setTextColor(COLOR_ACCENT);
        badge.setTextSize(12);
        badge.setTypeface(Typeface.DEFAULT_BOLD);
        badge.setGravity(Gravity.CENTER);
        badge.setBackground(roundedDrawable(COLOR_ACCENT_SOFT, dp(999), 0x44E2B884));
        badge.setPadding(dp(12), dp(8), dp(12), dp(8));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, dp(8), 0);
        badge.setLayoutParams(params);
        return badge;
    }

    private TextView cardTitle(String text) {
        TextView title = new TextView(this);
        title.setText(text);
        title.setTextColor(COLOR_TEXT);
        title.setTextSize(18);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setPadding(0, 0, 0, dp(8));
        return title;
    }

    private TextView cardBody(String text) {
        TextView body = new TextView(this);
        body.setText(text);
        body.setTextColor(COLOR_TEXT_SECONDARY);
        body.setTextSize(14);
        body.setLineSpacing(0, 1.14f);
        return body;
    }

    private EditText createInput(String hint, String value) {
        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setHint(hint);
        input.setText(value);
        input.setTextColor(COLOR_TEXT);
        input.setHintTextColor(COLOR_TEXT_MUTED);
        input.setBackground(roundedDrawable(COLOR_SURFACE_ELEVATED, dp(16), 0x26E2B884));
        input.setPadding(dp(16), dp(14), dp(16), dp(14));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(12), 0, dp(10));
        input.setLayoutParams(params);
        return input;
    }

    private void addButton(String text, boolean primary, View.OnClickListener listener) {
        Button button = createButton(text, primary);
        button.setOnClickListener(listener);
        root.addView(button, fullButtonParams());
    }

    private Button cardButton(String text, boolean primary, View.OnClickListener listener) {
        Button button = createButton(text, primary);
        button.setOnClickListener(listener);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(4), 0, dp(8));
        button.setLayoutParams(params);
        return button;
    }

    private Button createButton(String text, boolean primary) {
        Button button = new Button(this);
        button.setText(text);
        button.setAllCaps(false);
        button.setTextSize(15);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setPadding(dp(16), dp(14), dp(16), dp(14));
        if (primary) {
            button.setTextColor(COLOR_DARK_BUTTON_TEXT);
            button.setBackground(roundedDrawable(COLOR_ACCENT, dp(18), COLOR_ACCENT));
        } else {
            button.setTextColor(COLOR_TEXT);
            button.setBackground(roundedDrawable(COLOR_SURFACE_ALT, dp(18), 0x33E2B884));
        }
        return button;
    }

    private LinearLayout.LayoutParams fullButtonParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(4), 0, dp(8));
        return params;
    }

    private Switch optionSwitch(String text, boolean checked) {
        Switch option = new Switch(this);
        option.setText(text);
        option.setTextColor(COLOR_TEXT_SECONDARY);
        option.setTextSize(14);
        option.setChecked(checked);
        option.setPadding(0, dp(8), 0, dp(8));
        return option;
    }

    private void addFooter() {
        LinearLayout footer = cardContainer(false);
        TextView footerTitle = cardTitle("Safe mode");
        TextView footerBody = cardBody("No hidden tracking, no paid-theme bypass, no aggressive task killer, and no unsafe root battery hacks. This update focuses on cleaner UI design and safer customization flow.");
        footer.addView(footerTitle);
        footer.addView(footerBody);
        root.addView(footer);
    }

    private void copyReport() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(
                    "ColorOS Themes Rock Support Report",
                    BatteryOptimizationAdvisor.supportReport(this) + "\n" + CustomizationManager.report(this)
            ));
            Toast.makeText(this, "Support report copied", Toast.LENGTH_SHORT).show();
        }
    }

    private void openSettings(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException firstFailure) {
            try {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            } catch (ActivityNotFoundException secondFailure) {
                Toast.makeText(this, "Settings page not available on this device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
