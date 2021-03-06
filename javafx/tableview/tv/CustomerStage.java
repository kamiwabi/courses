package tv;

import tv.Account;
import tv.AlertHelper;

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
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.Window;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.event.ActionEvent;
import java.util.ArrayList;

public class CustomerStage extends Application {
 
  @Override
  public void start(Stage stage) {
 
      TableView<Account> table = new TableView<Account>();
      table.setEditable(true);

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

      final ObservableList items = FXCollections.observableArrayList();
      items.add(true);
      items.add(false);
      activeCol.setCellFactory(ComboBoxTableCell.<Account, Boolean>forTableColumn(items)); 
      activeCol.setOnEditCommit(
          (CellEditEvent<Account, Boolean> t) -> {
              ((Account) t.getTableView().getItems().get(
                  t.getTablePosition().getRow())
                  ).setActive(t.getNewValue());
              }
      );

      firstNameCol.setCellFactory(TextFieldTableCell.<Account>forTableColumn());
      firstNameCol.setOnEditCommit(
          (CellEditEvent<Account, String> t) -> {
              ((Account) t.getTableView().getItems().get(
                  t.getTablePosition().getRow())
                  ).setFirstName(t.getNewValue());
              }
      );

      // Defines how to fill data for each cell.
      // Get value from property of Account.
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
      table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

      Button btnDelete = new Button("Delete data");
      btnDelete.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
              ObservableList<Account> selectedRows = table.getSelectionModel().getSelectedItems();
              ArrayList<Account> rows = new ArrayList<>(selectedRows);
              rows.forEach(row -> table.getItems().remove(row));
          }
      });

      StackPane root = new StackPane();
      root.setPadding(new Insets(5));
      root.getChildren().add(table);
      root.getChildren().add(btnDelete);
 
      table.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override 
          public void handle(MouseEvent event) {
              if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                  Window owner = table.getScene().getWindow();
                  int index = table.getSelectionModel().getSelectedIndex();
                  AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner , "Information",
                      table.getItems().get(index).getUserName());
              }
          }
      });

      stage.setTitle("Customer List");
 
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
