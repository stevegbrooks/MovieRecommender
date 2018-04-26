import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 * This class will be used by MovieRatingsPredictor
 * and assist in the task of making ratings predictions.
 * @author sgb
 *
 */
public class SimilarityEngine2 {
	private Logger log;
	private PearsonsCorrelation pearsonsCorrelation;
	
	public SimilarityEngine2() {
		pearsonsCorrelation = new PearsonsCorrelation();
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
					if (!Double.isNaN(pearsonCoeff)) {
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
		
	private double getPearsonCorrelation(User user1, User user2) {
		Map<Movie, Double> user1Ratings = user1.getMovieRatings();
		Map<Movie, Double> user2Ratings = user2.getMovieRatings();
		
		ArrayList<Double> user1RatingsData = new ArrayList<>();
		ArrayList<Double> user2RatingsData = new ArrayList<>();
		
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
					user1RatingsData.add(user1Ratings.get(movie1));
					user2RatingsData.add(user2Ratings.get(movie2));
					
					sumU1 += user1Ratings.get(movie1);
					sumU2 += user2Ratings.get(movie2);
					sumU1U2 += user1Ratings.get(movie1) * user2Ratings.get(movie2);
					sumU1U1 += Math.pow(user1Ratings.get(movie1), 2);
					sumU2U2 += Math.pow(user2Ratings.get(movie2), 2);
					break;
				}
			}
		}
		user1RatingsData.trimToSize();
		user2RatingsData.trimToSize();
		double[] user1RatingsDataArray = new double[user1RatingsData.size()];
		for (int i = 0; i < user1RatingsData.size(); i++) {
			user1RatingsDataArray[i] = user1RatingsData.get(i);
		}
		double[] user2RatingsDataArray = new double[user2RatingsData.size()];
		for (int i = 0; i < user2RatingsData.size(); i++) {
			user2RatingsDataArray[i] = user2RatingsData.get(i);
		}
		
		//double alternativeAnswer = pearsonsCorrelation.correlation(user1RatingsDataArray, user2RatingsDataArray);
		
		double numerator = sumU1U2/moviesInCommon - sumU1 * sumU2 / moviesInCommon / moviesInCommon;
		double denominatorPart1 = Math.sqrt(sumU1U1 / moviesInCommon - sumU1 * sumU1 / moviesInCommon / moviesInCommon);
		double denominatorPart2 = Math.sqrt(sumU2U2 / moviesInCommon - sumU2 * sumU2 / moviesInCommon / moviesInCommon);
			
		return numerator/denominatorPart1/denominatorPart2;
		//return alternativeAnswer;
	}
}
