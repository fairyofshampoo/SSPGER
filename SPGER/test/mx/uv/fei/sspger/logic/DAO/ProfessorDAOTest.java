package mx.uv.fei.sspger.logic.DAO;

import java.util.List;
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
    public void testSuccessfulAddProfessor() throws Exception {
        System.out.println("addProfessor");
        
        Professor professor = new Professor();
        
        professor.setEMail("angesanchez@uv.mx");
        professor.setName("Ángel Juan");
        professor.setLastName("Sánchez García");
        professor.setPersonalNumber("111");
        professor.setHonorificTitle("Dr.");
        
        ProfessorDAO professorDAO = new ProfessorDAO();
        
        int expResult = 1;
        int result = professorDAO.addProfessor(professor);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testSuccessfulGetProfessor() throws Exception {
        System.out.println("getProfessor");
        
        String email = "angesanchez@uv.mx";
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor professorTest = professorDAO.getProfessor(email);
        Professor professor = new Professor();
        
        professor.setEMail("angesanchez@uv.mx");
        professor.setName("Ángel Juan");
        professor.setLastName("Sánchez García");
        professor.setPersonalNumber("111");
        professor.setHonorificTitle("Dr.");
        
        Professor expResult = professor;
        Professor result = professorTest;
        assertEquals(expResult, result);
    }

    @Test
    public void testSuccessfulGetAllProfessors() throws Exception {
        System.out.println("getAllProfessors");
        ProfessorDAO professorDAO = new ProfessorDAO();
        
        Professor professor1 = new Professor();
        professor1.setEMail("angesanchez@uv.mx");
        professor1.setPersonalNumber("111");
        
        Professor professor2 = new Professor();
        professor2.setEMail("oalonso@uv.mx");
        professor2.setPersonalNumber("222");
        
        List<Professor> expResult = new ArrayList<>();
        expResult.add(professor1);
        expResult.add(professor2);
        
        List<Professor> result = professorDAO.getAllProfessors();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSuccessfulUpdateProfessor() throws Exception {
        System.out.println("updateProfessor");
        String email = "oalonso@uv.mx";
        Professor professor = new Professor();
        ProfessorDAO professorDAO = new ProfessorDAO();
        
        professor.setEMail("oalonso@uv.mx");
        professor.setName("Oscar Rodrigo");
        professor.setLastName("Alonso Durán");
        professor.setPersonalNumber("222");
        professor.setHonorificTitle("Mtro.");
        
        int expResult = 1;
        int result = professorDAO.updateProfessor(email, professor);
        assertEquals(expResult, result);
    }

    @Test
    public void testSuccessfulDeleteProfessor() throws Exception {
        System.out.println("deleteProfessor");
        String email = "oalonso@uv.mx";
        ProfessorDAO professorDAO = new ProfessorDAO();
        int expResult = 1;
        int result = professorDAO.deleteProfessor(email);
        assertEquals(expResult, result);
    }
    
}
