package model;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;

/**
 * @author Daniel
 * The Movie class, which implements the Serializable interface
 */
public class Movie implements Serializable {
	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = 3502145753890835117L;
	/**
	 * The ShowingStatus enum defines the available showing statuses of the movies
	 */
	public enum ShowingStatus { COMING_SOON, PREVIEW, NOW_SHOWING, END_OF_SHOWING };
	/**
	 * The MovieRating enum defines the available age ratings of the movies
	 */
	public enum MovieRating { PG, NC16, M18, R21 };
	/**
	 * The MovieType enum defines the available types of the movies
	 */
	public enum MovieType { BLOCKBUSTER, INDIE, IMAX };
	/**
	 * The title attribute stores the movie title
	 */
	private String title;
	/**
	 * The director attribute stores the director of the movie
	 */
	private String director;
	/**
	 * The cast attribute is an ArrayList of the cast member's names
	 */
	private ArrayList<String> cast;
	/**
	 * The synopsis attribute stores the movie synopsis
	 */
	private String synopsis;
	/**
	 * The duration attribute stores the duration in minutes of the movie
	 */
	private Duration duration;
	/**
	 * The status attribute stores the showing staus of the movie
	 */
	private ShowingStatus status;
	/**
	 * The movieRating attribute stores the age rating of the movie
	 */
	private MovieRating movieRating;
	/**
	 * The movieType attribute stores the movie type
	 */
	private MovieType movieType;
	
	/**
	 * Default constructor
	 */
	public Movie() {}
	
	/**
	 * Constructor to create a new Movie object
	 * @param title
	 * @param director
	 * @param cast
	 * @param synopsis
	 * @param status
	 * @param movieRating
	 * @param movieType
	 */
	public Movie(String title, String director, ArrayList<String> cast, String synopsis, Duration duration, ShowingStatus status, MovieRating movieRating, MovieType movieType) {
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.synopsis = synopsis;
		this.duration = duration;
		this.status = status;
		this.movieRating = movieRating;
		this.movieType = movieType;
		System.out.println("Movie " + title + " created!");
	}

	/**
	 * @param nStatus
	 * This method implements the code to allow cinema staff to edit the status of movies
	 */
	public void editStatus(ShowingStatus nStatus) {
		this.status = nStatus;
		System.out.println("Updated the showing status to " + status);
	}

	/**
	 * This method implements the code to indicate that a movie is to be removed by updating 
	 * the status to END_OF_SHOWING
	 */
	public void deleteMovie() {
		this.status = ShowingStatus.END_OF_SHOWING;
		System.out.println("Marked movie as removed.");
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * @return cast
	 */
	public ArrayList<String> getCast() {
		return cast;
	}

	/**
	 * @return synopsis
	 */
	public String getSynopsis() {
		return synopsis;
	}

	/**
	 * @return duration
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * @return status
	 */
	public ShowingStatus getStatus() {
		return status;
	}

	/**
	 * @return movieRating
	 */
	public MovieRating getMovieRating() {
		return movieRating;
	}

	/**
	 * @return movieType
	 */
	public MovieType getMovieType() {
		return movieType;
	}

}
