package serversystem.signs;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import serversystem.handler.ExtraItemHandler;
import serversystem.utilities.ServerSign;

public class ExtraItemSign implements ServerSign{

	@Override
	public String getLabel() {
		return "extraitem";
	}

	@Override
	public void onAction(Player player, Sign sign, String args) {
		if(ExtraItemHandler.getExtraItemById(args) != null) {
			player.getInventory().addItem(ExtraItemHandler.getExtraItemById(args));
		} else if(ExtraItemHandler.getExtraItemByName(args) != null) {
			player.getInventory().addItem(ExtraItemHandler.getExtraItemByName(args));
		}
	}

	@Override
	public boolean onPlace(Player player, String args) {
		if(ExtraItemHandler.getExtraItemById(args) != null) {
			return true;
		} else if(ExtraItemHandler.getExtraItemByName(args) != null) {
			return true;
		}
		return false;
	}

}
