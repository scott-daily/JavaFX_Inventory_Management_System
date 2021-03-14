package models;

import controllers.ControlData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Concrete class that stores a complete list of created Parts and Products and associated methods to manipulate the lists.
 */
public class Inventory {
    /**
     * ObservableList object instantiated hold all created Parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * ObservableList object instantiated to hold all created Products.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new Part to the allParts list.
     * @param newPart The Part to be added.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new Product to the allProducts list.
     * @param newProduct The Product to be added.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Gets all Parts in the form of an ObservableList.
     * @return An ObservableList containing Part objects.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all Products in the form of an ObservableList.
     * @return An ObservableList containing Product objects.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Updates a Part by replacing the existing Part.
     * @param index The index of the Part to replace.
     * @param selectedPart The updated Part object.
     */
    public static void updatePart(int index, Part updatedPart) {
        allParts.set(index, updatedPart);
    }

    /**
     * Updates a Product by replacing the existing Product.
     * @param index The index of the Product to replace.
     * @param selectedPart The updated Product object.
     */
    public static void updateProduct(int index, Product updatedProduct) {
        allProducts.set(index, updatedProduct);
    }

    /**
     * Deletes a Part from the allParts list.
     * @param selectedPart The Part to be deleted.
     */
    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    /**
     * Deletes a Part from the allProducts list.
     * @param selectedProduct The Part to be deleted.
     */
    public static void deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }

    /**
     * Searches the allParts list for a Part's ID.
     * If found, saves the index of the Part for later use in the ControlData class.
     * @param partId The ID of the Part to be searched for.
     * @return The Part with a matching ID or null if no Part is found.
     */
    public static Part lookupPart(int partId) {
        int index = 0;
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == partId) {
                ControlData.setSelectedPartIndex(index);
                return part;
            } else {
                index++;
            }
        }
        return null;
    }

    /**
     * Searches the allProducts list for a Product's ID.
     * If found, saves the index of the Product for later use in the ControlData class.
     * @param productId The ID of the Product to be searched for.
     * @return The Product with a matching ID or null if no Product is found.
     */
    public static Product lookupProduct(int productId) {
        int index = 0;
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == productId) {
                ControlData.setSelectedProductIndex(index);
                return product;
            } else {
                index++;
            }
        }
        return null;
    }
}
