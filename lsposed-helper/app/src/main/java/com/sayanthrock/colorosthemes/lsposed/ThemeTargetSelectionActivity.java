package com.sayanthrock.colorosthemes.lsposed;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/** Customer-facing selector for verified Theme Store files/targets. */
public final class ThemeTargetSelectionActivity extends Activity {
    private static final int BG = 0xFF0F0F10;
    private static final int CARD = 0xFF1A1A1D;
    private static final int TEXT = 0xFFF5F2EA;
    private static final int MUTED = 0xFFB9B1A3;
    private static final int ACCENT = 0xFFE2B884;
    private static final int SUCCESS = 0xFF8FD694;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(24), dp(18), dp(30));
        scroll.addView(root);

        TextView title = text("Theme Store targets", 28, TEXT, true);
        root.addView(title, wrap());
        TextView subtitle = text("com.oplus.themestore • ColorOS Themes Rock", 14, MUTED, false);
        subtitle.setPadding(0, dp(6), 0, dp(18));
        root.addView(subtitle, wrap());

        TextView policy = text("Choose a verified repository-managed target. Arbitrary filesystem paths are not accepted. Rootd remains systemless-only.", 14, MUTED, false);
        policy.setPadding(dp(14), dp(14), dp(14), dp(14));
        policy.setBackground(round(CARD, 16));
        root.addView(policy, margin());

        for (ThemeTargetRegistry.Target target : ThemeTargetRegistry.all()) {
            addTarget(root, target);
        }

        TextView current = text("Selected: " + ThemeTargetRegistry.status(this), 14, SUCCESS, true);
        current.setPadding(0, dp(18), 0, 0);
        root.addView(current, wrap());
        setContentView(scroll);
    }

    private void addTarget(LinearLayout root, final ThemeTargetRegistry.Target target) {
        TextView item = text(target.label + "\n" + target.relativePath + "\n" + target.capability + " • " + (target.verified ? "Verified" : "Needs device test")), 16, TEXT, false);
        item.setPadding(dp(16), dp(16), dp(16), dp(16));
        item.setGravity(Gravity.CENTER_VERTICAL);
        item.setBackground(round(CARD, 18));
        item.setOnClickListener(v -> {
            if (!ThemeTargetRegistry.select(this, target.id)) {
                Toast.makeText(this, "Target is not verified", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Selected: " + target.label, Toast.LENGTH_SHORT).show();
            recreate();
        });
        root.addView(item, margin());
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);
        if (bold) view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return view;
    }

    private GradientDrawable round(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(radius));
        return drawable;
    }

    private LinearLayout.LayoutParams wrap() {
        return new LinearLayout.LayoutParams(-1, -2);
    }

    private LinearLayout.LayoutParams margin() {
        LinearLayout.LayoutParams params = wrap();
        params.topMargin = dp(10);
        return params;
    }

    private int dp(int value) { return (int) (value * getResources().getDisplayMetrics().density + 0.5f); }
}
