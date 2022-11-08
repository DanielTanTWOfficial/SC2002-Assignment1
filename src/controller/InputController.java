package controller;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputController {
    private static Scanner sc = new Scanner(System.in);

    // For menu option
    public static int getInt() {
        int input = 0;
        boolean valid = false;
        while(!valid) {
            if (sc.hasNextInt()) {
                input = sc.nextInt();
                valid = true;
            }
            else {
                System.out.println("That's not a number!");
            }
            sc.nextLine();
        }
        return input;
    }

    public static int getPositiveInt() {
        int input = 0;
        boolean valid = false;
        while(!valid) {
            if (sc.hasNextInt()) {
                input = sc.nextInt();
                if (input >= 0) {
                    valid = true;
                }
                else {
                    System.out.println("That's not a positive number!");
                }
            }
            else {
                System.out.println("That's not a number!");
            }
            sc.nextLine();
        }
        return input;
    }

    // For Dates
    public static int getIntRange(int lowerRange, int upperRange) {
        int input = 0;
        boolean valid = false;
        while(!valid) {
            if (sc.hasNextInt()) {
                input = sc.nextInt();
                if (input >= lowerRange && input <= upperRange) {
                    valid = true;
                }
                else {
                    System.out.println("Number out of range!");
                }
            }
            else {
                System.out.println("That's not a number!");
            }
            sc.nextLine();
        }
        return input;
    }

    // For prices
    public static double getPositiveDouble() {
        double input = 0.0;
        boolean valid = false;
        while(!valid) {
            if(sc.hasNextDouble()) {
                input = sc.nextDouble();
                if (input >= 0) {
                    valid = true;
                }
                else {
                    System.out.println("That's not a positive decimal number!");
                }
            }
            else {
                System.out.println("That's not a decimal number!");
            }
            sc.nextLine();
        }
        return input;
    }

    // For passwords
    public static String getString() {
        String input = "";
        while(input.equals("")) {
            input += sc.nextLine();
            if(input.equals("")) {
                System.out.println("That's an empty string!");
            }
        }
        return input;
    }

    public static String getEmail() {
        // there are many restrictions to choose from, this is OWASP Validation Regular Expression
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        String input = "";
        boolean valid = false;
        while(!valid){
            input = sc.nextLine();
            if(input.matches(emailRegex)) {
                valid = true;
            }
            else {
                System.out.println("Must be valid email address");
            }
        }
        return input;
    }

    public static String getMobileNumber() {
        // Singapore mobile numbers have 8 digits that start with 8 or 9
        String numberRegex = "^[0-9]{8}$";
        String input = "";
        boolean valid = false;
        while(!valid) {
            input = sc.nextLine();
            if(input.matches(numberRegex) && ((input.startsWith("8") || input.startsWith("9")))) {
                valid = true;
            }
            else{
                System.out.println("Must be valid Singapore mobile number (8 digits long, starts with either 8 or 9)");
            }
        }
        return input;
    }
    
    public static boolean getBoolean() {
        String input = "";
        boolean output = false;
        boolean valid = false;
        while (!valid){
            input = sc.next();
            if(input.equals("y") || input.equals("Y")) {
                valid = true;
                output = true;
            }
            else if (input.equals("n") || input.equals("N")) {
                valid = true;
            }
            else {
                System.out.println("Must be either y or n");
            }
        }
        return output;
    }
    
    public static LocalDate getDate() {
        LocalDate result = null;
        String date;
        boolean valid = false;
        while(!valid) {
            try {
                date = sc.nextLine();
                result = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                valid = true;
            }
            catch(DateTimeParseException e){
                System.out.println("Must be of pattern YYYY/MM/DD!");
            }
        }
        return result;
    }

}
