import java.util.HashMap;
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
public class RatingDataManager implements DataManager{
	private RatingsReader ratingsReader;
	private Set<Rating> ratings;
	private Set<User> users;
	
	public RatingDataManager(String ratingsFile) {
		ratingsReader = new RatingsDATReader(ratingsFile);
		ratings = ratingsReader.read();
		users = ratingsReader.getUsers();
		createDataLayer();
	}

	@Override
	public void createDataLayer() {
		Map<Integer, Double> movieRatings = new HashMap<>();
		for (User user : users) {
			Integer userID_PK = user.getUserID();
			for (Rating rating : ratings) {
				Integer userID_FK = rating.getUserID();
				Integer movieID_FK = rating.getMovieID();
				if (userID_FK == userID_PK) {
					movieRatings.put(movieID_FK, rating.getRating());
				}
			}
			user.setMovieRatings(movieRatings);
		}
	}
	
	public Set<User> getUsersDataLayer() {
		return users;
	}
	
	public Set<Rating> getRatingsDataLayer() {
		return ratings;
	}

}
