package controllers;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productTableID;

    @FXML
    private TableColumn<Product, String> productTableName;

    @FXML
    private TableColumn<Product, Integer> productTableInv;

    @FXML
    private TableColumn<Product, Double> productTableCost;

    @FXML
    private TextField partSearchField;

    @FXML
    private TextField productSearchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partTableCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productTableCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.getAllParts(), p -> true);
        FilteredList<Product> filteredProducts = new FilteredList<>(Inventory.getAllProducts(), p -> true);

        productSearchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredProducts.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                if (product.getName().contains(newValue)) {
                    return true;
                } else if (String.valueOf(product.getId()).contains(newValue)) {
                    return true;
                }
                return false;
            });
        }));

        SortedList<Product> sortedProducts = new SortedList<>(filteredProducts);
        sortedProducts.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedProducts);

        partSearchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredParts.setPredicate(part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                if (part.getName().contains(newValue)) {
                    return true;
                } else if (String.valueOf(part.getId()).equals(newValue)) {
                    return true;
                }
                return false;
            });
        }));

        SortedList<Part> sortedParts = new SortedList<>(filteredParts);
        sortedParts.comparatorProperty().bind(partTable.comparatorProperty());
        partTable.setItems(sortedParts);
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
        ControlData.setSelectedPartIndex(partTable.getSelectionModel().getSelectedIndex());

        Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene addPartScene = new Scene(root, 600, 600);
        stage.setTitle("Modify Part");
        stage.setScene(addPartScene);
        stage.show();
    }

    @FXML
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        ControlData.saveSelectedProduct(productTable.getSelectionModel().getSelectedItem());
        ControlData.setSelectedProductIndex(productTable.getSelectionModel().getSelectedIndex());

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

    @FXML
    public void deleteSelectedProduct(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(selectedProduct);
    }
}
