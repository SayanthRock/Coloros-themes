# Root APK Customer Options

This project now uses a permission-aware customer option model.

## Customer flow

1. Open the APK.
2. Open Permission Center.
3. Read the root status card.
4. Grant only the permissions that are needed.
5. Use safe wallpaper, theme preview, recommended apps, and support report tools.
6. Advanced options remain locked until root is detected and the customer confirms rollback steps.

## No root state

When root is not detected, the app shows:

```text
No Root permission
Root permission is not detected, some functions will not be available!
OK
```

Safe options stay available:

- Wallpaper setup
- Recommended apps selector
- Permission report
- Android settings shortcuts
- Theme preview and customer guidance
- Rollback and safe compatibility mode

Locked options:

- Advanced theme mode
- System theme path helper
- Backup before apply for system paths

## Permissions

| Permission screen | Purpose |
|---|---|
| Allow access to manage all files | Import local theme files, wallpapers, backups, and customer-owned assets |
| Allow from this source | Allow customer-approved direct APK installation outside an app store |
| Wallpaper permission | Apply selected customer wallpaper where Android supports it |

## System file policy

System-file options must follow these rules:

- Do not run automatically.
- Do not try to gain root automatically.
- Require root detection first.
- Require customer confirmation.
- Require backup and rollback notes.
- Keep unsupported features locked.

## Customer safety

The APK should explain what is available and what is locked. It should not pretend that non-root phones can change protected system files.
