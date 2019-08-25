package pkg;

import java.io.FileInputStream;
import java.io.IOException;
 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
 
import pkg.HelloFxmlController;

public class HelloFxml extends Application
{
    public static void main(String[] args) 
    {
        Application.launch(args);
    }
     
    @Override
    public void start(Stage stage) throws IOException 
    {

        HelloFxmlController controller = new HelloFxmlController();
        // Create the FXMLLoader 
        FXMLLoader loader = new FXMLLoader();

        AnchorPane root = (AnchorPane) loader.load(getClass().getResource("HelloFxml.fxml"));
        loader.setController(controller);

        // Create the Scene
        Scene scene = new Scene(root);
        // Set the Scene to the Stage
        stage.setScene(scene);
        // Set the Title to the Stage
        stage.setTitle("A SceneBuilder Example with a Controller");
        // Display the Stage
        stage.show();
    }
 
}
