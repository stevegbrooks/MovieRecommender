/**
 * POJO for passing along a movie recommendation to the client
 * @author sgb
 *
 */
public class Recommendation implements Comparable<Recommendation>{
	private Movie movie;
	private double prediction;
	
	public Recommendation(Movie movie, double prediction) {
		this.movie = movie;
		this.prediction = prediction;
	}

	/**
	 * @return the movie
	 */
	public Movie getMovie() {
		return movie;
	}

	/**
	 * @param movie the movie to set
	 */
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	/**
	 * @return the prediction
	 */
	public double getPrediction() {
		return prediction;
	}

	/**
	 * @param prediction the prediction to set
	 */
	public void setPrediction(double prediction) {
		this.prediction = prediction;
	}
	/**
	 * Sorts in descending order based on predicted rating
	 */
	@Override
	public int compareTo(Recommendation o) {
		if (this.prediction < o.getPrediction()) {
			return 1;
		} else if (this.prediction > o.getPrediction()) {
			return - 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Movie ID: " + this.movie.getMovieID());
		sb.append("; Movie Name: " + this.movie.toString());
		sb.append("; Predicted Rating: " + (double) Math.round(prediction * 100)/100);
		return sb.toString();
	}
}
