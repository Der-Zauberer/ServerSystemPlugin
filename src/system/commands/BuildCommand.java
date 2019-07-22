package system.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import system.main.Config;

public class BuildCommand implements Listener, CommandExecutor {
	
	private static ArrayList<String> buildPlayers = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(buildPlayers.contains(player.getUniqueId().toString())) {
				buildPlayers.remove(player.getUniqueId().toString());
				player.sendMessage(ChatColor.YELLOW + "[Server] !Du kannst jetzt nicht mehr bauen!");
			} else {
				buildPlayers.add(player.getUniqueId().toString());
				player.sendMessage(ChatColor.YELLOW + "[Server] Du kannst jetzt bauen");
			}
		}
		return true;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(Config.isWorldProtected(event.getPlayer().getWorld().getName())) {
			if(buildPlayers.contains(event.getPlayer().getUniqueId().toString())) {
				return;
			}
			event.setCancelled(true);
		}
	}
}
