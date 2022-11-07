package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.SerializationUtil;
import model.Ticket;

// Handles Tickets & Bookings
public class BookingController {
	
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
	
	public static void saveTicketsFile(Ticket ticket) {
		
		try {
			SerializationUtil.serialize(ticket, "tickets.ser");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	
}
