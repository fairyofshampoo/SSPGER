 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mx.uv.fei.sspger.GUI;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.LgacDAO;

/**
 *
 * @author fabin
 */
public class SPGER {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException{
        Lgac lgac = new Lgac();
        LgacDAO lgacDao = new LgacDAO();
        
        lgac.setId("L6");
        lgac.setName("Tecnologías de software2");
        lgac.setDescription("Se orienta al estudio de diversas propiedades2, enfoques,...");

        try{
            if(lgacDao.addLgac(lgac) > 0){
                System.out.println("LGAC registrado correctamente");
            }else{
                System.out.println("LGAC no se pudo registrar");
            }
        }catch(SQLException exception){
            System.out.println("Error de conectividad con la base de datos");
        }
        
        Lgac lgacAux = new Lgac();
        lgacAux = lgacDao.getLgac("L1");
        System.out.println("\n" + lgacAux.getName() + "\n");
        
        List<Lgac> lgacList = new ArrayList<>();
        lgacList = lgacDao.getAllLgac();

        for(int i = 0 ; i < lgacList.size() ; i++){
            System.out.println(lgacList.get(i).getId());
        }
        
        
        AcademicBody academicBody = new AcademicBody();
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        
        academicBody.setId("UV-CA-129");
        academicBody.setName("Ingenieria y Tecnología de Software2");

        try{
            if(academicBodyDao.addAcademicBody(academicBody) > 0){
                System.out.println("Cuerpo académico registrado correctamente");
            }else{
                System.out.println("Cuerpo académico no se pudo registrar");
            }
        }catch(SQLException exception){
            System.out.println("Error de conectividad con la base de datos");
        }
        
        
        List<AcademicBody> academicBodyList = new ArrayList<>();
        academicBodyList = academicBodyDao.getAllAcademicBody();

        for(int i = 0 ; i < academicBodyList.size() ; i++){
            System.out.println(academicBodyList.get(i).getId());
        }
        
        AcademicBody academicBodyAux = new AcademicBody();
        academicBodyAux = academicBodyDao.getAcademicBody("UV-CA-128");
        System.out.println("\n" + academicBodyAux.getName() + "\n");
    }
    
}
