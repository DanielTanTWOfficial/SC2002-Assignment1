package model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import model.Movie.MovieType;
/**
 * 
 * @author WeiJie
 * The Ticket class, which implements the Serializable interface
 */
public class Ticket implements Serializable{
	
	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = 4187781382629687412L;
	
	/**
	 * Id of the ticket object in format TXXXRXCX
	 * Where X = CinemaCode, row, col
	 */
	private String ticketId;
	
	/**
	 * date of the screening
	 */
	private LocalDate date;
	
	/**
	 * time of the screening
	 */
	private LocalTime startTime;
	
	/**
	 * CinemaCode of the Cinema
	 */
	private int CinemaCode;
	
	/**
	 * Title of the movie
	 */
	private String movieTitle;
	
	/**
	 * The type of ticket (ADULT, SENIOR, CHILD)
	 */
	private TicketType ticketType;
	
	/**
	 * Individual price of the ticket
	 */
	private double price;
	
	/**
	 * SeatNo as a String RXCX
	 * where X = row, col
	 */
	private String seatNo;
	
	public enum TicketType { ADULT, CHILD, SENIOR };
	
	/**
	 * Constructor to create a new Ticket object
	 * @param ticketId
	 * @param date
	 * @param startTime
	 * @param CinemaCode
	 * @param movieTitle
	 * @param ticketType
	 * @param seatNo
	 */
	public Ticket(String ticketId, LocalDate date, LocalTime startTime, int CinemaCode, String movieTitle, TicketType ticketType, String seatNo) {
		this.ticketId = ticketId;
		this.setDate(date);
		this.startTime = startTime;
		this.CinemaCode = CinemaCode;
		this.movieTitle = movieTitle;
		this.ticketType = ticketType;
		this.price = 0;
		this.seatNo = seatNo;
	}

	/**
	 * @return ticketId
	 */
	public String getTicketId() {
		return ticketId;
	}

	/**
	 * This method implements the code to set ticketId
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	
	/**
	 * @return startTime
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 * This method implements the code to set startTime
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return CinemaCode
	 */
	public int getCinemaCode() {
		return CinemaCode;
	}

	/**
	 * This method implements the code to set cinemaCode
	 */
	public void setCinemaCode(int cinemaCode) {
		CinemaCode = cinemaCode;
	}

	/**
	 * @return movieTitle
	 */
	public String getMovieTitle() {
		return movieTitle;
	}

	/**
	 * This method implements the code to set movieTitle
	 */
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	/**
	 * @return price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * This method implements the code to set price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return seatNo
	 */
	public String getSeatNo() {
		return seatNo;
	}

	/**
	 * This method implements the code to set SeatNo
	 */
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	/**
	 * @return ticketType
	 */
	public TicketType getTicketType() {
		return ticketType;
	}

	/**
	 * This method implements the code to set the ticketType
	 * @param ticketType
	 */
	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}
	
	/**
	 * This method implements the code to print all the details of the ticket
	 */
	public void printTicket() {
		System.out.printf("TicketId: %s, Movie: %s\n", ticketId, movieTitle);
		System.out.printf("Date: %s\n", date);
		System.out.printf("Showtime: %s\n", startTime);
		System.out.printf("Cinema: %d\n", CinemaCode);
		System.out.printf("Ticket Type: %s\n", ticketType);
		System.out.printf("Seat No: %s\n", seatNo);
		System.out.printf("Price: %.2f\n", price);
	}

	/**
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * This method allows the booking system to set the date
	 * @param date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
}
