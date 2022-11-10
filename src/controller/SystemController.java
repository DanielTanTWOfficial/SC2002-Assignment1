package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import model.Holiday;
import model.SerializationUtil;

/**
 * @author Daniel
 * Controller class for admin system settings configuration like holidays and toggling of movie ranking filters available to users
 */
public class SystemController {
	public static ArrayList<Object> readHolidaysFile() {
		ArrayList<Object> holidays = new ArrayList<>();
        try {
        	holidays = SerializationUtil.deserialize("holidays.ser");
            return holidays;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
        return new ArrayList<Object>();
	}
    /**
     * Called to add holidays into the system
     * @return int
     */
    public static int addHolidays() {
    	ArrayList<Object> holidayObjects = new ArrayList<>();
    	ArrayList<LocalDate> holidays = new ArrayList<>();
    	Holiday holiday = null;
    	LocalDate holidayDate;
    	char selection;
    	boolean firstTime = true;
    	
    	// check if a Holiday object already exists to avoid duplicate creation
    	File f = new File("holidays.ser");
    	if(f.isFile()) {
    		firstTime = false;
    	}
    	
    	if(firstTime) {
    		holiday = new Holiday();
    	}
    	else {
    		try {
    			holidayObjects = SerializationUtil.deserialize("holidays.ser");
    		} catch (IOException | ClassNotFoundException e) {
    			e.printStackTrace();
    			System.out.println("Unable to read movie listings.");
    			return 0;
    		}
    		holiday = (Holiday)holidayObjects.get(0);
    	}
    	
    	holidays = holiday.getHolidays();
    	
    	// dates will be formatted into YYYY-MM-DD format
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("=============== HOLIDAY CREATION =============== ");
    	while(true) {
    		System.out.println("Enter the holiday date YYYY/MM/DD (E.g. 2022/10/03): ");
    		holidayDate = InputController.getDate();
    		holidays.add(holidayDate);
    		System.out.println("Add another date (Y/N)? ");
    		selection = sc.next().charAt(0);
    		if(selection == 'N') {
    			break;
    		}
    	}
    	
    	// re-serialise the updated holiday object
    	File dfile = new File("holidays.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	if(firstTime == false) {
	    	// serialize updated movies to file
	    	holiday = (Holiday)holidayObjects.get(0);
    	}
		try {
			SerializationUtil.serialize(holiday, "holidays.ser");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Holiday update unsuccessful!");
			return 0;
		}
    	
    	return 1;
    }

	
	/** 
	 * Called to remove a LocalDate from the Holiday object holidays ArrayList
	 * @return int
	 */
	public static int removeHoliday() {
		ArrayList<Object> holidayObjects = new ArrayList<>();
		ArrayList<LocalDate> holidays = new ArrayList<>();
		Holiday holiday = null;
		boolean firstTime = true;
		int selection = 0;

		Scanner sc = new Scanner(System.in);

		// check if a Holiday object already exists to avoid error
    	File f = new File("holidays.ser");
    	if(f.isFile()) {
    		firstTime = false;
    	}
		if(firstTime) {
			System.out.println("No holiday created yet!");
			return 0;
		}

		try {
			holidayObjects = SerializationUtil.deserialize("holidays.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read holidays.");
			return 0;
		}
		holiday = (Holiday)holidayObjects.get(0);
		holidays = holiday.getHolidays();

		System.out.println("=============== HOLIDAY REMOVAL =============== ");
		System.out.println("Current holidays: ");

		for(int i=0;i<holidays.size();i++) {
			System.out.println((i+1) + ". " + holidays.get(i));
		}

		System.out.println("Which holiday do you want to remove: ");
		selection = InputController.getIntRange(1, holidays.size());

		// remove selected LocalTime from the ArrayList
		holidays.remove(selection-1);

		// re-serialise the updated holiday object
    	File dfile = new File("holidays.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// serialize updated movies to file
    	holiday = (Holiday)holidayObjects.get(0);
		try {
			SerializationUtil.serialize(holiday, "holidays.ser");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Holiday update unsuccessful!");
			return 0;
		}
    	return 1;
	}

	/** 
	 * Allows admins to list all saved holidays
	 */
	public static void listHolidays() {
		ArrayList<Object> holidayObjects = new ArrayList<>();
		ArrayList<LocalDate> holidays = new ArrayList<>();
		Holiday holiday = null;
		boolean firstTime = true;

		// check if a Holiday object already exists to avoid error
    	File f = new File("holidays.ser");
    	if(f.isFile()) {
    		firstTime = false;
    	}
		if(firstTime) {
			System.out.println("No holiday created yet!");
			return;
		}

		try {
			holidayObjects = SerializationUtil.deserialize("holidays.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read holidays.");
			return;
		}
		holiday = (Holiday)holidayObjects.get(0);
		holidays = holiday.getHolidays();

		System.out.println("=============== HOLIDAYS =============== ");
		System.out.println("Current holidays: ");

		for(int i=0;i<holidays.size();i++) {
			System.out.println((i+1) + ". " + holidays.get(i));
		}
	}
	
	/** 
	 * Allows admins to set whether movie listings should be ranked by ticket sales/overall ratings/any
	 * @return int
	 */
	public static int configureFilter() {
		String filterVal = "";

		System.out.println("=============== RANKING CONTROL =============== ");
		System.out.println("Toggle the filters available to rank the movie listings: ");
		System.out.println("Available filters: ");
		System.out.println("1. Filter by ticket sales");
		System.out.println("2. Filter by overall rating");
		System.out.println("3. Filter by either ticket sales or overall rating");
		
		System.out.println("Enter the filter option to set: ");
		switch(InputController.getIntRange(1, 3)) {
		case 1:
			filterVal = "sales";
			break;
		case 2:
			filterVal = "ratings";
			break;
		case 3:
			filterVal = "any";
			break;
		default:
			System.out.println("Invalid option, try again.");
			break;
		}

		// write the filter value to a text file for reference
		try (PrintWriter out = new PrintWriter("filter.txt")) {
		    out.println(filterVal);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error saving filter to file!");
			return 0;
		}

		return 1;
	}
}
