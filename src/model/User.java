package model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

// Add a controller for Accounts to User
// Conencted to Modules == Controllers
// 

public class User implements Serializable {
	private static final long serialVersionUID = 943875L;
	private String email;
	private String passwordHashed;
	private int role; 
    public static final int MOVIE_GOER = 1, ADMIN = 2;
    
	public User(String email, String password, int role) {
        this.email = email;
        this.passwordHashed = PasswordSHA256(password, email);
        this.role = role;
    }
    
	public boolean validatePassword(String passwordInput){
    	return this.passwordHashed.equals(PasswordSHA256(passwordInput, this.getEmail()));
    }
    
	public String getEmail() {
    	return this.email;
	}
	
    // public or private
	public String getPasswordHashed() {
		return this.passwordHashed;
	}
    
	public int getRole() {
    	return this.role;
	}
	
	public void updatePassword(String currentPassword, String newPassword) {
		if (this.validatePassword(currentPassword))
			this.passwordHashed = PasswordSHA256(newPassword, this.getEmail());		
	}
    	
	public String PasswordSHA256(String password, String salt){
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

    // should we show the hashed password?
	public String toString() {
		String toReturn = "";
		toReturn += "E-mail: " + getEmail() + "\n"
					+ "Role: " + getRole();
		return toReturn; 
	}
    
}