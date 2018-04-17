import java.util.List;

/**
 * This class will operate the RatingsReader and
 * take in a List of type Rating in order to create 
 * the data layer for User.
 * 
 * @author sgb
 *
 */
public class RatingsDataManager implements DataManager{
	private RatingsReader ratingsReader;
	private List<Rating> ratings;
	private List<User> users;
	
	public RatingsDataManager(String ratingsFile) {
		ratingsReader = new RatingsDATReader(ratingsFile);
		ratings = ratingsReader.read();
		createDataLayer();
	}

	@Override
	public void createDataLayer() {
		//TODO: implement!
	}
	
	public List<User> getUsers() {
		return users;
	}

}
