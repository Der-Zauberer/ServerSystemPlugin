package system.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.minecraft.server.v1_14_R1.PacketPlayOutTitle.EnumTitleAction;
import system.main.Config;
import system.main.System;
import system.utilities.PlayerPacket;

public class SystemEvents implements Listener{

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event) {
		if(!Config.isJoinMessageActiv()) {
			event.setJoinMessage("");
		}
		if(Config.lobbyExists()) {
			event.getPlayer().teleport(Config.getLobby());
		}
		if(Config.hasDefaultGamemode()) {
			event.getPlayer().setGameMode(Config.getDefaultGamemode());
		}
		if(event.getPlayer().isOp()) {
			System.addPlayerToTeam("RankAdmin", event.getPlayer().getName());
		}
		PlayerPacket.sendTitle(event.getPlayer(), EnumTitleAction.TITLE, Config.getTitle(), Config.getTitleColor(), 20, 40);
		PlayerPacket.sendTitle(event.getPlayer(), EnumTitleAction.SUBTITLE, Config.getSubtitle(), Config.getSubtitleColor(), 20, 40);
	}
	
	@EventHandler
	public void onQuitEvent(PlayerQuitEvent event) {
		if(!Config.isLeaveMessageActiv()) {
			event.setQuitMessage("");
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setFormat(System.getPlayerNameColor(event.getPlayer())  + event.getPlayer().getName() + ChatColor.WHITE + ": " + event.getMessage());
	}
	
}
