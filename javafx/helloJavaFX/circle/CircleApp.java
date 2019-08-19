package circle;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.scene.text.Text; 
import javafx.stage.Screen;
import javafx.scene.layout.StackPane;
import javafx.collections.ObservableList; 
import javafx.scene.Node;

public class CircleApp extends Application {
    public void start(Stage stage) {

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        Text text = new Text();      
      
        //Setting the text to be added. 
        text.setText(screenBounds.toString());
       
        //setting the position of the text 
        //text.setX(10); 
        //text.setY(10); 

        Circle circ = new Circle(40, 40, 30);
        
        StackPane root = new StackPane();

        ObservableList<Node>  list = root.getChildren();
        list.addAll(text, circ);

        //Scene scene = new Scene(root, 800, 300);
        Scene scene = new Scene(root);

        stage.setTitle("My JavaFX Application");
        stage.setScene(scene);
        stage.show();

    }
}
