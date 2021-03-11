package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import models.Inventory;
import models.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<Part, Integer> partTableID;

    @FXML
    private TableColumn<Part, String> partTableName;

    @FXML
    private TableColumn<Part, Integer> partTableInv;

    @FXML
    private TableColumn<Part, Double> partTableCost;

    @FXML
    private TableView<?> productTable;

    @FXML
    private TableColumn<?, ?> productTableID;

    @FXML
    private TableColumn<?, ?> productTableName;

    @FXML
    private TableColumn<?, ?> productTableInv;

    @FXML
    private TableColumn<?, ?> productTableCost;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());

        partTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partTableCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/AddPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene addPartScene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(addPartScene);
        stage.show();
    }

    @FXML
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/AddProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene addPartScene = new Scene(root, 1100, 700);
        stage.setTitle("Add Product");
        stage.setScene(addPartScene);
        stage.show();
    }

    @FXML
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        ControlData.saveSelectedPart(partTable.getSelectionModel().getSelectedItem());

        Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene addPartScene = new Scene(root, 600, 600);
        stage.setTitle("Modify Part");
        stage.setScene(addPartScene);
        stage.show();
    }

    @FXML
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene addPartScene = new Scene(root, 1100, 700);
        stage.setTitle("Modify Product");
        stage.setScene(addPartScene);
        stage.show();
    }

    @FXML
    public void deleteSelectedPart(ActionEvent actionEvent) throws IOException {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedPart);
    }
}
