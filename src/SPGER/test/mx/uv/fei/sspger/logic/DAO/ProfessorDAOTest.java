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
    public void testAddProfessorTransactionSuccessful() throws SQLException {
        System.out.println("addProfessorTransaction");
        Professor professor = new Professor();
        professor.setEMail("oalonso@uv.mx");
        professor.setPassword("Oscar1234");
        professor.setStatus(1);
        professor.setName("Oscar");
        professor.setLastName("Alonso Ramírez");
        professor.setPersonalNumber("12233");
        professor.setHonorificTitle("Dr.");
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
        int idUsuarioProfesor = 111;
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor professorTest = professorDAO.getProfessor(idUsuarioProfesor);
        Professor professor = new Professor();
        
        professor.setId(10);
        professor.setEMail("elrevo@uv.mx");
        professor.setName("Juan Carlos");
        professor.setLastName("Pérez Arriaga");
        professor.setPersonalNumber("52312");
        professor.setHonorificTitle("Ing.");
        professor.setIsAdmin(1);
        
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
        professor1.setPersonalNumber("11121");
        professor1.setHonorificTitle("Dr.");
        professor1.setIsAdmin(1);
        
        Professor professor2 = new Professor();
        professor2.setId(10);
        professor2.setEMail("elrevo@uv.mx");
        professor2.setName("Juan Carlos");
        professor2.setLastName("Pérez Arriaga");
        professor2.setPersonalNumber("52312");
        professor2.setHonorificTitle("Ing.");
        professor2.setIsAdmin(1);
        
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
        professor2.setId(10);
        professor2.setEMail("elrevo@uv.mx");
        professor2.setName("Juan Carlos");
        professor2.setLastName("Pérez Arriaga");
        professor2.setPersonalNumber("52312");
        professor2.setHonorificTitle("Ing.");
        professor2.setIsAdmin(1);
        
        List<Professor> expResult = new ArrayList<>();
        expResult.add(professor1);
        expResult.add(professor2);
        
        List<Professor> result = professorDAO.getProfessorsByStatus(status);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testUpdateProfessorTransaction() throws SQLException {
        System.out.println("updateProfessorTransaction");
        String email = "oalonso@uv.mx";
        
        Professor professor = new Professor();
        professor.setEMail("oscaralonso@uv.mx");
        professor.setStatus(1);
        professor.setName("Oscar");
        professor.setLastName("Alonso Hernández");
        professor.setPersonalNumber("12233");
        professor.setHonorificTitle("Dr.");
        professor.setIsAdmin(0);
        
        ProfessorDAO professorDAO = new ProfessorDAO();
        int expResult = 2;
        int result = professorDAO.updateProfessorTransaction(email, professor);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateProfessorWithPasswordTransaction method, of class ProfessorDAO.
     */
    @Test
    public void testUpdateProfessorWithPasswordTransaction() throws Exception {
        System.out.println("updateProfessorWithPasswordTransaction");
        String eMail = "oscaralonso@uv.mx";
        Professor professor = new Professor();
        professor.setEMail("oscaralonso@uv.mx");
        professor.setStatus(1);
        professor.setName("Oscar");
        professor.setLastName("Alonso Juárez");
        professor.setPersonalNumber("12233");
        professor.setHonorificTitle("Dr.");
        professor.setIsAdmin(0);
        professor.setPassword("PedroPascal123");
        
        ProfessorDAO instance = new ProfessorDAO();
        int expResult = 2;
        int result = instance.updateProfessorWithPasswordTransaction(eMail, professor);
        assertEquals(expResult, result);
    }

    /**
     * Test of searchProfessorsbyName method, of class ProfessorDAO.
     */
    @Test
    public void testSearchProfessorsbyName() throws Exception {
        System.out.println("searchProfessorsbyName");
        String name = "Juan Carlos";
        ProfessorDAO instance = new ProfessorDAO();
        Professor professor1 = new Professor();
        professor1.setHonorificTitle("Ing.");
        professor1.setEMail("elrevo@uv.mx");
        professor1.setId(10);
        professor1.setName("Juan Carlos");
        professor1.setIsAdmin(1);
        professor1.setLastName("Pérez Arriaga");
        professor1.setPersonalNumber("52312");
        professor1.setStatus(1);
        List<Professor> expResult = new ArrayList<>();
        expResult.add(professor1);
        List<Professor> result = instance.searchProfessorsbyName(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDirectorsPerProjectStatus method, of class ProfessorDAO.
     */
    @Test
    public void testGetDirectorsPerProjectStatus() throws SQLException {
        System.out.println("getDirectorsPerProjectStatus");
        String projectStatus = "PROPUESTO";
        ProfessorDAO instance = new ProfessorDAO();
        List<Professor> expResult = new ArrayList<>();
        Professor professor1 = new Professor();
        professor1.setHonorificTitle("Ing.");
        professor1.setEMail("elrevo@uv.mx");
        professor1.setId(10);
        professor1.setName("Juan Carlos");
        professor1.setIsAdmin(1);
        professor1.setLastName("Pérez Arriaga");
        professor1.setPersonalNumber("52312");
        expResult.add(professor1);
        List<Professor> result = instance.getDirectorsPerProjectStatus(projectStatus);
        assertEquals(expResult, result);
    }

    
}
