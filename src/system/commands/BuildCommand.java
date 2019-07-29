package system.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import system.main.Config;
import system.utilities.PlayerBuildMode;

public class BuildCommand implements Listener, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			PlayerBuildMode.buildmodePlayer((Player) sender);
		}else if(Bukkit.getServer().getPlayer(args[0]) != null) {
			PlayerBuildMode.buildmodePlayer(Bukkit.getServer().getPlayer(args[0]), (Player) sender);
		}
		return true;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(Config.isWorldProtected(event.getPlayer().getWorld().getName())) {
			if(PlayerBuildMode.isPlayerBuildmode(event.getPlayer())) {
				return;
			}
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(Config.isWorldProtected(event.getPlayer().getWorld().getName())) {
			if(PlayerBuildMode.isPlayerBuildmode(event.getPlayer())) {
				return;
			}
			event.setCancelled(true);
		}
	}
}
