#!/usr/bin/env bash
set -euo pipefail

error_exit() {
  echo "ERROR: $1"
  exit 1
}

need_file() {
  local file="$1"
  [ -f "$file" ] || error_exit "Missing file: $file"
}

need_dir() {
  local dir="$1"
  [ -d "$dir" ] || error_exit "Missing folder: $dir"
}

echo "Validating module files"

need_file "module.prop"
need_file "customize.sh"
need_file "post-fs-data.sh"
need_file "service.sh"
need_file "uninstall.sh"
need_file "scripts/package.sh"
need_file "scripts/check-theme-size.sh"
need_file "themes/default/theme.json"
need_file "themes/default/design-tokens.json"
need_file "customer-options/options.json"

need_dir "system_ext/media/themeInner"
need_dir "themes/default/wallpapers"
need_dir "themes/default/icons"
need_dir "themes/default/lockscreen"
need_dir "themes/default/homescreen"
need_dir "themes/default/sounds"
need_dir "themes/default/previews"
need_dir "docs"

grep -q "id=coloros_themes_rock" module.prop || error_exit "Wrong module id"
grep -q "ColorOS Themes Rock" module.prop || error_exit "Module name missing"
grep -q "Default Rock Premium" themes/default/theme.json || error_exit "Default theme missing"

echo "Module validation passed"
