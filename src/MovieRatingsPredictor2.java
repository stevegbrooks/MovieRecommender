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
public class MovieRatingsPredictor2 {
	private DataManager2 dataManager2;
	private Set<User> usersAndRatings;
	private Set<Movie> moviesAndRatings;
	private Map<Integer, User> userIDToUserMap;
	private Map<Integer, Movie> movieIDToMovieMap;
	private SimilarityEngine2 similarityEngine2;
	private Logger log;
	
	public MovieRatingsPredictor2(String ratingsFileName, String moviesFileName) {
		dataManager2 = new DataManager2(ratingsFileName, moviesFileName);
		usersAndRatings = dataManager2.getUsersAndRatings();
		moviesAndRatings = dataManager2.getMoviesAndRatings();
		userIDToUserMap = dataManager2.getUserIDToUserMap();
		movieIDToMovieMap = dataManager2.getMovieIDToMovieMap();
		similarityEngine2 = new SimilarityEngine2();
		log = Logger.getInstance();
		log.setMessage("MovieRatingsPredictor::MovieRatingsPredictor instantiated");
		log.printToLog();
	}
	
	public Double getPrediction(User user, Movie movieOfInterest, int neighborhoodSize) {
		
		Map<Movie, Double> usersMovieRatings = user.getMovieRatings();
		for (Movie movie : usersMovieRatings.keySet()) {
			if (movie.compareTo(movieOfInterest) == 0) {
				throw new IllegalArgumentException("Error: User has already rated this movie, "
						+ "and gave it a rating of " + usersMovieRatings.get(movie) + ".");
			}
		}
		
		double usersAvgRating = user.getAvgRating();
		
		List<Neighbor> neighbors = similarityEngine2.getUserNeighborhood(user, movieOfInterest, dataManager2.getUsersAndRatings(), neighborhoodSize);
		
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
		
		if (numerator == 0 || denominator == 0 
				|| numerator == Double.NaN || denominator == Double.NaN) {
			return null;
		} else {
			return usersAvgRating + (numerator/denominator);
		}
	}
	
	
	public List<Recommendation> getMovieRecommendations(User user, int threshold) {
		//I'll first need to get the list of movies that a given user has NOT seen
		//Then, for each movie, run the getPrediction() method, and store that in a list
		//of recommendations.  Then sort by predicted rating, descending, and return the
		//n-highest (as defined by threshold).
		List<Recommendation> recommendations = new ArrayList<>();
		Map<Movie, Double> moviesUserHasSeen = user.getMovieRatings();
		for (Movie movie : moviesAndRatings) {
			for (Movie movieSeen : moviesUserHasSeen.keySet()) {
				if (movie.compareTo(movieSeen) != 0) {
					double predictedRating = getPrediction(user, movie, 2);
					recommendations.add(new Recommendation(movie, predictedRating));
				}
			}
		}
		log.setMessage("MovieRatingsPredictor::getMovieRecommendations returned a List of Recommendations.");
		log.printToLog();
		Collections.sort(recommendations);
		if (recommendations.size() > threshold) {
			List<Recommendation> recommendationsSlice = recommendations.subList(0, threshold);
			return recommendationsSlice;
		} else {
			return recommendations;
		}
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
