import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * This class will create HashMap's for each of the movies 
 * coming out of the MovieReader.
 * 
 * The HashMap's key-value pair will be Movie and Integer in order to 
 * represent all the ratings that the movie has received. 
 * 
 * @author sgb
 *
 */
public class MovieDataManager implements DataManager {
	private MovieReader movieReader;
	private RatingReader ratingsReader;
	private List<Rating> ratings;
	private Set<Movie> movies;

	public MovieDataManager(String moviesFileName, String ratingsFileName) {
		movieReader = new MovieDATReader(moviesFileName);
		ratingsReader = new RatingDATReader(ratingsFileName);
		movies = movieReader.read();
		ratings = ratingsReader.read();
		
		createDataLayer();
	}

	@Override
	public void createDataLayer() {
		for (Movie movie : movies) {
			Map<Integer, Double> userRatings = new HashMap<>();
			Integer movieID_PK = movie.getMovieID();
			for (Rating rating : ratings) {
				Integer movieID_FK = rating.getMovieID();
				Integer userID_FK = rating.getUserID();
				if (movieID_FK == movieID_PK) {
					userRatings.put(userID_FK, rating.getRating());
				}
			}
			movie.setUserRatings(userRatings);
		}
	}

	public Set<Movie> getMoviesDataLayer() {
		return movies;
	}
	

}
