package net.niicox.SystemOP.database;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

import net.niicox.SystemOP.Operator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MySQL {
    private HikariDataSource dataSource;

    public MySQL(Operator main) {
        init(main);
    }

    public void init(Operator main) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        HikariConfig hikari = new HikariConfig();
        hikari.setJdbcUrl("jdbc:mysql://" + main.getConfig().getString("database.address") + ":" + main.getConfig().getString("database.port") + "/" + main.getConfig().getString("database.database"));
        hikari.setDriverClassName("com.mysql.jdbc.Driver");
        hikari.setUsername(main.getConfig().getString("database.username"));
        hikari.setPassword(main.getConfig().getString("database.password"));
        hikari.setMinimumIdle(main.getConfig().getInt("database.pool-settings.minimum-idle"));
        hikari.setMaximumPoolSize(main.getConfig().getInt("database.pool-settings.maximum-pool-size"));
        hikari.setConnectionTimeout(main.getConfig().getInt("database.pool-settings.connection-timeout"));
        try {
            this.dataSource = new HikariDataSource(hikari);
            main.getLogger().log(Level.INFO, "Plugin conectado a MySQL");
        }catch (Exception e) {
            Bukkit.getPluginManager().disablePlugin((Plugin)main);
        }
    }


    public void close() throws SQLException {
        if (this.dataSource != null && !this.dataSource.isClosed())
            this.dataSource.close();
    }

    private void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException sQLException) {}
        if (ps != null)
            try {
                ps.close();
            } catch (SQLException sQLException) {}
        if (res != null)
            try {
                res.close();
            } catch (SQLException sQLException) {}
    }

    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }
}