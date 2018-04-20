import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private Set<User> usersAndRatings;
	private Set<Movie> moviesAndRatings;
	private Logger log;

	public DataManager(String ratingsFile, String movieNamesFile) {
		ratingReader = new RatingDATReader(ratingsFile);
		movieNameReader = new MovieNamesDATReader(movieNamesFile);

		ratings = ratingReader.read();
		movieNames = movieNameReader.read();

		usersAndRatings = new HashSet<>();
		moviesAndRatings = new HashSet<>();
		
		log = Logger.getInstance();
		log.setMessage("DataManager::DataManager instantiated.");
		log.printToLog();
	}

	public Set<User> getUsersAndRatings() {
		Collections.sort(ratings, new RatingUserIDComparator());
		Map<Movie, Double> movieRatings = new HashMap<>();
		
		User user = ratings.get(0).getUser();
		int listLastIndex = ratings.size() - 1;
		int iterationCounter = 0;
		
		for (RatingTuple rating : ratings) {

			User currentUser = rating.getUser();
			if (currentUser.compareTo(user) == 0 && iterationCounter < listLastIndex) {
			
				movieRatings.put(rating.getMovie(), rating.getRating());
			
			} else if (currentUser.compareTo(user) == 0 && iterationCounter == listLastIndex) {
				
				movieRatings.put(rating.getMovie(), rating.getRating());
				user.setMovieRatings(movieRatings);
				usersAndRatings.add(user);
			
			} else if (currentUser.compareTo(user) != 0) {
				
				user.setMovieRatings(movieRatings);
				usersAndRatings.add(user);
				movieRatings = new HashMap<>();
				movieRatings.put(rating.getMovie(), rating.getRating());
				user = currentUser;
			
			} 
			iterationCounter++;
		}
		log.setMessage("DataManager::Successfully created 'Users and Movie Ratings' data layer.");
		log.printToLog();
		return usersAndRatings;
	}

	public Set<Movie> getMoviesAndRatings() {
		Collections.sort(ratings, new RatingMovieIDComparator());
		Map<User, Double> userRatings = new HashMap<>();
		
		int listLastIndex = ratings.size() - 1;
		int iterationCounter = 0;
		
		Movie movie = ratings.get(0).getMovie();
		movie.setMovieName(movieNames.get(movie));

		for (RatingTuple rating : ratings) {
			
			Movie currentMovie = rating.getMovie();
			currentMovie.setMovieName(movieNames.get(currentMovie));
			
			if (currentMovie.compareTo(movie) == 0 && iterationCounter < listLastIndex) {
				
				userRatings.put(rating.getUser(), rating.getRating());
			
			} else if (currentMovie.compareTo(movie) == 0 && iterationCounter == listLastIndex) {
				
				userRatings.put(rating.getUser(), rating.getRating());
				movie.setUserRatings(userRatings);
				moviesAndRatings.add(movie);
				
			} else if (currentMovie.compareTo(movie) != 0 && iterationCounter == listLastIndex) {
				
				movie.setUserRatings(userRatings);
				moviesAndRatings.add(movie);
				userRatings = new HashMap<>();
				userRatings.put(rating.getUser(), rating.getRating());
				currentMovie.setUserRatings(userRatings);
				moviesAndRatings.add(currentMovie);
			
			} else if (currentMovie.compareTo(movie) != 0) {
				
				movie.setUserRatings(userRatings);
				moviesAndRatings.add(movie);
				userRatings = new HashMap<>();
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
