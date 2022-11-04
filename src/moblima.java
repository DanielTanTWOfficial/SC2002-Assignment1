package src;

import src.controller.InputController;

public class moblima {
    public void main() {
        initialiseInfra();

        boolean exit = false;
		while (!exit) {
            System.out.println("=============== MOBLIMA =============== ");
            System.out.println("1. Admin");
            System.out.println("2. Movie-goer");
            System.out.println("3. Exit");
            System.out.println("======================================= ");
			System.out.print("Select action: ");
		    switch(InputController.getInt()) {
                case 1:
                    adminModule();
                    break;
                case 2:
                    movieGoerModule();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting MOBLIMA");
                    break;
                default:
                    System.out.println("Let's try that again...");
			}
			
		}
    }

    public void initialiseInfra() {
        // Create Cineplexes and Cinemas
        // NOTE: at least 3 Cineplexes
    }

    // Login (must login before you can access the rest of the features)
    // 1. Create/Update/Remove movie listing
    // 2. Create/Update/Remove cinema showtimes and the movies to be shown
    // 3. Configure system settings
        // Set Holidays
        // Register new admin accounts
    public void adminModule() {

    }

    // No need to login
    // 1. Search/List movie
    // 2. View movie details – including reviews and ratings
    // 3. Check seat availability and selection of seat/s.
    // 4. Book and purchase ticket
    // 5. View booking history
    // 6. List the Top 5 ranking by ticket sales OR by overall reviewers’ ratings
    public void movieGoerModule() {

    }


}
