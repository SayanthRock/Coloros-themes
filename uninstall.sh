#!/system/bin/sh
# Cleanup marker for uninstall.

LOG_DIR="/data/local/tmp/coloros-themes-rock"
mkdir -p "$LOG_DIR" 2>/dev/null
cat > "$LOG_DIR/removed.txt" <<EOF
ColorOS Themes Rock removed
Rollback status: module uninstall requested
Systemless-only cleanup: no direct system partition cleanup needed
EOF
chmod 0644 "$LOG_DIR/removed.txt" 2>/dev/null

# Backward-compatible marker used by older support notes.
echo "ColorOS Themes Rock removed" > /data/local/tmp/coloros-themes-rock-removed.txt 2>/dev/null
exit 0
