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
    
    private final int ERROR_ADDITION = -1;
    private final String ADD_STUDENT_COMMAND = "insert into estudiante(correo_institucional, nombre, apellido, matricula) values(?,?,?,?)";
    private final String ADD_ACCESS_ACCOUNT_COMMAND = "insert into cuenta_acceso(correo, password, estado) values (?, SHA1(?), ?)";
    private final String GET_STUDENT_QUERY = "SELECT * FROM estudiante WHERE correo_institucional = ?";
    private final String GET_ALL_STUDENTS_QUERY = "SELECT * FROM estudiante";
    private final String GET_STUDENTS_BY_STATUS_QUERY = "SELECT estudiante.* FROM cuenta_acceso INNER JOIN estudiante ON cuenta_acceso.correo = estudiante.correo_institucional WHERE cuenta_acceso.estado = ?";
    private final String UPDATE_ACCESS_ACCOUNT_COMMAND = "UPDATE cuenta_acceso SET correo = ?, password = SHA1(?), estado = ? WHERE correo = ?";
    private final String UPDATE_STUDENT_COMMAND = "UPDATE estudiante SET nombre = ?, apellido = ?, matricula = ? WHERE correo_institucional = ?";
    private final String CHANGE_STUDENT_STATUS_QUERY = "UPDATE cuenta_acceso SET estado = ? WHERE correo = ?";
    
    @Override
    public int addStudentTransaction(Student student) throws SQLException {
        int response = ERROR_ADDITION;
        try{
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(ADD_ACCESS_ACCOUNT_COMMAND);
        
            accountStatement.setString(1, student.getEMail());
            accountStatement.setString(2, student.getPassword());
            accountStatement.setInt(3, student.getStatus());
        
            response = accountStatement.executeUpdate();
        
            if(response != ERROR_ADDITION){
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
    public Student getStudent(String email) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_STUDENT_QUERY);

        statement.setString(1,email);

        ResultSet studentResult = statement.executeQuery();
        Student student = new Student();
        
        if(studentResult.next()){
        student.setEMail(studentResult.getString("correo"));
        student.setName(studentResult.getString("nombre"));
        student.setLastName(studentResult.getString("apellido"));
        student.setRegistrationTag(studentResult.getString("matricula"));
        student.setId(studentResult.getInt("idUsuarioEstudiante"));
        }
        DataBaseManager.closeConnection();

        return student;
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
        int response = ERROR_ADDITION;
        try{
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACCESS_ACCOUNT_COMMAND);
        
            accountStatement.setString(1, student.getEMail());
            accountStatement.setString(2, student.getPassword());
            accountStatement.setInt(3, student.getStatus());
            accountStatement.setString(4, email);
        
            response = accountStatement.executeUpdate();
        
            if(response != ERROR_ADDITION){
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
        int response = ERROR_ADDITION;
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
    DataBaseManager.getConnection();
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(GET_STUDENTS_BY_STATUS_QUERY);

        statement.setInt(1, status);

        ResultSet studentResult = statement.executeQuery();
        
        List<Student> studentList = new ArrayList<>();
        
        while(studentResult.next()){
            Student student = new Student();
            student.setEMail(studentResult.getString("correo"));
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
    
}
