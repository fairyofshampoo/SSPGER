package mx.uv.fei.sspger.GUI;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SPGER extends Application{

    private static Scene scene;
    
    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        scene = new Scene (root);
        
        stage.setTitle("Sistema de Gestión de Proyecto Guiado y Experiencia Recepcional");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void setRoot(String fxml){
        try{
            scene.setRoot(loadFXML(fxml));
        } catch (IOException ioException){
            Logger.getLogger(SPGER.class.getName()).log(Level.SEVERE, null, ioException);
        }
        
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SPGER.class.getResource(fxml));
        return fxmlLoader.load();
    }
    
}
