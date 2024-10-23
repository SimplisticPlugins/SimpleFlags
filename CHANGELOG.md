## Changes:
- Updated how flags are handled internally to easily add newer flags.
  - Currently working on another flag I am interested in.

## Fixed:
- Added a null check if the flag is not in the registry.
  - This only happens if you enable a config option, and do `/simpleflags reload`
  - WorldGuard flags can only be added on startup, as any time after that... the registry is frozen.