package serversystem.cityadventure;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import serversystem.utilities.ServerArea;

public class CityBuildPlot extends ServerArea {
	
	private String owner;
	private ArrayList<String> trustedPlayers;
	private ArrayList<String> bannedPlayers;

	public CityBuildPlot(Location location1, Location location2) {
		super(location1, location2);
	}
	
	public void setOwner(Player player) {
		this.owner = player.getUniqueId().toString();
	}
	
	public Player getOwner() {
		return Bukkit.getPlayer(UUID.fromString(owner));
	}
	
	public void addTrustedPlayer(Player player) {
		trustedPlayers.add(player.getUniqueId().toString());
	}
	
	public void removeTrustedPlayer(Player player) {
		trustedPlayers.remove(player.getUniqueId().toString());
	}
	
	public ArrayList<Player> getTrustedPlayers() {
		ArrayList<Player> players = new ArrayList<>();
		for (String player : trustedPlayers) {
			players.add(Bukkit.getPlayer(UUID.fromString(player)));
		}
		return players;
	}
	
	public boolean isPlayerTrusted(Player player) {
		return trustedPlayers.contains(player.getUniqueId().toString());
	}
	
	public void addBannedPlayer(Player player) {
		bannedPlayers.add(player.getUniqueId().toString());
	}
	
	public void removeBannedPlayer(Player player) {
		bannedPlayers.remove(player.getUniqueId().toString());
	}
	
	public boolean isPlayerBanned(Player player) {
		return trustedPlayers.contains(player.getUniqueId().toString());
	}
	
	public ArrayList<Player> getBannedPlayers() {
		ArrayList<Player> players = new ArrayList<>();
		for (String player : bannedPlayers) {
			players.add(Bukkit.getPlayer(UUID.fromString(player)));
		}
		return players;
	}

}
