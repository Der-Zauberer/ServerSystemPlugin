package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

import serversystem.config.Config;

public class PlayerBuildMode implements Listener {
	
	private static ArrayList<Player> buildplayers = new ArrayList<>();
	
	public static void buildmodePlayer(Player player) {
		buildmodePlayer(player, player);
	}
	
	public static void buildmodePlayer(Player player, Player sender) {
		if(buildplayers.contains(player)) {
			buildplayers.remove(player);
			ChatHandler.sendServerMessage(sender, "You can no longer build!");
			if(player != sender) {
				ChatHandler.sendServerMessage(sender, player.getName() + " can no longer build!");
			}
		} else {
			buildplayers.add(player);
			ChatHandler.sendServerMessage(sender, "You can build now!");
			if(player != sender) {
				ChatHandler.sendServerMessage(sender, player.getName() + " can build now!");
			}
		}
	}
	
	public static boolean isPlayerInBuildmode(Player player) {
		if(buildplayers.contains(player)) {
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(Config.hasWorldProtection(event.getPlayer().getWorld().getName())) {
			if(!PlayerBuildMode.isPlayerInBuildmode(event.getPlayer())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(Config.hasWorldProtection(event.getPlayer().getWorld().getName())) {
			if(!PlayerBuildMode.isPlayerInBuildmode(event.getPlayer())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			if(Config.hasWorldProtection(event.getEntity().getWorld().getName())) {
				if(!PlayerBuildMode.isPlayerInBuildmode(((Player) event.getEntity()))) {
					event.setCancelled(true);
				}
			}
		} else {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
    public void onThrow(ProjectileLaunchEvent event) {
		if(event.getEntity() instanceof Player) {
			if(Config.hasWorldProtection(event.getEntity().getWorld().getName())) {
				if(!PlayerBuildMode.isPlayerInBuildmode(((Player) event.getEntity()))) {
					event.setCancelled(true);
				}
			}
		} else {
			event.setCancelled(true);
		}
    }

}
