package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import serversystem.config.Config;
import serversystem.config.Config.WorldOption;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.CommandAssistant;

public class BuildCommand implements CommandExecutor, TabCompleter, Listener {
	
	private static final ArrayList<Player> buildPlayers = new ArrayList<>();
	
	public static void toggleBuildMode(Player player) {
		toggleBuildMode(player, player);
	}
	
	public static void toggleBuildMode(Player player, CommandSender sender) {
		if (buildPlayers.contains(player)) {
			buildPlayers.remove(player);
			ChatUtil.sendServerMessage(player, "You can no longer build!");
			if (player != sender) ChatUtil.sendServerMessage(sender, player.getName() + " can no longer build!");
		} else {
			buildPlayers.add(player);
			ChatUtil.sendServerMessage(player, "You can build now!");
			if(player != sender) ChatUtil.sendServerMessage(sender, player.getName() + " can build now!");
		}
	}
	
	public static boolean isInBuildmode(Player player) {
		return buildPlayers.contains(player);
	}
	
	private static boolean isActionForbidden(World world, Player player) {
		return Config.getWorldOption(world, WorldOption.PROTECTION) && !buildPlayers.contains(player);
	}
	
	private static boolean isBlockDisabled(Block block, Player player) {
		if(!player.hasPermission("serversystem.tools.disabledblocks")) {
			for (String string : Config.getDisabledBlocks()) {
				if (block.getBlockData().getAsString().equals(string)) return true;
			}
		}
		return false;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistent = new CommandAssistant(sender);
		if (args.length == 0) {
			if (assistent.isSenderInstanceOfPlayer(true)) toggleBuildMode((Player) sender);
		} else {
			if (assistent.isPlayer(args[0])) toggleBuildMode(Bukkit.getPlayer(args[0]), sender);
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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onBlockBreak(BlockBreakEvent event) {
		event.setCancelled(isActionForbidden(event.getPlayer().getWorld(), event.getPlayer()));
		if (!event.isCancelled()) event.setCancelled(isBlockDisabled(event.getBlock(), event.getPlayer()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onBlockPlace(BlockPlaceEvent event) {
		event.setCancelled(isActionForbidden(event.getPlayer().getWorld(), event.getPlayer()));
		if (!event.isCancelled()) event.setCancelled(isBlockDisabled(event.getBlock(), event.getPlayer()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
		if (event.getRemover() instanceof Player) event.setCancelled(isActionForbidden(event.getRemover().getWorld(), (Player) event.getRemover()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public static void onProjectileLaunch(ProjectileLaunchEvent event) {
		if (event.getEntity().getShooter() instanceof Player) event.setCancelled(isActionForbidden(event.getEntity().getWorld(), (Player) event.getEntity().getShooter()));
    }
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) event.setCancelled(isActionForbidden(event.getDamager().getWorld(), (Player) event.getDamager()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
		event.setCancelled(isActionForbidden(event.getPlayer().getWorld(), event.getPlayer()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onPlayerInteract(PlayerInteractEvent event) {
		if (isActionForbidden(event.getPlayer().getWorld(), event.getPlayer())) {
			final Material mainMaterial = event.getPlayer().getInventory().getItemInMainHand().getType();
			final Material secondaryMaterial = event.getPlayer().getInventory().getItemInOffHand().getType();
			ArrayList<Material> vorbiddenMaterials = new ArrayList<>();
			vorbiddenMaterials.add(Material.ARMOR_STAND);
			vorbiddenMaterials.add(Material.PAINTING);
			vorbiddenMaterials.add(Material.ITEM_FRAME);
			vorbiddenMaterials.add(Material.GLOW_ITEM_FRAME);
			for (Material material : vorbiddenMaterials) {
				if (mainMaterial == material || secondaryMaterial == material) {
					event.setCancelled(true);
					return;
				}
			}
			if (mainMaterial.toString().contains("MINECARD") || secondaryMaterial.toString().contains("MINECARD")) event.setCancelled(true);
			else if (mainMaterial.toString().contains("BOAT") || secondaryMaterial.toString().contains("BOAT")) event.setCancelled(true);
			else if (mainMaterial.toString().contains("ITEM_FRAME") || secondaryMaterial.toString().contains("ITEM_FRAME")) event.setCancelled(true);
			else if (mainMaterial.toString().contains("SPAWN_EGG") || secondaryMaterial.toString().contains("SPAWN_EGG")) event.setCancelled(true);
			else if (mainMaterial.toString().contains("BUCKET") || secondaryMaterial.toString().contains("BUCKET")) event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (isActionForbidden(event.getPlayer().getWorld(), event.getPlayer()) && event.getRightClicked().getType() == EntityType.ITEM_FRAME) event.setCancelled(true);
	}

}
