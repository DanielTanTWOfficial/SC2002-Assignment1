package controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import model.MovieListing;
import model.SerializationUtil;

public class CustomerController {
    /**
     * Displays the top 5 movie listings by ticket sales or reviewer ratings
     * @param bySales
     */
    public static void displayTopMovieListings() {
    	ArrayList<Object> mListings = new ArrayList<>();
    	ArrayList<MovieListing> castedListings = new ArrayList<>();
    	MovieListing mListing = null;
		String filterVal = "";
		int selection = 0;
		boolean bySales = true;

		Scanner sc = new Scanner(System.in);

		// read set filter value from file
		Path path = Paths.get("filter.txt");
		try {
			filterVal = Files.readString(path, StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// if filter value is set by admin, user cannot choose, otherwise they can choose
		if(filterVal == "ratings") {
			bySales = false;
		}
		else if(filterVal == "any") {
			while(true) {
				System.out.println("Do you want to filter by ticket sales (1) or overall rating (2)? ");
				try {
					selection = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				if(selection <= 2 && selection > 0) {
					break;
				}
				System.out.println("Invalid option, try again.");
			}
			if(selection == 2) {
				bySales = false;
			}
		}
    	
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		if(!bySales) {
    			mListing.setBySales(false);
    		}
    		castedListings.add(mListing);
    	}
    	
    	// sort the castedListings ArrayList to get the sorted movie listings
    	Collections.sort(castedListings);
    	
    	// display the top 5 movie listings by sales/ratings
    	for(int i=0;i<5;i++) {
    		System.out.print((i+1) + ". ");
    		mListing = castedListings.get(i);
    		mListing.printInfo(false);
    	}
    }
    
    /**
     * Called to print all movie listing information.
     */
    public static void displayAllMovieListings() {
    	ArrayList<Object> mListings = new ArrayList<>();
    	MovieListing mListing = null;
    	
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		mListing.printInfo(false);
    	}
    }

	/**
     * Called to print details of a specific movie listing
     */
	public static void displaySpecificListing() {
		ArrayList<Object> mListings = new ArrayList<>();
    	MovieListing mListing = null;
		int selection = 0;

		Scanner sc = new Scanner(System.in);

		try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		System.out.println((1+1) + ". " + mListing.getMovie().getTitle());
    	}

		while(true) {
			System.out.println("Which movie do you want to view details of? ");
			try {
				selection = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			if(selection <= mListings.size() && selection > 0) {
				break;
			}
			System.out.println("Invalid option, try again.");
		}

		mListing = (MovieListing)mListings.get(selection-1);

		mListing.printInfo(true);

	}
}
