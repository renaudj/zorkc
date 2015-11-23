package me.renaudj.zork.items;

import java.util.Arrays;
import java.util.List;

/**
 * Created by renaudj on 11/20/15.
 */
public enum InventorySlotType {
    RIGHT_HAND("right", "RIGHT", "RIGHT_HAND"),
    LEFT_HAND("left"),
    HELMET("hat"),
    CHESTPLATE,
    GLOVE,
    LEGGINGS("pants"),
    BOOTS("shoes"),
    INVENTORY;

    private final List<String> aliases;

    InventorySlotType(String... aliases) {
        this.aliases = Arrays.asList(aliases);
    }

    public boolean isAlias(String al) {
        return aliases.contains(al);
    }

    public List<String> getAliases() {
        return aliases;
    }


}
