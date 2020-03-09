package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

import serversystem.utilities.PlayerBuildMode;
import serversystem.utilities.PlayerVanish;
import serversystem.config.Config;
import serversystem.utilities.ChatMessage;
import serversystem.utilities.ChatMessage.ErrorMessage;

public class BuildCommand implements Listener, CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			PlayerBuildMode.buildmodePlayer((Player) sender);
		}else if(args.length == 1 && Bukkit.getPlayer(args[0]) != null) {
			PlayerBuildMode.buildmodePlayer(Bukkit.getPlayer(args[0]), (Player) sender);
		}else if(args.length == 1 && !Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
			ChatMessage.sendServerErrorMessage(sender, ErrorMessage.PLAYERNOTONLINE);
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = new ArrayList<>();
		if(args.length == 1) {
			commands.clear();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!PlayerVanish.isPlayerVanished(player)) {
					commands.add(player.getName());
				}
			}
		} else {
			commands.clear();
		}
		return commands;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(Config.hasWorldProtect(event.getPlayer().getWorld().getName())) {
			if(!PlayerBuildMode.isPlayerInBuildmode(event.getPlayer())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(Config.hasWorldProtect(event.getPlayer().getWorld().getName())) {
			if(!PlayerBuildMode.isPlayerInBuildmode(event.getPlayer())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			if(Config.hasWorldProtect(event.getEntity().getWorld().getName())) {
				if(!PlayerBuildMode.isPlayerInBuildmode(((Player) event.getEntity()))) {
					event.setCancelled(true);
				}
			}
		} else {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
    public void onThrow(ProjectileLaunchEvent event) {
		if(event.getEntity() instanceof Player) {
			if(Config.hasWorldProtect(event.getEntity().getWorld().getName())) {
				if(!PlayerBuildMode.isPlayerInBuildmode(((Player) event.getEntity()))) {
					event.setCancelled(true);
				}
			}
		} else {
			event.setCancelled(true);
		}
    }
	
}
