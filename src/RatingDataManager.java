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
public class RatingDataManager implements DataManager{
	private RatingReader ratingsReader;
	private List<Rating> ratings;
	private Map<User, Map<Movie, Rating>> users;
	
	public RatingDataManager(String ratingsFile) {
		ratingsReader = new RatingDATReader(ratingsFile);
		ratings = ratingsReader.read();
		users = ratingsReader.getUsersDataLayer();
		createDataLayer();
	}

	@Override
	public void createDataLayer() {
	}
	
	public Map<User, Map<Movie, Rating>> getUsersDataLayer() {
		return users;
	}
	
	public List<Rating> getRatingsDataLayer() {
		return ratings;
	}

}
