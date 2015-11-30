package me.renaudj.zork.entity;

import me.renaudj.zork.Zork;
import me.renaudj.zork.events.PlayerAttackEvent;
import me.renaudj.zork.events.PlayerDamageEvent;
import me.renaudj.zork.items.Inventory;
import me.renaudj.zork.items.Item;
import me.renaudj.zork.items.Weapon;
import me.renaudj.zork.room.Room;

/**
 * Created by renaudj on 11/17/15.
 */
public class Player extends EntityLiving {
    private Inventory inventory;
    private Room currentRoom;
    private Object currentView = null;
    private int hp;
    private int maxHp;

    public Player() {
        super("", 100);
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

    public void goToRoom(Room room) {
        if (room.getRequiredItem() != null) {
            if (!getInventory().hasItem(room.getRequiredItem().getName())) {
                System.out.println("You need a " + room.getRequiredItem().getName() + " to enter here!");
                return;
            }
        }
        getInventory().removeItem(room.getRequiredItem());
        room.setRequiredItem(null);
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
        if(getCurrentRoom().getName() !="Room0" )
        System.out.println(getCurrentRoom().getExitString());
    }

    public void attack(EntityLiving c) {
        super.attack(c);
        int damage = 0;
        Item i = null;
        if (getRightHand() != null || getLeftHand() != null) {
            if (getRightHand() != null)
                if (getRightHand() instanceof Weapon) {
                    damage = ((Weapon) getRightHand()).getPower();
                    i = (Weapon) getRightHand();
                } else {
                    damage = 1;
                }
            if (getLeftHand() != null)
                if (getLeftHand() instanceof Weapon) {
                    damage = ((Weapon) getLeftHand()).getPower();
                    i = (Weapon) getLeftHand();
                } else {
                    damage = 1;
                }
        } else {
            damage = 1;
        }
        Zork.getInstance().getEventExecutor().executeEvent(new PlayerAttackEvent(this, damage, c, i));
    }

    public void onDamage(EntityLiving p) {
        super.onDamage(p);
        int damage = 0;
        Item i = null;
        if (p.getRightHand() != null || p.getLeftHand() != null) {
            if (p.getRightHand() != null)
                i = p.getRightHand();
            if (p.getLeftHand() != null)
                i = p.getLeftHand();
            if (i instanceof Weapon) {
                damage = ((Weapon) i).getPower();
            } else {
                damage = 1;
            }
        } else {
            damage = 1;
        }
        Zork.getInstance().getEventExecutor().executeEvent(new PlayerDamageEvent(this, damage, p, i));
    }

    public void onDeath(EntityLiving p) {

    }
}
