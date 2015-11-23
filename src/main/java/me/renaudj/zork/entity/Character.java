package me.renaudj.zork.entity;

import me.renaudj.zork.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Character extends EntityLiving {

    private String description;
    private List<Item> deathDrops;

    public Character(String name, int maxHp, String description) {
        super(name, maxHp);
        this.description = description;
        this.deathDrops = new ArrayList<Item>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getDeathDrops() {
        return deathDrops;
    }

    public void setDeathDrops(List<Item> deathDrops) {
        this.deathDrops = deathDrops;
    }

    public void onDamage(EntityLiving p) {
        super.onDamage(p);
    }

    public void onDeath(EntityLiving p) {
        if (getRightHand() != null) {
            deathDrops.add(getRightHand());
        }
        if (getLeftHand() != null) {
            deathDrops.add(getLeftHand());
        }
        for (Item i : getDeathDrops()) {
            ((Player) p).getCurrentRoom().addItem(i);
        }
        if (getDeathDrops().size() > 0) {
            System.out.print(getName() + " dropped");
            for (Item i : ((Player) p).getCurrentRoom().getItems()) {
                String o = ", a " + i.getName();
                System.out.print(o.substring(1));
            }
            System.out.println();
        } else {
            System.out.println(getName() + " didn't have any items.");
        }
        ((Player) p).getCurrentRoom().removeCharacter(this);
    }
}
