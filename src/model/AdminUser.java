package MOBLIMA.src.model;

public class AdminUser extends User {
    public AdminUser(String username, String password) {
		super(username, password, ADMIN);
	}
}
