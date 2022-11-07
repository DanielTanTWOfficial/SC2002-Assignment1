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

    public static ArrayList<Object> readCineplexesFile() {
        ArrayList<Object> cineplexes = new ArrayList<>();
        try {
			cineplexes = SerializationUtil.deserialize("cineplexes.ser");
            return cineplexes;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
        return new ArrayList<Object>();
    }

    /**
     * Creates the cineplexes and cinemas
     */

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
     * might need to change this to accept no parameters because later on we only use the ser file
     * this means we need to add vendorController
     */
    public static void listCineplexes(Vendor vendor) {
        // ArrayList<Object> cineplexes = readCineplexesFile();

        if (vendor.getNumCineplexes() == 0) {
            System.out.println("No cineplexes registered!");
        }
        else {
            // int choice=-1;
            // int back=0;
            // while (back == 0){
            vendor.printCineplexes();
                // Cineplex cineplexChoice = vendor.getCineplex(choice-1);
                // cineplexChoice.printCinemas();
                // System.out.println("Enter 0 to return to Cineplex list. Enter 1 to exit.");
                // back = InputController.getIntRange(0, 1);
        }

    }

    // returns 0 to return back to main menu
    // else, returns the index of the cineplex chosen in "cineplexes.ser"
    public static Cineplex chooseCineplex(Vendor vendor) {
        // ArrayList<Object> cineplexesInfo = readCineplexesFile();

        // System.out.println("Welcome to "+ vendor.getVendorName() + "!");
        boolean exit = false;
        while (!exit) {
            System.out.println("Choose a cineplex to view your movie (0 to return): ");
            listCineplexes(vendor);
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