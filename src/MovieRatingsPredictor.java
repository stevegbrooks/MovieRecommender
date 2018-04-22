import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class will house the getPrediction() and
 * getMovieRecommendations() methods, and act as the 
 * layer between the data and the user.
 * 
 * @author sgb
 *
 */
public class MovieRatingsPredictor {
	private DataManager dataManager;
	private Set<User> usersAndRatings;
	private UserSimilarityEngine similarityEngine;
	private Logger log;
	
	public MovieRatingsPredictor(String ratingsFileName, String moviesFileName) {
		dataManager = new DataManager(ratingsFileName, moviesFileName);
		usersAndRatings = dataManager.getUsersAndRatings();
		similarityEngine = new UserSimilarityEngine();
		log = Logger.getInstance();
		log.setMessage("MovieRatingsPredictor::MovieRatingsPredictor instantiated");
		log.printToLog();
	}
	
	public double getPrediction(User user, Movie movieOfInterest, int neighborhoodSize) {
		
		Map<Movie, Double> usersMovieRatings = user.getMovieRatings();
		for (Movie movie : usersMovieRatings.keySet()) {
			if (movie.compareTo(movieOfInterest) == 0) {
				throw new IllegalArgumentException("Error: User has already rated this movie, "
						+ "and gave it a rating of " + usersMovieRatings.get(movie) + ".");
			}
		}
		
		double usersAvgRating = user.getAvgRating();
		
		List<Neighbor> neighbors = similarityEngine.getUserNeighborhood(user, movieOfInterest, dataManager.getUsersAndRatings(), neighborhoodSize);
		
		double numerator = 0;
		double denominator = 0;
		
		for (Neighbor neighbor : neighbors) {
			
			double neighborsCoeff = neighbor.getCoeff();
			double neighborsAvgRating = neighbor.getUser().getAvgRating();
			Map<Movie, Double> neighborsRatings = neighbor.getUser().getMovieRatings();
			
			double neighborsRatingOfMovie = 0;
			
			for (Movie movie : neighborsRatings.keySet()) {
				if (movie.compareTo(movieOfInterest) == 0) {
					neighborsRatingOfMovie = neighborsRatings.get(movie);
				}
			}
			
			numerator += (neighborsCoeff * (neighborsRatingOfMovie - neighborsAvgRating));
			denominator += Math.abs(neighborsCoeff);
			
		}
		log.setMessage("MovieRatingsPredictor::getPredictor() returned a prediction.");
		log.printToLog();
		return usersAvgRating + (numerator/denominator);
	}
	
	
	public List<Recommendation> getMovieRecommendations(User user, int threshold) {
		
		log.setMessage("MovieRatingsPredictor::getMovieRecommendations returned a List of Recommendations.");
		log.printToLog();
		return null;
	}

	/**
	 * @return the usersAndRatings
	 */
	public Set<User> getUsersAndRatings() {
		return usersAndRatings;
	}

}
