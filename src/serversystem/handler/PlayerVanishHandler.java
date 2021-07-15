package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import serversystem.main.ServerSystem;

public class PlayerVanishHandler {
	
	private static ArrayList<Player> vanishedplayers = new ArrayList<>();
	
	public static void vanishPlayer(Player player) {
		vanishPlayer(player, player);
	}
	
	public static void vanishPlayer(Player player, CommandSender sender) {
		if(vanishedplayers.contains(player)) {
			showPlayer(player);
			ChatHandler.sendServerMessage(player, "You are no longer vanished!");
			if(player != sender) {
				ChatHandler.sendServerMessage(sender, player.getName() + " is no longer vanished!");
			}
		} else {
			hidePlayer(player);
			ChatHandler.sendServerMessage(player, "You are vanished now!");
			if(player != sender) {
				ChatHandler.sendServerMessage(sender, player.getName() + " is vanished now!");
			}
		}
	}
	
	public static boolean isPlayerVanished(Player player) {
		if(vanishedplayers.contains(player)) {
			return true;
		}
		return false;
	}
	
	public static ArrayList<Player> getVanishedPlayers() {
		return vanishedplayers;
	}
	
	private static void hidePlayer(Player player) {
		TeamHandler.addPlayerToTeam(TeamHandler.TEAMVANISH, player.getName());
		for(Player everyPlayer : Bukkit.getOnlinePlayers()) {
			everyPlayer.hidePlayer(ServerSystem.getInstance(), player);
		}
		if (!vanishedplayers.isEmpty()) {
			for(Player vanishedplayer : vanishedplayers) {
				vanishedplayer.showPlayer(ServerSystem.getInstance(), player);
				player.hidePlayer(ServerSystem.getInstance(), vanishedplayer);
			}
		}
		vanishedplayers.add(player);
		
	}
	
	private static void showPlayer(Player player) {
		TeamHandler.addRoleToPlayer(player);
		for(Player everyPlayer : WorldGroupHandler.getWorldGroup(player).getPlayers()) {
			everyPlayer.showPlayer(ServerSystem.getInstance(), player);
		}
		if (!vanishedplayers.isEmpty()) {
			for(Player vanishedplayer : vanishedplayers) {
				player.hidePlayer(ServerSystem.getInstance(), vanishedplayer);
			}
		}
		vanishedplayers.remove(player);
			
	}

}
