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
