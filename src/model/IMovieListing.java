package model;

/**
 * @author d188878
 * Interface for the MovieListing class
 */
public interface IMovieListing {
	/**
	 * Prints a pretty display of movie info
	 */
	public void printInfo();
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
	/**
	 * Allows for comparison of movies based on ticket sales/overall ratings
	 * @param listing
	 * @param bySales
	 * @return
	 */
	//public int compareTo(MovieListing listing, boolean bySales);
}
