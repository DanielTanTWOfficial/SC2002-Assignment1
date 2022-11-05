package model;

import java.util.ArrayList;
import java.util.Objects;
import java.io.Serial;
import java.io.Serializable;

public class Cineplex implements Serializable {
    @Serial
    private static final long serialVersionUID = 123458L;
    private String location;
    private ArrayList<Cinema> cinemas;
    private int numCinemas;

    public Cineplex(String location) {
        this.location = location;
        this.cinemas = new ArrayList<Cinema>();
        this.numCinemas=0;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumCinemas() {
        return this.numCinemas;
    }

    public void addNewCinema(Cinema newCinema){
        this.cinemas.add(newCinema);
        this.numCinemas++;
    }

    public void printCinemas(){
        System.out.println("Cinemas at "+ this.location +": \n");

        //print cinemas
        for (int i = 0; i < numCinemas; i++) {
            Cinema currentCinema = this.cinemas.get(i);

            System.out.println("============== " + (i + 1) + " =============");
            System.out.println(currentCinema.cinemaTypestoString(currentCinema.getCinemaClass()));
            System.out.println("Cinema Code: " + currentCinema.getCinemaCode());
            System.out.println("Number of seats: " + currentCinema.getNumSeats());
            System.out.println();
        }
    }

    public Cinema getCinema(int i) {
        return this.cinemas.get(i);
    }

}
