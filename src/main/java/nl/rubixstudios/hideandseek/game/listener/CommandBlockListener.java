package nl.rubixstudios.hideandseek.game.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandBlockListener implements Listener {

    @EventHandler
    public void onOperator(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().isOp()) return;
        event.setCancelled(true);
    }
}
