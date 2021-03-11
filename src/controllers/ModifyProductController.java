package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Inventory;
import models.Part;
import models.Product;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    @FXML
    private TableView<Part> allPartsTable;

    @FXML
    private TableColumn<Part, Integer> partTableID;

    @FXML
    private TableColumn<Part, String> partTableName;

    @FXML
    private TableColumn<Part, Integer> partTableInv;

    @FXML
    private TableColumn<Part, Double> partTableCost;

    @FXML
    private TextField productIDField;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productInvField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productMaxField;

    @FXML
    private TextField productMinField;

    @FXML
    private TableView<Part> productPartsTable;

    @FXML
    private TableColumn<Part, Integer> productPartTableID;

    @FXML
    private TableColumn<Part, String> productPartTableName;

    @FXML
    private TableColumn<Part, Integer> productPartTableInv;

    @FXML
    private TableColumn<Part, Double> productPartTableCost;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        Product savedProduct = ControlData.getSelectedProduct();

        allPartsTable.setItems(Inventory.getAllParts());
        partTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partTableCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        productPartsTable.setItems(savedProduct.getAllAssociatedParts());
        productPartTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartTableCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        productNameField.setText(savedProduct.getName());
        productIDField.setText(String.valueOf(savedProduct.getId()));
        productInvField.setText(String.valueOf(savedProduct.getStock()));
        productPriceField.setText(String.valueOf(savedProduct.getPrice()));
        productMaxField.setText(String.valueOf(savedProduct.getMax()));
        productMinField.setText(String.valueOf(savedProduct.getMin()));
    }

    public void onClickAddPartToProduct(ActionEvent actionEvent) throws IOException {
        Product savedProduct = ControlData.getSelectedProduct();

        savedProduct.addAssociatedPart(allPartsTable.getSelectionModel().getSelectedItem());
        productPartsTable.setItems(savedProduct.getAllAssociatedParts());

        productPartTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartTableCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("To Main");
        stage.setScene(scene);
        stage.show();
    }
}