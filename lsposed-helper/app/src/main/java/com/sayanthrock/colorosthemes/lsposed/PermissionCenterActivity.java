package com.sayanthrock.colorosthemes.lsposed;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PermissionCenterActivity extends Activity {

    private static final int COLOR_BG = 0xFFF9F9FF;
    private static final int COLOR_TEXT = 0xFF20232B;
    private static final int COLOR_MUTED = 0xFF626777;
    private static final int COLOR_BORDER = 0xFFC9CCD8;
    private static final int COLOR_BLUE = 0xFF075DD8;
    private static final int COLOR_WARN = 0xFFFFF4E5;

    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        render();
        if (!RootPermissionManager.isRootDetected()) {
            new AlertDialog.Builder(this)
                    .setTitle("No Root permission")
                    .setMessage("Root permission is not detected, some functions will not be available!")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    private void render() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(COLOR_BG);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(30), dp(32), dp(30), dp(34));
        scroll.addView(root);

        topBar();
        rootStatus();
        section("Recommended", "Select recommended apps?");
        simpleCard("Theme Store", "Recommended for applying owned theme packages");
        simpleCard("Files", "Recommended for local theme and wallpaper files");
        simpleCard("Settings", "Recommended for display, battery, wallpaper, and app settings");
        simpleCard("LSPosed Manager", "Recommended only when LSPosed mode is used");
        simpleCard("Root Manager", "Recommended only for module installation on rooted phones");

        section("Permissions", "Open Android settings and allow manually.");
        simpleCard("Allow access to manage all files", RootPermissionManager.canManageAllFiles() ? "Already allowed" : "Open Android settings to allow access");
        simpleCard("Allow from this source", RootPermissionManager.canInstallUnknownApps(this) ? "Already allowed" : "Open Android settings to allow installs");
        simpleCard("OK", "Confirm and refresh permission status");

        section("Advanced customer options", "Locked options stay disabled when root is not detected.");
        boolean rootReady = RootPermissionManager.isRootDetected();
        simpleCard("Theme advanced mode", rootReady ? "Available after customer confirmation" : "Locked: root permission is not available");
        simpleCard("Backup before apply", rootReady ? "Backup step enabled" : "Locked: root permission is not available");
        simpleCard("Rollback mode", "Keep uninstall and rollback notes enabled");
        simpleCard("Safe compatibility mode", "Disable unsupported options automatically");

        simpleCard("Permission report", RootPermissionManager.permissionReport(this));
        setContentView(scroll);
    }

    private void topBar() {
        LinearLayout bar = new LinearLayout(this);
        bar.setOrientation(LinearLayout.HORIZONTAL);
        bar.setGravity(Gravity.CENTER_VERTICAL);
        TextView back = text("‹", 42, COLOR_TEXT);
        back.setGravity(Gravity.CENTER);
        back.setOnClickListener(v -> finish());
        bar.addView(back, new LinearLayout.LayoutParams(dp(54), dp(54)));
        TextView title = text("Permission Center", 30, COLOR_TEXT);
        bar.addView(title, new LinearLayout.LayoutParams(0, dp(54), 1f));
        TextView refresh = text("↻", 30, 0xFF000000);
        refresh.setGravity(Gravity.CENTER);
        refresh.setOnClickListener(v -> render());
        bar.addView(refresh, new LinearLayout.LayoutParams(dp(54), dp(54)));
        root.addView(bar, matchWrap());
    }

    private void rootStatus() {
        boolean ok = RootPermissionManager.isRootDetected();
        LinearLayout card = card(ok ? COLOR_BLUE : COLOR_WARN, ok ? COLOR_BLUE : 0xFFFFB74D);
        card.addView(text(ok ? "Root permission detected" : "No Root permission", 22, ok ? 0xFFFFFFFF : COLOR_TEXT));
        card.addView(text(ok ? "Advanced functions are available after confirmation." : "Root permission is not detected, some functions will not be available!", 16, ok ? 0xFFE8F0FF : COLOR_MUTED));
        root.addView(card, cardParams());
    }

    private void section(String title, String subtitle) {
        TextView titleView = text(title, 18, 0xFF8A8FA0);
        titleView.setPadding(0, dp(10), 0, dp(4));
        root.addView(titleView, matchWrap());
        TextView subtitleView = text(subtitle, 16, COLOR_MUTED);
        subtitleView.setPadding(0, 0, 0, dp(14));
        root.addView(subtitleView, matchWrap());
    }

    private void simpleCard(String title, String subtitle) {
        LinearLayout card = card(0x00FFFFFF, COLOR_BORDER);
        card.addView(text(title, 20, COLOR_TEXT));
        TextView sub = text(subtitle, 15, COLOR_MUTED);
        sub.setPadding(0, dp(4), 0, 0);
        card.addView(sub);
        root.addView(card, cardParams());
    }

    private LinearLayout card(int fill, int stroke) {
        LinearLayout c = new LinearLayout(this);
        c.setOrientation(LinearLayout.VERTICAL);
        c.setPadding(dp(18), dp(18), dp(18), dp(18));
        android.graphics.drawable.GradientDrawable d = new android.graphics.drawable.GradientDrawable();
        d.setColor(fill);
        d.setCornerRadius(dp(18));
        d.setStroke(dp(1), stroke);
        c.setBackground(d);
        return c;
    }

    private TextView text(String value, int sp, int color) {
        TextView v = new TextView(this);
        v.setText(value);
        v.setTextSize(sp);
        v.setTextColor(color);
        return v;
    }

    private LinearLayout.LayoutParams cardParams() {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 0, 0, dp(18));
        return p;
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
