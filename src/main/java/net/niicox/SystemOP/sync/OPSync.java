package net.niicox.SystemOP.sync;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import net.niicox.SystemOP.Operator;
import net.niicox.SystemOP.database.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class OPSync {
    private Operator main;

    private Statement statement;


    public OPSync(Operator main) {
        this.main = main;
        try {
            statement = main.getDatabase().getConnection().createStatement();
        } catch (SQLException e) {
            main.getLogger().log(Level.WARNING, "Se produjo un error en el constructor OpSync ", e.getErrorCode());
        }
    }

    public void check(){
        try{
            ResultSet rs = this.statement.executeQuery(this.main.getConfig().getString("application.opSync.taskSelect"));
            if (rs.last() && rs.getInt(this.main.getConfig().getString("application.opSync.taskColumID")) != this.main.getConfig().getInt("application.opSync.currentRowSize") && rs.getInt(this.main.getConfig().getString("application.opSync.taskColumID")) > this.main.getConfig().getInt("application.opSync.currentRowSize")) {
                this.main.getConfig().set("application.opSync.currentRowSize", Integer.valueOf(rs.getInt(this.main.getConfig().getString("application.opSync.taskColumID"))));
                this.main.saveConfig();

                if(rs.getString(this.main.getConfig().getString("application.opSync.taskColumServer")).equals(this.main.getConfig().getString("application.modalidad"))){
                    if(this.main.getConfig().getString("application.world").equals(rs.getString(this.main.getConfig().getString("application.opSync.taskColumServerWorld")))){
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "op " + rs.getString(this.main.getConfig().getString("application.opSync.taskColumPlayer")));
                    }else{
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "op " + rs.getString(this.main.getConfig().getString("application.opSync.taskColumPlayer")));
                    }
                }
            }
            //rs.beforeFirst();
        }catch(SQLException e){
            main.getLogger().log(Level.WARNING, "Se produjo un error al intentar verificar las tablas..", e.getErrorCode());
        }

    }




    /*
    public OPSync(Operator main) {

        this.main = main;

        try {
            statement = main.getDatabase().getConnection().createStatement();

        } catch (SQLException e) {
            main.getLogger().log(Level.WARNING, "Se produjo un error en el constructor OpSync ", e.getErrorCode());
        }
    }

    public void check(){

        main.getLogger().log(Level.WARNING, "B");

    }*/
}



/*
    public OPSync(Operator main) {
        this.main = main;
        try {
            this.statement = main.getDatabase().getConnection().createStatement();
        } catch (SQLException e) {
            main.getLogger().log(Level.WARNING, "Se produjo un error en el constructor OpSync ", e.getErrorCode());
        }
    }

    public void check(){
        try{
            ResultSet rs = this.statement.executeQuery(this.main.getConfig().getString("application.opSync.taskSelect"));
            if (rs.last() &&
                    rs.getInt(this.main.getConfig().getString("application.opSync.taskColumID")) != this.main.getConfig().getInt("application.opSync.currentRowSize") && rs.getInt(this.main.getConfig().getString("application.opSync.taskColumID")) > this.main.getConfig().getInt("application.opSync.currentRowSize")) {
                this.main.getConfig().set("application.opSync.currentRowSize", Integer.valueOf(rs.getInt(this.main.getConfig().getString("application.opSync.taskColumID"))));
                this.main.saveConfig();

                if(rs.getString(this.main.getConfig().getString("application.opSync.taskColumServer")).equals(this.main.getConfig().getString("application.modalidad"))){
                    if(this.main.getConfig().getString("application.world").equals(rs.getString(this.main.getConfig().getString("application.opSync.taskColumServerWorld")))){
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "op " + rs.getString(this.main.getConfig().getString("application.opSync.taskColumPlayer")));
                    }else{
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "op " + rs.getString(this.main.getConfig().getString("application.opSync.taskColumPlayer")));
                    }
                }
            }
            rs.beforeFirst();
        }catch(SQLException e){
            main.getLogger().log(Level.WARNING, "Se produjo un error al intentar verificar las tablas..", e.getErrorCode());
        }

    }
}*/

