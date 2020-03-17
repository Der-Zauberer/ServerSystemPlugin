package serversystem.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import net.minecraft.server.v1_14_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_14_R1.PacketPlayInClientCommand.EnumClientCommand;
import serversystem.handler.ChatMessage;
import serversystem.handler.WorldGroupHandler;
import serversystem.main.ServerSystem;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setDeathMessage("");
		if(!WorldGroupHandler.getWorldGroup(event.getEntity()).isServerGame()) {
			Bukkit.getScheduler().runTaskLater(ServerSystem.getInstance(), new Runnable() {
				@Override
				public void run() {
					((CraftPlayer) event.getEntity()).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
					ChatMessage.sendPlayerDeathMessage((Player) event.getEntity());
				}
			}, 10);
		} else if(!WorldGroupHandler.getWorldGroup(event.getEntity()).getServerGame().getGametype().equals("Murder")) {
			((CraftPlayer) event.getEntity()).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
			ChatMessage.sendPlayerDeathMessage((Player) event.getEntity());
			event.getEntity().setGameMode(GameMode.SPECTATOR);
		}
	}

}
