package nl.rubixstudios.hideandseek.game.listener;

import nl.rubixstudios.hideandseek.game.GameManager;
import nl.rubixstudios.hideandseek.handler.chat.ChatHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ChatHandler.getInstance().getPlayerObjects().remove(event.getPlayer().getUniqueId());

        GameManager.getInstance().checkIfThereIsAWinner();
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        ChatHandler.getInstance().getPlayerObjects().remove(event.getPlayer().getUniqueId());

        GameManager.getInstance().checkIfThereIsAWinner();
    }

    @EventHandler
    public void arrowEvent(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            arrow.remove();
        }
    }
}
