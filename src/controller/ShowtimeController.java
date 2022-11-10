package controller;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import model.SerializationUtil;
import model.Showtime;
import model.Vendor;
import model.Movie;
import model.MovieListing;
import model.Review;
import model.Movie.MovieRating;
import model.Movie.MovieType;
import model.Movie.ShowingStatus;
import model.Cineplex;
import model.Cinema;

/**
 * @author Daniel
 * Controller class for actions related to showtimes
 */
public class ShowtimeController {
    /**
     * Provides admin options to choose action to take for showtime management
     */
	public static void showtimeActions() {
		System.out.println("=============== SHOWTIME CONTROL =============== ");
		System.out.println("1. Create a new movie showtime");
		System.out.println("2. Update a movie showtime");
		System.out.println("3. Remove a movie showtime");
		System.out.println("============================================= ");
        System.out.print("Select action: ");
		switch(InputController.getIntRange(1, 3)) {
		case 1:
			ShowtimeController.addShowtime();
			break;
		case 2:
			ShowtimeController.editShowtime();
			break;
		case 3:
			ShowtimeController.removeShowtime();
			break;
		default:
			System.out.println("Invalid option");
			break;
		}
	}
    /**
     * Called to add a new showtime for a movie
     * @return int
     */
    public static int addShowtime() {
    	ArrayList<Object> mListings = new ArrayList<>();
    	ArrayList<Object> cineplexesInfo = new ArrayList<>();
    	ArrayList<Cineplex> cineplexes = new ArrayList<>();
    	ArrayList<Cinema> cinemas = new ArrayList<>();
		Vendor vendor = null;
		Cinema cinema = null;
		Cineplex cineplex = null;
    	MovieListing mListing = null;
    	int selection = 0;
    	String showtimeId,  usrInput;
    	LocalDate date;
    	LocalTime start, end;
    	Showtime newShowtime;
    	
    	// dates will be formatted into YYYY-MM-DD format
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    	// time will be formatted into HH:MM format
    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("=============== SHOWTIME CREATION =============== ");
    	System.out.println("Available movies to add showtimes for: ");
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read movie listings.");
			return 0;
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		System.out.println((i+1) + ". " + mListing.getMovie().getTitle());
    	}
    	
		System.out.println("Enter the movie to add the showtime for: ");
		selection = InputController.getIntRange(1, mListings.size());
	
    	mListing = (MovieListing)mListings.get(selection-1);
    	
    	try {
			cineplexesInfo = SerializationUtil.deserialize("cineplexes.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read cineplexes info.");
			return 0;
		}
    	
    	System.out.println("Available cineplexes: ");
    	
		//vendor = (Vendor)cineplexesInfo.get(0);

    	// get list of cineplexes to display to user
		
		for(int i=0;i<cineplexesInfo.size();i++) {
			cineplexes.add((Cineplex)cineplexesInfo.get(i));
		}
    	//cineplexes = vendor.getCineplexes();
    	
    	for(int i=0;i<cineplexes.size();i++) {
    		System.out.println((i+1) + ". " + cineplexes.get(i).getLocation());
    	}
    	
		System.out.println("Enter the cineplex for the showtime: ");
		selection = InputController.getIntRange(1, cineplexes.size());
    	
    	System.out.println("Available cinemas: ");
    	
		cineplex = cineplexes.get(selection-1);
    	cinemas = cineplex.getCinemas();
    	
    	for(int i=0;i<cinemas.size();i++) {
    		System.out.println((i+1) + ". " + cinemas.get(i).getCinemaCode());
    	}
    	
		System.out.println("Select the cinema to add the showtime for: ");
		selection = InputController.getIntRange(1, cinemas.size());
    	
    	cinema = cinemas.get(selection-1);
    	
    	System.out.println("Enter the showtime date YYYY/MM/DD (E.g. 2022/10/02): ");
    	date = InputController.getDate();
    	
    	System.out.println("Enter the showtime start time HH:MM (E.g. 10:30): ");
    	usrInput = sc.next();
    	start = LocalTime.parse(usrInput, timeFormat);
    	
    	end = start.plus(mListing.getMovie().getDuration());
    	
