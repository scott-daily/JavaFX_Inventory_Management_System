package controllers;

import models.Part;
import models.Product;

/**
 * The ControlData class exists to easily transfer data between view controllers.
 */
public class ControlData {

    /**
     * The part that was last selected in a TableView.
     */
    private static Part selectedPart;
    /**
     * The index of the last selected part in a TableView.
     */
    private static int selectedPartIndex;
    /**
     * The product that was last selected in a TableView.
     */
    private static Product selectedProduct;
    /**
     * The index of the last selected product in a TableView.
     */
    private static int selectedProductIndex;

    /**
     * Saves the currently selected part in a TableView into the selectedPart variable.
     * @param selectedPart The currently selected part.
     */
    public static void saveSelectedPart(Part selectedPart) {
        ControlData.selectedPart = selectedPart;
    }

    /**
     * Returns the last selected Part.
     * @return The currently stored Part.
     */
    public static Part getSavedPart() {
        return selectedPart;
    }

    /**
     * Returns the last selected Part index.
     * @return An int representing the last saved Part index.
     */
    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }

    /**
     * Saves the currently selected Part's index.
     * @param index The index of the currently selected Part.
     */
    public static void setSelectedPartIndex(int index) {
        ControlData.selectedPartIndex = index;
    }

    /**
     * Saves the currently selected Product.
     * @param selectedProduct The currently selected Product.
     */
    public static void saveSelectedProduct(Product selectedProduct) {
        ControlData.selectedProduct = selectedProduct;
    }

    /**
     * Returns the last selected Product.
     * @return The currently stored Product.
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    /**
     * Saves the currently selected Product's index.
     * @param index An int representing the currently selected product's index.
     */
    public static void setSelectedProductIndex(int index) {
        ControlData.selectedProductIndex = index;
    }

    /**
     * Returns the last selected Product's index.
     * @return An int representing the currently stored product's index.
     */
    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }
}
