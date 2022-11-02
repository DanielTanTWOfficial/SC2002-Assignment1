package controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import model.SerializationUtil;
import model.Showtime;
import model.Holiday;
import model.Movie;
import model.MovieListing;
import model.Movie.MovieRating;
import model.Movie.MovieType;
import model.Movie.ShowingStatus;

public class ManagementController {
    /**
     * Creates a new Movie object
     * @return
     */
    public static int createMovie() {
    	String title;
    	String director;
    	ArrayList<String> cast = new ArrayList<>();
    	int numCast = 0, count, selection = 0;
    	String synopsis;
    	ShowingStatus showingStatus;
    	MovieRating movieRating;
    	MovieType movieType;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Creating a new movie: ");
    	System.out.println("Enter the movie title: ");
    	title = sc.nextLine();
    	System.out.println("Enter the director: ");
    	director = sc.nextLine();
    	System.out.println("Enter the synopsis: ");
    	synopsis = sc.nextLine();
    	
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
    	
    	Movie newMovie = new Movie(title, director, cast, synopsis, showingStatus, movieRating, movieType);
    	
    	// serialize to file
		try {
			SerializationUtil.serialize(newMovie, "movies.ser");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Movie save unsuccessful!");
			return 0;
		}
		
		if(createMovieListing(newMovie) == 1) {
			return 1;
		}
		
		return 0;
    }
    
    /**
     * Creates a new movie listing for the new movie
     * @param movie
     * @return
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
     * @return
     */
    public static int deleteMovie() {
    	ArrayList<Object> movies = new ArrayList<>();
    	Movie movie = null;
    	int selection = 0;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Deleting movie:");
    	System.out.println("Available movies: ");
    	
    	try {
			movies = SerializationUtil.deserialize("movies.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	for(int i=0;i<movies.size();i++) {
    		movie = (Movie)movies.get(i);
    		System.out.println((i+1) + ". " + movie.getTitle());
    	}
    	
    	while(true) {
	    	System.out.println("Which movie do you want to delete? ");
	    	try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	}
	    	if(selection <= movies.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}
    	
    	movie = (Movie)movies.get(selection-1);
    	
    	// set the showingStatus to END_OF_SHOWING
    	movie.deleteMovie();
    	
    	// remove movie listing before deleting movie completely
    	deleteMovieListing(movie);
    	
    	// delete movie file to be overwritten
    	File dfile = new File("movies.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// save remaining movie objects to file
    	movies.remove(selection);
    	for(int i=0;i<movies.size();i++) {
    		// serialize to file
    		try {
    			SerializationUtil.serialize(movie, "movies.ser");
    		} catch (IOException e) {
    			e.printStackTrace();
    			System.out.println("Movie save unsuccessful!");
    			return 0;
    		}
    	}
    	
    	return 0;
    }
    
    /**
     * Deletes the respective movie listing
     * @param movie
     * @return
     */
    public static int deleteMovieListing(Movie movie) {
    	ArrayList<Object> mListings = new ArrayList<>();
    	ArrayList<Showtime> showtimes = new ArrayList<>();
    	ArrayList<Review> reviews = new ArrayList<>();
    	MovieListing mListing = null;
    	
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing) mListings.get(i);
    		if(mListing.getMovie().getTitle() == movie.getTitle()) {
    			// remove movie listing from ArrayList if it matches given movie
    			mListings.remove(i);
    			break;
    		}
    	}
    	
    	File dfile = new File("movieListings.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// serialize remaining movie listings to file
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		try {
    			SerializationUtil.serialize(mListing, "movieListings.ser");
    		} catch (IOException e) {
    			e.printStackTrace();
    			System.out.println("Movie listing update unsuccessful!");
    			return 0;
    		}
    	}
    	
    	return 1;
    }
    
