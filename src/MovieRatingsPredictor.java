import java.util.ArrayList;
import java.util.Collections;
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
	private MovieRatingsDataStore dataStore;
	private Set<User> usersAndRatings;
	private Set<Movie> moviesAndRatings;
	private Map<Integer, User> userIDToUserMap;
	private Map<Integer, Movie> movieIDToMovieMap;
	private SimilarityEngine similarityEngine;
	private Logger log;
	
	public MovieRatingsPredictor(String ratingsFileName, String moviesFileName) {
		dataStore = new MovieRatingsDataStore(ratingsFileName, moviesFileName);
		
		usersAndRatings = dataStore.getUsersAndRatings();
		moviesAndRatings = dataStore.getMoviesAndRatings();
		userIDToUserMap = dataStore.getUserIDToUserMap();
		movieIDToMovieMap = dataStore.getMovieIDToMovieMap();
		
		similarityEngine = new SimilarityEngine();
		
		log = Logger.getInstance();
		log.setMessage("MovieRatingsPredictor::MovieRatingsPredictor instantiated");
		log.printToLog();
	}
	/**
	 * This method is used both internally by the getMovieRecommendation() method as well as 
	 * externally by the UserInterface. It produces a Double based on the algorithms in the SimilarityEngine class.
	 * @param user the user of interest
	 * @param movieOfInterest a movie that the user has not seen 
	 * @param neighborhoodSize the number of neighbors to base the prediction on
	 * @param isInternalCall whether or not the method is being called internally
	 * @return a Double. Returns null if the prediction is NaN.
	 */
	public Double getPrediction(User user, Movie movieOfInterest, int neighborhoodSize, boolean isInternalCall) {
		
		if (!isInternalCall) {
			Map<Movie, Double> usersMovieRatings = user.getMovieRatings();
			for (Movie movie : usersMovieRatings.keySet()) {
				if (movie.compareTo(movieOfInterest) == 0) {
					throw new IllegalArgumentException("Error: User has already rated this movie, "
							+ "and gave it a rating of " + usersMovieRatings.get(movie) + ".");
				}
			}
		}
		
		double usersAvgRating = user.getAvgRating();
		
		List<Neighbor> neighbors = similarityEngine.getUserNeighborhood(user, movieOfInterest, 
				dataStore.getUsersAndRatings(), neighborhoodSize);
		
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
		
		if (denominator == 0 || Double.isNaN(numerator) || Double.isNaN(denominator)) {
			return null;
		} else {
			double prediction = usersAvgRating + (numerator/denominator);
			
			if (prediction >= 5) {
				return 5.0;
			} else if (prediction <= 0) {
				return 0.0;
			} else {
				return prediction;
			}
			
		}
	}
	/**
	 * This method picks out movies that the user has not seen and runs the getPrediction() method. It 
	 * stores the result in a List of type Recommendation.
	 * It stops doing this when the number of recommendations is equal to the threshold argument.
	 * It then sorts (descending in terms of predicted rating) the list of recommendations.
	 * 
	 * @param user the user to get recommendations for
	 * @param threshold the number of recommendations to return
	 * @return a List of type Recommendation, sorted descending in terms of predicted rating
	 */
	public List<Recommendation> getMovieRecommendations(User user, int threshold) {
		
		List<Recommendation> recommendations = new ArrayList<>();
		Map<Movie, Double> moviesUserHasSeen = user.getMovieRatings();
		for (Movie movie : moviesAndRatings) {
			if (!moviesUserHasSeen.containsKey(movie)) {
				boolean isInternalCall = true;
				Double predictedRating = getPrediction(user, movie, 2, isInternalCall);
				if (predictedRating != null) {
					recommendations.add(new Recommendation(movie, predictedRating));
				}
			}
			if (recommendations.size() >= threshold) {
				break;
			}
		}
		log.setMessage("MovieRatingsPredictor::getMovieRecommendations returned a List of Recommendations.");
		log.printToLog();
		Collections.sort(recommendations);
		return recommendations;
	}

	/**
	 * @return the usersAndRatings
	 */
	public Set<User> getUsersAndRatings() {
		return usersAndRatings;
	}

	/**
	 * @return the userIDToUserMap
	 */
	public Map<Integer, User> getUserIDToUserMap() {
		return userIDToUserMap;
	}

	/**
	 * @return the movieIDToMovieMap
	 */
	public Map<Integer, Movie> getMovieIDToMovieMap() {
		return movieIDToMovieMap;
	}

}
