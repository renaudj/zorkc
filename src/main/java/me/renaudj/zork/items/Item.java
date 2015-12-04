package me.renaudj.zork.items;

import me.renaudj.zork.Ability.IAbility;
import me.renaudj.zork.entity.EntityLiving;
import me.renaudj.zork.entity.Player;

public class Item {
    private String name;
    private int durability;
    private int maxDurability;
    private int weight;
    private String description;
    private IAbility ability;

    /**
     * A generic Item, can be put into containers or inventories.
     *
     * @param name          Item's name
     * @param maxDurability How many times the item can be used before it breaks
     * @param weight        Weight for inventory limitation
     * @param description   Item description
     */

    public Item(String name, int maxDurability, int weight, String description) {
        this.name = name;
        this.maxDurability = maxDurability;
        this.durability = maxDurability;
        this.weight = weight;
        this.description = description;
        this.ability = new IAbility() {
            public void activate(EntityLiving entityLiving) {
                return;
            }
        };
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }

    public IAbility getAbility() {
        return this.ability;
    }

    public void setAbility(IAbility ability) {
        this.ability = ability;
    }

    public int getWeight() {
        return weight;
    }

    public boolean use(Player p) {
        ability.activate(p);
        return true;
    }
}
