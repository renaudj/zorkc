package me.renaudj.zork;

import java.util.List;

public class Container extends Item{
    public List<Item> items;
    private String name;
    public Container(String name, List<Item> items){
        super(name, 0, -1, "");
        this.items = items;
        this.name = name;
    }

    public List<Item> getItems(){
        return this.items;
    }

    public void removeItem(Item i){
        items.remove(i);
    }

    public String getName(){
        return this.name;
    }

}
