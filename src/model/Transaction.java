package model;

import java.io.Serializable;

public class Transaction implements Serializable{
	
	private static final long serialVersionUID = -4901981362604757722L;
	private String transactionId;
	private String bookingId;
	private double tranAmount;
	
	public Transaction(String transactionId, String bookingId, double tranAmount) {
		this.transactionId = transactionId;
		this.bookingId = bookingId;
		this.tranAmount = tranAmount;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public double getTranAmount() {
		return tranAmount;
	}
	public void setTranAmount(double tranAmount) {
		this.tranAmount = tranAmount;
	}
}
