package model;

import java.io.Serializable;

public class Review implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -705589914269932166L;
	private String reviewerName;
	private String review;
	private double rating;
	
	public Review(String reviewerName , String review , double rating)
	{
		this.reviewerName = reviewerName;
		this.review = review;
		this.rating = rating;
	}
	
	public String getReview()
	{
		return review;
	}
	
	public void setReview(String review)
	{
		this.review = review;
	}
	
	public String getReviewerName()
	{
		return reviewerName;
	}
	
	public void setReviewerName(String reviewerName)
	{
		this.reviewerName = reviewerName;
	}
	
	public double getRating()
	{
		return rating;
	}
	
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
	
	public void printReview() {
		System.out.println("Reviewed by: " + this.reviewerName);
		System.out.println("Review: \n" + this.review);
		System.out.println("Rating: " + this.rating);
	}

}
