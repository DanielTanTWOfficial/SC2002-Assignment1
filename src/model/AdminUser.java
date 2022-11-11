package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author Daniel
 * The AdminUser subclass extends the User class with more attributes and methods for password
 */
public class AdminUser extends User {
	/**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
	private static final long serialVersionUID = 167358L;

	/**
	 * The passwordHashed attribute stores the hashed password using SHA-512
	 * We do not need to store the password in plaintext, plus it adds to security risks
	 */
	private String passwordHashed;

	/**
	 * Constructor to create an AdminUser object
	 * @param email
	 * @param password
	 */
    public AdminUser(String email, String password) {
		super(email);
		this.passwordHashed = passwordSHA512(password, email);
	}

	/**
	 * @return passwordHashed
	 */
	public String getPasswordHashed() {
		return this.passwordHashed;
	}

	/**
	 * Compare current passwordHashed with a SHA512 hashed password inputted
	 * @param passwordInput
	 * @return true if password is correct based on SHA512
	 */
	public boolean validatePassword(String passwordInput){
    	return this.passwordHashed.equals(passwordSHA512(passwordInput, this.getEmail()));
    }

	/**
	 * Change passwordHashed with a new SHA-512 hashed password
	 * Must verify before changing
	 * @param currentPassword
	 * @param newPassword
	 */
	public void updatePassword(String currentPassword, String newPassword) {
		if (this.validatePassword(currentPassword))
			this.passwordHashed = passwordSHA512(newPassword, this.getEmail());		
	}
    
	/**
	 * SHA-512 algorithm
	 * @param password
	 * @param salt
	 * @return hashed password
	 */
	public String passwordSHA512(String password, String salt){
		String hashedPassword = null;
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        md.update(salt.getBytes(StandardCharsets.UTF_8));
	        byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        hashedPassword = sb.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return hashedPassword;
	}

	/**
	 * @return string of email of adminUser
	 */
	public String toString(){
		String toReturn = "";
		toReturn 	+= "E-mail: " + getEmail();
		return toReturn; 
	}
}
