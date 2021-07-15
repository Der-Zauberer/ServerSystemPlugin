package serversystem.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import serversystem.config.Config;
import serversystem.config.Config.WorldOption;
import serversystem.handler.ChatHandler;
import serversystem.handler.ChatHandler.ErrorMessage;
import serversystem.handler.PlayerVanishHandler;
import serversystem.handler.WarpHandler;

public class CommandAssistant {
	
	private CommandSender sender;
	
	public CommandAssistant(CommandSender sender) {
		this.sender = sender;
	}
	
	public boolean isSenderInstanceOfPlayer() {
		if(sender instanceof Player) {
			return true;
		}
		ChatHandler.sendServerErrorMessage(sender, ErrorMessage.ONLYPLAYER);
		return false;	
	}
	
	public boolean isSenderInstanceOfPlayer(boolean notenougharguments) {
		if(sender instanceof Player) {
			return true;
		}
		if(notenougharguments) {
			ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOTENOUGHARGUMENTS);
		} else {
			ChatHandler.sendServerErrorMessage(sender, ErrorMessage.ONLYPLAYER);
		}
		return false;
	}
	
	public boolean isSenderNotInstanceOfPlayer() {
		if(!(sender instanceof Player)) {
			return true;
		} 
		ChatHandler.sendServerErrorMessage(sender, ErrorMessage.ONLYCONSOLE);
		return false;
	}
	
	public boolean isWorld(String world) {
		if(Bukkit.getWorld(world) != null) {
			return true;
		}
		ChatHandler.sendServerErrorMessage(sender, "The world " + world + " does not exist!");
		return false;
	}
	
	public boolean isPlayer(String player) {
		if(Bukkit.getPlayer(player) != null) {
			return true;
		}
		ChatHandler.sendServerErrorMessage(sender, "The player " + player + " is not online!");
		return false;
	}
	
	public boolean isWarp(String warp) {
		if(WarpHandler.getWarp(warp) != null) {
			return true;
		}
		ChatHandler.sendServerErrorMessage(sender, "The warp " + warp + " does not exist!");
		return false;
	}
	
	public boolean hasPlayerPermission(Player player, String permission) {
		if(player.hasPermission(permission)) {
			return true;
		} else {
			ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOPERMISSION);
			return false;
		}
	}
	
	public boolean hasSenderPermission(String permission) {
		if(sender.hasPermission(permission)) {
			return true;
		} else {
			ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOPERMISSION);
			return false;
		}
	}
	
	public boolean hasPermission(String permission) {
		if(sender.hasPermission(permission)) {
			return true;
		} 
		ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOPERMISSION);
		return false;
	}
	
	public boolean hasPermissionOrIsConsole(String permission) {
		if(!(sender instanceof Player) || sender.hasPermission(permission)) {
			return true;
		} 
		ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOPERMISSION);
		return false;
	}
	
	public boolean isGameMode(String gamemode) {
		for(GameMode gm : GameMode.values()) {
			if(gm.toString().equalsIgnoreCase(gamemode)) {
				return true;
			}
		}
		ChatHandler.sendServerErrorMessage(sender, gamemode + " is not a valid gamemode!");
		return false;
	}
	
	public boolean isMaterial(String material) {
		if(material.startsWith("minecraft:")) {
			material = material.substring(10);
		}
		for(Material mt : Material.values()) {
			if(mt.toString().equalsIgnoreCase(material)) {
				return true;
			}
		}
		ChatHandler.sendServerErrorMessage(sender, material + " is not a valid material!");
		return false;
	}
	
	public boolean isWorldOption(String option) {
		for(WorldOption wo : WorldOption.values()) {
			if(wo.toString().equalsIgnoreCase(option)) {
				return true;
			}
		}
		ChatHandler.sendServerErrorMessage(sender, option + " is not a valid option!");
		return false;
	}
	
	public boolean isBoolean(String bool) {
		if(bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("false")) {
			return true;
		}
		ChatHandler.sendServerErrorMessage(sender, bool + " is not a valid boolean!");
		return false;
	}
	
	public boolean hasMinArguments(int min, String args[]) {
		if(args.length >= min) {
			return true;
		}
		ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOTENOUGHARGUMENTS);
		return false;
	}
	
	public boolean hasMaxArguments(int min, String args[]) {
		if(args.length <= min) {
			return true;
		}
		return false;
	}
	
	public boolean isPath(int position, String path, int min, String args[]) {
		if(args.length >= min && args[position].equals(path)) {
			return true;
		}
		return false;
	}
	
	public boolean isPath(int position, String path, int min, int max, String args[]) {
		if(args.length >= min && args.length <= max && args[position].equals(path)) {
			return true;
		}
		return false;
	}
	
	public List<String> getPlayer() {
		List<String> list = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!PlayerVanishHandler.isPlayerVanished(player)) {
				list.add(player.getName());
			}
		}
		return list;
	}
	
	public List<String> getWorlds() {
		List<String> list = new ArrayList<>();
		for (World world : Bukkit.getWorlds()) {
			list.add(world.getName());
		}
		return list;
	}
	
	public List<String> getWorlds(CommandSender sender) {
		List<String> list = new ArrayList<>();
		for(World world : Bukkit.getWorlds()) {
			if(Config.getWorldPermission(world.getName()) == null || sender.hasPermission(Config.getWorldPermission(world.getName()))) {
				list.add(world.getName());
			}
		}
		return list;
	}
	
	public List<String> getWarps() {
		List<String> list = new ArrayList<>();
		for(ServerWarp warp : WarpHandler.getWarps()) {
			list.add(warp.getName());
		}
		return list;
	}
	
	public List<String> getWarps(Player player) {
		List<String> list = new ArrayList<>();
		for(ServerWarp warp : WarpHandler.getWarps()) {
			if(warp.getPermission() == null || sender.hasPermission(warp.getPermission())) {
				if(warp.isGlobal() || warp.getLocation().getWorld() == player.getWorld()) {
					list.add(warp.getName());
				}
			}
		}
		return list;
	}

}
