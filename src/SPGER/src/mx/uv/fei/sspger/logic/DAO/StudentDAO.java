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

public class StudentDAO implements IStudent {

    private final int ERROR = -1;
    private final int VALUE_BY_DEFAULT = 0;
    private final String ADD_STUDENT_COMMAND = "insert into estudiante(correo_institucional, nombre, apellido, matricula) values(?,?,?,?)";
    private final String ADD_ACCESS_ACCOUNT_COMMAND = "insert into cuenta_acceso(correo, password, estado) values (?, SHA1(?), ?)";
    private final String GET_ALL_STUDENTS_QUERY = "SELECT * FROM estudiante";
    private final String GET_STUDENTS_BY_STATUS_QUERY = "SELECT estudiante.* FROM cuenta_acceso INNER JOIN estudiante ON cuenta_acceso.correo = estudiante.correo_institucional WHERE cuenta_acceso.estado = ?";
    private final String UPDATE_ACCESS_ACCOUNT_COMMAND = "UPDATE cuenta_acceso SET correo = ?, estado = ? WHERE correo = ?";
    private final String UPDATE_STUDENT_COMMAND = "UPDATE estudiante SET nombre = ?, apellido = ?, matricula = ? WHERE correo_institucional = ?";
    private final String CHANGE_STUDENT_STATUS_QUERY = "UPDATE cuenta_acceso SET estado = ? WHERE correo = ?";
    private final String SEARCH_STUDENT_BY_NAME = "SELECT * FROM cuenta_acceso INNER JOIN estudiante ON cuenta_acceso.correo = estudiante.correo_institucional WHERE CONCAT(estudiante.nombre, ?, estudiante.apellido) LIKE ?";
    private final String GET_STUDENT_BY_ID = "SELECT * FROM estudiante INNER JOIN cuenta_acceso ON cuenta_acceso.correo = estudiante.correo_institucional WHERE idUsuarioEstudiante = ?";
    private final String GET_STUDENTS_BY_RECEPTIONAL_WORK = "SELECT nombre, apellido, idEstudiante, correo_institucional FROM estudiante_trabajo_recepcional INNER JOIN estudiante ON estudiante.idUsuarioEstudiante = estudiante_trabajo_recepcional.idEstudiante WHERE idTrabajoRecepcional = ?";
    private final String GET_STUDENTS_BY_PROJECT = "SELECT estudiante.idUsuarioEstudiante, nombre, apellido, correo_institucional FROM estudiante INNER JOIN estudiante_anteproyecto ON estudiante_anteproyecto.idEstudiante = estudiante.idUsuarioEstudiante WHERE idAnteproyecto = ? AND estadoEstudiante = ?";
    private final String UPDATE_ACCESS_ACCOUNT_WITH_PASSWORD_COMMAND = "UPDATE cuenta_acceso SET correo = ?, password = SHA1(?), estado = ? WHERE correo = ?";
    private final String GET_STUDENT_PER_COURSE = "SELECT idUsuarioEstudiante, nombre, apellido, matricula, correo_institucional "
        + "FROM estudiante NATURAL JOIN cursa WHERE cursa.idCurso = ?";
    private final String GET_STUDENTS_NOT_IN_COURSE = "SELECT idUsuarioEstudiante, nombre, apellido, matricula, correo_institucional FROM estudiante INNER JOIN cuenta_acceso ON correo = correo_institucional WHERE estado = 1 AND idUsuarioEstudiante NOT IN (SELECT idUsuarioEstudiante FROM sspger.cursa NATURAL JOIN sspger.curso WHERE curso.estado = ?);";

    private Student setUserStudentData(ResultSet studentResult) throws SQLException {
        Student student = new Student();

        student.setId(ERROR);
        if (studentResult.next()) {
            student.setEMail(studentResult.getString("correo_institucional"));
            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setRegistrationTag(studentResult.getString("matricula"));
            student.setId(studentResult.getInt("idUsuarioEstudiante"));
            student.setStatus(studentResult.getInt("estado"));
        }

        return student;
    }

