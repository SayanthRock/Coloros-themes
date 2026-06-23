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
            int flags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            try {
                getContentResolver().takePersistableUriPermission(uri, flags & Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (SecurityException ignored) {
                // Some gallery apps do not provide persistable access. The URI can still work for the current session.
            }
            CustomizationManager.saveImageUri(this, uri);
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
            render();
        }
    }

    private void render() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(0xFF0F1117);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(20), dp(18), dp(24));
        scrollView.addView(root);

        addTitle("ColorOS Themes Rock");
        addSubtitle("Battery + Customization Helper");
        addCard("Device status", BatteryOptimizationAdvisor.supportReport(this));
        addCard("Optimization status", BatteryOptimizationAdvisor.optimizationStatus(this));
        addRecommendationCard(BatteryOptimizationAdvisor.recommendations(this));
        addCustomizationCenter();
        addBatteryButtons();

        addButton("Copy full support report", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyReport();
            }
        });

        addButton("Refresh status", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                render();
            }
        });

        addFooter();
        setContentView(scrollView);
    }

    private void addCustomizationCenter() {
        addCard(
                "Customization Center",
                "Open any image from your phone and apply it as Home screen, Lock screen, or both. OTA name/background and About phone labels are stored as safe helper options for preview and customer reports."
        );

        Uri imageUri = CustomizationManager.selectedImageUri(this);
        if (imageUri != null) {
            ImageView preview = new ImageView(this);
            preview.setAdjustViewBounds(true);
            preview.setMaxHeight(dp(220));
            preview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            preview.setImageURI(imageUri);
            GradientDrawable background = new GradientDrawable();
            background.setColor(0xFF191D27);
            background.setCornerRadius(dp(18));
            preview.setBackground(background);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp(220)
            );
            params.setMargins(0, 0, 0, dp(12));
            root.addView(preview, params);
        }

        addCard("Selected image", CustomizationManager.selectedImageLabel(this));

        addButton("Open image picker", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        addButton("Apply image to Home screen", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySelectedWallpaper(WallpaperManager.FLAG_SYSTEM);
            }
        });

        addButton("Apply image to Lock screen", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySelectedWallpaper(WallpaperManager.FLAG_LOCK);
            }
        });

        addButton("Apply image to Home + Lock screen", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySelectedWallpaper(WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
            }
        });

        addButton("Open Android wallpaper settings", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent("android.settings.WALLPAPER_SETTINGS"));
            }
        });

        addAboutPhoneOptions();
        addOtaOptions();
    }

    private void addAboutPhoneOptions() {
        LinearLayout card = cardContainer();
        card.addView(cardTitle("About phone customization"));
        card.addView(cardBody("Saved as a safe helper label for preview and support reports. Directly changing OEM About phone system identity is not enabled by default."));

        final EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setText(CustomizationManager.aboutPhoneName(this));
        input.setTextColor(0xFFFFFFFF);
        input.setHintTextColor(0xFF9EA4B3);
        input.setHint("About phone label");
        card.addView(input);

        Button save = smallButton("Save About phone label");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomizationManager.setAboutPhoneName(MainActivity.this, input.getText().toString());
                Toast.makeText(MainActivity.this, "About phone label saved", Toast.LENGTH_SHORT).show();
                render();
            }
        });
        card.addView(save);

        Button openSettings = smallButton("Open About phone settings");
        openSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS));
            }
        });
        card.addView(openSettings);

        root.addView(card);
    }

    private void addOtaOptions() {
        LinearLayout card = cardContainer();
        card.addView(cardTitle("OTA customization"));
        card.addView(cardBody("Use this for safe local OTA branding options. The default OTA name can be Sayanth Rock and can be turned on or off."));

        final EditText otaName = new EditText(this);
        otaName.setSingleLine(true);
        otaName.setText(CustomizationManager.otaName(this));
        otaName.setTextColor(0xFFFFFFFF);
        otaName.setHintTextColor(0xFF9EA4B3);
        otaName.setHint("OTA display name");
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

        Button save = smallButton("Save OTA customization");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomizationManager.setOtaName(MainActivity.this, otaName.getText().toString());
                Toast.makeText(MainActivity.this, "OTA customization saved", Toast.LENGTH_SHORT).show();
                render();
            }
        });
        card.addView(save);

        root.addView(card);
        addCard("Current customization report", CustomizationManager.report(this));
    }

    private void addBatteryButtons() {
        addButton("Open Battery Saver settings", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS));
            }
        });

        addButton("Open Battery Optimization list", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS));
            }
        });

        addButton("Open this app info", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                openSettings(intent);
            }
        });

        addButton("Open advanced app battery page", new View.OnClickListener() {
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

    private void addTitle(String text) {
        TextView title = new TextView(this);
        title.setText(text);
        title.setTextColor(0xFFFFFFFF);
        title.setTextSize(28);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.START);
        root.addView(title, matchWrap());
    }

    private void addSubtitle(String text) {
        TextView subtitle = new TextView(this);
        subtitle.setText(text);
        subtitle.setTextColor(0xFFE2B884);
        subtitle.setTextSize(16);
        subtitle.setPadding(0, dp(4), 0, dp(16));
        root.addView(subtitle, matchWrap());
    }

    private void addCard(String title, String body) {
        LinearLayout card = cardContainer();
        TextView titleView = cardTitle(title);
        TextView bodyView = cardBody(body);
        card.addView(titleView);
        card.addView(bodyView);
        root.addView(card);
    }

    private void addRecommendationCard(List<String> recommendations) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < recommendations.size(); i++) {
            builder.append(i + 1).append(". ").append(recommendations.get(i));
            if (i < recommendations.size() - 1) {
                builder.append("\n\n");
            }
        }
        addCard("Safe battery fixes", builder.toString());
    }

    private LinearLayout cardContainer() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(14), dp(16), dp(14));
        GradientDrawable background = new GradientDrawable();
        background.setColor(0xFF191D27);
        background.setCornerRadius(dp(18));
        background.setStroke(dp(1), 0x33E2B884);
        card.setBackground(background);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, dp(12));
        card.setLayoutParams(params);
        return card;
    }

    private TextView cardTitle(String text) {
        TextView title = new TextView(this);
        title.setText(text);
        title.setTextColor(0xFFFFFFFF);
        title.setTextSize(17);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setPadding(0, 0, 0, dp(8));
        return title;
    }

    private TextView cardBody(String text) {
        TextView body = new TextView(this);
        body.setText(text);
        body.setTextColor(0xFFD7DAE0);
        body.setTextSize(14);
        body.setLineSpacing(0, 1.12f);
        return body;
    }

    private void addButton(String text, View.OnClickListener listener) {
        Button button = smallButton(text);
        button.setOnClickListener(listener);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(4), 0, dp(8));
        root.addView(button, params);
    }

    private Button smallButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setAllCaps(false);
        button.setTextColor(0xFF111318);
        button.setTextSize(15);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setBackgroundColor(0xFFE2B884);
        return button;
    }

    private Switch optionSwitch(String text, boolean checked) {
        Switch option = new Switch(this);
        option.setText(text);
        option.setTextColor(0xFFD7DAE0);
        option.setTextSize(14);
        option.setChecked(checked);
        option.setPadding(0, dp(8), 0, dp(8));
        return option;
    }

    private void addFooter() {
        TextView footer = new TextView(this);
        footer.setText("Safe mode: no hidden tracking, no paid-theme bypass, no aggressive task killer, no unsafe root battery hacks.");
        footer.setTextColor(0xFF9EA4B3);
        footer.setTextSize(12);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(0, dp(16), 0, 0);
        root.addView(footer, matchWrap());
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
