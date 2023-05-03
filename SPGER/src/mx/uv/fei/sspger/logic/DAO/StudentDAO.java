package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
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
    public int addStudent(Student student) throws SQLException {
        int result;
        String query = "insert into estudiante(correo_institucional, nombre, apellido, matricula) values(?,?,?,?)";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, student.getEMail());
        statement.setString(2, student.getName());
        statement.setString(3, student.getLastName());
        statement.setString(4, student.getRegistrationTag());
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
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
        student.setEMail(studentResult.getString("correo_institucional"));
        student.setName(studentResult.getString("nombre"));
        student.setLastName(studentResult.getString("apellido"));
        student.setRegistrationTag(studentResult.getString("matricula"));
        
        DataBaseManager.closeConnection();

        return student;
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        String query = "SELECT * FROM estudiante";
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet studentResult = statement.executeQuery(query);
        
        List<Student> studentList = new ArrayList<>();
        
        while(studentResult.next()){
            Student student = new Student();
            student.setEMail(studentResult.getString("correo_institucional"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setRegistrationTag(studentResult.getString("matricula"));
            studentList.add(student);
        }
        
        DataBaseManager.closeConnection();
        
        return studentList;
    }

    @Override
    public int updateStudent(String email, Student student) throws SQLException {
        int result;
        String query = "UPDATE estudiante SET correo_institucional = ?, nombre = ?, apellido = ?, matricula = ? WHERE correo_institucional = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);       
        
        statement.setString(1, student.getEMail());
        statement.setString(2, student.getName());
        statement.setString(3, student.getLastName());
        statement.setString(4, student.getRegistrationTag());
        statement.setString(5, email);
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public int deleteStudent(String email) throws SQLException {
        int result;
        String query = "DELETE FROM estudiante WHERE correo_institucional = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, email);
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
    
}
