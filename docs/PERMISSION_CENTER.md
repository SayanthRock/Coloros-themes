# Permission Center

The APK now includes a customer-facing Permission Center screen.

## Included UI sections

- No Root permission warning
- Recommended apps selection
- Manage all files permission status
- Allow from this source status
- Advanced customer options
- Rollback and safe compatibility options
- Permission report

## Behavior

When root is not detected, the app shows:

```text
No Root permission
Root permission is not detected, some functions will not be available!
OK
```

Advanced options remain locked on non-root phones. Customers can still use safe wallpaper, settings, support, and theme preview tools.

## Safety rule

The app does not try to gain root automatically. It only detects capability and shows safe customer options.
