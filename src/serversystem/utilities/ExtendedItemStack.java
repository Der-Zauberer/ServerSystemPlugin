package serversystem.utilities;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import serversystem.main.ServerSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ExtendedItemStack extends ItemStack {

    private Consumer<PlayerInteractEvent> interactAction;
    private Consumer<PlayerInteractEvent> leftClickAction;
    private Consumer<PlayerInteractEvent> rightClickAction;

    private static final ListenerClass listener = new ListenerClass();
    private static final ArrayList<NamespacedKey> nameSpacedKeys = new ArrayList<>();
    private static final ArrayList<ExtendedItemStack> extendedItemStacks = new ArrayList<>();

    public ExtendedItemStack(String displayName, Material material, int customModelData) {
        super(material);
        final ItemMeta itemMeta = getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.RESET + displayName);
        itemMeta.setCustomModelData(customModelData);
        setItemMeta(itemMeta);
    }

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

    public ExtendedItemStack setCustomModelData(int customModelData) {
        final ItemMeta itemMeta = getItemMeta();
        assert itemMeta != null;
        itemMeta.setCustomModelData(customModelData);
        setItemMeta(itemMeta);
        return this;
    }

    public ExtendedItemStack setLore(String string) {
        final String[] list = string.split("\n");
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

    public ExtendedItemStack addPlayerSkullMeta(Player player) {
        if (getType() == Material.PLAYER_HEAD) {
            final SkullMeta skullMeta = (SkullMeta) getItemMeta();
            assert skullMeta != null;
            skullMeta.setOwningPlayer(player);
            setItemMeta(skullMeta);
        }
        return this;
    }

    public void registerEvents() {
        if (this instanceof Listener)
            Bukkit.getPluginManager().registerEvents((Listener) this, ServerSystem.getInstance());
    }

    public void setInteractAction(Consumer<PlayerInteractEvent> interactAction) {
		this.interactAction = interactAction;
	}
    
    public Consumer<PlayerInteractEvent> getInteractAction() {
		return interactAction;
	}
    
    public void setLeftClickAction(Consumer<PlayerInteractEvent> leftClickAction) {
		this.leftClickAction = leftClickAction;
	}
    
    public Consumer<PlayerInteractEvent> getLeftClickAction() {
		return leftClickAction;
	}
    
    public void setRightClickAction(Consumer<PlayerInteractEvent> rightClickAction) {
		this.rightClickAction = rightClickAction;
	}
    
    public Consumer<PlayerInteractEvent> getRightClickAction() {
		return rightClickAction;
	}

    public static void registerItem(ExtendedItemStack itemStack) {
        extendedItemStacks.add(itemStack);
    }

    public static ArrayList<ExtendedItemStack> getItems() {
        return extendedItemStacks;
    }

    public static ArrayList<ItemStack> getItemsAsItemStack() {
        return new ArrayList<>(extendedItemStacks);
    }

    public static void registerNameSpaceKey(NamespacedKey nameSpacedKey) {
        nameSpacedKeys.add(nameSpacedKey);
    }

    public static ArrayList<NamespacedKey> getNamespacedKeys() {
        return nameSpacedKeys;
    }

    public static ExtendedItemStack getItem(ItemStack itemStack) {
        for (ExtendedItemStack item : extendedItemStacks) {
            if (itemStack != null && item.getType() == itemStack.getType() && itemStack.hasItemMeta() && itemStack.getItemMeta() != null && itemStack.getItemMeta().hasCustomModelData() && Objects.requireNonNull(item.getItemMeta()).getCustomModelData() == itemStack.getItemMeta().getCustomModelData()) {
                return item;
            }
        }
        return null;
    }

    public static boolean isItem(ItemStack itemStack, Material material, int customModelData) {
        return itemStack != null && itemStack.getType() == material && itemStack.hasItemMeta() && itemStack.getItemMeta() != null && itemStack.getItemMeta().hasCustomModelData() && itemStack.getItemMeta().getCustomModelData() == customModelData;
    }

    public static ListenerClass getListener() {
        return listener;
    }

    private static class ListenerClass implements Listener {

        @EventHandler
        public static void onPlayerJoin(PlayerJoinEvent event) {
            event.getPlayer().discoverRecipes(ExtendedItemStack.getNamespacedKeys());
        }

        @EventHandler
        public static void onEntityTarget(EntityTargetEvent event) {
            if (event.getEntityType() == EntityType.PIG && event.getTarget() instanceof Player) {
                ItemStack mainItem = ((Player) event.getTarget()).getInventory().getItemInMainHand();
                ItemStack secondaryItem = ((Player) event.getTarget()).getInventory().getItemInOffHand();
                if (mainItem.getType() == Material.CARROT_ON_A_STICK && mainItem.hasItemMeta() && mainItem.getItemMeta() != null && mainItem.getItemMeta().hasCustomModelData() && mainItem.getItemMeta().getCustomModelData() != 0) {
                    event.setCancelled(true);
                } else if (secondaryItem.getType() == Material.CARROT_ON_A_STICK && secondaryItem.hasItemMeta() && Objects.requireNonNull(secondaryItem.getItemMeta()).hasCustomModelData() && secondaryItem.getItemMeta().getCustomModelData() != 0) {
                    event.setCancelled(true);
                }
            }
        }

        @EventHandler
        public static void onPlayerInteract(PlayerInteractEvent event) {
            final ExtendedItemStack itemStack;
            if ((itemStack = getItem(event.getItem())) != null) {
                if (itemStack.getInteractAction() != null) itemStack.getInteractAction().accept(event);
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (itemStack.getLeftClickAction() != null) itemStack.getLeftClickAction().accept(event);
                } else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (itemStack.getRightClickAction() != null) itemStack.getRightClickAction().accept(event);
                }
            }
        }

    }

}
