package serversystem.utilities;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public abstract interface ServerSign {
	
	public String getLabel();
	
	public void onAction(Player player, Sign sign, String args);

	public boolean onPlace(Player player, String args);
	
}