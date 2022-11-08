package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
public class AdminUser extends User {
	private static final long serialVersionUID = 167358L;
	private String passwordHashed;

    public AdminUser(String email, String password) {
		super(email);
		this.passwordHashed = passwordSHA512(password, email);
	}

	public String getPasswordHashed() {
		return this.passwordHashed;
	}

	public boolean validatePassword(String passwordInput){
    	return this.passwordHashed.equals(passwordSHA512(passwordInput, this.getEmail()));
    }

	public void updatePassword(String currentPassword, String newPassword) {
		if (this.validatePassword(currentPassword))
			this.passwordHashed = passwordSHA512(newPassword, this.getEmail());		
	}
    	
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

	public String toString(){
		String toReturn = "";
		toReturn 	+= "E-mail: " + getEmail();
		return toReturn; 
	}
}
