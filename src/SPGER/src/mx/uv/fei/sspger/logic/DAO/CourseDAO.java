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

public class CourseDAO implements ICourse {

    private final String FINISH_COURSE = "UPDATE curso SET estado = 'Finalizado' WHERE idCurso = ?";
    private final String SEARCH_COURSE = "SELECT COUNT(*) AS searchedCourse FROM curso WHERE idCurso = ?";
    private final int ERROR_IN_COUNT = -1;
    private final String INVALID_ID = "InvaId";
    private final String SEARCH_COURSE_BY_STUDENT = "SELECT * FROM curso INNER JOIN cursa ON curso.idCurso = cursa.idCurso WHERE cursa.idUsuarioEstudiante = ?";
    private final String REGISTER_COURSE = "INSERT INTO curso(idCurso, idPeriodoEscolar, nombre, nrc, seccion, bloque, estado) values (?,?,?,?,?,?,?)";
    private final String GET_COURSE_BY_STATE = "SELECT idCurso, nombre, nrc, seccion, estado, idPeriodoEscolar, bloque FROM curso WHERE estado = ?";
    private final String GET_ALL_COURSES = "SELECT idCurso, nombre, nrc, seccion, estado, bloque, idPeriodoEscolar, idUsuarioProfesor FROM curso";
    private final String GET_COURSE_BY_ID = "SELECT idCurso, nombre, nrc, seccion, estado, bloque, idPeriodoEscolar, idUsuarioProfesor FROM curso WHERE idCurso = ?";
    private final String ENROLL_STUDENT_TO_COURSE = "INSERT INTO cursa (idCurso, idUsuarioEstudiante) values (?, ?)";
    private final String GET_ENROLL_ID = "SELECT idCurso, idUsuarioEstudiante, idCursa FROM cursa WHERE idCurso = ?";
    private final String EXPELL_STUDENT_FROM_COURSE = "DELETE from cursa WHERE idCurso = ? AND idUsuarioEstudiante = ?";
    private final String MODIFY_COURSE = "UPDATE curso SET idCurso = ?, idPeriodoEscolar = ?, nombre = ?, nrc = ?, seccion = ?, bloque = ?, estado = ?, idUsuarioProfesor = ? WHERE idCurso = ?";
    private final String ENROLL_PROFESSOR_TO_COURSE = "UPDATE curso SET idUsuarioProfesor = ? WHERE (idCurso = ?)";
    private final String GET_COURSE_PER_PROFESSOR = "SELECT idCurso, nombre, nrc, seccion, bloque, idPeriodoEscolar FROM curso WHERE idUsuarioProfesor = ?";
    private final String GET_COURSES_PER_STATE_AND_PROFESSOR = "SELECT * FROM curso WHERE estado = ? AND idUsuarioProfesor = ?";

