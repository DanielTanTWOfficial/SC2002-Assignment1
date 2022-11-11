package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Daniel
 * The Holiday object class which implements Serializable
 */
public class Holiday implements Serializable {
	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = 1553139173673584909L;
	/**
	 * ArrayList of holidays created by the admin
	 */
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
	 * @return ArrayList of holidays
	 */
	public ArrayList<LocalDate> getHolidays() {
		return holidays;
	}
	

}
