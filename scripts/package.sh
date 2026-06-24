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

REQUIRED_FILES=(
  "module.prop"
  "customize.sh"
  "post-fs-data.sh"
  "service.sh"
  "uninstall.sh"
)

for file in "${REQUIRED_FILES[@]}"; do
  if [[ ! -f "$file" ]]; then
    echo "Missing required module file: $file" >&2
    exit 1
  fi
done

INCLUDE_PATHS=(
  "module.prop"
  "customize.sh"
  "post-fs-data.sh"
  "service.sh"
  "uninstall.sh"
  "system_ext"
  "themes"
  "theme-packs"
  "customer-options"
  "assets"
  "docs"
  "scripts"
)

FILES=()
for path in "${INCLUDE_PATHS[@]}"; do
  [[ -e "$path" ]] && FILES+=("$path")
done

mkdir -p "$OUT_DIR"
rm -f "$OUT_DIR"/*.zip

if [[ -f module.prop ]]; then
  sed -i "s/^version=.*/version=$VERSION/" module.prop
fi

printf 'Building %s\n' "$ZIP_NAME"
zip -r "$OUT_DIR/$ZIP_NAME" "${FILES[@]}" \
  -x "*.git*" \
  -x "dist/*" \
  -x "*.DS_Store" \
  -x "**/__pycache__/*" \
  -x "**/*.pyc"

printf 'Done: %s/%s\n' "$OUT_DIR" "$ZIP_NAME"
