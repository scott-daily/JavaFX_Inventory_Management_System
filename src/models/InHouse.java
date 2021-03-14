package models;

/**
 *  Concrete class representing an in-house part; implements the abstract Part class.
 */
public class InHouse extends Part {
    /**
     * Integer representing the machine ID that a part was manufactured with.
     */
    private int machineId;

    /**
     * Constructor to generate a new InHouse object.
     * @param id The ID of the Part.
     * @param name The name of the Part.
     * @param price The price of the Part.
     * @param stock The current inventory of the Part.
     * @param min The minimum allowed in inventory.
     * @param max The maximum allowed in inventory.
     * @param machineId The ID of the machine that built the part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *  Gets the Part's machine ID.
     * @return An int representing the machine ID.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets the Part's machine ID.
     * @param machineId An int representing a machine ID.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
