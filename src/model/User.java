package model;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 943875L;
	private String email;
    
	public User(String email) {
        this.email = email;
    }
    
	public String getEmail() {
    	return this.email;
	} 

	public void updateEmail(String newEmail) {
		this.email = newEmail;
	}
}