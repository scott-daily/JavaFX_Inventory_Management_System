package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import models.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The ModifyProductController class is used to handle the Modify Product View's actions.
 */
public class ModifyPartController implements Initializable {

    /**
     * Radio button that indicates a part is in-house.
     */
    @FXML
    private RadioButton inHouseButton;

    /**
     * Radio button that indicates a part is outsourced.
     */
    @FXML
    private RadioButton outsourcedButton;

    /**
     * Text field that contains a Part ID.
     */
    @FXML
    private TextField partIDField;

    /**
     * Text field that contains a Part Name.
     */
    @FXML
    private TextField partNameField;

    /**
     * Text field that contains a Part's current inventory.
     */
    @FXML
    private TextField partInvField;

    /**
     * Text field that contains a Part's price.
     */
    @FXML
    private TextField partPriceField;

    /**
     * Text field that contains a Part's maximum allowed inventory level.
     */
    @FXML
    private TextField partMaxField;

    /**
     * Text field that contains a Part's minimum allowed inventory level.
     */
    @FXML
    private TextField partMinField;

    /**
     * Label that displays the part's source.
     */
    @FXML
    private Label sourceLabel;

    /**
     *  Text field that displays the type of source a part has.
     */
    @FXML
    private TextField sourceField;

    /**
     * Initializes the Modify Product Form's text fields and radio buttons with the selected Part's stored data attributes.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        try {
            Part savedPart = ControlData.getSavedPart();

            partNameField.setText(savedPart.getName());
            partIDField.setText(String.valueOf(savedPart.getId()));
            partInvField.setText(String.valueOf(savedPart.getStock()));
            partPriceField.setText(String.valueOf(savedPart.getPrice()));
            partMaxField.setText(String.valueOf(savedPart.getMax()));
            partMinField.setText(String.valueOf(savedPart.getMin()));

            if (String.valueOf(savedPart.getClass()).equals("class models.InHouse")) {
                inHouseButton.setSelected(true);
                InHouse tempPart = (InHouse) savedPart;
                sourceField.setText(String.valueOf(tempPart.getMachineId()));
                sourceLabel.setText("Machine ID");
                sourceLabel.setLayoutX(50);
            }
            if (String.valueOf(savedPart.getClass()).equals("class models.Outsourced")) {
                outsourcedButton.setSelected(true);
                Outsourced tempPart = (Outsourced) savedPart;
                sourceField.setText(String.valueOf(tempPart.getCompanyName()));
                sourceLabel.setText("Company Name");
            }
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Warning");
            alert.setContentText("A part must be selected for modification.");
            alert.showAndWait();
        }
    }

    /**
     * Sets the text to "Company Name" if part is outsourced.
     * @param event Event occurs when the outsourced radio button is selected.
     */
    @FXML
    void setTextCompanyName(MouseEvent event) {
        sourceLabel.setText("Company Name");
        sourceLabel.setLayoutX(25);
    }

    /**
     * Sets the text to "Machine ID" if part is in-house.
     * @param event Event occurs when the In-House radio button is selected.
     */
    @FXML
    void setTextMachineID(MouseEvent event) {
        sourceLabel.setText("Machine ID");
        sourceLabel.setLayoutX(50);
    }

    /**
     * Updates the selected Part and adds it to the Inventory Parts list.
     * @param actionEvent Event occurs when a Part is updated.
     * @throws IOException Exception thrown if input values are invalid.
     */
    @FXML
    public void onClickUpdatePart(ActionEvent actionEvent) throws IOException {
        int min = Integer.parseInt(partMinField.getText());
        int max = Integer.parseInt(partMaxField.getText());
        int inventory = Integer.parseInt(partInvField.getText());

        if (min >= max || inventory < min || inventory > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Warning");
            alert.setContentText("Min must be less than Max and Inventory must be between these values.");
            alert.showAndWait();
        } else {
            try {
                if (partNameField.getText().length() == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Warning");
                    alert.setContentText("The product must have a name.");
                    alert.showAndWait();
                } else {
                    int currentIndex = ControlData.getSelectedPartIndex();

                    if (inHouseButton.isSelected()) {
                        Inventory.updatePart(currentIndex, new InHouse(Integer.parseInt(String.valueOf(partIDField.getText())), partNameField.getText(), Double.parseDouble(partPriceField.getText()), Integer.parseInt(partInvField.getText()), Integer.parseInt(partMinField.getText()), Integer.parseInt(partMaxField.getText()), Integer.parseInt(sourceField.getText())));
                        System.out.println("Updated an in-house part.");
                    }
                    if (outsourcedButton.isSelected()) {
                        Inventory.updatePart(currentIndex, new Outsourced(Integer.parseInt(String.valueOf(partIDField.getText())), partNameField.getText(), Double.parseDouble(partPriceField.getText()), Integer.parseInt(partInvField.getText()), Integer.parseInt(partMinField.getText()), Integer.parseInt(partMaxField.getText()), sourceField.getText()));
                        System.out.println("Updated an outsourced part.");
                    }
                    Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1200, 500);
                    stage.setTitle("To Main");
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Warning");
                alert.setContentText("Valid values must be used in all text inputs.");
                alert.showAndWait();
            }
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
