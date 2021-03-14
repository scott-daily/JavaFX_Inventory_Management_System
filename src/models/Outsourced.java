package models;

/**
 *  Concrete class representing an outsourced part; implements the abstract Part class.
 */
public class Outsourced extends Part {

    /**
     * String representing the name of the Part manufacturer.
     */
    private String companyName;

    /**
     * Constructor to generate a new Outsourced object.
     * @param id The ID of the Part.
     * @param name The name of the Part.
     * @param price The price of the Part.
     * @param stock The current inventory of the Part.
     * @param min The minimum allowed in inventory.
     * @param max The maximum allowed in inventory.
     * @param companyName The name of the company that built the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Gets a Part's company name.
     * @return A string representing the company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets a Part's company name.
     * @param companyName A string representing a company name.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
