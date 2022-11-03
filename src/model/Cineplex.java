package model;

import java.io.Serial;
import java.io.Serializable;

public class Cineplex implements Serializable {
    @Serial
    private static final long serialVersionUID = 123458L;
    private String location;
    private int numCinemas;
    private Cinema[] cinemas;

    public Cineplex(String location) {
        this.location = location;
        this.cinemas = new Cinema[999];
        this.numCinemas=0;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumCinemas() {
        return numCinemas;
    }

    public void addNewCinema(Cinema newCinema){
        this.cinemas[this.numCinemas]=newCinema;
        this.numCinemas++;
    }

    public void printCinemas(){
        System.out.println("Cinemas at "+this.location+":");
        for (int i=0;i<numCinemas;i++){
            System.out.println(this.cinemas[i].getCinemaName());
            System.out.println("Number of seats: "+this.cinemas[i].getNumSeats());
            System.out.println("Description: "+this.cinemas[i].getCinemaDetails());
            System.out.println();
        }
    }

}
