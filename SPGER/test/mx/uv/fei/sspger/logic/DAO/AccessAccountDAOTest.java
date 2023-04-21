package mx.uv.fei.sspger.logic.DAO;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
import mx.uv.fei.sspger.logic.*;


public class AccessAccountDAOTest {
    
    public AccessAccountDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
   
    @After
    public void tearDown() {
    }

    @Test
    public void testSuccessfulAddAccessAccount() throws Exception {
        System.out.println("addAccessAccount");
        
        AccessAccount accessAccount = new AccessAccount();
        
        accessAccount.setEMail("oalonso@uv.mx");
        accessAccount.setPassword("Miri0301");
        accessAccount.setPrivileges(1);
        
        AccessAccountDAO accessAccountDAO = new AccessAccountDAO();
        
        int expResult = 1;
        int result = accessAccountDAO.addAccessAccount(accessAccount);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testSuccessfulGetAccessAccount() throws Exception {
        System.out.println("getAccessAccount");
        String email = "angesanchez@uv.mx";
        AccessAccountDAO accessAccountDAO = new AccessAccountDAO();
        AccessAccount accessAccountTest = accessAccountDAO.getAccessAccount(email);
        AccessAccount accessAccount = new AccessAccount();
        
        accessAccount.setEMail("angesanchez@uv.mx");
        accessAccount.setPassword("Miri0301");
        accessAccount.setPrivileges(1);
        
        AccessAccount expResult = accessAccount;
        AccessAccount result = accessAccountTest;
        assertEquals(expResult, result);
    }
    @Test
    public void testSuccessfulGetAllAccessAccounts() throws Exception {
        System.out.println("getAllAccessAccounts");
        AccessAccountDAO accessAccountDAO = new AccessAccountDAO();
        
        AccessAccount accessAccount1 = new AccessAccount();
        accessAccount1.setEMail("angesanchez@uv.mx");
        
        AccessAccount accessAccount2 = new AccessAccount();
        accessAccount2.setEMail("Asd@uv.mx");
        
        AccessAccount accessAccount3 = new AccessAccount();
        accessAccount3.setEMail("oalonso@uv.mx");
        
        AccessAccount accessAccount4 = new AccessAccount();
        accessAccount4.setEMail("miau@uv.mx");

        List<AccessAccount> expResult = new ArrayList<>();
        expResult.add(accessAccount1);
        expResult.add(accessAccount2);
        expResult.add(accessAccount3);
        
        List<AccessAccount> result = accessAccountDAO.getAllAccessAccounts();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSuccessfulUpdateAccessAccount() throws Exception {
        System.out.println("updateAccessAccount");
        String email = "oalonso@uv.mx";
        AccessAccount accessAccount = new AccessAccount();
        AccessAccountDAO accessAccountDAO = new AccessAccountDAO();
        
        accessAccount.setEMail("oscar_alonso@uv.mx");
        accessAccount.setPassword("P4SsW0RD");
        
        int expResult = 1;
        int result = accessAccountDAO.updateAccessAccount(email, accessAccount);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSuccessfulDeleteAccessAccount() throws Exception {
        System.out.println("deleteAccessAccount");
        String email = "oscar_alonso@uv.mx";
        AccessAccountDAO accessAccountDAO = new AccessAccountDAO();
        int expResult = 1;
        int result = accessAccountDAO.deleteAccessAccount(email);
        assertEquals(expResult, result);
    }
    
}
