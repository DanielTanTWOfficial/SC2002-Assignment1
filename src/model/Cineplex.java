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
        System.out.println("Cinemas at "+this.location+": \n");
        int[] cinemasPrinted = new int[2];

        //initialising values in array
        for (int i=0;i<2;i++){
            cinemasPrinted[i]=0; //0 for not printed, 1 for printed
        }

        //print cinemas
        for (int i=0;i<numCinemas;i++){
            if (cinemasPrinted[this.cinemas[i].getCinemaName().ordinal()]==0) {
                System.out.println(cinemaTypestoString(this.cinemas[i].getCinemaName()));
                System.out.println("Number of seats: " + this.cinemas[i].getNumSeats());
                System.out.println("Description: " + this.cinemas[i].getCinemaDetails());
                System.out.println();

                cinemasPrinted[this.cinemas[i].getCinemaName().ordinal()]=1;
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
