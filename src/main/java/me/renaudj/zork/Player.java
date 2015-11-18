package me.renaudj.zork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renaudj on 11/17/15.
 */
public class Player {
    private List<Item> inventory;
    private Room currentRoom;
    private Object currentView = null;
    private int hp;
    private Item leftHand = null;
    private Item rightHand = null;

    public Player() {
        this.inventory = new ArrayList<Item>();
        this.hp = 100;
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

    public int getHP() {
        return this.hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public Object getCurrentView() {
        return this.currentView;
    }

    public void setCurrentView(Object view) {
        this.currentView = view;
    }

    public List<Item> getInventory() {
        return this.inventory;
    }

    public void addItem(Item i) {
        inventory.add(i);
    }

    public void removeItem(String name) {
        if (hasItem(name)) {
            for (Item i : inventory) {
                if (i.getName().equalsIgnoreCase(name))
                    inventory.remove(i);
            }
        }
    }

    public void removeItem(Item i) {
        inventory.remove(i);
    }

    public Item getItem(String name) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(name))
                return i;
        }
        return null;
    }

    public boolean hasItem(String name) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public void goToRoom(Room room) {
        setCurrentRoom(room);
        setCurrentView(room);
        System.out.println(room.getDescription());
        if (room.hasItems()) {
            System.out.print("You see");
            for (Item i : room.getItems()) {
                String o = ", a " + i.getName();
                System.out.print(o.substring(1));
            }
            System.out.println();
        } else
            System.out.println("There's nothing interesting here.");
        if (room.hasCharacters()) {
            for (Character i : room.getCharacters()) {
                System.out.println(i.getName() + " is here.");
                System.out.println(i.getDescription());
            }
            System.out.println();
        }
    }

    public void attack(Character c) {
        if (getRightHand() != null || getLeftHand() != null) {
            if (getRightHand() != null)
                if (getRightHand() instanceof Weapon) {
                    c.setHp(c.getHp() - ((Weapon) getRightHand()).getPower());
                    c.getRightHand().setDurability(c.getRightHand().getDurability() - 1);
                } else {
                    c.setHp(c.getHp() - 1);
                }
            if (getLeftHand() != null)
                if (getLeftHand() instanceof Weapon) {
                    c.setHp(c.getHp() - ((Weapon) getLeftHand()).getPower());
                    c.getLeftHand().setDurability(c.getLeftHand().getDurability() - 1);
                } else {
                    c.setHp(c.getHp() - 1);
                }
        } else {
            c.setHp(c.getHp() - 1);
        }
        c.onDamage(this);
    }
}