    /**
     *
     * @param idStudent to get a student from the database
     * @return a student with id = -1 if there's no result
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public Student getStudent(int idStudent) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_STUDENT_BY_ID);

        statement.setInt(1, idStudent);

        Student student = new Student();
        ResultSet studentResult = statement.executeQuery();
        student = setUserStudentData(studentResult);

        DataBaseManager.closeConnection();

        return student;
    }

    /**
     *
     * @param courseId to get a student in the course selected
     * @return an empty list if there's no result or a list full of students if
     * there is
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Student> getStudentsPerCourse(String courseId) throws SQLException {

        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_STUDENT_PER_COURSE);

        statement.setString(1, courseId);

        ResultSet studentResult = statement.executeQuery();
        List<Student> studentList = new ArrayList<>();

        while (studentResult.next()) {
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

    private int updateStudentData(Student student) throws SQLException {
        PreparedStatement studentStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_STUDENT_COMMAND);
        studentStatement.setString(1, student.getName());
        studentStatement.setString(2, student.getLastName());
        studentStatement.setString(3, student.getRegistrationTag());
        studentStatement.setString(4, student.getEMail());

        return studentStatement.executeUpdate();
    }

    /**
     *
     * @param idReceptionalWork to get all the students assigned to a
     * receptional work
     * @return an empty list if there's no result or a list full of students if
     * there is
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Student> getStudentPerReceptionalWork(int idReceptionalWork) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_STUDENTS_BY_RECEPTIONAL_WORK);

        statement.setInt(1, idReceptionalWork);

        ResultSet studentResult = statement.executeQuery();
        List<Student> studentList = new ArrayList<>();

        while (studentResult.next()) {
            Student student = new Student();

            student.setName(studentResult.getString("nombre"));
            student.setLastName(studentResult.getString("apellido"));
            student.setId(studentResult.getInt("idEstudiante"));
            student.setEMail(studentResult.getString("correo_institucional"));

            studentList.add(student);
        }

        DataBaseManager.closeConnection();

        return studentList;
    }

    /**
     *
     * @return an empty list if there's no result or a list full of students if
     * there is
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Student> getAvailableStudentsNotInCourse() throws SQLException {
        String query = GET_STUDENTS_NOT_IN_COURSE;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setString(1, "Disponible");
        ResultSet studentResult = statement.executeQuery();
        List<Student> studentList = new ArrayList<>();

        while (studentResult.next()) {
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

    /**
     *
     * @param student to save a new student in the database
     * @return 2 if the transaction is successful
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int addStudentTransaction(Student student) throws SQLException {
        int response = ERROR;
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(ADD_ACCESS_ACCOUNT_COMMAND);

            accountStatement.setString(1, student.getEMail());
            accountStatement.setString(2, student.getPassword());
            accountStatement.setInt(3, student.getStatus());

            response = accountStatement.executeUpdate();

            if (response != ERROR) {
                PreparedStatement studentStatement = DataBaseManager.getConnection().prepareStatement(ADD_STUDENT_COMMAND);

                studentStatement.setString(1, student.getEMail());
                studentStatement.setString(2, student.getName());
                studentStatement.setString(3, student.getLastName());
                studentStatement.setString(4, student.getRegistrationTag());

                response = response + studentStatement.executeUpdate();
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            response = ERROR;
            DataBaseManager.getConnection().rollback();
            throw new SQLException("Error en transacción" + sqlException.getMessage());
            
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    /**
     *
     * @return an empty list if there's no result or a list full of students if
     * there is
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Student> getAllStudents() throws SQLException {
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet studentResult = statement.executeQuery(
            GET_ALL_STUDENTS_QUERY);

        List<Student> studentList = new ArrayList<>();

        while (studentResult.next()) {
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

    /**
     *
     * @param eMail to know what student needs the update
     * @param student to update the student data
     * @return 2 if it's successful or 0 if it's not
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int updateStudentTransaction(String eMail, Student student) throws SQLException {
        int response = VALUE_BY_DEFAULT;
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACCESS_ACCOUNT_COMMAND);

            accountStatement.setString(1, student.getEMail());
            accountStatement.setInt(2, student.getStatus());
            accountStatement.setString(3, eMail);

            response = accountStatement.executeUpdate();

            if (response != VALUE_BY_DEFAULT) {
                response = response + updateStudentData(student);
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            response = ERROR;
            DataBaseManager.getConnection().rollback();
            throw new SQLException("Error en transacción" + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    /**
     *
     * @param eMail to know what student needs the update
     * @param student to update the student data and password
     * @return 2 if it's successful or 0 if it's not
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int updateStudentWithPasswordTransaction(String eMail, Student student) throws SQLException {
        int response = VALUE_BY_DEFAULT;
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACCESS_ACCOUNT_WITH_PASSWORD_COMMAND);

            accountStatement.setString(1, student.getEMail());
            accountStatement.setString(2, student.getPassword());
            accountStatement.setInt(3, student.getStatus());
            accountStatement.setString(4, eMail);

            response = accountStatement.executeUpdate();

            if (response != VALUE_BY_DEFAULT) {
                response = response + updateStudentData(student);
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            response = ERROR;
            DataBaseManager.getConnection().rollback();
            throw new SQLException("Error en transacción" + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    /**
     *
     * @param email to search the student and update
     * @param status to update the status
     * @return the number of rows afected, 1 if it's successful
     * @throws SQLException if there's an error in the database.
     */
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

    /**
     *
     * @param status to get all the students that are in that status from the
     * database
     * @return an empty list if there's no result or a list full of students if
     * there is
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Student> getStudentsByStatus(int status) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().
            prepareStatement(GET_STUDENTS_BY_STATUS_QUERY);

        statement.setInt(1, status);

        ResultSet studentResult = statement.executeQuery();

        List<Student> studentList = new ArrayList<>();

        while (studentResult.next()) {
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

    private List<Student> setStudentList(ResultSet studentResult) throws SQLException {
        List<Student> studentList = new ArrayList<>();
        while (studentResult.next()) {
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

    /**
     *
     * @param name to find a list of students with that name
     * @return an empty list if there's no result or a list full of students if
     * there is
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Student> searchStudentbyName(String name) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(SEARCH_STUDENT_BY_NAME);
        String blankSpace = " ";
        String searchName = name + "%";

        statement.setString(1, blankSpace);
        statement.setString(2, searchName);

        List<Student> studentList = new ArrayList<>();
        ResultSet studentResult = statement.executeQuery();
        studentList = setStudentList(studentResult);

        DataBaseManager.closeConnection();

        return studentList;
    }

    /**
     *
     * @param idProject to find the students that have that project
     * @param estadoEstudiante and that are in this status
     * @return an empty list if there's no result or a list full of students if
     * there is
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Student> getStudentsByProjectByStatus(int idProject, String estadoEstudiante) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_STUDENTS_BY_PROJECT);

        statement.setInt(1, idProject);
        statement.setString(2, estadoEstudiante);

        ResultSet studentResult = statement.executeQuery();

        List<Student> studentList = new ArrayList<>();

        while (studentResult.next()) {
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
