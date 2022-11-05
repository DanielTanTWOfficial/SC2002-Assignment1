package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import model.SerializationUtil;
import model.Showtime;
import model.Vendor;
import model.Holiday;
import model.Movie;
import model.MovieListing;
import model.Review;
import model.Movie.MovieRating;
import model.Movie.MovieType;
import model.Movie.ShowingStatus;
import model.Cineplex;
import model.Cinema;

public class ManagementController {
	/**
     * Provides admin options to choose action to take for movie management
     */
	public static void movieActions() {
		System.out.println("=============== MOVIE CONTROL =============== ");
		System.out.println("1. Create a new movie listing");
		System.out.println("2. Update a movie status");
		System.out.println("3. Remove a movie listing");
		System.out.println("============================================= ");
        System.out.print("Select action: ");
		switch(InputController.getIntRange(1, 3)) {
		case 1:
			createMovie();
			break;
		case 2:
			updateMovieStatus();
			break;
		case 3:
			deleteMovie();
			break;
		default:
			System.out.println("Invalid option");
			break;
		}
	}

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
			addShowtime();
			break;
		case 2:
			editShowtime();
			break;
		case 3:
			removeShowtime();
			break;
		default:
			System.out.println("Invalid option");
			break;
		}
	}

    /**
     * Creates a new Movie object
     * @return int
     */
    public static int createMovie() {
    	String title;
    	String director;
    	ArrayList<String> cast = new ArrayList<>();
    	int numCast = 0, count, selection = 0;
    	String synopsis;
		Duration duration;
    	ShowingStatus showingStatus;
    	MovieRating movieRating;
    	MovieType movieType;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("=============== MOVIE CREATION =============== ");
    	System.out.println("Enter the movie title: ");
    	title = sc.nextLine();
    	System.out.println("Enter the director: ");
    	director = sc.nextLine();
    	System.out.println("Enter the synopsis: ");
    	synopsis = sc.nextLine();
		System.out.println("Enter the duration in minutes: ");
		duration = Duration.ofMinutes(sc.nextLong());
    	
    	while(true) {
	    	System.out.println("Enter the number of cast members (min. 2): ");
	    	try {
	    	    numCast = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	}
	    	if(numCast >= 2) {
	    		break;
	    	}
	    	System.out.println("Too few cast members!");
    	}
    	for(int i=0;i<numCast;i++) {
    		System.out.println("Enter name of cast member " + (i+1) + ": ");
    		cast.add(sc.nextLine());
    	}
    	
    	System.out.println("Available showing status options: ");
    	count = 1;
    	for(ShowingStatus status : ShowingStatus.values()) {
    		System.out.println(count + ". " + status);
    		count++;
    	}
    	System.out.println("Select the showing status: ");
    	selection = sc.nextInt();
    	
    	switch (selection) {
		case 1: showingStatus = ShowingStatus.values()[0];
				break;
		case 2: showingStatus = ShowingStatus.values()[1];
				break;
		case 3: showingStatus = ShowingStatus.values()[2];
				break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + selection);
		}
    	
    	System.out.println("Available movie rating: ");
    	count = 1;
    	for(MovieRating rating : MovieRating.values()) {
    		System.out.println(count + ". " + rating);
    		count++;
    	}
    	System.out.println("Select the movie rating: ");
    	selection = sc.nextInt();
    	
    	switch (selection) {
		case 1: movieRating = MovieRating.values()[0];
				break;
		case 2: movieRating = MovieRating.values()[1];
				break;
		case 3: movieRating = MovieRating.values()[2];
				break;
		case 4: movieRating = MovieRating.values()[3];
		default:
			throw new IllegalArgumentException("Unexpected value: " + selection);
		}
    	
    	System.out.println("Available movie type: ");
    	count = 1;
    	for(MovieType type : MovieType.values()) {
    		System.out.println(count + ". " + type);
    		count++;
    	}
    	System.out.println("Select the movie type: ");
    	selection = sc.nextInt();
    	
    	switch (selection) {
		case 1: movieType = MovieType.values()[0];
				break;
		case 2: movieType = MovieType.values()[1];
				break;
		case 3: movieType = MovieType.values()[2];
				break;
		case 4: movieType = MovieType.values()[3];
		default:
			throw new IllegalArgumentException("Unexpected value: " + selection);
		}
    	
    	Movie newMovie = new Movie(title, director, cast, synopsis, duration, showingStatus, movieRating, movieType);
		
		if(createMovieListing(newMovie) == 1) {
			return 1;
		}
		
		return 0;
    }
    
    /**
     * Creates a new movie listing for the new movie
     * @param movie
     * @return int
     */
    private static int createMovieListing(Movie movie) {
    	MovieListing newMovieListing = new MovieListing(movie);
    	
    	// serialize to file
		try {
			SerializationUtil.serialize(newMovieListing, "movieListings.ser");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Movie listing save unsuccessful!");
			return 0;
		}
		
		return 1;
    }
    
    /**
     * Deletes a movie object
     * @return int
     */
    public static int deleteMovie() {
    	ArrayList<Object> mListings = new ArrayList<>();
    	MovieListing mListing = null;
    	int selection = 0;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("=============== MOVIE DELETION =============== ");
    	System.out.println("Available movies: ");
    	
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		System.out.println((i+1) + ". " + mListing.getMovie().getTitle());
    	}
    	
    	while(true) {
	    	System.out.println("Which movie do you want to delete? ");
	    	try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	}
	    	if(selection <= mListings.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}
    	
    	mListing = (MovieListing)mListings.get(selection-1);
    	
    	// set the showingStatus to END_OF_SHOWING
    	mListing.getMovie().deleteMovie();
    	
    	// remove movie listing
    	mListings.remove(selection-1);
    	
    	// delete movie file to be overwritten
    	File dfile = new File("movieListings.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// save remaining movie objects to file
    	for(int i=0;i<mListings.size();i++) {
			mListing = (MovieListing)mListings.get(i);
    		// serialize to file
    		try {
    			SerializationUtil.serialize(mListing, "movieListings.ser");
    		} catch (IOException e) {
    			e.printStackTrace();
    			System.out.println("Movie listing update unsuccessful!");
    			return 0;
    		}
    	}
    	
    	return 0;
    }
    
    /**
     * Called to update the showing status of movies
     * @return int
     */
    public static int updateMovieStatus() {
    	ArrayList<Object> mListings = new ArrayList<>();
    	MovieListing mListing = null;
    	int count, selection = 0;
    	ShowingStatus showingStatus;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("=============== MOVIE STATUS UPDATE =============== ");
    	System.out.println("Available movies: ");
    	
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read movie listings.");
			return 0;
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		System.out.println((i+1) + ". " + mListing.getMovie().getTitle() + ": " + mListing.getMovie().getStatus());
    	}
    	
    	while(true) {
    		System.out.println("Which movie do you want to update the status for? ");
    		try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	    return 0;
	    	}
	    	if(selection <= mListings.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}
    	
    	mListing = (MovieListing)mListings.get(selection-1);
    	
    	System.out.println("Available status options: ");
    	count = 1;
    	for(ShowingStatus status : ShowingStatus.values()) {
    		System.out.println(count + ". " + status);
    		count++;
    	}
    	System.out.println("Select the showing status: ");
    	selection = sc.nextInt();
    	
    	switch (selection) {
		case 1: showingStatus = ShowingStatus.values()[0];
				break;
		case 2: showingStatus = ShowingStatus.values()[1];
				break;
		case 3: showingStatus = ShowingStatus.values()[2];
				break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + selection);
		}
    	
    	// call to edit movie showing status
    	mListing.getMovie().editStatus(showingStatus);
    	
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
    			System.out.println("Movie update unsuccessful!");
    			return 0;
    		}
    	}
    	
    	return 1;
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
    		System.out.println((1+1) + ". " + mListing.getMovie().getTitle());
    	}
    	
    	while(true) {
    		System.out.println("Enter the movie to add the showtime for: ");
    		try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	    return 0;
	    	}
    		if(selection <= mListings.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}
    	
    	mListing = (MovieListing)mListings.get(selection-1);
    	
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
    	
    	while(true) {
    		System.out.println("Enter the cineplex for the showtime: ");
    		try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	    return 0;
	    	}
    		if(selection <= cineplexes.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}
    	
    	System.out.println("Available cinemas: ");
    	
    	cinemas = cineplexes.get(selection-1).getCinemas();
    	
    	for(int i=0;i<cinemas.size();i++) {
    		System.out.println((i+1) + ". " + cinemas.get(i).getCinemaCode());
    	}
    	
    	while(true) {
    		System.out.println("Select the cinema to add the showtime for: ");
    		try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	    return 0;
	    	}
    		if(selection <= cinemas.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}
    	
    	cinema = cinemas.get(selection-1);
    	
    	System.out.println("Enter the showtime date YYYY/MM/DD (E.g. 2022/10/02): ");
    	usrInput = sc.next();
    	date = LocalDate.parse(usrInput, dateFormat);
    	
    	System.out.println("Enter the showtime start time HH:MM (E.g. 10:30): ");
    	usrInput = sc.next();
    	start = LocalTime.parse(usrInput, timeFormat);
    	
    	end = start.plus(mListing.getMovie().getDuration());
    	
    	// set the showtimeId (1st word of movie title + showtime index in listing)
    	showtimeId = mListing.getMovie().getTitle().split("[ \\t\\n\\,\\?\\;\\.\\:\\!]")[0] + mListing.getShowtimes().size();

    	// create the new showtime object
    	newShowtime = new Showtime(showtimeId, date, start, end, cinema);

		// check if the showtime will overlap with another showtime at the same Cinema
		if(checkShowtimeOverlap(newShowtime, mListing)) {
			System.out.println("The showtime overlaps with another existing showtime!");
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
	 * Called by addShowtime() to check if a showtime overlaps with another at the same cinema
	 * @param showtime
	 * @param mListing
	 * @return boolean
	 */
	public static boolean checkShowtimeOverlap(Showtime showtime, MovieListing mListing) {
		ArrayList<Showtime> showtimes = mListing.getShowtimes();
		
		for(int i=0;i<showtimes.size();i++) {
			// first check if the cinema is the same
			if(showtime.getCinemaCode() == showtimes.get(i).getCinemaCode()) {
				// then check if the date is the same
				if(showtime.getDate().isEqual(showtimes.get(i).getDate())) {
					// check if the showtimes overlap
					if(showtime.getStart().isBefore(showtimes.get(i).getEnd()) && showtimes.get(i).getStart().isBefore(showtime.getEnd())) {
						return true;
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
    		System.out.println((1+1) + ". " + mListing.getMovie().getTitle());
    	}
    	
    	while(true) {
    		System.out.println("Enter the movie to edit the showtime for: ");
    		try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	    return 0;
	    	}
    		if(selection <= mListings.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}
    	
    	mListing = (MovieListing)mListings.get(selection-1);
    	
    	// list existing showtimes for the selected movie listing
    	System.out.println("Available showtimes: ");
    	showtimes = mListing.getShowtimes();
    	for(int i=0;i<showtimes.size();i++) {
    		showtime = (Showtime)showtimes.get(i);
    		System.out.println((i+1) + ". ");
    		showtime.printShowtime();
    	}
    	
    	while(true) {
    		System.out.println("Enter the showtime to edit: ");
    		try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	    return 0;
	    	}
    		if(selection <= showtimes.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}
    	
    	showtime = (Showtime)showtimes.get(selection-1);
    	
    	// ask the user which attribute of showtime is to be edited
    	System.out.println("Choose showtime attribute to edit: ");
    	System.out.println("1. Date");
    	System.out.println("2. Start time");
    	selection = sc.nextInt();
    	
    	switch(selection) {
    	case 1:
    		System.out.println("Enter the new date YYYY/MM/DD (E.g. 2022/10/03): ");
    		usrInput = sc.next();
    		date = LocalDate.parse(usrInput, dateFormat);
    		showtime.editDate(date);
    		break;
    	case 2:
    		System.out.println("Enter the new start time HH:MM (E.g. 10:30): ");
        	usrInput = sc.next();
        	start = LocalTime.parse(usrInput, timeFormat);
			end = start.plus(mListing.getMovie().getDuration());
        	showtime.editStart(start);
			showtime.editEnd(end);
			if(checkShowtimeOverlap(showtime, mListing)) {
				System.out.println("Cannot confirm changes as showtime will overlap with another!");
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
    		System.out.println((1+1) + ". " + mListing.getMovie().getTitle());
    	}
    	
    	while(true) {
    		System.out.println("Enter the movie to remove the showtime for: ");
    		try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	    return 0;
	    	}
    		if(selection <= mListings.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}
    	
    	mListing = (MovieListing)mListings.get(selection-1);
    	
    	// list existing showtimes for the selected movie listing
    	System.out.println("Available showtimes: ");
    	showtimes = mListing.getShowtimes();
    	for(int i=0;i<showtimes.size();i++) {
    		showtime = (Showtime)showtimes.get(i);
    		System.out.println((i+1) + ". ");
    		showtime.printShowtime();
    	}
    	
    	while(true) {
    		System.out.println("Enter the showtime to remove: ");
    		try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	    return 0;
	    	}
    		if(selection <= showtimes.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}
    	
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
    
    /**
     * Called to add holidays into the system
     * @return int
     */
    public static int addHolidays() {
    	ArrayList<Object> holidayObjects = new ArrayList<>();
    	ArrayList<LocalDate> holidays = new ArrayList<>();
    	Holiday holiday = null;
    	LocalDate holidayDate;
    	String usrInput;
    	char selection;
    	boolean firstTime = true;
    	
    	// check if a Holiday object already exists to avoid duplicate creation
    	File f = new File("holidays.ser");
    	if(f.isFile()) {
    		firstTime = false;
    	}
    	
    	if(firstTime) {
    		holiday = new Holiday();
    	}
    	else {
    		try {
    			holidayObjects = SerializationUtil.deserialize("holidays.ser");
    		} catch (IOException | ClassNotFoundException e) {
    			e.printStackTrace();
    			System.out.println("Unable to read movie listings.");
    			return 0;
    		}
    		holiday = (Holiday)holidayObjects.get(0);
    	}
    	
    	holidays = holiday.getHolidays();
    	
    	// dates will be formatted into YYYY-MM-DD format
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("=============== HOLIDAY CREATION =============== ");
    	while(true) {
    		System.out.println("Enter the holiday date YYYY/MM/DD (E.g. 2022/10/03): ");
    		usrInput = sc.next();
    		holidayDate = LocalDate.parse(usrInput, dateFormat);
    		holidays.add(holidayDate);
    		System.out.println("Add another date (Y/N)? ");
    		selection = sc.next().charAt(0);
    		if(selection == 'N') {
    			break;
    		}
    	}
    	
    	// re-serialise the updated holiday object
    	File dfile = new File("holidays.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// serialize updated movies to file
    	holiday = (Holiday)holidayObjects.get(0);
		try {
			SerializationUtil.serialize(holiday, "holidays.ser");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Holiday update unsuccessful!");
			return 0;
		}
    	
    	return 1;
    }

	
	/** 
	 * Called to remove a LocalDate from the Holiday object holidays ArrayList
	 * @return int
	 */
	public static int removeHoliday() {
		ArrayList<Object> holidayObjects = new ArrayList<>();
		ArrayList<LocalDate> holidays = new ArrayList<>();
		Holiday holiday = null;
		boolean firstTime = true;
		int selection = 0;

		Scanner sc = new Scanner(System.in);

		// check if a Holiday object already exists to avoid error
    	File f = new File("holidays.ser");
    	if(f.isFile()) {
    		firstTime = false;
    	}
		if(firstTime) {
			System.out.println("No holiday created yet!");
			return 0;
		}

		try {
			holidayObjects = SerializationUtil.deserialize("holidays.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read holidays.");
			return 0;
		}
		holiday = (Holiday)holidayObjects.get(0);
		holidays = holiday.getHolidays();

		System.out.println("=============== HOLIDAY REMOVAL =============== ");
		System.out.println("Current holidays: ");

		for(int i=0;i<holidays.size();i++) {
			System.out.println((i+1) + ". " + holidays.get(i));
		}

		while(true) {
    		System.out.println("Which holiday do you want to remove: ");
    		try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	    return 0;
	    	}
    		if(selection <= holidays.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}

		// remove selected LocalTime from the ArrayList
		holidays.remove(selection-1);

		// re-serialise the updated holiday object
    	File dfile = new File("holidays.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// serialize updated movies to file
    	holiday = (Holiday)holidayObjects.get(0);
		try {
			SerializationUtil.serialize(holiday, "holidays.ser");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Holiday update unsuccessful!");
			return 0;
		}
    	return 1;
	}

	/** 
	 * Allows admins to list all saved holidays
	 */
	public static void listHolidays() {
		ArrayList<Object> holidayObjects = new ArrayList<>();
		ArrayList<LocalDate> holidays = new ArrayList<>();
		Holiday holiday = null;
		boolean firstTime = true;

		// check if a Holiday object already exists to avoid error
    	File f = new File("holidays.ser");
    	if(f.isFile()) {
    		firstTime = false;
    	}
		if(firstTime) {
			System.out.println("No holiday created yet!");
			return;
		}

		try {
			holidayObjects = SerializationUtil.deserialize("holidays.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read holidays.");
			return;
		}
		holiday = (Holiday)holidayObjects.get(0);
		holidays = holiday.getHolidays();

		System.out.println("=============== HOLIDAYS =============== ");
		System.out.println("Current holidays: ");

		for(int i=0;i<holidays.size();i++) {
			System.out.println((i+1) + ". " + holidays.get(i));
		}
	}
	
	/** 
	 * Allows admins to set whether movie listings should be ranked by ticket sales/overall ratings/any
	 * @return int
	 */
	public static int configureFilter() {
		String filterVal;
		int selection = 0;

		Scanner sc = new Scanner(System.in);

		System.out.println("=============== RANKING CONTROL =============== ");
		System.out.println("Toggle the filters available to rank the movie listings: ");
		System.out.println("Available filters: ");
		System.out.println("1. Filter by ticket sales");
		System.out.println("2. Filter by overall rating");
		System.out.println("3. Filter by either ticket sales or overall rating");
		
		while(true) {
    		System.out.println("Enter the filter option to set: ");
    		try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	    return 0;
	    	}
    		if(selection <= 3 && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}

		if(selection == 1) {
			filterVal = "sales";
		}
		else if(selection == 2) {
			filterVal = "ratings";
		}
		else {
			filterVal = "any";
		}

		// write the filter value to a text file for reference
		try (PrintWriter out = new PrintWriter("filter.txt")) {
		    out.println(filterVal);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error saving filter to file!");
			return 0;
		}

		return 1;
	}
}
