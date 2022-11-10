package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import model.Vendor;
import model.Cineplex;
import model.Cinema;
import model.CinemaBooking;
import model.SerializationUtil;

/**
 * @author Bernice
 * This controller handles Cinplex related methods
 */
public class CineplexController {
    // public static void cineplexController(){

    //     printCineplexes();
    //     addCineplex();

    //     //delete serialization file
    //     File dfile = new File("VendorCineplexesInfo.ser");
    //     try {
    //         SerializationUtil.deleteFile(dfile);
    //     }catch(IOException e){
    //         e.printStackTrace();
    //     }

    // }

    /**
     * Reads the Cineplex objects from file
     * @return ArrayList<Object>
     */
    public static ArrayList<Object> readCineplexesFile() {
        ArrayList<Object> cineplexes = new ArrayList<>();
        try {
			cineplexes = SerializationUtil.deserialize("cineplexes.ser");
            return cineplexes;
		} catch (IOException | ClassNotFoundException e) {
			// e.printStackTrace();
		}
        return new ArrayList<Object>();
    }

    /**
     * Creates the cineplexes and cinemas
     */
    public static void addCineplexes(Vendor vendor, ArrayList<Cineplex> cineplexesArray){
        for (int i = 0; i < cineplexesArray.size(); i++) {
            vendor.addNewCineplex(cineplexesArray.get(i));

            try {
                SerializationUtil.serialize(cineplexesArray.get(i),"cineplexes.ser");
                System.out.println("Cineplex @ " + cineplexesArray.get(i).getLocation() + " added!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Cineplex adding unsuccessful!");
            }
        }
    }

    /**
     * Displays the cineplexes
     */
    public static void displayCineplexes(Vendor vendor) {
        // ArrayList<Object> cineplexes = readCineplexesFile();

        if (vendor.getNumCineplexes() == 0) {
            System.out.println("No cineplexes registered! Press 0 to return.");
        }
        else {
            vendor.printCineplexes();
        }
    }

    /**
     * returns 0 to return back to main menu
     * else, returns the cineplex chosen in "cineplexes.ser"
     * @param vendor
     * @return Cineplex
     */
    public static Cineplex chooseCineplex(Vendor vendor) {
        // ArrayList<Object> cineplexesInfo = readCineplexesFile();

        boolean exit = false;
        while (!exit) {
            displayCineplexes(vendor);
            System.out.println("Choose a cineplex to view your movie (0 to return): ");
            int choice = InputController.getInt();
            if (choice == 0) {
                exit = true;
                System.out.println("Returning to previous menu...");
            }
            else if (choice < 0 || choice > vendor.getNumCineplexes() + 1) {
                System.out.println("Invalid cineplex! Please try again...");
            }
            else {
                return vendor.getCineplex(choice - 1);
            }
        }
        return null;
    }
}