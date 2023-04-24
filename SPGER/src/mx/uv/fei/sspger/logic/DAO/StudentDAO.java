package mx.uv.fei.sspger.logic.DAO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.contracts.IStudent;
import mx.uv.fei.sspger.logic.Student;


public class StudentDAO implements IStudent{

    @Override
    public int registrar(Student estudiante) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Student> getStudentList() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
