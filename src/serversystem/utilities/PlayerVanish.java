package serversystem.utilities;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import serversystem.main.ServerSystem;

public class PlayerVanish {
	
	private static ArrayList<String> vanishedPlayers = new ArrayList<>();
	
	public static void vanishPlayer(Player player) {
		vanishPlayer(player, player);
	}
	
	public static void vanishPlayer(Player player, Player sender) {
		if(player == sender) {
			if(vanishedPlayers.contains(player.getUniqueId().toString())) {
				vanishedPlayers.remove(player.getUniqueId().toString());
				for(Player everyPlayer : Bukkit.getOnlinePlayers()) {
					PlayerTeam.addRankTeam(player);
					everyPlayer.showPlayer(ServerSystem.getInstance(), player);
				}
				player.sendMessage(ChatColor.YELLOW + "[Server] You are no longer vanished!");
			} else {
				for(Player everyPlayer : Bukkit.getOnlinePlayers()) {
					PlayerTeam.addPlayerToTeam(ServerSystem.TEAMVANISH, player.getName());
					everyPlayer.hidePlayer(ServerSystem.getInstance(), player);
				}
				if (!vanishedPlayers.isEmpty()) {
					for(String vansihedplayer : vanishedPlayers) {
						Bukkit.getPlayer(vansihedplayer).showPlayer(ServerSystem.getInstance(), player);
					}
				}
				vanishedPlayers.add(player.getUniqueId().toString());
				player.sendMessage(ChatColor.YELLOW + "[Server] You are now vanished!");
			}
		} else {
			if(vanishedPlayers.contains(player.getUniqueId().toString())) {
				vanishedPlayers.remove(player.getUniqueId().toString());
				for(Player everyPlayer : Bukkit.getOnlinePlayers()) {
					PlayerTeam.addRankTeam(player);
					everyPlayer.showPlayer(ServerSystem.getInstance(), player);
				}
				player.sendMessage(ChatColor.YELLOW + "You are no longer vanished!");
				sender.sendMessage(ChatColor.YELLOW + "[Server] " + player.getName() + " is no longer vanished!");
			} else {
				for(Player everyPlayer : Bukkit.getOnlinePlayers()) {
					PlayerTeam.addPlayerToTeam(ServerSystem.TEAMVANISH, player.getName());
					everyPlayer.hidePlayer(ServerSystem.getInstance(), player);
				}
				if (!vanishedPlayers.isEmpty()) {
					for(String vansihedplayer : vanishedPlayers) {
						Bukkit.getPlayer(vansihedplayer).showPlayer(ServerSystem.getInstance(), player);
					}
				}
				vanishedPlayers.add(player.getUniqueId().toString());
				player.sendMessage(ChatColor.YELLOW + "You are now vanished!");
				sender.sendMessage(ChatColor.YELLOW + "[Server] " + player.getName() + " is now vanished!");
			}
		}
		if(!Bukkit.getOnlinePlayers().contains(player)) {
			sender.sendMessage(ChatColor.YELLOW + "[Server] " + player.getName() + " isn't online!");
		}
	}
	
	public static boolean isPlayerVanished(Player player) {
		if(vanishedPlayers.contains(player.getUniqueId().toString())) {
			return true;
		}
		return false;
	}
	
	public static ArrayList<String> getVanishedPlayers() {
		return vanishedPlayers;
	}

}
