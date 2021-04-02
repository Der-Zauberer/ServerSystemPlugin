# ServerSystemPlugin

This is a Bukkit/Spigot plugin for permissions, multiworld features and other admin features.

## Develpement and Version

API Version: *1.14.x*<br>
Spigot Version: *1.14.4-R0.1-SNAPSHOT*<br>
Plugin Version: *v1.4*<br>
Java Class Version: *52 (Java8)*<br>

## Commands

|Command|Usage|Permission|Default Permission|Description|
|---|---|---|---|---|
|admin|`/admin`|`serversystem.command.admin`|false|Open the admin menu|
|build|`/build [<player>]`|`serversystem.command.build`|false|Allow the player to build in protected worlds|
|enderchest|`/enderchest [<player>]`|`serversystem.command.enderchest`|false|Open the enderchest of a player|
|inventory|`/inventory [<player>]`|`serversystem.command.inventory`|false|Open the inventory of a player|
|lobby|`/lobby`|`serversystem.command.lobby`|true|Teleport player to lobby|
|permission|`/permission [<player>] [<group>]`|`serversystem.command.permission`|false|Set the permissions of a player|
|vanish|`/vanish [<player>]`|`serversystem.command.vanish`|false|Allow the player to vanish|
|world|`/world [action] [<world>] [<player] /world [action] [<world>] [action] [boolean]`|`serversystem.command.world`|false|Teleport player to an other world or edit an other world|

## Permissions
|Permission|Default|Description|
|---|---|---|
|`serversystem.command.admin`|op|Open the admin inventory|
|`serversystem.command.build`|op|Allow the player to build in protected worlds|
|`serversystem.command.enderchest`|op|Open the enderchest of a player|
|`serversystem.command.inventory`|op|Open the inventory of a player|
|`serversystem.command.lobby`|true|Teleport player to lobby|
|`serversystem.command.permission`|false|Set the permissions of a player|
|`serversystem.command.vanish`|op|Allow the player to vanish|
|`serversystem.command.world`|op|Teleoprt player to other teleport player to an other world or edit an other world|

The rank permissions give the player a colored name and prefix (more information at "Groups").

## Config
The config can be found at `/plugins/serversystem/plugin.yml` in your server folder.

### Disable join- and leavemessage
You have the oportunity to disable the join- and leavemessage if you don't want to have minecraft's default join- and leavemessage.
```json
Server:
  joinmessage: false
  leavemessage: false
```

