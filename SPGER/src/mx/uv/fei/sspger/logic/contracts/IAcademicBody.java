package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyMember;


public interface IAcademicBody {
    int addAcademicBody(AcademicBody academicBody) throws SQLException;
    AcademicBody getAcademicBody(String key) throws SQLException;
    int addAcademicBodyMember(AcademicBodyMember academicBodyMember, String academicBodyKey) throws SQLException;
    int updateAcademicBody(String key, AcademicBody academicBody) throws SQLException;
    int deleteAcademicBody(String key) throws SQLException;
    int addAcademicBodyTransaction(AcademicBody academicBody) throws SQLException;
    List<AcademicBody> getAllAcademicBody() throws SQLException;
    AcademicBodyMember getProfessor(String email) throws SQLException;
    int getExistenceAcademicBody(String key) throws SQLException;
    AcademicBodyMember getAcademicBodyResponsible(String key) throws SQLException;
    List<AcademicBodyMember> getAcademicBodyMembers(String key) throws SQLException;
    int updateAcademicBodyStatus(int status, String key) throws SQLException;
    int deleteAcademicBodyMember(String key, AcademicBodyMember academicBodyMember) throws SQLException;
    int updateAcademicBodyTransaction(String key, AcademicBody academicBody, List<AcademicBodyMember> oldMembers) throws SQLException;
}
