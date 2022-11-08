package model;

import java.time.LocalTime;

import model.Movie.MovieRating;
import model.Ticket.TicketType;

// Base chain
// This chain calculates which type of ticket it belongs to (Adult, Senior, Child)
public class TicketTypeHandler implements ITicketHandler {

	private ITicketHandler next;
	
	public TicketTypeHandler() {
		
	}
	
	@Override
	public void setNext(ITicketHandler next) {
		this.next = next;
	}

	// Checks include
	// 1. Public Holiday
	// 2. Mon-Fri bef 6pm
	// 3. Purchasing Child ticket for a movie M18 || R21
	@Override
	public int calculateTicketPrice(Ticket ticket, Showtime showtime, Movie movie) {
		
		Holiday holiday = new Holiday();
		boolean isHoliday = false;
		boolean is3D = false;
		
		// Check if date of showtime is a holiday
		for (int i = 0; i < holiday.getHolidays().size(); i++) {
			if (holiday.getHolidays().get(i) == showtime.getDate())
				isHoliday = true;
		}
		
		if (ticket.getTicketType() == TicketType.ADULT) {
			next = new TicketCategoryHandler();
			return next.calculateTicketPrice(ticket, showtime, movie);
		}
		else {
			// Check if is public holiday
			if (isHoliday) {
				System.out.println("Not available on public holidays");
				return -1;
			}
			// Check if purchasing Child ticket for a movie M18 || R21
			else if (ticket.getTicketType() == TicketType.CHILD && (movie.getMovieRating() == MovieRating.M18 || movie.getMovieRating() == MovieRating.R21)) {
				System.out.println("Cannot purchase child ticket for M18 and R21 movies");
				return -1;
			}
			// Check Mon-Fri before 6pm
			if (showtime.getDate().getDayOfWeek().getValue() <= 5 && showtime.getStart().isBefore(LocalTime.of(18, 0))) {
				switch (ticket.getTicketType()) {
					case CHILD:
						if (is3D)
							ticket.setPrice(9);
						else
							ticket.setPrice(7);
						next = new TicketMovieTypeHandler();
						return next.calculateTicketPrice(ticket, showtime, movie);
					case SENIOR:
						if (is3D) {
							System.out.println("No 3D for Senior ticket");
							return -1;
						}
						else
							ticket.setPrice(4);
						next = new TicketMovieTypeHandler();
						return next.calculateTicketPrice(ticket, showtime, movie);
					default:
						break;
				}
				
				return 1;
			}
			// Change to Adult Ticket (Automatically change to adult ticket)
			else {
				ticket.setTicketType(TicketType.ADULT);
				next = new TicketCategoryHandler();
				return next.calculateTicketPrice(ticket, showtime, movie);
			}	
		}
	}
	
	

}
