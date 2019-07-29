package system.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import system.commands.AdminCommand;
import system.commands.BuildCommand;
import system.commands.VanishCommand;
import system.commands.WorldCommand;
import system.events.SystemEvents;
import system.utilities.PlayerTeam;
import system.utilities.PlayerVanish;

public class System extends JavaPlugin{
	
	private static System instance;
	
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
	}
	
	private void registerTeams() {
		PlayerTeam.createTeam("RankAdmin", "[Admin] ", ChatColor.RED);
		PlayerTeam.createTeam("RankTeam", "[Team] ", ChatColor.BLUE);
		PlayerTeam.createTeam("Vanish", "[Vansihed] ", ChatColor.GRAY);
	}
		
	public static System getInstance() {
		return instance;
	}
	
	public static void setInstance(System instance) {
		System.instance = instance;
	}
	
}
