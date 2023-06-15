package mx.uv.fei.sspger.dataaccess;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataBaseManager {
    /*
    * The atribute and methods are static because for managing the exception 
    * of a transaction we need access to the connection to make a rollback 
    * in the catch block, and we also need the connection for the finally 
    * block to close it.
    */
    
    private static Connection connection;
    private static final String URL_PROPERTY_FIELD = "url";
    private static final String USER_PROPERTY_FIELD = "user";
    private static final String PASSWORD_PROPERTY_FIELD = "password";

    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = connect();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Error en base de datos");
        }
        return connection;
    }

    private static Connection connect() throws SQLException {
        Connection newConnection = null;
        Properties properties = new DataBaseManager().getPropertiesFile();
        if (properties != null) {
            newConnection = DriverManager.getConnection(
                    properties.getProperty(URL_PROPERTY_FIELD),
                    properties.getProperty(USER_PROPERTY_FIELD),
                    properties.getProperty(PASSWORD_PROPERTY_FIELD));

        } else {
            throw new SQLException("No fue posible encontrar las credenciales de la base de datos");
        }
        return newConnection;
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
    
    private Properties getPropertiesFile() {
        Properties properties = null;
        try {
            InputStream file = new FileInputStream("src/mx/uv/fei/sspger/dataaccess/databaseconfig.properties");
            if (file != null) {
                properties = new Properties();
                properties.load(file);
            }
            file.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }
}