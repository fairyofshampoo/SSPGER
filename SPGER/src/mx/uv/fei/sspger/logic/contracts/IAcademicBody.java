package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyMember;


public interface IAcademicBody {
    int addAcademicBody(AcademicBody academicBody) throws SQLException;
    AcademicBody getAcademicBody(String key) throws SQLException;
    int addAcademicBodyMember(AcademicBodyMember academicBodyMember, String academicBodyKey) throws SQLException;
    int updateAcademicBodyMember(AcademicBodyMember academicBodyMember, String academicBodyKey) throws SQLException;
    int updateAcademicBody(String key, AcademicBody academicBody) throws SQLException;
    int deleteAcademicBody(String key) throws SQLException;
    int addAcademicBodyTransaction(AcademicBody academicBody) throws SQLException;
    List<AcademicBody> getAllAcademicBody() throws SQLException;
    AcademicBodyMember getProfessor(String email) throws SQLException;
    int getExistenceAcademicBody(AcademicBody academicBody) throws SQLException;
}
