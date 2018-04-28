import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is used by MovieRatingsPredictor
 * and assists in the task of making predictions.
 * @author sgb
 *
 */
public class SimilarityEngine {
	private Logger log;
	
	public SimilarityEngine() {
		log = Logger.getInstance();
		log.setMessage("UserSimilarityEngine::UserSimilarityEngine instantiated.");
		log.printToLog();
	}
	
	public List<Neighbor> getUserNeighborhood(User userOfInterest, Movie movieOfInterest, 
			Set<User> otherUsers, int neighborhoodSize) {
		
		List<Neighbor> neighborhood = new ArrayList<>();
		for (User otherUser : otherUsers) {
			if (otherUser.compareTo(userOfInterest) != 0) {
				Map<Movie, Double> otherUsersRatings = otherUser.getMovieRatings();
				boolean hasMovie = false;
				for (Movie movie : otherUsersRatings.keySet()) {
					if (movie.compareTo(movieOfInterest) == 0) {
						hasMovie = true;
						break;
					}
				}
				if (hasMovie) {
					Double pearsonCoeff = getPearsonCorrelation(userOfInterest, otherUser);
					if (pearsonCoeff != null) {
						neighborhood.add(new Neighbor(otherUser, pearsonCoeff));
					}
				}
			}
		}
		
		Collections.sort(neighborhood);
		
		if (neighborhood.size() > neighborhoodSize) {
			List<Neighbor> neighborhoodSlice = neighborhood.subList(0, neighborhoodSize);
			return neighborhoodSlice;
		}
		
		return neighborhood;
	}
		
	private Double getPearsonCorrelation(User user1, User user2) {
		Map<Movie, Double> user1Ratings = user1.getMovieRatings();
		Map<Movie, Double> user2Ratings = user2.getMovieRatings();
		
		double sumU1 = 0;
		double sumU2 = 0;
		double sumU1U2 = 0;
		double sumU1U1 = 0;
		double sumU2U2 = 0;
		int moviesInCommon = 0;
		
		for (Movie movie1 : user1Ratings.keySet()) {
			for (Movie movie2 : user2Ratings.keySet()) {
				if (movie1.equals(movie2)) {
					moviesInCommon++;
					sumU1 += user1Ratings.get(movie1);
					sumU2 += user2Ratings.get(movie2);
					sumU1U2 += user1Ratings.get(movie1) * user2Ratings.get(movie2);
					sumU1U1 += Math.pow(user1Ratings.get(movie1), 2);
					sumU2U2 += Math.pow(user2Ratings.get(movie2), 2);
					break;
				}
			}
		}
		
		double numerator = sumU1U2/moviesInCommon - sumU1 * sumU2 / moviesInCommon / moviesInCommon;
		double denominatorPart1 = Math.sqrt(sumU1U1 / moviesInCommon - sumU1 * sumU1 / moviesInCommon / moviesInCommon);
		double denominatorPart2 = Math.sqrt(sumU2U2 / moviesInCommon - sumU2 * sumU2 / moviesInCommon / moviesInCommon);

		double prediction =  numerator/denominatorPart1/denominatorPart2;
		
		if (Double.isNaN(prediction)) {
			return null;
		} else {
			if (prediction >= 1) {
				return 1.0;
			} else if (prediction <= -1) {
				return -1.0;
			} else {
				return prediction;
			}
		}
	}
}
