package model;

/**
 * @author Daniel
 * The CustomerUser subclass extends the User class with more attributes and methods for mobilenumber
 */

public class CustomerUser extends User {
	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = 437816L;

	/**
	 * The mobileNumber attribute stores the mobile number of the user in plaintext
	 */
	private String mobileNumber;

	/**
	 * Constructor for CustomerUser
	 * @param email
	 * @param mobileNumber
	 */
	public CustomerUser(String email, String mobileNumber) {
		super(email);
		this.mobileNumber = mobileNumber;
	}
	
	/**
	 * @return moblileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @return email and mobileNumber
	 */
	public String toString(){
		String toReturn = "";
		toReturn 	+= "E-mail: " + getEmail() + "\n"
					+ "Mobile number: " + getMobileNumber();
		return toReturn; 
	}
}

