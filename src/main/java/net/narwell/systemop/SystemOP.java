package net.narwell.systemop;

import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.narwell.systemop.database.MySQLD;
import net.narwell.systemop.sync.opSync;

public class SystemOP extends JavaPlugin {

    private MySQLD database;
    private opSync opSync;

    private static SystemOP instance;

    public static SystemOP getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        saveDefaultConfig();

        instance = this;

        database = new MySQLD(this, "");
        opSync = new opSync(this);

        new BukkitRunnable() {
            @Override
            public void run() {

                try {
                    opSync.check();
                } catch (SQLException exception) {
                    getLogger().log(Level.WARNING, "Ocurrio un error al intentar checar las tablas xd");
                    exception.printStackTrace();
                }

            }
        }.runTaskTimer(this, getConfig().getLong("application.task"), getConfig().getLong("application.task")); //Delays in ticks*/

    }


    @Override
    public void onDisable() {

        try {
            database.close();
        }catch(SQLException ex) {

        }

    }

    public MySQLD database() {
        return database;
    }
    
}
