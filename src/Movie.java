import java.util.HashMap;
import java.util.Map;

/**
 * @author sgb
 *
 */
public class Movie implements Comparable<Movie> {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + movieID;
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
		Movie other = (Movie) obj;
		if (movieID != other.movieID)
			return false;
		return true;
	}

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

	
}
