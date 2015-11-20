package me.renaudj.zork.entity;

import me.renaudj.zork.items.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renaudj on 11/17/15.
 */
public class Character {

    private String name;
    private int hp;
    private int maxHp;
    private String description;
    private List<Item> inventory;
    private List<Item> deathDrops;
    private Item leftHand;
    private Item rightHand;

    public Character(String name, int maxHp, String description) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.description = description;
        this.inventory = new ArrayList<Item>();
        this.deathDrops = new ArrayList<Item>();
    }

    public Item getLeftHand() {
        return leftHand;
    }

    public void setLeftHand(Item leftHand) {
        this.leftHand = leftHand;
    }

    public Item getRightHand() {
        return rightHand;
    }

    public void setRightHand(Item rightHand) {
        this.rightHand = rightHand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public List<Item> getDeathDrops() {
        return deathDrops;
    }

    public void setDeathDrops(List<Item> deathDrops) {
        this.deathDrops = deathDrops;
    }

    public void onDamage(Player p) {
        if (getHp() > 0)
            System.out.println(name + " : " + getHp() + "/" + getMaxHp() + " : Ouch!");
        else {
            onDeath(p);
        }
    }

    public void onDeath(Player p) {
        System.out.println("AAAARRRGGGHHH");
        if (getRightHand() != null) {
            deathDrops.add(getRightHand());
        }
        if (getLeftHand() != null) {
            deathDrops.add(getLeftHand());
        }
        for (Item i : getDeathDrops()) {
            p.getCurrentRoom().addItem(i);
        }
        if (getDeathDrops().size() > 0) {
            System.out.print(name + " dropped");
            for (Item i : p.getCurrentRoom().getItems()) {
                String o = ", a " + i.getName();
                System.out.print(o.substring(1));
            }
            System.out.println();
        } else {
            System.out.println(name + " didn't have any items.");
        }
        p.getCurrentRoom().removeCharacter(this);
    }
}