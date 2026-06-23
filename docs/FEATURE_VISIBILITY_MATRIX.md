# Feature Visibility Matrix

Use this matrix so customers understand what works before and after permissions are granted.

## No root permission

Show this warning:

```text
No Root permission
Root permission is not detected, some functions will not be available!
OK
```

Show these features:

- Wallpaper setup
- Recommended apps selector
- Permission report
- Android settings shortcuts
- Theme preview
- Safe compatibility mode
- Rollback notes

Lock these features:

- Advanced theme mode
- System theme path helper
- System backup before apply
- System file apply

## Manage all files granted

Show these features:

- Theme package import
- Wallpaper import
- Backup file picker
- Support report export

## Allow from this source granted

Show these features:

- APK update install
- Helper APK install guide

## Root detected

Show these features only after customer confirmation:

- Advanced theme mode
- System theme path helper
- System backup before apply
- Rollback mode
- Compatibility guard

Required customer confirmation:

- Device model checked
- Android version checked
- Backup plan confirmed
- Rollback steps confirmed
- One feature applied at a time

## CI fix note

Use Gradle 8.10.2 with Android Gradle Plugin 8.7.3. Avoid Gradle 9.x for this Android project unless the Android Gradle Plugin is upgraded and tested.
