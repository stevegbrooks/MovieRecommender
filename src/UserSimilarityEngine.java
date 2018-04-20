import java.util.Map;

/**
 * This class will be used by MovieRatingsPredictor
 * and assist in the task of making ratings predictions.
 * @author sgb
 *
 */
public class UserSimilarityEngine {
	private Logger log;
	
	public UserSimilarityEngine() {
		log = Logger.getInstance();
		log.setMessage("UserSimilarityEngine::UserSimilarityEngine instantiated.");
		log.printToLog();
	}
		
	public double pearsonCorrelation(User user1, User user2) {
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
				}
			}
		}
		
		double numerator = sumU1U2/moviesInCommon - sumU1 * sumU2 / moviesInCommon / moviesInCommon;
		double denominatorPart1 = Math.sqrt(sumU1U1 / moviesInCommon - sumU1 * sumU1 / moviesInCommon / moviesInCommon);
		double denominatorPart2 = Math.sqrt(sumU2U2 / moviesInCommon - sumU2 * sumU2 / moviesInCommon / moviesInCommon);
			
		return numerator/denominatorPart1/denominatorPart2;
	}
}
