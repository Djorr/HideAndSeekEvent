package nl.rubixstudios.hideandseek.game.listener;

import nl.rubixstudios.hideandseek.handler.chat.ChatHandler;
import nl.rubixstudios.hideandseek.handler.chat.ChatType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerEventListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        if (!ChatHandler.getInstance().getPlayerObject(player).getChatType().equals(ChatType.SEEKERS))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        if (!ChatHandler.getInstance().getPlayerObject(player).getChatType().equals(ChatType.SEEKERS))
            event.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN)
            event.setCancelled(true);
    }
}
