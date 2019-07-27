package system.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import system.main.Config;

public class SystemEvents implements Listener{
	
	Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
	Team team = null;

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event) {
		if(!Config.worldExists(event.getPlayer().getWorld().getName())) {
			Config.registerWorld(event.getPlayer().getWorld().getName());
		}
		if(!Config.isJoinMessageActiv()) {
			event.setJoinMessage("");
		}
		if(Config.lobbyExists()) {
			event.getPlayer().teleport(Config.getLobby());
		}
		if(Config.hasDefaultGamemode()) {
			event.getPlayer().setGameMode(Config.getDefaultGamemode());
		}
	}
	
	@EventHandler
	public void onQuitEvent(PlayerQuitEvent event) {
		if(!Config.isLeaveMessageActiv()) {
			event.setQuitMessage("");
		}
	}
	
	@EventHandler
	public void onAchivementGet(PlayerAnimationEvent event) {
		if (!Config.hasAchivements()) {
			event.setCancelled(true);
		}
	}
	
}
