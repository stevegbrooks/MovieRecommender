import java.util.HashMap;
import java.util.Map;

/**
 * Basic POJO to store User information from the 'ratings.dat' file.
 * @author sgb
 *
 */
public class User implements Comparable<User> {
	private int userID;
	private double meanRating;
	
	public User(int userID) {
		this.userID = userID;
		meanRating = 0;
	}

	/**
	 * @return the meanRating
	 */
	public double getMeanRating() {
		return meanRating;
	}

	/**
	 * @param meanRating the meanRating to set
	 */
	public void setMeanRating(double meanRating) {
		this.meanRating = meanRating;
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
