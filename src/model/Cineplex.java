package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Cineplex implements Serializable {
    @Serial
    private static final long serialVersionUID = 123458L;
    private String location;
    private int numCinemas;
    private ArrayList<Cinema> cinemas;

    public Cineplex(String location) {
        this.location = location;
        this.cinemas = new ArrayList<Cinema>();
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

    public ArrayList<Cinema> getCinemas() {
        return cinemas;
    }

    public void addNewCinema(Cinema newCinema){
        cinemas.add(newCinema);
        this.numCinemas++;
    }

    public void printCinemas(){
        System.out.println("Cinemas at "+this.location+": \n");
        int[] cinemasPrinted = new int[2];

        //initialising values in array
        for (int i=0;i<CinemaTypes.values().length;i++){
            cinemasPrinted[i]=0;//0 for not printed, 1 for printed
        }

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
    }

    private String cinemaTypestoString(CinemaTypes cinemaTypes){
        switch (cinemaTypes){
            case STANDARD: return "Standard DOLBY ATMOS cinema";
            case PLATINUM: return "Platinum Movie Suites";
        }
        return "";
    }

}
