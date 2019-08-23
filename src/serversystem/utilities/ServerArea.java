package serversystem.utilities;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ServerArea {
	
	private int id;
	private Location location[];
	private static int numberOfAreas = 0;
	
	public ServerArea(Location location1, Location location2) {
		id = numberOfAreas;
		location = new Location[2];
		location[0] = location1;
		location[1] = location2;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isLocationInArea(Location location) {
		int minx = Math.min(this.location[0].getBlockX(), this.location[1].getBlockX()),
		miny = Math.min(this.location[0].getBlockY(), this.location[1].getBlockY()),
		minz = Math.min(this.location[0].getBlockZ(), this.location[1].getBlockZ()),
		maxx = Math.max(this.location[0].getBlockX(), this.location[1].getBlockX()),
		maxy = Math.max(this.location[0].getBlockY(), this.location[1].getBlockY()),
		maxz = Math.max(this.location[0].getBlockZ(), this.location[1].getBlockZ());
		if(location==null) {return false;}
		if(!location.getWorld().equals(this.location[0].getWorld())||!this.location[0].getWorld().equals(this.location[0].getWorld())) {
			return false;
		}
		if(minx <= location.getBlockX() && maxx >= location.getBlockX()) {
			if(minz <= location.getBlockZ() && maxz >= location.getBlockZ()) {
				if(miny <= location.getBlockY() && maxy >= location.getBlockY()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Location getPosition1() {
		return location[0];
	}
	
	public Location getPosition2() {
		return location[2];
	}
	
	public boolean isPlayerInArea(Player player) {
		return isLocationInArea(player.getLocation());
	}

}
