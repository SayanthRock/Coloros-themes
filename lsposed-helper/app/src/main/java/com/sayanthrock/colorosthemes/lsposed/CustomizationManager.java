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
 * Stores user-controlled customization choices and applies safe wallpaper changes.
 */
public final class CustomizationManager {

    public static final String DEFAULT_OTA_NAME = "Sayanth Rock";

    private static final String PREFS = "customization_center";
    private static final String KEY_IMAGE_URI = "image_uri";
    private static final String KEY_OTA_NAME = "ota_name";
    private static final String KEY_OTA_BRANDING = "ota_branding";
    private static final String KEY_OTA_BACKGROUND = "ota_background";
    private static final String KEY_ABOUT_PHONE_NAME = "about_phone_name";

    private CustomizationManager() {
        // Utility class.
    }

    public static void saveImageUri(Context context, Uri uri) {
        prefs(context).edit().putString(KEY_IMAGE_URI, uri == null ? "" : uri.toString()).apply();
    }

    public static Uri selectedImageUri(Context context) {
        String value = prefs(context).getString(KEY_IMAGE_URI, "");
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Uri.parse(value);
    }

    public static String selectedImageLabel(Context context) {
        Uri uri = selectedImageUri(context);
        return uri == null ? "No image selected" : uri.toString();
    }

    public static void setOtaName(Context context, String name) {
        String value = name == null || name.trim().isEmpty() ? DEFAULT_OTA_NAME : name.trim();
        prefs(context).edit().putString(KEY_OTA_NAME, value).apply();
    }

    public static String otaName(Context context) {
        return prefs(context).getString(KEY_OTA_NAME, DEFAULT_OTA_NAME);
    }

    public static void setOtaBrandingEnabled(Context context, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_OTA_BRANDING, enabled).apply();
    }

    public static boolean otaBrandingEnabled(Context context) {
        return prefs(context).getBoolean(KEY_OTA_BRANDING, false);
    }

    public static void setOtaBackgroundEnabled(Context context, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_OTA_BACKGROUND, enabled).apply();
    }

    public static boolean otaBackgroundEnabled(Context context) {
        return prefs(context).getBoolean(KEY_OTA_BACKGROUND, false);
    }

    public static void setAboutPhoneName(Context context, String name) {
        String value = name == null || name.trim().isEmpty() ? "ColorOS Themes Rock" : name.trim();
        prefs(context).edit().putString(KEY_ABOUT_PHONE_NAME, value).apply();
    }

    public static String aboutPhoneName(Context context) {
        return prefs(context).getString(KEY_ABOUT_PHONE_NAME, "ColorOS Themes Rock");
    }

    public static String applyWallpaper(Context context, int target) throws IOException {
        Uri uri = selectedImageUri(context);
        if (uri == null) {
            return "Select an image first.";
        }

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        ContentResolver resolver = context.getContentResolver();

        InputStream inputStream = null;
        try {
            inputStream = resolver.openInputStream(uri);
            if (inputStream == null) {
                return "Cannot open selected image.";
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setStream(inputStream, null, true, target);
            } else {
                wallpaperManager.setStream(inputStream);
            }
            return "Wallpaper applied successfully.";
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static Intent imagePickerIntent() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        return intent;
    }

    public static String report(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("Customization Center\n");
        builder.append("Selected image: ").append(selectedImageLabel(context)).append('\n');
        builder.append("About phone label: ").append(aboutPhoneName(context)).append('\n');
        builder.append("OTA name: ").append(otaName(context)).append('\n');
        builder.append("OTA name toggle: ").append(otaBrandingEnabled(context)).append('\n');
        builder.append("OTA background toggle: ").append(otaBackgroundEnabled(context)).append('\n');
        return builder.toString();
    }

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
}
