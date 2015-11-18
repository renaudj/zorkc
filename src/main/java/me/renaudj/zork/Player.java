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
            System.out.println("There's nothing intersting here.");
    }
}
