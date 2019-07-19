package system.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import system.commands.SystemCommands;
import system.events.SystemEvents;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		registerEvents();
		registerCommands();
	}

	@Override
	public void onDisable() {
	}

	public void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new SystemEvents(), this);
	}
	
	public void registerCommands() {
		getCommand("test").setExecutor(new SystemCommands());
	}

}
