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
    private final String ADD_ACCESS_ACCOUNT_QUERY = "insert into cuenta_acceso"
            + "(correo_institucional, password, privilegios, estado) values"
            + "(?, sha1(?),?,?)";
    private final String GET_ACCESS_ACCOUNT_QUERY = "SELECT * FROM "
            + "cuenta_acceso WHERE correo_institucional = ?";
    private final String GET_ALL_ACCESS_ACCOUNT = "SELECT * FROM cuenta_acceso";
    private final String UPDATE_ACCESS_ACCOUNT = "UPDATE cuenta_acceso SET "
            + "correo_institucional = ?, password = ?, privilegios = ?, "
            + "estado = ? WHERE correo_institucional = ?";
    private final String DELETE_ACCESS_ACCOUNT = "DELETE FROM cuenta_acceso "
            + "WHERE correo_institucional = ?";
    private final String DISABLE_ACCESS_ACCOUNT = "UPDATE cuenta_acceso SET "
            + "estado = ? WHERE correo_institucional = ?";
    private final String GET_ALL_ACTIVE_ACCOUNTS = "SELECT * FROM cuenta_acceso WHERE estado = 1";
    
    @Override
    public int addAccessAccount(AccessAccount accessAccount) throws SQLException{
    int result;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(ADD_ACCESS_ACCOUNT_QUERY);
        
        statement.setString(1, accessAccount.getEMail());
        statement.setString(2, accessAccount.getPassword());
        statement.setInt(3, accessAccount.getPrivileges());
        statement.setInt(4, accessAccount.getUserStatus());
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public AccessAccount getAccessAccount(String email) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(GET_ACCESS_ACCOUNT_QUERY);

        statement.setString(1,email);

        ResultSet accountResult = statement.executeQuery();
        accountResult.next();
        
        AccessAccount account = new AccessAccount();
        account.setEMail(accountResult.getString(
                "correo_institucional"));
        account.setPassword(accountResult.getString("SHA1(password)"));
        account.setPrivileges(accountResult.getInt("privilegios"));
        account.setUserStatus(accountResult.getInt("estado"));
        
        DataBaseManager.closeConnection();

        return account;
    }

    @Override
    public List<AccessAccount> getAllAccessAccounts() throws SQLException {
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet accountResult = statement.executeQuery(GET_ALL_ACCESS_ACCOUNT);
        
        List<AccessAccount> accountList = new ArrayList<>();
        
        while(accountResult.next()){
            AccessAccount account = new AccessAccount();
            account.setEMail(accountResult.getString(
                    "correo_institucional"));
            account.setPassword(accountResult.getString("SHA1(password)"));
            account.setPrivileges(accountResult.getInt(
                    "privilegios"));
            account.setUserStatus(accountResult.getInt("estado"));
            accountList.add(account);
        }
        
        DataBaseManager.closeConnection();
        
        return accountList;
    }
    
    @Override
    public int updateAccessAccount (String email, AccessAccount accessAccount) throws SQLException{
        int result;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(UPDATE_ACCESS_ACCOUNT);       
        
        statement.setString(1, accessAccount.getEMail());
        statement.setString(2, accessAccount.getPassword());
        statement.setInt(3, accessAccount.getPrivileges());
        statement.setInt(4, accessAccount.getUserStatus());
        statement.setString(5, email);
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
    
    @Override
    public int deleteAccessAccount (String email) throws SQLException{
        int result;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(DELETE_ACCESS_ACCOUNT);
        
        statement.setString(1, email);
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public int disableAccessAccount(String email) throws SQLException {
        int result;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(DISABLE_ACCESS_ACCOUNT);       
        
        statement.setInt(1, 0);
        statement.setString(2, email);
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public List<AccessAccount> getAllActiveAccounts() throws SQLException {
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet accountResult = statement.executeQuery(GET_ALL_ACTIVE_ACCOUNTS);
        
        List<AccessAccount> accountList = new ArrayList<>();
        
        while(accountResult.next()){
            AccessAccount account = new AccessAccount();
            account.setEMail(accountResult.getString(
                    "correo_institucional"));
            account.setPassword(accountResult.getString("SHA1(password)"));
            account.setPrivileges(accountResult.getInt(
                    "privilegios"));
            account.setUserStatus(accountResult.getInt("estado"));
            accountList.add(account);
        }
        
        DataBaseManager.closeConnection();
        
        return accountList;
    }

    @Override
    public List<AccessAccount> getAllDisabledAccounts() throws SQLException {
    String query = "SELECT correo_institucional, estado FROM cuenta_acceso WHERE estado = 0";
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet accountResult = statement.executeQuery(query);
        
        List<AccessAccount> accountList = new ArrayList<>();
        
        while(accountResult.next()){
            AccessAccount account = new AccessAccount();
            account.setEMail(accountResult.getString(
                    "correo_institucional"));
            account.setUserStatus(accountResult.getInt("estado"));
            accountList.add(account);
        }
        
        DataBaseManager.closeConnection();
        
        return accountList;
    }
    
}
