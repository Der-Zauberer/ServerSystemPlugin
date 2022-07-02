package serversystem.utilities;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInventory {

    private boolean fixed;
    private final int height;
    private final Inventory inventory;
    private final Player player;
    private final HashMap<Integer, Consumer<InventoryClickEvent>> actions;

    private static final ListenerClass listener = new ListenerClass();
    private static final ArrayList<PlayerInventory> inventories = new ArrayList<>();

    public PlayerInventory(Player player, int height, String name) {
        this.fixed = false;
        this.height = height;
        this.player = player;
        this.inventory = Bukkit.createInventory(player, height * 9, name);
        this.actions = new HashMap<>();
    }

    public PlayerInventory setItem(int slot, ItemStack itemStack, Consumer<InventoryClickEvent> action) {
        inventory.setItem(slot, itemStack);
        actions.put(slot, action);
        return this;
    }

    public PlayerInventory setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
        actions.remove(slot);
        return this;
    }

    public PlayerInventory setItemList(List<ItemStack> items, Consumer<InventoryClickEvent> action) {
        return setItemList(items, action, null);
    }

    public PlayerInventory setItemList(List<ItemStack> items, Consumer<InventoryClickEvent> action, Consumer<InventoryClickEvent> backAction) {
        fillList(items, action, 0, backAction);
        return this;
    }

    private void fillList(List<ItemStack> items, Consumer<InventoryClickEvent> action, int position, Consumer<InventoryClickEvent> backAction) {
        clear();
        for (int i = 0; i < inventory.getSize() - 9 && i + position < items.size(); i++) {
            setItem(i, items.get(i + position), action);
        }
        if (position > inventory.getSize() - 10) {
            setItem(inventory.getSize() - 9, new ExtendedItemStack("Previous Page", Material.ARROW), event -> {
                fillList(items, action, position - (inventory.getSize() - 9), backAction);
            });
        }
        if (backAction != null) {
            setItem(inventory.getSize() - 5, new ExtendedItemStack("Back", Material.SPECTRAL_ARROW), backAction);
        }
        if (position + inventory.getSize() - 10 < items.size()) {
            setItem(inventory.getSize() - 1, new ExtendedItemStack("Next Page", Material.ARROW), event -> {
                fillList(items, action, position + (inventory.getSize() - 9), backAction);
            });
        }
    }

    public PlayerInventory open() {
        player.openInventory(inventory);
        inventories.add(this);
        return this;
    }

    public PlayerInventory close() {
        player.closeInventory();
        inventories.remove(this);
        return this;
    }

    public PlayerInventory clear() {
        actions.clear();
        inventory.clear();
        return this;
    }

    public PlayerInventory setFixed(boolean fixed) {
        this.fixed = fixed;
        return this;
    }

    public boolean isFixed() {
        return fixed;
    }

    public int getHeight() {
        return height;
    }

    public PlayerInventory setInventoryClickAction(int slot, Consumer<InventoryClickEvent> action) {
        actions.put(slot, action);
        return this;
    }

    public Consumer<InventoryClickEvent> getInventoryClickAction(int slot) {
        return actions.get(slot);
    }

    public static ListenerClass getListener() {
        return listener;
    }

    private static class ListenerClass implements Listener {

        @EventHandler
        public static void onInventoryClicked(InventoryClickEvent event) {
            try {
                if (event.getCurrentItem() == null) return;
                for (PlayerInventory inventory : inventories) {
                    if (event.getInventory().equals(inventory.inventory)) {
                        if (inventory.actions.containsKey(event.getSlot()))
                            inventory.actions.get(event.getSlot()).accept(event);
                        if (inventory.isFixed()) event.setCancelled(true);
                        return;
                    } else if (inventory.isFixed() && event.getWhoClicked().getOpenInventory().getTopInventory().equals(inventory.inventory)) {
                        event.setCancelled(true);
                        return;
                    }
                }

            } catch (ConcurrentModificationException ignored) {
            }
        }

        @EventHandler
        public static void onInventoryClosed(InventoryCloseEvent event) {
            for (PlayerInventory inventory : inventories) {
                if (event.getInventory() == inventory.inventory) {
                    inventories.remove(inventory);
                    return;
                }
            }
        }

    }

}