    private int registerCourse(Course course, int idSemester) throws SQLException {
        int result;

        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(REGISTER_COURSE);

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

    /**
     * @param state to search in the database for the courses with that state.
     * @return a List of courses if found or a empty list if not found any
     * match.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Course> getCoursesPerState(String state) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_COURSE_BY_STATE);

        statement.setString(1, state);

        ResultSet coursesResult = statement.executeQuery();
        List<Course> coursesList = new ArrayList<>();

        while (coursesResult.next()) {
            Course course = new Course();

            course.manualSetOfCourseId(coursesResult.getString("idCurso"));
            course.setName(coursesResult.getString("nombre"));
            course.setNrc(coursesResult.getString("nrc"));
            course.setSection(coursesResult.getInt("seccion"));
            course.setState(coursesResult.getString("estado"));
            course.setBlock(coursesResult.getInt("bloque"));
            course.setSemesterId(coursesResult.getInt("idPeriodoEscolar"));
            coursesList.add(course);
        }
        DataBaseManager.closeConnection();

        return coursesList;
    }

    /**
     * @return A List with all the fields of all the courses.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Course> getAllCourses() throws SQLException {
        DataBaseManager.getConnection();

        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet coursesResult = statement.executeQuery(GET_ALL_COURSES);
        List<Course> coursesList = new ArrayList<>();

        while (coursesResult.next()) {
            Course course = new Course();

            course.manualSetOfCourseId(coursesResult.getString("idCurso"));
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

    /**
     * @param idCourse to search in the database for a course with that id.
     * @return a course object with all it's fields or a course with an errorId
     * if not found.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public Course getCourseById(String idCourse) throws SQLException {
        DataBaseManager.getConnection();

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_COURSE_BY_ID);

        statement.setString(1, idCourse);
        ResultSet coursesResult = statement.executeQuery();

        Course course = new Course();
        course.manualSetOfCourseId(INVALID_ID);

        if (coursesResult.next()) {
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

    /**
     * @param idCourse to search in the database for a course with that id
     * @return an int (1) if it found a course, (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int searchCourseCount(String idCourse) throws SQLException {
        String query = SEARCH_COURSE;
        int courseCount = 0;

        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setString(1, idCourse);

        ResultSet searchCount = statement.executeQuery();

        if (searchCount.next()) {
            courseCount = searchCount.getInt("searchedCourse");
        } else {
            courseCount = ERROR_IN_COUNT;
        }

        return courseCount;
    }

    /**
     * @param enrollToCourse to register an enrolled student with a course.
     * @return an int (1) if register succeded (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int enrollStudentToCourse(EnrollToCourse enrollToCourse) throws SQLException {
        int response;

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(ENROLL_STUDENT_TO_COURSE);

        statement.setString(1, enrollToCourse.getCourseId());
        statement.setInt(2, enrollToCourse.getStudentId());

        response = statement.executeUpdate();

        DataBaseManager.closeConnection();

        return response;
    }

    private int enrollStudentToCourseTransaction(EnrollToCourse enrollToCourse) throws SQLException {
        int response;

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(ENROLL_STUDENT_TO_COURSE);

        statement.setString(1, enrollToCourse.getCourseId());
        statement.setInt(2, enrollToCourse.getStudentId());

        response = statement.executeUpdate();

        return response;
    }

    @Override
    public List<EnrollToCourse> getEnrollId(String idCourse) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ENROLL_ID);

        statement.setString(1, idCourse);

        ResultSet studentsResult = statement.executeQuery();
        List<EnrollToCourse> enrolledStudents = new ArrayList<>();
        EnrollToCourse enrollToCourse = new EnrollToCourse();

        while (studentsResult.next()) {
            enrollToCourse.setCourseId(studentsResult.getString("idCurso"));
            enrollToCourse.setStudentId(studentsResult.getInt("idUsuarioEstudiante"));
            enrollToCourse.setCoursesId(studentsResult.getInt("idCursa"));
            enrolledStudents.add(enrollToCourse);
        }

        DataBaseManager.closeConnection();

        return enrolledStudents;
    }

    /**
     * @param expellFromCourse to delete the row of that that makes a match
     * @return an int (1) if delete succeded (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int expellStudentFromCourse(EnrollToCourse expellFromCourse) throws SQLException {
        int result;

        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(EXPELL_STUDENT_FROM_COURSE);

        statement.setString(1, expellFromCourse.getCourseId());
        statement.setInt(2, expellFromCourse.getStudentId());

        result = statement.executeUpdate();

        return result;
    }

    /**
     * @param course to modify the course with this new fields.
     * @param professorId to add the professor to the course.
     * @param courseId to search for the course that wants to be modified.
     * @return an int (1) if modification succeded (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int modifyCourse(Course course, int professorId, String courseId) throws SQLException {
        int result;

        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(MODIFY_COURSE);

        statement.setString(1, course.getCourseId());
        statement.setInt(2, course.getSemesterId());
        statement.setString(3, course.getName());
        statement.setString(4, course.getNrc());
        statement.setInt(5, course.getSection());
        statement.setInt(6, course.getBlock());
        statement.setString(7, course.getState());
        statement.setInt(8, professorId);
        statement.setString(9, courseId);

        result = statement.executeUpdate();

        return result;
    }

    private int enrollProfessorToCourse(int professorId, Course course) throws SQLException {
        int result;

        DataBaseManager.getConnection();

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(ENROLL_PROFESSOR_TO_COURSE);

        statement.setInt(1, professorId);
        statement.setString(2, course.getCourseId());

        statement.executeUpdate();

        result = statement.executeUpdate();

        return result;
    }

    /**
     * @param professorId to register the professor to the course.
     * @param course to register a course in the database.
     * @param studentsList to register the enrollment between student and
     * course.
     * @param idSemester to register the the course.
     * @return an int (>0) if register succeded (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int registerCourseTransaction(int professorId, Course course, List<EnrollToCourse> studentsList, int idSemester) throws SQLException {
        int result = 0;
        try {
            DataBaseManager.getConnection().setAutoCommit(false);

            result += registerCourse(course, idSemester);

            for (int studentsCounter = 0; studentsCounter < studentsList.size(); studentsCounter++) {
                result += enrollStudentToCourseTransaction(studentsList.get(studentsCounter));
            }

            enrollProfessorToCourse(professorId, course);

            DataBaseManager.getConnection().commit();

        } catch (SQLException sqlException) {
            DataBaseManager.getConnection().rollback();
            result = ERROR_IN_COUNT;
            throw new SQLException("Error en la transacción " + sqlException.getMessage());
        } finally {
            DataBaseManager.closeConnection();
        }
        return result;
    }

    /**
     * @param professorId to search for courses with this professorId
     * @return a list with courses if matches were found, an empty list if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Course> getCoursesPerProfessor(int professorId) throws SQLException {

        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_COURSE_PER_PROFESSOR);

        statement.setInt(1, professorId);

        ResultSet coursesResult = statement.executeQuery();
        List<Course> coursesList = new ArrayList<>();

        while (coursesResult.next()) {
            Course course = new Course();

            course.manualSetOfCourseId(coursesResult.getString("idCurso"));
            course.setName(coursesResult.getString("nombre"));
            course.setNrc(coursesResult.getString("nrc"));
            course.setSection(coursesResult.getInt("seccion"));
            course.setBlock(coursesResult.getInt("bloque"));
            course.setSemesterId(coursesResult.getInt("idPeriodoEscolar"));
            coursesList.add(course);
        }
        DataBaseManager.closeConnection();

        return coursesList;
    }

    /**
     * @param state to search for courses in the database with this state.
     * @param professorId to search for courses in the database with this
     * professorId.
     * @return a list with courses if matches were found, an empty list if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Course> getCoursesPerStateAndProfessor(String state, int professorId) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_COURSES_PER_STATE_AND_PROFESSOR);

        statement.setString(1, state);
        statement.setInt(2, professorId);

        ResultSet coursesResult = statement.executeQuery();
        List<Course> coursesList = new ArrayList<>();

        while (coursesResult.next()) {
            Course course = new Course();

            course.manualSetOfCourseId(coursesResult.getString("idCurso"));
            course.setName(coursesResult.getString("nombre"));
            course.setNrc(coursesResult.getString("nrc"));
            course.setSection(coursesResult.getInt("seccion"));
            course.setBlock(coursesResult.getInt("bloque"));
            course.setSemesterId(coursesResult.getInt("idPeriodoEscolar"));
            coursesList.add(course);
        }
        DataBaseManager.closeConnection();

        return coursesList;
    }

    /**
     * @param courseId to search for a course in the database with this
     * courseId.
     * @return an int (1) if succeded (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int disableCourse(String courseId) throws SQLException {
        String query = FINISH_COURSE;
        int result = 0;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setString(1, courseId);

        result = statement.executeUpdate();

        return result;
    }

    /**
     * @param idStudent to search for a course in the database with this
     * studentId.
     * @return a list with courses if matches were found, an empty list if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Course> getCoursebyStudent(int idStudent) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(SEARCH_COURSE_BY_STUDENT);

        statement.setInt(1, idStudent);

        ResultSet coursesResult = statement.executeQuery();
        List<Course> coursesList = new ArrayList<>();

        while (coursesResult.next()) {
            Course course = new Course();

            course.manualSetOfCourseId(coursesResult.getString("idCurso"));
            course.setName(coursesResult.getString("nombre"));
            course.setNrc(coursesResult.getString("nrc"));
            course.setSection(coursesResult.getInt("seccion"));
            course.setBlock(coursesResult.getInt("bloque"));
            course.setSemesterId(coursesResult.getInt("idPeriodoEscolar"));
            course.setState(coursesResult.getString("estado"));
            coursesList.add(course);
        }
        DataBaseManager.closeConnection();

        return coursesList;
    }
}
