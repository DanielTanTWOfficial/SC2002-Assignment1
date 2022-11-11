package model;

import java.io.Serializable;

/**
 * @author Daniel
 * The User superclass stores basic information of all users using MOBLIMA
 * Implements Serialiazable to store account information in ser files
 */

public class User implements Serializable {
	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = 943875L;
	
	/**
	 * The email attribute stores the email of the user in plaintext
	 */
	private String email;

    /**
	 * Constructor to create a new User object
	 * @param email
	 */
	public User(String email) {
        this.email = email;
    }
    
	/**
	 * @return email
	 */
	public String getEmail() {
    	return this.email;
	} 

	/**
	 * Changes email to the one passed in
	 * @param newEmail
	 */
	public void updateEmail(String newEmail) {
		this.email = newEmail;
	}
}