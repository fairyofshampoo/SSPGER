package mx.uv.fei.sspger.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataBaseManager {
    private Connection connection;
    
    public Connection getConnection() throws SQLException {
        connect();
        return connection;
    }
    
    private void connect() throws SQLException {
        try {
            FileInputStream configFile = new FileInputStream(new File("src\\mx\\uv\\fei\\sspger\\dataaccess\\databaseconfig.txt"));
            Properties properties = new Properties();
            properties.load(configFile);
            configFile.close();
            String name = properties.getProperty("name");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            connection = DriverManager.getConnection(name, user, password);
    } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
        } catch (IOException iOException){
            System.out.println(iOException.getMessage());
        }
    }
    
    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}