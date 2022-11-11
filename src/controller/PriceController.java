package controller;

import java.util.ArrayList;
import java.time.LocalDate;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import model.Cinema.CinemaClass;
import model.Holiday;
import model.Movie;
import model.Movie.MovieRating;
import model.Movie.MovieType;
import model.Showtime;
import model.Ticket;
import model.Ticket.TicketType;
/**
 * 
 * @author WeiJie
 * Controller class for actions related to ticket price
 */
public class PriceController {
	
	/**
	 * HashMap read from the price.txt file
	 */
	public static HashMap<String, Double> priceList;

	/*
    Factors that affect price:
        - CinemaType: STANDARD, PLATINUM
        - MovieType: BLOCKBUSTER, INDIE, IMAX
        - Age: Adult, Senior Citizen, Children
        - Date: Holiday, Weekend
     */
	/**
	 * This method allows admin user to update the fields that affect ticket price in price.txt
	 */
    public static void updatePrices() {
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Update Cinema Class Price");
            System.out.println("2. Update Movie Type Price");
            System.out.println("3. Update Ticket Type Price");
            System.out.println("4. Update Weekend Price"); 
            System.out.println("5. Update Holiday Price");
            System.out.println("6. Exit");
            System.out.print("Select action: ");
            switch(InputController.getIntRange(1, 8)) {
                case 1:
					updateCinemaClassPrice();
                    break;
                case 2:
					updateMovieTypePrice();
                    break;
                case 3:
					updateTicketTypePrice();
                    break;
                case 4:
					updateWeekendPrice();
                    break;
                case 5:
					updateHolidayPrice();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Let's try that again...");
            }
        }
    }

    /**
     * This method takes in a ticket object along with our objects needed to compute the price and sets that ticket object's price.
     * returns 1 for success
     * @param ticket
     * @param showtime
     * @param cinemaClass
     * @param movie
     * @return
     */
	public static int computePrice(Ticket ticket, Showtime showtime, CinemaClass cinemaClass, Movie movie) {
    	readPrices();
    	
    	ArrayList<Object> holidayObjects = SystemController.readHolidaysFile();
		Holiday holiday = (Holiday) holidayObjects.get(0);
		ArrayList<LocalDate> holidays = holiday.getHolidays();

		boolean isHoliday = false;
		// Check if date of showtime is a holiday
		for (int i = 0; i < holidays.size(); i++) {
			if (holidays.get(i).equals(showtime.getDate())) {
				isHoliday = true;
				break;
			}
		}

		// Check if holiday. Don't care ticket type
		if (isHoliday) {
			ticket.setTicketType(TicketType.ADULT);
			ticket.setPrice(PriceController.priceList.get("adult") + PriceController.priceList.get("holiday"));
		}
		// Compute price for the respective ticket types
		else {
			if (showtime.getDate().getDayOfWeek().getValue() > 5) {
				ticket.setPrice(PriceController.priceList.get("weekend"));
			}
				
			switch (ticket.getTicketType()) {
				case CHILD:
					ticket.setPrice(ticket.getPrice() +  PriceController.priceList.get("children"));
					break;
				case SENIOR:
					ticket.setPrice(ticket.getPrice() +  PriceController.priceList.get("senior"));
					break;
				case ADULT:
					ticket.setPrice(ticket.getPrice() +  PriceController.priceList.get("adult"));
					break;
				default:
					break;
			}	
		}
		
		// Add price depending on the movie types
		switch (movie.getMovieType()) {
			case IMAX:
				ticket.setPrice(ticket.getPrice() + PriceController.priceList.get("imax"));
				break;
			case BLOCKBUSTER:
				ticket.setPrice(ticket.getPrice() + PriceController.priceList.get("blockbuster"));
				break;
			case INDIE:
				ticket.setPrice(ticket.getPrice() + PriceController.priceList.get("indie"));
				break;
			default:
				break;
		}
		
		// Add price depending on the cinema types
		switch (cinemaClass) {
			case STANDARD:
				ticket.setPrice(ticket.getPrice() + PriceController.priceList.get("standard"));
				break;
			case PLATINUM:
				ticket.setPrice(ticket.getPrice() + PriceController.priceList.get("platinum"));
				break;
			default:
				break;
			}
    	
        return 1;
    }
	
	// stores the current HashMap to txt file
	/**
	 * Updates price.txt with the Hashmap priceList
	 */
    public static void storePrices() {
    	File f = new File("price.txt");
		if(f.exists())
			f.delete();
		
    	try (PrintWriter out = new PrintWriter("price.txt")) {
    		for (Entry<String, Double> entry : priceList.entrySet()) {
    		    String key = entry.getKey();
    		    double value = entry.getValue();
    		    out.println(key + " " + value);
    		}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
	
	// Reads price.txt file and updates the HashMap
    /**
     * Reads the price.txt file and updates it to the Hashmap priceList
     */
    public static void readPrices() {
    	priceList = new HashMap<String, Double>();
    	File file = new File("price.txt");
    	Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(" ");
				priceList.put(line[0], Double.parseDouble(line[1]));
	    	}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * This method updates the CinemaClass keys in the Hashmap and calls storePrices()
     */
    public static void updateCinemaClassPrice() {
		readPrices();
		System.out.println("---Cinema Classes---");

		int counter = 1;
		for (CinemaClass cc: CinemaClass.values()) {
			System.out.println(counter + ": " + cc);
			counter++;
		}

		System.out.print("Please select from the above Cinema Classes: ");
		int choice = InputController.getIntRange(1, counter - 1);
		double newPrice = -1;
		boolean done = false;
		switch (choice) {
			case 1: // STANDARD
				System.out.println("Current price for Cinema Class " + CinemaClass.values()[choice - 1] + " = " + priceList.get("standard"));
				System.out.print("Please enter new price: ");
				while (!done) {
					newPrice = InputController.getPositiveDouble();
					if (newPrice == priceList.get("standard")) {
						System.out.print("Same price entered! Please enter again: ");
					}
					else {
						done = true;
					}
				}
				priceList.put("standard", newPrice);
				System.out.println("Price changed successfully");
				break;
			case 2: // PLATINUM
				System.out.println("Current price for Cinema Class " + CinemaClass.values()[choice - 1] + " = " + priceList.get("platinum"));
				System.out.print("Please enter new price: ");
				while (!done) {
					newPrice = InputController.getPositiveDouble();
					if (newPrice == priceList.get("platinum")) {
						System.out.print("Same price entered! Please enter again: ");
					}
					else {
						done = true;
					}
				}
				priceList.put("platinum", newPrice);
				System.out.println("Price changed successfully");
				break;
			default:
				System.out.println("Price changed unsuccessfully");
		}
		storePrices();
	}

    
    /**
     * This method updates the MovieType keys in the Hashmap and calls storePrices()
     */
	public static void updateMovieTypePrice() {
		readPrices();
		System.out.println("---Movie Types---");

		int counter = 1;
		for (MovieType mt: MovieType.values()) {
			System.out.println(counter + ": " + mt);
			counter++;
		}

		System.out.print("Please select from the above Movie Types: ");
		int choice = InputController.getIntRange(1, counter - 1);
		double newPrice = -1;
		boolean done = false;
		switch (choice) {
			case 1: // BLOCKBUSTER
				System.out.println("Current price for Movie Type " + MovieType.values()[choice - 1] + " = " + priceList.get("blockbuster"));
				System.out.print("Please enter new price: ");
				while (!done) {
					newPrice = InputController.getPositiveDouble();
					if (newPrice == priceList.get("blockbuster")) {
						System.out.print("Same price entered! Please enter again: ");
					}
					else {
						done = true;
					}
				}
				priceList.put("blockbuster", newPrice);
				System.out.println("Price changed successfully");
				break;
			case 2: // INDIE
				System.out.println("Current price for Movie Type " + MovieType.values()[choice - 1] + " = " + priceList.get("indie"));
				System.out.print("Please enter new price: ");
				while (!done) {
					newPrice = InputController.getPositiveDouble();
					if (newPrice == priceList.get("indie")) {
						System.out.print("Same price entered! Please enter again: ");
					}
					else {
						done = true;
					}
				}
				priceList.put("indie", newPrice);
				System.out.println("Price changed successfully");
				break;
			case 3: // IMAX
				System.out.println("Current price for Movie Type " + MovieType.values()[choice - 1] + " = " + priceList.get("imax"));
				System.out.print("Please enter new price: ");
				while (!done) {
					newPrice = InputController.getPositiveDouble();
					if (newPrice == priceList.get("imax")) {
						System.out.print("Same price entered! Please enter again: ");
					}
					else {
						done = true;
					}
				}
				priceList.put("imax", newPrice);
				System.out.println("Price changed successfully");
				break;
			default:
				System.out.println("Price changed unsuccessfully");
		}
		storePrices();
	}

	/**
	 * This method updates the TicketType keys in the Hashmap and calls storePrices()
	 */
	public static void updateTicketTypePrice() {
		readPrices();
		System.out.println("---Ticket Types---");

		int counter = 1;
		for (TicketType tt: TicketType.values()) {
			System.out.println(counter + ": " + tt);
			counter++;
		}

		System.out.print("Please select from the above Ticket Types: ");
		int choice = InputController.getIntRange(1, counter - 1);
		double newPrice = -1;
		boolean done = false;
		switch (choice) {
			case 1: // ADULT
				System.out.println("Current price for Ticket Type " + TicketType.values()[choice - 1] + " = " + priceList.get("adult"));
				System.out.print("Please enter new price: ");
				while (!done) {
					newPrice = InputController.getPositiveDouble();
					if (newPrice == priceList.get("adult")) {
						System.out.print("Same price entered! Please enter again: ");
					}
					else {
						done = true;
					}
				}
				priceList.put("adult", newPrice);
				System.out.println("Price changed successfully");
				break;
			case 2: // SENIOR
				System.out.println("Current price for Ticket Type" + TicketType.values()[choice - 1] + " = " + priceList.get("senior"));
				System.out.print("Please enter new price: ");
				while (!done) {
					newPrice = InputController.getPositiveDouble();
					if (newPrice == priceList.get("senior")) {
						System.out.print("Same price entered! Please enter again: ");
					}
					else {
						done = true;
					}
				}
				priceList.put("senior", newPrice);
				System.out.println("Price changed successfully");
				break;
			case 3: // STUDENT
				System.out.println("Current price for Ticket Type " + TicketType.values()[choice - 1] + " = " + priceList.get("student"));
				System.out.print("Please enter new price: ");
				while (!done) {
					newPrice = InputController.getPositiveDouble();
					if (newPrice == priceList.get("student")) {
						System.out.print("Same price entered! Please enter again: ");
					}
					else {
						done = true;
					}
				}
				priceList.put("student", newPrice);
				System.out.println("Price changed successfully");
				break;
			default:
				System.out.println("Price changed unsuccessfully");
		}
		storePrices();
	}

	/**
	 * This method updates the Weekend keys in the Hashmap and calls storePrices()
	 */
	public static void updateWeekendPrice() {
		readPrices();

		double newPrice = -1;
		boolean done = false;
		System.out.println("Current price for Weekend tickets = " + priceList.get("weekend"));
		System.out.print("Please enter new price: ");
		while (!done) {
			newPrice = InputController.getPositiveDouble();
			if (newPrice == priceList.get("weekend")) {
				System.out.print("Same price entered! Please enter again: ");
			}
			else {
				done = true;
			}
		}
		priceList.put("weekend", newPrice);
		System.out.println("Price changed successfully");

		storePrices();
	}

	/**
	 * This method updates the Holiday keys in the Hashmap and calls storePrices()
	 */
	public static void updateHolidayPrice() {
		readPrices();

		double newPrice = -1;
		boolean done = false;
		System.out.println("Current price for Holiday tickets = " + priceList.get("holiday"));
		System.out.print("Please enter new price: ");
		while (!done) {
			newPrice = InputController.getPositiveDouble();
			if (newPrice == priceList.get("holiday")) {
				System.out.print("Same price entered! Please enter again: ");
			}
			else {
				done = true;
			}
		}
		priceList.put("holiday", newPrice);
		System.out.println("Price changed successfully");
		
		storePrices();
	}
}
