import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class creates and stores the necessary data layers for this program.
 * 
 * This is the top of the Data tier, and is the source of information for the Controller tier. 
 * It builds the necessary dependencies based on the file paths given to it 
 * by the Controller tier.
 * 
 * This class stores the Collections and Maps of data that the Controller needs to do its job.
 * 
 * @author sgb
 *
 */
public class DataStore {

	private RatingTupleReaderFactory ratingTupleReaderFactory;
	private RatingTupleReader ratingTupleReader;
	private List<RatingTuple> ratings;

	private MovieNamerFactory movieNamerFactory;
	private MovieNamer movieNamer;

	private Set<User> usersAndRatings;
	private Set<Movie> moviesAndRatings;
	private Map<Integer, User> userIDToUser;
	private Map<Integer, Movie> movieIDToMovie;

	private Log log;
	
	/**
	 * This is a busy constructor. Basically a client just needs to instantiate
	 * this object and then call the getter methods to retrieve the data.
	 * @param ratingsFile
	 * @param movieNamesFile
	 */
	public DataStore(String ratingsFile, String movieNamesFile) {
		log = Log.getInstance();
		log.print("DataManager::DataManager instantiated.");
		log.close();
		
		ratingTupleReaderFactory = new RatingTupleReaderFactory();
		ratingTupleReader = ratingTupleReaderFactory.createReader(ratingsFile);

		movieNamerFactory = new MovieNamerFactory();
		movieNamer = movieNamerFactory.createNamer(movieNamesFile);

		ratings = ratingTupleReader.read();
		
		usersAndRatings = new HashSet<>();
		userIDToUser = new HashMap<Integer, User>();
		usersAndRatings = createUsersAndRatingsDataLayer();
		
		moviesAndRatings = new HashSet<>();
		movieIDToMovie = new HashMap<Integer, Movie>();
		moviesAndRatings = createMoviesAndRatingsDataLayer();
		
		movieNamer.nameMovies();
	}
	/**
	 * Creates a Set of type User, and sets all User objects with a 
	 * HashMap<Movie, Double> for all the movies ratings that the User has completed.
	 * @return Set<User>
	 */
	private Set<User> createUsersAndRatingsDataLayer() {
		Collections.sort(ratings, new RatingTupleComparator_User());
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
				userIDToUser.put(userID, user);
			
			} else if (currentUser.compareTo(user) != 0) {
				
				user.setMovieRatings(movieRatings);
				user.setAvgRating(ratingTotal/movieRatings.size());
				
				usersAndRatings.add(user);
				userIDToUser.put(userID, user);
				
				movieRatings = new HashMap<>();
				ratingTotal = 0;
				ratingTotal += rating.getRating();
				
				movieRatings.put(rating.getMovie(), rating.getRating());
				
				user = currentUser;
				userID = currentUserID;
			
			} 
			iterationCounter++;
		}
		log.print("DataManager::Successfully created 'Users and Movie Ratings' data layer.");
		log.close();
		return usersAndRatings;
	}
	/**
	 * This method creates a Set of type Movie, and also sets each Movie objects
	 * with a HashMap<User, Double> of all the ratings its received and from which users.
	 * 
	 * It also binds movie names to each Movie object. Dependency on the MovieNameMapper.
	 * @return Set<Movie>
	 */
	private Set<Movie> createMoviesAndRatingsDataLayer() {
		Collections.sort(ratings, new RatingTupleComparator_Movie());
		Map<User, Double> userRatings = new HashMap<>();
		
		int listLastIndex = ratings.size() - 1;
		int iterationCounter = 0;
		
		Movie movie = ratings.get(0).getMovie();
		int movieID = movie.getMovieID();
		movieNamer.addObserver(movie);

		for (RatingTuple rating : ratings) {
			
			Movie currentMovie = rating.getMovie();
			int currentMovieID = currentMovie.getMovieID();
			movieNamer.addObserver(currentMovie);
			
			if (currentMovie.compareTo(movie) == 0 && iterationCounter < listLastIndex) {
				
				userRatings.put(rating.getUser(), rating.getRating());
			
			} else if (currentMovie.compareTo(movie) == 0 && iterationCounter == listLastIndex) {
				
				userRatings.put(rating.getUser(), rating.getRating());
				movie.setUserRatings(userRatings);
				moviesAndRatings.add(movie);
				movieIDToMovie.put(movieID, movie);
				
			} else if (currentMovie.compareTo(movie) != 0 && iterationCounter == listLastIndex) {
				
				movie.setUserRatings(userRatings);
				moviesAndRatings.add(movie);
				movieIDToMovie.put(movieID, movie);
				userRatings = new HashMap<>();
				userRatings.put(rating.getUser(), rating.getRating());
				currentMovie.setUserRatings(userRatings);
				moviesAndRatings.add(currentMovie);
				movieIDToMovie.put(currentMovieID, currentMovie);
			
			} else if (currentMovie.compareTo(movie) != 0) {
				
				movie.setUserRatings(userRatings);
				moviesAndRatings.add(movie);
				movieIDToMovie.put(movieID, movie);
				userRatings = new HashMap<>();
				userRatings.put(rating.getUser(), rating.getRating());
				movie = currentMovie;
				movieID = currentMovieID;
				
			}
			iterationCounter++;
		}
		log.print("DataManager::Successfully created 'Movies and User Ratings' data layer.");
		log.close();
		return moviesAndRatings;
	}
	
	/**
	 * @return the userIDToUserMap
	 */
	public Map<Integer, User> getUserIDToUserMap() {
		return userIDToUser;
	}

	/**
	 * @return the movieIDToMovieMap
	 */
	public Map<Integer, Movie> getMovieIDToMovieMap() {
		return movieIDToMovie;
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
	
}
