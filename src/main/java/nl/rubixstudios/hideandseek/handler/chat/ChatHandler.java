package nl.rubixstudios.hideandseek.handler.chat;

import lombok.Getter;
import nl.rubixstudios.hideandseek.EventHAS;
import nl.rubixstudios.hideandseek.handler.chat.commands.ChatCommand;
import nl.rubixstudios.hideandseek.handler.chat.object.PlayerObject;
import nl.rubixstudios.hideandseek.handler.manager.Handler;
import nl.rubixstudios.hideandseek.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class ChatHandler extends Handler implements Listener {

    @Getter public static ChatHandler instance;

    private final Map<UUID, PlayerObject> playerObjects;

    public ChatHandler() {
        instance = this;

        this.playerObjects = new HashMap<>();

        EventHAS.getInstance().getCommand("chat").setExecutor(new ChatCommand());
    }

    public void addToChannel(Player player, ChatType chatType) {
        PlayerObject playerObject;
        if (this.isAlreadyPlayerObject(player)) {
            playerObject = this.getPlayerObject(player);
            playerObject.setChatType(chatType);
            this.playerObjects.put(player.getUniqueId(), playerObject);
        } else {
            playerObject = this.createObject(player);
            playerObject.setChatType(chatType);
            this.playerObjects.put(player.getUniqueId(), playerObject);
        }
    }

    public List<PlayerObject> getList(ChatType chatType) {
        return ChatHandler.getInstance().getPlayerObjects().values().stream().filter(playerObject -> playerObject.getChatType().equals(chatType)).collect(Collectors.toList());
    }

    private boolean isAlreadyPlayerObject(Player player) {
        return this.playerObjects.containsKey(player.getUniqueId());
    }

    private PlayerObject createObject(Player player) {
        return new PlayerObject(player.getUniqueId());
    }

    public PlayerObject getPlayerObject(Player player) {
        return this.playerObjects.get(player.getUniqueId());
    }

    public List<PlayerObject> getPlayersByChatType(ChatType chatType) {
        return this.getPlayerObjects().values().stream().filter(playerObject -> playerObject.getChatType().equals(chatType)).collect(Collectors.toList());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        final Player player = event.getPlayer();
        if (player == null) return;

        final String message = event.getMessage();
        if (message == null) return;

        final ChatType chatType = this.getPlayerObject(player).getChatType();
        if (chatType == null) return;

        if (Bukkit.getOnlinePlayers().isEmpty()) {
            final String formatMessage = Color.translate("&f<name> &8» &7<message>"
                    .replace("<name>", player.getName())
                    .replace("<message>", message)
            );
            player.sendMessage(formatMessage);
        }

        Bukkit.getOnlinePlayers().forEach(recipient -> {
            if (recipient == null) return;

            final ChatType pChatType = this.getPlayerObject(recipient).getChatType();
            if (pChatType != chatType) return;

            final String formatMessage = Color.translate("&f<name> &8» &7<message>"
                    .replace("<name>", player.getName())
                    .replace("<message>", message)
            );
            recipient.sendMessage(formatMessage);
        });
    }




}
