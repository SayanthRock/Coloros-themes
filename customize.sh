#!/system/bin/sh

SKIPMOUNT=false
PROPFILE=false
POSTFSDATA=true
LATESTARTSERVICE=true

ui_print "ColorOS Themes Rock"
ui_print "OPPO / OnePlus / realme theme helper"

API="$(getprop ro.build.version.sdk)"
BRAND="$(getprop ro.product.brand | tr '[:upper:]' '[:lower:]')"
MODEL="$(getprop ro.product.model)"

ui_print "Device: $BRAND $MODEL"
ui_print "SDK: $API"

mkdir -p "$MODPATH/system_ext/media/themeInner"

set_perm_recursive "$MODPATH" 0 0 0755 0644
set_perm "$MODPATH/customize.sh" 0 0 0755
set_perm "$MODPATH/post-fs-data.sh" 0 0 0755
set_perm "$MODPATH/service.sh" 0 0 0755
set_perm "$MODPATH/uninstall.sh" 0 0 0755

ui_print "Install complete. Reboot and test."
