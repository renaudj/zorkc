package me.renaudj.zork.items;

import me.renaudj.zork.entity.Player;

/**
 * Created by renaudj on 11/20/15.
 */
public class Food extends Item {
    private int hpBonus;

    public Food(String name, int weight, int hpBonus, String description) {
        super(name, 0, weight, description);
        this.hpBonus = hpBonus;
    }

    public int getHPBonus() {
        return hpBonus;
    }

    public boolean use(Player p) {
        p.setHP(p.getHP() + getHPBonus());
        if (p.getHP() > p.getMaxHP()) {
            p.setHP(p.getMaxHP());
            System.out.println("You've gained " + getHPBonus() + " HP");
            return true;
        }
        return true;
    }
}
