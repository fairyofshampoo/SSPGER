/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package mx.uv.fei.sspger.logic;

import mx.uv.fei.sspger.logic.DAO.AccessAccountDAO;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
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
    public void testAddAccessAccount() throws SQLException {
        System.out.println("addAccessAccount");
        AccessAccount accessAccount = new AccessAccount();
        accessAccount.setEMail("oalonso@uv.mx");
        accessAccount.setPassword("Miri0301");
        accessAccount.setPrivileges(1);
        AccessAccountDAO instance = new AccessAccountDAO();
        int expResult = 0;
        int result = instance.addAccessAccount(accessAccount);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAccessAccount method, of class AccessAccountDAO.
     */
    @Test
    public void testGetAccessAccount() throws SQLException {
        System.out.println("getAccessAccount");
        String email = "";
        AccessAccountDAO instance = new AccessAccountDAO();
        AccessAccount expResult = null;
        AccessAccount result = instance.getAccessAccount(email);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllAccessAccounts method, of class AccessAccountDAO.
     */
    @Test
    public void testGetAllAccessAccounts() throws Exception {
        System.out.println("getAllAccessAccounts");
        AccessAccountDAO instance = new AccessAccountDAO();
        boolean expResult = true;
        boolean result = true; //mandar equals
        assertEquals(expResult, result);
    }
    
}
