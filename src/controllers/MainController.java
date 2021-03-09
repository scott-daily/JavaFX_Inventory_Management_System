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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<?> partTable;

    @FXML
    private TableColumn<?, ?> partTableID;

    @FXML
    private TableColumn<?, ?> partTableName;

    @FXML
    private TableColumn<?, ?> partTableInv;

    @FXML
    private TableColumn<?, ?> partTableCost;

    @FXML
    private TableView<?> productTable;

    @FXML
    private TableColumn<?, ?> productTableID;

    @FXML
    private TableColumn<?, ?> productTableName;

    @FXML
    private TableColumn<?, ?> productTableInv;

    @FXML
    private TableColumn<?, ?> productTableCost;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        stage.setTitle("Add Part");
        stage.setScene(addPartScene);
        stage.show();
    }

    @FXML
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene addPartScene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(addPartScene);
        stage.show();
    }

    @FXML
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene addPartScene = new Scene(root, 1100, 700);
        stage.setTitle("Add Part");
        stage.setScene(addPartScene);
        stage.show();
    }
}
