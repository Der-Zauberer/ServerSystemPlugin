package serversystem.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import serversystem.commands.AdminCommand;
import serversystem.commands.BuildCommand;
import serversystem.commands.PermissionCommand;
import serversystem.commands.VanishCommand;
import serversystem.commands.WorldCommand;
import serversystem.utilities.PlayerTeam;
import serversystem.utilities.PlayerVanish;

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
		registerEvents();
		registerCommands();
		setInstance(this);
		registerTeams();
	}

	@Override
	public void onDisable() {
		for(String player : PlayerVanish.getVanishedPlayers()) {
			PlayerVanish.vanishPlayer(Bukkit.getPlayer(player));
		}
	}

	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new SystemEvents(), this);
		Bukkit.getPluginManager().registerEvents(new AdminCommand(), this);
		Bukkit.getPluginManager().registerEvents(new BuildCommand(), this);
	}
	
	private void registerCommands() {
		getCommand("admin").setExecutor(new AdminCommand());
		getCommand("build").setExecutor(new BuildCommand());
		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("world").setExecutor(new WorldCommand());
		getCommand("permission").setExecutor(new PermissionCommand());
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
