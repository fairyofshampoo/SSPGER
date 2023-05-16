package mx.uv.fei.sspger.logic.DAO;


import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.uv.fei.sspger.logic.Professor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ProfessorDAOTest {
    
    public ProfessorDAOTest() {
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
    public void testAddProfessorTransaction() throws SQLException {
        System.out.println("addProfessorTransaction");
        Professor professor = new Professor();
        professor.setEMail("spiderman@uv.mx");
        professor.setPassword("Miri0301");
        professor.setStatus(1);
        professor.setName("Peter Benjamin");
        professor.setLastName("Parker");
        professor.setPersonalNumber("14AF");
        professor.setHonorificTitle("Ing.");
        professor.setIsAdmin(0);
        
        ProfessorDAO professorDAO = new ProfessorDAO();
        int expResult = 2;
        int result = professorDAO.addProfessorTransaction(professor);
        assertEquals(expResult, result);
    }

    /**
     * Test of getProfessor method, of class ProfessorDAO.
     */
    @Test
    public void testGetProfessor() throws SQLException {
        System.out.println("getProfessor");
        String email = "angesanchez@uv.mx";
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor professorTest = professorDAO.getProfessor(email);
        Professor professor = new Professor();
        
        professor.setId(1);
        professor.setEMail("angesanchez@uv.mx");
        professor.setName("Ángel Juan");
        professor.setLastName("Sánchez García");
        professor.setPersonalNumber("111");
        professor.setHonorificTitle("Dr.");
        professor.setIsAdmin(0);
        
        Professor expResult = professor;
        Professor result = professorTest;
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllProfessors method, of class ProfessorDAO.
     */
    @Test
    public void testGetAllProfessors() throws SQLException {
        System.out.println("getAllProfessors");
        ProfessorDAO professorDAO = new ProfessorDAO();
        
        Professor professor1 = new Professor();
        professor1.setId(1);
        professor1.setEMail("angesanchez@uv.mx");
        professor1.setName("Ángel Juan");
        professor1.setLastName("Sánchez García");
        professor1.setPersonalNumber("111");
        professor1.setHonorificTitle("Dr.");
        professor1.setIsAdmin(0);
        
        Professor professor2 = new Professor();
        professor2.setId(2);
        professor2.setEMail("elrevo@gmail.com");
        professor2.setName("Juan Carlos");
        professor2.setLastName("Pérez");
        professor2.setPersonalNumber("23RX1");
        professor2.setHonorificTitle("Ing");
        professor2.setIsAdmin(0);
        
        List<Professor> expResult = new ArrayList<>();
        expResult.add(professor1);
        expResult.add(professor2);
        
        List<Professor> result = professorDAO.getAllProfessors();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetProfessorsByStatus() throws SQLException {
        System.out.println("getProfessorsByStatus");
        int status = 1;
        ProfessorDAO professorDAO = new ProfessorDAO();
        
        Professor professor1 = new Professor();
        professor1.setId(1);
        professor1.setEMail("angesanchez@uv.mx");
        professor1.setName("Ángel Juan");
        professor1.setLastName("Sánchez García");
        professor1.setPersonalNumber("111");
        professor1.setHonorificTitle("Dr.");
        professor1.setIsAdmin(0);
        
        Professor professor2 = new Professor();
        professor2.setId(2);
        professor2.setEMail("elrevo@gmail.com");
        professor2.setName("Juan Carlos");
        professor2.setLastName("Pérez");
        professor2.setPersonalNumber("23RX1");
        professor2.setHonorificTitle("Ing");
        professor2.setIsAdmin(0);
        
        List<Professor> expResult = new ArrayList<>();
        expResult.add(professor1);
        expResult.add(professor2);
        
        List<Professor> result = professorDAO.getProfessorsByStatus(status);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testUpdateProfessorTransaction() throws SQLException {
        System.out.println("updateProfessorTransaction");
        String email = "spiderman@uv.mx";
        
        Professor professor = new Professor();
        professor.setEMail("spiderman@uv.mx");
        professor.setPassword("miMimiau@12");
        professor.setStatus(1);
        professor.setName("Peter Benjamin");
        professor.setLastName("Parker");
        professor.setPersonalNumber("2323140");
        professor.setHonorificTitle("Ing.");
        professor.setIsAdmin(0);
        
        ProfessorDAO professorDAO = new ProfessorDAO();
        int expResult = 2;
        int result = professorDAO.updateProfessorTransaction(email, professor);
        assertEquals(expResult, result);
    }

    /**
     * Test of changeProfessorStatus method, of class ProfessorDAO.
     */
    @Test
    public void testChangeProfessorStatus() throws SQLException{
        System.out.println("changeProfessorStatus");
        String email = "spiderman@uv.mx";
        int status = 0;
        ProfessorDAO instance = new ProfessorDAO();
        int expResult = 1;
        int result = instance.changeProfessorStatus(email, status);
        assertEquals(expResult, result);
    }
    
}
