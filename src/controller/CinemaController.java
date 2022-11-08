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

public class CinemaController {
    public static ArrayList<Object> readCinemasFile() {
        ArrayList<Object> cinemas = new ArrayList<>();
        try {
			cinemas = SerializationUtil.deserialize("cinemas.ser");
            return cinemas;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
        return new ArrayList<Object>();
    }

    public static void addCinemas(Cineplex cineplex, ArrayList<Cinema> cinemasArray) {
        for (int i = 0; i < cinemasArray.size(); i++) {
            cineplex.addNewCinema(cinemasArray.get(i));

            try {
                SerializationUtil.serialize(cinemasArray.get(i),"cineplexes.ser");
                System.out.println("Cinema of code  " + cinemasArray.get(i).getCinemaCode() + " added!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Cinema adding unsuccessful!");
            }
        }
    }

    public static void listCinemas(Cineplex cineplex) {
        // ArrayList<Object> cinemas = readCinemasFile();

        if (cineplex.getNumCinemas() == 0) {
            System.out.println("No cinemas registered!");
        }
        else {
            cineplex.printCinemas();
        }
    }

    public static Cinema chooseCinema(Cineplex cineplex) {
        // ArrayList<Object> cinemas = readCinemasFile();

        System.out.println("Welcome to " + cineplex.getLocation() + " cineplex!");
        boolean exit = false;
        while (!exit) {
            System.out.println("Choose a cinema to view their movie (0 to return): ");
            listCinemas(cineplex);
            int choice = InputController.getInt();
            if (choice == 0) {
                exit = true;
                System.out.println("Returning to previous menu...");
            }
            else if (choice < 0 || choice > cineplex.getNumCinemas() + 1) {
                System.out.println("Invalid cinema! Please try again...");
            }
            else {
                return cineplex.getCinema(choice - 1);
            }
        }
        return null;
    }
}
