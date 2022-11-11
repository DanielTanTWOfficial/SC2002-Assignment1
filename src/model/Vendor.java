package model;

import java.util.ArrayList;
import java.util.Objects;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Bernice
 * The Vendor class, which implements Serializable
 */
public class Vendor implements Serializable {
    /**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
    @Serial
    private static final long serialVersionUID = 123456L;
    /**
     * The vendorName attribute stores the name of the main company owning the Cineplexes
     */
    public String vendorName;
    /**
     * The cineplexes attribute is an ArrayList of Cineplex objects managed by the Vendor
     */
    private ArrayList<Cineplex> cineplexes;
    /**
     * The numCineplexes attribute tracks the number of Cineplexes operated by the vendor
     */
    private int numCineplexes;

    /**
     * Constructor that creates new Vendor object
     * @param vendorName
     */
    public Vendor(String vendorName) {
        this.vendorName = vendorName;
        this.cineplexes = new ArrayList<Cineplex>();
        this.numCineplexes = 0;
    }

    /**
     * 
     * @return vendorName
     */
    public String getVendorName() {
        return this.vendorName;
    }

    /**
     * 
     * @param vendorName
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * 
     * @return numCineplexes
     */
    public int getNumCineplexes() {
        return numCineplexes;
    }

    /**
     * 
     * @return cineplexes
     */
    public ArrayList<Cineplex> getCineplexes() {
        return cineplexes;
    }

    /**
     * 
     * @param newCineplex
     */
    public void addNewCineplex(Cineplex newCineplex){
        this.cineplexes.add(newCineplex);
        this.numCineplexes++;
    }

    /**
     * Prints cineplexes available for Vendor
     */
    public void printCineplexes(){
        System.out.println("Locations of "+this.vendorName+" cinemas:");
        for (int i=0;i<numCineplexes;i++){
            System.out.println("Cineplex "+(i+1)+": "+this.cineplexes.get(i).getLocation());
        }
    }

    /**
     * Returns the cineplex according to it's index in the Vendor's cineplexes list
     * @param i
     * @return cineplexes.get(i)
     */
    public Cineplex getCineplex(int i){
        return this.cineplexes.get(i);
    }

}
