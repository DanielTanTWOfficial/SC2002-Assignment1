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

    public static void addCathayCineplexes(){
        //-----creating cineplexes and cinemas
        Vendor thisVendor = new Vendor("Cathay Cineplexes");

        Cineplex dummyCineplex;
        Cinema dummyCinema;

        dummyCineplex = new Cineplex("Cineleisure Orchard"); //creating new cineplex
        thisVendor.addNewCineplex(dummyCineplex);
        //ading cinemas to cineplex
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",101);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",102);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",103);
        dummyCineplex.addNewCinema(dummyCinema);

        dummyCineplex = new Cineplex("West Mall"); //creating new cineplex
        thisVendor.addNewCineplex(dummyCineplex);
        //ading cinemas to cineplex
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",201);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",202);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",203);
        dummyCineplex.addNewCinema(dummyCinema);

        dummyCineplex = new Cineplex("JEM"); //creating new cineplex
        thisVendor.addNewCineplex(dummyCineplex);
        //ading cinemas to cineplex
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",301);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",302);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.PLATINUM,10,20,"The most luxurious cinematic destination, only in JEM mall. Platinum Movie Suites: Cinemas with its own exclusive lounge area, leather recliner seats, and wider legroom.",303);
        dummyCineplex.addNewCinema(dummyCinema);


        //serialising data
        try {
            SerializationUtil.serialize(thisVendor,"VendorCineplexesInfo.ser");
        } catch (IOException e) {
            e.printStackTrace();
            return;
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