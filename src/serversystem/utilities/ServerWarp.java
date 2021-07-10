package serversystem.utilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import serversystem.handler.ChatHandler;
import serversystem.handler.ChatHandler.ErrorMessage;

public class ServerWarp {
	
	private String name;
	private Material material;
	private Location location;
	private boolean global;
	private String permission;
	
	public ServerWarp(String name, Material material, Location location, boolean global) {
		this.name = name;
		this.material = material;
		this.location = location;
		this.global = global;
	}
	
	public String getName() {
		return name;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public boolean isGlobal() {
		return global;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public void warpPlayer(Player player) {
		if(global || player.getWorld() == location.getWorld()) {
			if(permission != null) {
				if(!player.hasPermission(permission)) {
					ChatHandler.sendServerErrorMessage(player, ErrorMessage.NOPERMISSION);
				}
			}
			player.teleport(location);
		} else {
			ChatHandler.sendServerErrorMessage(player, ErrorMessage.NOPERMISSION);
		}
	}
	
}
