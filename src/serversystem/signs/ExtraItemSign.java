package serversystem.signs;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import serversystem.extraitems.UltraSwoardItem;
import serversystem.utilities.ServerSign;

public class ExtraItemSign implements ServerSign{

	@Override
	public String getLabel() {
		return "extraitem";
	}

	@Override
	public void onAction(Player player, Sign sign, String args) {
		if(args.equalsIgnoreCase("ultra_soward")) {
			player.getInventory().addItem(new UltraSwoardItem().getItem());
		}
	}

	@Override
	public boolean onPlace(Player player, String args) {
		if(args.equalsIgnoreCase("ultra_soward")) {
			return true;
		}
		return false;
	}

}
