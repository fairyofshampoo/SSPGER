package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.contracts.IStudent;
import mx.uv.fei.sspger.logic.Student;


public class StudentDAO implements IStudent{
    private final int ERROR = -1;
    private final String ADD_STUDENT_COMMAND = "insert into estudiante(correo_institucional, nombre, apellido, matricula) values(?,?,?,?)";
    private final String ADD_ACCESS_ACCOUNT_COMMAND = "insert into cuenta_acceso(correo, password, estado) values (?, SHA1(?), ?)";
    private final String GET_ALL_STUDENTS_QUERY = "SELECT * FROM estudiante";
    private final String GET_STUDENTS_BY_STATUS_QUERY = "SELECT estudiante.* FROM cuenta_acceso INNER JOIN estudiante ON cuenta_acceso.correo = estudiante.correo_institucional WHERE cuenta_acceso.estado = ?";
    private final String UPDATE_ACCESS_ACCOUNT_COMMAND = "UPDATE cuenta_acceso SET correo = ?, password = SHA1(?), estado = ? WHERE correo = ?";
    private final String UPDATE_STUDENT_COMMAND = "UPDATE estudiante SET nombre = ?, apellido = ?, matricula = ? WHERE correo_institucional = ?";
    private final String CHANGE_STUDENT_STATUS_QUERY = "UPDATE cuenta_acceso SET estado = ? WHERE correo = ?";
    private final String GET_STUDENTS_BY_RECEPTIONAL_WORK = "SELECT nombre, apellido, idEstudiante FROM estudiante_trabajo_recepcional INNER JOIN estudiante ON estudiante.idUsuarioEstudiante = estudiante_trabajo_recepcional.idEstudiante WHERE idTrabajoRecepcional = ?";
    private final String SEARCH_STUDENT_BY_NAME = "SELECT * FROM cuenta_acceso INNER JOIN estudiante ON cuenta_acceso.correo = estudiante.correo_institucional WHERE estudiante.nombre LIKE ?";
    private final String GET_STUDENT_BY_ID = "SELECT * FROM estudiante WHERE idUsuarioEstudiante = ?";

