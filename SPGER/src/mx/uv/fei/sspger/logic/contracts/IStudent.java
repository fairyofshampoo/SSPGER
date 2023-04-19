/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Student;

/**
 *
 * @author miche
 */
public interface IStudent {
    int registrar(Student estudiante) throws SQLException;
    List <Student> getEstudianteList() throws SQLException;
    
}
