package model;

import java.util.ArrayList;
import java.util.Objects;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Cineplex implements Serializable {

    /**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
    @Serial
    private static final long serialVersionUID = 123458L;
    private String location;
    private ArrayList<Cinema> cinemas;
    private int numCinemas;

    /**
     * Constructor that creates new Cineplex object
     * @param location
     */
    public Cineplex(String location) {
        this.location = location;
        this.cinemas = new ArrayList<>();
        this.numCinemas=0;
    }

    /** 
     * Adds new Cinema to cinema list
     */
    public void addNewCinema(Cinema newCinema){
        this.cinemas.add(newCinema);
        this.numCinemas++;
    }

    /**
     * Prints list of Cinemas in the Cineplex
     */
    public void printCinemas(){
        System.out.println("Cinemas at "+ this.location +": \n");

        int[] cinemasPrinted = new int[2];
        //initiaising values in array
            for (int i=0;i<Cinema.CinemaClass.values().length;i++){
                    cinemasPrinted[i]=0;//0 for not printed, 1 for printed
                }
        
        //print cinemas
        for (int i=0;i<numCinemas;i++){
            if (cinemasPrinted[this.cinemas.get(i).getCinemaClass().ordinal()]==0) {
                System.out.println(this.cinemas.get(i).getCinemaClass());
                System.out.println("Number of seats: " + this.cinemas.get(i).getNumSeats());
                //System.out.println("Description: " + this.cinemas.get(i).getCinemaDetails());
                System.out.println();

                cinemasPrinted[this.cinemas.get(i).getCinemaClass().ordinal()]=1;
            }
        }

        
    }

    /**
     * @param i
     * @return cinema in cinema list corresponding to i
     */
    public Cinema getCinema(int i) {
        return this.cinemas.get(i);
    }

    /**
     * @return location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return numCinemas
     */
    public int getNumCinemas() {
        return this.numCinemas;
    }

    /**
     * @return cinemas list
     */
    public ArrayList<Cinema> getCinemas() {
        return cinemas;
    }

    

}
