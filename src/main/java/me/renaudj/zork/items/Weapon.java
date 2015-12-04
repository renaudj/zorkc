package me.renaudj.zork.items;

import me.renaudj.zork.entity.EntityLiving;

/**
 * Created by renaudj on 11/16/15.
 */
public class Weapon extends Item {
    private int power;
    private int precision;

    /**
     * A weapon, can deal damage to characters and enemies
     *
     * @param name          Item's name
     * @param maxDurability How many times the item can be used before it breaks
     * @param weight        Weight for inventory limitation
     * @param description   Item description
     * @param power         Damage in HP
     * @param precision     Damage modifier, currently useless
     */
    public Weapon(String name, int maxDurability, int weight, String description, int power, int precision) {
        super(name, maxDurability, weight, description);
        this.power = power;
        this.precision = precision;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void activateAbility(EntityLiving p, EntityLiving c) {

    }
}
