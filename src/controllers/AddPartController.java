package controllers;

import java.util.ArrayList;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    @FXML
    private RadioButton inHouseButton;

    @FXML
    private ToggleGroup inOrOutTG;

    @FXML
    private RadioButton outsourcedButton;

    @FXML
    private TextField partIDField;

    @FXML
    private TextField partNameField;

    @FXML
    private TextField partInvField;

    @FXML
    private TextField partPriceField;

    @FXML
    private TextField partMaxField;

    @FXML
    private TextField partMinField;

    @FXML
    private Label sourceText;

    @FXML
    private TextField sourceField;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void setTextCompanyName(MouseEvent event) {
        sourceText.setText("Company Name");
    }

    @FXML
    void setTextMachineID(MouseEvent event) {
        sourceText.setText("Machine ID");
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
    void onClickSavePart(ActionEvent event) {
        if (inHouseButton.isSelected()) {
            Inventory.addPart(new InHouse(generateUniqueID(), partNameField.getText(), Double.parseDouble(partPriceField.getText()), Integer.parseInt(partInvField.getText()), Integer.parseInt(partMinField.getText()), Integer.parseInt(partMaxField.getText()), Integer.parseInt(sourceField.getText())));
            System.out.println("Added a new in-house part");
        }
        if (outsourcedButton.isSelected()) {
            Inventory.addPart(new Outsourced(generateUniqueID(), partNameField.getText(), Double.parseDouble(partPriceField.getText()), Integer.parseInt(partInvField.getText()), Integer.parseInt(partMinField.getText()), Integer.parseInt(partMaxField.getText()), sourceField.getText()));
            System.out.println("Added a new outsourced part");
        }
    }

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
