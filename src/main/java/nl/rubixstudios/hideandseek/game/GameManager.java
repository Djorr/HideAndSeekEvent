package nl.rubixstudios.hideandseek.game;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import nl.rubixstudios.hideandseek.EventHAS;
import nl.rubixstudios.hideandseek.game.command.ToggleGameCommand;
import nl.rubixstudios.hideandseek.game.listener.*;
import nl.rubixstudios.hideandseek.handler.chat.ChatHandler;
import nl.rubixstudios.hideandseek.handler.chat.ChatType;
import nl.rubixstudios.hideandseek.handler.chat.commands.ChatCommand;
import nl.rubixstudios.hideandseek.handler.chat.object.PlayerObject;
import nl.rubixstudios.hideandseek.util.Color;
import nl.rubixstudios.hideandseek.util.ManagerEnabler;
import nl.rubixstudios.hideandseek.util.Message;
import nl.rubixstudios.hideandseek.util.ParticleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
public class GameManager implements Listener, ManagerEnabler {

    @Getter private static GameManager instance;

    @Getter @Setter private boolean gameActive;

    @Getter private final Map<UUID, UUID> spectatorList;

    public GameManager() {
        instance = this;

        this.spectatorList = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, EventHAS.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(), EventHAS.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerQuitEventListener(), EventHAS.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerDeathEventListener(), EventHAS.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerFallDamageListener(), EventHAS.getInstance());
        Bukkit.getPluginManager().registerEvents(new WorldBorderListener(), EventHAS.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerEventListener(), EventHAS.getInstance());
        Bukkit.getPluginManager().registerEvents(new CommandBlockListener(), EventHAS.getInstance());

        EventHAS.getInstance().getCommand("toggleGame").setExecutor(new ToggleGameCommand());
    }

    public void checkIfThereIsAWinner() {
        if (this.isGameActive()) {
            final int hidersLeft = ChatHandler.getInstance().getPlayersByChatType(ChatType.HIDERS).size();
            if (hidersLeft == 1) {
                final PlayerObject playerObject = ChatHandler.getInstance().getPlayerObjects().values().stream().filter(playerObject1 -> playerObject1.getChatType() == ChatType.HIDERS).findFirst().orElse(null);
                if (playerObject == null) return;

                final Player player = Bukkit.getPlayer(playerObject.getPlayerId());

                final String message1 = Color.translate("&8&l&m                                                  ");
                final String message2 = Color.translate(" &f&lWinnaar: &d&l" + player.getName());
                final String message3 = Color.translate(" &f&lPrijs gewonnen: &b&l€50");
                final String message4 = Color.translate("&8&l&m                                                  ");
                Message.sendMessageToAllPlayer(message1);
                Message.sendMessageToAllPlayer(message2);
                Message.sendMessageToAllPlayer(message3);
                Message.sendMessageToAllPlayer(message4);

                Bukkit.getOnlinePlayers().forEach(online -> {
                    if (online == player) return;
                    online.teleport(player);
                });

                ParticleUtil.spawnFireWork(player.getLocation(), 1);
            }
        }
    }
}
