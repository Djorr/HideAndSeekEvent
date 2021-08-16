package nl.rubixstudios.hideandseek.game.chests.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.rubixstudios.hideandseek.util.ParticleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class ChestObject {

    private final Location location;

    @Setter private long timeFirework;
    private final long timeClicked;

    private final int secondsBeforeOpening;
    private final int secondsBeforeDespawn;

    @Setter private boolean opened;
    @Setter private boolean done;


    public ChestObject(Location location) {
        this.location = location;

        this.timeFirework = System.currentTimeMillis();
        this.timeClicked = System.currentTimeMillis();

        this.secondsBeforeOpening = 10;
        this.secondsBeforeDespawn = 25;

    }

    public Location getChestLoc() {
        return new Location(
                this.location.getWorld(),
                this.location.getX() + 0.5,
                this.location.getY() + 0.5,
                this.location.getZ() + 0.5,
                this.location.getYaw(),
                this.location.getPitch());
    }

    public void dropRandomItems() {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "randomdrop " + this.locationToString(this.getChestLoc()));
    }

    public String locationToString(Location location) {
        if(location == null) return null;

        String worldName = location.getWorld().getName();
        String x = Double.toString(Math.round(location.getX() * 1000D) / 1000D);
        String y = Double.toString(Math.round(location.getY() * 1000D) / 1000D);
        String z = Double.toString(Math.round(location.getZ() * 1000D) / 1000D);
        String yaw = Double.toString(Math.round(location.getYaw() * 100D) / 100D);
        String pitch = Double.toString(Math.round(location.getPitch() * 100D) / 100D);

        return worldName + "|" + x + "|" + y + "|" + z + "|" + yaw + "|" + pitch;
    }
}
