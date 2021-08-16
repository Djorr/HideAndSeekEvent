package nl.rubixstudios.hideandseek.util;

import org.bukkit.Bukkit;

public class Message {

    public static void sendMessageToAllPlayer(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(message));
    }
}
