/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package spger;

import mx.uv.fei.sspger.logic.AccessAccountDAO;
import mx.uv.fei.sspger.logic.AccessAccount;
import mx.uv.fei.sspger.logic.ProjectDAO;
import mx.uv.fei.sspger.logic.Project;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author fabin
 */
public class SPGER {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AccessAccount account = new AccessAccount();
        account.setInstitutionalEMail("oalonso@uv.mx");
        account.setPassword("Miri0301");
        account.setPrivileges(1);

        AccessAccountDAO assignmentDao = new AccessAccountDAO();
        
        try{
            if(assignmentDao.addAccessAccount(account) > 0){
                System.out.println("Registro exitoso.");
            }else {
                System.out.println("Registro fallido.");
            }
        }catch (SQLException accessAcountRegisterError){
            System.out.println("Error al registrar la cuenta de acceso");
        }
        
        try{
            List<AccessAccount> accessAccountList = new ArrayList<>();
            AccessAccountDAO accessAccountDAO = new AccessAccountDAO();

            accessAccountList = accessAccountDAO.getAllAccessAccounts();

            for(int i = 0 ; i < accessAccountList.size() ; i++){
               System.out.println(accessAccountList.get(i).getInstitutionalEMail());
            }
        }catch(SQLException accessAcountRegisterError){
            System.out.println("Error al obtener todas las cuentas de acceso");
        }
        
        Project project = new Project();
        project.setName("Nombre1");
        project.setDescription("Descripcion ejemplo");
        project.setExpectedResults("Resultados esperados ejemplo");
        project.setDuration(12);
        project.setNotes("Notas ejemplo");
        project.setRequeriments("Requisitos ejemplo");
        project.setBibliography("Bibliografia ejemplo");

        ProjectDAO projectDao = new ProjectDAO();
        
        try{
            if(projectDao.addProject(project, "1", "L2" ) > 0){
                System.out.println("Registro exitoso.");
            }else {
                System.out.println("Registro fallido.");
            }
        }catch (SQLException projectRegisterError){
            System.out.println("Error al registrar anteproyecto");
        }
        
        try{
            List<Project> ProjectList = new ArrayList<>();
            ProjectDAO ProjectDAO = new ProjectDAO();

            ProjectList = ProjectDAO.getAllProjects();

            for(int i = 0 ; i < ProjectList.size() ; i++){
               System.out.println(ProjectList.get(i).getName());
            }
        }catch(SQLException accessAcountRegisterError){
            System.out.println("Error al obtener todos los anteproyectos");
        }
    }
    }
