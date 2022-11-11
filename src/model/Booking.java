package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * 
 * @author WeiJie
 * The Booking class, which implements the Serializable interface
 */
public class Booking implements Serializable{

	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = 2962605114036267141L;
	
	/**
	 * Id of the Booking object in format ByyyyMMddHHmm
	 * where yyyyMMdd = date of booking, HHmm = time of booking
	 */
	private String bookingId;
	
	/**
	 * Date when the booking was made
	 */
	private LocalDate date;
	
	/**
	 * Total cost of all the tickets in the booking
	 */
	private double totalCost;
	
	/**
	 * ArrayList of ticket objects
	 */
	private ArrayList<Ticket> tickets; // Either store ticket or ticketid
	
	/**
	 * Constructor to create a new Booking object
	 * @param bookingId
	 * @param date
	 */
	public Booking(String bookingId, LocalDate date) {
		this.bookingId = bookingId;
		this.date = date;
		totalCost = 0;
		tickets = new ArrayList<Ticket>();
	}
	/**
	 * 
	 * @return bookingId
	 */
	public String getBookingId() {
		return bookingId;
	}
	/**
	 * This method implements the code to allow the booking system to set bookingId
	 */
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	/**
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * This method implements the code to allow the booking system to set booking date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * This method implements the code to return the total cost of all tickets in the arrayList
	 */
	public double getTotalCost() {
		totalCost = 0;
		for (int i = 0; i < tickets.size(); i++) {
			totalCost += tickets.get(i).getPrice(); 
		}
		return totalCost;
	}

	/**
	 * This method implements the code to allow the booking system to set totalCost
	 */
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * @return tickets
	 */
	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	/**
	 * This method implements the code to allow the booking system to set tickets arrayList
	 */
	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}
}
