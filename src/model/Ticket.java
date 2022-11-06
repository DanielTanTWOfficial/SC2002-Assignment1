package model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import model.Movie.MovieType;

public class Ticket {
	
	private String ticketId;
	private String showtimeId;
	private int CinemaCode;
	private String movieTitle;
	private TicketType ticketType;
	private double price;
	private String seatNo;
	
	public enum TicketType { ADULT, CHILD, SENIOR };
	
	public Ticket() {
		
	}
	
	public Ticket(String ticketId, String showtimeId, int CinemaCode, String movieTitle, TicketType ticketType, double price, String seatNo) {
		this.ticketId = ticketId;
		this.showtimeId = showtimeId;
		this.CinemaCode = CinemaCode;
		this.movieTitle = movieTitle;
		this.ticketType = ticketType;
		this.price = price;
		this.seatNo = seatNo;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getShowtimeId() {
		return showtimeId;
	}

	public void setShowtimeId(String showtimeId) {
		this.showtimeId = showtimeId;
	}

	public int getCinemaCode() {
		return CinemaCode;
	}

	public void setCinemaCode(int cinemaCode) {
		CinemaCode = cinemaCode;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}
	
	// Adult Ticket
	// Mon-Wed (Category 1)
	// Thurs-Fri6pm (Category 2)
	// Fri6pm-Sun (Category 3)
	
}
