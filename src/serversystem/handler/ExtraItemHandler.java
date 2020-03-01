package serversystem.handler;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import serversystem.utilities.ExtraItem;

public class ExtraItemHandler implements Listener {
	
	private static ArrayList<ExtraItem> extraitems = new ArrayList<>();
	
	public static void registerExtraItem(ExtraItem extraitem) {
		extraitems.add(extraitem);
	}
	
	public static ExtraItem getExtraItemById(String id) {
		for (ExtraItem extraitem : extraitems) {
			if(extraitem.getId().equalsIgnoreCase(id)) {
				return extraitem;
			}
		}
		return null;
	}
	
	public static ExtraItem getExtraItemByName(String name) {
		for (ExtraItem extraitem : extraitems) {
			if(extraitem.getDisplayName().equals(name)) {
				return extraitem;
			}
		}
		return null;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR) {
			String itemname = ChatColor.stripColor(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName());
			if(getExtraItemByName(itemname) != null) {
				getExtraItemByName(itemname).onItemInteract(event, event.getPlayer());
			}
		}
	}
	
}
