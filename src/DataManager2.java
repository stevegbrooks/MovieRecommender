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
public class DataManager2 {
	private Reader<RatingTuple> ratingReader;
	private MovieNamesDATMapper movieNameReader;
	private List<RatingTuple> ratings;
	private Map<Movie, String> movieNames;
	private Set<User> usersAndRatings;
	private Set<Movie> moviesAndRatings;
	private Map<Integer, User> userIDToUserMap;
	private Map<Integer, Movie> movieIDToMovieMap;
	private Logger log;

	public DataManager2(String ratingsFile, String movieNamesFile) {
		log = Logger.getInstance();
		log.setMessage("DataManager::DataManager instantiated.");
		log.printToLog();
		
		ratingReader = new RatingDATReader(ratingsFile);
		movieNameReader = new MovieNamesDATMapper(movieNamesFile);

		ratings = ratingReader.read();
		movieNames = movieNameReader.read();

		usersAndRatings = new HashSet<>();
		userIDToUserMap = new HashMap<Integer, User>();
		usersAndRatings = createUsersAndRatingsDataLayer();
		
		moviesAndRatings = new HashSet<>();
		movieIDToMovieMap = new HashMap<Integer, Movie>();
		moviesAndRatings = createMoviesAndRatingsDataLayer();
		

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

	/**
	 * @return the usersAndRatings
	 */
	public Set<User> getUsersAndRatings() {
		return usersAndRatings;
	}

	/**
	 * @return the moviesAndRatings
	 */
	public Set<Movie> getMoviesAndRatings() {
		return moviesAndRatings;
	}

	private Set<User> createUsersAndRatingsDataLayer() {
		Collections.sort(ratings, new RatingUserIDComparator());
		Map<Movie, Double> movieRatings = new HashMap<>();
		
		User user = ratings.get(0).getUser();
		int userID = user.getUserID();
		int listLastIndex = ratings.size() - 1;
		int iterationCounter = 0;
		double ratingTotal = 0;
		
		for (RatingTuple rating : ratings) {

			User currentUser = rating.getUser();
			int currentUserID = currentUser.getUserID();
			
			if (currentUser.compareTo(user) == 0 && iterationCounter < listLastIndex) {
				
				ratingTotal += rating.getRating();
				movieRatings.put(rating.getMovie(), rating.getRating());
			
			} else if (currentUser.compareTo(user) == 0 && iterationCounter == listLastIndex) {
				
				ratingTotal += rating.getRating();
				movieRatings.put(rating.getMovie(), rating.getRating());
				user.setMovieRatings(movieRatings);
				user.setAvgRating(ratingTotal/movieRatings.size());
				usersAndRatings.add(user);
				
				userIDToUserMap.put(userID, user);
			
			} else if (currentUser.compareTo(user) != 0) {
				
				user.setMovieRatings(movieRatings);
				user.setAvgRating(ratingTotal/movieRatings.size());
				usersAndRatings.add(user);
				
				userIDToUserMap.put(userID, user);
				
				movieRatings = new HashMap<>();
				ratingTotal = 0;
				ratingTotal += rating.getRating();
				movieRatings.put(rating.getMovie(), rating.getRating());
				user = currentUser;
				userID = currentUserID;
			
			} 
			iterationCounter++;
		}
		log.setMessage("DataManager::Successfully created 'Users and Movie Ratings' data layer.");
		log.printToLog();
		return usersAndRatings;
	}

	private Set<Movie> createMoviesAndRatingsDataLayer() {
		Collections.sort(ratings, new RatingMovieIDComparator());
		Map<User, Double> userRatings = new HashMap<>();
		
		int listLastIndex = ratings.size() - 1;
		int iterationCounter = 0;
		
		Movie movie = ratings.get(0).getMovie();
		int movieID = movie.getMovieID();
		movie.setMovieName(movieNames.get(movie));

		for (RatingTuple rating : ratings) {
			
			Movie currentMovie = rating.getMovie();
			int currentMovieID = currentMovie.getMovieID();
			currentMovie.setMovieName(movieNames.get(currentMovie));
			
			if (currentMovie.compareTo(movie) == 0 && iterationCounter < listLastIndex) {
				
				userRatings.put(rating.getUser(), rating.getRating());
			
			} else if (currentMovie.compareTo(movie) == 0 && iterationCounter == listLastIndex) {
				
				userRatings.put(rating.getUser(), rating.getRating());
				movie.setUserRatings(userRatings);
				moviesAndRatings.add(movie);
				movieIDToMovieMap.put(movieID, movie);
				
			} else if (currentMovie.compareTo(movie) != 0 && iterationCounter == listLastIndex) {
				
				movie.setUserRatings(userRatings);
				moviesAndRatings.add(movie);
				movieIDToMovieMap.put(movieID, movie);
				userRatings = new HashMap<>();
				userRatings.put(rating.getUser(), rating.getRating());
				currentMovie.setUserRatings(userRatings);
				moviesAndRatings.add(currentMovie);
				movieIDToMovieMap.put(currentMovieID, currentMovie);
			
			} else if (currentMovie.compareTo(movie) != 0) {
				
				movie.setUserRatings(userRatings);
				moviesAndRatings.add(movie);
				movieIDToMovieMap.put(movieID, movie);
				userRatings = new HashMap<>();
				userRatings.put(rating.getUser(), rating.getRating());
				movie = currentMovie;
				movieID = currentMovieID;
				
			}
			iterationCounter++;
		}
		log.setMessage("DataManager::Successfully created 'Movies and User Ratings' data layer.");
		log.printToLog();
		return moviesAndRatings;
	}
	
	
}
