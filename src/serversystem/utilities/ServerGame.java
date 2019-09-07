package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import net.minecraft.server.v1_14_R1.PacketPlayOutTitle.EnumTitleAction;
import serversystem.main.ServerSystem;

public class ServerGame {
	
	private WorldGroup worldgroup;
	private int minPlayers;
	private int maxPlayers;
	private int startPlayers;
	private int startTime;
	private int countdown;
	private int countdownSheduler;
	private Location lobby;
	private ArrayList<Location> spawn;
	private ArrayList<Team> teams;
	private String gametype;
	private String builder;
	private boolean running;
	private BossBar bossbar;
	
	public ServerGame(WorldGroup worldgroup, int minPlayers, int maxPlayers, int startPlayers, int startTime, String gametype) {
		this.worldgroup = worldgroup;
		this.setMinPlayers(minPlayers);
		this.setMaxPlayers(maxPlayers);
		this.setStartPlayers(startPlayers);
		this.setStartTime(startTime);
		this.setGametype(gametype);
		this.countdown = startTime;
		running = false;
		bossbar = Bukkit.createBossBar(ChatColor.YELLOW + "Players 0/" + maxPlayers, BarColor.YELLOW, BarStyle.SEGMENTED_10);
		bossbar.setProgress(0);
	}
	
	public void onPlayerJoin(Player player) {
		if(lobby != null) {
			player.teleport(lobby);
		}
		if(!running) {
			if(worldgroup.getCurrentPlayers() == maxPlayers) {
				onGameStart();
			} else if(worldgroup.getCurrentPlayers() < startPlayers) {
				bossbar.setTitle(ChatColor.YELLOW + "Players " + worldgroup.getCurrentPlayers() + "/" + maxPlayers);
				bossbar.setProgress((double)worldgroup.getCurrentPlayers() / (double)maxPlayers);
				bossbar.addPlayer(player);
			} else if(worldgroup.getCurrentPlayers() == startPlayers) {
				bossbar.setTitle(ChatColor.YELLOW + "Start in " + startTime + " Seconds");
				bossbar.setProgress(1);
				countdownSheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(ServerSystem.getInstance(), new Runnable() {
					@Override
					public void run() {
						if(countdown >= 0 && worldgroup.getCurrentPlayers() >= minPlayers) {
							bossbar.setProgress((double)countdown / (double)startTime);
							bossbar.setTitle(ChatColor.YELLOW + "Start in " + countdown + " Seconds");
							if(countdown == 3) {
								for(Player players : worldgroup.getPlayers()) {
									PlayerPacket.sendTitle(players, EnumTitleAction.TITLE, gametype, "yellow", 20);
									PlayerPacket.sendTitle(players, EnumTitleAction.SUBTITLE, "Map: " + worldgroup.getName(),  "aqua", 20);
								}
							}
							countdown --;
						} else {
							if(worldgroup.getCurrentPlayers() >= minPlayers) {
								onGameStart();
							}
							bossbar.setVisible(false);
							countdown = startTime;
							Bukkit.getScheduler().cancelTask(countdownSheduler);
						}
					}
				}, 0, 20L);
			}
		} else {
			player.setGameMode(GameMode.SPECTATOR);
			PlayerTeam.addPlayerToTeam(ServerSystem.TEAMSPECTATOR, player);
		}
	}
	
	public void onPlayerLeave(Player player) {
		bossbar.removePlayer(player);
		if(!running) {
			if (worldgroup.getCurrentPlayers() < startPlayers) {
				bossbar.setTitle(ChatColor.YELLOW + "Players " + worldgroup.getCurrentPlayers()  + "/" + maxPlayers);
			}
		}
		if(running && worldgroup.getCurrentPlayers() == 0) {
			onGameRestart();
		}
		player.setGameMode(GameMode.ADVENTURE);
		PlayerTeam.addRankTeam(player);
	}
	
	public void onGameStart() {
		this.running = true;
		for(Player player : worldgroup.getPlayers()) {
			player.setGameMode(GameMode.SURVIVAL);
		}
	}
	
	public void onGameRestart() {
		this.running = false;
		bossbar = Bukkit.createBossBar(ChatColor.YELLOW + "Players 0/" + maxPlayers, BarColor.YELLOW, BarStyle.SEGMENTED_10);
		bossbar.setProgress(0);
	}
	
	public void onGameEnds() {
		this.running = false;
		for(Player player : worldgroup.getPlayers()) {
			player.setGameMode(GameMode.ADVENTURE);
			player.getInventory().clear();
		}
	}
	
	public WorldGroup getWorldgroup() {
		return worldgroup;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getStartPlayers() {
		return startPlayers;
	}

	public void setStartPlayers(int startPlayers) {
		this.startPlayers = startPlayers;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	public ArrayList<Location> getSpawn() {
		return spawn;
	}

	public void setSpawn(ArrayList<Location> spawn) {
		this.spawn = spawn;
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public void setTeams(ArrayList<Team> teams) {
		this.teams = teams;
	}

	public String getGametype() {
		return gametype;
	}

	public void setGametype(String gametype) {
		this.gametype = gametype;
	}

	public String getBuilder() {
		return builder;
	}

	public void setBuilder(String builder) {
		this.builder = builder;
	}

	public BossBar getBossbar() {
		return bossbar;
	}

	public void setBossbar(BossBar bossbar) {
		this.bossbar = bossbar;
	}

}
