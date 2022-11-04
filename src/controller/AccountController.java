package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import controller.InputController;

import model.SerializationUtil;
import model.User;
import model.AdminUser;
import model.CustomerUser;


public class AccountController {
    
    // admins can create and delete its own and other admin accounts
    
    public static ArrayList<Object> readAdminAccountsFile() {
        ArrayList<Object> adminAccounts = new ArrayList<>();
        try {
			adminAccounts = SerializationUtil.deserialize("adminAccounts.ser");
            return adminAccounts;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
        return new ArrayList<Object>();
    }

    // we only allow one admin account per email for security reasons
    public static boolean isAdminAccount(String email) {
        ArrayList<Object> adminAccounts = readAdminAccountsFile();
        for (int i = 0; i < adminAccounts.size(); i++) {
            AdminUser verifiedUser = (AdminUser) adminAccounts.get(i);
            if (verifiedUser.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static boolean verify(String email, String password) {
        ArrayList<Object> adminAccounts = readAdminAccountsFile();
        for (int i = 0; i < adminAccounts.size(); i++) {
            AdminUser verifiedUser = (AdminUser) adminAccounts.get(i);
            if (verifiedUser.getEmail().equals(email) && verifiedUser.validatePassword(password)) {
                return true;
            }
        }
        return false;
    }

    // only for admin to login
    // allow the user to exit if they quit trying to login
    // optional features:
        // allow only 5 tries
    public static boolean login() {
        System.out.println("=== Logging In ===");

        boolean result = false;
        boolean exit = false;

        do {
            System.out.println("Please enter your email.");
            String email = InputController.getEmail();
            System.out.println("Please enter your password.");
            String password = InputController.getString();
            result = verify(email, password);

            if (!result) {
                System.out.println("Wrong email or password.");
                System.out.println("1. Enter again");
                System.out.println("2. Exit");
                if (InputController.getIntRange(1, 2) == 2) {
                    exit = true;
                }
            }
        } while (!result && !exit);

        if (result) {
            System.out.println("You have logged in successully.");
            return true;
        }
        return false;
    }

    public static void createAdminAccount() {
        System.out.println("=== Creating new admin account ===");

        System.out.println("Please enter your new account's email.");
        String email = InputController.getEmail();
        System.out.println("Please enter your new account's password.");
        String password = InputController.getString();
        
        if (isAdminAccount(email)) {
            System.out.println("Admin account already exist!");
        }
        else {
            AdminUser newUser = new AdminUser(email, password);
            try {
                SerializationUtil.serialize(newUser, "adminAccounts.ser");
                System.out.println("New admin account registered!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Admin account registering unsuccessful!");
            }
        }  
    }

    public static void deleteAdminAccount() {
        System.out.println("=== Deleting admin account ===");

        System.out.println("Please enter the email of admin account to delete.");
        String email = InputController.getEmail();

        if (!isAdminAccount(email)) {
            System.out.println("Admin account does not exist!");
        }
        else {
            ArrayList<Object> adminAccounts = readAdminAccountsFile();
            for(int i = 0; i < adminAccounts.size(); i++) {
                AdminUser verifiedUser = (AdminUser) adminAccounts.get(i);
                if (verifiedUser.getEmail().equals(email)) {
                    System.out.println("Admin account " + email + " successfully deleted.");
                    adminAccounts.remove(i);
                    break;
                }
            }
        }  
    }

    // public static void changeEmail() {
    //     System.out.println("=== Changing account password ===");

    //     do {
    //         System.out.println("Please enter your current email.");
    //         String oldEmail = InputController.getEmail();
    //         System.out.println("Please enter your new email.");
    //         String newEmail = InputController.getEmail();

            
    //     }
        
    // }

    // public static void changePassword() {
    //     System.out.println("=== Changing account password ===");

    //     System.out.println("Please enter your email.");
    //     String email = InputController.getEmail();
    //     System.out.println("Please enter your password.");
    //     String password = InputController.getString();

    //     if (verify(email, password)) {
    //         ArrayList<Object> adminAccounts = readAdminAccountsFile();
    //     }

        

    // }
}
