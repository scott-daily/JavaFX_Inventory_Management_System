package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    //private static ObservableList<Product> allProducts;

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
}
