package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.main.ServerSystem;

public class ServerWarp extends ServerComponent {

	private Location location;
	private Material material;
	private boolean global;
	private String permission;

	public ServerWarp(String name, Location location) {
		super(name);
		this.location = location;
		material = Material.ENDER_PEARL;
		global = true;
	}
	
	@Override
	public void update() {
		Config.saveWarp(this);
	}
	
	@Override
	public void remove() {
		Config.removeWarp(this);
	}

	@Override
	public String getName() {
		return super.getName();
	}
	
	public Location getLocation() {
		return location;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setGlobal(boolean global) {
		this.global = global;
	}
	
	public boolean isGlobal() {
		return global;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public static ArrayList<ServerWarp> getWarps(Player player) {
		final ArrayList<ServerWarp> result = new ArrayList<>();
		for (ServerWarp warp : ServerSystem.getWarps()) {
			if ((warp.isGlobal() || player.getWorld() == warp.getLocation().getWorld()) && (warp.getPermission() == null || player.hasPermission(warp.getPermission()))) result.add(warp);
		}
		return result;
	}

}