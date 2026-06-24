# Rootd Customer Foundation

This document defines the customer status layer for ColorOS Themes Rock.

Rootd should be used as a status-first helper, not as an automatic changer.

## Customer screens

- Device profile
- Root status
- LSPosed status
- Target package status
- Overlay readiness
- Rollback readiness
- Support report

## Default behavior

- Safe mode: on
- Runtime apply: off
- Customer package targets: needs testing
- Preview assets: allowed
- Support report export: allowed

## Required target packages

- `android`
- `com.android.systemui`
- `com.android.settings`

These targets must stay labelled clearly in the APK so customers know what is tested and what is not tested yet.