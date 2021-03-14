package controllers;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import models.Inventory;
import models.Part;
import models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The MainController is used to handle the Main view's actions.
 */
public class MainController implements Initializable {

    /**
     * TableView that holds a view of all Parts in Inventory.
     */
    @FXML
    private TableView<Part> partTable;

    /**
     * TableColumn that holds a Part's ID.
     */
    @FXML
    private TableColumn<Part, Integer> partTableID;

    /**
     * TableColumn that hold's a Part's name.
     */
    @FXML
    private TableColumn<Part, String> partTableName;

    /**
     * TableColumn that holds a Part's inventory amount.
     */
    @FXML
    private TableColumn<Part, Integer> partTableInv;

    /**
     * TableColumn that holds a Part's cost.
     */
    @FXML
    private TableColumn<Part, Double> partTableCost;
    /**
     * TableView that holds all Products in Inventory.
     */
    @FXML
    private TableView<Product> productTable;

    /**
     * TableColumn that holds a Product's ID.
     */
    @FXML
    private TableColumn<Product, Integer> productTableID;

    /**
     * TableColumn that holds a Product's name.
     */
    @FXML
    private TableColumn<Product, String> productTableName;

    /**
     * TableColumn that holds a Part's inventory amount.
     */
    @FXML
    private TableColumn<Product, Integer> productTableInv;

    /**
     * TableColumn that holds a Product's cost.
     */
    @FXML
    private TableColumn<Product, Double> productTableCost;

    /**
     * TextField that holds the string to search the Part table.
     */
    @FXML
    private TextField partSearchField;

    /**
     * TextField that holds the string to search the Product table.
     */
    @FXML
    private TextField productSearchField;

    /**
     * Left Pane that css border is applied to.
     */
    @FXML
    private Pane leftBorderPane;

    /**
     * Right Pane that css border is applied to.
     */
    @FXML
    private Pane rightBorderPane;

    /**
     * Initializes the Part TableView and Product TableView with all Parts and Products in Inventory.  Sets listeners on the both search fields to wait for user
     * input to filter the list based on the input.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        leftBorderPane.setStyle("-fx-border-color: black;"+"-fx-border-radius: 10px;");
        rightBorderPane.setStyle("-fx-border-color: black;"+"-fx-border-radius: 10px;");

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

    /**
     * Transitions to the Add Part View to create a new Part.
     * @param actionEvent Occurs when button to add a new Part is clicked.
     * @throws IOException Throws if error occurs during FXML loading.
     */
    @FXML
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/AddPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene addPartScene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(addPartScene);
        stage.show();
    }

    /**
     * Transitions to the Add Product View to create a new Product.
     * @param actionEvent Occurs when button to add a new Product is clicked.
     * @throws IOException Throws if error occurs during FXML loading.
     */
    @FXML
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/AddProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene addPartScene = new Scene(root, 1100, 700);
        stage.setTitle("Add Product");
        stage.setScene(addPartScene);
        stage.show();
    }

    /**
     * Transitions to the Modify Part View to alter an existing Part.
     * @param actionEvent Occurs when button to modify a Part is clicked.
     * @throws IOException Throws if error occurs during FXML loading.
     */
    @FXML
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        if (partTable.getSelectionModel().getSelectedItem() != null) {
            ControlData.saveSelectedPart(partTable.getSelectionModel().getSelectedItem());
            ControlData.setSelectedPartIndex(partTable.getSelectionModel().getSelectedIndex());

            Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene addPartScene = new Scene(root, 600, 600);
            stage.setTitle("Modify Part");
            stage.setScene(addPartScene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Warning");
            alert.setContentText("A part must be selected for modification.");
            alert.showAndWait();
        }
    }

    /**
     * Transitions to the Modify Product View to alter an existing Product.
     * @param actionEvent Occurs when button to modify a Product is clicked.
     * @throws IOException Throws if error occurs during FXML loading.
     */
    @FXML
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        if (productTable.getSelectionModel().getSelectedItem() != null) {
            ControlData.saveSelectedProduct(productTable.getSelectionModel().getSelectedItem());
            ControlData.setSelectedProductIndex(productTable.getSelectionModel().getSelectedIndex());

            Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene addPartScene = new Scene(root, 1100, 700);
            stage.setTitle("Modify Product");
            stage.setScene(addPartScene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Warning");
            alert.setContentText("A product must be selected for modification.");
            alert.showAndWait();
        }
    }

    /**
     *  Deletes the selected Part from the Inventory Part list.
     * @param actionEvent Occurs when delete Part button is clicked.
     */
    @FXML
    public void deleteSelectedPart(ActionEvent actionEvent) {
        if (partTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected part?");
            Optional<ButtonType> deleteResult = alert.showAndWait();

            if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                Part selectedPart = partTable.getSelectionModel().getSelectedItem();
                Inventory.deletePart(selectedPart);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Warning");
            alert.setContentText("A part must be selected to be deleted.");
            alert.showAndWait();
        }
    }

    /**
     * Deletes the selected Product from the Inventory Product list.
     * @param actionEvent Occurs when a Product is selected and the delete Product button is clicked.
     */
    @FXML
    public void deleteSelectedProduct(ActionEvent actionEvent) {
        if (productTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected product?");
            Optional<ButtonType> deleteResult = alert.showAndWait();

            if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
                if (selectedProduct.getAllAssociatedParts().isEmpty())
                    Inventory.deleteProduct(selectedProduct);
                else {
                    Alert partAlert = new Alert(Alert.AlertType.ERROR);
                    partAlert.setTitle("Error Warning");
                    partAlert.setContentText("All parts associated with a product must be removed first.");
                    partAlert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Warning");
            alert.setContentText("A product must be selected to be deleted.");
            alert.showAndWait();
        }
    }

    /**
     *  Exits the program when exit button is clicked.
     * @param actionEvent Occurs when the exit button is clicked.
     */
    @FXML
    public void onClickExitProgram(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the program?");
        Optional<ButtonType> exitAnswer = alert.showAndWait();

        if (exitAnswer.isPresent() && exitAnswer.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
}
