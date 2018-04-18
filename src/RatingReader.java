import java.util.List;
import java.util.Map;

public abstract class RatingReader {
	public abstract List<Rating> read();
	public abstract Map<User, Map<Movie, Rating>> getUsersDataLayer();
}
