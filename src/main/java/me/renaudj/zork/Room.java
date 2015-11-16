package me.renaudj.zork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private HashMap<Direction, Room> exits;
    private String name;
    private String description;
    private ArrayList<Item> items;
    //TODO Implement monsters
    //private ArrayList<Monster> monsters;

    public Room(String name, String description){
        this.name = name;
        this.description = description;
        this.exits = new HashMap<Direction, Room>();
        this.items = new ArrayList<Item>();
        //TODO Implement monsters
        //this.monsters = new ArrayList<Monster>();
    }

    public void addExit(Direction direction, Room room){
        this.exits.put(direction, room);
    }

    public void addItem(Item i){
        items.add(i);
    }

    public List<Item> getItems(){
        return items;
    }

    public Map<Direction, Room> getExits(){
        return exits;
    }

    public Room getExit(Direction direction){
        if(exits.containsKey(direction))
        return exits.get(direction);
        else
            return null;
    }

    public String getName(){
        return name;
    }

    public void removeItem(Item i){
        items.remove(i);
    }

    public String getDescription(){
        return description;
    }

    public boolean hasItems(){
        return items.size() > 0;
    }
}
