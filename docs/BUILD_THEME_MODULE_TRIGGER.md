# Build Theme Module Trigger

This file is used to safely trigger the **Build Theme Module** GitHub Actions workflow from the `main` branch.

The workflow watches `docs/**`, so updating this file starts validation and module ZIP generation without changing runtime module behavior.

## Latest trigger

- Purpose: generate updated module ZIP after Rootd customer overlay foundation update.
- Expected workflow: `.github/workflows/build-theme-module.yml`
- Expected output: `dist/ColorOS-Themes-Rock-v0.5.4.zip` inside the workflow artifact.

## Notes

The push build creates a workflow artifact. Public GitHub Release publishing still requires either a version tag or a manual workflow dispatch with publishing enabled.
