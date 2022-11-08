import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Objects;

import controller.*;
import model.Vendor;
import model.Cinema.CinemaClass;
import model.Cineplex;
import model.Review;
import model.Cinema;

public class MainMenu {
    public void main() {
    	File f = new File("vendors.ser");
        if (f.exists() == false) {
        	initialiseInfra();
        }

        boolean exit = false;
		while (!exit) {
            System.out.println("=============== MOBLIMA =============== ");
            System.out.println("1. Admin");
            System.out.println("2. Movie-goer");
            System.out.println("3. Exit");
            System.out.println("======================================= ");
			System.out.print("Select action: ");
		    switch(InputController.getIntRange(1, 3)) {
                case 1:
                    adminModule();
                    break;
                case 2:
                    movieGoerModule();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting MOBLIMA");
                    break;
                default:
                    System.out.println("Let's try that again...");
			}
			
		}
    }

    // Create Cineplexes and Cinemas
    // NOTE: at least 3 Cineplexes with * Cinemas and more than 5 movies
    // This is not under any of the modules as admins cannot add cineplexes or cinemas so we have to do this manually
    public void initialiseInfra() {
        Vendor cathay = new Vendor("Cathay Cineplexes");
        
        ArrayList<Cineplex> cineplexes = new ArrayList<>();
        Cineplex orchard = new Cineplex("Cineleisure Orchard");
        Cineplex westmall = new Cineplex("West Mall");
        Cineplex jem = new Cineplex("Jem");
        cineplexes.add(orchard);
        cineplexes.add(westmall);
        cineplexes.add(jem);

        ArrayList<Cinema> cinemasOrchard = new ArrayList<>();
        cinemasOrchard.add(new Cinema(CinemaClass.STANDARD, 101, 11, 22));
        cinemasOrchard.add(new Cinema(CinemaClass.STANDARD, 102, 11, 22));
        cinemasOrchard.add(new Cinema(CinemaClass.PLATINUM, 103, 10, 20));
        CinemaController.addCinemas(orchard, cinemasOrchard);
        
        ArrayList<Cinema> cinemasWestmall = new ArrayList<>();
        cinemasWestmall.add(new Cinema(CinemaClass.STANDARD, 201, 11, 22));
        cinemasWestmall.add(new Cinema(CinemaClass.STANDARD, 202, 11, 22));
        cinemasWestmall.add(new Cinema(CinemaClass.PLATINUM, 203, 10, 20));
        CinemaController.addCinemas(westmall, cinemasWestmall);

        ArrayList<Cinema> cinemasJem = new ArrayList<>();
        cinemasJem.add(new Cinema(CinemaClass.STANDARD, 301, 11, 22));
        cinemasJem.add(new Cinema(CinemaClass.STANDARD, 302, 11, 22));
        cinemasJem.add(new Cinema(CinemaClass.PLATINUM, 303, 10, 20));
        CinemaController.addCinemas(jem, cinemasJem);
        CineplexController.addCineplexes(cathay, cineplexes);
        VendorController.addVendor(cathay);
    }

    /*
    Login (must login before you can access the rest of the features)
    1. Create/Update/Remove movie listing
    2. Create/Update/Remove cinema showtimes and the movies to be shown
    3. Configure system settings
        3a. Set Holidays
        3b. Register new admin accounts
    */
    public void adminModule() {
        boolean loggedIn = AdminController.login();

        while (loggedIn) {
            System.out.println("=============== MOBLIMA ADMIN =============== ");
            System.out.println("1. Create/Update/Remove movie listing");
            System.out.println("2. Create/Update/Remove movie showtimes");
            System.out.println("3. Search/List movies");
            System.out.println("4. List Top 5 movies");
            System.out.println("5. Configure system settings");
            System.out.println("6. Log out");
            System.out.println("============================================= ");
            System.out.print("Select action: ");
            switch(InputController.getIntRange(1, 6)) {
                case 1:
                    MovieListingController.movieActions();
                    break;
                case 2:
                    ShowtimeController.showtimeActions();
                    break;
                case 3:
                    CustomerController.displayAllMovieListings();
                    break;
                case 4:
                    CustomerController.displayTopMovieListings();
                    break;
                case 5:
                    systemSettings();
                    break;
                case 6:
                    loggedIn = false;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Let's try that again...");
			}

        }
    }

    public void systemSettings() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=============== System Settings =============== ");
            System.out.println("1. Update prices");
            System.out.println("2. List holidays");
            System.out.println("3. Add holidays");
            System.out.println("4. Delete holidays");
            System.out.println("5. Change password");
            System.out.println("6. Create admin account"); // only allow another admin to create admin accounts
            System.out.println("7. Manage movie ranking filter settings");
            System.out.println("8. Exit");
            System.out.println("============================================= ");
            System.out.print("Select action: ");
            switch(InputController.getIntRange(1, 9)) {
                case 1:
                    PriceController.updatePrices();
                    break;
                case 2:
                    SystemController.listHolidays();
                    break;
                case 3:
                    SystemController.addHolidays();
                    break;
                case 4:
                    SystemController.removeHoliday();
                    break;
                case 5:
                    AdminController.changePassword();
                    break;
                case 6:
                    AdminController.createAdminAccount();
                    break;
                case 7:
                    SystemController.configureFilter();
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Let's try that again...");
            }
        }
    }

    // No need to login
    // 1. Search/List movie
    // 2. View movie details – including reviews and ratings
    // 3. Check seat availability and selection of seat/s.
    // 4. Book and purchase ticket
    // 5. View booking history
    // 6. List the Top 5 ranking by ticket sales OR by overall reviewers’ ratings
    public void movieGoerModule() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=============== Welcome to MOBLIMA! ===============");
            System.out.println("1. Search/List movies");
            System.out.println("2. View movie details");
            System.out.println("3. Check seat availability");
            System.out.println("4. Book and purchase ticket");
            System.out.println("5. View booking history");
            System.out.println("6. List Top 5 movies");
            System.out.println("7. Rate movies");
            System.out.println("8. Exit");
            System.out.println("===================================================");
            System.out.print("Select action: ");
            switch(InputController.getIntRange(1, 8)) {
                case 1:
                    CustomerController.displayAllMovieListings();
                    break;
                case 2:
                    CustomerController.displaySpecificListing();
                    break;
                case 3:
                    CustomerController.checkAvailableSeats();
                    break;
                case 4:
                    CustomerController.makeBooking();
                    break;
                case 5:
                	CustomerController.viewBookingHistory();
                    break;
                case 6:
                    CustomerController.displayTopMovieListings();
                    break;
                case 7:
                    ReviewController.submitReview();
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Let's try that again...");
			}

        }
        
    }


}
