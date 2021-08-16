package nl.rubixstudios.hideandseek.game.chests;

import lombok.Getter;
import nl.rubixstudios.hideandseek.EventHAS;
import nl.rubixstudios.hideandseek.game.chests.listener.ChestOpenEventListener;
import nl.rubixstudios.hideandseek.game.chests.object.ChestObject;
import nl.rubixstudios.hideandseek.game.chests.task.ChestTask;
import nl.rubixstudios.hideandseek.util.ManagerEnabler;
import nl.rubixstudios.hideandseek.util.ParticleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ChestManager implements Listener, ManagerEnabler {

    @Getter private static ChestManager instance;

    @Getter private final List<ChestObject> chestObjectList;

    @Getter private final BukkitRunnable chestsTask;

    public ChestManager() {
        instance = this;

        this.chestObjectList = new ArrayList<>();

        this.chestsTask = new ChestTask();
        this.chestsTask.runTaskTimer(EventHAS.getInstance(), 0L, 20L);

        Bukkit.getPluginManager().registerEvents(this, EventHAS.getInstance());
        Bukkit.getPluginManager().registerEvents(new ChestOpenEventListener(), EventHAS.getInstance());
    }

    public ChestObject getChestObject(Location location) {
        return this.getChestObjectList().stream().filter(chestObject -> chestObject.getLocation().equals(location)).findFirst().orElse(null);
    }

    public boolean isAlreadyChestObject(Location location) {
        if (this.getChestObjectList().isEmpty()) return false;
        return this.getChestObjectList().stream().anyMatch(chestObject -> chestObject.getLocation().equals(location));
    }


    public void spawnFirework(Location location) {
        ParticleUtil.spawnFireWork(location, 1);
    }
}