    	// set the showtimeId (1st word of movie title + showtime index in listing)
    	showtimeId = mListing.getMovie().getTitle().split("[ \\t\\n\\,\\?\\;\\.\\:\\!]")[0] + mListing.getShowtimes().size();

    	// create the new showtime object
    	newShowtime = new Showtime(showtimeId, date, start, end, cinema, cineplex.getLocation());

		// check if the showtime will overlap with another showtime at the same Cinema
		if(checkShowtimeOverlap(newShowtime, mListings)) {
			System.out.println("The showtime overlaps with another existing showtime!");
			return 0;
		}

		// check if there is a clash of showings of the same movie at the same cineplex at the same start time
		if(checkDuplicateShowtime(newShowtime, mListing)) {
			System.out.println("There is already a showtime at this time for this movie at this cineplex!");
			return 0;
		}
    	
		// add the new showtime object to the movie listing showtime ArrayList
    	mListing.addShowtime(newShowtime);
    	
    	// save new movie listings to file
    	File dfile = new File("movieListings.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// serialize updated movies to file
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		try {
    			SerializationUtil.serialize(mListing, "movieListings.ser");
    		} catch (IOException e) {
    			e.printStackTrace();
    			System.out.println("MovieListing update unsuccessful!");
    			return 0;
    		}
    	}
    	
