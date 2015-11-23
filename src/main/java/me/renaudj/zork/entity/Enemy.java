package me.renaudj.zork.entity;

/**
 * Created by renaudj on 11/17/15.
 */
public class Enemy extends Character {
    public Enemy(String name, int maxHp, String description) {
        super(name, maxHp, description);
    }

    public void onDamage(EntityLiving c) {
        super.onDamage(c);
        if (getHP() > 0)
            attack(c);
    }
}
