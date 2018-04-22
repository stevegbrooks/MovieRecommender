import java.util.HashMap;
import java.util.Map;

/**
 * Basic POJO to store User information from the 'ratings.dat' file.
 * @author sgb
 *
 */
public class User implements Comparable<User> {
	private int userID;
	private double avgRating;
	private Map<Movie, Double> movieRatings;
	
	public User(int userID) {
		this.userID = userID;
		this.avgRating = 0;
		movieRatings = new HashMap<>();
	}

	/**
	 * @return the avgRating
	 */
	public double getAvgRating() {
		return avgRating;
	}

	/**
	 * @param avgRating the avgRating to set
	 */
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	/**
	 * @return the movieRatings
	 */
	public Map<Movie, Double> getMovieRatings() {
		return movieRatings;
	}

	/**
	 * @param movieRatings the movieRatings to set
	 */
	public void setMovieRatings(Map<Movie, Double> movieRatings) {
		this.movieRatings = movieRatings;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	public boolean equals(User user) {
		return this.userID == user.getUserID();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		User other = (User) obj;
		if (userID != other.userID)
			return false;
		return true;
	}

	@Override
	public int compareTo(User otherUser) {
		return this.userID - otherUser.userID;
	}

	@Override
	public String toString() {
		return "User ID = " + userID;
	}
	
	
}
