package serversystem.extraitems;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import serversystem.utilities.ExtraItem;

public class UltraSwoardItem extends ExtraItem{
	
	public UltraSwoardItem() {
		super("ultra_sword", Material.WOODEN_SWORD);
		setDisplayName(ChatColor.RESET + "Ultra Sword");
		setDescription(Arrays.asList("Type: Weapon"));
	}
	
	@Override
	public void onRightClick(PlayerInteractEvent event, Player player, ExtraItem extraitem) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 5, 2), true);
		
	}

	@Override
	public void onLeftClick(PlayerInteractEvent event, Player player, ExtraItem extraitem) {
		
	}

}
