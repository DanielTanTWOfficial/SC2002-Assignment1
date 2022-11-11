package controller;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;

import model.SerializationUtil;
import model.Movie;
import model.MovieListing;
import model.Movie.MovieRating;
import model.Movie.MovieType;
import model.Movie.ShowingStatus;

/**
 * @author Daniel
 * Controller class for actions related to movie listings such as creating, removing and editing movie listings
 */
public class MovieListingController {
    public static ArrayList<Object> readMovieListingsFile() {
		ArrayList<Object> movieListings = new ArrayList<>();
		try {
			movieListings = SerializationUtil.deserialize("database/movieListings.ser");
			return movieListings;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return movieListings;
	}

	/**
     * Gets user selected movie listing and returns the index in the ArrayList of MovieListing objects for easy reference
	 * @return int
     */
	public static int getMovieListingIndex() {
		MovieListing mListing = null;
		ArrayList<Object> mListings = readMovieListingsFile();
		int selection = 0;

		for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		System.out.println((i+1) + ". " + mListing.getMovie().getTitle());
    	}
    	
		System.out.println("Which movie do you want? ");
		selection = InputController.getIntRange(1, mListings.size());
    	
    	return selection-1;

	}

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
    	title = InputController.getString();
    	System.out.println("Enter the director: ");
    	director = InputController.getString();
    	System.out.println("Enter the synopsis: ");
    	synopsis = InputController.getString();
		System.out.println("Enter the duration in minutes: ");
		duration = Duration.ofMinutes(sc.nextLong());
    	
    	while(true) {
	    	System.out.println("Enter the number of cast members (min. 2): ");
	    	numCast = InputController.getPositiveInt();
	    	if(numCast >= 2) {
	    		break;
	    	}
	    	System.out.println("Too few cast members!");
    	}
    	for(int i=0;i<numCast;i++) {
    		System.out.println("Enter name of cast member " + (i+1) + ": ");
    		cast.add(InputController.getString());
    	}
    	
    	System.out.println("Available showing status options: ");
    	count = 1;
    	for(ShowingStatus status : ShowingStatus.values()) {
			if(status != ShowingStatus.END_OF_SHOWING) {
				System.out.println(count + ". " + status);
				count++;
			}
    	}
    	System.out.println("Select the showing status: ");
    	
    	selection = InputController.getIntRange(1, ShowingStatus.values().length-1);

		showingStatus = ShowingStatus.values()[selection-1];
    	
    	System.out.println("Available movie rating: ");
    	count = 1;
    	for(MovieRating rating : MovieRating.values()) {
    		System.out.println(count + ". " + rating);
    		count++;
    	}
    	System.out.println("Select the movie rating: ");
    	selection = InputController.getIntRange(1, MovieRating.values().length);
    	
		movieRating = MovieRating.values()[selection-1];
    	
    	System.out.println("Available movie type: ");
    	count = 1;
    	for(MovieType type : MovieType.values()) {
    		System.out.println(count + ". " + type);
    		count++;
    	}
    	System.out.println("Select the movie type: ");
    	
    	selection = InputController.getIntRange(1, MovieType.values().length);

		movieType = MovieType.values()[selection-1];
    	
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
			SerializationUtil.serialize(newMovieListing, "database/movieListings.ser");
			System.out.println("Movie listing created successfully!");
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
    	
    	System.out.println("=============== MOVIE DELETION =============== ");
    	System.out.println("Available movies: ");
    	
    	try {
			mListings = SerializationUtil.deserialize("database/movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		System.out.println((i+1) + ". " + mListing.getMovie().getTitle());
    	}
    	
		System.out.println("Which movie do you want to delete? ");
		selection = InputController.getIntRange(1, mListings.size());
    	
    	mListing = (MovieListing)mListings.get(selection-1);
    	
    	// set the showingStatus to END_OF_SHOWING
    	mListing.getMovie().deleteMovie();
    	
    	// delete movie file to be overwritten
    	File dfile = new File("database/movieListings.ser");
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
    			SerializationUtil.serialize(mListing, "database/movieListings.ser");
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
    	
    	System.out.println("=============== MOVIE STATUS UPDATE =============== ");
    	System.out.println("Available movies: ");
    	
    	try {
			mListings = SerializationUtil.deserialize("database/movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read movie listings.");
			return 0;
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		System.out.println((i+1) + ". " + mListing.getMovie().getTitle() + ": " + mListing.getMovie().getStatus());
    	}
    	
		System.out.println("Which movie do you want to update the status for? ");
		selection = InputController.getIntRange(1, mListings.size());
    	
    	mListing = (MovieListing)mListings.get(selection-1);
    	
    	System.out.println("Available status options: ");
    	count = 1;
    	for(ShowingStatus status : ShowingStatus.values()) {
    		if(status != ShowingStatus.END_OF_SHOWING) {
	    		System.out.println(count + ". " + status);
	    		count++;
    		}
    	}
    	System.out.println("Select the showing status: ");
    	selection = InputController.getIntRange(1, ShowingStatus.values().length-1);
    	
		showingStatus = ShowingStatus.values()[selection-1];
    	
    	// call to edit movie showing status
    	mListing.getMovie().editStatus(showingStatus);
    	
    	File dfile = new File("database/movieListings.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// serialize updated movies to file
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		try {
    			SerializationUtil.serialize(mListing, "database/movieListings.ser");
    		} catch (IOException e) {
    			e.printStackTrace();
    			System.out.println("Movie update unsuccessful!");
    			return 0;
    		}
    	}
    	
    	return 1;
    }
}