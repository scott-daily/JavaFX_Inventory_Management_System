package controllers;

import models.Part;

public class ControlData {

    private static Part selectedPart;

    public static void saveSelectedPart(Part selectedPart) {
        ControlData.selectedPart = selectedPart;
    }

    public static Part getSavedPart() {
        return selectedPart;
    }
}
