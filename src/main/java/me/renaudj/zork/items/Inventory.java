package me.renaudj.zork.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by renaudj on 11/20/15.
 */
public class Inventory {
    private HashMap<InventorySlotType, List<Item>> items;

    public Inventory() {
        this.items = new HashMap<InventorySlotType, List<Item>>();
        for (InventorySlotType slt : InventorySlotType.values()) {
            this.items.put(slt, new ArrayList<Item>());
        }
    }

    public Inventory(HashMap<InventorySlotType, List<Item>> items) {
        this.items = items;
    }

    public void addItem(Item i) {
        List<Item> itemsl = this.items.get(InventorySlotType.INVENTORY);
        itemsl.add(i);
        items.put(InventorySlotType.INVENTORY, itemsl);
    }

    public void removeItem(Item i) {
        for (InventorySlotType slt : InventorySlotType.values()) {
            if (getSlot(slt).contains(i)) {
                List<Item> its = getSlot(slt);
                its.remove(i);
                items.put(slt, its);
            }
        }
    }

    public void removeItem(String item) {
        for (Item it : getFullInventory()) {
            if (it.getName().equalsIgnoreCase(item)) {
                removeItem(it);
            }
        }
    }

    public List<Item> getSlot(InventorySlotType slot) {
        return items.get(slot);
    }

    public List<Item> getFullInventory() {
        List<Item> full = new ArrayList<Item>();
        for (List<Item> it : items.values()) {
            full.addAll(it);
        }
        return full;
    }

    public void equip(InventorySlotType slot, Item i) {
        List<Item> itemsl = items.get(slot);
        if (itemsl.size() > 0) {
            addItem(itemsl.get(0));
            itemsl.remove(0);
            removeItem(i);
            itemsl.add(i);
            items.put(slot, itemsl);
        } else {
            removeItem(i);
            itemsl.add(i);
            items.put(slot, itemsl);
        }
    }

    public void unequipAll() {
        for (InventorySlotType slt : InventorySlotType.values()) {
            if (!slt.equals(InventorySlotType.INVENTORY)) {
                unequip(slt);
            }
        }
    }

    public void unequip(InventorySlotType slot) {
        List<Item> items = getSlot(slot);
        List<Item> inventory = getSlot(InventorySlotType.INVENTORY);
        inventory.addAll(items);
        items.clear();
        this.items.put(InventorySlotType.INVENTORY, inventory);
        this.items.put(slot, items);
    }

    public Item getItem(String name) {
        for (Item i : getFullInventory()) {
            if (i.getName().equalsIgnoreCase(name))
                return i;
        }
        return null;
    }

    public boolean hasItem(String name) {
        for (Item i : getFullInventory()) {
            if (i.getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public Item getItemInSlot(InventorySlotType slot) {
        if (items.get(slot).size() == 1 && !slot.equals(InventorySlotType.INVENTORY)) {
            return items.get(slot).get(0);
        }
        return null;
    }
}
