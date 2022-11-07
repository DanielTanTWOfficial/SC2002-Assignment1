package controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import model.Cinema;
import model.Cineplex;
import model.Movie;
import model.MovieListing;
import model.SerializationUtil;
import model.Showtime;
import model.Ticket;
import model.Vendor;
import model.Cinema.CinemaClass;
import model.Movie.ShowingStatus;
import model.Ticket.TicketType;

public class CustomerController {
    /**
     * Displays the top 5 movie listings by ticket sales or reviewer ratings
     * @param bySales
     */
    public static void displayTopMovieListings() {
    	ArrayList<Object> mListings = new ArrayList<>();
    	ArrayList<MovieListing> castedListings = new ArrayList<>();
    	MovieListing mListing = null;
		String filterVal = "";
		boolean invalid = true;
		boolean bySales = true;

		// read set filter value from file
		Path path = Paths.get("filter.txt");
		try {
			filterVal = Files.readString(path, StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("=============== ALL MOVIES =============== ");

		// if filter value is set by admin, user cannot choose, otherwise they can choose
		if(filterVal == "ratings") {
			bySales = false;
		}
		else if(filterVal == "any") {
			while(invalid) {
				System.out.println("Do you want to filter by ticket sales (1) or overall rating (2)? ");
				switch(InputController.getIntRange(1, 2)) {
					case 1:
						bySales = true;
						invalid = false;
						break;
					case 2:
						bySales = false;
						invalid = false;
						break;
					default:
						System.out.println("Invalid option");
						break;
					}
			}
		}
    	
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		if(!bySales) {
    			mListing.setBySales(false);
    		}
    		castedListings.add(mListing);
    	}
    	
    	// sort the castedListings ArrayList to get the sorted movie listings
    	Collections.sort(castedListings);
    	
    	// display the top 5 movie listings by sales/ratings
    	for(int i=0;i<5;i++) {
    		System.out.print((i+1) + ". ");
    		mListing = castedListings.get(i);
    		mListing.printInfo(false);
    	}
    }
    
    /**
     * Called to print all movie listing information.
     */
    public static void displayAllMovieListings() {
    	ArrayList<Object> mListings = new ArrayList<>();
    	MovieListing mListing = null;
    	
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("=============== TOP MOVIES =============== ");
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		mListing.printInfo(false);
    	}
    }

	/**
     * Called to print details of a specific movie listing
     */
	public static void displaySpecificListing() {
		ArrayList<Object> mListings = new ArrayList<>();
    	MovieListing mListing = null;
		int selection = 0;

		try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		System.out.println((1+1) + ". " + mListing.getMovie().getTitle());
    	}

		System.out.println("Which movie do you want to view details of? ");
		selection = InputController.getIntRange(1, mListings.size());

		mListing = (MovieListing)mListings.get(selection-1);

