package mx.uv.fei.sspger.dataaccess;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataBaseManager{
    private Connection connection;
    private final String DATABASE_NAME = "spger-database-2023.mysql.database.azure.com";
    private final String DATABASE_USER = "ikariscraft";
    private final String DATABASE_PASSWORD = "Mario_Nara_Mich";

    public DataBaseManager() {
    }
    
    public Connection getConnection() throws SQLException{
        connect();
        return connection;
    }
    
    private void connect() throws SQLException{
        connection = DriverManager.getConnection(DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD);
    }
    
    public void closeConnection(){
        if(connection != null){
            try{
                if(!connection.isClosed()){
                    connection.close();
                }
            } catch(SQLException exception){
                Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
    }
    
}
