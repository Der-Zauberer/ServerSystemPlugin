package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import serversystem.actions.ServerSignClickAction;
import serversystem.actions.ServerSignPlaceAction;

public class ServerSign implements Listener {
	
	private String name;
	private ServerSignClickAction clickAction;
	private ServerSignPlaceAction placeAction;
	
	private static ServerSign instance = new ServerSign();
	private static ArrayList<ServerSign> signs = new ArrayList<>();
	
	private ServerSign() {}
	
	public ServerSign(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setClickAction(ServerSignClickAction clickAction) {
		this.clickAction = clickAction;
	}
	
	public ServerSignClickAction getClickAction() {
		return clickAction;
	}
	
	public void setPlaceAction(ServerSignPlaceAction placeAction) {
		this.placeAction = placeAction;
	}
	
	public ServerSignPlaceAction getPlaceAction() {
		return placeAction;
	}
	
	public static void registerServerSign(ServerSign serversign) {
		signs.add(serversign);
	}
	
	public static void executeServerSign(Player player, Sign sign, String label, String args) {
		for (ServerSign serverSign : signs) {
			if (serverSign.getName().equalsIgnoreCase(label)) {
				boolean status = getStatus(player, label, args);
				updateSign(sign, status);
				if (serverSign.getClickAction() != null && status) serverSign.getClickAction().onAction(player, sign, args);
			}
		}
	}
	
	public static void updateSign(Sign sign, boolean status) {
		if (status) sign.setLine(2, "\u00A72" + ChatColor.stripColor(sign.getLine(2)));
		else sign.setLine(2, "\u00A74" + ChatColor.stripColor(sign.getLine(2)));
		sign.update(true);
	}
	
	public static boolean getStatus(Player player, String label, String args) {
		for (ServerSign serverSign : signs) {
			if (serverSign.getName().equalsIgnoreCase(label)) {
				if (serverSign.getPlaceAction() != null) return serverSign.getPlaceAction().onAction(player, args);
				else return true;
			}
		}
		return false;
	}
	
	public static ServerSign getInstance() {
		return instance;
	}
	
	@EventHandler
	public static void onInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if (sign.getLine(0) != null && sign.getLine(3) != null) {
				String label = ChatColor.stripColor(sign.getLine(1)).substring(1, sign.getLine(1).length() -1);
				executeServerSign(event.getPlayer(), sign, label, ChatColor.stripColor(sign.getLine(2)));
			}
		}
	}
	
	@EventHandler
	public static void onSignChange(SignChangeEvent event) {
		if (event.getLine(0) != null && event.getLine(3) != null) {
			if (event.getLine(1).contains("[") || event.getLine(1).contains("]")) {
				if (event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
					String label = event.getLine(1).substring(1, event.getLine(1).length() -1);
					if (getStatus(event.getPlayer(), label, event.getLine(2))) event.setLine(2, "\u00A72" + event.getLine(2));
					else event.setLine(2, "\u00A74" + event.getLine(2));
				} else if(!event.getPlayer().hasPermission("serversystem.tools.signeddit")){
					event.setLine(1, "\u00A74Permissions");
					event.setLine(2, "\u00A74required!");
				}
			}	
		}
	}
	
}
