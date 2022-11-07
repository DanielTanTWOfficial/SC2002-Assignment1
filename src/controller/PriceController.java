package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import model.Cinema;
import model.Cinema.CinemaClass;
import model.Holiday;
import model.Movie;
import model.SerializationUtil;
import model.Showtime;
import model.Ticket;
import model.Movie.MovieRating;
import model.Ticket.TicketType;

public class PriceController {
	
	public static HashMap<String, Double> priceList;
	
    public static int computePrice(Ticket ticket, Showtime showtime, CinemaClass cinemaClass, Movie movie) {
    	readPrices();
    	
    	Holiday holiday = new Holiday();
		boolean isHoliday = false;
		
		// Check if date of showtime is a holiday
		for (int i = 0; i < holiday.getHolidays().size(); i++) {
			if (holiday.getHolidays().get(i) == showtime.getDate())
				isHoliday = true;
		}
		
		// Check if purchasing Child ticket for a movie M18 || R21
		if (ticket.getTicketType() == TicketType.CHILD && (movie.getMovieRating() == MovieRating.M18 || movie.getMovieRating() == MovieRating.R21)) {
			System.out.println("Cannot purchase child ticket for M18 and R21 movies");
			return 0;
		}
		
		// Check if holiday. Don't care ticket type
		if (isHoliday) {
			ticket.setTicketType(TicketType.ADULT);
			ticket.setPrice(PriceController.priceList.get("adult") + PriceController.priceList.get("holiday"));
		}
		// Computer price for the respective ticket types
		else {
			switch (ticket.getTicketType()) {
				case CHILD:
					ticket.setPrice(PriceController.priceList.get("children"));
					break;
				case SENIOR:
					ticket.setPrice(PriceController.priceList.get("senior"));
					break;
				case ADULT:
					ticket.setPrice(PriceController.priceList.get("adult"));
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
		case DIGITAL:
			ticket.setPrice(ticket.getPrice() + PriceController.priceList.get("digital"));
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
    
    public static void updatePrice(String key, double value) {
    	readPrices();
    	priceList.put(key, value);
    	storePrices();
    }
}
