package model;

import java.io.Serializable;

/**
 * @author zhiheng
 * The Review class, which implements the Serializable interface 
 */

public class Review implements Serializable {
	/**
	 * Automatically generates a serialVersionUID which is used to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that are 
	 * compatible with respect to serialization during deserialization. 
	 */
	private static final long serialVersionUID = -705589914269932166L;
	/**
	 * The reviewerName stores the person who submits the review for a particular movie listing.
	 */
	private String reviewerName;
	/**
	 * The review stores the review written for a particular movie listing. 
	 */
	private String review;
	/**
	 * The rating stores the given rating from 1 to 5 for the movie listing after the reviewer watch the movie listing. 
	 */
	private double rating;

	/**
	 * Default constructor
	 */
	public Review(){}

	/**
	 * Constructor to create a new Review.
	 * @param reviewerName
	 * @param review
	 * @param rating
	 */
	
	public Review(String reviewerName , String review , double rating)
	{
		this.reviewerName = reviewerName;
		this.review = review;
		this.rating = rating;
	}
	
	
	/** 
	 * This method is used to get the review of the movie
	 * @return String
	 */
	public String getReview()
	{
		return review;
	}
	
	
	/** 
	 * This method is used to change the review of the movie. 
	 * @param review
	 */
	public void setReview(String review)
	{
		this.review = review;
	}
	
	
	/** 
	 * This method is used to get the Reviewer Name of the Review.
	 * @return String
	 */
	public String getReviewerName()
	{
		return reviewerName;
	}
	
	
	/** 
	 * This method is used to change the Reviewer Name of the Review. 
	 * @param reviewerName
	 */
	public void setReviewerName(String reviewerName)
	{
		this.reviewerName = reviewerName;
	}
	
	
	/** 
	 * This method is used to get the rating of the movie from the Review object.
	 * @return double
	 */
	public double getRating()
	{
		return rating;
	}
	
	
	/** 
	 * This method is used to change the movie rating and it is from a range from 1-5. 
	 * @param rating
	 */
	public void setRating(Double rating)
	{
		int correct = 0;
		while(correct != 1)
		{
			if(rating >= 1 && rating <= 5)
			{
				this.rating = rating;
				correct = 1;
			} else 
			{
			  System.out.println("Please type a rating from 1 - 5");
			}
		}

	}
	
	/**
	 * Prints information about the review being submitted for a movie
	 */
	public void printReview() {
		System.out.println("Reviewed by: " + this.reviewerName);
		System.out.println("Review: \n" + this.review);
		System.out.println("Rating: " + this.rating);
	}

}
