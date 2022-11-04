package model;

public class AdminUser extends User {
	private static final long serialVersionUID = 167358L;

    public AdminUser(String username, String password) {
		super(username, password, ADMIN);
	}
}
