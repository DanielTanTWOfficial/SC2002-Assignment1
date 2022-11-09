package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Booking implements Serializable{

	private static final long serialVersionUID = 2962605114036267141L;
	private String bookingId;
	private LocalDate date;
	private double totalCost;
	private ArrayList<Ticket> tickets; // Either store ticket or ticketid
	
	public Booking(String bookingId, LocalDate date) {
		this.bookingId = bookingId;
		this.date = date;
		totalCost = 0;
		tickets = new ArrayList<Ticket>();
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getTotalCost() {
		totalCost = 0;
		for (int i = 0; i < tickets.size(); i++) {
			totalCost += tickets.get(i).getPrice(); 
		}
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}
}
