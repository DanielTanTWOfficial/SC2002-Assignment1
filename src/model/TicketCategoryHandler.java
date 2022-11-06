package model;

import java.time.LocalTime;

import model.Movie.MovieType;

// Handles the price based on how much the ticket is based on date and time
public class TicketCategoryHandler implements ITicketHandler {

	private ITicketHandler next;
	
	@Override
	public void setNext(ITicketHandler next) {
		this.next = next;
	}

	// Adult Tickets
	// Category 1: Monday To Wednesday
	// Category 2: Thursday
	// Category 3: Friday before 6pm
	// Category 4: Friday after 6pm & Weekends
	@Override
	public int calculateTicketPrice(Ticket ticket, Showtime showtime, Movie movie) {
		
		int dayOfTheWeek = showtime.getDate().getDayOfWeek().getValue();
		LocalTime screeningTime = showtime.getStart();
		boolean is3D = false; // Need to be changed
		
		boolean MondayToWednesday = (dayOfTheWeek <= 3);
		boolean Thursday = (dayOfTheWeek == 4);
		boolean Friday = (dayOfTheWeek == 5);
		boolean Before6pm = screeningTime.isBefore(LocalTime.of(18, 0));
		
		// Category 1
		if (MondayToWednesday) {
			if (is3D)
				ticket.setPrice(11);
			else
				ticket.setPrice(8.50);
		}
		// Category 2
		else if (Thursday) {
			if (is3D)
				ticket.setPrice(11);
			else
				ticket.setPrice(9.50);
		}
		// Category 3
		else if (Friday && Before6pm) {
			if (is3D)
				ticket.setPrice(15);
			else
				ticket.setPrice(9.50);
		}
		// Category 4
		else {
			if (is3D)
				ticket.setPrice(15);
			else
				ticket.setPrice(11);
		}
		next = new TicketMovieTypeHandler();
		return next.calculateTicketPrice(ticket, showtime, movie);
	}

}
