package nl.rubixstudios.hideandseek.game.chests.task;

import nl.rubixstudios.hideandseek.game.chests.ChestManager;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestTask extends BukkitRunnable {

    private final ChestManager chestManager;

    public ChestTask() {
        this.chestManager = ChestManager.getInstance();
    }

    @Override
    public void run() {
        // Handle Chest Removal
        this.handleChestRemoval();
    }

    private void spawnParticleBreak(Location location) {
        for (int i = 0; i < 5; i++) {
            final Block b = location.getWorld().getBlockAt(location);
            location.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getTypeId());
        }
    }

    private void handleChestRemoval() {
        if (this.chestManager.getChestObjectList().isEmpty()) return;

        this.chestManager.getChestObjectList().forEach(chestObject -> {
            if (chestObject.isDone()) return;
            if (chestObject.getTimeClicked() == 0) return;

            if ((System.currentTimeMillis() - chestObject.getTimeClicked()) > chestObject.getSecondsBeforeDespawn() * 1000L) {
                chestObject.setDone(true);
                return;
            }

            if ((System.currentTimeMillis() - chestObject.getTimeClicked()) > chestObject.getSecondsBeforeOpening() * 1000L && !chestObject.isOpened()) {
                chestObject.setOpened(true);

                chestObject.dropRandomItems();
                chestObject.getLocation().getBlock().setType(Material.AIR);
                this.spawnParticleBreak(chestObject.getLocation());
            }

            if ((System.currentTimeMillis() - chestObject.getTimeFirework()) > 1000L) {
                chestObject.setTimeFirework(System.currentTimeMillis());
                this.chestManager.spawnFirework(chestObject.getChestLoc());
            }
        });
    }
}
