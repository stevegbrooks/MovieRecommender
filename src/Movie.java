import java.util.HashMap;
import java.util.Map;

/**
 * @author sgb
 *
 */
public class Movie implements Comparable<Movie> {
	private int movieID;
	private String movieName;
	private Map<Integer, Double> userRatings;
	
	public Movie(int movieID, String movieName) {
		this.movieID = movieID;
		this.movieName = movieName;
		userRatings = new HashMap<>();
	}
	
	public Movie(int movieID) {
		this.movieID = movieID;
		this.movieName = new String();
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
		return ("Movie ID = " + movieID);
	}

	
}
