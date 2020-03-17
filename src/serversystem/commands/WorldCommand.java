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
import serversystem.config.Config;
import serversystem.handler.ChatMessage;
import serversystem.handler.PlayerVanish;
import serversystem.handler.WorldGroupHandler;

public class WorldCommand implements CommandExecutor, TabCompleter{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if(args.length == 1 && args[0].equals("list")) {
			String worlds = "";
			for(World world : Bukkit.getWorlds()) {
				worlds = worlds + world.getName() + " ";
			}
			ChatMessage.sendServerMessage(sender, "Worlds: " + worlds);
		} else if(args.length == 2 && args[0].equals("teleport")) {
			if(Bukkit.getWorld(args[1]) != null) {
				WorldGroupHandler.teleportPlayer(player, Bukkit.getWorld(args[1]));
				ChatMessage.sendServerMessage(sender, "You are in world " + args[1] +  "!");
			} else {
				ChatMessage.sendServerErrorMessage(sender, "The world " + args[1] +  " does not exist!");
			}	
		} else if(args.length == 3 && args[0].equals("teleport")) {
			if(Bukkit.getWorld(args[1]) != null && Bukkit.getPlayer(args[2]) != null) {
				WorldGroupHandler.teleportPlayer(Bukkit.getPlayer(args[2]), Bukkit.getWorld(args[1]));
				if(sender != Bukkit.getServer().getPlayer(args[2])) {ChatMessage.sendServerMessage(sender, "The player " + args[2] +  " is in world " + args[1] +  "!");} 
				ChatMessage.sendServerMessage(Bukkit.getPlayer(args[2]), "You are in world " + args[1] +  "!");
			} else if(Bukkit.getWorld(args[1]) == null) {
				ChatMessage.sendServerErrorMessage(sender, "The world " + args[1] +  " does not exist!");
			} else if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))) {
				ChatMessage.sendServerErrorMessage(sender, "The player " + args[2] + " is not online!");
			}
		} else if (args.length == 2 && args[0].equals("create")) {
			if (Bukkit.getWorld(args[1]) == null) {
				WorldGroupHandler.createWorld(args[1]);
				ChatMessage.sendServerMessage(sender, "The world " + args[1] + " is successfully created!");
			} else {
				ChatMessage.sendServerMessage(sender, "The world " + args[1] + " is already loaded!");
			}
		} else if(args.length == 2 && args[0].equals("remove")) {
			if(Bukkit.getWorld(args[1]) != null) {
				WorldGroupHandler.removeWorld(args[1]);
				ChatMessage.sendServerMessage(sender, "The world " + args[1] + " will be removed after a restart!");
			} else {
				ChatMessage.sendServerErrorMessage(sender, "The world " + args[1] +  " does not exist!");
			}
		} else if ((args.length == 3 || args.length == 4) && args[0].equals("edit")) {
			if(args.length == 3) {
				if (Bukkit.getWorld(args[1]) != null) {
					switch (args[2]) {
					case "protection": ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + Config.hasWorldProtection(args[1]) + " for the world " + args[1] + "!"); break;
					case "pvp": ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + Config.hasWorldPVP(args[1]) + " for the world " + args[1] + "!"); break;
					case "damage": ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + Config.hasWorldDamage(args[1]) + " for the world " + args[1] + "!"); break;
					case "hunger": ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " +Config.hasWorldHunger(args[1]) + " for the world " + args[1] + "!"); break;
					case "explosion": ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + Config.hasWorldExplosion(args[1]) + " for the world " + args[1] + "!"); break;
					case "gamemode": ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + Config.getWorldGamemode(args[1]).toString().toLowerCase() + " for the world " + args[1] + "!"); break;
					default:
						ChatMessage.sendServerMessage(sender, "The option " + args[2] + " does not exist!"); break;
					}
				} else {
					ChatMessage.sendServerErrorMessage(sender, "The world " + args[1] +  " does not exist!");
				}
			} else {
				if (Bukkit.getWorld(args[1]) != null) {
					if(!args[2].equals("gamemode")) {
						boolean worldboolean = false;
						if(args[3].equals("true")) {
							worldboolean = true;
						}
						switch (args[2]) {
						case "protection": Config.setWorldProtection(args[1], worldboolean); ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!"); break;
						case "pvp": Config.setWorldPVP(args[1], worldboolean); ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!"); break;
						case "damage": Config.setWorldDamage(args[1], worldboolean); ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!"); break;
						case "hunger": Config.setWorldHunger(args[1], worldboolean); ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!"); break;
						case "explosion": Config.setWorldExplosion(args[1], worldboolean); ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!"); break;
						default:
							ChatMessage.sendServerMessage(sender, "The option " + args[2] + " does not exist!"); break;
						}
					} else {
						switch (args[3]) {
						case "survival": Config.setWorldGamemode(args[1], GameMode.SURVIVAL); ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to survival for the world " + args[1] + "!"); break;
						case "creative": Config.setWorldGamemode(args[1], GameMode.CREATIVE); ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to creative for the world " + args[1] + "!"); break;
						case "adventure": Config.setWorldGamemode(args[1], GameMode.ADVENTURE); ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to adventure for the world " + args[1] + "!"); break;
						case "spectator": Config.setWorldGamemode(args[1], GameMode.SPECTATOR); ChatMessage.sendServerMessage(sender, "The option " + args[2] + " is set to spectator for the world " + args[1] + "!"); break;
						default:
							ChatMessage.sendServerMessage(sender, "The gamemode " + args[2] + " does not exist!"); break;
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
			commands.add("remove");
		} else if((args.length == 2 && args[0].equals("teleport")) ||  (args.length == 2 && args[0].equals("edit")) ||  (args.length == 2 && args[0].equals("remove"))) {
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
	
}
