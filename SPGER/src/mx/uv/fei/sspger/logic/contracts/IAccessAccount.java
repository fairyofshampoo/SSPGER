package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.AccessAccount;
import mx.uv.fei.sspger.logic.AccessAccount;


public interface IAccessAccount {
    int addAccessAccount(AccessAccount accessAccount) throws SQLException;
    AccessAccount getAccessAccount(String email) throws SQLException;
    List <AccessAccount> getAllAccessAccounts() throws SQLException;
    int updateAccessAccount (String email, AccessAccount accessAccount) throws SQLException;
    int deleteAccessAccount (String email) throws SQLException;
}
