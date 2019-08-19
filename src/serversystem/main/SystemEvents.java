package serversystem.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle.EnumTitleAction;
import serversystem.utilities.PlayerPacket;
import serversystem.utilities.PlayerPermission;
import serversystem.utilities.PlayerTeam;
import serversystem.utilities.PlayerVanish;

public class SystemEvents implements Listener{

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event) {
		Config.addPlayer(event.getPlayer());
		PlayerPermission.addConfigPermissions(event.getPlayer());
		PlayerTeam.addRankTeam(event.getPlayer());
		if(!Config.isJoinMessageActiv()) {
			event.setJoinMessage("");
		}
		if(Config.lobbyExists()) {
			event.getPlayer().teleport(Config.getLobby());
		}
		if(Config.hasDefaultGamemode()) {
			event.getPlayer().setGameMode(Config.getDefaultGamemode());
		}
		PlayerPacket.sendTitle(event.getPlayer(), EnumTitleAction.TITLE, Config.getTitle(), Config.getTitleColor(), 20, 40);
		PlayerPacket.sendTitle(event.getPlayer(), EnumTitleAction.SUBTITLE, Config.getSubtitle(), Config.getSubtitleColor(), 20, 40);
	}
	
	@EventHandler
	public void onQuitEvent(PlayerQuitEvent event) {
		if(!Config.isLeaveMessageActiv()) {
			event.setQuitMessage("");
		}
		if(PlayerVanish.isPlayerVanished(event.getPlayer())) {
			PlayerVanish.vanishPlayer(event.getPlayer());
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setFormat(PlayerTeam.getPlayerNameColor(event.getPlayer())  + event.getPlayer().getName() + ChatColor.WHITE + ": " + event.getMessage());
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			if(!Config.hasWorldDamage(event.getEntity().getWorld().getName())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			if(!Config.hasWorldHunger(event.getEntity().getWorld().getName())) {
				event.setCancelled(true);
			}
		}
	}
	
}
