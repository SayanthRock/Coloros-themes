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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

/**
 * Working-only customer dashboard for ColorOS Themes Rock.
 */
public class MainActivity extends Activity {

    private static final int REQUEST_PICK_IMAGE = 1001;

    private static final int COLOR_BG = 0xFF0F0F10;
    private static final int COLOR_CARD = 0xFF1A1A1D;
    private static final int COLOR_CARD_SOFT = 0xFF242429;
    private static final int COLOR_ACCENT = 0xFFE2B884;
    private static final int COLOR_TEXT = 0xFFF5F2EA;
    private static final int COLOR_MUTED = 0xFFB9B1A3;
    private static final int COLOR_BORDER = 0x4DFFFFFF;
    private static final int COLOR_SUCCESS = 0xFF8FD694;
    private static final int COLOR_WARNING = 0xFFFFCC66;
    private static final int COLOR_DANGER = 0xFFFF7A7A;

    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(COLOR_BG);
        getWindow().setNavigationBarColor(COLOR_BG);
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
                    // Some picker apps do not support persistable access.
                }
            }
            CustomizationManager.saveImageUri(this, uri);
            toast("Image selected");
            render();
        }
    }

    private void render() {
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

        addHeader();
        addWorkingOnlyDashboard();
        addThemeTools();
        addPerformanceLevel();
        addSystemImprovement();
        addSupportAndRollback();
        addDeviceReport();

        setContentView(scrollView);
    }

    private void addHeader() {
        TextView title = text("ColorOS Themes Rock", 30, COLOR_TEXT, true);
        root.addView(title, matchWrap());

        TextView subtitle = text("Working-only APK tools for OPPO, OnePlus, and realme customization.", 15, COLOR_MUTED, false);
        subtitle.setPadding(0, dp(6), 0, dp(18));
        root.addView(subtitle, matchWrap());
    }

    private void addWorkingOnlyDashboard() {
        LinearLayout card = card(true);
        card.addView(badge("Working only", COLOR_SUCCESS));
        card.addView(cardTitle("Safe customer dashboard"));
        card.addView(cardBody("Problematic, unsupported, and untested features are not shown as working. Keep only safe tools, clear status labels, and rollback guidance."));
        card.addView(cardButton("Refresh status", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                render();
                toast("Status refreshed");
            }
        }));
        root.addView(card, cardParams());
    }

    private void addThemeTools() {
        addSectionTitle("Theme tools", "Safe tools stay available");
        addFeatureCard("Select wallpaper", "Choose an image from the Android picker.", "Working", COLOR_SUCCESS, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });
        addFeatureCard("Apply wallpaper", "Apply selected image to Home and Lock screen where Android allows it.", "Working", COLOR_SUCCESS, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySelectedWallpaper(WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
            }
        });
        addFeatureCard("Theme package import", "Disabled until ownership, format, and compatibility are verified.", "Needs testing", COLOR_WARNING, toastClick("Theme import is disabled until verified"));
        addFeatureCard("Problematic ZIP assets", "Compiled APK, Xposed metadata, native binaries, and signing files are blocked from import.", "Blocked", COLOR_DANGER, toastClick("Unsafe ZIP assets are blocked"));
        addSelectedImagePreview();
    }

    private void addPerformanceLevel() {
        addSectionTitle("Performance Level", "Safe presets first");
        addFeatureCard("Balanced", "Default daily profile. Keeps safe tools visible and avoids risky root actions.", "Working", COLOR_SUCCESS, toastClick("Balanced is recommended"));
        addFeatureCard("Battery Saver", "Shows Android battery settings and safe guidance only.", "Safe guidance", COLOR_SUCCESS, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS));
            }
        });
        addFeatureCard("Smooth", "Shows display and refresh-rate settings where supported.", "Limited", COLOR_WARNING, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
            }
        });
        addFeatureCard("Performance and Custom", "Hidden from normal use until tested on real devices with rollback ready.", "Needs testing", COLOR_WARNING, toastClick("Performance and Custom need testing"));
    }

    private void addSystemImprovement() {
        addSectionTitle("APK system improvement", "Status first, action second");
        addFeatureCard("Device status", "Show model, Android version, and support report.", "Working", COLOR_SUCCESS, toastClick("Device status ready"));
        addFeatureCard("Root status", "Show root requirement honestly. Do not claim root is active unless detected.", "Root required", COLOR_WARNING, toastClick("Root status guide ready"));
        addFeatureCard("LSPosed scope", "Hooks stay unavailable until LSPosed and scope are verified.", "LSPosed scope required", COLOR_WARNING, toastClick("LSPosed scope guide ready"));
        addFeatureCard("System hooks", "Problematic hooks stay disabled until device testing is complete.", "Not available", COLOR_DANGER, toastClick("System hooks are disabled"));
    }

    private void addSupportAndRollback() {
        addSectionTitle("Support and rollback", "Keep recovery steps visible");
        addFeatureCard("Copy support report", "Copy device and module status for issue reports.", "Working", COLOR_SUCCESS, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyReport();
            }
        });
        addFeatureCard("Share support report", "Share safe diagnostic text only. No personal files are included.", "Working", COLOR_SUCCESS, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareReport();
            }
        });
        addFeatureCard("Rollback help", "Disable problematic features and return to safe defaults.", "Required", COLOR_ACCENT, toastClick("Rollback guide ready"));
    }

    private void addDeviceReport() {
        addSectionTitle("Latest report", "Safe diagnostic text");
        addPlainCard(BatteryOptimizationAdvisor.supportReport(this) + "\n" + CustomizationManager.report(this));
    }

    private void addSelectedImagePreview() {
        Uri imageUri = CustomizationManager.selectedImageUri(this);
        if (imageUri == null) {
            addPlainCard("No image selected yet. Tap Select wallpaper to choose an image.");
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

    private void addPlainCard(String body) {
        LinearLayout card = card(false);
        card.addView(cardBody(body));
        root.addView(card, cardParams());
    }

    private void addSectionTitle(String title, String subtitle) {
        TextView titleView = text(title, 15, COLOR_ACCENT, true);
        titleView.setPadding(0, dp(10), 0, dp(4));
        root.addView(titleView, matchWrap());
        TextView subtitleView = text(subtitle, 14, COLOR_MUTED, false);
        subtitleView.setPadding(0, 0, 0, dp(14));
        root.addView(subtitleView, matchWrap());
    }

    private Button cardButton(String text, boolean primary, View.OnClickListener listener) {
        Button button = new Button(this);
        button.setText(text);
        button.setAllCaps(false);
        button.setTextSize(15);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setPadding(dp(16), dp(12), dp(16), dp(12));
        button.setTextColor(primary ? COLOR_BG : COLOR_TEXT);
        button.setBackground(rounded(primary ? COLOR_ACCENT : COLOR_CARD_SOFT, dp(22), primary ? COLOR_ACCENT : COLOR_BORDER));
        button.setOnClickListener(listener);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(14), 0, 0);
        button.setLayoutParams(params);
        return button;
    }

    private LinearLayout card(boolean elevated) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(18), dp(18), dp(18));
        card.setBackground(rounded(elevated ? COLOR_CARD_SOFT : COLOR_CARD, dp(24), elevated ? COLOR_ACCENT : COLOR_BORDER));
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
        view.setPadding(0, dp(12), 0, dp(8));
        return view;
    }

    private TextView cardBody(String body) {
        TextView view = text(body, 16, COLOR_MUTED, false);
        view.setLineSpacing(0, 1.08f);
        return view;
    }

    private TextView text(String text, int sp, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextSize(sp);
        view.setTextColor(color);
        view.setTypeface(Typeface.DEFAULT, bold ? Typeface.BOLD : Typeface.NORMAL);
        return view;
    }

    private GradientDrawable rounded(int color, int radius, int strokeColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        drawable.setStroke(dp(1), strokeColor);
        return drawable;
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private LinearLayout.LayoutParams cardParams() {
        LinearLayout.LayoutParams params = matchWrap();
        params.setMargins(0, 0, 0, dp(14));
        return params;
    }

    private void openImagePicker() {
        try {
            startActivityForResult(CustomizationManager.imagePickerIntent(), REQUEST_PICK_IMAGE);
        } catch (ActivityNotFoundException failure) {
            toast("No image picker found");
        }
    }

    private void applySelectedWallpaper(int flags) {
        Uri imageUri = CustomizationManager.selectedImageUri(this);
        if (imageUri == null) {
            toast("Select an image first");
            return;
        }

        InputStream input = null;
        try {
            input = getContentResolver().openInputStream(imageUri);
            if (input == null) {
                toast("Cannot open selected image");
                return;
            }
            WallpaperManager.getInstance(this).setStream(input, null, true, flags);
            toast("Wallpaper applied");
        } catch (IOException | SecurityException failure) {
            toast("Wallpaper apply failed");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignored) {
                    // Nothing else to close.
                }
            }
        }
    }

    private void openSettings(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException failure) {
            toast("Settings screen not available");
        }
    }

    private void copyReport() {
        String report = BatteryOptimizationAdvisor.supportReport(this) + "\n" + CustomizationManager.report(this);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText("ColorOS Themes Rock report", report));
            toast("Report copied");
        }
    }

    private void shareReport() {
        String report = BatteryOptimizationAdvisor.supportReport(this) + "\n" + CustomizationManager.report(this);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, "ColorOS Themes Rock support report");
        share.putExtra(Intent.EXTRA_TEXT, report);
        try {
            startActivity(Intent.createChooser(share, "Share support report"));
        } catch (ActivityNotFoundException failure) {
            toast("No share app found");
        }
    }

    private View.OnClickListener toastClick(final String message) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast(message);
            }
        };
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
