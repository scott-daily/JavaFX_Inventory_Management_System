package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts;

    private static Part selectedPart;

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static void saveSelectedPart(Part selectedPart) {
        Inventory.selectedPart = selectedPart;
    }

    public static Part getSavedPart() {
        return selectedPart;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    public static void deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }

    public static Part lookupPart(int partId) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }
}
