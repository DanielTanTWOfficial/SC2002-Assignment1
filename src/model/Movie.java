package model;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;

/**
 * @author d188878
 * The Movie class, which implements the IMovie interface and Serializable interface
 */
public class Movie implements Serializable {
	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = 3502145753890835117L;
	public enum ShowingStatus { COMING_SOON, PREVIEW, NOW_SHOWING, END_OF_SHOWING };
	public enum MovieRating { PG, NC16, M18, R21 };
	public enum MovieType { BLOCKBUSTER, DIGITAL, IMAX };
	private String title;
	private String director;
	private ArrayList<String> cast;
	private String synopsis;
	private Duration duration;
	private ShowingStatus status;
	private MovieRating movieRating;
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
