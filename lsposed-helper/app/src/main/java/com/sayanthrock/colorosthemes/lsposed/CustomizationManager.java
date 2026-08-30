package com.sayanthrock.colorosthemes.lsposed;

import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;

import java.io.IOException;
import java.io.InputStream;

/**
 * Single state/lifecycle store for customer options and verified theme targets.
 *
 * Options use the same customer-facing lifecycle:
 * disabled -> configure -> preview -> apply -> applied -> reset.
 * Target selection is registry-backed; arbitrary filesystem paths are rejected.
 */
public final class CustomizationManager {

    public static final String DEFAULT_OTA_NAME = "Sayanth Rock";
    private static final int DEFAULT_STATUS_BAR_BLUR = 24;
    private static final int MIN_STATUS_BAR_BLUR = 0;
    private static final int MAX_STATUS_BAR_BLUR = 100;
    private static final String PREFS = "customization_center";
    private static final String KEY_IMAGE_URI = "image_uri";
    private static final String KEY_OTA_NAME = "ota_name";
    private static final String KEY_OTA_BRANDING = "ota_branding";
    private static final String KEY_OTA_BACKGROUND = "ota_background";
    private static final String KEY_ABOUT_PHONE_NAME = "about_phone_name";
    private static final String KEY_STATUS_BAR_BLUR_ENABLED = "status_bar_blur_enabled";
    private static final String KEY_STATUS_BAR_BLUR_AMOUNT = "status_bar_blur_amount";
    private static final String KEY_STATUS_BAR_BLUR_FORCE_FALLBACK = "status_bar_blur_force_fallback";
    private static final String KEY_OPTION_ENABLED_PREFIX = "option.enabled.";
    private static final String KEY_OPTION_STATE_PREFIX = "option.state.";

    public enum OptionState { DISABLED, READY, CONFIGURED, PREVIEW, APPLIED, NEEDS_DEVICE_TEST, UNSUPPORTED }

    private CustomizationManager() { }

