# Publish Release Checklist

Use this checklist before publishing the ColorOS Customizer APK or app bundle.

## GitHub Release

1. Open Actions.
2. Select Build App Bundle Release.
3. Run workflow.
4. Use the current app version tag, for example `v0.4.1`.
5. Keep Publish GitHub Release enabled.
6. Download the generated files from the release page.

## Generated files

- Debug APK for direct installation testing.
- Release AAB for marketplace upload after final signing and policy checks.
- BUILD_INFO.txt.
- SHA256SUMS-APP.txt.

## Device testing

- Test on one OPPO phone.
- Test on one OnePlus or realme phone when available.
- Confirm the no-root dialog appears on non-root devices.
- Confirm recommended apps and permission status cards display correctly.
- Confirm wallpaper options work.
- Confirm settings shortcuts open correct Android settings pages.

## Marketplace notes

- Review permissions before upload.
- Confirm the all-files access permission is needed for the public build.
- Keep advanced options locked when root is not detected.
- Use app bundle output for marketplace publishing.
- Keep direct APK release available only for testing or website distribution.
