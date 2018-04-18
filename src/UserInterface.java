import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class UserInterface {

	public static void main(String[] args) {
		System.out.println("Start Reading:" + Instant.now());
		RatingDataManager rdm = new RatingDataManager("ratings.dat");
		System.out.println("End Reading:" + Instant.now());
		
		Map<User, Map<Movie, Rating>> users = rdm.getUsersDataLayer();
		
		int counter = 0;
		for (User user : users.keySet()) {
			Map<Movie, Rating> movieRatings = new HashMap<>();
			movieRatings = users.get(user);
			System.out.println(user.toString());
			System.out.println("# of Ratings: " + movieRatings.size());
			System.out.println("Ratings: " + movieRatings.toString());
			System.out.println("=========");
			counter++;
			if (counter > 9) {
				break;
			}
		}
	}
}
