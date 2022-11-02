package src.controller;

public class CustomerController {
    /**
     * Displays the top 5 movie listings by ticket sales or reviewer ratings
     * @param bySales
     */
    public static void displayTopMovieListings(boolean bySales) {
    	ArrayList<Object> mListings = new ArrayList<>();
    	ArrayList<MovieListing> castedListings = new ArrayList<>();
    	MovieListing mListing = null;
    	
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
    		mListing.printInfo();
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
    		mListing.printInfo();
    	}
    }
}