		mListing.printInfo(true);

	}

	/**
     * Called to print seating chart for a particular showtime
	 * @return int
     */
	public static int checkAvailableSeats() {
		ArrayList<Object> cineplexesInfo = new ArrayList<>();
		ArrayList<Cineplex> cineplexes = new ArrayList<>();
    	ArrayList<Cinema> cinemas = new ArrayList<>();
		ArrayList<Object> mListings = new ArrayList<>();
		ArrayList<Showtime> showtimes = new ArrayList<>();
		ArrayList<Showtime> matchingShowtimes = new ArrayList<>();
		Vendor vendor = null;
		MovieListing mListing = null;
		Showtime showtime = null;
		Cineplex cineplex = null;
		CinemaClass cinemaClass;
		int count;
		int selection = 0;
		String usrInput, location;
		LocalDate filterDate;

		// dates will be formatted into YYYY-MM-DD format
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		Scanner sc = new Scanner(System.in);

		System.out.println("=============== SEAT AVAILABILITY =============== ");
		System.out.println("Available cineplexes: ");
    	try {
			cineplexesInfo = SerializationUtil.deserialize("VendorCineplexesInfo.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read cineplexes info.");
			return 0;
		}
    	
    	System.out.println("Available cineplexes: ");
    	
		vendor = (Vendor)cineplexesInfo.get(0);

    	// get list of cineplexes to display to user
    	cineplexes = vendor.getCineplexes();
    	
    	for(int i=0;i<cineplexes.size();i++) {
    		System.out.println((1+1) + ". " + cineplexes.get(i).getLocation());
    	}
    	
		System.out.println("Enter the cineplex to view the showtimes: ");
		selection = InputController.getIntRange(1, cineplexes.size());
    	
		cineplex = cineplexes.get(selection-1);
		location = cineplex.getLocation();

		System.out.println("Cinema classes: ");
		count = 1;
    	for(CinemaClass status : CinemaClass.values()) {
    		System.out.println(count + ". " + status);
    		count++;
    	}
		
		System.out.println("Enter the desired cinema class");
		selection = InputController.getIntRange(1, CinemaClass.values().length);

		cinemaClass = CinemaClass.values()[selection-1];

    	System.out.println("Available movies to check showtimes for: ");
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read movie listings.");
			return 0;
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		System.out.println((1+1) + ". " + mListing.getMovie().getTitle());
    	}
    	
		System.out.println("Which movie do you want to view the showtimes for: ");
		selection = InputController.getIntRange(1, mListings.size());
    	
    	mListing = (MovieListing)mListings.get(selection-1);

		showtimes = mListing.getShowtimes();

    	System.out.println("Enter the date to view showtimes for YYYY/MM/DD (E.g. 2022/10/03): ");
		usrInput = sc.next();
    	filterDate = LocalDate.parse(usrInput, dateFormat);

		// filter out showtimes available for the chosen cineplex and cinemaClass on selected date only
		for(int i=0;i<showtimes.size();i++) {
			if(showtimes.get(i).getLocation() == location && showtimes.get(i).getCinemaBooking().getCinemaClass() == cinemaClass && showtimes.get(i).getDate().isEqual(filterDate)) {
				matchingShowtimes.add(showtimes.get(i));
			}
		}

		for(int i=0;i<matchingShowtimes.size();i++) {
			System.out.println((i+1) + ". Date: " + matchingShowtimes.get(i).getDate() + " Time: " + matchingShowtimes.get(i).getStart());
		}

		System.out.println("Which showtime do you want to check seat availability of: ");
		selection = InputController.getIntRange(1, matchingShowtimes.size());

		showtime = matchingShowtimes.get(selection-1);

		// print the seats
		showtime.getCinemaBooking().printSeats();

		return 1;
	}

	/*
	 * 1. List movies
	 * 2. List and Choose Cineplex
	 * 3. Choose Movie 
	 * 4. List and Choose Showtime
	 * 5. List and Choose Seat 
	 * 6. Compute Price
	 * 7. Prompt for email and mobile number
	 * 8. Perform Transaction
	 * 9. Save booking
	 */
	
	// 1. List Movies
	// 2. Select movie
	// 3. Display showtimes
	// 4. Select showtime
	// 5. Display seats
	// 6. Select seat
	// 7. Display ticket types
	// 8. Select ticket type
	// 9. Create ticket
	public static void makeBooking() {

		ArrayList<Object> vendors = VendorController.readVendorsFile();
		ArrayList<Object> movieListings = MovieListingController.readMovieListingsFile();
		Vendor vendor = (Vendor) vendors.get(0); // only have 1 vendor

		// 1. List Movies
		ArrayList<Movie> movies = new ArrayList<Movie>();
		for (int i = 0; i < movieListings.size(); i++) {
			MovieListing list = (MovieListing) movieListings.get(i);
			if (!movies.contains(list.getMovie()))
				movies.add(list.getMovie());
			System.out.println(movies.size() + ". " + list.getMovie().getTitle());
		}
		
		// 2. Select movie
		System.out.print("Select Movie: ");
		int chosenMovie = InputController.getIntRange(1, movies.size()) -1;
		Movie movie = movies.get(chosenMovie);
		
		// 3. Display Show times
		ArrayList<Showtime> showtimes = new ArrayList<Showtime>();
		for (int i = 0; i < movieListings.size(); i++) {
			MovieListing list = (MovieListing) movieListings.get(i);
			if (list.getMovie() == movies.get(chosenMovie)) {
				for (int j = 0; j < list.getShowtimes().size(); j++) {
					Showtime showtime = list.getShowtimes().get(j);
					showtimes.add(showtime);
					System.out.printf("%d. Location: %s, Cinema: %s, Date: %s %s\n", showtimes.size(), showtime.getLocation(), showtime.getCinemaCode(), showtime.getDate(), showtime.getStart());
				}
				break;
			}
		}
		
		// 4. Select Show times
		System.out.print("Select Showtime: ");
		int chosenShowtime = InputController.getIntRange(1, showtimes.size()) -1;
		Showtime showtime = showtimes.get(chosenShowtime);
		
		// 5. Display seats
		System.out.println();
		showtime.getCinemaBooking().printSeats();
		
		// 6. Select seat
		System.out.print("Select Seat: ");
		String chosenSeat = InputController.getString();
		System.out.println("Selected String: " + chosenSeat);
		
		// 7. Display ticket types
		for (int i = 0; i < TicketType.values().length; i++) {
			System.out.println(i+1 + ". " + TicketType.values()[i]);
		}
		
		// 8. Select ticket type
		System.out.print("Select Ticket Type: ");
		int chosenType = InputController.getIntRange(1, TicketType.values().length) -1;
		System.out.println();
		
		// 9. Create ticket
		Ticket ticket = new Ticket("T001", showtime.getShowtimeId(), showtime.getCinemaCode(), movie.getTitle(), TicketType.values()[chosenType], 0, chosenSeat);
		PriceController.computePrice(ticket, showtime, showtime.getCinemaBooking().getCinemaClass(), movie);
		System.out.println(ticket.getPrice());
		
		// Set seat as taken !!THIS DOESNT UPDATE MOVIELISTING BECAUSE IDK HOW
		int row = (int) chosenSeat.charAt(0) - 65;
		chosenSeat = chosenSeat.replace(Character.toString(chosenSeat.charAt(0)), "");
		int column = Integer.parseInt(chosenSeat) - 1;
		showtime.getCinemaBooking().assignSeat(row, column);
		
		BookingController.saveTicketsFile(ticket);
		// perform transaction
		// prompt for email and movile phone
		
		// generate ticket
	}
}
