package serversystem.utilities;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import serversystem.commands.VanishCommand;
import serversystem.config.Config;
import serversystem.config.Config.WorldOption;

public class CommandAssistant {

	private CommandSender sender;

	public CommandAssistant(CommandSender sender) {
		this.sender = sender;
	}

	public boolean isSenderInstanceOfPlayer() {
		if (sender instanceof Player) return true;
		ChatUtil.sendErrorMessage(sender, ChatUtil.ONLY_PLAYER);
		return false;
	}

	public boolean isSenderInstanceOfPlayer(boolean notenougharguments) {
		if (sender instanceof Player) return true;
		if (notenougharguments) ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		else ChatUtil.sendErrorMessage(sender, ChatUtil.ONLY_PLAYER);
		return false;
	}

	public boolean isSenderNotInstanceOfPlayer() {
		if (!(sender instanceof Player)) return true;
		ChatUtil.sendErrorMessage(sender, ChatUtil.ONLY_CONSOLE);
		return false;
	}

	public boolean isWorld(String world) {
		if (Bukkit.getWorld(world) != null) return true;
		ChatUtil.sendErrorMessage(sender, "The world " + world + " does not exist!");
		return false;
	}

	public boolean isPlayer(String player) {
		if (Bukkit.getPlayer(player) != null) return true;
		ChatUtil.sendErrorMessage(sender, "The player " + player + " is not online!");
		return false;
	}

	public boolean isWarp(String warp) {
		if (ServerWarp.getWarp(warp) != null) return true;
		ChatUtil.sendErrorMessage(sender, "The warp " + warp + " does not exist!");
		return false;
	}

	public boolean hasPlayerPermission(Player player, String permission) {
		if (player.hasPermission(permission)) return true;
		ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
		return false;
	}

	public boolean hasSenderPermission(String permission) {
		if (sender.hasPermission(permission)) return true;
		ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
		return false;
	}

	public boolean hasPermission(String permission) {
		if (sender.hasPermission(permission)) return true;
		ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
		return false;
	}

	public boolean hasPermissionOrIsConsole(String permission) {
		if (!(sender instanceof Player) || sender.hasPermission(permission)) return true;
		ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
		return false;
	}

	public boolean isGameMode(String gamemode) {
		for (GameMode gm : GameMode.values()) {
			if (gm.toString().equalsIgnoreCase(gamemode)) return true;
		}
		ChatUtil.sendErrorMessage(sender, gamemode + " is not a valid gamemode!");
		return false;
	}

	public boolean isMaterial(String material) {
		if (material.startsWith("minecraft:")) material = material.substring(10);
		for (Material mt : Material.values()) {
			if (mt.toString().equalsIgnoreCase(material)) return true;
		}
		ChatUtil.sendErrorMessage(sender, material + " is not a valid material!");
		return false;
	}

	public boolean isWorldOption(String option) {
		for (WorldOption wo : WorldOption.values()) {
			if (wo.toString().equalsIgnoreCase(option)) return true;
		}
		ChatUtil.sendErrorMessage(sender, option + " is not a valid option!");
		return false;
	}

	public boolean isBoolean(String bool) {
		if (bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("false")) return true;
		ChatUtil.sendErrorMessage(sender, bool + " is not a valid boolean!");
		return false;
	}

	public boolean hasMinArguments(int min, String args[]) {
		if (args.length >= min) return true;
		ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		return false;
	}

	public boolean hasMaxArguments(int min, String args[]) {
		if (args.length <= min) return true;
		return false;
	}

	public boolean isPath(int position, String path, int min, String args[]) {
		return args.length >= min && args[position].equals(path);
	}

	public boolean isPath(int position, String path, int min, int max, String args[]) {
		return args.length >= min && args.length <= max && args[position].equals(path);
	}

	public List<String> getPlayers() {
		final List<String> list = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!VanishCommand.isVanished(player)) list.add(player.getName());
		}
		return list;
	}

	public List<String> getWorlds() {
		final List<String> list = new ArrayList<>();
		for (World world : Bukkit.getWorlds()) {
			list.add(world.getName());
		}
		return list;
	}

	public List<String> getWorlds(CommandSender sender) {
		final List<String> list = new ArrayList<>();
		for (World world : Bukkit.getWorlds()) {
			if (Config.getWorldPermission(world) == null || sender.hasPermission(Config.getWorldPermission(world))) {
				list.add(world.getName());
			}
		}
		return list;
	}

	public List<String> getWarps() {
		final List<String> list = new ArrayList<>();
		for (ServerWarp warp : ServerWarp.getWarps()) {
			list.add(warp.getName());
		}
		return list;
	}

	public List<String> getWarps(Player player) {
		final List<String> list = new ArrayList<>();
		for (ServerWarp warp : ServerWarp.getWarps()) {
			if (warp.getPermission() == null || sender.hasPermission(warp.getPermission())) {
				if (warp.isGlobal() || warp.getLocation().getWorld() == player.getWorld()) list.add(warp.getName());
			}
		}
		return list;
	}

	public List<String> cutArguments(String args[], List<String> commands) {
		final String command = args[args.length - 1];
		final List<String> output = new ArrayList<>();
		if (!command.isEmpty() && !command.equals("")) {
			for (String string : commands) {
				if (string.startsWith(command)) output.add(string);
			}
			return output;
		} else {
			return commands;
		}
	}

}
