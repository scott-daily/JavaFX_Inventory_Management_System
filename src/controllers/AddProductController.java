package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import models.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.SplittableRandom;

/**
 * The AddProductController class is used to handle the Add Product View's actions.
 */
public class AddProductController implements Initializable {
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
     * Initializes the Part TableView with all Parts in Inventory.  Sets a listener on the search field to wait for user
     * input to filter the list based on the input.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
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
    }

    /**
     * Used to store a list of Part's currently added to the new Product's associated Part's list.
     */
    ObservableList<Part> newProductPartList = FXCollections.observableArrayList();

    /* DETAILED DESCRIPTION OF ERROR AND HOW IT WAS FIXED:
    The Add Part button to put a part from the allPart's list into the Product's associatedParts list was incorrectly adding
    blank spaces to the associatedPart's TableView if a Part was not selected before the add button was clicked.  This was
    fixed by adding an if-else block to ensure that a Part is selected before it is allowed to be added.  If a part is not
    selected, then a warning is displayed to the user.
     */
    /**
     * Adds the currently selected Part to the Product's associated Part's list.
     * @param actionEvent Occurs when add Part button is clicked.
     */
    public void onClickAddPartToProduct(ActionEvent actionEvent) {
        if (allPartsTable.getSelectionModel().getSelectedItem() != null) {
            newProductPartList.add(allPartsTable.getSelectionModel().getSelectedItem());
            productPartsTable.setItems(newProductPartList);

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
                Part selectedPart = productPartsTable.getSelectionModel().getSelectedItem();
                newProductPartList.remove(selectedPart);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Warning");
            alert.setContentText("A part must be selected to be removed.");
            alert.showAndWait();
        }
    }

    /**
     * Stores used Product ID's so that new Product's have unique ID's.
     */
    private ArrayList<Integer> usedIdArray = new ArrayList<>();

    /**
     * Generates a unique ID to be used in a Part constructor method.
     * Uses the SplittableRandom class to generate a unique sequence of values between the specified bounds.
     * Uses boolean value isUnique to track if the generated ID already exists within the usedIdArray.
     * @return Returns an int that represents a unique ID between 1 and 1000.
     */
    private int generateUniqueID() {
        boolean isUnique = false;
        int randomID = new SplittableRandom().nextInt(1, 1_001);

        while (!isUnique) {
            if (!usedIdArray.contains(randomID)) {
                isUnique = true;
                usedIdArray.add(randomID);
            }
            else {
                randomID = new SplittableRandom().nextInt(1, 1_001);
            }
        }
        return randomID;
    }

    /**
     * Saves a new Product in the Inventory's Product list.
     * @param actionEvent Occurs when save Product button is clicked.
     * @throws IOException Thrown when Product creation field values are invalid.
     */
    @FXML
    void onClickSaveProduct(ActionEvent actionEvent) throws IOException {
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
                        Product newProduct = new Product(generateUniqueID(), productNameField.getText(), Double.parseDouble(productPriceField.getText()), Integer.parseInt(productInvField.getText()), Integer.parseInt(productMinField.getText()), Integer.parseInt(productMaxField.getText()));
                        for (Part part : newProductPartList) {
                            newProduct.addAssociatedPart(part);
                        }

                        Inventory.addProduct(newProduct);

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