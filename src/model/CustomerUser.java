package model;

public class CustomerUser extends User {
	private String name;
	private String mobileNumber;
	// add array of bookings
	public String bookings[];

    // different from Admin, we need the name and mobile number to differentiate customers
	public CustomerUser(String username, String password, String name, String mobileNumber) {
		super(username, password, MOVIE_GOER);
		this.name = name;
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}

	public String toString(){
		String toReturn = "";
		toReturn 	+= "Username: " + getName() + "\n"
					+ "E-mail: " + getEmail() + "\n"
					+ "Mobile number: " + getMobileNumber();
		return toReturn; 
	}
}

