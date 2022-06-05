package serversystem.actions;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public interface ServerSignClickAction {
	
	public abstract void onAction(Player player, Sign sign, String args);

}
