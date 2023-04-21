package mx.uv.fei.sspger.logic.DAO;


import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.logic.AccessAccount;
import mx.uv.fei.sspger.logic.contracts.IAccessAccount;


public class AccessAccountDAO implements IAccessAccount{

    @Override
    public int addAccessAccount(AccessAccount accessAccount) throws SQLException {
    int result;
        String query = "insert into cuenta_acceso(correo_institucional, password, privilegios) values(?,?,?)";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, accessAccount.getEMail());
        statement.setString(2, accessAccount.getPassword());
        statement.setInt(3, accessAccount.getPrivileges());
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public AccessAccount getAccessAccount(String email) throws SQLException {
        String query = "SELECT * FROM cuenta_acceso WHERE correo_institucional = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setString(1,email);

        ResultSet accountResult = statement.executeQuery();
        accountResult.next();
        
        AccessAccount account = new AccessAccount();
        account.setEMail(accountResult.getString("correo_institucional"));
        account.setPassword(accountResult.getString("password"));
        account.setPrivileges(accountResult.getInt("privilegios"));
        
        DataBaseManager.closeConnection();

        return account;
    }

    @Override
    public List<AccessAccount> getAllAccessAccounts() throws SQLException {
    String query = "SELECT * FROM cuenta_acceso";
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet accountResult = statement.executeQuery(query);
        
        List<AccessAccount> accountList = new ArrayList<>();
        
        while(accountResult.next()){
            AccessAccount account = new AccessAccount();
            account.setEMail(accountResult.getString("correo_institucional"));
            account.setPassword(accountResult.getString("password"));
            account.setPrivileges(accountResult.getInt("privilegios"));
            accountList.add(account);
        }
        
        DataBaseManager.closeConnection();
        
        return accountList;
    }
    
    @Override
    public int updateAccessAccount (String email, AccessAccount accessAccount) throws SQLException{
        int result;
        String query = "UPDATE cuenta_acceso SET correo_institucional = ?, password = ?, privilegios = ? WHERE correo_institucional = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);       
        
        statement.setString(1, accessAccount.getEMail());
        statement.setString(2, accessAccount.getPassword());
        statement.setInt(3, accessAccount.getPrivileges());
        statement.setString(4, email);
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
    
    @Override
    public int deleteAccessAccount (String email) throws SQLException{
        int result;
        String query = "DELETE FROM cuenta_acceso WHERE correo_institucional = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, email);
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
    
}
