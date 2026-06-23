#!/system/bin/sh

SKIPMOUNT=false
PROPFILE=false
POSTFSDATA=true
LATESTARTSERVICE=true

MODULE_ID="coloros_themes_rock"
SAFE_DISABLE_FILE="$MODPATH/disable"
PROFILE_DIR="$MODPATH/config"
PROFILE_FILE="$PROFILE_DIR/device-profile.conf"

ui_print "ColorOS Themes Rock"
ui_print "OPPO / OnePlus / realme theme helper"
ui_print "Hook and overlay ready architecture"

API="$(getprop ro.build.version.sdk)"
ANDROID_RELEASE="$(getprop ro.build.version.release)"
BRAND="$(getprop ro.product.brand | tr '[:upper:]' '[:lower:]')"
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
case "$BRAND" in
  oppo|oneplus|realme)
    SUPPORTED_BRAND=true
    ;;
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
MODEL=$MODEL
DEVICE=$DEVICE
ANDROID_RELEASE=$ANDROID_RELEASE
API=$API
OPLUS_VERSION=$OPLUS_VERSION
ROM_DISPLAY=$ROM_DISPLAY
SUPPORTED_BRAND=$SUPPORTED_BRAND
HOOK_MODE=optional
OVERLAY_MODE=safe
CUSTOMER_STATUS=needs_device_test
EOF

set_perm_recursive "$MODPATH" 0 0 0755 0644
set_perm "$MODPATH/customize.sh" 0 0 0755
set_perm "$MODPATH/post-fs-data.sh" 0 0 0755
set_perm "$MODPATH/service.sh" 0 0 0755
set_perm "$MODPATH/uninstall.sh" 0 0 0755
set_perm "$PROFILE_FILE" 0 0 0644

ui_print "Device profile saved."
ui_print "Install complete. Reboot and test on one device before sharing."
