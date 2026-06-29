package com.sayanthrock.colorosthemes.lsposed;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Very small update checker for the public release metadata JSON.
 */
public final class UpdateChecker {

    public interface Callback {
        void onResult(String report);
        void onError(String message);
    }

    private static final String STABLE_URL = "https://raw.githubusercontent.com/SayanthRock/Coloros-themes/main/latestStable.json";

    private UpdateChecker() {
        // Utility class.
    }

    public static void checkStableAsync(final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                InputStream stream = null;
                try {
                    connection = (HttpURLConnection) new URL(STABLE_URL).openConnection();
                    connection.setConnectTimeout(7000);
                    connection.setReadTimeout(7000);
                    connection.setRequestProperty("Accept", "application/json");
                    connection.connect();

                    int status = connection.getResponseCode();
                    if (status < 200 || status >= 300) {
                        callback.onError("Update check failed with HTTP " + status);
                        return;
                    }

                    stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder raw = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        raw.append(line);
                    }

                    JSONObject json = new JSONObject(raw.toString());
                    String version = json.optString("version", "unknown");
                    String releaseLevel = json.optString("releaseLevel", "unknown");
                    JSONArray changelog = json.optJSONArray("changelog");
                    StringBuilder report = new StringBuilder();
                    report.append("Latest channel: ").append(releaseLevel).append('\n');
                    report.append("Latest version: ").append(version).append('\n');
                    if (changelog != null && changelog.length() > 0) {
                        report.append("Changelog:\n");
                        for (int i = 0; i < changelog.length(); i++) {
                            report.append("- ").append(changelog.optString(i)).append('\n');
                        }
                    }
                    callback.onResult(report.toString().trim());
                } catch (Throwable failure) {
                    callback.onError("Update check failed: " + failure.getClass().getSimpleName());
                } finally {
                    try {
                        if (stream != null) {
                            stream.close();
                        }
                    } catch (Throwable ignored) {
                        // Ignore close failure.
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
