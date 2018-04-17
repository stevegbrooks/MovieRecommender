import java.util.HashMap;
import java.util.Map;

public class Movie {
	private int movieID;
	private String movieName;
	private Map<User, Integer> userRatings;
	
	public Movie(int movieID, String movieName) {
		this.movieID = movieID;
		this.movieName = movieName;
		userRatings = new HashMap<>();
	}

	/**
	 * @return the movieID
	 */
	public int getMovieID() {
		return movieID;
	}

	/**
	 * @return the movieName
	 */
	public String getMovieName() {
		return movieName;
	}

	/**
	 * @return the userRatings
	 */
	public Map<User, Integer> getUserRatings() {
		return userRatings;
	}
	
}
