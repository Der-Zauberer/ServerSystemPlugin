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
import serversystem.commands.PermissionReloadComnmand;
import serversystem.commands.SpeedCommand;
import serversystem.commands.VanishCommand;
import serversystem.commands.WTPCommand;
import serversystem.commands.WarpCommand;
import serversystem.commands.WorldCommand;
import serversystem.config.Config;
import serversystem.config.Config.ConfigOption;
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
import serversystem.utilities.ServerSign;
import serversystem.utilities.TeamUtil;
import serversystem.utilities.WorldGroup;

public class ServerSystem extends JavaPlugin {

	private static ServerSystem instance;

	@Override
	public void onEnable() {
		setInstance(this);
		registerEvents();
		registerCommands();
		registerWorldSigns();
		for (String world : Config.getLoadWorlds()) {
			if (Bukkit.getWorld(world) == null) {
				Bukkit.getWorlds().add(new WorldCreator(world).createWorld());
			}
		}
		WorldGroup.autoCreateWorldGroups();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (WorldGroup.isEnabled() && WorldGroup.getWorldGroup(player) == null) WorldGroup.getWorldGroup(player.getWorld()).join(player);
			for (Player everyPlayer : Bukkit.getOnlinePlayers()) {
				player.showPlayer(this, everyPlayer);
			}
			TeamUtil.addRoleToPlayer(player);
			if (Config.getConfigOption(ConfigOption.LOBBY) && Config.getLobbyWorld() != null) {
				player.teleport(Config.getLobbyWorld().getSpawnLocation());
			}
		}
		WorldGroup.autoRemoveWorldGroups();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> WorldGroup.autoSavePlayerStats(), 1L, (long) 120 * 20);
		Bukkit.getScheduler().runTaskLater(this, () -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				PermissionUtil.loadPlayerPermissions(player);
				TeamUtil.addRoleToPlayer(player);
				ChatUtil.sendServerTitle(player);
				ChatUtil.sendServerTablistTitle(player);
			}
		}, 40);
	}

	@Override
	public void onDisable() {
		WorldGroup.autoSavePlayerStats();
		TeamUtil.resetTeams();
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
		
		Bukkit.getPluginManager().registerEvents(ChatUtil.getInstance(), instance);
		Bukkit.getPluginManager().registerEvents(ExtendedItemStack.getInstance(), instance);
		Bukkit.getPluginManager().registerEvents(PermissionUtil.getInstance(), instance);
		Bukkit.getPluginManager().registerEvents(PlayerInventory.getInstance(), instance);
		Bukkit.getPluginManager().registerEvents(ServerSign.getInstance(), instance);
	}

	private static void registerCommands() {
		instance.getCommand("admin").setExecutor(new AdminCommand());
		instance.getCommand("build").setExecutor(new BuildCommand());
		instance.getCommand("enderchest").setExecutor(new EnderchestCommand());
		instance.getCommand("inventory").setExecutor(new InventoryCommand());
		instance.getCommand("lobby").setExecutor(new LobbyCommand());
		instance.getCommand("permission").setExecutor(new PermissionCommand());
		instance.getCommand("permissionreload").setExecutor(new PermissionReloadComnmand());
		instance.getCommand("speed").setExecutor(new SpeedCommand());
		instance.getCommand("vanish").setExecutor(new VanishCommand());
		instance.getCommand("warp").setExecutor(new WarpCommand());
		instance.getCommand("world").setExecutor(new WorldCommand());
		instance.getCommand("wtp").setExecutor(new WTPCommand());
	}

	private static void registerWorldSigns() {
		ServerSign.registerServerSign(new WorldSign());
		ServerSign.registerServerSign(new WarpSign());
	}

	public static void setInstance(ServerSystem instance) {
		ServerSystem.instance = instance;
	}
	
	public static ServerSystem getInstance() {
		return instance;
	}

}
