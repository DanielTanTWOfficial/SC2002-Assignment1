package model;

import java.io.Serial;
import java.io.Serializable;

public class Vendor implements IVendor, Serializable {
    @Serial
    private static final long serialVersionUID = 123456L;
    public String vendorName;
    private Cineplex[] cineplexes;
    private int numCineplexes;

    public Vendor(String vendorName) {
        this.vendorName = vendorName;
        this.cineplexes= new Cineplex[999];
        this.numCineplexes = 0;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getNumCineplexes() {
        return numCineplexes;
    }

    public void addNewCineplex(Cineplex newCineplex){
        this.cineplexes[this.numCineplexes]=newCineplex;
        this.numCineplexes++;
    }

    public void printCineplexes(){
        System.out.println("Locations of "+this.vendorName+" cinemas:");
        for (int i=0;i<numCineplexes;i++){
            System.out.println("Cineplex "+(i+1)+": "+this.cineplexes[i].getLocation());
        }
    }

    public Cineplex getCineplex(int i){
        return this.cineplexes[i];
    }

}