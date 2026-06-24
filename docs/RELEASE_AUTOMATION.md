# APK Release Automation

This guide explains how to build and upload the APK release from GitHub Actions.

## Workflow

Use:

```text
.github/workflows/publish-github-release.yml
```

Workflow name:

```text
Publish GitHub Release
```

## What the workflow does

The workflow automatically:

1. Checks out the repository.
2. Sets up JDK 17.
3. Sets up Android SDK 35.
4. Sets up Gradle 8.10.2.
5. Resolves release version and channel.
6. Validates the theme module.
7. Checks theme asset sizes.
8. Builds debug APK.
9. Builds release APK output.
10. Builds the module ZIP.
11. Collects APK, ZIP, build info, and checksums.
12. Uploads workflow artifact.
13. Generates build provenance attestation.
14. Creates release notes.
15. Creates or updates a GitHub Release.
16. Uploads release files with clobber support.

## Manual release steps

1. Open the repository on GitHub.
2. Open **Actions**.
3. Select **Publish GitHub Release**.
4. Tap **Run workflow**.
5. Choose channel:
   - `beta` for testing APK updates.
   - `stable` only after real-device testing.
   - `nightly` for experimental builds.
6. Enter version, for example:

```text
v0.5.6-beta
```

7. Keep **publish** enabled.
8. Start the workflow.

## Uploaded files

The release will include:

```text
ColorOS-Customizer-v0.5.6-beta-debug.apk
ColorOS-Customizer-v0.5.6-beta-release.apk
ColorOS-Customizer-v0.5.6-beta-release-unsigned.apk, if signing is not configured
ColorOS-Themes-Rock module ZIP
BUILD_INFO.txt
SHA256SUMS.txt
```

## Current beta update

Current planned beta:

```text
v0.5.6-beta
```

Focus:

```text
Working-only APK cleanup and system improvement
```

## Release rule

Use beta first. Do not mark it stable until the APK is tested on a real OPPO, OnePlus, or realme device.

Only working and safe tools should be enabled by default. Problematic or untested features should stay hidden, blocked, or marked as Needs testing.
