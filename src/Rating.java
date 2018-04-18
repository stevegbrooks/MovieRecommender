
public class Rating implements Comparable<Rating> {
	private int userID;
	private int movieID;
	private double rating;
	
	public Rating(int userID, int movieID, double rating) {
		this.userID = userID;
		this.movieID = movieID;
		this.rating = rating;
	}
	
	public Rating(double rating) {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + movieID;
		long temp;
		temp = Double.doubleToLongBits(rating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + userID;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rating other = (Rating) obj;
		if (movieID != other.movieID)
			return false;
		if (Double.doubleToLongBits(rating) != Double.doubleToLongBits(other.rating))
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}

	@Override
	public int compareTo(Rating otherRating) {
		if (this.userID != otherRating.getUserID()) {
			return this.userID - otherRating.getUserID();
		} else {
			if (this.movieID != otherRating.getUserID()) {
				return this.movieID - otherRating.getMovieID();
			} else {
				double ratingDiff = this.rating - otherRating.getRating();
				if (ratingDiff < 0) {
					return -1;
				} else if (ratingDiff > 0) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return ("Rating = " + rating);
	}
	
}
