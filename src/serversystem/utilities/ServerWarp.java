package serversystem.utilities;

import java.util.ArrayList;
import java.util.Comparator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import serversystem.config.SaveConfig;

public class ServerWarp {

	private String name;
	private Material material;
	private Location location;
	private boolean global;
	private String permission;
	
	private static final ArrayList<ServerWarp> warps = SaveConfig.getWarps();

	public ServerWarp(String name, Location location) {
		this.name = name;
		this.location = location;
		material = Material.ENDER_PEARL;
		global = true;
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
	
	public static void addWarp(ServerWarp warp) {
		warps.add(warp);
		SaveConfig.setWarp(warp);
	}
	
	public static void removeWarp(ServerWarp warp) {
		warps.remove(warp);
		SaveConfig.removeWarp(warp);
	}
	
	public static ServerWarp getWarp(String name) {
		for (ServerWarp warp : warps) {
			if (warp.getName().equals(name)) {
				return warp;
			}
		}
		return null;
	}
	
	public static ArrayList<ServerWarp> getWarps() {
		warps.sort(Comparator.comparing(ServerWarp::getName));
		return warps;
	}
	
	public static ArrayList<ServerWarp> getWarps(Player player) {
		final ArrayList<ServerWarp> result = new ArrayList<>();
		for (ServerWarp warp : warps) {
			if ((warp.isGlobal() || player.getWorld() == warp.getLocation().getWorld()) && (warp.getPermission() == null || player.hasPermission(warp.getPermission()))) result.add(warp);
		}
		return result;
	}

}
