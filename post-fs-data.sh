#!/system/bin/sh
# Early boot hook kept lightweight on purpose.
# Only prepare safe config/log directories and permissions here.

MODDIR=${0%/*}
CONFIG_DIR="$MODDIR/config"
LOG_DIR="/data/local/tmp/coloros-themes-rock"
SAFE_DISABLE_FILE="$MODDIR/disable"

mkdir -p "$CONFIG_DIR" "$LOG_DIR" 2>/dev/null
chmod 0755 "$CONFIG_DIR" "$LOG_DIR" 2>/dev/null

# Keep this module recoverable. If the disable flag exists, later services should exit.
if [ -f "$SAFE_DISABLE_FILE" ]; then
  echo "ColorOS Themes Rock safe-disable active" > "$LOG_DIR/safe-mode.txt" 2>/dev/null
  chmod 0644 "$LOG_DIR/safe-mode.txt" 2>/dev/null
  exit 0
fi

# Best-effort context restore. Ignore failures on devices that do not allow it.
restorecon -R "$CONFIG_DIR" "$LOG_DIR" 2>/dev/null

exit 0
