package serversystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import serversystem.utilities.CommandAssistant;

public class SpeedCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(new CommandAssistant(sender).isSenderInstanceOfPlayer()) {
			Player player = (Player)sender;
			if(player.getFlySpeed() > 0.2) {
				player.setFlySpeed((float) 0.1);
			} else {
				player.setFlySpeed((float) 0.4);
			}
		}
		return true;
	}

}