package model;

import java.util.ArrayList;
import java.util.Objects;

import model.Cinema.CinemaClass;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Bernice
 * The Cineplex class, which implements the Serializable interface
 */
public class Cineplex implements Serializable {
    /**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
    private static final long serialVersionUID = 123458L;
    /**
     * The location attribute stores the cineplex location
     */
    private String location;
    /**
     * The cinemas attribute is an ArrayList of Cinema objects within the Cineplex
     */
    private ArrayList<Cinema> cinemas;
    /**
     * The numCinemas attribute tracks the number of cinemas within a cineplex
     */
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
     * Returns location of cineplex
     * @return location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Sets location of cineplex
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns number of cinemas
     * @return numCinemas
     */
    public int getNumCinemas() {
        return this.numCinemas;
    }

    /**
     * Returns the cinemas in cineplex
     * @return cinemas
     */
    public ArrayList<Cinema> getCinemas() {
        return cinemas;
    }

    /**
     * @param i
     * @return cinema in cinema list corresponding to i
     */
    public Cinema getCinema(int i) {
        return this.cinemas.get(i);
    }

    /** 
     * Adds new Cinema to cinema list
     * @param newCinema
     */
    public void addNewCinema(Cinema newCinema){
        this.cinemas.add(newCinema);
        this.numCinemas++;
    }

    /**
     * Returns the unique cinema classes at the cineplex
     * @return presentCinemaClasses
     */
    public ArrayList<CinemaClass> getCineplexCinemaClasses() {
        ArrayList<CinemaClass> presentCinemaClasses = new ArrayList<>();
        for (int i = 0; i < getCinemas().size(); i++) {
            Cinema currentCinema = getCinema(i);
            if (!presentCinemaClasses.contains(currentCinema.getCinemaClass())) {
                presentCinemaClasses.add(currentCinema.getCinemaClass());
            }
        }
        return presentCinemaClasses;
    }

    /**
     * Prints list of Cinemas in the Cineplex
     */
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
            System.out.println("Cinemas at "+this.location+": \n");
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

    

}
