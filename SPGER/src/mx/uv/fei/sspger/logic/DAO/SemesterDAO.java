package mx.uv.fei.sspger.logic.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.Semester;
import mx.uv.fei.sspger.logic.contracts.ISemester;

public class SemesterDAO implements ISemester{

    @Override
    public Semester getSemesterPerStartDate(Semester semester) throws SQLException {
        String query = "SELECT * FROM periodo_escolar WHERE fechaInicio = ?";
        
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setDate(1, semester.getStartDate());
        
        ResultSet semesterResult = statement.executeQuery();
        Semester returnSemester = new Semester();
        
        semesterResult.next();
        
        returnSemester.setSemesterId(semesterResult.getInt("idPeriodoEscolar"));
        returnSemester.setStartDate(semesterResult.getDate("fechaInicio"));
        returnSemester.setDeadline(semesterResult.getDate("fechaFin"));
        
        DataBaseManager.closeConnection();
        
        return returnSemester;
    }

    @Override
    public List<Semester> getAvailableSemesters() throws SQLException {
        String query = "SELECT * FROM periodo_escolar WHERE NOW() BETWEEN fechaInicio AND fechaFin OR NOW() < fechaInicio";
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet semesterResult = statement.executeQuery(query);
        List<Semester> semesterList = new ArrayList<>();
        
        while(semesterResult.next()){
            Semester semester = new Semester();
            
            semester.setSemesterId(semesterResult.getInt("idPeriodoEscolar"));
            semester.setStartDate(semesterResult.getDate("fechaInicio"));
            semester.setDeadline(semesterResult.getDate("fechaFin"));
            semesterList.add(semester);
        } 
        DataBaseManager.closeConnection();
        
        return semesterList;
    }

    @Override
    public Semester getSemesterPerId(int semesterId) throws SQLException{
        String query = "SELECT * FROM periodo_escolar WHERE idPeriodoEscolar = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, semesterId);
        
        ResultSet semesterResult = statement.executeQuery();
        
        Semester semester = new Semester();
        
        semesterResult.next();
        
        semester.setSemesterId(semesterResult.getInt("idPeriodoEscolar"));
        semester.setStartDate(semesterResult.getDate("fechaInicio"));
        semester.setDeadline(semesterResult.getDate("fechaFin"));
        
        DataBaseManager.closeConnection();
        
        return semester;
    }
}