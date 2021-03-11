package controllers;

import models.Part;

public class ControlData {

    private static Part selectedPart;
    private static int selectedIndex;

    public static void saveSelectedPart(Part selectedPart) {
        ControlData.selectedPart = selectedPart;
    }

    public static Part getSavedPart() {
        return selectedPart;
    }

    public static int getSelectedIndex() {
        return selectedIndex;
    }

    public static void setSelectedIndex(int index) {
        ControlData.selectedIndex = index;
    }
}
