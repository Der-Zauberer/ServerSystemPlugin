package serversystem.handler;

import java.util.ArrayList;
import java.util.Comparator;

import org.bukkit.entity.Player;
import serversystem.config.SaveConfig;
import serversystem.utilities.ServerWarp;

public class WarpHandler {
	
	private static ArrayList<ServerWarp> warps = new ArrayList<>();
	
	public static void initializeWarps() {
		warps = SaveConfig.getWarps();
	}
	
	public static void setWarp(ServerWarp warp) {
		if(getWarp(warp.getName()) != null) {
			warps.set(warps.indexOf(getWarp(warp.getName())), warp);
		} else {
			warps.add(warp);
		}
		for(ServerWarp warps : getWarps()) {
			SaveConfig.setWarp(warps);
		}
	}
	
	public static void removeWarp(ServerWarp warp) {
		warps.remove(warp);
		SaveConfig.removeWarp(warp);
	}
	
	public static ServerWarp getWarp(String name) {
		for(ServerWarp warp : warps) {
			if(warp.getName().equals(name)) {
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
		ArrayList<ServerWarp> result = new ArrayList<>();
		for(ServerWarp warp : warps) {
			if(warp.isGlobal() || player.getWorld() == warp.getLocation().getWorld()) {
				if(warp.getPermission() == null || player.hasPermission(warp.getPermission())) {
					result.add(warp);
				}
			}
		}
		return result;
	}

}
