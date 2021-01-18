package serversystem.events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.minecraft.server.v1_14_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_14_R1.PacketPlayInClientCommand.EnumClientCommand;
import serversystem.config.Config;
import serversystem.handler.ChatHandler;
import serversystem.handler.WorldGroupHandler;
import serversystem.main.ServerSystem;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(event.getEntity() instanceof Player && WorldGroupHandler.isEnabled()) {
			World world = event.getEntity().getWorld();
			if(Config.hasDeathMessage(world.getName())) {
				ChatHandler.sendServerWorldGroupMessage(WorldGroupHandler.getWorldGroup(event.getEntity()), event.getDeathMessage());
			}
			Bukkit.getScheduler().runTaskLater(ServerSystem.getInstance(), new Runnable() {
				@Override
				public void run() {
					((CraftPlayer) event.getEntity()).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
					event.getEntity().teleport(world.getSpawnLocation());
				}
			}, 10);
		}
		event.setDeathMessage("");
	}
	
}
