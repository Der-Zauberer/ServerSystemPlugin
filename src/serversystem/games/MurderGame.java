package serversystem.games;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle.EnumTitleAction;
import serversystem.handler.PlayerPacket;
import serversystem.handler.TeamHandler;
import serversystem.main.ServerSystem;
import serversystem.utilities.ServerGame;
import serversystem.utilities.WorldGroup;

public class MurderGame extends ServerGame{
	
	private Player murderer;
	private Player detective;
	private int gameTime;
	private int gameScheduler;
	
	public MurderGame(WorldGroup worldgroup, int minPlayers, int maxPlayers, int startPlayers, int startTime) {
		super(worldgroup, minPlayers, maxPlayers, startPlayers, startTime, "Murder");
		TeamHandler.createTeam(worldgroup.getName() + "Murder", "", ChatColor.WHITE);
		TeamHandler.createTeam(worldgroup.getName() + "Detective", "", ChatColor.WHITE);
		TeamHandler.createTeam(worldgroup.getName() + "Innocent", "", ChatColor.WHITE);
	}
	
	@Override
	public void onGameStart() {
		super.onGameStart();
		Random random = new Random(getWorldgroup().getPlayers().size());
		murderer = getWorldgroup().getPlayers().get(random.nextInt());
		do {
			detective = getWorldgroup().getPlayers().get(random.nextInt());
		} while (murderer == detective);
		TeamHandler.addPlayerToTeam(getWorldgroup() + "Murderer", murderer);
		TeamHandler.addPlayerToTeam(getWorldgroup() + "Detective", detective);
		getBossbar().setColor(BarColor.GREEN);
		getBossbar().setTitle(ChatColor.GREEN + "Players alive " + getWorldgroup().getCurrentPlayers() + "/" + getWorldgroup().getCurrentPlayers());
		getBossbar().setVisible(true);
		getBossbar().setProgress(1);
		for (Player player : getWorldgroup().getPlayers()) {
			getBossbar().addPlayer(player);
			if (player != murderer && player != detective) {
				TeamHandler.addPlayerToTeam(getWorldgroup() + "Innocent", player);
			}
		}
		gameTime = 185;
		gameScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(ServerSystem.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (gameTime == 183) {
					for (Player player : getWorldgroup().getPlayers()) {if(player.getGameMode() != GameMode.ADVENTURE) {PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "3", "red", 10);} }
				}
				if (gameTime == 182) {
					for (Player player : getWorldgroup().getPlayers()) {if(player.getGameMode() != GameMode.ADVENTURE) {PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "2", "red", 10);} }
				}
				if (gameTime == 181) {
					for (Player player : getWorldgroup().getPlayers()) {if(player.getGameMode() != GameMode.ADVENTURE) {PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "1", "red", 10);} }
				}
				if (gameTime == 180) {
					for (Player player : getWorldgroup().getPlayers()) {
						if (player == murderer && player.getGameMode() == GameMode.ADVENTURE) {
							PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "Murderer", "red", 10);
							PlayerPacket.sendTitle(player, EnumTitleAction.SUBTITLE, "Kill all player!", "green", 10);
						} else if (player == detective && player.getGameMode() == GameMode.ADVENTURE) {
							PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "Detective", "aqua", 10);
							PlayerPacket.sendTitle(player, EnumTitleAction.SUBTITLE, "Kill the murderer!", "yellow", 10);
						} else if (player != murderer && player != detective && player.getGameMode() == GameMode.ADVENTURE) {
							PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "Innocent", "green", 10);
							PlayerPacket.sendTitle(player, EnumTitleAction.SUBTITLE, "Stay alive as long as possible!", "yellow", 10);
						}
					}
				}
				if (gameTime == 0) {
					Bukkit.getScheduler().cancelTask(gameScheduler);
					for (Player player : getWorldgroup().getPlayers()) {
						PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "Innocents win!", "green", 10);
						PlayerPacket.sendTitle(player, EnumTitleAction.SUBTITLE, "The murderer was " + murderer.getName() + " !", "green", 10);
					}
					onGameEnds();
				}
				gameTime--;
			}
		}, 0, 20L);
	}
	
	@Override
	public void onGameEnds() {
		Bukkit.getScheduler().cancelTask(gameScheduler);
		murderer = null;
		detective = null;
		super.onGameEnds();
	}
	
	@Override
	public void onPlayerLeave(Player player) {
		super.onPlayerLeave(player);
		if(murderer != null) {
			ArrayList<Player> innocents = new ArrayList<>();
			for(Player players : getWorldgroup().getPlayers()) {
				if (players.getGameMode() == GameMode.ADVENTURE && (players != murderer && players != detective)) {
					innocents.add(players);
				}
			}
			if (innocents.size() == 0) {
				for (Player players : getWorldgroup().getPlayers()) {
					PlayerPacket.sendTitle(players, EnumTitleAction.TITLE, "Murder win!", "red", 10);
					PlayerPacket.sendTitle(players, EnumTitleAction.SUBTITLE, "The murderer was " + murderer.getName() + " !", "red", 10);
				}
			}
		}
	}
	
	public void onDeath(Player player) {
		if(player == murderer) {
			player.setGameMode(GameMode.SPECTATOR);
			for (Player players : getWorldgroup().getPlayers()) {
				PlayerPacket.sendTitle(players, EnumTitleAction.TITLE, "Innocents win!", "green", 10);
				PlayerPacket.sendTitle(players, EnumTitleAction.SUBTITLE, "The murderer was " + murderer.getName() + " !", "red", 10);
			}
			onGameEnds();
		} else if (player == detective) {
			player.setGameMode(GameMode.SPECTATOR);
			for (Player players : getWorldgroup().getPlayers()) {
				PlayerPacket.sendTitle(players, EnumTitleAction.TITLE, "Murder win!", "red", 10);
				PlayerPacket.sendTitle(players, EnumTitleAction.SUBTITLE, "The murderer was " + murderer.getName() + " !", "red", 10);
			}
			onGameEnds();
		} else if (player != murderer && player != detective) {
			player.setGameMode(GameMode.SPECTATOR);
			ArrayList<Player> innocents = new ArrayList<>();
			for(Player players : getWorldgroup().getPlayers()) {
				if (players.getGameMode() == GameMode.ADVENTURE && (players != murderer && players != detective)) {
					innocents.add(players);
				}
			}
			if (innocents.size() == 0) {
				for (Player players : getWorldgroup().getPlayers()) {
					PlayerPacket.sendTitle(players, EnumTitleAction.TITLE, "Murder win!", "red", 10);
					PlayerPacket.sendTitle(players, EnumTitleAction.SUBTITLE, "The murderer was " + murderer.getName() + " !", "red", 10);
				}
			}
			onGameEnds();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		TeamHandler.removeTeam(getWorldgroup().getName() + "Murder");
		TeamHandler.removeTeam(getWorldgroup().getName() + "Murder");
		TeamHandler.removeTeam(getWorldgroup().getName() + "Murder");
		super.finalize();
	}

}
