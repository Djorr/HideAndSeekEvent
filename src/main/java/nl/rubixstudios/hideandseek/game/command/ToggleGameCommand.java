package nl.rubixstudios.hideandseek.game.command;

import nl.rubixstudios.hideandseek.game.GameManager;
import nl.rubixstudios.hideandseek.util.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleGameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        final Player player = (Player) sender;

        if (player.isOp()) {
            if (args.length != 0) {
                player.sendMessage(Color.translate("&e&lGame &8» &fUsage: &e/toggleGame"));
                return false;
            }

            final GameManager gameManager = GameManager.getInstance();
            if (gameManager.isGameActive()) {
                gameManager.setGameActive(false);
                player.sendMessage(Color.translate("&e&lGame &8» &fGame status changed to: &c&lINACTIVE"));
            } else {
                gameManager.setGameActive(true);
                player.sendMessage(Color.translate("&e&lGame &8» &fGame status changed to: &a&lACTIVE"));
            }
        }

        return true;
    }
}
