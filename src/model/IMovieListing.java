package model;

/**
 * @author d188878
 * Interface for the MovieListing class
 */
public interface IMovieListing {
	/**
	 * Prints a pretty display of movie info
	 * @param withReviews is true when displaying a single movie listing to include given reviews
	 */
	public void printInfo(boolean withReviews);
	/**
	 * Allows new viewer reviews to be added
	 * @param review
	 */
	public void addReview(Review review);
	/**
	 * Allows staff to add new showtimes to the movie listing
	 * @param showtime
	 */
	public void addShowtime(Showtime showtime);
}
