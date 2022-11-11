package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import model.Review;
import model.SerializationUtil;
import model.MovieListing;

/**
 * @author zhiheng 
 * ReviewController class is to allow the user to interact the functions which are related to reviews in the main menu. 
 */

public class ReviewController{ 
    /**
     * This method is to allow the user to submit a review to the movie that the user selects. 
     */
    public static void submitReview(){ 

        ArrayList<Object> mListings = new ArrayList<>();
    	MovieListing mListing = null;
    	
        /**
         * the movielisting files need to be deserialized so that the file can be read by users
         */
    	try {
			mListings = SerializationUtil.deserialize("database/movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

        /**
         * user will be prompted to select which movie to review by inputting the corresponding number to the movie title
         */
        System.out.println("Select which movie do you want to review");
        for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
            System.out.println("Movie #" +(i+1) + ": " + mListing.getMovie().getTitle());
        }

        /**
         * select the movie which the user wants to review
         */
        int movienumber = InputController.getInt() - 1; 
        MovieListing movieselected = (MovieListing)mListings.get(movienumber); 

        /**
         * user will be prompted to select which movie to review by inputting the corresponding number to the movie title
         */
        //entering the details of the review to create the review
        System.out.println("Please enter your name:");
        String name = InputController.getString();
        System.out.println("Please enter your review: ");
        String reviewdetails = InputController.getString();

        System.out.println("Please enter your rating for this movie: " + movieselected.getMovie().getTitle());
        double rating = InputController.getPositiveDouble();

        /**
         * creating the review based on the details being submitted 
         */
        Review r1 = new Review(name, reviewdetails, rating);
        movieselected.addReview(r1);

        /**
         * save new movie listings with the new review to the file
         */
    	File dfile = new File("database/movieListings.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	

        /**
         * serialize the updated movies to file
         */
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		try {
    			SerializationUtil.serialize(mListing, "database/movieListings.ser");
    		} catch (IOException e) {
    			e.printStackTrace();
    			System.out.println("MovieListing update unsuccessful!");
    		}
    	}

    }

}