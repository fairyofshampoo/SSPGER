/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mx.uv.fei.sspger.GUI;

import mx.uv.fei.sspger.logic.AssignmentDAO;
import mx.uv.fei.sspger.logic.Assignment;
import java.sql.Timestamp;
import java.sql.SQLException;
/**
 *
 * @author fabin
 */
public class SPGER {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Assignment assignment = new Assignment();
        assignment.setTitle("Primer asignación");
        assignment.setDeadline(Timestamp.valueOf("2023-10-19 10:23:54"));
        assignment.setStartDate(Timestamp.valueOf("2023-8-19 20:00:00"));
        assignment.setDescription("Ejemplo de descripción");
        assignment.setPublicationDate(Timestamp.valueOf("2023-8-19 10:23:54"));
        
        AssignmentDAO assignmentDao = new AssignmentDAO();
        
        try{
            if(assignmentDao.registerAssignment(assignment, "1") > 0){
                System.out.println("Registro exitoso.");
            }else {
                System.out.println("Registro fallido.");
            }
        }catch (SQLException assignmentRegisterError){
            System.out.println("Error al registrar la asignación");
        }
    }
    
}
