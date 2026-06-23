#!/usr/bin/env bash
set -euo pipefail

MODULE_ID="ColorOS-Themes-Rock"
VERSION="v0.1.0"
OUT_DIR="dist"
ZIP_NAME="$MODULE_ID-$VERSION.zip"

mkdir -p "$OUT_DIR"

FILES=(
  "module.prop"
  "customize.sh"
  "post-fs-data.sh"
  "service.sh"
  "uninstall.sh"
  "system_ext"
  "themes"
  "docs"
  "customer-options"
)

printf 'Building %s\n' "$ZIP_NAME"
zip -r "$OUT_DIR/$ZIP_NAME" "${FILES[@]}" -x "*.git*" -x "dist/*" -x "*.DS_Store"
printf 'Done: %s/%s\n' "$OUT_DIR" "$ZIP_NAME"
