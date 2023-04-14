/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;

import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author miche
 */
public class AccessAccountDAO implements IAccessAccount{

    @Override
    public int addAccessAccount(AccessAccount accessAccount, String idUsuarioProfesor, String idUsuarioEstudiante) throws SQLException {
    int result;
        String query = "insert into cuenta_acceso(correo_institucional, idUsuarioProfesorCuenta, idUsuarioEstudianteCuenta, privilegios) values(?,?,?,?,?)";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, accessAccount.getInstitutionalEMail());
        statement.setString(2, idUsuarioProfesor);
        statement.setString(3, accessAccount.getPassword());
        statement.setString(4, idUsuarioEstudiante);
        statement.setInt(5, accessAccount.getPrivileges());
        
        result = statement.executeUpdate();
        
        dataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public AccessAccount getAccessAccount() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AccessAccount> getAllAccessAccounts() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
