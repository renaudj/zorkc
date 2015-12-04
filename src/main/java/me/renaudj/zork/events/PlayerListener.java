package me.renaudj.zork.events;

/**
 * Created by renaudj on 11/22/15.
 */
public class PlayerListener implements EventListener {

    @Event
    public void onAttack(PlayerAttackEvent event) {
        System.out.println("You dealt " + event.getDamage() + " damage on " + event.getEntity().getName() + " | " + event.getEntity().getHP() + "/" + event.getEntity().getMaxHP());
    }

    @Event
    public void onPlayerDamaged(PlayerDamageEvent e) {
        System.out.println(e.getEntity().getName() + " dealt " + e.getDamage() + " damage on you wielding " + (e.getItemInHand() == null ? "nothing" : e.getItemInHand().getName()) + " | " + e.getPlayer().getHP() + "/" + e.getPlayer().getMaxHP());
    }
}
