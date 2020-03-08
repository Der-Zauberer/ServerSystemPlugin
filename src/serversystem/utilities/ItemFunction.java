package serversystem.utilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public interface ItemFunction {
	
	public abstract void onItemUse(Player player, ItemStack itemstack, Action action);
	public abstract Material getType();
	public abstract int getCustomModelData();

}
