package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.main.ServerSystem;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.TeamUtil;

public class VanishCommand implements CommandExecutor, TabCompleter {

	private static final ArrayList<Player> vanishedPlayers = new ArrayList<>();
	
	public static void toggleVanish(Player player, boolean chatOutput) {
		toggleVanish(player, player, chatOutput);
	}
	
	public static void toggleVanish(Player player, CommandSender sender, boolean chatOutput) {
		if (vanishedPlayers.contains(player)) {
			show(player);
			if (chatOutput) ChatUtil.sendMessage(player, "You are no longer vanished!");
			if (player != sender && chatOutput) ChatUtil.sendMessage(sender, player.getName() + " is no longer vanished!");
		} else {
			hide(player);
			if (chatOutput) ChatUtil.sendMessage(player, "You are vanished now!");
			if (player != sender && chatOutput) ChatUtil.sendMessage(sender, player.getName() + " is vanished now!");
		}
	}
	
	public static boolean isVanished(Player player) {
		return vanishedPlayers.contains(player);
	}
	
	public static ArrayList<Player> getVanishedPlayers() {
		return vanishedPlayers;
	}
	
	private static void hide(Player player) {
		TeamUtil.addPlayerToTeam(TeamUtil.TEAMVANISH, player.getName());
		for (Player everyPlayer : ChatUtil.getVisiblePlayers(player, false)) {
			if (isVanished(everyPlayer)) {
				player.showPlayer(ServerSystem.getInstance(), everyPlayer);
				everyPlayer.showPlayer(ServerSystem.getInstance(), player);
			} else {
				everyPlayer.hidePlayer(ServerSystem.getInstance(), player);
			}	
		}
		vanishedPlayers.add(player);	
	}
	
	private static void show(Player player) {
		TeamUtil.addRoleToPlayer(player);
		for (Player everyPlayer : ChatUtil.getVisiblePlayers(player, false)) {
			everyPlayer.showPlayer(ServerSystem.getInstance(), player);
		}
		for (Player vanishedPlayer : vanishedPlayers) {
			player.hidePlayer(ServerSystem.getInstance(), vanishedPlayer);
		}
		vanishedPlayers.remove(player);
	}
	
	public static void getVisualPlayers(Player player) {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) toggleVanish((Player) sender, true);
			else ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else if (args.length == 1) {
			if (Bukkit.getPlayer(args[0]) != null) toggleVanish(Bukkit.getPlayer(args[0]), sender, true);
			else ChatUtil.sendPlayerNotOnlineErrorMessage(sender, args[0]);
		} else {
			ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) return ChatUtil.cutArguments(args, ChatUtil.getReachableChatPlayers(sender));
		else return new ArrayList<>();
	}

}
