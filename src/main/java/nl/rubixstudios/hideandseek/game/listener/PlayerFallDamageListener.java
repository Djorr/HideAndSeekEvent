package nl.rubixstudios.hideandseek.game.listener;

import nl.rubixstudios.hideandseek.handler.chat.ChatHandler;
import nl.rubixstudios.hideandseek.handler.chat.ChatType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerFallDamageListener implements Listener {

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        final Player damager = (Player) event.getDamager();

        if (!ChatHandler.getInstance().getPlayerObject(damager).getChatType().equals(ChatType.SEEKERS)) {
            event.setDamage(0D);
        }
    }
}
