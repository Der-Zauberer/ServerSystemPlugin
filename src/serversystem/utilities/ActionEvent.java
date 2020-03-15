package serversystem.utilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ActionEvent {
	
	public abstract void executeOnAction(ItemStack itemstack, Player player);

}
