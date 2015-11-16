package me.renaudj.zork;

public class Item {
    private String name;
    private int durability;
    private int maxDurability;
    private int weight;
    private String description;

    public Item(String name, int maxDurability, int weight, String description){
        this.name = name;
        this.maxDurability = maxDurability;
        this.durability = maxDurability;
        this.weight = weight;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getDurability() {
        return durability;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public int getWeight() {
        return weight;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }
}
