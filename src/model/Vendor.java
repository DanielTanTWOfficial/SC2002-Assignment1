package model;

import java.util.ArrayList;
import java.util.Objects;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Vendor implements Serializable {
    @Serial
    private static final long serialVersionUID = 123456L;
    public String vendorName;
    private ArrayList<Cineplex> cineplexes;
    private int numCineplexes;

    public Vendor(String vendorName) {
        this.vendorName = vendorName;
        this.cineplexes = new ArrayList<Cineplex>();
        this.numCineplexes = 0;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getNumCineplexes() {
        return numCineplexes;
    }

    public ArrayList<Cineplex> getCineplexes() {
        return cineplexes;
    }

    public void addNewCineplex(Cineplex newCineplex){
        this.cineplexes.add(newCineplex);
        this.numCineplexes++;
    }

    public void printCineplexes(){
        System.out.println("Locations of "+this.vendorName+" cinemas:");
        for (int i=0;i<numCineplexes;i++){
            System.out.println("Cineplex "+(i+1)+": "+this.cineplexes.get(i).getLocation());
        }
    }

    /**
     * Returns the cineplex according to it's index in the Vendor's cineplexes list
     * @param i
     * @return
     */
    public Cineplex getCineplex(int i){
        return this.cineplexes.get(i);
    }

}
