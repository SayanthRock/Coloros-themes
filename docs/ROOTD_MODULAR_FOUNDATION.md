# Rootd Modular Foundation

This document defines the first implementation layer for the customer-ready Rootd update.

## Modules

- Core status layer
- Device compatibility layer
- Settings layer
- Theme validation layer
- Rollback layer
- Diagnostics layer
- Backup and restore layer

## Rule

Every customer-facing action should be safe, reversible, and clearly labelled.

## First implementation order

1. Add status models.
2. Add settings flags.
3. Add diagnostics report builder.
4. Add theme validation.
5. Add backup and rollback helpers.
6. Connect the UI screens.
