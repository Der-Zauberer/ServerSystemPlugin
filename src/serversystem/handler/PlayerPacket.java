package serversystem.handler;

import java.util.ArrayList;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_14_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle.EnumTitleAction;

public class PlayerPacket {
	
	public static void sendTitle(Player player, EnumTitleAction titleAction, String jsonstring, int time) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(titleAction, ChatSerializer.a(jsonstring), 0, time, 0);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static void sendTitle(Player player, EnumTitleAction titleAction, String text, String color, int time) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(titleAction, ChatSerializer.a("{\"text\":\"" + text +  "\",\"color\":\"" + color +  "\"}"), 0, time, 0);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static void sendTitle(Player player, EnumTitleAction titleAction, String text, String color, String bold, String italic, int time) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(titleAction, ChatSerializer.a("{\"text\":\"" + text +  "\",\"color\":\"" + color + "\",\"bold\":\"" + bold +  "\",\"italic\":\"" + italic +  "\"}"), 0, time, 0);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static void sendTitle(ArrayList<Player> players, EnumTitleAction titleAction, String jsonstring, int time) {
		for (Player player : players) {
			sendTitle(player, titleAction, jsonstring, time);
		}
	}
	
	public static void sendTitle(ArrayList<Player> players, EnumTitleAction titleAction, String text, String color, int time) {
		for (Player player : players) {
			sendTitle(player, titleAction, text, color, time);
		}
	}
	
	public static void sendTitle(ArrayList<Player> players, EnumTitleAction titleAction, String text, String color, String bold, String italic, int time) {
		for (Player player : players) {
			sendTitle(player, titleAction, text, color, bold, italic, time);
		}
	}

}
