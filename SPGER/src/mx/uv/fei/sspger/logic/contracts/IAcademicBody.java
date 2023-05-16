package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyMember;


public interface IAcademicBody {
    int addAcademicBody(AcademicBody academicBody) throws SQLException;
    AcademicBody getAcademicBody(String key) throws SQLException;
    List<AcademicBody> getAllAcademicBody() throws SQLException;
    int updateAcademicBody(String key, AcademicBody academicBody) throws SQLException;
    int deleteAcademicBody(String key) throws SQLException;
    int addAcademicBodyMember(AcademicBodyMember academicBodyMember) throws SQLException;
    int updateAcademicBodyMember(AcademicBodyMember academicBodyMember) throws SQLException;
    int removeAcademicBodyMember(int id) throws SQLException;
    List<AcademicBodyMember> getAllAcademicBodyMember() throws SQLException;
    AcademicBodyMember getAcademicBodyMember(int id) throws SQLException;
    int getAcademicBodyMemberId(String academicBodyKey, int professorId) throws SQLException;
}
