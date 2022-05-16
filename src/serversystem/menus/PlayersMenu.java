package serversystem.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import net.md_5.bungee.api.ChatColor;
import serversystem.utilities.PlayerInventory;

public class PlayersMenu extends PlayerInventory {
	
	public PlayersMenu(Player player) {
		super(player, calculateSize(Bukkit.getOnlinePlayers().size() + 9), "Players");
		setItemOption(ItemOption.FIXED);
		int i = 0;
		setItem(calculateSize(Bukkit.getOnlinePlayers().size() + 9) - 5, createItem("Back", Material.SPECTRAL_ARROW), (itemstack) -> {new AdminMenu(player).open();});
		for (Player players : Bukkit.getOnlinePlayers()) {
			if(i < 45) {
				setItem(i, createPlayerSkullItem(players.getName(), players), (itemstack) -> {setPlayer(itemstack, player, players);});
				i++;
			} else {
				return;
			}
		}
	}
	
	private void setPlayer(ItemStack itemstack, Player player, Player target) {
		String name = ChatColor.stripColor(itemstack.getItemMeta().getDisplayName());
		if (Bukkit.getPlayer(name) != null) {
			new PlayerMenu(player, target).open();
		}
	}

}
