
public class RatingTuple implements Comparable<RatingTuple> {
	private User user;
	private Movie movie;
	private double rating;
	
	public RatingTuple(User user, Movie movie, double rating) {
		this.user = user;
		this.movie = movie;
		this.rating = rating;
	}
	
	public RatingTuple(double rating) {
		this.rating = rating;
	}

	/**
	 * @return the userID
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the movieID
	 */
	public Movie getMovie() {
		return movie;
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
		result = prime * result + movie.getMovieID();
		long temp;
		temp = Double.doubleToLongBits(rating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + user.getUserID();
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
		RatingTuple other = (RatingTuple) obj;
		if (movie.getMovieID() != other.movie.getMovieID())
			return false;
		if (Double.doubleToLongBits(rating) != Double.doubleToLongBits(other.rating))
			return false;
		if (user.getUserID() != other.user.getUserID())
			return false;
		return true;
	}

	@Override
	public int compareTo(RatingTuple otherRating) {
		if (this.user.getUserID() != otherRating.user.getUserID()) {
			return this.user.getUserID() - otherRating.user.getUserID();
		} else {
			if (this.movie.getMovieID() != otherRating.user.getUserID()) {
				return this.movie.getMovieID() - otherRating.movie.getMovieID();
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
}
