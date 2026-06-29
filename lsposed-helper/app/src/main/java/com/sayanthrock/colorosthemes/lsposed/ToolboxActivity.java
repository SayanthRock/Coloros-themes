package com.sayanthrock.colorosthemes.lsposed;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

/**
 * Lightweight settings app for the ColorOS Toolbox module.
 */
public class ToolboxActivity extends Activity {

    private static final int COLOR_BG = 0xFF0F0F10;
    private static final int COLOR_CARD = 0xB81A1A1D;
    private static final int COLOR_CARD_SOFT = 0xAA242429;
    private static final int COLOR_ACCENT = 0xFFE2B884;
    private static final int COLOR_ACCENT_SOFT = 0xFFF0D2A8;
    private static final int COLOR_ACCENT_GLOW = 0x44E2B884;
    private static final int COLOR_TEXT = 0xFFF5F2EA;
    private static final int COLOR_MUTED = 0xFFB9B1A3;
    private static final int COLOR_BORDER = 0x4DFFFFFF;
    private static final int COLOR_SUCCESS = 0xFF8FD694;
    private static final int COLOR_WARNING = 0xFFFFCC66;

    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(COLOR_BG);
        getWindow().setNavigationBarColor(COLOR_BG);
        render();
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

        addHero();
        addStatusCard();
        addActionRow();
        addTweaks();
        addUpdateCard();

