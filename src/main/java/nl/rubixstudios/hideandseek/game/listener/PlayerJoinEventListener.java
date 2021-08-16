package nl.rubixstudios.hideandseek.game.listener;

import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import nl.rubixstudios.hideandseek.EventHAS;
import nl.rubixstudios.hideandseek.game.GameManager;
import nl.rubixstudios.hideandseek.game.kits.Seeker;
import nl.rubixstudios.hideandseek.handler.chat.ChatHandler;
import nl.rubixstudios.hideandseek.handler.chat.ChatType;
import nl.rubixstudios.hideandseek.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class PlayerJoinEventListener implements Listener {

    private final ChatHandler chatHandler;
    private final GameManager gameManager;

    public PlayerJoinEventListener() {
        this.chatHandler = ChatHandler.getInstance();
        this.gameManager = GameManager.getInstance();
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();
        if (gameManager.isGameActive()) {
            if (player.isOp() || player.hasPermission("event.bypass")) return;
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Color.translate("&c&lJe kunt de server niet meer betreden, het event is al begonnen!"));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();


        player.setHealthScale(40D);
        player.setMaxHealth(player.getHealthScale());
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getActivePotionEffects().clear();

        if (player.getName().equals("Djorr") || player.getName().equals("AnasPlays")) {
            ChatHandler.getInstance().addToChannel(player, ChatType.SEEKERS);
            player.setGameMode(GameMode.SURVIVAL);

            Seeker.giveSeekerInventory(player);
        } else {
            ChatHandler.getInstance().addToChannel(player, ChatType.HIDERS);
            player.setGameMode(GameMode.SURVIVAL);

            Seeker.giveHiderInventory(player);
        }
    }
}
