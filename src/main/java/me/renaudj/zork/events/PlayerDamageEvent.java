package me.renaudj.zork.events;

import me.renaudj.zork.entity.EntityLiving;
import me.renaudj.zork.entity.Player;
import me.renaudj.zork.items.Item;

/**
 * Created by renaudj on 11/22/15.
 */
public class PlayerDamageEvent implements IEvent {
    private int damage;
    private EntityLiving entity;
    private Item itemInHand;
    private Player player;

    public PlayerDamageEvent(Player player, int damage, EntityLiving entity, Item itemInHand) {
        this.player = player;
        this.damage = damage;
        this.entity = entity;
        this.itemInHand = itemInHand;
    }

    public int getDamage() {
        return damage;
    }

    public Player getPlayer() {
        return this.player;
    }

    public EntityLiving getEntity() {
        return entity;
    }

    public Item getItemInHand() {
        return itemInHand;
    }
}
