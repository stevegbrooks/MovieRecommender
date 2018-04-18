import java.util.HashMap;
import java.util.Map;

/**
 * Basic POJO to store User information from the 'ratings.dat' file.
 * @author sgb
 *
 */
public class User {
	private int userID;
	private Map<Integer, Double> movieRatings;
	
	public User(int userID) {
		this.userID = userID;
		movieRatings = new HashMap<>();
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @return the ratings
	 */
	public Map<Integer, Double> getMovieRatings() {
		return movieRatings;
	}

	/**
	 * @param movieRatings the movieRatings to set
	 */
	public void setMovieRatings(Map<Integer, Double> movieRatings) {
		this.movieRatings = movieRatings;
	}
}
