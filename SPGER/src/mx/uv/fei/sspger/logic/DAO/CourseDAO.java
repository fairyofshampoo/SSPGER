package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.EnrollToCourse;
import mx.uv.fei.sspger.logic.contracts.ICourse;


public class CourseDAO implements ICourse{

    @Override
    public int registerCourse(Course course, int idSemester) throws SQLException {
        int result;
        String query = "INSERT INTO curso(idCurso, idPeriodoEscolar, nombre, nrc, seccion, bloque, estado) values (?,?,?,?,?,?,?)";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, course.getCourseId());
        statement.setInt(2, idSemester);
        statement.setString(3, course.getName());
        statement.setString(4, course.getNrc());
        statement.setInt(5, course.getSection());
        statement.setInt(6, course.getBlock());
        statement.setString(7, course.getState());

        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public List<Course> getCoursesPerState(String state) throws SQLException {
        String query = "SELECT * FROM asignacion WHERE estado = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, state);
        
        ResultSet coursesResult = statement.executeQuery();
        List<Course> coursesList = new ArrayList<>();
        
        while(coursesResult.next()){
            Course course = new Course();
            
            course.setName(coursesResult.getString("nombre"));
            course.setNrc(coursesResult.getString("nrc"));
            course.setSection(coursesResult.getInt("seccion"));
            course.setState(coursesResult.getString("estado"));
            course.setBlock(coursesResult.getInt("bloque"));
            coursesList.add(course);
        } 
        DataBaseManager.closeConnection();
        
        return coursesList;
    }

    @Override
    public List<Course> getAllCourses() throws SQLException {
        String query = "Select * From curso";
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet coursesResult = statement.executeQuery(query);
        List<Course> coursesList = new ArrayList<>();
        
        while(coursesResult.next()){
            Course course = new Course();
            
            course.setName(coursesResult.getString("nombre"));
            course.setNrc(coursesResult.getString("nrc"));
            course.setSection(coursesResult.getInt("seccion"));
            course.setState(coursesResult.getString("estado"));
            course.setBlock(coursesResult.getInt("bloque"));
            coursesList.add(course);
        } 
        DataBaseManager.closeConnection();
        
        return coursesList;
    }

    @Override
    public Course getCourseByID(String id) throws SQLException {
        String query = "SELECT * FROM curso WHERE idCurso = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, id);
        
        ResultSet coursesResult = statement.executeQuery(query);
        
        Course course = new Course();
            
        course.setName(coursesResult.getString("nombre"));
        course.setNrc(coursesResult.getString("nrc"));
        course.setSection(coursesResult.getInt("seccion"));
        course.setState(coursesResult.getString("estado"));
        course.setBlock(coursesResult.getInt("bloque"));
        
        DataBaseManager.closeConnection();
        
        return course;
    }

    @Override
    public int enrollStudentToCourse(EnrollToCourse enrollToCourse) throws SQLException {
        int response = 0;
        String query = "INSERT INTO cursa (idCurso, idUsuarioEstudiante) values (?, ?)";
        
        try{
            DataBaseManager.getConnection().setAutoCommit(false);

            PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, enrollToCourse.getCourseId());
            statement.setInt(2, enrollToCourse.getStudentId());
            statement.executeUpdate();
            DataBaseManager.getConnection().commit();

            ResultSet result = statement.getGeneratedKeys();
            
            result.next();
            
            response = result.getInt(1);
            
        } catch (SQLException exception){
            DataBaseManager.getConnection().rollback();
            //LOGGER
        } finally {
            DataBaseManager.closeConnection();
        }
        return response;
    }

    @Override
    public int expellStudentFromCourse(EnrollToCourse expellFromCourse) throws SQLException {
        int result;
        String query = "Delete from cursa where idCursa = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, expellFromCourse.getCoursesId());

        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public int modifyCourse(Course course, int idSemester, int professorId) throws SQLException {
        int result;
        String query = "UPDATE curso SET idCurso = ?, idPeriodoEscolar = ?, nombre = ?, nrc = ?, seccion = ?, bloque = ?, estado = ?, idUsuarioProfesor = ? WHERE idCurso = ?)";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(9, course.getCourseId());
        course.setCourseId(idSemester);
        statement.setString(1, course.getCourseId());
        statement.setInt(2, idSemester);
        statement.setString(3, course.getName());
        statement.setString(4, course.getNrc());
        statement.setInt(5, course.getSection());
        statement.setInt(6, course.getBlock());
        statement.setString(7, course.getState());
        statement.setInt(8, professorId);

        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public int enrollProfessorToCourse(int professorId, Course course) throws SQLException {
        int result;
        String query = "UPDATE curso SET idUsuarioProfesor = ? where idCurso = ?";
        DataBaseManager.getConnection();

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setInt(1, professorId);
        statement.setString(2, course.getCourseId());
        
        statement.executeUpdate();

        result = statement.executeUpdate();
        
        return result;
    }
}
