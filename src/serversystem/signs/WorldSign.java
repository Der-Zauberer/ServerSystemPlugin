package serversystem.signs;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.ServerSign;
import serversystem.utilities.WorldGroup;
import serversystem.utilities.ChatUtil.ErrorMessage;

public class WorldSign extends ServerSign {

	public WorldSign() {
		super("world");
		setClickAction(this::clickAction);
		setPlaceAction(this::placeAction);
	}
	
	private void clickAction(Player player, Sign sign, String args) {
		World world = Bukkit.getWorld(args);
		if (world != null) {
			if (!Config.hasWorldPermission(world) || player.hasPermission(Config.getWorldPermission(world))) {
				WorldGroup.teleportPlayer(player, Bukkit.getWorld(args));
			} else {
				ChatUtil.sendServerErrorMessage(player, ErrorMessage.NOPERMISSION);
			}
		}
	}

	private boolean placeAction(Player player, String args) {
		return Bukkit.getWorld(args) != null;
	}

}
