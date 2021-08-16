package nl.rubixstudios.hideandseek;

import lombok.Getter;
import nl.rubixstudios.hideandseek.game.GameManager;
import nl.rubixstudios.hideandseek.game.chests.ChestManager;
import nl.rubixstudios.hideandseek.handler.manager.Handler;
import nl.rubixstudios.hideandseek.handler.manager.HandlerManager;
import nl.rubixstudios.hideandseek.util.Color;
import nl.rubixstudios.hideandseek.util.ManagerEnabler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

@Getter
public class EventHAS extends JavaPlugin {

    @Getter private static EventHAS instance;

    private HandlerManager handlerManager;
    private GameManager gameManager;
    private ChestManager chestManager;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        try {
            this.setupManagers();
            this.setupHandlers();
        } catch(Exception e) {
            this.log("   &fError occurred while enabling HideAndSeek. Error:");
            this.log("");

            e.printStackTrace();

            this.log("");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        this.disableManagers();

        Bukkit.getServicesManager().unregisterAll(this);
    }

    private void disableManagers() {
        this.handlerManager.disable();
    }

    private void setupManagers() throws Exception {
        for(Field field : this.getClass().getDeclaredFields()) {
            if(!ManagerEnabler.class.isAssignableFrom(field.getType())) continue;

            field.setAccessible(true);

            Constructor<?> constructor = field.getType().getDeclaredConstructor();
            field.set(this, constructor.newInstance());
        }
    }



    private void setupHandlers() throws Exception {
        for(Field field : this.getClass().getDeclaredFields()) {
            if(field.getType().getSuperclass() != Handler.class) continue;

            field.setAccessible(true);
            field.set(this, this.handlerManager.getHandler(field.getType()));
        }
    }

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage(Color.translate(message));
    }

    public ClassLoader getPluginClassLoader() {
        return this.getClassLoader();
    }
}
