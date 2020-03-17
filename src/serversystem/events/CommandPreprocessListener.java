package serversystem.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import serversystem.handler.ChatMessage;

public class CommandPreprocessListener implements Listener {
	
	@EventHandler
	public void onCommandPreProcess(PlayerCommandPreprocessEvent event){
		if(event.getMessage().toLowerCase().startsWith("/say") || event.getMessage().toLowerCase().startsWith("/minecraft:say") || event.getMessage().toLowerCase().startsWith("/me") || event.getMessage().toLowerCase().startsWith("/minecraft:me")){
			event.setCancelled(true);
			String[] messagelist = event.getMessage().split(" ");
			ChatMessage.sendPlayerChatMessage(event.getPlayer(), getStringMessage(messagelist, 1));
			return;
		}
		if(event.getMessage().toLowerCase().startsWith("/msg") || event.getMessage().toLowerCase().startsWith("/minecraft:msg") || event.getMessage().toLowerCase().startsWith("/tell") || event.getMessage().toLowerCase().startsWith("/minecraft:tell")){
			String[] messagelist = event.getMessage().split(" ");
			if(Bukkit.getPlayer(messagelist[1]) != null) {
				event.setCancelled(true);
				ChatMessage.sendPlayerPrivateMessage(event.getPlayer(), Bukkit.getPlayer(messagelist[1]), getStringMessage(messagelist, 2));
				
			}
			return;
		}
		if(event.getMessage().toLowerCase().startsWith("/execute")) {
			String[] messagelist = event.getMessage().split(" ");
			if(messagelist[1].equalsIgnoreCase("as") && messagelist[3].equalsIgnoreCase("run") && (messagelist[4].equalsIgnoreCase("say") || messagelist[4].equalsIgnoreCase("minecraft:say") || messagelist[4].equalsIgnoreCase("me") || messagelist[4].equalsIgnoreCase("minecraft:me")) && Bukkit.getPlayer(messagelist[2]) != null) {
				event.setCancelled(true);
				ChatMessage.sendPlayerChatMessage(Bukkit.getPlayer(messagelist[2]), getStringMessage(messagelist, 5));
			}
			if(messagelist[1].equalsIgnoreCase("as") && messagelist[3].equalsIgnoreCase("run") && (messagelist[4].equalsIgnoreCase("msg") || messagelist[4].equalsIgnoreCase("minecraft:say")) && Bukkit.getPlayer(messagelist[2]) != null && Bukkit.getPlayer(messagelist[5]) != null) {
				event.setCancelled(true);
				ChatMessage.sendPlayerPrivateMessage(Bukkit.getPlayer(messagelist[2]), Bukkit.getPlayer(messagelist[5]), getStringMessage(messagelist, 6));
			}
			return;
		}
	}
	
	private String getStringMessage(String[] messagelist, int indexOfFirstMessgae) {
		String message = "";
		for (int i = indexOfFirstMessgae; i < messagelist.length; i++) {
			message = message + " " + messagelist[i];
			message = message.substring(1);
		}
		return message;
	}

}
