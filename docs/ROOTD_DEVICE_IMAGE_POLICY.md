# Rootd Device Image Policy

This repository uses a safety-first policy for device image files and root-aware customer features.

## Uploaded file record

A local file named `init_boot.img` was inspected before this update.

| Field | Value |
|---|---|
| File name | `init_boot.img` |
| Size | `8,388,608` bytes |
| Detected format | Android boot image family |
| SHA-256 | `8b64cf094649708f6635f1e6e9b1a352835b9bad496eb79f9640198b29fbadab` |
| Public repo decision | Raw device images are not committed by default |

## Public repository rule

Device image files are model-specific and firmware-specific. This project should store documentation, compatibility notes, checksums, rollback notes, and support reports, not raw customer device images.

Do not commit these by default:

- `init_boot.img`
- `boot.img`
- `vendor_boot.img`
- `vbmeta.img`
- `super.img`
- Unknown firmware dumps
- Customer-specific modified images

## Rootd feature boundary

Rootd can safely provide:

- Root status display
- Root manager status display
- LSPosed status display
- Scope status display
- Device profile display
- Compatibility labels
- Backup and rollback reminders
- Safe settings shortcuts
- Customer support reports

Rootd must not provide public one-click device image changes, hidden unsafe toggles, or claims that every phone can be fixed automatically.

## Customer status labels

Every advanced item must show one of these labels:

| Label | Meaning |
|---|---|
| Working | Tested on a matching real device |
| Limited | Works only on selected ROMs or device models |
| Needs testing | Built but not verified enough for normal customers |
| Not available | Unsupported on the current device |
| Root required | Root access is required |
| Rollback required | Backup and restore instructions must be visible first |

## Performance safety rule

Performance pages should focus on visibility and customer diagnostics.

Allowed:

- Show CPU status where available
- Show battery temperature
- Show battery drain estimate
- Show refresh rate and FPS where available
- Open standard Android settings pages
- Export support reports

Not allowed as public defaults:

- Permanent maximum-performance forcing
- Removing device thermal protection
- Unsupported system property edits
- Device-wide changes without rollback
- Guaranteed battery or lag-fix claims

## Safe customer wording

Use this warning for advanced Rootd pages:

> Advanced Rootd options are device-specific. Use only features marked Working for your exact phone and software version. Keep backup and rollback ready before changing advanced settings.
