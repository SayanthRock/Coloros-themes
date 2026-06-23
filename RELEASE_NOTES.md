# Release Notes

## v0.2.0, Module Fix Upload

This release prepares the module for customer testing through GitHub Actions artifact upload.

## Fixed

- Added stronger module validation before ZIP packaging.
- Added shell syntax checks for module scripts.
- Added JSON syntax checks for theme and customer option files.
- Added default theme structure and design tokens.
- Added UI design system for premium dark customer style.
- Added lag fix guide and asset size checking.
- Added customer options catalog.
- Added default theme download guide.
- Added problem solver matrix.
- Added release checklist and device test matrix.

## Build flow

The module ZIP is built by GitHub Actions after validation passes.

```text
Validate module files -> Check theme assets -> Package ZIP -> Upload artifact
```

## Customer status

This release is ready for customer testing after the GitHub Actions build passes.

## Needs real device testing

- OPPO Android 15/16/17
- realme Android 15/16/17
- OnePlus Android 15/16/17
- Lock screen behavior
- Home screen behavior
- Icons and sounds
- LSPosed experimental mode
