#!/system/bin/sh
# ColorOS Themes Rock late-boot service.
# Rootd policy: apply only reversible settings and owned systemless assets.
# Never replace real system files or write directly to system partitions.

MODDIR=${0%/*}
CONFIG="$MODDIR/config/settings.conf"
PROFILE="$MODDIR/config/device-profile.conf"
SAFE_DISABLE_FILE="$MODDIR/disable"
ROOTD="$MODDIR/rootd.sh"
LOG_DIR="/data/local/tmp/coloros-themes-rock"
LOG_FILE="$LOG_DIR/service.log"
ROOTD_REPORT="$LOG_DIR/rootd-system-health.txt"

mkdir -p "$LOG_DIR" 2>/dev/null

log_msg() {
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" >> "$LOG_FILE" 2>/dev/null
}

read_profile_value() {
  key="$1"
  if [ -f "$PROFILE" ]; then
    grep -E "^$key=" "$PROFILE" 2>/dev/null | head -n 1 | cut -d'=' -f2-
  fi
}

write_rootd_report() {
  API="$(getprop ro.build.version.sdk)"
  BRAND="$(getprop ro.product.brand)"
  MODEL="$(getprop ro.product.model)"
  DEVICE="$(getprop ro.product.device)"
  ROM="$(getprop ro.build.display.id)"
  ANDROID_TRACK="$(read_profile_value ANDROID_TRACK)"
  SUPPORTED_BRAND="$(read_profile_value SUPPORTED_BRAND)"

  cat > "$ROOTD_REPORT" <<EOF
ColorOS Themes Rock Rootd System Health
Brand: $BRAND
Model: $MODEL
Device: $DEVICE
Android SDK: $API
ROM: $ROM
Android track: $ANDROID_TRACK
Supported OPPO family brand: $SUPPORTED_BRAND
Module path: $MODDIR
Theme mount target: $MODDIR/system_ext/media/themeInner
Systemless-only policy: true
Unsafe direct system writes: false
Safe-disable active: $(if [ -f "$SAFE_DISABLE_FILE" ]; then echo true; else echo false; fi)
EOF
  chmod 0644 "$ROOTD_REPORT" 2>/dev/null
}

until [ "$(getprop sys.boot_completed)" = "1" ]; do
  sleep 2
done

log_msg "ColorOS Themes Rock service started"
log_msg "Brand: $(getprop ro.product.brand)"
log_msg "Model: $(getprop ro.product.model)"
log_msg "Android: $(getprop ro.build.version.release) / SDK $(getprop ro.build.version.sdk)"
log_msg "OPlus version: $(getprop ro.build.version.oplus)"

if [ -f "$SAFE_DISABLE_FILE" ]; then
  log_msg "Safe-disable flag detected. Service exiting without applying options."
  write_rootd_report
  exit 0
fi

if [ ! -x "$ROOTD" ]; then
  log_msg "Rootd safety engine is missing or not executable. Refusing runtime changes."
  write_rootd_report
  exit 1
fi

# Validate the immutable Rootd policy and module target before changing settings.
if ! "$ROOTD" validate >/dev/null 2>&1; then
  log_msg "Rootd validation failed. Refusing runtime changes."
  write_rootd_report
  exit 1
fi

if [ -f "$PROFILE" ]; then
  log_msg "Loaded device profile from $PROFILE"
  log_msg "Android track: $(read_profile_value ANDROID_TRACK)"
  log_msg "Systemless policy: $(read_profile_value ROOTD_MODE)"
else
  log_msg "No device profile found. Continuing with safe defaults only."
fi

REFRESH_RATE="auto"
ANIMATION_SCALE="default"
BATTERY_MODE="balanced"
ROOTD_SYSTEMLESS_ONLY="true"
SHOW_ROOTD_WARNINGS="true"

if [ -f "$CONFIG" ]; then
  # shellcheck disable=SC1090
  . "$CONFIG"
  log_msg "Loaded config from $CONFIG"
else
  log_msg "No config found. Using safe defaults."
fi

if [ "$ROOTD_SYSTEMLESS_ONLY" != "true" ]; then
  log_msg "Unsafe direct system mode requested but blocked by Rootd policy."
  ROOTD_SYSTEMLESS_ONLY="true"
fi

case "$REFRESH_RATE" in
  60|90|120|144)
    settings put system peak_refresh_rate "$REFRESH_RATE" 2>/dev/null
    settings put system min_refresh_rate "$REFRESH_RATE" 2>/dev/null
    log_msg "Applied refresh rate: $REFRESH_RATE"
    ;;
  auto|default|"") log_msg "Refresh rate left as device default" ;;
  *) log_msg "Skipped invalid refresh rate value: $REFRESH_RATE" ;;
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
  default|normal|"") log_msg "Animation scale left as device default" ;;
  *) log_msg "Skipped invalid animation scale value: $ANIMATION_SCALE" ;;
esac

case "$BATTERY_MODE" in
  saver)
    settings put global low_power 1 2>/dev/null
    log_msg "Requested battery saver mode"
    ;;
  balanced|performance|default|"") log_msg "Battery mode left as user/device controlled: $BATTERY_MODE" ;;
  *) log_msg "Skipped invalid battery mode value: $BATTERY_MODE" ;;
esac

write_rootd_report
log_msg "Rootd report written to $ROOTD_REPORT"
log_msg "Service finished"
exit 0
