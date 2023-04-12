/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.sspger.logic;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author miche
 */
public interface IProfessor {
    int registrar(Professor professor) throws SQLException;
    List <Professor> getProfessorList() throws SQLException;
}
