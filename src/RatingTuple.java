/**
 * A RatingTuple represents the data that comes from a movie ratings file.
 * The RatingTuple object itself is created by the RatingTupleReader, and it 
 * consists of a User, a Movie, and the rating that that User gave to that Movie.
 * @author sgb
 *
 */
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
	
	/**
	 * This compareTo method compares first in terms of UserID#, then
	 * MovieID#, and then finally on the rating that the user gave to the movie.
	 * 
	 * That is to say, one RatingTuple is equal to another if and only if the UserID,
	 * the MovieID, and the rating are all the same.
	 */
	@Override
	public int compareTo(RatingTuple otherRating) {
		if (!this.user.equals(otherRating.user)) {
			return this.user.compareTo(otherRating.user);
		} else {
			if (this.movie.equals(otherRating.movie)) {
				return this.movie.compareTo(otherRating.movie);
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