    /**
     * Called to update the showing status of movies
     * @return
     */
    public static int updateMovieStatus() {
    	ArrayList<Object> movies = new ArrayList<>();
    	Movie movie = null;
    	int count, selection = 0;
    	ShowingStatus showingStatus;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Updating movie status: ");
    	System.out.println("Available movies: ");
    	
    	try {
			movies = SerializationUtil.deserialize("movies.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read movies.");
			return 0;
		}
    	
    	for(int i=0;i<movies.size();i++) {
    		movie = (Movie)movies.get(i);
    		System.out.println((i+1) + ". " + movie.getTitle() + ": " + movie.getStatus());
    	}
    	
    	while(true) {
    		System.out.println("Which movie do you want to update the status for? ");
    		try {
	    	    selection = Integer.parseInt(sc.nextLine());
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	    return 0;
	    	}
	    	if(selection <= movies.size() && selection > 0) {
	    		break;
	    	}
	    	System.out.println("Invalid option, try again.");
    	}
    	
    	movie = (Movie)movies.get(selection-1);
    	
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
    	movie.editStatus(showingStatus);
    	
    	File dfile = new File("movies.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// serialize updated movies to file
    	for(int i=0;i<movies.size();i++) {
    		movie = (Movie)movies.get(i);
    		try {
    			SerializationUtil.serialize(movie, "movies.ser");
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
     * @return
     */
    public static int addShowtime() {
    	ArrayList<Object> showtimes = new ArrayList<>();
    	ArrayList<Object> mListings = new ArrayList<>();
    	ArrayList<Object> cineplexesInfo = new ArrayList<>();
    	ArrayList<Cineplex> cineplexes = new ArrayList<>();
    	ArrayList<Cinema> cinemas = new ArrayList<>();
    	MovieListing mListing = null;
    	int selection = 0;
    	String showtimeId, cinemaCode, usrInput;
    	LocalDate date;
    	LocalTime start, end;
    	Showtime newShowtime;
    	
    	// dates will be formatted into YYYY-MM-DD format
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    	// time will be formatted into HH:MM format
    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Adding a new showtime: ");
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
			cineplexesInfo = SerializationUtil.deserialize("vendorCineplexesInfo.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read movie listings.");
			return 0;
		}
    	
    	System.out.println("Available cineplexes: ");
    	
    	// get list of cineplexes to display to user
    	cineplexes = cineplexesInfo.get(0).getCineplexes();
    	
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
    	
    	cinemaCode = cinemas.get(selection-1).getCinemaCode();
    	
    	System.out.println("Enter the showtime date YYYY/MM/DD (E.g. 2022/10/02): ");
    	usrInput = sc.next();
    	date = LocalDate.parse(usrInput, dateFormat);
    	
    	System.out.println("Enter the showtime start time HH:MM (E.g. 10:30): ");
    	usrInput = sc.next();
    	start = LocalTime.parse(usrInput, timeFormat);
    	
    	System.out.println("Enter the showtime end time HH:MM (E.g. 22:30): ");
    	usrInput = sc.next();
    	end = LocalTime.parse(usrInput, timeFormat);
    	
    	// set the showtimeId (1st word of movie title + showtime index in listing)
    	showtimeId = mListing.getMovie().getTitle().split("[ \\t\\n\\,\\?\\;\\.\\:\\!]")[0] + mListing.getShowtimes().size();
    	
    	// update the movie listing with the added showtime
    	newShowtime = new Showtime(showtimeId, date, start, end, cinemaCode);
    	
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
     * Called to allow staff to edit the showtimes of movies
     * @return
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
    	System.out.println("Editing showtimes: ");
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
    	System.out.println("3. End time");
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
        	showtime.editStart(start);
        	break;
    	case 3:
    		System.out.println("Enter the new end time HH:MM (E.g. 10:30): ");
        	usrInput = sc.next();
        	end = LocalTime.parse(usrInput, timeFormat);
        	showtime.editEnd(end);
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
     * Called to add holidays into the system
     * @return
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
    	
    	System.out.println("Adding holidays: ");
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
}
