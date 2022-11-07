package model;

import model.Movie.MovieType;

// Handles Movie Type
public class TicketMovieTypeHandler implements ITicketHandler{

	private ITicketHandler next;
	@Override
	public void setNext(ITicketHandler next) {
		this.next = next;
	}

	// Adds $1 if movie is blockbuster :)
	// Returns 1 if successful
	@Override
	public int calculateTicketPrice(Ticket ticket, Showtime showtime, Movie movie) {
		
		if (movie.getMovieType() == MovieType.BLOCKBUSTER)
			ticket.setPrice(ticket.getPrice() + 1);
		return 1;
	}

}
