#!/system/bin/sh
# Late boot hook.
# Keep this file small. Heavy background work can slow boot and annoy customers.
LOG_DIR="/data/local/tmp/coloros-themes-rock"
mkdir -p "$LOG_DIR"
{
  echo "ColorOS Themes Rock service started"
  date
  getprop ro.product.brand
  getprop ro.product.model
  getprop ro.build.version.release
} > "$LOG_DIR/device-info.txt" 2>/dev/null
exit 0
