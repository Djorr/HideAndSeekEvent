package nl.rubixstudios.hideandseek.handler.manager;

import nl.rubixstudios.hideandseek.EventHAS;
import nl.rubixstudios.hideandseek.handler.chat.ChatHandler;
import nl.rubixstudios.hideandseek.util.ManagerEnabler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class HandlerManager implements ManagerEnabler {

    private final List<Handler> handlers;

    public HandlerManager() {
        this.handlers = new ArrayList<>();

        this.handlers.add(new ChatHandler());

        this.handlers.stream().filter(handler -> handler instanceof Listener).forEach(handler ->
                Bukkit.getPluginManager().registerEvents((Listener) handler, EventHAS.getInstance()));
    }

    public void disable() {
        try {
            this.handlers.forEach(Handler::disable);
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.handlers.clear();
    }

    public Handler getHandler(Class<?> clazz) {
        return this.handlers.stream().filter(handler -> handler.getClass() == clazz).findFirst().orElse(null);
    }
}
