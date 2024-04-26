package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyMember;

public interface IAcademicBody {

    AcademicBody getAcademicBody(String key) throws SQLException;

    int addAcademicBodyMember(List<AcademicBodyMember> memberList, String academicBodyKey) throws SQLException;

    int updateAcademicBody(String key, AcademicBody academicBody) throws SQLException;

    AcademicBodyMember getMemberPerIdProfessor(int idProfessor, String academicBodyKey) throws SQLException;

    List<AcademicBody> getAcademicBodiesFromProfessor(int professorId) throws SQLException;

    int addAcademicBodyTransaction(AcademicBody academicBody) throws SQLException;

    List<AcademicBody> getAllAcademicBody() throws SQLException;

    int getExistenceAcademicBody(String key) throws SQLException;

    AcademicBodyMember getAcademicBodyResponsible(String key) throws SQLException;

    List<AcademicBodyMember> getAcademicBodyMembers(String key) throws SQLException;

    int updateAcademicBodyStatus(int status, String key) throws SQLException;

    int deleteAcademicBodyMember(List<AcademicBodyMember> memberList) throws SQLException;

    List<AcademicBodyMember> getAllAcademicBodyMembers(String key) throws SQLException;

    int responsibleExistence(String key) throws SQLException;

    List<AcademicBody> getAcademicBodiesActive() throws SQLException;
}
