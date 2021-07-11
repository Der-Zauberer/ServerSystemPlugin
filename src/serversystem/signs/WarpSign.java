package serversystem.signs;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import serversystem.handler.ChatHandler;
import serversystem.handler.WarpHandler;
import serversystem.handler.ChatHandler.ErrorMessage;
import serversystem.utilities.ServerSign;
import serversystem.utilities.ServerWarp;

public class WarpSign implements ServerSign {

	@Override
	public String getLabel() {
		return "warp";
	}

	@Override
	public void onAction(Player player, Sign sign, String args) {
		if(WarpHandler.getWarp(args) != null) {
			ServerWarp warp = WarpHandler.getWarp(args);
			if(warp.getPermission() == null || player.hasPermission(warp.getPermission())) {
				player.teleport(WarpHandler.getWarp(args).getLocation());
			} else {
				ChatHandler.sendServerErrorMessage(player, ErrorMessage.NOPERMISSION);
			};
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
