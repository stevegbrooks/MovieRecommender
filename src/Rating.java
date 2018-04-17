
public class Rating {
	private int userID;
	private int movieID;
	private double rating;
	
	public Rating(int userID, int movieID, double rating) {
		this.userID = userID;
		this.movieID = movieID;
		this.rating = rating;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @return the movieID
	 */
	public int getMovieID() {
		return movieID;
	}

	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}
	
	
}
