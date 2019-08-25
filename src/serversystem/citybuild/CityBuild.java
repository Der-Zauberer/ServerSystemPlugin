package serversystem.citybuild;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Listener;

import serversystem.main.SaveConfig;
import serversystem.utilities.ServerArea;

public class CityBuild implements Listener {
	
	private static ArrayList<World> worlds =  new ArrayList<>();
	private static ArrayList<CityBuildPlot> plots = new ArrayList<>();
	
	public CityBuild() {
		if(SaveConfig.getCitybuildWorlds() != null) {worlds = SaveConfig.getCitybuildWorlds();}
		if(SaveConfig.getCitybuildPlots() != null) {plots = SaveConfig.getCitybuildPlots();}
	}
	
	public static boolean hasWorld(World world) {
		return worlds.contains(world);
	}
	
	public static void addWorld(World world) {
		worlds.add(world);
	}
	
	public static void removeWorld(World world) {
		worlds.remove(world);
	}
	
	public static boolean hasPlot(CityBuildPlot plot) {
		return plots.contains(plot);
	}
	
	public static void addPlot(CityBuildPlot plot) {
		plots.add(plot);
	}
	
	public static void removeArea(ServerArea area) {
		plots.remove(area);
	}
	
	public static CityBuildPlot getAreaFromLocation(Location location) {
		for (CityBuildPlot plot : plots) {
			if (plot.isLocationInArea(location)) {
				return plot;
			}
		}
		return null;
	}
	
	public static ArrayList<World> getWorlds() {
		return worlds;
	}
	
	public static ArrayList<CityBuildPlot> getPlots() {
		return plots;
	}

}