    	return 1;
    }

	/** 
	 * Called by addShowtime() to check if a new showtime overlaps with another at the same cinema
	 * @param showtime
	 * @param mListings
	 * @return boolean
	 */
	public static boolean checkShowtimeOverlap(Showtime showtime, ArrayList<Object> mListings) {
		ArrayList<Showtime> showtimes = new ArrayList<>();
		MovieListing mListing = null;

		// loop through all movie listings and their showtimes
		for(int i=0;i<mListings.size();i++) {
			mListing = (MovieListing)mListings.get(i);
			showtimes = mListing.getShowtimes();
			for(int j=0;j<showtimes.size();j++) {
				// first check if the cinema is the same
				if(showtime.getCinemaCode() == showtimes.get(j).getCinemaCode()) {
					// then check if the date is the same
					if(showtime.getDate().isEqual(showtimes.get(j).getDate())) {
						// check if the showtimes overlap
						if(showtime.getStart().isBefore(showtimes.get(j).getEnd()) && showtimes.get(j).getStart().isBefore(showtime.getEnd())) {
							// make sure showtime is not the one we are trying to edit
							if(!showtime.getShowtimeId().equals(showtimes.get(j).getShowtimeId())) {
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}

	/** 
	 * Called by addShowtime() to check if a new showtime starts at the same time as another showtime for the same movie
	 * at the same Cineplex
	 * @param showtime
	 * @param mListing
	 * @return boolean
	 */
	public static boolean checkDuplicateShowtime(Showtime showtime, MovieListing mListing) {
		ArrayList<Showtime> showtimes = mListing.getShowtimes();

		for(int i=0;i<showtimes.size();i++) {
			// first check if the cineplex is the same
			if(showtime.getLocation() == showtimes.get(i).getLocation()) {
				// then check if the date is the same
				if(showtime.getDate().isEqual(showtimes.get(i).getDate())) {
					// check if the showtimes have the same start time
					if(showtime.getStart().equals(showtimes.get(i).getStart())) {
						// make sure showtime is not the one we are trying to edit
						if(!showtime.getShowtimeId().equals(showtimes.get(i).getShowtimeId())) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}
    
    /**
     * Called to allow staff to edit the showtimes of movies
     * @return int
     */
    public static int editShowtime() {
    	ArrayList<Object> mListings = new ArrayList<>();
    	ArrayList<Showtime> showtimes = new ArrayList<>();
    	MovieListing mListing = null;
    	Showtime showtime = null;
    	int selection = 0;
    	LocalDate date;
    	LocalTime start, end;
    	String usrInput;
    	
    	Scanner sc = new Scanner(System.in);
    	// dates will be formatted into YYYY-MM-DD format
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    	// time will be formatted into HH:MM format
    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    	
    	// let user choose movie listing to edit showtimes for
    	System.out.println("=============== SHOWTIME UPDATE =============== ");
    	System.out.println("Available movies to edit showtimes for: ");
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read movie listings.");
			return 0;
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		System.out.println((i+1) + ". " + mListing.getMovie().getTitle());
    	}
    	
		System.out.println("Enter the movie to edit the showtime for: ");
		selection = InputController.getIntRange(1, mListings.size());
    	
    	mListing = (MovieListing)mListings.get(selection-1);
    	
    	// list existing showtimes for the selected movie listing
    	System.out.println("Available showtimes: ");
    	showtimes = mListing.getShowtimes();
    	for(int i=0;i<showtimes.size();i++) {
    		showtime = (Showtime)showtimes.get(i);
    		System.out.println((i+1) + ". ");
    		showtime.printShowtime();
    	}
    	
		System.out.println("Enter the showtime to edit: ");
		selection = InputController.getIntRange(1, showtimes.size());
    	
    	showtime = (Showtime)showtimes.get(selection-1);
    	
    	// ask the user which attribute of showtime is to be edited
    	System.out.println("Choose showtime attribute to edit: ");
    	System.out.println("1. Date");
    	System.out.println("2. Start time");
    	
    	switch(InputController.getIntRange(1, 2)) {
    	case 1:
    		System.out.println("Enter the new date YYYY/MM/DD (E.g. 2022/10/03): ");
    		date = InputController.getDate();
    		
    		showtime.editDate(date);
    		break;
    	case 2:
    		System.out.println("Enter the new start time HH:MM (E.g. 10:30): ");
        	usrInput = sc.next();
        	start = LocalTime.parse(usrInput, timeFormat);
			end = start.plus(mListing.getMovie().getDuration());
        	showtime.editStart(start);
			showtime.editEnd(end);
			if(checkShowtimeOverlap(showtime, mListings)) {
				System.out.println("Cannot confirm changes as showtime will overlap with another!");
				return 0;
			}
			if(checkDuplicateShowtime(showtime, mListing)) {
				System.out.println("Cannot confirm changes as there is already another showtime at the same time for this movie at this cineplex!");
				return 0;
			}
        	break;
        default:
        	System.out.println("You entered an invalid option!");
        	return 0;
    	}
    	
    	// re-serialise the updated movie listing object
    	File dfile = new File("movieListings.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// serialize updated movies to file
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		try {
    			SerializationUtil.serialize(mListing, "movieListings.ser");
    		} catch (IOException e) {
    			e.printStackTrace();
    			System.out.println("MovieListing update unsuccessful!");
    			return 0;
    		}
    	}
    	
    	return 1;
    }

	/**
     * Called to remove showtimes
     * @return int
     */
	public static int removeShowtime() {
		ArrayList<Object> mListings = new ArrayList<>();
    	ArrayList<Showtime> showtimes = new ArrayList<>();
    	MovieListing mListing = null;
    	Showtime showtime = null;
    	int selection = 0;

		Scanner sc = new Scanner(System.in);

		System.out.println("=============== REMOVE SHOWTIME =============== ");
		System.out.println("Available movies to remove the showtime for: ");
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read movie listings.");
			return 0;
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		System.out.println((i+1) + ". " + mListing.getMovie().getTitle());
    	}
    	
		System.out.println("Enter the movie to remove the showtime for: ");
		selection = InputController.getIntRange(1, mListings.size());
    	
    	mListing = (MovieListing)mListings.get(selection-1);
    	
    	// list existing showtimes for the selected movie listing
    	System.out.println("Available showtimes: ");
    	showtimes = mListing.getShowtimes();
    	for(int i=0;i<showtimes.size();i++) {
    		showtime = (Showtime)showtimes.get(i);
    		System.out.println((i+1) + ". ");
    		showtime.printShowtime();
    	}
    	
		System.out.println("Enter the showtime to remove: ");
		selection = InputController.getIntRange(1, showtimes.size());
    	
    	showtime = (Showtime)showtimes.remove(selection-1);

		// re-serialise the updated MovieListing object
    	File dfile = new File("movieListings.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// serialize updated movie listings to file
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		try {
    			SerializationUtil.serialize(mListing, "movieListings.ser");
    		} catch (IOException e) {
    			e.printStackTrace();
    			System.out.println("MovieListing update unsuccessful!");
    			return 0;
    		}
    	}

    	return 1;
	}

	
}