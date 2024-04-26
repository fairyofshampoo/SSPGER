package mx.uv.fei.sspger.logic.DAO;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyMember;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class AcademicBodyDAOTest {
    
    public AcademicBodyDAOTest() {
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
    
    /**
     * Test of addAcademicBodyTransaction method, of class AcademicBodyDAO.
     */
    @Test
    public void testAddAcademicBodyTransaction() throws SQLException {
        System.out.println("addAcademicBodyTransaction");
        AcademicBodyDAO instance = new AcademicBodyDAO();
        List<AcademicBodyMember> academicBodyMemberList = new ArrayList<>();
        AcademicBody academicBody = new AcademicBody();
        
        academicBody.setKey("UV-CA-122");
        academicBody.setName("Ingeniería de Software");
        
        AcademicBodyMember academicBodyMember1 = new AcademicBodyMember();
        academicBodyMember1.setId(10);
        academicBodyMember1.setRole("Responsable");
        academicBodyMemberList.add(academicBodyMember1);
        
        AcademicBodyMember academicBodyMember2 = new AcademicBodyMember();
        academicBodyMember2.setId(11);
        academicBodyMember2.setRole("Miembro");
        academicBodyMemberList.add(academicBodyMember2);
        
        academicBody.setMember(academicBodyMemberList);
        
        int expResult = 3;
        int result = instance.addAcademicBodyTransaction(academicBody);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of updateAcademicBody method, of class AcademicBodyDAO.
     */
    @Test
    public void testUpdateAcademicBody() throws SQLException {
        System.out.println("updateAcadenicBody");
        String id = "UV-CA-122";
        AcademicBody academicBody = new AcademicBody();
        AcademicBodyDAO instance = new AcademicBodyDAO();
        
        academicBody.setKey("UV-CA-121");
        academicBody.setName("Tecnología de Software");
        
        int expResult = 1;
        int result = instance.updateAcademicBody(id, academicBody);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of addAcademicBodyMember method, of class AcademicBodyDAO.
     */
    @Test
    public void testAddAcademicBodyMember() throws SQLException {
        System.out.println("addAcademicBodyMember");
        String academicBodyKey = "UV-CA-121";
        List<AcademicBodyMember> academicBodyMemberList = new ArrayList<>();
        AcademicBodyMember academicBodyMember = new AcademicBodyMember();
        
        academicBodyMember.setId(12);
        academicBodyMember.setRole("Miembro");
        academicBodyMemberList.add(academicBodyMember);
        
        AcademicBodyDAO instance = new AcademicBodyDAO();
        int expResult = 1;
        int result = instance.addAcademicBodyMember(academicBodyMemberList, academicBodyKey);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of deleteAcademicBodyMember method, of class AcademicBodyDAO.
     */
    @Test
    public void testDeleteAcademicBodyMember() throws SQLException {
        System.out.println("deleteAcademicBodyMember");
        List<AcademicBodyMember> memberList = new ArrayList<>();
        AcademicBodyMember academicBodyMember = new AcademicBodyMember();
        
        academicBodyMember.setIdAcademicBodyMember(12);
        
        AcademicBodyMember academicBodyMember2 = new AcademicBodyMember();
        
        academicBodyMember2.setIdAcademicBodyMember(11);
        
        memberList.add(academicBodyMember);
        memberList.add(academicBodyMember2);
        
        AcademicBodyDAO instance = new AcademicBodyDAO();
        int expResult = 2;
        int result = instance.deleteAcademicBodyMember(memberList);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAcademicBody method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetAcademicBody() throws SQLException {
        System.out.println("getAcademicBody");
        String id = "UV-CA-127";
        AcademicBodyDAO academicBodyTest = new AcademicBodyDAO();
        
        AcademicBody academicBodyResult = academicBodyTest.getAcademicBody(id);
        
        AcademicBody academicBodyExpected = new AcademicBody();
        
        academicBodyExpected.setKey("UV-CA-121");
        academicBodyExpected.setName("Tecnología de Software");
        academicBodyExpected.setStatus(1);
        
        AcademicBody result = academicBodyResult;
        AcademicBody expResult = academicBodyExpected;
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllAcademicBody method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetAllAcademicBody() throws SQLException {
        System.out.println("getAllAcademicBody");
        
        AcademicBody academicBody1 = new AcademicBody();
        academicBody1.setKey("UV-CA-127");
        academicBody1.setName("Ingeniería y tecnología de software");
        academicBody1.setStatus(1);
        
        AcademicBody academicBody2 = new AcademicBody();
        academicBody2.setKey("UV-CA-121");
        academicBody2.setName("Tecnología de Software");
        academicBody2.setStatus(1);
        
        List<AcademicBody> expResult = new ArrayList<>();
        expResult.add(academicBody1);
        expResult.add(academicBody2);
        
        AcademicBodyDAO academicBodyTest = new AcademicBodyDAO();
        List<AcademicBody> result = academicBodyTest.getAllAcademicBody();
        
        assertEquals(expResult, result);
    }    

    /**
     * Test of getExistenceAcademicBody method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetExistenceAcademicBodyNotExists() throws SQLException {
        System.out.println("getExistenceAcademicBody");
        String key = "UV-CA-123";
        AcademicBodyDAO instance = new AcademicBodyDAO();
        int expResult = 0;
        int result = instance.getExistenceAcademicBody(key);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getExistenceAcademicBody method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetExistenceAcademicBodyExists() throws SQLException {
        System.out.println("getExistenceAcademicBody");
        String key = "UV-CA-127";
        AcademicBodyDAO instance = new AcademicBodyDAO();
        int expResult = 1;
        int result = instance.getExistenceAcademicBody(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAcademicBodyMembers method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetAcademicBodyMembersNotExists() throws SQLException {
        System.out.println("getAcademicBodyMembers");
        String key = "UV-CA-121";
        AcademicBodyDAO instance = new AcademicBodyDAO();
        List<AcademicBodyMember> expResult = new ArrayList<>();
        List<AcademicBodyMember> result = instance.getAcademicBodyMembers(key);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getAcademicBodyMembers method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetAcademicBodyMembersExists() throws SQLException {
        System.out.println("getAcademicBodyMembers");
        String key = "UV-CA-127";
        AcademicBodyDAO instance = new AcademicBodyDAO();
        List<AcademicBodyMember> expResult = new ArrayList<>();
        AcademicBodyMember member1 = new AcademicBodyMember();
        
        member1.setIdAcademicBodyMember(33);
        member1.setRole("Miembro");
        member1.setId(12);
        member1.setHonorificTitle("Dr.");
        member1.setName("María Karen");
        member1.setLastName("Cortés Verdín");
        
        expResult.add(member1);
        List<AcademicBodyMember> result = instance.getAcademicBodyMembers(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAcademicBodyResponsible method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetAcademicBodyResponsible() throws SQLException {
        System.out.println("getAcademicBodyResponsible");
        String key = "UV-CA-127";
        AcademicBodyDAO instance = new AcademicBodyDAO();
        AcademicBodyMember expResult = new AcademicBodyMember();
        
        expResult.setId(11);
        expResult.setIdAcademicBodyMember(32);
        expResult.setRole("Responsable");
        expResult.setHonorificTitle("Dr.");
        expResult.setName("Jorge Octavio");
        expResult.setLastName("Ocharán Hernández");
        
        AcademicBodyMember result = instance.getAcademicBodyResponsible(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateAcademicBodyStatus method, of class AcademicBodyDAO.
     */
    @Test
    public void testUpdateAcademicBodyStatusToInactive() throws SQLException {
        System.out.println("updateAcademicBodyStatus");
        int status = 0;
        String key = "UV-CA-121";
        AcademicBodyDAO instance = new AcademicBodyDAO();
        int expResult = 1;
        int result = instance.updateAcademicBodyStatus(status, key);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getAllAcademicBodyMembers method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetAllAcademicBodyMembers() throws SQLException {
        System.out.println("getAllAcademicBodyMembers");
        String key = "UV-CA-127";
        AcademicBodyDAO instance = new AcademicBodyDAO();
        List<AcademicBodyMember> expResult = new ArrayList<>();
        
        AcademicBodyMember member1 = new AcademicBodyMember();
        member1.setIdAcademicBodyMember(33);
        member1.setRole("Miembro");
        member1.setId(12);
        member1.setHonorificTitle("Dr.");
        member1.setName("María Karen");
        member1.setLastName("Cortés Verdín");
        
        AcademicBodyMember member2 = new AcademicBodyMember();
        member2.setId(11);
        member2.setIdAcademicBodyMember(32);
        member2.setRole("Responsable");
        member2.setHonorificTitle("Dr.");
        member2.setName("Jorge Octavio");
        member2.setLastName("Ocharán Hernández");
        
        expResult.add(member1);
        expResult.add(member2);
        
        List<AcademicBodyMember> result = instance.getAllAcademicBodyMembers(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of responsibleExistence method, of class AcademicBodyDAO.
     */
    @Test
    public void testResponsibleExistence() throws SQLException {
        System.out.println("responsibleExistence");
        String key = "UV-CA-127";
        AcademicBodyDAO instance = new AcademicBodyDAO();
        int expResult = 1;
        int result = instance.responsibleExistence(key);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of updateAcademicBody method, of class AcademicBodyDAO.
     */
    @Test
    public void testUpdateAcademicBodyNotFound() throws SQLException {
        System.out.println("updateAcadenicBody");
        String id = "UV-CA-120";
        AcademicBody academicBody = new AcademicBody();
        AcademicBodyDAO instance = new AcademicBodyDAO();
        
        academicBody.setKey("UV-CA-121");
        academicBody.setName("Tecnología de Software");
        
        int expResult = 0;
        int result = instance.updateAcademicBody(id, academicBody);
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test of updateAcademicBodyStatus method, of class AcademicBodyDAO.
     */
    @Test
    public void testUpdateAcademicBodyStatusToActive() throws SQLException {
        System.out.println("updateAcademicBodyStatus");
        int status = 1;
        String key = "UV-CA-121";
        AcademicBodyDAO instance = new AcademicBodyDAO();
        int expResult = 1;
        int result = instance.updateAcademicBodyStatus(status, key);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMemberPerIdProfessor method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetMemberPerIdProfessor() throws SQLException {
        System.out.println("getMemberPerIdProfessor");
        int idProfessor = 11;
        String academicBodyKey = "UV-CA-127";
        AcademicBodyDAO instance = new AcademicBodyDAO();
        AcademicBodyMember expResult = new AcademicBodyMember();
        expResult.setId(11);
        expResult.setIdAcademicBodyMember(32);
        expResult.setRole("Responsable");
        expResult.setHonorificTitle("Dr.");
        expResult.setName("Jorge Octavio");
        expResult.setLastName("Ocharán Hernández");
        
        AcademicBodyMember result = instance.getMemberPerIdProfessor(idProfessor, academicBodyKey);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAcademicBodiesFromProfessor method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetAcademicBodiesFromProfessor() throws SQLException {
        System.out.println("getAcademicBodiesFromProfessor");
        int professorId = 12;
        AcademicBodyDAO instance = new AcademicBodyDAO();
        List<AcademicBody> expResult = new ArrayList<>();
        AcademicBody academicBody1 = new AcademicBody();
        
        academicBody1.setKey("UV-CA-127");
        academicBody1.setName("Ingeniería y tecnología de software");
        
        expResult.add(academicBody1);
        
        List<AcademicBody> result = instance.getAcademicBodiesFromProfessor(professorId);
        assertEquals(expResult, result);
    }

    

    /**
     * Test of getAcademicBodiesActive method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetAcademicBodiesActive() throws SQLException {
        System.out.println("getAcademicBodiesActive");
        AcademicBodyDAO instance = new AcademicBodyDAO();
        List<AcademicBody> expResult = new ArrayList<>();
        AcademicBody academicBody1 = new AcademicBody();
        
        academicBody1.setKey("UV-CA-127");
        academicBody1.setName("Ingeniería y tecnología de software");
        
        AcademicBody academicBody2 = new AcademicBody();
        academicBody2.setKey("UV-CA-121");
        academicBody2.setName("Tecnología de Software");
        
        List<AcademicBody> result = instance.getAcademicBodiesActive();
        assertEquals(expResult, result);
    }
}
