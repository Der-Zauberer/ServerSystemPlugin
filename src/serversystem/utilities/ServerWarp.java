package serversystem.utilities;

import org.bukkit.Location;
import org.bukkit.Material;

import serversystem.config.SaveConfig;
import serversystem.handler.WarpHandler;

public class ServerWarp {
	
	private String name;
	private Material material;
	private Location location;
	private boolean global;
	private String permission;
	
	public ServerWarp(String name, Location location) {
		this.name = name;
		this.location = location;
		material = Material.ENDER_PEARL;
		global = true;
		WarpHandler.addWarp(this);
	}
	
	public String getName() {
		return name;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
		SaveConfig.setWarp(this);
	}
	
	public Location getLocation() {
		return location;
	}
	
	public boolean isGlobal() {
		return global;
	}
	
	public void setGlobal(boolean global) {
		this.global = global;
		SaveConfig.setWarp(this);
	}
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
		SaveConfig.setWarp(this);
	}
	
	public void remove() {
		WarpHandler.removeWarp(this);
		try {
			finalize();
		} catch (Throwable exception) {
			exception.printStackTrace();
		}
	}
	
}
