package mx.uv.fei.sspger.logic;


import java.sql.SQLException;

/**
 *
 * @author fabin
 */
public interface IAcademicBody {
    int addAcademicBody (AcademicBody academicBody) throws SQLException;
    AcademicBody getAcademicBody() throws SQLException;
}
