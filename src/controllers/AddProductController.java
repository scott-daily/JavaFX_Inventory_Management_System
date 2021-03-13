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

public class AddProductController implements Initializable {

    @FXML
    private TableView<Part> allPartsTable;

    @FXML
    private TableColumn<Part, Integer> partTableID;

    @FXML
    private TableColumn<Part, String> partTableName;

    @FXML
    private TableColumn<Part, Inventory> partTableInv;

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

    @FXML
    private TextField partSearchField;

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

    ObservableList<Part> newProductPartList = FXCollections.observableArrayList();

    public void onClickAddPartToProduct(ActionEvent actionEvent) throws IOException {
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

    public void onClickRemovePart(ActionEvent actionEvent) throws IOException {
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

    private ArrayList<Integer> usedIdArray = new ArrayList<>();

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

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("To Main");
        stage.setScene(scene);
        stage.show();
    }
}