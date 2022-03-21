package net.narwell.systemop.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class MySQLD {


    private HikariDataSource dataSource;

    public MySQLD(final Plugin main, final String tableStatement) {
        init(main);
        //create(tableStatement);
    }

    
    public void init(final Plugin main) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        HikariConfig hikari = new HikariConfig();

        hikari.setJdbcUrl("jdbc:mysql://" + main.getConfig().getString("Data.address") + ":" + main.getConfig().getString("Data.port") + "/" + main.getConfig().getString("Data.database"));
        hikari.setDriverClassName("com.mysql.jdbc.Driver");
        hikari.setUsername(main.getConfig().getString("Data.username"));
        hikari.setPassword(main.getConfig().getString("Data.password"));
        hikari.setMinimumIdle(main.getConfig().getInt("Data.pool-settings.minimum-idle"));
        hikari.setMaximumPoolSize(main.getConfig().getInt("Data.pool-settings.maximum-pool-size"));
        hikari.setConnectionTimeout(main.getConfig().getInt("Data.pool-settings.connection-timeout"));
        //hikari.setConnectionTestQuery(testQuery);

        try {
            dataSource = new HikariDataSource(hikari);
            main.getLogger().log(Level.INFO, "Plugin connected to MySQL");
        }catch (Exception e) {
            main.getLogger().log(Level.WARNING, "A problem has occurred with the plugin, I have been deactivated");
            Bukkit.getPluginManager().disablePlugin(main);
        }

    }


    public void create(final String tableStatement) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(tableStatement);

            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }


    public void close() throws SQLException {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    private void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ignored) {}

        if (ps != null)
            try {
                ps.close();
            } catch (SQLException ignored) {}

        if (res != null)
            try {
                res.close();
            } catch (SQLException ignored) {}
    }

    
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }



}
