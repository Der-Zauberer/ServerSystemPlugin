package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import serversystem.config.Config;

public class PlayerBuildHandler implements Listener {
	
	private static ArrayList<Player> buildPlayers = new ArrayList<>();
	
	public static void buildmodePlayer(Player player) {
		buildmodePlayer(player, player);
	}
	
	public static void buildmodePlayer(Player player, CommandSender sender) {
		if (buildPlayers.contains(player)) {
			buildPlayers.remove(player);
			ChatHandler.sendServerMessage(player, "You can no longer build!");
			if (player != sender) ChatHandler.sendServerMessage(sender, player.getName() + " can no longer build!");
		} else {
			buildPlayers.add(player);
			ChatHandler.sendServerMessage(player, "You can build now!");
			if(player != sender) ChatHandler.sendServerMessage(sender, player.getName() + " can build now!");
		}
	}
	
	public static boolean isPlayerInBuildmode(Player player) {
		return buildPlayers.contains(player);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		event.setCancelled(isActionForbidden(event.getPlayer().getWorld(), event.getPlayer()));
		if (!event.isCancelled()) event.setCancelled(isBlockDisabled(event.getBlock(), event.getPlayer()));
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		event.setCancelled(isActionForbidden(event.getPlayer().getWorld(), event.getPlayer()));
		if (!event.isCancelled()) event.setCancelled(isBlockDisabled(event.getBlock(), event.getPlayer()));
	}
	
	@EventHandler
	public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
		if (event.getRemover() instanceof Player) event.setCancelled(isActionForbidden(event.getRemover().getWorld(), (Player) event.getRemover()));
	}
	
	@EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
		if (event.getEntity().getShooter() instanceof Player) event.setCancelled(isActionForbidden(event.getEntity().getWorld(), (Player) event.getEntity().getShooter()));
    }
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) event.setCancelled(isActionForbidden(event.getDamager().getWorld(), (Player) event.getDamager()));
	}
	
	@EventHandler
	public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
		event.setCancelled(isActionForbidden(event.getPlayer().getWorld(), event.getPlayer()));
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (isActionForbidden(event.getPlayer().getWorld(), event.getPlayer())) {
			Material mainMaterial = event.getPlayer().getInventory().getItemInMainHand().getType();
			Material secondaryMaterial = event.getPlayer().getInventory().getItemInOffHand().getType();
			ArrayList<Material> vorbiddenMaterials = new ArrayList<>();
			vorbiddenMaterials.add(Material.ARMOR_STAND);
			vorbiddenMaterials.add(Material.PAINTING);
			vorbiddenMaterials.add(Material.ITEM_FRAME);
			vorbiddenMaterials.add(Material.GLOW_ITEM_FRAME);
			for (Material material : vorbiddenMaterials) {
				if (mainMaterial == material || secondaryMaterial == material) {
					event.setCancelled(true);
					return;
				}
			}
			if (mainMaterial.toString().contains("MINECARD") || secondaryMaterial.toString().contains("MINECARD")) event.setCancelled(true);
			else if (mainMaterial.toString().contains("BOAT") || secondaryMaterial.toString().contains("BOAT")) event.setCancelled(true);
			else if (mainMaterial.toString().contains("ITEM_FRAME") || secondaryMaterial.toString().contains("ITEM_FRAME")) event.setCancelled(true);
			else if (mainMaterial.toString().contains("SPAWN_EGG") || secondaryMaterial.toString().contains("SPAWN_EGG")) event.setCancelled(true);
			else if (mainMaterial.toString().contains("BUCKET") || secondaryMaterial.toString().contains("BUCKET")) event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (isActionForbidden(event.getPlayer().getWorld(), event.getPlayer()) && event.getRightClicked().getType() == EntityType.ITEM_FRAME) event.setCancelled(true);
	}
	
	private boolean isActionForbidden(World world, Player player) {
		return Config.hasWorldProtection(world.getName()) && !buildPlayers.contains(player);
	}
	
	private boolean isBlockDisabled(Block block, Player player) {
		if(!player.hasPermission("serversystem.tools.disabledblocks")) {
			for (String string : Config.getDisabledBlocks()) {
				if (block.getBlockData().getAsString().equals(string)) {
					return true;
				}
			}
		}
		return false;
	}

}
