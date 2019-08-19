package serversystem.utilities;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_14_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle.EnumTitleAction;

public class PlayerPacket {
	
	public static void sendTitle(Player player, EnumTitleAction titleAction, String jsonstring, int fade, int time) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(titleAction, ChatSerializer.a(jsonstring), fade, time, fade);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static void sendTitle(Player player, EnumTitleAction titleAction, String text, String color, int fade, int time) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(titleAction, ChatSerializer.a("{\"text\":\"" + text +  "\",\"color\":\"" + color +  "\"}"), fade, time, fade);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static void sendTitle(Player player, EnumTitleAction titleAction, String text, String color, String bold, String italic, int fade, int time) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(titleAction, ChatSerializer.a("{\"text\":\"" + text +  "\",\"color\":\"" + color + "\",\"bold\":\"" + bold +  "\",\"italic\":\"" + italic +  "\"}"), fade, time, fade);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

}
