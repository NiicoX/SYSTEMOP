package net.niicox.SystemOP;

import net.niicox.SystemOP.database.MySQL;
import net.niicox.SystemOP.sync.OPSync;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.logging.Level;

public class Operator extends JavaPlugin {
    private MySQL database;

    private OPSync opSync;

    public void onEnable(){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&'," "));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6        _   _ _ _         __  __"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6       | \\ | (_|_) ___ ___\\ \\/ /"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6       |  \\| | | |/ __/ _ \\\\  / "));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6       | |\\  | | | (_| (_) /  \\ "));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6       |_| \\_|_|_|\\___\\___/_/\\_\\"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b          ADDON &aSystemOP v2.0-SNAPSHOT"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b          Developer &aNiicoX"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', " "));

        saveDefaultConfig();

        this.database = new MySQL(this);
        this.opSync = new OPSync(this);

        (new BukkitRunnable() {
            public void run() {
                opSync.check();
            }
        }).runTaskTimer((Plugin)this, getConfig().getLong("application.task"), getConfig().getLong("application.task"));

/*        (new BukkitRunnable() {
            public void run() {
                try {
                    Operator.this.opSync.check();
                } catch (SQLException e) {
                  Operator.this.getLogger().log(Level.WARNING, "Se produjo un error al intentar verificar las tablas..", e.getErrorCode());
                }
            }
        }).runTaskTimer((Plugin)this, getConfig().getLong("application.task"), getConfig().getLong("application.task"));
        */
    }

    public void onDisable() {
        try {
            getDatabase().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public MySQL getDatabase() {
        return this.database;
    }

    public OPSync getOpSync() {
        return this.opSync;
    }

}