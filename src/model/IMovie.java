package model;

import src.model.Movie.ShowingStatus;

/**
 * @author d188878
 * Interface for the Movie class
 */
public interface IMovie {
	/**
	 * Allows staff to update the movie showing status
	 * @param nStatus
	 */
	public void editStatus(ShowingStatus nStatus);
	/**
	 * Allows staff to remove a movie
	 */
	public void deleteMovie();
}
