package tv;
 
import tv.Account;
 
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class CustomerStage extends Application {
 
  @Override
  public void start(Stage stage) {
 
      TableView<Account> table = new TableView<Account>();
 
      // Create column UserName (Data type of String).
      TableColumn<Account, String> userNameCol //
              = new TableColumn<Account, String>(" Name");
 
      // Create column Email (Data type of String).
      TableColumn<Account, String> emailCol//
              = new TableColumn<Account, String>("Email");
 
      // Create column FullName (Data type of String).
      TableColumn<Account, String> fullNameCol//
              = new TableColumn<Account, String>("Full Name");
 
      // Create 2 sub column for FullName.
      TableColumn<Account, String> firstNameCol//
              = new TableColumn<Account, String>("First Name");
 
      TableColumn<Account, String> lastNameCol //
              = new TableColumn<Account, String>("Last Name");
 
      // Add sub columns to the FullName
      fullNameCol.getColumns().addAll(firstNameCol, lastNameCol);
 
      // Active Column
      TableColumn<Account, Boolean> activeCol//
              = new TableColumn<Account, Boolean>("Active");
 
      // Defines how to fill data for each cell.
      // Get value from property of Account. .
      userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
      emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
      firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
      lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
      activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
    
      // Set Sort type for userName column
      userNameCol.setSortType(TableColumn.SortType.DESCENDING);
      lastNameCol.setSortable(false);
 
      // Display row data
      ObservableList<Account> list = getUserList();
      table.setItems(list);
 
      table.getColumns().addAll(userNameCol, emailCol, fullNameCol, activeCol);
 
      StackPane root = new StackPane();
      root.setPadding(new Insets(5));
      root.getChildren().add(table);
 
      stage.setTitle("TableView (o7planning.org)");
 
      Scene scene = new Scene(root, 450, 300);
      stage.setScene(scene);
      stage.show();
  }
 
  private ObservableList<Account> getUserList() {
 
      Account user1 = new Account(1L, "smith", "smith@gmail.com", //
              "Susan", "Smith", true);
      Account user2 = new Account(2L, "mcneil", "mcneil@gmail.com", //
              "Anne", "McNeil", true);
      Account user3 = new Account(3L, "white", "white@gmail.com", //
              "Kenvin", "White", false);
 
      ObservableList<Account> list = FXCollections.observableArrayList(user1, user2, user3);
      return list;
  }
 
  public static void main(String[] args) {
      launch(args);
  }
}
