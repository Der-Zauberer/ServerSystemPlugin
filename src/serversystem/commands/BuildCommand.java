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
import org.bukkit.entity.Entity;
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

public class BuildCommand implements CommandExecutor, TabCompleter, Listener {
	
	private static final ArrayList<Player> buildPlayers = new ArrayList<>();
	
	public static void toggleBuildMode(Player player) {
		toggleBuildMode(player, player);
	}
	
	private static void toggleBuildMode(Player player, CommandSender sender) {
		if (!Config.getWorldOption(player.getWorld(), WorldOption.PROTECTION)) {
			ChatUtil.sendErrorMessage(sender, "This world is not protected!");
			return;
		} else if (player != sender && !sender.hasPermission("serversystem.command.build.other")) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
		} else if (buildPlayers.contains(player)) {
			buildPlayers.remove(player);
			ChatUtil.sendMessage(player, "You can no longer build!");
			if (player != sender) ChatUtil.sendMessage(sender, player.getName() + " can no longer build!");
		} else {
			buildPlayers.add(player);
			ChatUtil.sendMessage(player, "You can build now!");
			if(player != sender) ChatUtil.sendMessage(sender, player.getName() + " can build now!");
		}
	}
	
	public static boolean isInBuildmode(Player player) {
		return buildPlayers.contains(player);
	}
	
	private static boolean isActionForbidden(World world, Entity entity) {
		return Config.getWorldOption(world, WorldOption.PROTECTION) && (!(entity instanceof Player) || !buildPlayers.contains((Player) entity));
	}
	
	private static boolean isBlockDisabled(Block block, Player player) {
		if(!player.hasPermission("serversystem.tools.disabledblocks")) {
			for (String string : Config.getDisabledBlocks()) {
				if (!string.contains(":")) string = "minecraft:" + string;
				if (block.getBlockData().getAsString().equals(string)) return true;
			}
		}
		return false;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) toggleBuildMode((Player) sender);
			else ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else if (args.length == 1) {
			if (!sender.hasPermission("serversystem.command.build.other")) ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
			else if (Bukkit.getPlayer(args[0]) != null) toggleBuildMode(Bukkit.getPlayer(args[0]), sender);
			else ChatUtil.sendPlayerNotOnlineErrorMessage(sender, args[0]);
		} else {
			ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (ChatUtil.getCommandLayer(1, args) && sender.hasPermission("serversystem.command.build.other")) return ChatUtil.removeWrong(ChatUtil.getPlayerList(sender), args);
		return new ArrayList<>();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onBlockBreak(BlockBreakEvent event) {
		if (!event.isCancelled() && isActionForbidden(event.getPlayer().getWorld(), event.getPlayer())) event.setCancelled(true);
		if (!event.isCancelled() && isBlockDisabled(event.getBlock(), event.getPlayer())) event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onBlockPlace(BlockPlaceEvent event) {
		if (!event.isCancelled() && isActionForbidden(event.getPlayer().getWorld(), event.getPlayer())) event.setCancelled(true);
		if (!event.isCancelled() && isBlockDisabled(event.getBlock(), event.getPlayer())) event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
		if (!event.isCancelled() && event.getRemover() instanceof Player && isActionForbidden(event.getRemover().getWorld(), event.getRemover())) event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public static void onProjectileLaunch(ProjectileLaunchEvent event) {
		if (!event.isCancelled() && event.getEntity().getShooter() instanceof Player && isActionForbidden(event.getEntity().getWorld(), (Entity) event.getEntity().getShooter())) event.setCancelled(true);
    }
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) return;
		if (!event.isCancelled() && event.getDamager() instanceof Player && isActionForbidden(event.getDamager().getWorld(), event.getDamager())) event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
		if (!event.isCancelled() && isActionForbidden(event.getPlayer().getWorld(), event.getPlayer())) event.setCancelled(true);
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
			for (Material material : vorbiddenMaterials) {
				if (mainMaterial == material || secondaryMaterial == material) {
					event.setCancelled(true);
					return;
				}
			}
			if (mainMaterial.name().contains("MINECART") || secondaryMaterial.name().contains("MINECART")) event.setCancelled(true);
			else if (mainMaterial.name().contains("BOAT") || secondaryMaterial.name().contains("BOAT")) event.setCancelled(true);
			else if (mainMaterial.name().contains("ITEM_FRAME") || secondaryMaterial.name().contains("ITEM_FRAME")) event.setCancelled(true);
			else if (mainMaterial.name().contains("SPAWN_EGG") || secondaryMaterial.name().contains("SPAWN_EGG")) event.setCancelled(true);
			else if (mainMaterial.name().contains("BUCKET") || secondaryMaterial.name().contains("BUCKET")) event.setCancelled(true);
			else if (mainMaterial.name().contains("ITEM_FRAME") || secondaryMaterial.name().contains("ITEM_FRAME")) event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (!event.isCancelled() && isActionForbidden(event.getPlayer().getWorld(), event.getPlayer()) && event.getRightClicked().getType() == EntityType.ITEM_FRAME) event.setCancelled(true);
	}

}
