package nl.rubixstudios.hideandseek.game.chests.listener;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import nl.rubixstudios.hideandseek.game.chests.ChestManager;
import nl.rubixstudios.hideandseek.game.chests.object.ChestObject;
import nl.rubixstudios.hideandseek.handler.chat.ChatHandler;
import nl.rubixstudios.hideandseek.handler.chat.ChatType;
import nl.rubixstudios.hideandseek.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChestOpenEventListener implements Listener {

    private final HeadDatabaseAPI api;
    private final ChestManager chestManager;

    public ChestOpenEventListener() {
        this.api = new HeadDatabaseAPI();
        this.chestManager = ChestManager.getInstance();
    }

    @EventHandler
    public void onOpenCustomChest(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (!event.getClickedBlock().getType().equals(Material.SKULL)) return;

        final Location chestLoc = event.getClickedBlock().getLocation();
        if (chestLoc == null) return;

        if (chestManager.getChestObjectList().stream().anyMatch(chestObject1 -> chestObject1.getLocation().equals(chestLoc))) return;

        final ChestObject chestObject = new ChestObject(chestLoc);
        this.chestManager.getChestObjectList().add(chestObject);

        ChatHandler.getInstance().getPlayerObjects().values().forEach(playerObject -> {
            if (playerObject.getChatType() != ChatType.SEEKERS) return;

            final Player seeker = Bukkit.getPlayer(playerObject.getPlayerId());
            if (seeker == null) return;

            seeker.sendMessage(Color.translate("&d&lChest &8» &e&lSeekers &fer is een chest geopend op &dx<x> z<z>&f."
                    .replace("<x>", "" + chestLoc.getX())
                    .replace("<z>", "" + chestLoc.getZ())
            ));
        });
    }
}
