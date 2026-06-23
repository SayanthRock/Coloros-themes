# Customization Center

ColorOS Customizer v0.3.0 adds a user-controlled customization screen inside the helper APK.

## Added options

- Open any image from the phone gallery or file picker.
- Preview the selected image inside the APK.
- Apply the selected image to the Home screen wallpaper.
- Apply the selected image to the Lock screen wallpaper.
- Apply the selected image to both Home and Lock screens.
- Save an About phone helper label for preview and support reports.
- Save an OTA display name. Default value: `Sayanth Rock`.
- Turn OTA name display on or off inside the helper settings.
- Turn OTA background preview on or off using the selected image.
- Copy a full support report with battery and customization status.

## Safe behavior

The APK uses Android public APIs for image picking and wallpaper setting. OTA and About phone custom text are stored as helper options first. This keeps the app safe across OPPO, OnePlus, and realme devices while real device testing continues.

## How to use

1. Open **ColorOS Customizer**.
2. Tap **Open image picker**.
3. Select any image you like.
4. Use one of the wallpaper buttons:
   - Apply image to Home screen
   - Apply image to Lock screen
   - Apply image to Home + Lock screen
5. Enter your About phone helper label if needed.
6. Keep OTA display name as `Sayanth Rock` or change it.
7. Turn OTA name and OTA background options on or off.
8. Copy the support report before testing or sharing feedback.

## Build artifact

GitHub Actions workflow:

```text
.github/workflows/build-helper-apk.yml
```

Artifact name:

```text
ColorOS-Customizer-v0.3.0
```
