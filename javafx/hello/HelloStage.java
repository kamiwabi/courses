package hello;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class HelloStage extends Application
{
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    public void start(final Stage stage)
    {
        Button btnNone = new Button("Will display non modal window");
        btnNone.setOnAction(new EventHandler <ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                showDialog(stage, Modality.NONE);
            }
        });

        Button btnWindowModal = new Button("Will display modal window");
        btnWindowModal.setOnAction(new EventHandler <ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                showDialog(stage, Modality.WINDOW_MODAL);
            }
        });

        Button btnAppModal = new Button("Will display app modal");
        btnAppModal.setOnAction(new EventHandler <ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                showDialog(stage, Modality.APPLICATION_MODAL);
            }
        });

        // Create the VBox
        VBox root = new VBox();
        // Add the children to the VBox
        root.getChildren().addAll(btnNone, btnWindowModal, btnAppModal);

        // Create the Scene
        Scene scene = new Scene(root, 300, 200);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("The Primary Stage");
        // Display the Stage
        stage.show();
    }

    private void showDialog(Window owner, Modality modality)
    {
        // Create a Stage with specified owner and modality
        final Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(modality);

        // Create the Label
        Label modalityLabel = new Label(modality.toString());
        // Create the Button
        Button closeButton = new Button("Close");
        // Add the EventHandler to the Button
        closeButton.setOnAction(new EventHandler <ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                stage.close();
            }
        });

        // Create the VBox
        VBox root = new VBox();
        // Add the Children to the VBox
        root.getChildren().addAll(modalityLabel, closeButton);

        // Create the Scene
        Scene scene = new Scene(root, 200, 100);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("A Dialog Box");
        // Display the Stage
        stage.show();
    }
}
