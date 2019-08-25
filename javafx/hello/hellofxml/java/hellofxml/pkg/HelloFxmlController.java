package pkg;

import java.net.URL;
import java.util.ResourceBundle;
 
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
 
public class HelloFxmlController
{
    @FXML
    // The reference of inputText will be injected by the FXML loader
    private TextField txtMasukan;
     
    // The reference of outputText will be injected by the FXML loader
    @FXML
    private TextArea taHasilInput;
     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     
    // Add a public no-args constructor
    public HelloFxmlController() 
    {
    }
     
    @FXML
    private void initialize() 
    {
    }
     
    @FXML
    private void printOutput() 
    {
        taHasilInput.setText(txtMasukan.getText());
    }
}
