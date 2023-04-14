package mx.uv.fei.sspger.logic;


import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author fabin
 */
public interface IAcademicBody {
    int addAcademicBody (AcademicBody academicBody) throws SQLException;
    AcademicBody getAcademicBody(String id) throws SQLException;
    List<AcademicBody> getAllAcademicBody() throws SQLException;
}
