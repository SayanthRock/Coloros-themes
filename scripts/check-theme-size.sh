#!/usr/bin/env bash
set -euo pipefail

MAX_MB=5
THEME_DIR="themes/default"

if [ ! -d "$THEME_DIR" ]; then
  echo "Theme folder not found: $THEME_DIR"
  exit 1
fi

echo "Checking large theme assets in $THEME_DIR"
find "$THEME_DIR" -type f \( -name "*.jpg" -o -name "*.jpeg" -o -name "*.png" -o -name "*.webp" -o -name "*.ogg" \) -print0 | while IFS= read -r -d '' file; do
  size_mb=$(( ($(wc -c < "$file") + 1048575) / 1048576 ))
  if [ "$size_mb" -gt "$MAX_MB" ]; then
    echo "Large asset: $file (${size_mb}MB). Optimize it for smoother customer devices."
  fi
done

echo "Theme asset check complete."
