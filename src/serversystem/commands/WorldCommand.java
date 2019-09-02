package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.main.Config;
import serversystem.utilities.PlayerVanish;
import serversystem.utilities.ServerMessage;
import serversystem.utilities.WorldGroup;
import serversystem.utilities.WorldGroupHandler;

public class WorldCommand implements CommandExecutor, TabCompleter{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if(args.length == 1 && args[0].equals("list")) {
			String worlds = "";
			for(World world : Bukkit.getWorlds()) {
				worlds = worlds + world.getName() + " ";
			}
			ServerMessage.sendMessage(sender, "Worlds: " + worlds);
		} else if(args.length == 2 && args[0].equals("teleport")) {
			if(Bukkit.getWorld(args[1]) != null) {
				WorldGroupHandler.teleportPlayer(player, Bukkit.getWorld(args[1]));
				ServerMessage.sendMessage(sender, "You are in world " + args[1] +  "!");
			} else {
				ServerMessage.sendErrorMessage(sender, "The world " + args[1] +  " does not exist!");
			}	
		} else if(args.length == 3 && args[0].equals("teleport")) {
			if(Bukkit.getWorld(args[1]) != null && Bukkit.getPlayer(args[2]) != null) {
				WorldGroupHandler.teleportPlayer(Bukkit.getPlayer(args[2]), Bukkit.getWorld(args[1]));
				if(sender != Bukkit.getServer().getPlayer(args[2])) {ServerMessage.sendMessage(sender, "The player " + args[2] +  " is in world " + args[1] +  "!");} 
				ServerMessage.sendMessage(Bukkit.getPlayer(args[2]), "You are in world " + args[1] +  "!");
			} else if(Bukkit.getWorld(args[1]) == null) {
				ServerMessage.sendErrorMessage(sender, "The world " + args[1] +  " does not exist!");
			} else if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))) {
				ServerMessage.sendErrorMessage(sender, "The player " + args[2] + " is not online!");
			}
		} else if (args.length == 2 && args[0].equals("create")) {
			if (Bukkit.getWorld(args[1]) == null) {
				Bukkit.getWorlds().add(new WorldCreator(args[1]).createWorld());
				Config.addWorld(args[1]);
				Config.addLoadWorld(args[1]);
				WorldGroupHandler.addWorldGroup(new WorldGroup(args[1], Bukkit.getWorld(args[1])));
				ServerMessage.sendMessage(sender, "The world " + args[1] + " is successfully created!");
			} else {
				ServerMessage.sendMessage(sender, "The world " + args[1] + " is already loaded!");
			}
		} else if (args.length == 4 && args[0].equals("edit")) {
			if (Bukkit.getWorld(args[1]) != null) {
				boolean worldboolean = false;
				if(args[3].equals("true")) {
					worldboolean = true;
				}
				switch (args[2]) {
				case "protect": Config.setWorldProtected(args[1], worldboolean); ServerMessage.sendMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!"); break;
				case "pvp": Config.setWorldPVP(args[1], worldboolean); ServerMessage.sendMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!"); break;
				case "damage": Config.setWorldDamage(args[1], worldboolean); ServerMessage.sendMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!"); break;
				case "hunger": Config.setWorldHunger(args[1], worldboolean); ServerMessage.sendMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!"); break;
				case "explosion": Config.setWorldExplosion(args[1], worldboolean); ServerMessage.sendMessage(sender, "The option " + args[2] + " is set to " + args[3] + " for the world " + args[1] + "!"); break;
				default:
					ServerMessage.sendMessage(sender, "The option " + args[2] + " does not exist!"); break;
				}
			} else {
				ServerMessage.sendErrorMessage(sender, "The world " + args[1] +  " does not exist!");
			}
			
		} else {
			ServerMessage.sendErrorMessage(sender, "Not enought arguments!");
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
			commands.add("protect");
			commands.add("pvp");
			commands.add("damage");
			commands.add("hunger");
			commands.add("explosion");
		} else if(args.length == 4 && args[0].equals("edit")) {
			commands.clear();
			commands.add("true");
			commands.add("false");
		}
		return commands;
	}
	
}
