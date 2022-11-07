package model;

import java.util.ArrayList;
import java.util.Objects;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Cineplex implements Serializable {
    @Serial
    private static final long serialVersionUID = 123458L;
    private String location;
    private ArrayList<Cinema> cinemas;
    private int numCinemas;

    public Cineplex(String location) {
        this.location = location;
        this.cinemas = new ArrayList<>();
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

    public ArrayList<Cinema> getCinemas() {
        return cinemas;
    }

    public void addNewCinema(Cinema newCinema){
        this.cinemas.add(newCinema);
        this.numCinemas++;
    }

    public void printCinemas(){
        System.out.println("Cinemas at "+ this.location +": \n");

        //print cinemas
        for (int i=0;i<numCinemas;i++){
            if (cinemasPrinted[this.cinemas.get(i).getCinemaType().ordinal()]==0) {
                System.out.println(cinemaTypestoString(this.cinemas.get(i).getCinemaType()));
                System.out.println("Number of seats: " + this.cinemas.get(i).getNumSeats());
                System.out.println("Description: " + this.cinemas.get(i).getCinemaDetails());
                System.out.println();

                cinemasPrinted[this.cinemas.get(i).getCinemaType().ordinal()]=1;
            }
        }

        // int[] cinemasPrinted = new int[2];
        // //initialising values in array
        // for (int i=0;i<CinemaTypes.values().length;i++){
        //     cinemasPrinted[i]=0;//0 for not printed, 1 for printed
        // }

        // //print cinemas
        // for (int i=0;i<numCinemas;i++){
        //     if (cinemasPrinted[this.cinemas.get(i).getCinemaName().ordinal()]==0) {
        //         System.out.println(cinemaTypestoString(this.cinemas.get(i).getCinemaName()));
        //         System.out.println("Number of seats: " + this.cinemas.get(i).getNumSeats());
        //         System.out.println("Description: " + this.cinemas.get(i).getCinemaDetails());
        //         System.out.println();

        //         cinemasPrinted[this.cinemas.get(i).getCinemaName().ordinal()]=1;
        //     }
        // }
    }

    public Cinema getCinema(int i) {
        return this.cinemas.get(i);
    }

}
