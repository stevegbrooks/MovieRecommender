import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class will operate the RatingsReader and
 * take in a List of type Rating in order to create 
 * the data layer for User.
 * 
 * @author sgb
 *
 */
public class DataManager {
	private Reader<RatingTuple> ratingReader;
	private MovieNamesDATReader movieNameReader;
	private List<RatingTuple> ratings;
	private Map<Movie, String> movieNames;
	private Map<User, Map<Movie, Double>> usersAndRatings;
	private Map<Movie, Map<User, Double>> moviesAndRatings;
	private Logger log;

	public DataManager(String ratingsFile, String movieNamesFile) {
		ratingReader = new RatingDATReader(ratingsFile);
		movieNameReader = new MovieNamesDATReader(movieNamesFile);

		ratings = ratingReader.read();
		movieNames = movieNameReader.read();

		usersAndRatings = new HashMap<>();
		moviesAndRatings = new HashMap<>();
		
		log = Logger.getInstance();
		log.setMessage("DataManager::DataManager instantiated.");
		log.printToLog();
	}

	public Map<User, Map<Movie, Double>> getUsersAndRatings() {
		Collections.sort(ratings, new RatingUserIDComparator());
		Map<Movie, Double> movieRatings = new HashMap<>();
		
		User user = ratings.get(0).getUser();
		int listLastIndex = ratings.size() - 1;
		int iterationCounter = 0;
		double ratingTotal = 0;
		
		for (RatingTuple rating : ratings) {

			User currentUser = rating.getUser();
			if (currentUser.compareTo(user) == 0 && iterationCounter < listLastIndex) {
			
				ratingTotal += rating.getRating();
				movieRatings.put(rating.getMovie(), rating.getRating());
			
			} else if (currentUser.compareTo(user) == 0 && iterationCounter == listLastIndex) {
				
				ratingTotal += rating.getRating();
				movieRatings.put(rating.getMovie(), rating.getRating());
				usersAndRatings.put(user, movieRatings);
				user.setMeanRating(ratingTotal/movieRatings.size());
			
			} else if (currentUser.compareTo(user) != 0) {
				
				usersAndRatings.put(user, movieRatings);
				user.setMeanRating(ratingTotal/movieRatings.size());
				movieRatings = new HashMap<>();
				ratingTotal = 0;
				ratingTotal += rating.getRating();
				movieRatings.put(rating.getMovie(), rating.getRating());
				user = currentUser;
			
			} 
			iterationCounter++;
		}
		log.setMessage("DataManager::Successfully created 'Users and Movie Ratings' data layer.");
		log.printToLog();
		return usersAndRatings;
	}

	public Map<Movie, Map<User, Double>> getMoviesAndRatings() {
		Collections.sort(ratings, new RatingMovieIDComparator());
		Map<User, Double> userRatings = new HashMap<>();
		
		Movie movie = ratings.get(0).getMovie();
		movie.setMovieName(movieNames.get(movie));
		
		int listLastIndex = ratings.size() - 1;
		int iterationCounter = 0;
		double ratingTotal = 0;

		for (RatingTuple rating : ratings) {
			
			Movie currentMovie = rating.getMovie();
			currentMovie.setMovieName(movieNames.get(currentMovie));
			
			if (currentMovie.compareTo(movie) == 0 && iterationCounter < listLastIndex) {
				
				ratingTotal += rating.getRating();
				userRatings.put(rating.getUser(), rating.getRating());
			
			} else if (currentMovie.compareTo(movie) == 0 && iterationCounter == listLastIndex) {
				
				ratingTotal += rating.getRating();
				userRatings.put(rating.getUser(), rating.getRating());
				moviesAndRatings.put(movie, userRatings);
				movie.setMeanRating(ratingTotal/userRatings.size());
				
			} else if (currentMovie.compareTo(movie) != 0 && iterationCounter == listLastIndex) {
				
				moviesAndRatings.put(movie, userRatings);
				userRatings = new HashMap<>();
				ratingTotal = 0;
				ratingTotal += rating.getRating();
				userRatings.put(rating.getUser(), rating.getRating());
				moviesAndRatings.put(currentMovie, userRatings);
				currentMovie.setMeanRating(ratingTotal);
			
			} else if (currentMovie.compareTo(movie) != 0) {
				
				moviesAndRatings.put(movie, userRatings);
				movie.setMeanRating(ratingTotal/userRatings.size());
				userRatings = new HashMap<>();
				ratingTotal = 0;
				ratingTotal += rating.getRating();
				userRatings.put(rating.getUser(), rating.getRating());
				movie = currentMovie;
				
			}
			iterationCounter++;
		}
		log.setMessage("DataManager::Successfully created 'Movies and User Ratings' data layer.");
		log.printToLog();
		return moviesAndRatings;
	}
}
