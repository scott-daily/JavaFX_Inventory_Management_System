package models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class that loads the main inventory FXML screen.
 */
public class Main extends Application {

    /**
     * Loads the main.fxml view into the FXML Loader and displays the scene.
     * @param primaryStage The Stage to start.
     * @throws Exception Throws if view cannot be loaded.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1200, 500));
        primaryStage.show();
    }

    /**
     * Main method launches the application and instantiates Part and Product Objects for testing.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
        InHouse bike_tire = new InHouse(1, "Bike Tire", 4.23, 3, 1, 5, 123);
        InHouse handlebars = new InHouse(2, "Handlebars", 37.57, 5, 2, 6, 321);
        InHouse bike_peddles = new InHouse(3, "Bike Pedals", 24.99, 13, 3, 8, 321);
        Outsourced steel_bar = new Outsourced(5, "Steel Bar", 14.99, 24, 2, 10, "Bangkok Metals");

        Product bike = new Product(13,"Bike", 299.99, 5,2,10);
        Product unicycle = new Product(15,"Unicycle", 199.99, 17,1,20);
        Product tandem_bike = new Product(17,"Tandem Bike", 599.99, 5,2,5);

        bike.addAssociatedPart(bike_tire);
        bike.addAssociatedPart(bike_tire);
        bike.addAssociatedPart(handlebars);
        bike.addAssociatedPart(steel_bar);
        bike.addAssociatedPart(steel_bar);

        unicycle.addAssociatedPart(bike_tire);
        unicycle.addAssociatedPart(steel_bar);

        tandem_bike.addAssociatedPart(bike_tire);
        tandem_bike.addAssociatedPart(bike_tire);
        tandem_bike.addAssociatedPart(steel_bar);
        tandem_bike.addAssociatedPart(steel_bar);
        tandem_bike.addAssociatedPart(steel_bar);
        tandem_bike.addAssociatedPart(handlebars);

        Inventory.addProduct(bike);
        Inventory.addProduct(unicycle);
        Inventory.addProduct(tandem_bike);


        Inventory.addPart(bike_tire);
        Inventory.addPart(handlebars);
        Inventory.addPart(bike_peddles);
        Inventory.addPart(steel_bar);

        Application.launch(args);
    }
}
