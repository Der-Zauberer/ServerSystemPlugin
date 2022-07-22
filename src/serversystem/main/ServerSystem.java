package serversystem.main;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import serversystem.commands.AdminCommand;
import serversystem.commands.BackCommand;
import serversystem.commands.BuildCommand;
import serversystem.commands.EnderchestCommand;
import serversystem.commands.FlyCommand;
import serversystem.commands.GroupCommand;
import serversystem.commands.InventoryCommand;
import serversystem.commands.LobbyCommand;
import serversystem.commands.PermissionCommand;
import serversystem.commands.PermissionReloadCommand;
import serversystem.commands.SpeedCommand;
import serversystem.commands.VanishCommand;
import serversystem.commands.WarpCommand;
import serversystem.commands.WorldCommand;
import serversystem.config.Config;
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
import serversystem.signs.WarpSign;
import serversystem.signs.WorldSign;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.ExtendedItemStack;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.PlayerInventory;
import serversystem.utilities.ServerGroup;
import serversystem.utilities.ServerList;
import serversystem.utilities.ServerSign;
import serversystem.utilities.ServerWarp;
import serversystem.utilities.TeamUtil;
import serversystem.utilities.WorldGroup;

public class ServerSystem extends JavaPlugin {

	private static ServerList<ServerWarp> warps;
	private static ServerList<ServerGroup> groups;
	private static ServerSystem instance;

	@Override
	public void onEnable() {
		instance = this;
		registerEvents();
		registerCommands();
		registerWorldSigns();
		Config.getWorlds().stream().filter(world -> Bukkit.getWorld(world) == null).forEach(world ->  {
			Bukkit.getWorlds().add(new WorldCreator(world).createWorld());
		});
		WorldGroup.autoCreateWorldGroups();
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerQuitListener.onPlayerQuit(new PlayerQuitEvent(player, ""));
			PlayerJoinListener.onPlayerJoin(new PlayerJoinEvent(player, ""));
		}
		WorldGroup.autoRemoveWorldGroups();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> WorldGroup.autoSavePlayerStats(), 1L, (long) 120 * 20);
	}

	@Override
	public void onDisable() {
		WorldGroup.autoSavePlayerStats();
		TeamUtil.resetTeams();
		Bukkit.getOnlinePlayers().forEach(player -> PermissionUtil.resetPlayerPermissions(player));
	}

	private static void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new CommandPreprocessListener(), instance);
		Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), instance);
		Bukkit.getPluginManager().registerEvents(new ExplotionListener(), instance);
		Bukkit.getPluginManager().registerEvents(new HungerListener(), instance);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), instance);
		Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), instance);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), instance);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), instance);
		Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(), instance);
		Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), instance);
		
		Bukkit.getPluginManager().registerEvents(new BuildCommand(), instance);
		
		Bukkit.getPluginManager().registerEvents(ChatUtil.getListener(), instance);
		Bukkit.getPluginManager().registerEvents(ExtendedItemStack.getListener(), instance);
		Bukkit.getPluginManager().registerEvents(PermissionUtil.getListener(), instance);
		Bukkit.getPluginManager().registerEvents(PlayerInventory.getListener(), instance);
		Bukkit.getPluginManager().registerEvents(ServerSign.getListener(), instance);
	}

	private static void registerCommands() {
		instance.getCommand("admin").setExecutor(new AdminCommand());
		instance.getCommand("back").setExecutor(new BackCommand());
		instance.getCommand("build").setExecutor(new BuildCommand());
		instance.getCommand("enderchest").setExecutor(new EnderchestCommand());
		instance.getCommand("fly").setExecutor(new FlyCommand());
		instance.getCommand("group").setExecutor(new GroupCommand());
		instance.getCommand("inventory").setExecutor(new InventoryCommand());
		instance.getCommand("lobby").setExecutor(new LobbyCommand());
		instance.getCommand("permission").setExecutor(new PermissionCommand());
		instance.getCommand("permissionreload").setExecutor(new PermissionReloadCommand());
		instance.getCommand("speed").setExecutor(new SpeedCommand());
		instance.getCommand("vanish").setExecutor(new VanishCommand());
		instance.getCommand("warp").setExecutor(new WarpCommand());
		instance.getCommand("world").setExecutor(new WorldCommand());
	}

	private static void registerWorldSigns() {
		ServerSign.registerServerSign(new WorldSign());
		ServerSign.registerServerSign(new WarpSign());
	}
	
	public static ServerList<ServerWarp> getWarps() {
		if (warps == null) warps = new ServerList<>(Config::loadWarps);
		return warps;
	}
	
	public static ServerList<ServerGroup> getGroups() {
		if (groups == null) groups = new ServerList<>(Config::loadGroups);
		return groups;
	}
	
	public static ServerSystem getInstance() {
		return instance;
	}

}
