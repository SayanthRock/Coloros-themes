#!/usr/bin/env bash
set -euo pipefail

MODULE_ID="${MODULE_ID:-ColorOS-Themes-Rock}"
VERSION="${MODULE_VERSION:-}"
OUT_DIR="${OUT_DIR:-dist}"

if [[ -z "$VERSION" ]]; then
  VERSION="$(grep -E '^version=' module.prop | head -n 1 | cut -d'=' -f2-)"
fi

if [[ -z "$VERSION" ]]; then
  VERSION="v0.1.0"
fi

ZIP_NAME="$MODULE_ID-$VERSION.zip"

mkdir -p "$OUT_DIR"
rm -f "$OUT_DIR"/*.zip

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
