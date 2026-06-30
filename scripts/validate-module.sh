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

check_shell() {
  local file="$1"
  bash -n "$file" || error_exit "Shell syntax failed: $file"
}

check_json() {
  local file="$1"
  python3 -m json.tool "$file" >/dev/null || error_exit "JSON syntax failed: $file"
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
need_file "customer-options/safe-defaults.conf"
need_file "assets/Overlays/README.md"
need_file "assets/Overlays/targets.json"
need_file "assets/Overlays/customer-overlay-preset.json"
need_file "docs/DEFAULT_THEME_CUSTOMER_GUIDE.md"
need_file "docs/LAG_FIX_GUIDE.md"
need_file "docs/UI_DESIGN_SYSTEM.md"
need_file "docs/PROBLEM_SOLVER_MATRIX.md"
need_file "docs/ROOTD_CUSTOMER_FOUNDATION.md"
need_file "docs/ROOTD_SYSTEM_FILE_SUPPORT.md"
need_file "docs/ANDROID_15_16_17_SUPPORT.md"
need_file "lsposed-helper/app/src/main/java/com/sayanthrock/colorosthemes/lsposed/DeviceCompatibility.java"

need_dir "system_ext/media/themeInner"
need_dir "themes/default/wallpapers"
need_dir "themes/default/icons"
need_dir "themes/default/lockscreen"
need_dir "themes/default/homescreen"
need_dir "themes/default/sounds"
need_dir "themes/default/previews"
need_dir "assets/Overlays"
need_dir "assets/Overlays/android"
need_dir "assets/Overlays/systemui"
need_dir "assets/Overlays/settings"
need_dir "assets/Overlays/templates"
need_dir "docs"
need_dir "lsposed-helper/app/src/main/java/com/sayanthrock/colorosthemes/lsposed"

grep -q "id=coloros_themes_rock" module.prop || error_exit "Wrong module id"
grep -q "ColorOS Themes Rock" module.prop || error_exit "Module name missing"
grep -q "Default Rock Premium" themes/default/theme.json || error_exit "Default theme missing"
grep -q "default_theme" customer-options/options.json || error_exit "Default theme option missing"
grep -q "overlay_assets" customer-options/options.json || error_exit "Overlay assets option missing"
grep -q "rootd" customer-options/options.json || error_exit "Rootd options missing"
grep -q "android_17" customer-options/options.json || error_exit "Android 17 option missing"
grep -q "ROOTD_SYSTEMLESS_ONLY" customer-options/safe-defaults.conf || error_exit "Rootd safe default missing"
grep -q "UNSAFE_SYSTEM_WRITES=false" customize.sh || error_exit "Unsafe system write policy missing"
grep -qi "systemless" docs/ROOTD_SYSTEM_FILE_SUPPORT.md || error_exit "Rootd guide missing systemless policy"

check_shell "customize.sh"
check_shell "post-fs-data.sh"
check_shell "service.sh"
check_shell "uninstall.sh"
check_shell "scripts/package.sh"
check_shell "scripts/check-theme-size.sh"

check_json "themes/default/theme.json"
check_json "themes/default/design-tokens.json"
check_json "customer-options/options.json"
check_json "assets/Overlays/targets.json"
check_json "assets/Overlays/customer-overlay-preset.json"

echo "Module validation passed"
