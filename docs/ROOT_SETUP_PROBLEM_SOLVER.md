# Root Setup Problem Solver

This update adds a guided problem-solving layer for ColorOS Toolbox.

## What it means

Root setup unlocks advanced module guidance, diagnostics, scope refresh, and rollback actions. It does not make impossible guarantees for unsupported private ROM hooks. ColorOS, OxygenOS, and realme UI can change package names and private classes after system updates, so the module keeps repair actions visible and repeatable.

## New toolbox actions

- Root setup plan
- Fix current matters
- Optimize and refresh
- Copy solver report

## Root setup plan

The guided root setup flow asks users to:

1. Open or install a trusted root manager.
2. Install the module ZIP with Magisk, KernelSU, APatch, or a compatible module manager.
3. Open LSPosed or a compatible Xposed manager.
4. Enable ColorOS Toolbox.
5. Enable only required scopes.
6. Reboot after module or scope changes.
7. Refresh affected scope if a tweak breaks after a ROM update.

## Fix current matters checklist

The checklist helps solve common current issues:

- Root manager missing or inactive
- Module disabled in root manager
- LSPosed not detecting the module
- Scope missing after ROM or app update
- System UI tweak failing after OTA
- Launcher or weather package changed
- Unsupported tweak still enabled

## Optimize and refresh

The optimize flow is intentionally safe:

- Mark refresh as requested.
- Recheck LSPosed module state.
- Recheck required package scopes.
- Reboot after scope changes.
- Test one tweak group at a time.
- Export a solver report when something still fails.

## Safety stance

The toolbox should be able to guide everything that is reasonable after root setup, but it must not silently force unsupported hooks. Clear labels and rollback are more useful than pretending every ROM build behaves the same, because apparently firmware vendors enjoy creative chaos.