### Tablist text
You can add text above and below the names of the players in the tablist.
```json
Server:
  tablist:
    title:
      text: Welcome
      color: blue
    subtitle:
      text: to this server!
      color: dark_green
```
You can use the colors codes listed in the [Minecraft Wiki](https://minecraft.gamepedia.com/Formatting_codes#Color_codes "Color Codes").

### Lobby
You can set the lobby as main world of teh server. You can go back by `/lobby` if you have this enabled and if a user is connecting to the server, he will be spawn at the lobby world.
```json
Server:
  lobby: true
  lobbyworld: world
```

### Enable WorldGroups
If you want to use multible worlds with different inventories and spawn locations, you can use the WorldGroup-System. It works simular to the plugin MultiWorld. You can enable this feature in the here:
```json
Server:
  enableworldgroups: true
```
Every world, which is loaded has a configuration section. There can the option `group` be found. Worlds are in one worldgroup if they have the same group name. When a player switch to a world in the same worldgroup, he will keep his inventory and levels. If the world have a groupname, which no other world have, then the world is alone in a worldgroup and have a separated inventory and experience level.
```json
world:
    group: world
  world_nether:
    group: world
  hogwarts:
    group: hogwarts
```
In this example world and world_nether are in the same worldgroup while the world hogwarts has his own separated worldgroup.

### Disable commands and default permissions
You can disable permissions simply by adding the permission to the list. For example, you can disable minecraft default commands by adding its permission.
```json
DisabledPermissions:
- bukkit.command.version
- bukkit.command.plugins
- bukkit.command.help
```
You can find minecraft standard commands in the [Bukkit Wiki](https://bukkit.gamepedia.com/CraftBukkit_Commands "Commands").

### Load worlds
You can load other worlds to your server, if there in your server directory. You can add a new world just by adding the world to the list. The world 'world' and the subworlds 'world_nether' and 'world_the_end' are loaded by default. You can change the default world in the file 'server.properties'.
```json
Worldload:
- hogwarts
```

### Groups
The plugin have a permission system. A group have a name and permissions as a list. If do not want do use this add nothing to the groups. 
```json
Groups:
  player: ''
  moderator:
    permissions:
    - serversystem.rank.moderator
    - serversystem.command.permission
  admin:
    permissions:
    - serversystem.command.permission
    - serversystem.rank.admin
```

The rank permission is an individual permission set by the ranks. A rank defines the visual representation of a permission.

```json
Ranks:
  01RankAdmin:
    color: dark_red
    prefix: '[Admin] '
    permission: serversystem.rank.admin
```

Make sure you have an empty space after the prefix, otherwise the prefix will be displayed directly in front of the player's name. You can choose any name for the permission as long it is the same as the permission in the groups section. We choose `serversystem.rank.admin`. The name of the rank itself is also unimportant except for the number in front of the name. The number defines the order of the player in the tabbar. The Rank `04RankSupporter` will be displayd below the `01RankAdmin` but above the `06RankPremium` for example.

You can use the colors codes listed in the [Minecraft Wiki](https://minecraft.gamepedia.com/Formatting_codes#Color_codes "Color Codes").

You can add a group to the players in the player section. 
```json
Players:
  8e1f0a29-7279-412d-a6a6-4266164d6a87:
    name: Der_Zauberer
    exists: true
    group: admin

```

### Worlds
All loaded worlds have own settings. <br>
`exists` used for internal handling, true by default<br>
`group` defines the worldgroup of the world (more information at "Enable WorldGroups")<br>
`worldspawn` the player will always spawn at worldspawn if this is true, otherwise he would spawn at the last position<br>
`protect` if this is true, blocks can't be removed ore build until the player go in buildmode<br>
`pvp` players cant damaging each other if this is false<br>
`damage` players cant take damage from any source, if this is false<br>
`hunger` if this is false, player do not have to eat<br>
`explotion` explotions can not make damage to blocks, if this is false<br>
`deathmessage` no chat message about a player death will appear, if you disable the deathmessage<br> 
`gamemode` the player will set in this gamemode, if the join to the world for the first time<br>

```json
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
    deathmessage: true
    gamemode: 2
```

### Players
A player is identified by this UUID if he change his name. For easier editing the actual name is save to to config. The existing option is for internal handling. The player can add to a permission group (more information at "Groups").

```json
Players:
  8e1f0a29-7279-412d-a6a6-4266164d6a87:
    name: Der_Zauberer
    exists: true
    group: admin
```

### Example
```json
Server:
  joinmessage: false
  leavemessage: false
  enableworldgroups: false
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
  enableworldgroups: false
DisabledPermissions:
- bukkit.command.version
- bukkit.command.plugins
- bukkit.command.help
Worldload:
- hogwarts
Groups:
  player: ''
  moderator:
    permissions:
    - serversystem.rank.moderator
    - serversystem.command.permission
  admin:
    permissions:
    - serversystem.command.permission
    - serversystem.tools.commandblock
    - serversystem.rank.admin
Ranks:
  01RankAdmin:
    color: dark_red
    prefix: '[Admin] '
    permission: serversystem.rank.admin
  02RankModerator:
    color: dark_blue
    prefix: '[Moderator] '
    permission: serversystem.rank.moderator
  03RankDeveloper:
    color: aqua
    prefix: '[Developer] '
    permission: serversystem.rank.developer
  04RankSupporter:
    color: blue
    prefix: '[Supporter] '
    permission: serversystem.rank.supporter
  05RankYouTuber:
    color: dark_purple
    prefix: '[YouTuber] '
    permission: serversystem.rank.youtuber
  06RankPremium:
    color: gold
    prefix: '[Premium] '
    permission: serversystem.rank.premium
  07RankPlayer:
    color: white
    prefix: ''
    permission: serversystem.rank.player
Worlds:
  world:
    exists: true
    group: world
    worldspawn: true
    protect: true
    pvp: false
    damage: false
    huger: true
    explosion: false
    deathmessage: true
    gamemode: 2
  world_nether:
    exists: true
    group: world
    worldspawn: false
    protect: false
    pvp: true
    damage: true
    huger: true
    explosion: true
    deathmessage: true
    gamemode: 2
  world_the_end:
    exists: true
    group: world
    worldspawn: false
    protect: false
    pvp: true
    damage: true
    huger: true
    explosion: true
    deathmessage: true
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
    deathmessage: true
    gamemode: 2
Players:
  8e1f0a29-7279-412d-a6a6-4266164d6a87:
    name: Der_Zauberer
    exists: true
    group: admin
```
