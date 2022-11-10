package model;

import java.io.Serializable;
/**
 * 
 * @author WeiJie
 * The Transaction class, which implements the Serializable interface
 */
public class Transaction implements Serializable{

	private static final long serialVersionUID = 1672173522020889192L;
	private String transactionId;
	private Booking booking;
	private double tranAmount;
	private String emailAddress;
	private String mobileNo;
	
	public Transaction(String transactionId, Booking booking, String emailAddress, String mobileNo) {
		this.transactionId = transactionId;
		this.booking = booking;
		this.tranAmount = booking.getTotalCost();
		this.emailAddress = emailAddress;
		this.mobileNo = mobileNo;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public double getTranAmount() {
		return tranAmount;
	}
	public void setTranAmount(double tranAmount) {
		this.tranAmount = tranAmount;
	}
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	public void printTransaction() {
		System.out.printf("TransactionId: %s\n", transactionId);
		System.out.printf("BookingId: %s\n", booking.getBookingId());
		System.out.printf("Email: %s\n", emailAddress);
		System.out.printf("MobileNo: %s\n", mobileNo);
	}
}
