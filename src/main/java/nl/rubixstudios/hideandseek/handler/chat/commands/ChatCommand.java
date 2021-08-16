package nl.rubixstudios.hideandseek.handler.chat.commands;

import nl.rubixstudios.hideandseek.EventHAS;
import nl.rubixstudios.hideandseek.game.kits.Seeker;
import nl.rubixstudios.hideandseek.handler.chat.ChatHandler;
import nl.rubixstudios.hideandseek.handler.chat.ChatType;
import nl.rubixstudios.hideandseek.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        final Player player = (Player) sender;


        if (player.hasPermission("event.staff")) {
            if (args.length == 0) {
                player.sendMessage(Color.translate("&e&lChat &8» &fUsage: &e/chat hider | seeker | spectator | staff."));
                return false;
            } else if (args.length == 1) {
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                player.getActivePotionEffects().clear();

                ChatType chatType = ChatType.getByName(args[0]);
                if (args[0].equalsIgnoreCase("hider")) {
                    ChatHandler.getInstance().addToChannel(player, ChatType.HIDERS);
                    player.setGameMode(GameMode.SURVIVAL);

                    Seeker.giveHiderInventory(player);
                } else if (args[0].equalsIgnoreCase("seeker")) {
                    ChatHandler.getInstance().addToChannel(player, ChatType.SEEKERS);
                    player.setGameMode(GameMode.SURVIVAL);

                    Seeker.giveSeekerInventory(player);
                } else if (args[0].equalsIgnoreCase("staff")) {
                    ChatHandler.getInstance().addToChannel(player, ChatType.STAFF);
                    player.setGameMode(GameMode.SPECTATOR);

                } else if (args[0].equalsIgnoreCase("spectator")) {
                    ChatHandler.getInstance().addToChannel(player, ChatType.SPECTATOR);
                    player.setGameMode(GameMode.SPECTATOR);

                    this.changePlayerToSpectator(player);
                } else {
                    player.sendMessage("Wrong input! hider|seeker|staff|spectator");
                }

                player.sendMessage(Color.translate("&e&lChat &8» &fSuccesfully joined chat <chat>&f."
                        .replace("<chat>", chatType.getIdentifier())
                ));
            }
        }

        return true;
    }

    private void changePlayerToSpectator(Player player) {
        if (ChatHandler.getInstance().getPlayerObject(player).getChatType() == ChatType.HIDERS) {
            ChatHandler.getInstance().addToChannel(player, ChatType.SPECTATOR);
        }

        player.setGameMode(GameMode.SPECTATOR);
    }
}
