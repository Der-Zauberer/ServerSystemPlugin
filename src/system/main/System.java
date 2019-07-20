package system.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import system.commands.AdminCommand;
import system.commands.BuildCommand;
import system.commands.LobbyCommand;
import system.commands.LobbySetupCommand;
import system.events.SystemEvents;

public class System extends JavaPlugin{
	
	@Override
	public void onEnable() {
		new Config();
		registerEvents();
		registerCommands();
	}

	@Override
	public void onDisable() {
	}

	public void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new SystemEvents(), this);
		Bukkit.getPluginManager().registerEvents(new AdminCommand(), this);
		Bukkit.getPluginManager().registerEvents(new BuildCommand(), this);
	}
	
	public void registerCommands() {
		getCommand("admin").setExecutor(new AdminCommand());
		getCommand("build").setExecutor(new BuildCommand());
		getCommand("lobby").setExecutor(new LobbyCommand());
		getCommand("lobbysetup").setExecutor(new LobbySetupCommand());
	}
}
