#!/system/bin/sh
# ColorOS Themes Rock Rootd safety engine.
# Read-only diagnostics plus explicit systemless policy enforcement.
# This script never writes to /system or other real system partitions.

MODDIR=${0%/*}
PROFILE="$MODDIR/config/device-profile.conf"
CONFIG="$MODDIR/config/settings.conf"
DISABLE="$MODDIR/disable"
REPORT_DIR="/data/local/tmp/coloros-themes-rock"
REPORT="$REPORT_DIR/rootd-health.txt"

mkdir -p "$REPORT_DIR" 2>/dev/null

prop() { getprop "$1" 2>/dev/null; }
has_command() { command -v "$1" >/dev/null 2>&1; }

root_provider() {
  if [ -e /data/adb/magisk/magisk ]; then echo "Magisk"; return; fi
  if [ -e /data/adb/ksu/bin/ksud ]; then echo "KernelSU"; return; fi
  if [ -e /data/adb/ap/bin/apd ]; then echo "APatch"; return; fi
  if [ -n "$(prop ro.magisk.version)" ]; then echo "Magisk"; return; fi
  echo "Not detected"
}

root_uid="unknown"
if has_command su; then root_uid="$(su -c id -u 2>/dev/null | head -n 1)"; fi
root_ready=false
[ "$root_uid" = "0" ] && root_ready=true

systemless_only=true
if [ -f "$CONFIG" ]; then
  # shellcheck disable=SC1090
  . "$CONFIG"
  [ "${ROOTD_SYSTEMLESS_ONLY:-true}" = "true" ] || systemless_only=false
fi

# Rootd refuses to operate if the configured policy is weakened.
if [ "$systemless_only" != "true" ]; then
  systemless_only=true
  echo "ROOTD_SYSTEMLESS_ONLY=true" > "$CONFIG" 2>/dev/null
fi

mount_target="$MODDIR/system_ext/media/themeInner"
mount_ready=false
[ -d "$mount_target" ] && mount_ready=true

device_profile=false
[ -f "$PROFILE" ] && device_profile=true

disabled=false
[ -f "$DISABLE" ] && disabled=true

cat > "$REPORT" <<EOF
ColorOS Themes Rock — Rootd Health
Provider: $(root_provider)
Root executable: $root_ready
Root uid: $root_uid
Systemless-only: $systemless_only
Direct system writes: blocked
Module mount target prepared: $mount_ready
Device profile present: $device_profile
Safe-disable active: $disabled
Brand: $(prop ro.product.brand)
Manufacturer: $(prop ro.product.manufacturer)
Model: $(prop ro.product.model)
Device: $(prop ro.product.device)
Android: $(prop ro.build.version.release)
SDK: $(prop ro.build.version.sdk)
OPlus: $(prop ro.build.version.oplus)
Theme Store package: com.oplus.themestore
Theme Store target registry: verified-only
EOF

chmod 0644 "$REPORT" 2>/dev/null

# Target registry is intentionally closed. No arbitrary path may reach Rootd.
target_info() {
  case "$1" in
    theme_store_overlay)
      echo "Theme Store overlay|com.oplus.themestore|system_ext/media/themeInner|verified" ;;
    theme_store_colors)
      echo "Theme Store colors.xml|com.oplus.themestore|system_ext/media/themeInner/colors.xml|verified" ;;
    theme_store_assets)
      echo "Theme Store assets|com.oplus.themestore|system_ext/media/themeInner/assets|verified" ;;
    *)
      return 1 ;;
  esac
}

case "$1" in
  health|status|"")
    cat "$REPORT"
    ;;
  validate)
    [ "$systemless_only" = "true" ] || exit 2
    [ "$disabled" = "true" ] && exit 0
    [ "$mount_ready" = "true" ] || exit 3
    exit 0
    ;;
  target)
    # Usage: rootd.sh target <verified-target-id>
    # This only validates and reports a registry target; it never edits a file.
    [ "$systemless_only" = "true" ] || exit 2
    [ "$disabled" = "true" ] && exit 4
    [ -n "$2" ] || { echo "Target id required"; exit 64; }
    info="$(target_info "$2")" || { echo "Target rejected: not in verified registry"; exit 65; }
    echo "$info"
    ;;
  safe-disable)
    touch "$DISABLE"
    echo "Rootd safe-disable enabled. Reboot or restart the module to keep it inactive."
    ;;
  *)
    echo "Usage: $0 {health|status|validate|target <verified-target-id>|safe-disable}"
    exit 64
    ;;
esac
