package serversystem.utilities;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import serversystem.handler.WorldGroupHandler;
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
				for(Player everyPlayer : WorldGroupHandler.getWorldGroup(player).getPlayers()) {
					PlayerTeam.addRankTeam(player);
					everyPlayer.showPlayer(ServerSystem.getInstance(), player);
				}
				if (!vanishedPlayers.isEmpty()) {
					for(String vansihedplayer : vanishedPlayers) {
						player.hidePlayer(ServerSystem.getInstance(), Bukkit.getPlayer(UUID.fromString(vansihedplayer)));
					}
				}
			} else {
				for(Player everyPlayer : Bukkit.getOnlinePlayers()) {
					PlayerTeam.addPlayerToTeam(ServerSystem.TEAMVANISH, player.getName());
					everyPlayer.hidePlayer(ServerSystem.getInstance(), player);
				}
				if (!vanishedPlayers.isEmpty()) {
					for(String vansihedplayer : vanishedPlayers) {
						Bukkit.getPlayer(UUID.fromString(vansihedplayer)).showPlayer(ServerSystem.getInstance(), player);
						player.showPlayer(ServerSystem.getInstance(), Bukkit.getPlayer(UUID.fromString(vansihedplayer)));
					}
				}
				vanishedPlayers.add(player.getUniqueId().toString());
			}
		} else {
			if(vanishedPlayers.contains(player.getUniqueId().toString())) {
				vanishedPlayers.remove(player.getUniqueId().toString());
				for(Player everyPlayer : Bukkit.getOnlinePlayers()) {
					PlayerTeam.addRankTeam(player);
					everyPlayer.showPlayer(ServerSystem.getInstance(), player);
				}
				if (!vanishedPlayers.isEmpty()) {
					for(String vansihedplayer : vanishedPlayers) {
						player.hidePlayer(ServerSystem.getInstance(), Bukkit.getPlayer(UUID.fromString(vansihedplayer)));
					}
				}
			} else {
				for(Player everyPlayer : WorldGroupHandler.getWorldGroup(player).getPlayers()) {
					PlayerTeam.addPlayerToTeam(ServerSystem.TEAMVANISH, player.getName());
					everyPlayer.hidePlayer(ServerSystem.getInstance(), player);
				}
				if (!vanishedPlayers.isEmpty()) {
					for(String vansihedplayer : vanishedPlayers) {
						Bukkit.getPlayer(UUID.fromString(vansihedplayer)).showPlayer(ServerSystem.getInstance(), player);
						player.showPlayer(ServerSystem.getInstance(), Bukkit.getPlayer(UUID.fromString(vansihedplayer)));
					}
				}
				vanishedPlayers.add(player.getUniqueId().toString());
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
	
	public static ArrayList<Player> getVanishedPlayers() {
		ArrayList<Player> players = new ArrayList<>();
		for (String player : vanishedPlayers) {
			players.add(Bukkit.getPlayer(UUID.fromString(player)));
		}
		return players;
	}

}
