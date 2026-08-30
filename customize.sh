#!/system/bin/sh

SKIPMOUNT=false
PROPFILE=false
POSTFSDATA=true
LATESTARTSERVICE=true

MODULE_ID="coloros_themes_rock"
SAFE_DISABLE_FILE="$MODPATH/disable"
PROFILE_DIR="$MODPATH/config"
PROFILE_FILE="$PROFILE_DIR/device-profile.conf"
SETTINGS_FILE="$PROFILE_DIR/settings.conf"

ui_print "ColorOS Themes Rock"
ui_print "OPPO / OnePlus / realme theme helper"
ui_print "Android 15/16/17 readiness + Rootd systemless support"

API="$(getprop ro.build.version.sdk)"
ANDROID_RELEASE="$(getprop ro.build.version.release)"
BRAND="$(getprop ro.product.brand | tr '[:upper:]' '[:lower:]')"
MANUFACTURER="$(getprop ro.product.manufacturer | tr '[:upper:]' '[:lower:]')"
MODEL="$(getprop ro.product.model)"
DEVICE="$(getprop ro.product.device)"
OPLUS_VERSION="$(getprop ro.build.version.oplus)"
ROM_DISPLAY="$(getprop ro.build.display.id)"

ui_print "Device: $BRAND $MODEL"
ui_print "Android: $ANDROID_RELEASE / SDK $API"
ui_print "Build: $ROM_DISPLAY"

mkdir -p "$MODPATH/system_ext/media/themeInner"
mkdir -p "$PROFILE_DIR"

SUPPORTED_BRAND=false
case "$BRAND:$MANUFACTURER" in
  *oppo*|*oneplus*|*realme*|*oplus*)
    SUPPORTED_BRAND=true
    ;;
esac

ANDROID_TRACK="legacy"
case "$API" in
  35) ANDROID_TRACK="android_15_supported" ;;
  36) ANDROID_TRACK="android_16_forward_compatible" ;;
  37) ANDROID_TRACK="android_17_testing_required" ;;
  3[8-9]|[4-9][0-9]) ANDROID_TRACK="future_android_safe_mode" ;;
  3[1-4]) ANDROID_TRACK="modern_fallback" ;;
esac

if [ "$SUPPORTED_BRAND" != "true" ]; then
  ui_print "Unsupported brand detected. Installing in safe documentation mode."
  ui_print "No advanced customer options will be assumed supported until tested."
fi

if [ -f "$SAFE_DISABLE_FILE" ]; then
  ui_print "Safe disable flag found. Module will stay installed but inactive."
fi

cat > "$PROFILE_FILE" <<EOF
MODULE_ID=$MODULE_ID
BRAND=$BRAND
MANUFACTURER=$MANUFACTURER
MODEL=$MODEL
DEVICE=$DEVICE
ANDROID_RELEASE=$ANDROID_RELEASE
API=$API
ANDROID_TRACK=$ANDROID_TRACK
OPLUS_VERSION=$OPLUS_VERSION
ROM_DISPLAY=$ROM_DISPLAY
SUPPORTED_BRAND=$SUPPORTED_BRAND
ROOTD_MODE=systemless_only
HOOK_MODE=optional
OVERLAY_MODE=safe
THEME_MOUNT=system_ext/media/themeInner
CUSTOMER_STATUS=needs_device_test
UNSAFE_SYSTEM_WRITES=false
EOF

if [ ! -f "$SETTINGS_FILE" ]; then
  cat > "$SETTINGS_FILE" <<EOF
REFRESH_RATE=auto
ANIMATION_SCALE=default
BATTERY_MODE=balanced
ROOTD_SYSTEMLESS_ONLY=true
SHOW_ROOTD_WARNINGS=true
EOF
fi

set_perm_recursive "$MODPATH" 0 0 0755 0644
set_perm "$MODPATH/customize.sh" 0 0 0755
set_perm "$MODPATH/post-fs-data.sh" 0 0 0755
set_perm "$MODPATH/service.sh" 0 0 0755
set_perm "$MODPATH/rootd.sh" 0 0 0755
set_perm "$MODPATH/uninstall.sh" 0 0 0755
set_perm "$PROFILE_FILE" 0 0 0644
set_perm "$SETTINGS_FILE" 0 0 0644

ui_print "Device profile saved: $ANDROID_TRACK"
ui_print "Rootd health engine installed."
ui_print "Systemless-only safety policy is active."
ui_print "Install complete. Reboot and test on one OPPO/OnePlus/realme device before sharing."
