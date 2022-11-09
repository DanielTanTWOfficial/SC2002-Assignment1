package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author d188878
 * The MovieListing class, which implements the IMovieListing interface and Serializable interface
 */
public class MovieListing implements Serializable, Comparable<MovieListing> {
	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = 896429368504325337L;
	private Movie movie;
	private double overallRating;
	private double ticketSales;
	private ArrayList<Review> reviews;
	private ArrayList<Showtime> showtimes;
	private boolean bySales;
	
	/**
	 * Default constructor
	 */
	public MovieListing() {}
	
	/**
	 * Constructor to create a new MovieListing object
	 * @param movie
	 */
	public MovieListing(Movie movie) {
		this.movie = movie; // each movie listing is tied to 1 movie
		this.overallRating = 0.0;
		this.ticketSales = 0.0;
		this.bySales = true;
		this.reviews = new ArrayList<>();
		this.showtimes = new ArrayList<>();
	}
	
	/**
	 * Prints a pretty display of movie info
	 * @param withReviews is true when displaying a single movie listing to include given reviews
	 */
	public void printInfo(boolean withReviews) {
		System.out.println("Movie: " + movie.getTitle());
		System.out.println("Director: " + movie.getDirector());
		System.out.print("Cast: ");
		for(int i=0;i<movie.getCast().size();i++) {
			System.out.print(movie.getCast().get(i) + " ");
		}
		System.out.println("\nDuration: " + movie.getDuration().toMinutes() + " mins");
		System.out.println("Content Rating: " + movie.getMovieRating());
		System.out.println("Movie Type: " + movie.getMovieType());
		System.out.println("Showing Status: " + movie.getStatus());
		System.out.println("Ticket Sales: " + this.ticketSales);
		if(this.reviews.size() > 1) {
			System.out.println("Overall Rating: " + String.format("%.1f", this.overallRating));
		}
		else {
			System.out.println("Overall Rating: NA");
		}
		if(withReviews) {
			System.out.println("\nSynopsis: \n");
			System.out.println(movie.getSynopsis());
			System.out.println("Reviewer Ratings:");
			// print reviews
			for(int i=0;i<reviews.size();i++) {
				reviews.get(i).printReview();
			}
		}
	}

	/**
	 * Prints information shown when booking
	 */
	public void printSimpleInfo() {
		System.out.println(movie.getTitle() + " " + movie.getMovieRating() + " " + movie.getMovieType());
	}

	/**
	 * Allows staff to add new reviews
	 */
	public void addReview(Review review) {
		this.reviews.add(review);
		// call updateOverallRating() only if there is more than 1 individual rating
		if(this.reviews.size() > 1) {
			updateOverallRating();
		}
	}

	/**
	 * Allows staff to add new showtimes
	 */
	public void addShowtime(Showtime showtime) {
		this.showtimes.add(showtime);
	}
	
	/**
	 * Implements the comparator logic, used to sort the MovieListing objects by either ticket sales
	 * or overall ratings
	 * @param listing
	 */
	public int compareTo(MovieListing listing) {
		if(this.bySales) {
			if(this.ticketSales > listing.getTicketSales()) {
				return 1;
			}
			else if(this.ticketSales < listing.getTicketSales()) {
				return -1;
			}
			else {
				return 0;
			}
		}
		// comparing by overall ratings
		else {
			if(this.overallRating > listing.getOverallRating()) {
				return 1;
			}
			else if(this.overallRating < listing.getOverallRating()) {
				return -1;
			}
			else {
				return 0;
			}
		}
	}

	/**
	 * @return movie
	 */
	public Movie getMovie() {
		return movie;
	}

	/**
	 * @return overallRating
	 */
	public double getOverallRating() {
		return overallRating;
	}

	/**
	 * @return getTicketSales
	 */
	public double getTicketSales() {
		return ticketSales;
	}

	/**
	 * @param ticketSales
	 */
	public void setTicketSales(double ticketSales) {
		this.ticketSales += ticketSales;
	}

	/**
	 * @return reviews
	 */
	public ArrayList<Review> getReviews() {
		return reviews;
	}

	/**
	 * @return showtimes
	 */
	public ArrayList<Showtime> getShowtimes() {
		return showtimes;
	}
	
	
	/**
	 * @param bySales
	 */
	public void setBySales(boolean bySales) {
		this.bySales = bySales;
	}

	/**
	 * Called by the addReview() method to calculate the overall rating whenever a new review is added
	 * and the number of reviews is 2 or more
	 */
	private void updateOverallRating() {
		double sum = 0;
		// sum up total ratings
		for(int i=0;i<this.reviews.size();i++) {
			sum += this.reviews.get(i).getRating();
		}
		// divide total ratings by no. of reviews
		this.overallRating = sum / this.reviews.size();
	}

}
