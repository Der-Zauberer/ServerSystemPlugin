package serversystem.main;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import serversystem.commands.AdminCommand;
import serversystem.commands.BuildCommand;
import serversystem.commands.EnderchestCommand;
import serversystem.commands.InventoryCommand;
import serversystem.commands.LobbyCommand;
import serversystem.commands.PermissionCommand;
import serversystem.commands.VanishCommand;
import serversystem.commands.WTPCommand;
import serversystem.commands.WorldCommand;
import serversystem.config.Config;
import serversystem.config.SaveConfig;
import serversystem.events.CommandPreprocessListener;
import serversystem.events.EntityDamageListener;
import serversystem.events.ExplotionListener;
import serversystem.events.HungerListener;
import serversystem.events.PlayerDeathListener;
import serversystem.events.PlayerJoinListener;
import serversystem.events.PlayerQuitListener;
import serversystem.events.PlayerTeleportListener;
import serversystem.handler.ChatHandler;
import serversystem.handler.InventoryHandler;
import serversystem.handler.PermissionHandler;
import serversystem.handler.PlayerBuildMode;
import serversystem.handler.SignHandler;
import serversystem.handler.TeamHandler;
import serversystem.handler.WorldGroupHandler;
import serversystem.signs.WorldSign;

public class ServerSystem extends JavaPlugin{
	
	private static ServerSystem instance;
	
	@Override
	public void onEnable() {
		new Config();
		new SaveConfig();
		TeamHandler.initializeTeams();
		registerEvents();
		registerCommands();
		registerWorldSigns();
		setInstance(this);
		for (String world : Config.getLoadWorlds()) {
			if(Bukkit.getWorld(world) == null) {
				Bukkit.getWorlds().add(new WorldCreator(world).createWorld());
			}
		}
		WorldGroupHandler.autoCreateWorldGroups();
		for (Player player : Bukkit.getOnlinePlayers()) {
			PermissionHandler.removeConfigPermissions(player);
			PermissionHandler.addConfigPermissions(player);
			PermissionHandler.reloadPlayerPermissions(player);
			TeamHandler.addRoleToPlayer(player);
			if(Config.lobbyExists() && Config.getLobbyWorld() != null) {
				player.teleport(Config.getLobbyWorld().getSpawnLocation());
			}
		}
		WorldGroupHandler.autoRemoveWorldGroups();
		if(Config.lobbyExists() && Config.getLobbyWorld() != null) {
			Config.getLobbyWorld().setMonsterSpawnLimit(0);
		}
	}
	
	@Override
	public void onDisable() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(WorldGroupHandler.isEnabled()) {
				SaveConfig.saveGamemode(player, WorldGroupHandler.getWorldGroup(player));
				SaveConfig.saveInventory(player, WorldGroupHandler.getWorldGroup(player));
				SaveConfig.saveXp(player, WorldGroupHandler.getWorldGroup(player));
			}
			SaveConfig.saveLocation(player);
		}
		TeamHandler.resetTeams();
	}

	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new CommandPreprocessListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
		Bukkit.getPluginManager().registerEvents(new ExplotionListener(), this);
		Bukkit.getPluginManager().registerEvents(new HungerListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);
		
		Bukkit.getPluginManager().registerEvents(new ChatHandler(), this);
		Bukkit.getPluginManager().registerEvents(new PermissionHandler(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerBuildMode(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryHandler(), this);
		Bukkit.getPluginManager().registerEvents(new SignHandler(), this);
	}
	
	private void registerCommands() {
		getCommand("admin").setExecutor(new AdminCommand());
		getCommand("build").setExecutor(new BuildCommand());
		getCommand("enderchest").setExecutor(new EnderchestCommand());
		getCommand("inventory").setExecutor(new InventoryCommand());
		getCommand("lobby").setExecutor(new LobbyCommand());
		getCommand("permission").setExecutor(new PermissionCommand());
		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("world").setExecutor(new WorldCommand());
		getCommand("wtp").setExecutor(new WTPCommand());
	}
	
	private void registerWorldSigns() {
		SignHandler.registerServerSign(new WorldSign());
	}
		
	public static ServerSystem getInstance() {
		return instance;
	}
	
	public static void setInstance(ServerSystem instance) {
		ServerSystem.instance = instance;
	}
	
}
