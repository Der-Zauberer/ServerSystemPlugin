package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.handler.WorldGroupHandler;
import serversystem.handler.WorldGroupHandler.WorldSetting;
import serversystem.utilities.PlayerVanish;
import serversystem.utilities.ChatMessage;

public class WorldCommand implements CommandExecutor, TabCompleter{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1 && args[0].equals("list")) {
			String worlds = "";
			for(World world : Bukkit.getWorlds()) {
				worlds += world.getName() + " ";
			}
			ChatMessage.sendServerMessage(sender, "Worlds: " + worlds);
		} else if(args.length == 2 && args[0].equals("teleport")) {
			if(Bukkit.getWorld(args[1]) != null) {
				WorldGroupHandler.teleportPlayer((Player) sender, Bukkit.getWorld(args[1]));
				ChatMessage.sendServerMessage(sender, "You are in world " + args[1] +  "!");
			} else {
				ChatMessage.sendServerErrorMessage(sender, "The world " + args[1] +  " does not exist!");
			}	
		} else if(args.length == 3 && args[0].equals("teleport")) {
			Player player = Bukkit.getPlayer(args[2]);
			World world = Bukkit.getWorld(args[1]);
			if(world != null && player != null) {
				WorldGroupHandler.teleportPlayer(player, world);
				if(sender != player) {
					ChatMessage.sendServerMessage(sender, "The player " + player.getName() +  " is in world " + world.getName() +  "!");
				} 
				ChatMessage.sendServerMessage(player, "You are in world " + world +  "!");
			} else if(world == null) {
				ChatMessage.sendServerErrorMessage(sender, "The world " + args[1] +  " does not exist!");
			} else if(player == null) {
				ChatMessage.sendServerErrorMessage(sender, "The player " + args[2] + " is not online!");
			}
		} else if (args.length == 2 && args[0].equals("create")) {
			if(Bukkit.getWorld(args[1]) == null) {
				WorldGroupHandler.createWorld(args[1]);
				ChatMessage.sendServerMessage(sender, "The world " + args[1] + " is successfully created!");
			} else {
				ChatMessage.sendServerMessage(sender, "The world " + args[1] + " is already loaded!");
			}
		} else if ((args.length == 3 || args.length == 4) && args[0].equals("edit")) {
			World world = Bukkit.getWorld(args[1]);
			if(args.length == 3) {
				if (world != null) {
					if(getWorldSettingFromString(args[2]) != null) {
						if(args[2].equals("gamemode")) {
							WorldGroupHandler.setWorldGamemode(world, getGamemodeFromString(args[3]));
						} else {
							WorldGroupHandler.setWorldSettings(world, getWorldSettingFromString(args[2]), getBooleanFromString(args[3]));
						}
						ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!");
					} else {
						ChatMessage.sendServerMessage(sender, "The option " + args[2] + " does not exist or" + args[3] + " is not allowed!");
					}
				} else {
					ChatMessage.sendServerErrorMessage(sender, "The world " + args[1] +  " does not exist!");
				}
			} else {
				if (world != null) {
					if(!args[2].equals("gamemode")) {
						if(getWorldSettingFromString(args[2]) != null) {
							if(args[2].equals("gamemode")) {
								WorldGroupHandler.getWorldGamemode(world, getGamemodeFromString(args[3]));
							} else {
								WorldGroupHandler.getWorldSettings(world, getWorldSettingFromString(args[2]));
							}
							ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!");
						} else {
							ChatMessage.sendServerMessage(sender, "The option " + args[2] + " does not exist or" + args[3] + " is not allowed!");
						}
					}			
				} else {
					ChatMessage.sendServerErrorMessage(sender, "The world " + args[1] +  " does not exist!");
				}
			}
		} else {
			ChatMessage.sendServerErrorMessage(sender, "Not enought arguments!");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		ArrayList<String> commands = new ArrayList<>();
		if(args.length == 1) {
			commands.clear();
			commands.add("teleport");
			commands.add("create");
			commands.add("list");
			commands.add("edit");
		} else if((args.length == 2 && args[0].equals("teleport")) ||  (args.length == 2 && args[0].equals("edit"))) {
			commands.clear();
			for(World world : Bukkit.getWorlds()) {
				commands.add(world.getName());
			}
		} else if(args.length == 3 && args[0].equals("teleport")) {
			commands.clear();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!PlayerVanish.isPlayerVanished(player)) {
					commands.add(player.getName());
				}
			}
		} else if(args.length == 3 && args[0].equals("edit")) {
			commands.clear();
			commands.add("protection");
			commands.add("pvp");
			commands.add("damage");
			commands.add("hunger");
			commands.add("explosion");
			commands.add("gamemode");
		} else if((args.length == 4 && args[0].equals("edit")) && !args[2].equals("gamemode")) {
			commands.clear();
			commands.add("true");
			commands.add("false");
		} else if((args.length == 4 && args[0].equals("edit")) && args[2].equals("gamemode")) {
			commands.clear();
			commands.add("survival");
			commands.add("creative");
			commands.add("adventure");
			commands.add("spectator");
		}
		return commands;
	}
	
	private static WorldSetting getWorldSettingFromString(String string) {
		switch (string) {
		case "protection": return WorldSetting.PROTECTION;
		case "pvp": return WorldSetting.PROTECTION;
		case "damage": return WorldSetting.PROTECTION;
		case "hunger": return WorldSetting.PROTECTION;
		case "explosion": return WorldSetting.PROTECTION;
		case "gamemode": return WorldSetting.PROTECTION;
		default:
			return null;
		}
	}
	
	private static GameMode getGamemodeFromString(String string) {
		switch (string) {
		case "survival": return GameMode.SURVIVAL;
		case "creative": return GameMode.CREATIVE;
		case "adventure": return GameMode.ADVENTURE;
		case "spectator": return GameMode.SPECTATOR;
		default:
			return null;
		}
	}
	
	private static boolean getBooleanFromString(String string) {
		if(string.equals("true")) {
			return true;
		}
		return false;
	}
	
}
