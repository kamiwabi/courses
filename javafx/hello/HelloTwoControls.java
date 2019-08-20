package hello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.text.Text; 
import javafx.scene.paint.Color;

public class HelloTwoControls extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("My First JavaFX App");

        Label label = new Label("Hello World, JavaFX !");

        Text text = new Text();      
 
        text.setX(10);
        text.setY(100);
        text.setText("Hello how are you"); 

        Group root = new Group();
        Scene s = new Scene(root, 300, 300, Color.GRAY);

        root.getChildren().add(label);
        root.getChildren().add(text);

        primaryStage.setScene(s);
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
