/**
 * POJO to hold a User object and a pearson correlation coefficient. They will be
 * used to create a sortable list of neighbors relative to a User.
 * @author sgb
 *
 */
public class Neighbor implements Comparable<Neighbor>{
	private User user;
	private double coeff;
	
	public Neighbor(User user, double coeff) {
		this.user = user;
		this.coeff = coeff;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the coeff
	 */
	public double getCoeff() {
		return coeff;
	}

	/**
	 * @param coeff the coeff to set
	 */
	public void setCoeff(double coeff) {
		this.coeff = coeff;
	}
	/**
	 * Descending order compareTo method
	 */
	@Override
	public int compareTo(Neighbor o) {
		double thisCoeff = Math.round(this.coeff * 10000)/10000;
		double otherCoeff = Math.round(o.getCoeff() * 10000)/10000;
		
		if (thisCoeff - otherCoeff < 0) {
			return 1;
		} else if (thisCoeff - otherCoeff > 0) {
			return -1;
		} else {
			return 0;
		}
	}
	
	
}
