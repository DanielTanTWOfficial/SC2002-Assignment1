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
import model.MovieListing;
import model.SerializationUtil;
import model.Showtime;
import model.Vendor;
import model.Cinema.CinemaClass;
import model.Movie.ShowingStatus;

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

		System.out.println("=============== TOP MOVIES =============== ");

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
    	ArrayList<Object> movieListings = MovieListingController.readMovieListingsFile();
    	MovieListing movieListing;
    	
		System.out.println("=============== ALL MOVIES =============== ");
    	for(int i = 0; i < movieListings.size(); i++) {
    		movieListing = (MovieListing) movieListings.get(i);
    		movieListing.printInfo(false);
    	}
    }

	public static void displayShowingMovieListings() {
		System.out.println("Current Movies Available for Booking: ");

		int numberMoviesShowing = 1;
		ArrayList<Object> movieListings = MovieListingController.readMovieListingsFile();


		for (int i = 0; i < movieListings.size(); i++) {
			MovieListing currentMovieListing = (MovieListing) movieListings.get(i);
			if (currentMovieListing.getMovie().getStatus() == ShowingStatus.NOW_SHOWING || currentMovieListing.getMovie().getStatus() == ShowingStatus.PREVIEW) {
				System.out.print(numberMoviesShowing + ": ");
				currentMovieListing.printSimpleInfo();
				numberMoviesShowing++;
			}
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
	 * 1. Display all movie listings
	 * 2. Display and Choose Cineplex
	 * 3. Choose cinema class 
	 * 4. Choose Movie 
	 * 5. List and Choose Showtime
	 * 6. List and Choose Seat 
	 * 7. Compute Price
	 * 8. Prompt for email and mobile number
	 * 9. Perform Transaction
	 * 10. Save booking
	 */
	public static void makeBooking() {
		// Step 1 - List all movie listings that are available for booking
		displayShowingMovieListings();

		// Step 2 - Display all cineplexes for Cathay, and let user choose one
		// @return chosenCineplex
		// !!! Future feature: let user choose vendor
		ArrayList<Object> vendors = VendorController.readVendorsFile();
		Vendor cathay = (Vendor) vendors.get(0); // only have 1 vendor

		boolean cineplexDone = false;
		Cineplex chosenCineplex;
		ArrayList<Object> movieListings = MovieListingController.readMovieListingsFile();
		while (!cineplexDone) {
			chosenCineplex = CineplexController.chooseCineplex(cathay);
			if (chosenCineplex == null) { // exit booking
				return;
			}

			// check if cineplex has movieListings, else pick another cineplex
			int numberShowtimes = 0;
			for (int i = 0; i < movieListings.size(); i++) {
				MovieListing currentMovieListing = (MovieListing) movieListings.get(i);
				for (int j = 0; j < currentMovieListing.getShowtimes().size(); j++) {
					Showtime currentShowtime = currentMovieListing.getShowtimes().get(j);
					if (chosenCineplex.getLocation().equals(currentShowtime.getLocation())) {
						numberShowtimes++;
					}
				}
			}
			if (numberShowtimes == 0) {
				System.out.println("Chosen cineplex has no movies showing! Please pick another cineplex.");
			}
			else {
				cineplexDone = true;
			}
		}

		// Step 3 - Choose cinema class
		System.out.println("Please choose an available cinema class:");
		ArrayList<CinemaClass> presentCinemaClasses = chosenCineplex.getCineplexCinemaClasses();
		int counter = 1;
		for (CinemaClass status: presentCinemaClasses) {
			System.out.println(counter + ": " + status);
			counter++;
		}

		CinemaClass chosenCinemaClass;
		int chosenCinemaClassChoice = InputController.getIntRange(1, presentCinemaClasses.size());
		chosenCinemaClass = presentCinemaClasses.get(chosenCinemaClassChoice - 1);
		
		// Step 4 - Choose Movie
		// if chosen movie has showtimes in chosenCineplex, then list them 
		System.out.print("Choose a movie from the list: ");
		int movieChoice = InputController.getInt();

		ArrayList<Showtime> showtimeChoices = new ArrayList<>();
		System.out.println("Showtimes: ");
		MovieListing chosenMovieListing = (MovieListing) movieListings.get(movieChoice - 1);
		for (int i = 0; i < chosenMovieListing.getShowtimes().size(); i++) {
			Showtime currentShowtime = chosenMovieListing.getShowtimes().get(i);
			if (chosenCineplex.getLocation().equals(currentShowtime.getLocation()) && (currentShowtime.getCinemaBooking().getCinemaClass().equals(chosenCinemaClass))) {
				showtimeChoices.add(currentShowtime);
				System.out.print((i + 1) + ": ");
				currentShowtime.printShowtime();
			}
		}

		// Choose seats
		System.out.print("Choose the showtime: ");
		int showtimeChoice = InputController.getInt();
		Showtime chosenShowtime = showtimeChoices.get(showtimeChoice - 1);
		chosenShowtime.getCinemaBooking().printSeats();

		boolean seatChosen = false;
		boolean anotherSeatChoice = true;
		int seatsChosen = 0;
		int row, col; // seat details
		while (!seatChosen && anotherSeatChoice) {
			System.out.print("Please enter the row number: ");
			row = InputController.getIntRange(1, chosenShowtime.getCinemaBooking().getNumRows());
			System.out.print("Please enter the col number: ");
			col = InputController.getIntRange(1, chosenShowtime.getCinemaBooking().getNumCols());
			
			if (!chosenShowtime.getCinemaBooking().getSeats()[row - 1][col - 1].getAssigned()) {
				seatChosen = true;
				seatsChosen++;
				chosenShowtime.getCinemaBooking().getSeats()[row - 1][col - 1].assignSeat();
				System.out.println("Would you like to choose another seat? (y/n)");
				anotherSeatChoice = InputController.getBoolean();
				if (anotherSeatChoice) {
					seatChosen = false;
				}
				else {
					anotherSeatChoice = false;
				}
			}
			else {
				System.out.println("Seat has been taken");
			}
		}

		// compute price
		double totalPrice = PriceController.computePrice(chosenCineplex, chosenShowtime, seatsChosen);

		// perform transaction
			// prompt for email and movile phone
		
		// generate ticket
		





		





		
	}
}
