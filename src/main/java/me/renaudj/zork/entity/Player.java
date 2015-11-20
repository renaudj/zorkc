package me.renaudj.zork.entity;

import me.renaudj.zork.items.Inventory;
import me.renaudj.zork.items.InventorySlotType;
import me.renaudj.zork.items.Item;
import me.renaudj.zork.items.Weapon;
import me.renaudj.zork.room.Room;

/**
 * Created by renaudj on 11/17/15.
 */
public class Player {
    private Inventory inventory;
    private Room currentRoom;
    private Object currentView = null;
    private int hp;
    private int maxHp;

    public Player() {
        this.inventory = new Inventory();
        this.hp = 100;
        this.maxHp = 100;
    }

    public Item getLeftHand() {
        return getInventory().getItemInSlot(InventorySlotType.LEFT_HAND);
    }


    public Item getRightHand() {
        return getInventory().getItemInSlot(InventorySlotType.RIGHT_HAND);
    }

    public int getHP() {
        return this.hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public int getMaxHP() {
        return this.maxHp;
    }

    public void setMaxHP(int hp) {
        this.maxHp = hp;
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

    public Inventory getInventory() {
        return this.inventory;
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
