package serversystem.utilities;

import org.bukkit.inventory.ItemStack;

public interface ActionEvent {
	
	public abstract void executeOnAction(ItemStack itemstack);

}
