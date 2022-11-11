package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Daniel
 * The Showtime class, which implements the Serializable interface
 */
public class Showtime implements Serializable {
	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = -3528685110408677269L;
	/**
	 * The showtimeId attribute stores the unique ID for the showtime
	 */
	private String showtimeId;
	/**
	 * The date attribute stores the date of the Showtime
	 */
	private LocalDate date;
	/**
	 * The start attribute stores the start time of the showtime
	 */
	private LocalTime start;
	/**
	 * The end attribute stores the start time of the showtime
	 */
	private LocalTime end;
	/**
	 * The location attribute stores the location of the Showtime (Cineplex)
	 */
	private String location;
	/**
	 * The cinemaCode stores the identifier of the cinema showtime is at
	 */
	private int cinemaCode;
	/**
	 * The cinemaBooking attribute stores the seatng chart associated with the cinema the showtime is for
	 */
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
	public void editDate(LocalDate date) {
		this.date = date;
		System.out.println("Updated showtime date to " + date.toString());
	}

	/**
	 * This method implements the code to allow cinema staff to edit the start time of showtimes
	 */
	public void editStart(LocalTime start) {
		this.start = start;
		System.out.println("Updated showtime start to " + start.toString());
	}

	/**
	 * This method implements the code to allow cinema staff to edit the end time of showtimes
	 */
	public void editEnd(LocalTime end) {
		this.end = end;
		System.out.println("Updated showtime end to " + end.toString());
	}
	
	/**
	 * This method implements the code to allow the booking system to update the seat availability
	 */
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