    @Override
    public Student getStudent(int idStudent) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_STUDENT_BY_ID);

        statement.setInt(1,idStudent);

        ResultSet studentResult = statement.executeQuery();
        Student student = new Student();
        
        if(studentResult.next()){
            student.setEMail(studentResult.getString("correo_institucional"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setRegistrationTag(studentResult.getString("matricula"));
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
        }
        
        DataBaseManager.closeConnection();

        return student;
    }
    
    @Override
    public Student getStudent(String email) throws SQLException {
        String query = "SELECT * FROM estudiante WHERE correo_institucional = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setString(1,email);

        ResultSet studentResult = statement.executeQuery();
        studentResult.next();
        
        Student student = new Student();
        student.setId(studentResult.getInt("idUsuarioEstudiante"));
        student.setEMail(studentResult.getString("correo_institucional"));
        student.setName(studentResult.getString("nombre"));
        student.setLastName(studentResult.getString("apellido"));
        student.setRegistrationTag(studentResult.getString("matricula"));
        
        DataBaseManager.closeConnection();

        return student;
    }
    
    @Override
    public List<Student> getAvailableStudents() throws SQLException {
        String query = "Select idUsuarioEstudiante, nombre, apellido, matricula From estudiante";
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet studentResult = statement.executeQuery(query);
        List<Student> studentList = new ArrayList<>();
        
        while(studentResult.next()){
            Student student = new Student();
            
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setRegistrationTag(studentResult.getString("matricula"));
            studentList.add(student);
        } 
        DataBaseManager.closeConnection();
        
        return studentList;
    }

    @Override
    public List<Student> getStudentsPerCourse(String courseId) throws SQLException {
        String query = "SELECT idUsuarioEstudiante, nombre, apellido, matricula, correo_institucional "
                + "FROM estudiante NATURAL JOIN cursa WHERE cursa.idCurso = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, courseId);
        
        ResultSet studentResult = statement.executeQuery();
        List<Student> studentList = new ArrayList<>();
        
        while(studentResult.next()){
            Student student = new Student();
            
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setRegistrationTag(studentResult.getString("matricula"));
            student.setEMail(studentResult.getString("correo_institucional"));
            studentList.add(student);
        } 
        DataBaseManager.closeConnection();
        
        return studentList;
    }
    
     @Override
    public List<Student> getAvailableStudentsNotInCourse(String courseId) throws SQLException {
        String query = "SELECT idUsuarioEstudiante, nombre, apellido, matricula, correo_institucional FROM estudiante" +
        " WHERE idUsuarioEstudiante NOT IN (SELECT idUsuarioEstudiante FROM estudiante NATURAL JOIN cursa WHERE idCurso = ?)";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, courseId);
        
        ResultSet studentResult = statement.executeQuery();
        List<Student> studentList = new ArrayList<>();
        
        while(studentResult.next()){
            Student student = new Student();
            
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setRegistrationTag(studentResult.getString("matricula"));
            student.setEMail(studentResult.getString("correo_institucional"));
            studentList.add(student);
        } 
        DataBaseManager.closeConnection();
        
        return studentList;
    }

    @Override
    public List<Student> getStudentPerReceptionalWork(int idRedceptionalWork) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_STUDENTS_BY_RECEPTIONAL_WORK);

        statement.setInt(1,idRedceptionalWork);

        ResultSet studentResult = statement.executeQuery();
        List <Student> studentList = new ArrayList <>();
        
        while(studentResult.next()){
            Student student = new Student();

            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setId(studentResult.getInt("idEstudiante"));
            
            studentList.add(student);
        }
        
        DataBaseManager.closeConnection();

        return studentList;
    }
    
    @Override
    public int addStudentTransaction(Student student) throws SQLException {
        int response = ERROR;
        try{
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(ADD_ACCESS_ACCOUNT_COMMAND);
        
            accountStatement.setString(1, student.getEMail());
            accountStatement.setString(2, student.getPassword());
            accountStatement.setInt(3, student.getStatus());
        
            response = accountStatement.executeUpdate();
        
            if(response != ERROR){
                PreparedStatement studentStatement = DataBaseManager.getConnection().prepareStatement(ADD_STUDENT_COMMAND);
        
                studentStatement.setString(1, student.getEMail());
                studentStatement.setString(2, student.getName());
                studentStatement.setString(3, student.getLastName());
                studentStatement.setString(4, student.getRegistrationTag());
            
                response = response + studentStatement.executeUpdate();
            }
        
            DataBaseManager.getConnection().commit();
        } catch (SQLException ex){
            DataBaseManager.getConnection().rollback();
            Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet studentResult = statement.executeQuery(
                GET_ALL_STUDENTS_QUERY);
        
        List<Student> studentList = new ArrayList<>();
        
        while(studentResult.next()){
            Student student = new Student();
            student.setEMail(studentResult.getString("correo_institucional"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString(
                    "apellido"));
            student.setRegistrationTag(studentResult.getString(
                    "matricula"));
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
            studentList.add(student);
        }
        
        DataBaseManager.closeConnection();
        
        return studentList;
    }

    @Override
    public int updateStudentTransaction(String email, Student student) throws SQLException {
        int response = ERROR;
        try{
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACCESS_ACCOUNT_COMMAND);
        
            accountStatement.setString(1, student.getEMail());
            accountStatement.setString(2, student.getPassword());
            accountStatement.setInt(3, student.getStatus());
            accountStatement.setString(4, email);
        
            response = accountStatement.executeUpdate();
        
            if(response != ERROR){
                PreparedStatement studentStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_STUDENT_COMMAND);
                
                studentStatement.setString(1, student.getName());
                studentStatement.setString(2, student.getLastName());
                studentStatement.setString(3, student.getRegistrationTag());
                studentStatement.setString(4, email);
            
                response = response + studentStatement.executeUpdate();
            }
        
            DataBaseManager.getConnection().commit();
        } catch (SQLException ex){
            DataBaseManager.getConnection().rollback();
            Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    @Override
    public int changeStudentStatus(String email, int status) throws SQLException {
        int response = ERROR;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(CHANGE_STUDENT_STATUS_QUERY);       
        
        statement.setInt(1, status);
        statement.setString(2, email);
        
        response = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return response;
    }

    @Override
    public List<Student> getStudentsByStatus(int status) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(GET_STUDENTS_BY_STATUS_QUERY);

        statement.setInt(1, status);

        ResultSet studentResult = statement.executeQuery();
        
        List<Student> studentList = new ArrayList<>();
        
        while(studentResult.next()){
            Student student = new Student();
            student.setEMail(studentResult.getString("correo_institucional"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString(
                    "apellido"));
            student.setRegistrationTag(studentResult.getString(
                    "matricula"));
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
            studentList.add(student);
        }
        
        DataBaseManager.closeConnection();
        
        return studentList;
    }
    private List<Student> getStudentList(ResultSet studentResult) throws SQLException{
        List<Student> studentList = new ArrayList<>();
        while(studentResult.next()){
            Student student = new Student();
            student.setEMail(studentResult.getString("correo"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setRegistrationTag(studentResult.getString("matricula"));
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
            student.setStatus(studentResult.getInt("estado"));
            studentList.add(student);
        }
        return studentList;
    }
    @Override
    public List <Student> searchStudentbyName(String name) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(SEARCH_STUDENT_BY_NAME);
        String searchName = name + "%";
        statement.setString(1, searchName);
        
        List<Student> studentList = new ArrayList<>();
        ResultSet studentResult = statement.executeQuery();
        studentList = getStudentList(studentResult);
        DataBaseManager.closeConnection();
        return studentList;
    }
    
    @Override
    public List<Student> getStudentsByProjectByStatus(int idProject, String estadoEstudiante) throws SQLException{
        String query = "SELECT estudiante.idUsuarioEstudiante, nombre, apellido, correo_institucional FROM estudiante INNER JOIN estudiante_anteproyecto ON estudiante_anteproyecto.idEstudiante = estudiante.idUsuarioEstudiante WHERE idAnteproyecto = ? AND estadoEstudiante = ?";
        
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, idProject);
        statement.setString(2, estadoEstudiante);
        
        ResultSet studentResult = statement.executeQuery();
        
        List<Student> studentList = new ArrayList<>();
        
        while(studentResult.next()){
            Student student = new Student();
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
            student.setEMail(studentResult.getString("correo_institucional"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            studentList.add(student);
        }
        
        DataBaseManager.closeConnection();
        
        return studentList;
    }
}
