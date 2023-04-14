/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;

import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author miche
 */
public class AccessAccountDAO implements IAccessAccount{

    @Override
    public int addAccessAccount(AccessAccount accessAccount) throws SQLException {
    int result;
        String query = "insert into cuenta_acceso(correo_institucional, password, privilegios) values(?,?,?)";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, accessAccount.getInstitutionalEMail());
        statement.setString(2, accessAccount.getPassword());
        statement.setInt(3, accessAccount.getPrivileges());
        
        result = statement.executeUpdate();
        
        dataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public AccessAccount getAccessAccount(String email) throws SQLException {
        String query = "Select * from cuenta_acceso where correo_institucional = ?";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1,email);

        ResultSet accountResult = statement.executeQuery(query);
        
        AccessAccount account = new AccessAccount();
        account.setInstitutionalEMail(accountResult.getString("correo_institucional"));
        account.setPassword(accountResult.getString("password"));
        account.setPrivileges(accountResult.getInt("privilegios"));
        
        dataBaseManager.closeConnection();

        return account;
    }

    @Override
    public List<AccessAccount> getAllAccessAccounts() throws SQLException {
    String query = "SELECT * FROM cuenta_acceso";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet accountResult = statement.executeQuery(query);
        
        List<AccessAccount> accountList = new ArrayList<>();
        
        while(accountResult.next()){
            AccessAccount account = new AccessAccount();
            account.setInstitutionalEMail(accountResult.getString("correo_institucional"));
            account.setPassword(accountResult.getString("password"));
            account.setPrivileges(accountResult.getInt("privilegios"));
            accountList.add(account);
        }
        
        dataBaseManager.closeConnection();
        
        return accountList;
    }
    
}
