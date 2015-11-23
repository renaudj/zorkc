package me.renaudj.zork.items;

import me.renaudj.zork.entity.EntityLiving;

/**
 * Created by renaudj on 11/16/15.
 */
public class Weapon extends Item {
    private int power;
    private int precision;

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
