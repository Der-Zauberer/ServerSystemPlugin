package serversystem.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import serversystem.handler.WorldGroupHandler;

public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if(sign.getLine(0) != null && sign.getLine(3) != null) {
				if(sign.getLine(1).equals("[World]") && Bukkit.getWorld(ChatColor.stripColor(sign.getLine(2))) != null) {
					WorldGroupHandler.teleportPlayer(event.getPlayer(), Bukkit.getWorld(ChatColor.stripColor(sign.getLine(2))));
				}
				if(sign.getLine(1).equals("[Command]") && Bukkit.getServer().getPluginCommand(ChatColor.stripColor(sign.getLine(2))) != null) {
					Bukkit.getServer().dispatchCommand(event.getPlayer(), ChatColor.stripColor(sign.getLine(2)));
				}
			}
		}
	}

}
