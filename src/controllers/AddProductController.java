package controllers;

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
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    @FXML
    private TableView<?> allPartsTable;

    @FXML
    private TableColumn<?, ?> partTableID;

    @FXML
    private TableColumn<?, ?> partTableName;

    @FXML
    private TableColumn<?, ?> partTableInv;

    @FXML
    private TableColumn<?, ?> partTableCost;

    @FXML
    private TextField productIDField;

    @FXML
    private TextField productNameField;


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

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