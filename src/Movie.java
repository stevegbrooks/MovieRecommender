import java.util.HashMap;
import java.util.Map;

/**
 * @author sgb
 *
 */
public class Movie {
	private int movieID;
	private String movieName;
	private Map<Integer, Double> userRatings;
	
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
	 * @param userRatings the userRatings to set
	 */
	public void setUserRatings(Map<Integer, Double> userRatings) {
		this.userRatings = userRatings;
	}

	/**
	 * @return the userRatings
	 */
	public Map<Integer, Double> getUserRatings() {
		return userRatings;
	}
	
}
