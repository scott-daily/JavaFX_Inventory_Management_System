package controllers;

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
import models.Part;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    @FXML
    private ToggleGroup inOrOutTG;

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
    private TextField sourceField;

    @FXML
    private TextField partMinField;

    @FXML
    private RadioButton inHouseButton;

    @FXML
    private RadioButton outsourcedButton;

    @FXML
    private Label sourceLabel;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        Part savedPart = Inventory.getSavedPart();

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

    @FXML
    void setTextCompanyName(MouseEvent event) {
        sourceLabel.setText("Company Name");
        sourceLabel.setLayoutX(25);
    }

    @FXML
    void setTextMachineID(MouseEvent event) {
        sourceLabel.setText("Machine ID");
        sourceLabel.setLayoutX(50);
    }

    @FXML
    /*public void onClickUpdatePart(ActionEvent actionEvent) throws IOException {

        //Part p =
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
    }*/

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("To Main");
        stage.setScene(scene);
        stage.show();
    }
}
