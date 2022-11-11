package model;

import java.io.Serializable;
/**
 * 
 * @author WeiJie
 * The Transaction class, which implements the Serializable interface
 */
public class Transaction implements Serializable{

	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = 1672173522020889192L;
	
	/**
	 * The id of the transaction in format XXXyyyyMMddHHmm
	 * XXX = cinemaCode
	 */
	private String transactionId;
	
	/**
	 * Stores the Booking object related to the transaction
	 */
	private Booking booking;
	
	/**
	 * Total transaction amount
	 */
	private double tranAmount;
	
	/**
	 * Name of the moviegoer
	 */
	private String name;
	
	/**
	 * Email address of the moviegoer
	 */
	private String emailAddress;
	
	/**
	 * Mobile number of the moviegoer
	 */
	private String mobileNo;
	
	/**
	 * Constructor to create a new Transaction object
	 * @param transactionId
	 * @param booking
	 * @param name
	 * @param emailAddress
	 * @param mobileNo
	 */
	public Transaction(String transactionId, Booking booking, String name, String emailAddress, String mobileNo) {
		this.transactionId = transactionId;
		this.booking = booking;
		this.tranAmount = booking.getTotalCost();
		this.setName(name);
		this.emailAddress = emailAddress;
		this.mobileNo = mobileNo;
	}
	
	/**
	 * @return transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}
	
	/**
	 * This method implements the code to set transactionId
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	/**
	 * @return tranAmount
	 */
	public double getTranAmount() {
		return tranAmount;
	}
	
	/**
	 * This method implements the code to set tranAmount
	 * @param tranAmount
	 */
	public void setTranAmount(double tranAmount) {
		this.tranAmount = tranAmount;
	}
	
	/**
	 * @return emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * This method implements the code to set email address
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * This method implements the code to set mobileNo
	 * @param mobileNo
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return booking
	 */
	public Booking getBooking() {
		return booking;
	}

	/**
	 * 
	 * @param booking
	 */
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	/**
	 * This method implements the code to print all details of the transaction
	 */
	public void printTransaction() {
		System.out.printf("TransactionId: %s\n", transactionId);
		System.out.printf("BookingId: %s\n", booking.getBookingId());
		System.out.printf("Name: %s\n", name);
		System.out.printf("Email: %s\n", emailAddress);
		System.out.printf("MobileNo: %s\n", mobileNo);
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method implements the code to set name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
