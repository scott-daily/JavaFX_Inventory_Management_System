package controllers;

import models.Part;
import models.Product;

public class ControlData {

    private static Part selectedPart;
    private static int selectedPartIndex;

    private static Product selectedProduct;
    private static int selectedProductIndex;

    public static void saveSelectedPart(Part selectedPart) {
        ControlData.selectedPart = selectedPart;
    }

    public static Part getSavedPart() {
        return selectedPart;
    }

    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }

    public static void setSelectedPartIndex(int index) {
        ControlData.selectedPartIndex = index;
    }

    public static void saveSelectedProduct(Product selectedProduct) {
        ControlData.selectedProduct = selectedProduct;
    }

    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    public static void setSelectedProductIndex(int index) {
        ControlData.selectedProductIndex = index;
    }

    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }
}