        setContentView(scrollView);
    }

    private void addHero() {
        LinearLayout card = card(true);
        card.addView(badge(getString(R.string.toolbox_badge), COLOR_SUCCESS));
        TextView title = text(getString(R.string.app_name), 30, COLOR_TEXT, true);
        title.setPadding(0, dp(12), 0, dp(6));
        card.addView(title);
        card.addView(text(getString(R.string.toolbox_subtitle), 16, COLOR_MUTED, false));
        root.addView(card, cardParams());
    }

    private void addStatusCard() {
        LinearLayout card = card(false);
        card.addView(cardTitle(getString(R.string.section_status)));
        card.addView(cardBody("Version: " + BuildConfig.VERSION_NAME + "\n" + RootSetupAssistant.supportSummary(this) + "Last scope refresh: " + ScopeRefreshAdvisor.lastRefreshLabel(this)));
        card.addView(button(getString(R.string.action_copy_report), true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copy(getString(R.string.action_copy_report), buildSupportReport());
            }
        }));
        root.addView(card, cardParams());
    }

    private void addActionRow() {
        section(getString(R.string.section_tools), getString(R.string.section_tools_subtitle));
        root.addView(button(getString(R.string.action_open_root_manager), true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openKnownPackage(RootSetupAssistant.ROOT_MANAGER_PACKAGES, getString(R.string.message_no_root_manager));
            }
        }), cardParams());
        root.addView(button(getString(R.string.action_open_lsposed), false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openKnownPackage(RootSetupAssistant.LSPOSED_MANAGER_PACKAGES, getString(R.string.message_no_lsposed));
            }
        }), cardParams());
        root.addView(button(getString(R.string.action_refresh_scope), false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScopeRefreshAdvisor.markRefreshRequested(ToolboxActivity.this, "manual");
                new AlertDialog.Builder(ToolboxActivity.this)
                        .setTitle(getString(R.string.action_refresh_scope))
                        .setMessage(ScopeRefreshAdvisor.refreshChecklist(ToolboxActivity.this))
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        }), cardParams());
        root.addView(button(getString(R.string.action_open_permissions), false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ToolboxActivity.this, PermissionCenterActivity.class));
            }
        }), cardParams());
    }

    private void addTweaks() {
        section(getString(R.string.section_tweaks), getString(R.string.section_tweaks_subtitle));
        addToggleCard(getString(R.string.tweak_lockscreen), getString(R.string.tweak_lockscreen_desc), ToolboxPrefs.KEY_LOCKSCREEN, true, getString(R.string.badge_needs_test), COLOR_WARNING);
        addToggleCard(getString(R.string.tweak_statusbar), getString(R.string.tweak_statusbar_desc), ToolboxPrefs.KEY_STATUS_BAR, true, getString(R.string.badge_scope_required), COLOR_ACCENT);
        addToggleCard(getString(R.string.tweak_qs), getString(R.string.tweak_qs_desc), ToolboxPrefs.KEY_QUICK_SETTINGS, true, getString(R.string.badge_scope_required), COLOR_ACCENT);
        addToggleCard(getString(R.string.tweak_launcher), getString(R.string.tweak_launcher_desc), ToolboxPrefs.KEY_LAUNCHER, true, getString(R.string.badge_needs_test), COLOR_WARNING);
        addToggleCard(getString(R.string.tweak_systemui), getString(R.string.tweak_systemui_desc), ToolboxPrefs.KEY_SYSTEM_UI, true, getString(R.string.badge_scope_required), COLOR_ACCENT);
        addToggleCard(getString(R.string.tweak_weather), getString(R.string.tweak_weather_desc), ToolboxPrefs.KEY_WEATHER, false, RootSetupAssistant.weatherSupportStatus(), COLOR_WARNING);
    }

    private void addUpdateCard() {
        section(getString(R.string.section_updates), getString(R.string.section_updates_subtitle));
        LinearLayout card = card(false);
        card.addView(cardTitle(getString(R.string.section_updates)));
        card.addView(cardBody(getString(R.string.updates_note)));
        card.addView(button(getString(R.string.action_check_updates), true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateChecker.checkStableAsync(new UpdateChecker.Callback() {
                    @Override
                    public void onResult(final String report) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(ToolboxActivity.this)
                                        .setTitle(getString(R.string.action_check_updates))
                                        .setMessage(report)
                                        .setPositiveButton(android.R.string.ok, null)
                                        .show();
                            }
                        });
                    }

                    @Override
                    public void onError(final String message) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                toast(message);
                            }
                        });
                    }
                });
            }
        }));
        root.addView(card, cardParams());
    }

    private void addToggleCard(String title, String description, final String key, boolean defaultValue, String status, int statusColor) {
        LinearLayout card = card(false);
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.addView(text(title, 20, COLOR_TEXT, true), new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        header.addView(badge(status, statusColor));
        card.addView(header);
        TextView desc = cardBody(description);
        desc.setPadding(0, dp(10), 0, dp(12));
        card.addView(desc);

        Switch toggle = new Switch(this);
        toggle.setChecked(ToolboxPrefs.isEnabled(this, key, defaultValue));
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean enabled) {
                ToolboxPrefs.setEnabled(ToolboxActivity.this, key, enabled);
                toast(getString(R.string.message_saved));
            }
        });
        card.addView(toggle);
        root.addView(card, cardParams());
    }

    private Button button(String label, boolean primary, View.OnClickListener listener) {
        Button button = new Button(this);
        button.setText(label);
        button.setAllCaps(false);
        button.setTextSize(15);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setPadding(dp(16), dp(12), dp(16), dp(12));
        button.setTextColor(primary ? COLOR_BG : COLOR_TEXT);
        button.setBackground(rounded(primary ? COLOR_ACCENT : COLOR_CARD_SOFT, dp(22), primary ? COLOR_ACCENT_SOFT : COLOR_BORDER));
        button.setOnClickListener(listener);
        return button;
    }

    private void openKnownPackage(String[] packages, String errorMessage) {
        for (String packageName : packages) {
            Intent launch = getPackageManager().getLaunchIntentForPackage(packageName);
            if (launch != null) {
                try {
                    startActivity(launch);
                    return;
                } catch (ActivityNotFoundException ignored) {
                    // Try next.
                }
            }
        }
        toast(errorMessage);
    }

    private String buildSupportReport() {
        return RootSetupAssistant.supportSummary(this) + ScopeRefreshAdvisor.refreshChecklist(this) + "\n\n" + ToolboxPrefs.featureReport(this);
    }

    private void copy(String label, String value) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(label, value));
            toast(getString(R.string.message_copied));
        }
    }

    private void section(String title, String subtitle) {
        TextView titleView = text(title, 15, COLOR_ACCENT, true);
        titleView.setPadding(0, dp(10), 0, dp(4));
        root.addView(titleView, matchWrap());
        TextView subtitleView = text(subtitle, 14, COLOR_MUTED, false);
        subtitleView.setPadding(0, 0, 0, dp(14));
        root.addView(subtitleView, matchWrap());
    }

    private LinearLayout card(boolean elevated) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(18), dp(18), dp(18));
        card.setBackground(rounded(elevated ? COLOR_CARD_SOFT : COLOR_CARD, dp(26), elevated ? COLOR_ACCENT_GLOW : COLOR_BORDER));
        return card;
    }

    private TextView cardTitle(String value) {
        return text(value, 20, COLOR_TEXT, true);
    }

    private TextView cardBody(String value) {
        TextView body = text(value, 16, COLOR_MUTED, false);
        body.setLineSpacing(0, 1.08f);
        return body;
    }

    private TextView badge(String label, int color) {
        TextView view = text(label, 12, COLOR_BG, true);
        view.setGravity(Gravity.CENTER);
        view.setPadding(dp(10), dp(5), dp(10), dp(5));
        view.setBackground(rounded(color, dp(999), color));
        return view;
    }

    private TextView text(String value, int sp, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(sp);
        view.setTextColor(color);
        view.setTypeface(Typeface.DEFAULT, bold ? Typeface.BOLD : Typeface.NORMAL);
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
        LinearLayout.LayoutParams params = matchWrap();
        params.setMargins(0, 0, 0, dp(16));
        return params;
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void toast(String value) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
