import java.util.Map;

/**
 * Basic POJO to store User information from the 'ratings.dat' file.
 * @author sgb
 *
 */
public class User {
	private int userID;
	private Map<Movie, Integer> ratings;
	
	public User(int userID, Map<Movie, Integer> ratings) {
		this.userID = userID;
		this.ratings = ratings;
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
	public Map<Movie, Integer> getRatings() {
		return ratings;
	}
	
}
