package serversystem.signs;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.handler.ItemHandler;
import serversystem.utilities.ServerSign;

public class ItemSign implements ServerSign{

	@Override
	public String getLabel() {
		return "item";
	}

	@Override
	public void onAction(Player player, Sign sign, String args) {
		ItemStack itemstack = ItemHandler.getItem(args);
		if(itemstack != null) {
			player.getInventory().addItem(ItemHandler.getItem(args));
		}
	}

	@Override
	public boolean onPlace(Player player, String args) {
		ItemStack itemstack = ItemHandler.getItem(args);
		if(itemstack != null) {
			return true;
		}
		return false;
	}

}
