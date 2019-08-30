package serversystem.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import net.minecraft.server.v1_14_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_14_R1.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle.EnumTitleAction;
import serversystem.utilities.PlayerPacket;
import serversystem.utilities.PlayerPermission;
import serversystem.utilities.PlayerTeam;
import serversystem.utilities.PlayerVanish;
import serversystem.utilities.ServerMessage;
import serversystem.utilities.WorldGroupHandler;

public class SystemEvents implements Listener{

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event) {
		Config.addPlayer(event.getPlayer());
		PlayerPermission.removeConfigDisablePermissions(event.getPlayer());
		PlayerPermission.addConfigPermissions(event.getPlayer());
		if(event.getPlayer().isOp()) {
			event.getPlayer().setOp(false);
			event.getPlayer().setOp(true);
		} else {
			event.getPlayer().setOp(true);
			event.getPlayer().setOp(false);
		}
		if(!Config.isJoinMessageActiv()) {
			event.setJoinMessage("");
		}
		if(Config.lobbyExists() && Config.getLobbyWorld() != null) {
			event.getPlayer().teleport(Config.getLobbyWorld().getSpawnLocation());
		}
		WorldGroupHandler.getWorldGroup(event.getPlayer()).onPlayerJoin(event.getPlayer());
		event.getPlayer().setGameMode(Config.getWorldGamemode(event.getPlayer().getWorld().getName()));
		if(Config.getTitle() != null) {PlayerPacket.sendTitle(event.getPlayer(), EnumTitleAction.TITLE, Config.getTitle(), Config.getTitleColor(), 20, 40);}
		if(Config.getSubtitle() != null) {PlayerPacket.sendTitle(event.getPlayer(), EnumTitleAction.SUBTITLE, Config.getSubtitle(), Config.getSubtitleColor(), 20, 40);}
	}
	
	@EventHandler
	public void onQuitEvent(PlayerQuitEvent event) {
		if(!Config.isLeaveMessageActiv()) {
			event.setQuitMessage("");
		}
		if(PlayerVanish.isPlayerVanished(event.getPlayer())) {
			PlayerVanish.vanishPlayer(event.getPlayer());
		}
		WorldGroupHandler.getWorldGroup(event.getPlayer()).onPlayerLeave(event.getPlayer());
	}
	
	@EventHandler
	public void onCommandPreProcess(PlayerCommandPreprocessEvent event){
		if(event.getMessage().toLowerCase().startsWith("/say") || event.getMessage().toLowerCase().startsWith("/minecraft:say") || event.getMessage().toLowerCase().startsWith("/me") || event.getMessage().toLowerCase().startsWith("/minecraft:me")){
			event.setCancelled(true);
			String[] messagelist = event.getMessage().split(" ");
			String message = "";
			for (int i = 1; i < messagelist.length; i++) {
				message = message + " " + messagelist[i];
			}
			for(World world : WorldGroupHandler.getWorldGroup(event.getPlayer()).getWorlds()) {
				for(Player player : world.getPlayers()) {
					player.sendMessage(PlayerTeam.getPlayerNameColor(event.getPlayer())  + event.getPlayer().getName() + ChatColor.WHITE + ": " + message);
				}
			}
			return;
		}
		if(event.getMessage().toLowerCase().startsWith("/msg") || event.getMessage().toLowerCase().startsWith("/minecraft:msg") || event.getMessage().toLowerCase().startsWith("/tell") || event.getMessage().toLowerCase().startsWith("/minecraft:tell")){
			String[] messagelist = event.getMessage().split(" ");
			String message = "";
			if(Bukkit.getPlayer(messagelist[1]) != null) {
				event.setCancelled(true);
				for (int i = 2; i < messagelist.length; i++) {
					message = message + " " + messagelist[i];
				}
				Bukkit.getPlayer(messagelist[1]).sendMessage(PlayerTeam.getPlayerNameColor(event.getPlayer()) + event.getPlayer().getName() + ChatColor.WHITE + " -> " + PlayerTeam.getPlayerNameColor(Bukkit.getPlayer(messagelist[1])) + "Mir" + ChatColor.WHITE + " :" + ChatColor.GRAY + message);
				event.getPlayer().sendMessage(PlayerTeam.getPlayerNameColor(event.getPlayer()) + event.getPlayer().getName() + ChatColor.WHITE + " -> " + PlayerTeam.getPlayerNameColor(Bukkit.getPlayer(messagelist[1])) + "Mir" + ChatColor.WHITE + " :" + ChatColor.GRAY + message);
				
			}
			return;
		}
		if(event.getMessage().toLowerCase().startsWith("/execute")) {
			String[] messagelist = event.getMessage().split(" ");
			if(messagelist[1].equalsIgnoreCase("as") && messagelist[3].equalsIgnoreCase("run") && (messagelist[4].equalsIgnoreCase("say") || messagelist[4].equalsIgnoreCase("minecraft:say") || messagelist[4].equalsIgnoreCase("me") || messagelist[4].equalsIgnoreCase("minecraft:me")) && Bukkit.getPlayer(messagelist[2]) != null) {
				event.setCancelled(true);
				String message = "";
				for (int i = 5; i < messagelist.length; i++) {
					message = message + " " + messagelist[i];
				}
				for(Player player : event.getPlayer().getWorld().getPlayers()) {
					player.sendMessage(PlayerTeam.getPlayerNameColor(Bukkit.getPlayer(messagelist[2]))  + Bukkit.getPlayer(messagelist[2]).getName() + ChatColor.WHITE + ":" + message);
				}
			}
			if(messagelist[1].equalsIgnoreCase("as") && messagelist[3].equalsIgnoreCase("run") && (messagelist[4].equalsIgnoreCase("msg") || messagelist[4].equalsIgnoreCase("minecraft:say")) && Bukkit.getPlayer(messagelist[2]) != null && Bukkit.getPlayer(messagelist[5]) != null) {
				event.setCancelled(true);
				String message = "";
				for (int i = 6; i < messagelist.length; i++) {
					message = message + " " + messagelist[i];
				}
				Bukkit.getPlayer(messagelist[2]).sendMessage(PlayerTeam.getPlayerNameColor(Bukkit.getPlayer(messagelist[2])) + Bukkit.getPlayer(messagelist[2]).getName() + ChatColor.WHITE + " -> " + PlayerTeam.getPlayerNameColor(Bukkit.getPlayer(messagelist[5])) + "Mir" + ChatColor.WHITE + " :" + ChatColor.GRAY + message);
				Bukkit.getPlayer(messagelist[5]).sendMessage(PlayerTeam.getPlayerNameColor(Bukkit.getPlayer(messagelist[2])) + Bukkit.getPlayer(messagelist[2]).getName() + ChatColor.WHITE + " -> " + PlayerTeam.getPlayerNameColor(Bukkit.getPlayer(messagelist[5])) + "Mir" + ChatColor.WHITE + " :" + ChatColor.GRAY + message);
			}
			return;
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if (!WorldGroupHandler.getWorldGroup(event.getPlayer()).isServerGame()) {
			event.setFormat(PlayerTeam.getPlayerNameColor(event.getPlayer())  + event.getPlayer().getName() + ChatColor.WHITE + ": " + event.getMessage());
			event.setCancelled(true);
			for(Player player : WorldGroupHandler.getWorldGroup(event.getPlayer()).getPlayers()) {
				player.sendMessage(PlayerTeam.getPlayerNameColor(event.getPlayer())  + event.getPlayer().getName() + ChatColor.WHITE + ": " + event.getMessage());
			}
		}
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		if(event.getPlayer().getWorld() != event.getTo().getWorld()) {
			boolean vanished = PlayerVanish.isPlayerVanished(event.getPlayer());
			WorldGroupHandler.getWorldGroup(event.getPlayer()).onPlayerLeave(event.getPlayer());
			WorldGroupHandler.getWorldGroup(event.getTo().getWorld()).onPlayerJoin(event.getPlayer());
			if(vanished) {
				PlayerVanish.vanishPlayer(event.getPlayer());
			}
			event.getPlayer().setGameMode(Config.getWorldGamemode(event.getTo().getWorld().getName()));
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			if(!Config.hasWorldDamage(event.getEntity().getWorld().getName())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			if(!Config.hasWorldHunger(event.getEntity().getWorld().getName())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onExplotion(EntityExplodeEvent event) {
		if(!Config.hasWorldExplosion(event.getEntity().getWorld().getName())) {
			event.blockList().clear();
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setDeathMessage("");
		if(!WorldGroupHandler.getWorldGroup(event.getEntity()).isServerGame()) {
			Bukkit.getScheduler().runTaskLater(ServerSystem.getInstance(), new Runnable() {
				@Override
				public void run() {
					((CraftPlayer) event.getEntity()).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
					ServerMessage.sendDeathMessage((Player) event.getEntity());
				}
			}, 10);
		} else if(!WorldGroupHandler.getWorldGroup(event.getEntity()).getServerGame().getGametype().equals("Murder")) {
			((CraftPlayer) event.getEntity()).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
			ServerMessage.sendDeathMessage((Player) event.getEntity());
			event.getEntity().setGameMode(GameMode.SPECTATOR);
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if(!WorldGroupHandler.getWorldGroup(event.getPlayer()).isServerGame()) {
			event.setRespawnLocation(WorldGroupHandler.getWorldGroup(event.getPlayer()).getMainWorld().getSpawnLocation());
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if(event.getLine(0) != null && event.getLine(3) != null) {
			if(event.getLine(1).equals("[World]") && Bukkit.getWorld(event.getLine(2)) != null && event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
				String labelbody = event.getLine(2);
				event.setLine(2, "§2" + labelbody);
			} else if(!event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
				event.setLine(1, "§4Permissions");
				event.setLine(2, "§4required!");
			} else if(Bukkit.getWorld(event.getLine(2)) == null) {
				String labelbody = event.getLine(2);
				event.setLine(2, "§4" + labelbody);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if(sign.getLine(0) != null && sign.getLine(3) != null) {
				if(sign.getLine(1).equals("[World]") && Bukkit.getWorld(ChatColor.stripColor(sign.getLine(2))) != null) {
					WorldGroupHandler.teleportPlayer(event.getPlayer(), Bukkit.getWorld(ChatColor.stripColor(sign.getLine(2))));
				}
			}
		}
	}
	
}
