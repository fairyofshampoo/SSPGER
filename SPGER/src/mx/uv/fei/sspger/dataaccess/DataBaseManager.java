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
    /* The atribute and methods are static because for managing the exception 
    of a transaction we need access to the connection to make a rollback 
    in the catch block, and we also need the connection for the finally 
    block to close it.
    */
    
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()){
            connect();
        }
        
        return connection;
    }

    private static void connect() throws SQLException {
        try {
            FileInputStream configFile = new FileInputStream(new File("src/mx/uv/fei/sspger/dataaccess/databaseconfig.txt"));
            Properties properties = new Properties();
            properties.load(configFile);
            configFile.close();
            String name = properties.getProperty("name");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            connection = DriverManager.getConnection(name, user, password);
        } catch (FileNotFoundException fileNotFoundException) {
            //LOGGER
            throw new SQLException("No se pudo acceder a la base de datos.");
        } catch (IOException iOException){
            //LOGGER
            throw new SQLException("Hubo un error en el sistema.");
        }
    }

    public static void closeConnection() {
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