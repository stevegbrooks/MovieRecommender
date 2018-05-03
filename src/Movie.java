import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * POJO to represent a Movie
 * @author sgb
 */
public class Movie implements Observer, Comparable<Movie>{
	private int movieID;
	private String movieName;
	private Map<User, Double> userRatings;
	
	public Movie(int movieID) {
		this.movieID = movieID;
		this.movieName = null;
		userRatings = new HashMap<>();
	}
	
	/**
	 * @return the userRatings
	 */
	public Map<User, Double> getUserRatings() {
		return userRatings;
	}

	/**
	 * @param userRatings the userRatings to set
	 */
	public void setUserRatings(Map<User, Double> userRatings) {
		this.userRatings = userRatings;
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
	 * @param movieName the movieName to set
	 */
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	/**
	 * Hashcode function for adding to Set<Movie>
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + movieID;
		return result;
	}

	/**
	 * Custom equals function for determining whether one Movie
	 * object is the same as another - based on movieID#.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (movieID != other.movieID)
			return false;
		return true;
	}
	
	/**
	 * Sorts ascending by movieID#
	 */
	@Override
	public int compareTo(Movie otherMovie) {
		return this.movieID - otherMovie.movieID;
	}
	
	@Override
	public String toString() {
		if (movieName != null) {
			return (String) movieName;
		} else {
			return ("MovieID: " + movieID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		Map<Movie, String> names = (Map<Movie, String>) arg;
		movieName = names.get(this);
	}
}
