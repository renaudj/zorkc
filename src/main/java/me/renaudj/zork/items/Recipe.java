package me.renaudj.zork.items;

import me.renaudj.zork.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by renaudj on 12/4/15.
 */
public class Recipe {
    private List<Item> requirements;
    private Item result;

    public Recipe(List<Item> req, Item res) {
        requirements = req;
        result = res;
    }

    public List<Item> getRequirements() {
        return requirements;
    }

    public void craft(Player p) {
        if (hasRequiredItems(p)) {
            removeRequiredItems(p);
            p.getInventory().addItem(result);
            System.out.println("Crafted " + result.getName() + ".");
        } else {
            String out = "You still need";
            String req = "";
            for (Item i : getRequiredItems(p)) {
                req += ", " + i.getName();
            }
            out += req.substring(1);
            System.out.println(out);
        }
    }

    public List<Item> getRequiredItems(Player p) {
        List<Item> temp = new ArrayList<Item>();
        temp.addAll(requirements);

        Iterator<Item> iter = temp.iterator();

        while (iter.hasNext()) {
            Item i = iter.next();
            if (p.getInventory().hasItem(i.getName())) {
                iter.remove();
            }
        }
        return temp;
    }

    public boolean hasRequiredItems(Player p) {
        return getRequiredItems(p).size() == 0;
    }

    public void removeRequiredItems(Player p) {
        for (Item i : requirements) {
            p.getInventory().removeItem(i.getName());
        }
    }
}
