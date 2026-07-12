# BlockParty 3.x
[![Build](https://github.com/lmk02/BlockParty/actions/workflows/build.yml/badge.svg)](https://github.com/lmk02/BlockParty/actions/workflows/build.yml) [![GitHub license](https://img.shields.io/github/license/Leon167/BlockParty-2.0)](https://github.com/Leon167/BlockParty-2.0/blob/master/LICENSE) ![GitHub release](https://img.shields.io/github/release/Leon167/BlockParty-2.0)

BlockParty 3 is the modernized BlockParty line for current Minecraft servers, built on a Java 21 baseline with a Paper-first compatibility target.

## Support policy

- `BlockParty 3.x` supports `Minecraft 1.20.6+` and `1.21.x`
- `Java 21` is required to build and run this line
- Legacy support for `1.8` through `1.19.x` belongs on the `2.x` line

See [COMPATIBILITY.md](COMPATIBILITY.md) for the runtime matrix and release split.

## Installation

Download the jar file and place it into your plugins folder. A new folder is created (named BlockParty) after the server is started.
A few file and sub-folders should be located there.

## Configuration

#### config.yml

```yaml
# BlockParty configuration

# Locale file located in plugins/BlockParty/Locale/*.yml
LocaleFileName: locale_en.yml

# Saves all data on plugin shutdown. Not necessary
SaveOnDisable: false

# Disables listed sub commands, example:
# DisabledSubCommands: ["help", "reload", "join"]
DisabledSubCommands: []

# Enables command shortcuts
# Command shortcuts: /start <arenaName> - /stop
EnableCommandShortcuts: false

Chat:
  # Chat format when in arena
  # Placeholders:
  # %ARENA%: Arena player is in
  # %NAME%: Player name without formatting (e.g. rank, colors)
  # %DISPLAY%: Name with formatting
  # %MESSAGE%: Message sent
  ArenaChatFormat: "&8[&7%ARENA%&8] &7%DISPLAY% &8> &r%MESSAGE%"

  # Separates normal from arena chat
  # Useful for non-bungee servers
  ArenaPrivateChat: True

# How to use join signs:
# Line 1: [BlockParty]
# Line 2: Arena name (case sensitive)
JoinSigns:
  # Enable join signs
  Enabled: True

  # Update signs every x milliseconds
  UpdateMillis: 1000

  Lines:
    Disabled:
      1: "&4[Off-%ARENA%]"
      2: "Not available"
      3: "&8&l%PLAYERS%/%MAX_PLAYERS%"
      4: "&4• Stopped •"
    Lobby:
      1: "&5[Join-%ARENA%]"
      2: "Voting..."
      3: "&8&l%PLAYERS%/%MAX_PLAYERS%"
      4: "&5• Lobby •"
    LobbyFull:
      1: "&4[Full-%ARENA%]"
      2: "Voting..."
      3: "&8&l%PLAYERS%/%MAX_PLAYERS%"
      4: "&5• Lobby •"
    Ingame:
      1: "&8[Ingame-%ARENA%]"
      2: "%ALIVE% players alive"
      3: "&8&l%PLAYERS%/%MAX_PLAYERS%"
      4: "&8• Ingame •"
    Ending:
      2: "&8&lLobby"
      3: "&8&lrestarting..."

BungeeCord:
  # When set to "True", players will automatically connect to DefaultArena (see below)
  # and will be kicked from the server when leaving the arena.
  # This is mostly useful for BungeeCord servers
  Enabled: False

  # Arena players will connect to when BungeeCord is enabled. Please note the spelling and case sensitivity
  DefaultArena: Arena

Audio:
  # Enables external web audio playback providers
  Enabled: False

  # Available providers: "openaudiomc", "central_hub"
  Provider: central_hub

  # Public base URL where your audio files are hosted
  CdnBaseUrl: "https://cdn.example.com/blockparty/"

Database:
  # Use "MySQL" to enable MySQL, "SQL" to store data locally
  Method: SQL

  # Tables will have this prefix in front of the name
  TablePrefix: "bp_"

  SQLOptions:
    # File to save database to when using SQL
    FileName: "database.db"

  # MySQL credentials
  MySQLOptions:
    Host: localhost
    Port: 3306
    Database: database
    Username: username
    Password: password

# End of  configuration
```

## Arena setup

* Create an arena
  * Use the **/bp create \<arenaName>**
  * Replace \<arenaName> with the name of your arena
* Set the lobby spawn
  * Place yourself where you want to have your lobby spawn. Use **/bp setspawn \<arenaName> lobby**
  * Players get teleported to this position when they enter the lobby
* Set the boundaries of the arena floor
  * Use **/bp pos <1|2>** to set the positions to your current positions. OR:
  * Use **/bp wand** to get a stick to set the positions via left and right click
  * Finally use **/bp setfloor \<arenaName>** to set the floor boundaries for you arena
* Set the game spawn
  * Place yourself where you want to have your game spawn. Use **/bp setspawn \<arenaName> game**
  * Players get teleported to this position when the game starts
* Enable your arena
  * To be able to play in an arena you need to enable it first. Use **/bp enable \<arenaName>**

Thats it for the arena setup. To customize your arena, use ingame commands or head to the **\<arenaName>.yml** located in your `plugins/BlockParty/Arenas/` folder. Here you can find an example arena config:
<details>
<summary>Example arena config</summary>

```yaml
Settings:
  DistanceToOutArea: 5
  TimeToSearch: 8
  LevelAmount: 15
  MinPlayers: 2
  MaxPlayers: 20
  LobbyCountdown: 30
  TimeReductionPerLevel: 0.5
  TimeModifier: 0.1
  Enabled: true
  EnableParticles: true
  EnableLightnings: true
  AutoRestart: false
  AutoKick: false
  EnableBoosts: true
  EnableFallingBlocks: false
  UseAutoGeneratedFloors: true
  UsePatternFloors: true
  EnableActionbarInfo: true
  UseNoteBlockSongs: false
  UseWebSongs: true
  EnableFireworksOnWin: true
  TimerResetOnPlayerJoin: false
  AllowJoinDuringGame: true
  EnableScoreboard: false
  Name: example
  SongManager:
  - examplesong.mp3
  Signs: []
  LobbySpawn:
    World: world1.13
    X: 119.96556415260845
    Y: 64.0
    Z: 26.73431969945681
    Yaw: -57.567917
    Pitch: 35.83339
  GameSpawn:
    World: world1.13
    X: 126.06898491630506
    Y: 63.0
    Z: 30.055373974750374
    Yaw: -57.567917
    Pitch: 35.83339
  Floor:
    A:
      World: world1.13
      X: 124.0
      Y: 62.0
      Z: 28.0
      Yaw: 0.0
      Pitch: 0.0
    B:
      World: world1.13
      X: 127.0
      Y: 62.0
      Z: 31.0
      Yaw: 0.0
      Pitch: 0.0
    Patterns:
    - exampleFloor
    Width: 4.0
    Length: 4.0
```

</details>

## Floor setup

In BlockParty we are using our own format to save block data. We are making use of the Run-length encoding to keep the file as small as possible. You can create your own floors, save and use them.

#### Creating your own floor

* Build a two dimensional pattern using terracotta, wool and/or glass (all blocks supported in > 1.13)
* Set the boundaries of pattern
  * Use **/bp pos <1|2>** to set the positions to your current positions. OR:
  * Use **/bp wand** to get a stick to set the positions via left and right click
* Save your pattern
  * Finally use **/bp save \<patternName>** to save your pattern to the `/plugins/BlockParty/Floors/` folder
  
#### Add a floor to your arena

* Test your pattern
  * This is optional but you may want to test your floor
  * Use **/bp placepattern \<patternName>** to test it
* Activate UsePatternFloors
  * Make sure you set `UsePatternFloors: true` in your arena config
  * You can also add patterns in your arena config
  * Reload with **/bp reload** if you have just changed a value
* Selecting an initial pattern
  * If you name a pattern start it will load first
* Selecting the game over pattern
  * If you name a pattern end it will load when the game is over
  
#### Remove a pattern

* List the active patterns
  * Use **/bp listpatterns \<arenaName>** to list all active patterns for this arena
* Remove a paattern
  * Use **/bp removepattern \<arenaName> \<patternName>** to list all active patterns for this arena

## Audio setup

Music playback is handled by an external audio provider. Two providers are supported:

* **OpenAudioMc** — requires the [OpenAudioMc](https://openaudiomc.net/) plugin on the same server. Audio files are served from your `CdnBaseUrl`.
* **Central Hub (Aura)** — connects to a hosted Aura instance. Configure `ApiBaseUrl` and `FrontendBaseUrl`; a `ServerKey` is generated automatically on first startup. Both URLs must use `https://`.

Enable and choose a provider in the `Audio` section of `config.yml`, then:

* Add songs to your arena
  * Ingame: **/bp addsong \<arenaName> \<trackName>** (with the Central Hub provider, track names are validated against the synced catalog — use **/bp songs** to browse it)
  * Or add them to the `SongManager` list in your arena config
* Enable the use of web songs
  * Make sure `UseWebSongs: true` is set in your arena config
  
## Sign setup

You can also use signs to let players join to the arena. Your sign should look something like this:

| Line |     Text     |
|:----:|:------------:|
|   1  | [BlockParty] |
|   2  | \<arenaName> |
|   3  |              |
|   4  |              |

#### Customize your sign messages

Head over to your config.yml in the `plugins/BlockParty/` folder. You can edit the used text that is displayed on the sign right there.

## Permissions

* **blockparty.admin.***
  * This provides access to all BlockParty commands (/bp admin)
* **blockparty.user.***
  * This provides access to basic BlockParty commands (/bp help)
