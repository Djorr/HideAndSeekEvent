package nl.rubixstudios.hideandseek.game.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WorldBorderListener implements Listener {

    @EventHandler
    public void onFlight(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SPECTATOR) return;

        if (isInsideBorder(player.getLocation())) return;
        event.setTo(worldBorderCenter(player.getLocation()));
    }

    private boolean isInsideBorder(Location location) {
        final WorldBorder worldBorder = location.getWorld().getWorldBorder();
        final Location center = worldBorder.getCenter();
        final double radius = worldBorder.getSize() / 2d;
        return Math.abs(location.getX() - center.getX()) < radius
                && Math.abs(location.getZ() - center.getZ()) < radius;
    }

    private Location worldBorderCenter(Location location) {
        final WorldBorder worldBorder = location.getWorld().getWorldBorder();
        final Location center = worldBorder.getCenter();
        center.setY(100D);
        return center;
    }
}
