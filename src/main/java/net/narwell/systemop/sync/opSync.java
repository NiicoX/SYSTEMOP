package net.narwell.systemop.sync;

import net.narwell.systemop.SystemOP;

import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class opSync {

    private SystemOP main;

    public opSync(final SystemOP main) {
        this.main = main;
    }

    public void check() throws SQLException {

        PreparedStatement preparedStatement = SystemOP.getInstance().database().getConnection().prepareStatement(main.getConfig().getString("application.opSync.taskSelect"));
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()) { //se valida si hay resultados

            main.getLogger().log(Level.INFO, "Comprobando la tabla de 'operador' de la base de datos...");

            do {
              //tu codigo aquí llenando con datos lo que requieres...

              if (rs.getInt(main.getConfig().getString("application.opSync.taskColumID")) != main.getConfig().getInt("application.opSync.currentRowSize") && rs.getInt(main.getConfig().getString("application.opSync.taskColumID")) > main.getConfig().getInt("application.opSync.currentRowSize")) {

                  main.getLogger().log(Level.INFO, "Se encontro una nueva data de la tabla 'operador', ID; " + rs.getInt(main.getConfig().getString("application.opSync.taskColumID")) + " NICK; " + rs.getString(main.getConfig().getString("application.opSync.taskColumPlayer")));

                  main.getConfig().set("application.opSync.currentRowSize", rs.getInt(main.getConfig().getString("application.opSync.taskColumID")));
                  main.saveConfig();

                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "op " + rs.getString(main.getConfig().getString("application.opSync.taskColumPlayer")));
              }        

            } while(rs.next()); //repita mientras existan más datos

          }
        
    }



}
