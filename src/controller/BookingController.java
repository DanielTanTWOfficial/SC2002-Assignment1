package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.SerializationUtil;
import model.Ticket;

// Handles Tickets & Bookings
public class BookingController {
	
	// TicketArray will be in MainController
	
	public static ArrayList<Object> readTicketsFile() {
		ArrayList<Object> ticketList = new ArrayList<>();
        try {
        	ticketList = SerializationUtil.deserialize("tickets.ser");
            return ticketList;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
        return new ArrayList<Object>();
	}
	
	public static void saveTicketsFile(ArrayList<Ticket> ticketsList) {
		File f = new File("tickets.ser");
		if(f.exists())
			f.delete();
		
		for (int i = 0; i < ticketsList.size(); i++) {
			try {
				SerializationUtil.serialize(ticketsList.get(i), "tickets.ser");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}
	
	
	
	
}
