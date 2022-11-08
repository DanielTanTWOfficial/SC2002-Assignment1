package test;

import java.io.File;

import controller.*;

public class AccountControllerTest {
    public static void main(String[] args) {
        new File("adminAccounts.ser").delete();

        System.out.println("...Testing AccountController...");

        System.out.println("...Testing AccountController.createAdminAccount...");
        AdminController.createAdminAccount();
        AdminController.createAdminAccount();

        System.out.println("...Testing AccountController.readAdminAccountsFileAndPrint...");
        AdminController.readAdminAccountsFileAndPrint();

        System.out.println("...Testing AccountController.login...");
        AdminController.login();

        System.out.println("...Testing AccountController.deleteAdminAccount...");
        AdminController.deleteAdminAccount();
        AdminController.readAdminAccountsFileAndPrint();

        System.out.println("...Testing AccountController.changePassword...");
        AdminController.changePassword();
        AdminController.readAdminAccountsFileAndPrint();
    }
}
