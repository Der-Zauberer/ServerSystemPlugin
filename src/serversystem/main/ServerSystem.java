package serversystem.main;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import serversystem.cityadventure.CityBuild;
import serversystem.commands.AdminCommand;
import serversystem.commands.BuildCommand;
import serversystem.commands.EnderchestCommand;
import serversystem.commands.InventoryCommand;
import serversystem.commands.LobbyCommand;
import serversystem.commands.PermissionCommand;
import serversystem.commands.PlotCommand;
import serversystem.commands.VanishCommand;
import serversystem.commands.WorldCommand;
import serversystem.config.Config;
import serversystem.config.SaveConfig;
import serversystem.events.ChatListener;
import serversystem.events.CommandPreprocessListener;
import serversystem.events.EntityDamageListener;
import serversystem.events.ExplotionListener;
import serversystem.events.HungerListener;
import serversystem.events.PlayerDeathListener;
import serversystem.events.PlayerInteractListener;
import serversystem.events.PlayerJoinListener;
import serversystem.events.PlayerQuitListener;
import serversystem.events.PlayerRespawnListener;
import serversystem.events.PlayerTeleportListener;
import serversystem.events.SignChangeListener;
import serversystem.extraitems.UltraSwoardItem;
import serversystem.handler.ExtraItemHandler;
import serversystem.handler.MenuHandler;
import serversystem.handler.SignHandler;
import serversystem.handler.TeamHandler;
import serversystem.handler.WorldGroupHandler;
import serversystem.signs.ExtraItemSign;
import serversystem.signs.WorldSign;
import serversystem.utilities.WorldGroup;

public class ServerSystem extends JavaPlugin{
	
	private static ServerSystem instance;
	private static ExtraItemHandler extraitemhandler = new ExtraItemHandler();
	private static MenuHandler menuhandler = new MenuHandler();
	private static SignHandler signhandler = new SignHandler();
	private static TeamHandler teamhanler = new TeamHandler();
	private static WorldGroupHandler worldgrouphandler = new WorldGroupHandler();
	
	@Override
	public void onEnable() {
		new Config();
		new SaveConfig();
		TeamHandler.initializeTeams();
		registerEvents();
		registerCommands();
		registerWorldSigns();
		registerExtaItems();
		setInstance(this);
		for (String world : Config.getLoadWorlds()) {
			if(Bukkit.getWorld(world) == null) {
				Bukkit.getWorlds().add(new WorldCreator(world).createWorld());
			}
		}
		for (World world : Bukkit.getWorlds()) {
			WorldGroupHandler.addWorldGroup(new WorldGroup(world.getName(), world));
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			TeamHandler.addPlayerToRole(player);
			if(Config.lobbyExists() && Config.getLobbyWorld() != null) {
				player.teleport(Config.getLobbyWorld().getSpawnLocation());
			}
		}
		Bukkit.getWorld("world").setMonsterSpawnLimit(0);
	}

	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
		Bukkit.getPluginManager().registerEvents(new CommandPreprocessListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
		Bukkit.getPluginManager().registerEvents(new ExplotionListener(), this);
		Bukkit.getPluginManager().registerEvents(new HungerListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);
		Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);
		
		Bukkit.getPluginManager().registerEvents(new BuildCommand(), this);
		Bukkit.getPluginManager().registerEvents(new CityBuild(), this);
		Bukkit.getPluginManager().registerEvents(new ExtraItemHandler(), this);
		Bukkit.getPluginManager().registerEvents(new MenuHandler(), this);
	}
	
	private void registerCommands() {
		getCommand("admin").setExecutor(new AdminCommand());
		getCommand("build").setExecutor(new BuildCommand());
		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("world").setExecutor(new WorldCommand());
		getCommand("permission").setExecutor(new PermissionCommand());
		getCommand("lobby").setExecutor(new LobbyCommand());
		getCommand("plot").setExecutor(new PlotCommand());
		getCommand("enderchest").setExecutor(new EnderchestCommand());
		getCommand("inventory").setExecutor(new InventoryCommand());
	}
	
	private void registerWorldSigns() {
		SignHandler.registerServerSign(new WorldSign());
		SignHandler.registerServerSign(new ExtraItemSign());
	}
	
	private void registerExtaItems() {
		ExtraItemHandler.registerExtraItem(new UltraSwoardItem());	
	}
		
	public static ServerSystem getInstance() {
		return instance;
	}
	
	public static void setInstance(ServerSystem instance) {
		ServerSystem.instance = instance;
	}
	
	public static ExtraItemHandler getExtraItemHandler() {
		return extraitemhandler;
	}
	
	public static MenuHandler getMenuHandler() {
		return menuhandler;
	}
	
	public static SignHandler getSignHandler() {
		return signhandler;
	}
	
	public static TeamHandler getTeamHandler() {
		return teamhanler;
	}
	
	public static WorldGroupHandler getWorldGroupHandler() {
		return worldgrouphandler;
	}
	
}
