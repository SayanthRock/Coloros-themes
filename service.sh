#!/system/bin/sh
# ColorOS Themes Rock late-boot service.
# Safe rule: apply only small, reversible settings. Do not replace real /system files here.

MODDIR=${0%/*}
CONFIG="$MODDIR/config/settings.conf"
LOG_DIR="/data/local/tmp/coloros-themes-rock"
LOG_FILE="$LOG_DIR/service.log"

mkdir -p "$LOG_DIR"

log_msg() {
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" >> "$LOG_FILE" 2>/dev/null
}

until [ "$(getprop sys.boot_completed)" = "1" ]; do
  sleep 2
done

log_msg "ColorOS Themes Rock service started"
log_msg "Brand: $(getprop ro.product.brand)"
log_msg "Model: $(getprop ro.product.model)"
log_msg "Android: $(getprop ro.build.version.release)"

# Defaults. The module stays conservative unless the user enables options.
REFRESH_RATE="auto"
ANIMATION_SCALE="default"
BATTERY_MODE="balanced"

if [ -f "$CONFIG" ]; then
  # shellcheck disable=SC1090
  . "$CONFIG"
  log_msg "Loaded config from $CONFIG"
else
  log_msg "No config found. Using safe defaults."
fi

case "$REFRESH_RATE" in
  60|90|120|144)
    settings put system peak_refresh_rate "$REFRESH_RATE" 2>/dev/null
    settings put system min_refresh_rate "$REFRESH_RATE" 2>/dev/null
    log_msg "Applied refresh rate: $REFRESH_RATE"
    ;;
  auto|default|"")
    log_msg "Refresh rate left as device default"
    ;;
  *)
    log_msg "Skipped invalid refresh rate value: $REFRESH_RATE"
    ;;
esac

case "$ANIMATION_SCALE" in
  fast)
    settings put global window_animation_scale 0.5 2>/dev/null
    settings put global transition_animation_scale 0.5 2>/dev/null
    settings put global animator_duration_scale 0.5 2>/dev/null
    log_msg "Applied fast animation scale"
    ;;
  off)
    settings put global window_animation_scale 0 2>/dev/null
    settings put global transition_animation_scale 0 2>/dev/null
    settings put global animator_duration_scale 0 2>/dev/null
    log_msg "Disabled animation scale"
    ;;
  default|normal|"")
    log_msg "Animation scale left as device default"
    ;;
  *)
    log_msg "Skipped invalid animation scale value: $ANIMATION_SCALE"
    ;;
esac

case "$BATTERY_MODE" in
  saver)
    settings put global low_power 1 2>/dev/null
    log_msg "Requested battery saver mode"
    ;;
  balanced|performance|default|"")
    log_msg "Battery mode left as user/device controlled: $BATTERY_MODE"
    ;;
  *)
    log_msg "Skipped invalid battery mode value: $BATTERY_MODE"
    ;;
esac

log_msg "Service finished"
exit 0
