/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package mx.uv.fei.sspger.logic.DAO;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import mx.uv.fei.sspger.logic.AccessAccount;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author miche
 */
public class AccessAccountDAOTest {
    
    public AccessAccountDAOTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addAccessAccount method, of class AccessAccountDAO.
     */
    @Test
    public void testAddAccessAccount() throws Exception {
        System.out.println("addAccessAccount");
        
        AccessAccount accessAccount = new AccessAccount();
        
        accessAccount.setEMail("oalonso@uv.mx");
        accessAccount.setPassword("Miri0301");
        accessAccount.setPrivileges(1);
        
        AccessAccountDAO accessAccountDAO = new AccessAccountDAO();
        
        int expResult = 0;
        int result = accessAccountDAO.addAccessAccount(accessAccount);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getAccessAccount method, of class AccessAccountDAO.
     */
    @Test
    public void testGetAccessAccount() throws Exception {
        System.out.println("getAccessAccount");
        String email = "angesanchez@uv.mx";
        AccessAccountDAO accessAccountDAO = new AccessAccountDAO();
        AccessAccount accessAccount = new AccessAccount();
        
        accessAccount.setEMail("angesanchez@uv.mx");
        accessAccount.setPassword("Miri0301");
        accessAccount.setPrivileges(1);
        
        boolean expResult = true;
        boolean result = accessAccount == accessAccountDAO.getAccessAccount(email);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllAccessAccounts method, of class AccessAccountDAO.
     */
    @Test
    public void testGetAllAccessAccounts() throws Exception {
        System.out.println("getAllAccessAccounts");
        AccessAccountDAO instance = new AccessAccountDAO();
        List<AccessAccount> expResult = null;
        List<AccessAccount> result = instance.getAllAccessAccounts();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
