package mx.uv.fei.sspger.GUI;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApplication extends Application{

    private static Scene scene;
    
    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddCourse.fxml"));
        
        scene = new Scene (root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void setRoot(String fxml) throws IOException{
        try{
            scene.setRoot(loadFXML(fxml));
        } catch (IOException ioException){
            //logger
            System.out.println(ioException);
        }
        
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
}
