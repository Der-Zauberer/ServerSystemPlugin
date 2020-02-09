package serversystem.signs;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import serversystem.extraitems.Test;
import serversystem.utilities.ServerSign;

public class ExtraitemSign implements ServerSign{

	@Override
	public String getLabel() {
		return "extraitem";
	}

	@Override
	public void onAction(Player player, Sign sign, String args) {
		if(args.equals("Test")) {
			player.getInventory().addItem(Test.getItem());
		}
	}

	@Override
	public boolean onPlace(Player player, String args) {
		if(args.equals("Test")) {
			return true;
		}
		return false;
	}

}
