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

    public void setHP(int hp) {
        this.hp = hp;
    }

    public Player(){
        this.inventory = new ArrayList<Item>();
        this.hp = 100;
    }

    public void setCurrentRoom(Room room){
        this.currentRoom = room;
    }

    public void setCurrentView(Object view){
        this.currentView = view;
    }

    public int getHP(){
        return this.hp;
    }

    public Room getCurrentRoom(){
        return this.currentRoom;
    }

    public Object getCurrentView(){
        return this.currentView;
    }

    public List<Item> getInventory(){
        return this.inventory;
    }
}
