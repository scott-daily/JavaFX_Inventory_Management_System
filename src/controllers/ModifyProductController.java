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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Inventory;
import models.Part;
import models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The ModifyProductController class is used to handle the Modify Product View's actions.
 */
public class ModifyProductController implements Initializable {

    /**
     * TableView to hold Part data
     */
    @FXML
    private TableView<Part> allPartsTable;

    /**
     * TableView Column that holds a Part ID.
     */
    @FXML
    private TableColumn<Part, Integer> partTableID;

    /**
     * Tableview Column that holds a Part Name.
     */
    @FXML
    private TableColumn<Part, String> partTableName;

    /**
     * Tableview Column that holds a Part's inventory amount.
     */
    @FXML
    private TableColumn<Part, Inventory> partTableInv;

    /**
     * Tableview Column that holds a Part's cost.
     */
    @FXML
    private TableColumn<Part, Double> partTableCost;

    /**
     *  Tableview field that holds a Product ID.
     */
    @FXML
    private TextField productIDField;

    /**
     * TextField that holds a Product's name.
     */
    @FXML
    private TextField productNameField;

    /**
     * TextField that holds a Product's inventory amount.
     */
    @FXML
    private TextField productInvField;

    /**
     * TextField that holds a Product's price.
     */
    @FXML
    private TextField productPriceField;

    /**
     * TextField that holds a Product's maximum allowed quantity.
     */
    @FXML
    private TextField productMaxField;

    /**
     * TextField that holds a Product's minimum allowed quantity.
     */
    @FXML
    private TextField productMinField;

    /**
     * TableView that holds a Product's associated Parts.
     */
    @FXML
    private TableView<Part> productPartsTable;

    /**
     * TableColumn that holds a Part's ID.
     */
    @FXML
    private TableColumn<Part, Integer> productPartTableID;

    /**
     * TableColumn that holds a Part's Name.
     */
    @FXML
    private TableColumn<Part, String> productPartTableName;

    /**
     * Tableview Column that holds a Part's inventory amount.
     */
    @FXML
    private TableColumn<Part, Integer> productPartTableInv;

    /**
     * TableView Column that holds a Part's cost.
     */
    @FXML
    private TableColumn<Part, Double> productPartTableCost;

    /**
     * TextField that holds the string to search the TableView holding all Parts in Inventory.
     */
    @FXML
    private TextField partSearchField;

    /**
     * Initializes the Modify Part TableView with all Parts in Inventory and initializes the selected Product's associated Part's table.
     * Sets a listener on the search field to wait for user input to filter the Parts list based on the input.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        Product savedProduct = ControlData.getSelectedProduct();

        partTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partTableCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.getAllParts(), p -> true);

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
        sortedParts.comparatorProperty().bind(allPartsTable.comparatorProperty());
        allPartsTable.setItems(sortedParts);

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

    /**
     * Adds the currently selected Part to the Product's associated Part's list.
     * @param actionEvent Occurs when add Part button is clicked.
     */
    public void onClickAddPartToProduct(ActionEvent actionEvent) {
        if (allPartsTable.getSelectionModel().getSelectedItem() != null) {
            Product savedProduct = ControlData.getSelectedProduct();

            savedProduct.addAssociatedPart(allPartsTable.getSelectionModel().getSelectedItem());
            productPartsTable.setItems(savedProduct.getAllAssociatedParts());

            productPartTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
            productPartTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
            productPartTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPartTableCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Warning");
            alert.setContentText("A part must be selected to be added.");
            alert.showAndWait();
        }
    }

    /**
     * Removes a Part from the Product's associated Part's list.
     * @param actionEvent Occurs when Product Part remove button is clicked.
     */
    public void onClickRemovePart(ActionEvent actionEvent) {
        if (productPartsTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the selected part?");
            Optional<ButtonType> deleteResult = alert.showAndWait();

            if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                Product savedProduct = ControlData.getSelectedProduct();
                Part selectedPart = productPartsTable.getSelectionModel().getSelectedItem();
                savedProduct.deleteAssociatedPart(selectedPart);
            }
        } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Warning");
                alert.setContentText("A part must be selected to be removed.");
                alert.showAndWait();
            }
    }

    /**
     * Saves an updated Product in the Inventory's Product list.
     * @param actionEvent Occurs when save Product button is clicked.
     * @throws IOException Thrown when Product creation field values are invalid.
     */
    @FXML
    void onClickUpdateProduct(ActionEvent actionEvent) throws IOException {
            try {
                int min = Integer.parseInt(productMinField.getText());
                int max = Integer.parseInt(productMaxField.getText());
                int inventory = Integer.parseInt(productInvField.getText());

                if (min >= max || inventory < min || inventory > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Warning");
                    alert.setContentText("Min must be less than Max and Inventory must be between these values.");
                    alert.showAndWait();
                }
                else {
                    if (productNameField.getText().length() == 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Warning");
                        alert.setContentText("The product must have a name.");
                        alert.showAndWait();
                    } else {
                        int currentIndex = ControlData.getSelectedProductIndex();
                        Inventory.updateProduct(currentIndex, new Product(Integer.parseInt(String.valueOf(productIDField.getText())), productNameField.getText(), Double.parseDouble(productPriceField.getText()), Integer.parseInt(productInvField.getText()), Integer.parseInt(productMinField.getText()), Integer.parseInt(productMaxField.getText())));

                        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 1200, 500);
                        stage.setTitle("To Main");
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Warning");
                alert.setContentText("Valid values must be used in all text inputs.");
                alert.showAndWait();
            }
    }

    /**
     * Sets the main view scene to the stage.
     * @param actionEvent Event occurs when cancel button is clicked.
     * @throws IOException Exception thrown if error occurs during FXML view loading.
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("To Main");
        stage.setScene(scene);
        stage.show();
    }
}