package serversystem.utilities;

import serversystem.main.ServerSystem;
import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExtendedItemStack extends ItemStack {

    public ExtendedItemStack(String displayName, Material material) {
        super(material);
        final ItemMeta itemMeta = getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.RESET + displayName);
        setItemMeta(itemMeta);
    }

    public ExtendedItemStack setDisplayName(String displayName) {
        final ItemMeta itemMeta = getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.RESET + displayName);
        setItemMeta(itemMeta);
        return this;
    }
    
    public ExtendedItemStack setLore(String string) {
        return setLore(string, ChatColor.GRAY, 30);
    }

    public ExtendedItemStack setLore(String string, ChatColor color,  int lineLengt) {
    	final StringBuilder stringBuilder = new StringBuilder(ChatColor.RESET.toString() + color.toString());
    	int lenght = 0;
    	for (char character : string.toCharArray()) {
    		stringBuilder.append(character);
    		lenght ++;
    		if (character == '\n') {
    			lenght = 0;
    		} else if (lenght > lineLengt) {
    			final int lastSpace = stringBuilder.lastIndexOf(" ");
    			stringBuilder.setCharAt(lastSpace, '\n');
    			lenght = stringBuilder.length() - lastSpace - 1;
    		}
    	}
        final String[] list = stringBuilder.toString().replaceAll("\n", "\n" + ChatColor.RESET + color).split("\n");
        final List<String> lore = new ArrayList<>();
        Collections.addAll(lore, list);
        final ItemMeta itemMeta = getItemMeta();
        assert itemMeta != null;
        itemMeta.setLore(lore);
        setItemMeta(itemMeta);
        return this;
    }

    public ExtendedItemStack setLore(List<String> lore) {
        final ItemMeta itemMeta = getItemMeta();
        assert itemMeta != null;
        itemMeta.setLore(lore);
        setItemMeta(itemMeta);
        return this;
    }

    public ExtendedItemStack removeLore() {
        final ItemMeta itemMeta = getItemMeta();
        assert itemMeta != null;
        itemMeta.setLore(new ArrayList<String>());
        setItemMeta(itemMeta);
        return this;
    }

    public ExtendedItemStack addItemFlag(ItemFlag flag) {
        final ItemMeta itemMeta = getItemMeta();
        assert itemMeta != null;
        itemMeta.addItemFlags(flag);
        setItemMeta(itemMeta);
        return this;
    }

    public ExtendedItemStack removeItemFlag(ItemFlag flag) {
        final ItemMeta itemMeta = getItemMeta();
        assert itemMeta != null;
        itemMeta.removeItemFlags(flag);
        setItemMeta(itemMeta);
        return this;
    }

    public ExtendedItemStack addPotionMeta(Color color, PotionEffect effect) {
        if (getType() == Material.POTION) {
            final PotionMeta potionMeta = (PotionMeta) getItemMeta();
            assert potionMeta != null;
            potionMeta.setColor(color);
            potionMeta.addCustomEffect(effect, true);
            setItemMeta(potionMeta);
        }
        return this;
    }

    public void registerEvents() {
        if (this instanceof Listener) Bukkit.getPluginManager().registerEvents((Listener) this, ServerSystem.getInstance());
    }

    public static boolean isItem(ItemStack itemStack, Material material, String displayName) {
        return itemStack != null && itemStack.getType() == material && itemStack.hasItemMeta() && itemStack.getItemMeta() != null && itemStack.getItemMeta().getDisplayName().equals(displayName);
    } 

}
