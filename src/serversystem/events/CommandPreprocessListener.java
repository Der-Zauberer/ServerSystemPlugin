package serversystem.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import serversystem.utilities.ChatUtil;

public class CommandPreprocessListener implements Listener {
	
	@EventHandler
	public static void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (((event.getMessage().toLowerCase().startsWith("/say") || event.getMessage().toLowerCase().startsWith("/minecraft:say")) && event.getPlayer().hasPermission("minecraft.command.say"))  || ((event.getMessage().toLowerCase().startsWith("/me") || event.getMessage().toLowerCase().startsWith("/minecraft:me")) && (event.getPlayer().hasPermission("minecraft.command.me")))){
			String[] messagelist = event.getMessage().split(" ");
			if (messagelist.length > 1) {
				event.setCancelled(true);
				ChatUtil.sendChatMessage(event.getPlayer(), getStringMessage(messagelist, 1));
			}
			return;
		}
		if ((event.getMessage().toLowerCase().startsWith("/msg") || event.getMessage().toLowerCase().startsWith("/minecraft:msg") || event.getMessage().toLowerCase().startsWith("/tell") || event.getMessage().toLowerCase().startsWith("/minecraft:tell")) && event.getPlayer().hasPermission("minecraft.command.msg")){
			String[] messagelist = event.getMessage().split(" ");
			if (messagelist.length > 2 && Bukkit.getPlayer(messagelist[1]) != null) {
				event.setCancelled(true);
				ChatUtil.sendPrivateMessage(event.getPlayer(), Bukkit.getPlayer(messagelist[1]), getStringMessage(messagelist, 2));
			}
			return;
		}
		if (event.getMessage().toLowerCase().startsWith("/execute") && event.getPlayer().hasPermission("minecraft.command.execute")) {
			String[] messagelist = event.getMessage().split(" ");
			if (messagelist.length > 4 && messagelist[1].equalsIgnoreCase("as") && messagelist[3].equalsIgnoreCase("run") && (messagelist[4].equalsIgnoreCase("say") || messagelist[4].equalsIgnoreCase("minecraft:say") || messagelist[4].equalsIgnoreCase("me") || messagelist[4].equalsIgnoreCase("minecraft:me")) && Bukkit.getPlayer(messagelist[2]) != null) {
				event.setCancelled(true);
				ChatUtil.sendChatMessage(Bukkit.getPlayer(messagelist[2]), getStringMessage(messagelist, 5));
			}
			if (messagelist.length > 5 && messagelist[1].equalsIgnoreCase("as") && messagelist[3].equalsIgnoreCase("run") && (messagelist[4].equalsIgnoreCase("msg") || messagelist[4].equalsIgnoreCase("minecraft:say")) && Bukkit.getPlayer(messagelist[2]) != null && Bukkit.getPlayer(messagelist[5]) != null) {
				event.setCancelled(true);
				ChatUtil.sendPrivateMessage(Bukkit.getPlayer(messagelist[2]), Bukkit.getPlayer(messagelist[5]), getStringMessage(messagelist, 6));
			}
			return;
		}
	}
	
	private static String getStringMessage(String[] messagelist, int indexOfFirstMessgae) {
		String message = "";
		for (int i = indexOfFirstMessgae; i < messagelist.length; i++) {
			message += " " + messagelist[i];
		}
		if (message.startsWith(" ")) {
			message = message.substring(1);
		}
		return message;
	}

}
