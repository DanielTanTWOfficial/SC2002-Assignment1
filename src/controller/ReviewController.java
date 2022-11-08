package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import model.Review;
import model.SerializationUtil;
import model.MovieListing;
import java.util.Scanner;



public class ReviewController{ //this is the reviewcontroller class

    
    public static void submitReview(){ //this method is to submit a review from the movie listings listed.
        Scanner sc = new Scanner(System.in);
        ArrayList<Object> mListings = new ArrayList<>();
    	MovieListing mListing = null;
        ArrayList<Object> reviewsArray = new ArrayList<>();
        Review review;
    	
    	try {
			mListings = SerializationUtil.deserialize("movieListings.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

        System.out.println("Select which movie do you want to review");
        for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
            System.out.println("Movie #" +(i+1) + ": " + mListing.getMovie().getTitle());
        }

        int movienumber = sc.nextInt() - 1; //edit here to minus the movienumber by 1 because print value increase by 1
        MovieListing movieselected = (MovieListing)mListings.get(movienumber); //the exact movie that is being entered the review

        
        //entering review details

        System.out.println("Please enter your name:");
        String name = InputController.getString();
        System.out.println("Please enter your review: ");
        String reviewdetails = InputController.getString();
        System.out.println("Please enter a review ID that is unique:");
        int reviewID = InputController.getPositiveInt();
        int reviewflag = 0;
        while(reviewflag != 1)
        {
            for(int i = 0; i < reviewsArray.size(); i++)
            {
                review = (Review)reviewsArray.get(i);
                if(review.getReviewID() == reviewID)
                {
                    System.out.println("Please re-enter another review ID: ");
                }
    
            }
            reviewflag = 1;
        }
        System.out.println("Please enter your rating for this movie: " + movieselected.getMovie().getTitle());
        double rating = InputController.getPositiveDouble();

        //creating the review based on the details being submitted 
        Review r1 = new Review(name, reviewdetails, reviewID, rating);
        movieselected.addReview(r1);

        // save new movie listings to file
    	File dfile = new File("movieListings.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// serialize updated movies to file
    	for(int i=0;i<mListings.size();i++) {
    		mListing = (MovieListing)mListings.get(i);
    		try {
    			SerializationUtil.serialize(mListing, "movieListings.ser");
    		} catch (IOException e) {
    			e.printStackTrace();
    			System.out.println("MovieListing update unsuccessful!");
    		}
    	}

        sc.close();
    }
    



}