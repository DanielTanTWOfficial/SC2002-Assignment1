package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Holiday {
	private ArrayList<LocalDate> holidays;

	/**
	 * Default constructor
	 */
	public Holiday() {
		this.holidays = new ArrayList<>();
	}
	
	/**
	 * Method to add a new holiday
	 * @param hol
	 */
	public void addHoliday(LocalDate hol) {
		this.holidays.add(hol);
	}

	/**
	 * @return holidays
	 */
	public ArrayList<LocalDate> getHolidays() {
		return holidays;
	}
	

}
