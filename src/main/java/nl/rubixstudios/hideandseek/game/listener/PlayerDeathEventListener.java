package nl.rubixstudios.hideandseek.game.listener;

import nl.rubixstudios.hideandseek.game.GameManager;
import nl.rubixstudios.hideandseek.handler.chat.ChatHandler;
import nl.rubixstudios.hideandseek.handler.chat.ChatType;
import nl.rubixstudios.hideandseek.handler.chat.object.PlayerObject;
import nl.rubixstudios.hideandseek.util.Color;
import nl.rubixstudios.hideandseek.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.stream.Collectors;

public class PlayerDeathEventListener implements Listener {

    private final ChatHandler chatHandler;

    public PlayerDeathEventListener() {
        this.chatHandler = ChatHandler.getInstance();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);

        final Player hider = event.getEntity();
        final Player seeker = hider.getKiller();

        hider.getInventory().clear();
        event.getDrops().clear();

        int hiders = ChatHandler.getInstance().getPlayersByChatType(ChatType.HIDERS).size();
        if (ChatHandler.getInstance().getPlayersByChatType(ChatType.HIDERS).isEmpty()) {
            hiders = 0;
        }

        chatHandler.getPlayerObjects().remove(hider.getUniqueId());
        if (seeker != null) {
            final String message = Color.translate("&d<seeker> &fheeft &e<hider> &fvermoord! &8(&c<size> hiders over!&8)"
                    .replace("<seeker>", seeker.getName())
                    .replace("<hider>", hider.getName())
                    .replace("<size>", "" + hiders)
            );
            Message.sendMessageToAllPlayer(message);
        }

        hider.kickPlayer(Color.translate("&b&lAjjjj... jammer, volgende keer beter!"));

        GameManager.getInstance().checkIfThereIsAWinner();
    }
}
