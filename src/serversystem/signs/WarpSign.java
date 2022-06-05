package serversystem.signs;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import serversystem.handler.ChatHandler;
import serversystem.handler.ChatHandler.ErrorMessage;
import serversystem.utilities.ServerSign;
import serversystem.utilities.ServerWarp;

public class WarpSign extends ServerSign {

	public WarpSign() {
		super("warp");
		setClickAction(this::clickAction);
		setPlaceAction(this::placeAction);
	}
	
	private void clickAction(Player player, Sign sign, String args) {
		if (ServerWarp.getWarp(args) != null) {
			ServerWarp warp = ServerWarp.getWarp(args);
			if (warp.getPermission() == null || player.hasPermission(warp.getPermission())) {
				player.teleport(ServerWarp.getWarp(args).getLocation());
			} else {
				ChatHandler.sendServerErrorMessage(player, ErrorMessage.NOPERMISSION);
			}
		}
	}
	
	private boolean placeAction(Player player, String args) {
		return ServerWarp.getWarp(args) != null;
	}

}
