package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;


public class ProfessorCourseManagerController implements Initializable {
    public static int professorId;
    
    @FXML
    private ComboBox<?> cbxCourseFilter;

    @FXML
    private ImageView imgAcademy;

    @FXML
    private ImageView imgCourse;

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgProject;
    
    @FXML
    private GridPane gpCourseTable;
    
    int column = 0;
    int row = 0;
    
    @Override//Pensarlo hacer con un GRIDPANEL, NO SE TE OLVIDE, MEJORARA EL COMO SE MUESTRAN LOS DATOS.
    public void initialize(URL url, ResourceBundle rb) {
        List<Course> professorCourses = getProfessorCourses();
        try{
            for (int card = 0; card < professorCourses.size(); card++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/CourseCard.fxml"));
                AnchorPane courseCard = fxmlLoader.load();
                CourseCardController courseCardController = fxmlLoader.getController();
                courseCardController.setCourseData(professorCourses.get(card));
                
                if(column == 2){
                    column = 0;
                    row++;
                }
                
                gpCourseTable.add(courseCard, column++, row);
                GridPane.setMargin(courseCard, new Insets(10));
                
            }
        } catch (IOException ioException){
            Logger.getLogger(ProfessorCourseManagerController.class.getName()).log(Level.SEVERE, null, ioException);
        }
        
    }    
    
    private List<Course> getProfessorCourses (){
        CourseDAO courseDao = new CourseDAO();
        List<Course> courses = new ArrayList<>();
        
        try{
            courses = courseDao.getCoursesPerProfessor(professorId);
        } catch (SQLException sqlException){
            Logger.getLogger(ProfessorCourseManagerController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        
        return courses;
    }
}
