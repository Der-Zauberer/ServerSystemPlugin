package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import serversystem.config.Config;
import serversystem.main.ServerSystem;

public class PlayerPermission {
	
	public static void addConfigPermissions(Player player) {
		for(String permission : Config.getPlayerPermissions(player)) {
			PlayerPermission.addPermission(player, permission);
		}
	}
	
	public static void removeConfigPermissions(Player player) {
		for(String permission : Config.getPlayerPermissions(player)) {
			PlayerPermission.removePermission(player, permission);
		}
	}
	
	public static void removeConfigDisablePermissions(Player player) {
		for(String permission : Config.getDisabledPermission()) {
			PlayerPermission.removePermission(player, permission);
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

}
