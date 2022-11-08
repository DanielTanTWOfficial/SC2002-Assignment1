package model;

public class CustomerUser extends User {
	private static final long serialVersionUID = 437816L;
	private String mobileNumber;
	// add array of bookings
	// public String bookings[];

    // different from Admin, we need the name and mobile number to differentiate customers
	public CustomerUser(String email, String mobileNumber) {
		super(email);
		this.mobileNumber = mobileNumber;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}

	public String toString(){
		String toReturn = "";
		toReturn 	+= "E-mail: " + getEmail() + "\n"
					+ "Mobile number: " + getMobileNumber();
		return toReturn; 
	}
}

