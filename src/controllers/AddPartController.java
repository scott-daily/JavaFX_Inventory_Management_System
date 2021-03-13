package controllers;

import java.util.ArrayList;
import java.util.SplittableRandom;

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


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The AddPartController class is used to handle the Add Part View's actions.
 */
public class AddPartController implements Initializable {

    /**
     * Radio button that indicates a part is in-house.
     */
    @FXML
    private RadioButton inHouseButton;

    /**
     * Radio button that indicates a part is in-house.
     */
    @FXML
    private ToggleGroup inOrOutTG;

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
    private Label sourceText;

    /**
     *  Text field that displays the type of source a part has.
     */
    @FXML
    private TextField sourceField;

    /**
     * Initializes the Add Part Controller.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Sets the text to "Company Name" if part is outsourced.
     * @param event Event occurs when the outsourced radio button is selected.
     */
    @FXML
    void setTextCompanyName(MouseEvent event) {
        sourceText.setText("Company Name");
        sourceText.setLayoutX(25);
    }

    /**
     * Sets the text to "Machine ID" if part is in-house.
     * @param event Event occurs when the In-House radio button is selected.
     */
    @FXML
    void setTextMachineID(MouseEvent event) {
        sourceText.setText("Machine ID");
        sourceText.setLayoutX(50);
    }

    /**
     * Stores used Part ID's so that all created are unique.
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
     * Saves the newly created Part into the Inventory's Part list.
     * @param actionEvent Event occurs when a Part is saved.
     * @throws IOException Exception thrown if input values are invalid.
     */
    @FXML
    void onClickSavePart(ActionEvent actionEvent) throws IOException {
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
                    if (inHouseButton.isSelected()) {
                        Inventory.addPart(new InHouse(generateUniqueID(), partNameField.getText(), Double.parseDouble(partPriceField.getText()), Integer.parseInt(partInvField.getText()), Integer.parseInt(partMinField.getText()), Integer.parseInt(partMaxField.getText()), Integer.parseInt(sourceField.getText())));
                        System.out.println("Added a new in-house part");
                    }
                    if (outsourcedButton.isSelected()) {
                        Inventory.addPart(new Outsourced(generateUniqueID(), partNameField.getText(), Double.parseDouble(partPriceField.getText()), Integer.parseInt(partInvField.getText()), Integer.parseInt(partMinField.getText()), Integer.parseInt(partMaxField.getText()), sourceField.getText()));
                        System.out.println("Added a new outsourced part");
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
    @FXML
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("To Main");
        stage.setScene(scene);
        stage.show();
    }
}
