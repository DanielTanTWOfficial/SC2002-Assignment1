package model;

// Chain of Responsibility Design Pattern
// If this chain can't calculate the price, pass request to the next chain to handle

public interface ITicketHandler {
	
	public void setNext(ITicketHandler next);
	
	// returns error code
	public int calculateTicketPrice(Ticket ticket, Showtime showtime, Movie movie);

}
