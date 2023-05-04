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
            course.setSemesterId(coursesResult.getInt("idPeriodoEscolar"));
            course.setProfessorId(coursesResult.getInt("idUsuarioProfesor"));
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
            course.setSemesterId(coursesResult.getInt("idPeriodoEscolar"));
            course.setProfessorId(coursesResult.getInt("idUsuarioProfesor"));
            coursesList.add(course);
        } 
        DataBaseManager.closeConnection();
        
        return coursesList;
    }

    @Override
    public Course getCourseByID(String idCourse) throws SQLException {
        String query = "SELECT * FROM curso WHERE idCurso = ?";
        
        DataBaseManager.getConnection();
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, idCourse);
        ResultSet coursesResult = statement.executeQuery();
        
        Course course = new Course();
            
        if(coursesResult.next()){
        course.setName(coursesResult.getString("nombre"));
        course.setNrc(coursesResult.getString("nrc"));
        course.setSection(coursesResult.getInt("seccion"));
        course.setState(coursesResult.getString("estado"));
        course.setBlock(coursesResult.getInt("bloque"));
        course.manualSetOfCourseId(coursesResult.getString("idCurso"));
        course.setSemesterId(coursesResult.getInt("idPeriodoEscolar"));
        course.setProfessorId(coursesResult.getInt("idUsuarioProfesor"));
        }
        
        DataBaseManager.closeConnection();
        
        return course;
    }

    @Override
    public int enrollStudentToCourse(EnrollToCourse enrollToCourse) throws SQLException {
        int response;
        String query = "INSERT INTO cursa (idCurso, idUsuarioEstudiante) values (?, ?)";
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setString(1, enrollToCourse.getCourseId());
        statement.setInt(2, enrollToCourse.getStudentId());
            
        response = statement.executeUpdate();
            
        return response;
    }
    
    public List<EnrollToCourse> getEnrollId(String idCourse) throws SQLException{
        String query = "SELECT * FROM cursa WHERE idCurso = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, idCourse);
        
        ResultSet studentsResult = statement.executeQuery();
        List<EnrollToCourse> enrolledStudents = new ArrayList<>();
        EnrollToCourse enrollToCourse = new EnrollToCourse();
        
        while(studentsResult.next()){
            enrollToCourse.setCourseId(studentsResult.getString("idCurso"));
            enrollToCourse.setStudentId(studentsResult.getInt("idUsuarioEstudiante"));
            enrollToCourse.setCoursesId(studentsResult.getInt("idCursa"));
            enrolledStudents.add(enrollToCourse);
        }
        
        DataBaseManager.closeConnection();
        
        return enrolledStudents;
    }

    @Override
    public int expellStudentFromCourse(EnrollToCourse expellFromCourse) throws SQLException {
        int result;
        String query = "DELETE from cursa WHERE idCurso = ? AND idUsuarioEstudiante = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, expellFromCourse.getCourseId());
        statement.setInt(2, expellFromCourse.getStudentId());

        result = statement.executeUpdate();
        
        return result;
    }

    @Override
    public int modifyCourse(Course course, int idSemester, int professorId) throws SQLException {
        int result;
        String query = "UPDATE curso SET idCurso = ?, idPeriodoEscolar = ?, nombre = ?, nrc = ?, seccion = ?, bloque = ?, estado = ?, idUsuarioProfesor = ? WHERE idCurso = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
          
        statement.setString(1, course.getCourseId());
        statement.setInt(2, idSemester);
        statement.setString(3, course.getName());
        statement.setString(4, course.getNrc());
        statement.setInt(5, course.getSection());
        statement.setInt(6, course.getBlock());
        statement.setString(7, course.getState());
        statement.setInt(8, professorId);
        statement.setString(9, course.getCourseId());

        result = statement.executeUpdate();
        
        return result;
    }

    @Override
    public int modifyCourseWithoutProfessor(Course course, int idSemester) throws SQLException {
        int result;
        String query = "UPDATE curso SET idCurso = ?, idPeriodoEscolar = ?, nombre = ?, nrc = ?, seccion = ?, bloque = ?, estado = ? WHERE idCurso = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
          
        statement.setString(1, course.getCourseId());
        statement.setInt(2, idSemester);
        statement.setString(3, course.getName());
        statement.setString(4, course.getNrc());
        statement.setInt(5, course.getSection());
        statement.setInt(6, course.getBlock());
        statement.setString(7, course.getState());
        statement.setString(8, course.getCourseId());

        result = statement.executeUpdate();
        
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
    
    @Override
    public void registerCourseTransaction(int professorId, Course course, List<EnrollToCourse> studentsList, int idSemester) throws SQLException{  
        //No se te olvide revisar los affectedRow esperados con los que actualmente pasaron
        
        try{
            DataBaseManager.getConnection().setAutoCommit(false);

            registerCourse(course, idSemester);
            
            for(int studentsCounter = 0; studentsCounter < studentsList.size(); studentsCounter++){
                enrollStudentToCourse(studentsList.get(studentsCounter));
            }
            
            enrollProfessorToCourse(professorId, course);

            DataBaseManager.getConnection().commit();
            
        } catch (SQLException exception){
            DataBaseManager.getConnection().rollback();
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, exception);
        } finally {
            DataBaseManager.closeConnection();
        }    
    }
    
    @Override
    public void modifyCourseTransaction(int professorId, Course course, List<EnrollToCourse> studentsList, List<EnrollToCourse> studentsToExpell) throws SQLException{  
        
        try{
            DataBaseManager.getConnection().setAutoCommit(false);
            
            if(professorId == 0){
                modifyCourseWithoutProfessor(course, course.getSemesterId());
            }else{
                modifyCourse(course, course.getSemesterId(), professorId);
            }           
            
            for(EnrollToCourse enrollToCourse : studentsList){
                System.out.println("EnrollToCourse, ModifyTransaction: " + enrollToCourse.getCoursesId());
                enrollStudentToCourse(enrollToCourse);
            }
            
            for(EnrollToCourse expellFromCourse : studentsToExpell){
                System.out.println("ExpellFromCourse, ModifyTransaction: " + expellFromCourse.getCoursesId());
                expellStudentFromCourse(expellFromCourse);
            }
            
            DataBaseManager.getConnection().commit();
            
        } catch (SQLException exception){
            DataBaseManager.getConnection().rollback();
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, exception);
        } finally {
            DataBaseManager.closeConnection();
        }    
    }

    @Override
    public List<Course> getCoursesPerProfessor(int professorId) throws SQLException {
        String query = "SELECT * FROM curso WHERE idUsuarioProfesor = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, professorId);
        
        ResultSet coursesResult = statement.executeQuery();
        List<Course> coursesList = new ArrayList<>();
        
        while(coursesResult.next()){
            Course course = new Course();
            
            course.manualSetOfCourseId("idCurso");
            course.setName(coursesResult.getString("nombre"));
            course.setNrc(coursesResult.getString("nrc"));
            course.setSection(coursesResult.getInt("seccion"));
            course.setState(coursesResult.getString("estado"));
            course.setBlock(coursesResult.getInt("bloque"));
            course.setSemesterId(coursesResult.getInt("idPeriodoEscolar"));
            course.setProfessorId(coursesResult.getInt("idUsuarioProfesor"));
            coursesList.add(course);
        } 
        DataBaseManager.closeConnection();
        
        return coursesList;
    }
}
