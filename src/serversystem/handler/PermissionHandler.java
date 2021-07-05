package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import serversystem.config.Config;
import serversystem.main.ServerSystem;

public class PermissionHandler implements Listener {
	
	public static void addConfigPermissions(Player player) {
		if(Config.getPlayerPermissions(player) != null) {
			for(String permission : Config.getPlayerPermissions(player)) {
				PermissionHandler.addPermission(player, permission);
			}
		}
	}
	
	public static void removeConfigPermissions(Player player) {
		if(Config.getPlayerPermissions(player) != null) {
			for(String permission : Config.getPlayerPermissions(player)) {
				PermissionHandler.removePermission(player, permission);
			}
		}
	}
	
	public static void removeConfigDisablePermissions(Player player) {
		if(Config.getDisabledPermissions() != null) {
			for(String permission : Config.getDisabledPermissions()) {
				PermissionHandler.removePermission(player, permission);
			}
		}
	}
	
	public static void addPermission(Player player, String permission) {
		PermissionAttachment attachment = player.addAttachment(ServerSystem.getInstance());
		attachment.setPermission(permission, true);
	}
	
	public static void removePermission(Player player, String permission) {
		PermissionAttachment attachment = player.addAttachment(ServerSystem.getInstance());
		attachment.setPermission(permission, false);
	}
	
	public static void removeAll(Player player) {
		PermissionAttachment attachment = player.addAttachment(ServerSystem.getInstance());
		for(PermissionAttachmentInfo info : player.getEffectivePermissions()) {
			attachment.setPermission(info.getPermission(), false);
		}
	}
	
	public static ArrayList<String> getPlayerPermissions(Player player) {
		ArrayList<String> list = new ArrayList<>();
		for(PermissionAttachmentInfo info : player.getEffectivePermissions()) {
			list.add(info.getPermission());
		}
		return list;
	}
	public static void reloadPlayerPermissions(Player player) {
		if(player.isOp()) {
			player.setOp(false);
			player.setOp(true);
		} else {
			player.setOp(true);
			player.setOp(false);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.getBlock().getType() == Material.COMMAND_BLOCK || event.getBlock().getType() == Material.COMMAND_BLOCK_MINECART) {
			System.out.println(event.getPlayer().hasPermission("serversystem.tools.commandblock"));
			if(!event.getPlayer().hasPermission("serversystem.tools.commandblock")) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.getBlock().getType() == Material.COMMAND_BLOCK || event.getBlock().getType() == Material.COMMAND_BLOCK_MINECART) {
			if(!event.getPlayer().hasPermission("serversystem.tools.commandblock")) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock().getType() == Material.COMMAND_BLOCK || event.getClickedBlock().getType() == Material.COMMAND_BLOCK_MINECART) {
				if(!event.getPlayer().hasPermission("serversystem.tools.commandblock")) {
					event.getPlayer().closeInventory();
				}
			}
		}
	}

}
