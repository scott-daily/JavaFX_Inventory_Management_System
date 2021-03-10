package models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1200, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        // public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
        InHouse bike_tire = new InHouse(1, "Bike Tire", 4.23, 3, 1, 5, 123);
        InHouse handlebars = new InHouse(2, "Handlebars", 37.57, 5, 2, 6, 321);
        InHouse bike_peddles = new InHouse(3, "Bike Pedals", 24.99, 13, 3, 8, 321);
        Outsourced steel_bar = new Outsourced(5, "Steel Bar", 14.99, 24, 2, 10, "Bangkok Metals");

        Inventory.addPart(bike_tire);
        Inventory.addPart(handlebars);
        Inventory.addPart(bike_peddles);
        Inventory.addPart(steel_bar);

        Application.launch(args);
    }
}
