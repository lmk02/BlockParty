# Compatibility

## Release lines

- `BlockParty 3.x`
  - Status: active modern line
  - Java: `21+`
  - Minecraft server support: `1.20.6+`, `1.21.x`
  - Build target: modern shaded jar with `spigot_v1_20_R1` and `spigot_v1_21_R1`
- `BlockParty 2.x`
  - Status: legacy line
  - Java: pre-modern baseline
  - Minecraft server support: `1.8` through `1.19.x`
  - Notes: keep this branch or its final release available for older networks

## Verification checklist

- Build with `mvn -DskipTests package`
- Boot on a `1.20.6` server and confirm:
  - plugin load, enable, disable
  - arena join, leave, start, stop
  - floor generation and pattern placement
  - boosts and particle effects
  - inventory prompts, scoreboards, titles, and action bars
- Boot on a `1.21.x` server and confirm the same flow
- Re-check music discs and sign interactions on both supported versions when updating the adapter modules
