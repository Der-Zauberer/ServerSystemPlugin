package serversystem.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import serversystem.utilities.ItemBuilder;
import serversystem.utilities.ItemFunction;

public class UltraSwoardItem implements ItemFunction {
	
	public ItemStack getItem() {
		ItemBuilder itembuilder = new ItemBuilder();
		itembuilder.setDisplayName(ChatColor.RESET + "Ultra Swoard");
		itembuilder.setMaterial(Material.WOODEN_SWORD);
		itembuilder.setCustomModelData(1);
		return itembuilder.buildItem();
	}

	@Override
	public void onItemUse(Player player, ItemStack itemstack, Action action) {
		if(action == Action.RIGHT_CLICK_AIR) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 5, 2), true);
		}
	}

	@Override
	public Material getType() {
		return Material.WOODEN_SWORD;
	}

	@Override
	public int getCustomModelData() {
		return 1;
	}

}