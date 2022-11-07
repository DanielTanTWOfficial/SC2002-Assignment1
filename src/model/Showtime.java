package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import model.CinemaBooking;

/**
 * @author d188878
 * The Showtime class, which implements the IShowtime interface and Serializable interface
 */
public class Showtime implements IShowtime, Serializable {
	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = -3528685110408677269L;
	private String showtimeId;
	private LocalDate date;
	private LocalTime start;
	private LocalTime end;
	private String location;
	private int cinemaCode;
	private CinemaBooking cinemaBooking;
	
	/**
	 * Default constructor
	 */
	public Showtime() {}
	
	/**
	 * Constructor to create a new Showtime object
	 * @param showtimeId
	 * @param date
	 * @param start
	 * @param end
	 * @param cinema
	 * @param location
	 */
	public Showtime(String showtimeId, LocalDate date, LocalTime start, LocalTime end, Cinema cinema, String location) {
		this.showtimeId = showtimeId;
		this.date = date;
		this.start = start;
		this.end = end;
		this.cinemaCode = cinema.getCinemaCode();
		this.cinemaBooking = new CinemaBooking(cinema);
		this.location = location;
		System.out.println("Showtime created!");
	}
	
	/**
	 * This method implements the code to allow cinema staff to edit the date of showtimes
	 * @param date
	 */
	@Override
	public void editDate(LocalDate date) {
		this.date = date;
		System.out.println("Updated showtime date to " + date.toString());
	}

	/**
	 * This method implements the code to allow cinema staff to edit the start time of showtimes
	 */
	@Override
	public void editStart(LocalTime start) {
		this.start = start;
		System.out.println("Updated showtime start to " + start.toString());
	}

	/**
	 * This method implements the code to allow cinema staff to edit the end time of showtimes
	 */
	@Override
	public void editEnd(LocalTime end) {
		this.end = end;
		System.out.println("Updated showtime end to " + end.toString());
	}
	
	/**
	 * This method implements the code to allow the booking system to update the seat availability
	 */
	@Override
	public void updateSeatStatus(int row, int col) {
		cinemaBooking.assignSeat(row, col);
	}
	
	/**
	 * This method prints the showtime info
	 */
	public void printShowtime() {
		System.out.println("Showtime ID: " + this.showtimeId);
		System.out.println("Date: " + this.date);
		System.out.println("Start: " + this.start);
		System.out.println("End: " + this.end);
		System.out.println("Cineplex location: " + this.location);
		System.out.println("Cinema Code: " + this.cinemaCode);
	}
	
	/**
	 * @return showtimeId
	 */
	public String getShowtimeId() {
		return showtimeId;
	}

	/**
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @return start
	 */
	public LocalTime getStart() {
		return start;
	}

	/**
	 * @return end
	 */
	public LocalTime getEnd() {
		return end;
	}

	/**
	 * @return cinemaCode
	 */
	public int getCinemaCode() {
		return cinemaCode;
	}
	
	/**
	 * @return cinemaBooking
	 */
	public CinemaBooking getCinemaBooking() {
		return cinemaBooking;
	}

	/**
	 * @return location
	 */
	public String getLocation() {
		return location;
	}
}
