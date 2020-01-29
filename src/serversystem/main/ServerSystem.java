package serversystem.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import serversystem.cityadventure.CityBuild;
import serversystem.commands.AdminCommand;
import serversystem.commands.BuildCommand;
import serversystem.commands.LobbyCommand;
import serversystem.commands.PermissionCommand;
import serversystem.commands.PlotCommand;
import serversystem.commands.VanishCommand;
import serversystem.commands.WorldCommand;
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
import serversystem.handler.WorldGroupHandler;
import serversystem.utilities.PlayerTeam;
import serversystem.utilities.WorldGroup;

public class ServerSystem extends JavaPlugin{
	
	private static ServerSystem instance;
	
	public static final String TEAMVANISH = "00Vanish";
	public static final String TEAMRANKADMIN = "01RankAdmin";
	public static final String TEAMRANKMODERATOR = "02RankModerator";
	public static final String TEAMRANKDEVELOPER = "03RankDeveloper";
	public static final String TEAMRANKSUPPORTER = "04RankSupporter";
	public static final String TEAMRANKTEAM = "05RankTeam";
	public static final String TEAMRANKOPERATOR = "06RankOperator";
	public static final String TEAMRANKYOUTUBER = "07RankYouTuber";
	public static final String TEAMRANKPREMIUM = "08RankPremium";
	public static final String TEAMRANKPLAYER = "09RankPlayer";
	public static final String TEAMSPECTATOR = "100Spectator";
	
	@Override
	public void onEnable() {
		new Config();
		new SaveConfig();
		registerEvents();
		registerCommands();
		setInstance(this);
		registerTeams();
		for (String world : Config.getLoadWorlds()) {
			if(Bukkit.getWorld(world) == null) {
				Bukkit.getWorlds().add(new WorldCreator(world).createWorld());
			}
		}
		for (World world : Bukkit.getWorlds()) {
			WorldGroupHandler.addWorldGroup(new WorldGroup(world.getName(), world));
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerTeam.addRankTeam(player);
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
	
		Bukkit.getPluginManager().registerEvents(new AdminCommand(), this);
		Bukkit.getPluginManager().registerEvents(new BuildCommand(), this);
		Bukkit.getPluginManager().registerEvents(new CityBuild(), this);
	}
	
	private void registerCommands() {
		getCommand("admin").setExecutor(new AdminCommand());
		getCommand("build").setExecutor(new BuildCommand());
		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("world").setExecutor(new WorldCommand());
		getCommand("permission").setExecutor(new PermissionCommand());
		getCommand("lobby").setExecutor(new LobbyCommand());
		getCommand("plot").setExecutor(new PlotCommand());
	}
	
	private void registerTeams() {
		PlayerTeam.createTeam(TEAMVANISH, "[VANISH] ", ChatColor.GRAY);
		PlayerTeam.createTeam(TEAMRANKADMIN, "[Admin] ", ChatColor.DARK_RED);
		PlayerTeam.createTeam(TEAMRANKMODERATOR, "[Moderator] ", ChatColor.DARK_BLUE);
		PlayerTeam.createTeam(TEAMRANKDEVELOPER, "[Developer] ", ChatColor.AQUA);
		PlayerTeam.createTeam(TEAMRANKSUPPORTER, "[Supporter] ", ChatColor.BLUE);
		PlayerTeam.createTeam(TEAMRANKOPERATOR, "[OP] ", ChatColor.RED);
		PlayerTeam.createTeam(TEAMRANKYOUTUBER, "[YouTube] ", ChatColor.DARK_PURPLE);
		PlayerTeam.createTeam(TEAMRANKPREMIUM, "[Premium] ", ChatColor.GOLD);
		PlayerTeam.createTeam(TEAMRANKPLAYER, "", ChatColor.WHITE);
		PlayerTeam.createTeam(TEAMSPECTATOR, "[SPECTATOR] ", ChatColor.GRAY);
	}
		
	public static ServerSystem getInstance() {
		return instance;
	}
	
	public static void setInstance(ServerSystem instance) {
		ServerSystem.instance = instance;
	}
	
}
