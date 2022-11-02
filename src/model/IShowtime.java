package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author d188878
 * Interface for the Showtime class
 */
public interface IShowtime {
	/**
	 * Allows staff to update the showing date
	 * @param date
	 */
	public void editDate(LocalDate date);
	/**
	 * Allows staff to update the start time of screening
	 * @param start
	 */
	public void editStart(LocalTime start);
	/**
	 * Allows staff to update the end time of screening
	 * @param end
	 */
	public void editEnd(LocalTime end);
	/**
	 * Allows staff to update the cinema code
	 * @param code
	 */
	public void editCinema(String code);
	/**
	 * Allows booking to update the seat status
	 * @param seat
	 */
	public void updateSeatStatus(int row, int col);
	/**
	 * Prints showtime info
	 */
	public void printShowtime();
}
