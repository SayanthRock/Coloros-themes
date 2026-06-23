package com.sayanthrock.colorosthemes.lsposed;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Customer dashboard for safe battery and theme support guidance.
 */
public class MainActivity extends Activity {

    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        render();
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
        addSubtitle("Battery Optimization Helper");
        addCard("Device status", BatteryOptimizationAdvisor.supportReport(this));
        addCard("Optimization status", BatteryOptimizationAdvisor.optimizationStatus(this));
        addRecommendationCard(BatteryOptimizationAdvisor.recommendations(this));

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

        addButton("Copy support report", new View.OnClickListener() {
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
        Button button = new Button(this);
        button.setText(text);
        button.setAllCaps(false);
        button.setTextColor(0xFF111318);
        button.setTextSize(15);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setBackgroundColor(0xFFE2B884);
        button.setOnClickListener(listener);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(4), 0, dp(8));
        root.addView(button, params);
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
                    "ColorOS Themes Rock Battery Report",
                    BatteryOptimizationAdvisor.supportReport(this)
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