    public static void setOptionEnabled(Context context, String optionId, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_OPTION_ENABLED_PREFIX + optionId, enabled).apply();
        if (!enabled) setOptionState(context, optionId, OptionState.DISABLED);
        else if (getOptionState(context, optionId) == OptionState.DISABLED) setOptionState(context, optionId, OptionState.READY);
    }

    public static boolean isOptionEnabled(Context context, String optionId) {
        return prefs(context).getBoolean(KEY_OPTION_ENABLED_PREFIX + optionId, true);
    }

    public static void setOptionState(Context context, String optionId, OptionState state) {
        prefs(context).edit().putString(KEY_OPTION_STATE_PREFIX + optionId, state.name()).apply();
    }

    public static OptionState getOptionState(Context context, String optionId) {
        if (!isOptionEnabled(context, optionId)) return OptionState.DISABLED;
        String value = prefs(context).getString(KEY_OPTION_STATE_PREFIX + optionId, OptionState.READY.name());
        try { return OptionState.valueOf(value); }
        catch (IllegalArgumentException ignored) { return OptionState.READY; }
    }

    public static void configureOption(Context context, String optionId) {
        if (isOptionEnabled(context, optionId)) setOptionState(context, optionId, OptionState.CONFIGURED);
    }

    public static void previewOption(Context context, String optionId) {
        if (isOptionEnabled(context, optionId)) setOptionState(context, optionId, OptionState.PREVIEW);
    }

    public static void markApplied(Context context, String optionId) {
        if (isOptionEnabled(context, optionId)) setOptionState(context, optionId, OptionState.APPLIED);
    }

    public static void resetOption(Context context, String optionId) {
        prefs(context).edit().remove(KEY_OPTION_STATE_PREFIX + optionId).apply();
        setOptionState(context, optionId, isOptionEnabled(context, optionId) ? OptionState.READY : OptionState.DISABLED);
    }

    public static String optionStateLabel(Context context, String optionId) {
        switch (getOptionState(context, optionId)) {
            case CONFIGURED: return "Configured";
            case PREVIEW: return "Preview ready";
            case APPLIED: return "Applied";
            case NEEDS_DEVICE_TEST: return "Needs device test";
            case UNSUPPORTED: return "Unsupported";
            case DISABLED: return "Disabled";
            default: return "Ready";
        }
    }

    /** Selects only a verified repository-managed target. */
    public static boolean selectThemeTarget(Context context, String targetId) {
        return ThemeTargetRegistry.select(context, targetId);
    }

    public static ThemeTargetRegistry.Target selectedThemeTarget(Context context) {
        return ThemeTargetRegistry.selected(context);
    }

    public static String selectedThemeTargetLabel(Context context) {
        return ThemeTargetRegistry.selectedLabel(context);
    }

    public static String selectedThemeTargetPackage(Context context) {
        return ThemeTargetRegistry.selectedPackage(context);
    }

    public static String selectedThemeTargetPath(Context context) {
        return ThemeTargetRegistry.selectedRelativePath(context);
    }

    /** Returns true only for registry targets; rejects absolute or arbitrary paths. */
    public static boolean isVerifiedThemeTarget(String targetId) {
        return ThemeTargetRegistry.isAllowed(targetId);
    }

    public static String themeTargetStatus(Context context) {
        return ThemeTargetRegistry.status(context);
    }

    public static void saveImageUri(Context context, Uri uri) {
        prefs(context).edit().putString(KEY_IMAGE_URI, uri == null ? "" : uri.toString()).apply();
    }

    public static Uri selectedImageUri(Context context) {
        String value = prefs(context).getString(KEY_IMAGE_URI, "");
        return value == null || value.trim().isEmpty() ? null : Uri.parse(value);
    }

    public static String selectedImageLabel(Context context) {
        Uri uri = selectedImageUri(context);
        return uri == null ? "No image selected" : uri.toString();
    }

    public static void setOtaName(Context context, String name) {
        String value = name == null || name.trim().isEmpty() ? DEFAULT_OTA_NAME : name.trim();
        prefs(context).edit().putString(KEY_OTA_NAME, value).apply();
    }
    public static String otaName(Context context) { return prefs(context).getString(KEY_OTA_NAME, DEFAULT_OTA_NAME); }
    public static void setOtaBrandingEnabled(Context context, boolean enabled) { prefs(context).edit().putBoolean(KEY_OTA_BRANDING, enabled).apply(); }
    public static boolean otaBrandingEnabled(Context context) { return prefs(context).getBoolean(KEY_OTA_BRANDING, false); }
    public static void setOtaBackgroundEnabled(Context context, boolean enabled) { prefs(context).edit().putBoolean(KEY_OTA_BACKGROUND, enabled).apply(); }
    public static boolean otaBackgroundEnabled(Context context) { return prefs(context).getBoolean(KEY_OTA_BACKGROUND, false); }

    public static void setAboutPhoneName(Context context, String name) {
        String value = name == null || name.trim().isEmpty() ? "ColorOS Themes Rock" : name.trim();
        prefs(context).edit().putString(KEY_ABOUT_PHONE_NAME, value).apply();
    }
    public static String aboutPhoneName(Context context) { return prefs(context).getString(KEY_ABOUT_PHONE_NAME, "ColorOS Themes Rock"); }

    public static void setStatusBarBlurEnabled(Context context, boolean enabled) { prefs(context).edit().putBoolean(KEY_STATUS_BAR_BLUR_ENABLED, enabled).apply(); }
    public static boolean statusBarBlurEnabled(Context context) { return prefs(context).getBoolean(KEY_STATUS_BAR_BLUR_ENABLED, false); }
    public static void setStatusBarBlurAmount(Context context, int amount) { prefs(context).edit().putInt(KEY_STATUS_BAR_BLUR_AMOUNT, clampBlur(amount)).apply(); }
    public static int statusBarBlurAmount(Context context) { return clampBlur(prefs(context).getInt(KEY_STATUS_BAR_BLUR_AMOUNT, DEFAULT_STATUS_BAR_BLUR)); }
    public static void increaseStatusBarBlur(Context context) { setStatusBarBlurAmount(context, statusBarBlurAmount(context) + 10); }
    public static void decreaseStatusBarBlur(Context context) { setStatusBarBlurAmount(context, statusBarBlurAmount(context) - 10); }
    public static void setStatusBarBlurFallbackEnabled(Context context, boolean enabled) { prefs(context).edit().putBoolean(KEY_STATUS_BAR_BLUR_FORCE_FALLBACK, enabled).apply(); }
    public static boolean statusBarBlurFallbackEnabled(Context context) { return prefs(context).getBoolean(KEY_STATUS_BAR_BLUR_FORCE_FALLBACK, true); }
    public static boolean supportsRealStatusBarBlur() { return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S; }

    public static String statusBarBlurModeLabel(Context context) {
        if (supportsRealStatusBarBlur()) return "Real blur may be available on this Android version, depending on OEM support.";
        if (statusBarBlurFallbackEnabled(context)) return "Fallback mode stores the blur setting for compatible overlays and future module hooks.";
        return "Native status bar blur is not available on this phone.";
    }

    public static String applyWallpaper(Context context, int target) throws IOException {
        Uri uri = selectedImageUri(context);
        if (uri == null) return "Select an image first.";
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        ContentResolver resolver = context.getContentResolver();
        InputStream inputStream = null;
        try {
            inputStream = resolver.openInputStream(uri);
            if (inputStream == null) return "Cannot open selected image.";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) wallpaperManager.setStream(inputStream, null, true, target);
            else wallpaperManager.setStream(inputStream);
            markApplied(context, "wallpaper");
            return "Wallpaper applied successfully.";
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }

    public static Intent imagePickerIntent() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        return intent;
    }

    public static String report(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Customization Center\n");
        builder.append("Selected image: ").append(selectedImageLabel(context)).append('\n');
        builder.append("Theme Store target: ").append(themeTargetStatus(context)).append('\n');
        builder.append("About phone label: ").append(aboutPhoneName(context)).append('\n');
        builder.append("OTA name: ").append(otaName(context)).append('\n');
        builder.append("OTA name toggle: ").append(otaBrandingEnabled(context)).append('\n');
        builder.append("OTA background toggle: ").append(otaBackgroundEnabled(context)).append('\n');
        builder.append("Status bar blur enabled: ").append(statusBarBlurEnabled(context)).append('\n');
        builder.append("Status bar blur amount: ").append(statusBarBlurAmount(context)).append('%').append('\n');
        builder.append("Status bar blur mode: ").append(statusBarBlurModeLabel(context)).append('\n');
        return builder.toString();
    }

    private static int clampBlur(int value) { return Math.max(MIN_STATUS_BAR_BLUR, Math.min(MAX_STATUS_BAR_BLUR, value)); }
    private static SharedPreferences prefs(Context context) { return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE); }
}
