package serversystem.signs;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import serversystem.handler.WarpHandler;
import serversystem.utilities.ServerSign;

public class WarpSign implements ServerSign {

	@Override
	public String getLabel() {
		return "warp";
	}

	@Override
	public void onAction(Player player, Sign sign, String args) {
		if(WarpHandler.getWarp(args) != null) {
			WarpHandler.getWarp(args).warpPlayer(player);
		}
	}

	@Override
	public boolean onPlace(Player player, String args) {
		if(WarpHandler.getWarp(args) != null) {
			return true;
		}
		return false;
	}

}
