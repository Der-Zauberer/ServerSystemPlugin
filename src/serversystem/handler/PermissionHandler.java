package serversystem.handler;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import serversystem.config.Config;
import serversystem.main.ServerSystem;

public class PermissionHandler implements Listener {
	
	private static HashMap<Player, PermissionAttachment> addedattachments = new HashMap<>();
	private static HashMap<Player, ArrayList<String>> permissions = new HashMap<>();
	private static HashMap<Player, PermissionAttachment> removedattachments = new HashMap<>();
	
	public static void loadPlayerPermissions(Player player) {
		resetPlayerPermissions(player);
		if(Config.getPlayerPermissions(player) != null) {
			removeConfigDisablePermissions(player);
			for(String string : Config.getPlayerPermissions(player)) {
				if(string.endsWith("*")) {
					string = string.substring(0, string.length() - 2);
					boolean add = true;
					if(string.startsWith("-")) {
						add = false;
						string = string.substring(1);
					}
					for (Permission permission : Bukkit.getServer().getPluginManager().getPermissions()) {
						if(permission.getName().startsWith(string)) {
							if(add) {
								PermissionHandler.addPermission(player, permission.getName());
							} else if(player.hasPermission(permission.getName())) {
								PermissionHandler.removePermission(player, permission.getName());
							}
						}
					}
				} 
				else {
					if(string.startsWith("-")) {
						string = string.substring(1);
						if(player.hasPermission(string)) {
							PermissionHandler.removePermission(player, string);
						}
					}
					PermissionHandler.addPermission(player, string);
				}
			}
		} else {
			removeConfigDisablePermissions(player);
		}
		reloadPlayerPermissions(player);
	}
	
	public static void resetPlayerPermissions(Player player) {
		removeAllPermissions(player);
		reloadPlayerPermissions(player);
	}
	
	private static void removeConfigDisablePermissions(Player player) {
		if(Config.getDisabledPermissions() != null) {
			for(String string : Config.getDisabledPermissions()) {
				PermissionHandler.removePermission(player, string);
			}
		}
	}
	
	private static void addPermission(Player player, String permission) {
		if(addedattachments.get(player) != null) {
			addedattachments.get(player).setPermission(permission, true);
			permissions.get(player).add(permission);
		}
	}
	
	private static void removePermission(Player player, String permission) {
		if(removedattachments.get(player) != null) {
			removedattachments.get(player).setPermission(permission, false);
		}
	}
	
	private static void removeAllPermissions(Player player) {
		if(addedattachments.get(player) != null) {
			player.removeAttachment(addedattachments.get(player));
			addedattachments.get(player).remove();
		}
		if(permissions.get(player) != null) {
			for(String permission : permissions.get(player)) {
				if(removedattachments.get(player) != null) {
					removedattachments.get(player).setPermission(permission, false);
				}
			}
		}
		permissions.put(player, new ArrayList<>());
		PermissionAttachment attachment = player.addAttachment(ServerSystem.getInstance());
		addedattachments.put(player, attachment);
		PermissionAttachment test = player.addAttachment(ServerSystem.getInstance());
		removedattachments.put(player, test);
	}
	
	private static void reloadPlayerPermissions(Player player) {
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
