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
import serversystem.utilities.CommandAssistant;
import serversystem.utilities.TeamUtil;
import serversystem.utilities.WorldGroup;

public class VanishCommand implements CommandExecutor, TabCompleter {

	private static final ArrayList<Player> vanishedPlayers = new ArrayList<>();
	
	public static void toggleVanish(Player player) {
		toggleVanish(player, player);
	}
	
	public static void toggleVanish(Player player, CommandSender sender) {
		if (vanishedPlayers.contains(player)) {
			show(player);
			ChatUtil.sendServerMessage(player, "You are no longer vanished!");
			if (player != sender) ChatUtil.sendServerMessage(sender, player.getName() + " is no longer vanished!");
		} else {
			hide(player);
			ChatUtil.sendServerMessage(player, "You are vanished now!");
			if (player != sender) ChatUtil.sendServerMessage(sender, player.getName() + " is vanished now!");
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
		for (Player everyPlayer : Bukkit.getOnlinePlayers()) {
			everyPlayer.hidePlayer(ServerSystem.getInstance(), player);
		}
		if (!vanishedPlayers.isEmpty()) {
			for (Player vanishedplayer : vanishedPlayers) {
				vanishedplayer.showPlayer(ServerSystem.getInstance(), player);
				player.hidePlayer(ServerSystem.getInstance(), vanishedplayer);
			}
		}
		vanishedPlayers.add(player);	
	}
	
	private static void show(Player player) {
		TeamUtil.addRoleToPlayer(player);
		for (Player everyPlayer : WorldGroup.getWorldGroup(player).getPlayers()) {
			everyPlayer.showPlayer(ServerSystem.getInstance(), player);
		}
		if (!vanishedPlayers.isEmpty()) {
			for (Player vanishedplayer : vanishedPlayers) {
				player.hidePlayer(ServerSystem.getInstance(), vanishedplayer);
			}
		}
		vanishedPlayers.remove(player);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final CommandAssistant assistent = new CommandAssistant(sender);
		if (args.length == 0) {
			if (assistent.isSenderInstanceOfPlayer(true)) toggleVanish((Player) sender);
		} else {
			if (assistent.isPlayer(args[0])) toggleVanish(Bukkit.getPlayer(args[0]), sender);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		final CommandAssistant assistant = new CommandAssistant(sender);
		List<String> commands = new ArrayList<>();
		if (args.length == 1) commands = assistant.getPlayers();
		assistant.cutArguments(args, commands);
		return commands;
	}

}
