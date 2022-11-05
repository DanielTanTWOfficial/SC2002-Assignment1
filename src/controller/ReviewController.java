package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import model.Review;
import model.SerializationUtil;
import model.MovieListing;
import java.util.Scanner;


public class ReviewController{

    
    public static void submitReview(Review review){ //this method is to submit a review from the movie listings listed.
        Scanner sc = new Scanner(System.in);
        ArrayList<Object> mListings = new ArrayList<>();
    	MovieListing mListing = null;
        ArrayList<Object> reviewsArray = new ArrayList<>();
    	
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

        //checking the reviews file
        try {
			reviewsArray = SerializationUtil.deserialize("reviews.ser");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

        System.out.println("Please enter your name:");
        String name = sc.next();
        System.out.println("Please enter your review: ");
        String reviewdetails = sc.next();
        System.out.println("Please enter a review ID that is unique:");
        int reviewID = sc.nextInt();
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
        double rating = sc.nextDouble();

        //creating the review based on the details being submitted 
        Review r1 = new Review(name, reviewdetails, reviewID, rating);
        movieselected.addReview(r1);

        sc.close();
    }

    public static void addReview(ArrayList<Review> reviewsArray, Review review){ //put reviews into reviews array
            reviewsArray.add(review);
            try {
                SerializationUtil.serialize(reviewsArray,"reviews.ser");
                System.out.println("Review # " + reviewsArray.get(reviewsArray.size()) + " added!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Review adding unsuccessful!");
            }
        }
    

    public static ArrayList<Object> readReviewsFile() { //read the reviews file and deseralize it
        ArrayList<Object> reviews = new ArrayList<>();
        try {
			reviews = SerializationUtil.deserialize("reviews.ser");
            return reviews;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
        return new ArrayList<Object>();
    }

}