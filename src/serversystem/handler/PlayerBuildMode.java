package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import serversystem.config.Config;

public class PlayerBuildMode implements Listener {
	
	private static ArrayList<Player> buildplayers = new ArrayList<>();
	
	public static void buildmodePlayer(Player player) {
		buildmodePlayer(player, player);
	}
	
	public static void buildmodePlayer(Player player, CommandSender sender) {
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
	public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
		if(event.getRemover() instanceof Player) {
			if(Config.hasWorldProtection(event.getEntity().getWorld().getName())) {
				if(!PlayerBuildMode.isPlayerInBuildmode(((Player) event.getRemover()))) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
		if(event.getEntity().getShooter() instanceof Player) {
			if(Config.hasWorldProtection(event.getEntity().getWorld().getName())) {
				if(!PlayerBuildMode.isPlayerInBuildmode(((Player) event.getEntity().getShooter()))) {
					event.setCancelled(true);
				}
			}
		}
    }
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			if(Config.hasWorldProtection(event.getEntity().getWorld().getName())) {
				if(!PlayerBuildMode.isPlayerInBuildmode(((Player) event.getDamager()))) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
		if(Config.hasWorldProtection(event.getPlayer().getWorld().getName())) {
			if(!PlayerBuildMode.isPlayerInBuildmode(event.getPlayer())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(Config.hasWorldProtection(event.getPlayer().getWorld().getName())) {
			if(!PlayerBuildMode.isPlayerInBuildmode(event.getPlayer())) {
				Material mainmaterial = event.getPlayer().getInventory().getItemInMainHand().getType();
				Material secondarymaterial = event.getPlayer().getInventory().getItemInOffHand().getType();
				ArrayList<Material> vorbidden = new ArrayList<>();
				vorbidden.add(Material.ARMOR_STAND);
				vorbidden.add(Material.PAINTING);
				vorbidden.add(Material.ITEM_FRAME);
				vorbidden.add(Material.GLOW_ITEM_FRAME);
				for(Material material : vorbidden) {
					if(mainmaterial == material || secondarymaterial == material) {
						event.setCancelled(true);
						return;
					}
				}
				if(mainmaterial.toString().contains("MINECARD") || secondarymaterial.toString().contains("MINECARD")) {
					event.setCancelled(true);
				} else if(mainmaterial.toString().contains("BOAT") || secondarymaterial.toString().contains("BOAT")) {
					event.setCancelled(true);
				} else if(mainmaterial.toString().contains("ITEM_FRAME") || secondarymaterial.toString().contains("ITEM_FRAME")) {
					event.setCancelled(true);
				} else if(mainmaterial.toString().contains("SPAWN_EGG") || secondarymaterial.toString().contains("SPAWN_EGG")) {
					event.setCancelled(true);
				} else if(mainmaterial.toString().contains("BUCKET") || secondarymaterial.toString().contains("BUCKET")) {
					event.setCancelled(true);
				}
			}
		}
	}

}
