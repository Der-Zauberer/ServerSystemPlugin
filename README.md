# ServerSystemPlugin

This is a Bukkit/Spigot plugin for permisssions, multiworld features and other admin features.

## Commands

|Command|Usage|Permission|Default Permission|Description|
|---|---|---|---|---|
|admin|`/admin`|`serversystem.command.admin`|false|Open the admin menu|
|build|`/build [<player>]`|`serversystem.command.build`|false|Allow the player to build in protected worlds|
|vanish|`/vanish [<player>]`|`serversystem.command.vanish`|false|Allow the player to vanish|
|world|`/world [action] [<world>] [<player] /world [action] [<world>] [action] [boolean]`|`serversystem.command.world`|false|Teleoprt player to an other world or edit an other world|
|permission|`/permission`|`serversystem.command.permission`|false|Set the permissions of a player|
|lobby|`/lobby`|`serversystem.command.lobby`|true|Teleport player to lobby|
|enderchest|`/enderchest [<player>]`|`serversystem.command.enderchest`|false|Open the enderchest of a player|
|inventory|`/inventory [<player>]`|`serversystem.command.inventory`|false|Open the inventory of a player|
|wallet|`/wallet`|`serversystem.command.wallet`|false|Open the wallet menu|
|plot|Work in progress|

## Permissions

|Permission|Default|Description|
|---|---|---|
|`serversystem.command.admin`|op|Open the admin inventory|
|`serversystem.command.build`|op|Allow the player to build in protected worlds|
|`serversystem.command.vanish`|op|Allow the player to vanish|
|`serversystem.command.world`|op|Teleoprt player to other teleoprt player to an other world or edit an other world|
|`serversystem.command.permission`|false|Set the permissions of a player|
|`serversystem.command.lobby`|true|Teleport player to lobby|
|`serversystem.command.enderchest`|op|Open the enderchest of a player|
|`serversystem.command.inventory`|op|Open the inventory of a player|
|`serversystem.command.wallet`|op|Open the wallet menu|
|`serversystem.command.plot`|op|Work in progress|
|`serversystem.rank.admin`|false|Display admin prefix|
|`serversystem.rank.moderator`|false|Display admin prefix|
|`serversystem.rank.developer`|false|Display developer prefix|
|`serversystem.rank.supporter`|false|Display supporter prefix|
|`serversystem.rank.team`|false|Display team prefix|
|`serversystem.rank.operator`|op|Display operator prefix|
|`serversystem.rank.youtuber`|false|Display youtuber prefix|
|`serversystem.rank.premium`|false|Display premium prefix|
|`serversystem.rank.player`|true|Display player prefix|
|`serversystem.tools.signeddit`|op|Allow to create executable signs|

## Example config

```json
Server:
  joinmessage: false
  leavemessage: false
  title:
    text: 'Welcome'
    color: 'dark_blue'
  subtitle:
    text: 'to this server!'
    color: 'dark_green'
  message:
    prefix: '[Server]'
    prefixcolor: yellow
    color: yellow
    errorcolor: red
  tablist:
    title:
      text: Welcome
      color: blue
    subtitle:
      text: to this server!
      color: dark_green
  lobby: true
  lobbyworld: world
DisabledPermissions:
- bukkit.command.version
- bukkit.command.plugins
- bukkit.command.help
Worldload:
- hogwarts
Groups:
  player: ''
  moderator:
  - serversystem.rank.moderator
  - serversystem.command.permission
  admin:
  - serversystem.command.permission
  - serversystem.rank.admin
Worlds:
  world:
    exists: true
    group: world
    worldspawn: false
    protect: true
    pvp: false
    damage: false
    huger: true
    explosion: false
    gamemode: 2
    hunger: false
  world_nether:
    exists: true
    group: world_nether
    worldspawn: false
    protect: false
    pvp: true
    damage: true
    huger: true
    explosion: true
    gamemode: 2
  world_the_end:
    exists: true
    group: world_the_end
    worldspawn: false
    protect: false
    pvp: true
    damage: true
    huger: true
    explosion: true
    gamemode: 2
  hogwarts:
    exists: true
    group: hogwarts
    worldspawn: false
    protect: true
    pvp: false
    damage: true
    huger: true
    explosion: false
    gamemode: 2
Players:
  8e1f0a29-7279-412d-a6a6-4266164d6a87:
    name: Der_Zauberer
    exists: true
    group: admin
```
