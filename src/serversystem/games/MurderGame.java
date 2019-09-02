package serversystem.games;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle.EnumTitleAction;
import serversystem.main.ServerSystem;
import serversystem.utilities.PlayerPacket;
import serversystem.utilities.PlayerTeam;
import serversystem.utilities.ServerGame;
import serversystem.utilities.WorldGroup;

public class MurderGame extends ServerGame{
	
	private Player murder;
	private Player detective;
	private int gameTime;
	private int gameScheduler;
	
	public MurderGame(WorldGroup worldgroup, int minPlayers, int maxPlayers, int startPlayers, int startTime) {
		super(worldgroup, minPlayers, maxPlayers, startPlayers, startTime, "Murder");
		PlayerTeam.createTeam(worldgroup.getName() + "Murder", "", ChatColor.WHITE);
		PlayerTeam.createTeam(worldgroup.getName() + "Detective", "", ChatColor.WHITE);
		PlayerTeam.createTeam(worldgroup.getName() + "Innocent", "", ChatColor.WHITE);
		
	}
	
	@Override
	public void onGameStart() {
		super.onGameStart();
		Random random = new Random(getWorldgroup().getPlayers().size());
		murder = getWorldgroup().getPlayers().get(random.nextInt());
		do {
			detective = getWorldgroup().getPlayers().get(random.nextInt());
		} while (murder == detective);
		PlayerTeam.addPlayerToTeam(getWorldgroup() + "Murder", murder);
		PlayerTeam.addPlayerToTeam(getWorldgroup() + "Detective", detective);
		getBossbar().setColor(BarColor.GREEN);
		getBossbar().setTitle(ChatColor.GREEN + "Players alive " + getWorldgroup().getCurrentPlayers() + "/" + getWorldgroup().getCurrentPlayers());
		getBossbar().setVisible(true);
		getBossbar().setProgress(1);
		for (Player player : getWorldgroup().getPlayers()) {
			getBossbar().addPlayer(player);
			if (player != murder && player != detective) {
				PlayerTeam.addPlayerToTeam(getWorldgroup() + "Innocent", player);
			}
		}
		gameTime = 185;
		gameScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(ServerSystem.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (gameTime == 183) {
					for (Player player : getWorldgroup().getPlayers()) {if(player.getGameMode() != GameMode.ADVENTURE) {PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "3", "red", 0, 10);} }
				}
				if (gameTime == 182) {
					for (Player player : getWorldgroup().getPlayers()) {if(player.getGameMode() != GameMode.ADVENTURE) {PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "2", "red", 0, 10);} }
				}
				if (gameTime == 181) {
					for (Player player : getWorldgroup().getPlayers()) {if(player.getGameMode() != GameMode.ADVENTURE) {PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "1", "red", 0, 10);} }
				}
				if (gameTime == 180) {
					for (Player player : getWorldgroup().getPlayers()) {
						if (player == murder && player.getGameMode() == GameMode.ADVENTURE) {
							PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "Murder", "red", 0, 10);
							PlayerPacket.sendTitle(player, EnumTitleAction.SUBTITLE, "Kill all player!", "green", 0, 10);
						} else if (player == detective && player.getGameMode() == GameMode.ADVENTURE) {
							PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "Detective", "aqua", 0, 10);
							PlayerPacket.sendTitle(player, EnumTitleAction.SUBTITLE, "Kill the murder!", "yellow", 0, 10);
						} else if (player != murder && player != detective && player.getGameMode() == GameMode.ADVENTURE) {
							PlayerPacket.sendTitle(player, EnumTitleAction.TITLE, "Innocent", "green", 0, 10);
							PlayerPacket.sendTitle(player, EnumTitleAction.SUBTITLE, "Stay alive as long as possible!", "yellow", 0, 10);
						}
					}
				}
				if (gameTime == 0) {
					Bukkit.getScheduler().cancelTask(gameScheduler);
				}
				gameTime--;
			}
		}, 0, 20L);
	}
	
	@Override
	public void onGameEnds() {
		Bukkit.getScheduler().cancelTask(gameScheduler);
		murder = null;
		detective = null;
		super.onGameEnds();
	}
	
	public void onDeath(Player player) {
		if(player == murder) {
			
		} else if (player == detective) {
			
		} else if (player != murder && player != detective) {
			player.setGameMode(GameMode.SPECTATOR);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		PlayerTeam.removeTeam(getWorldgroup().getName() + "Murder");
		PlayerTeam.removeTeam(getWorldgroup().getName() + "Murder");
		PlayerTeam.removeTeam(getWorldgroup().getName() + "Murder");
		super.finalize();
	}

}
