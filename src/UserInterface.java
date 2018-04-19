import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class UserInterface {

	public static void main(String[] args) {
		System.out.println("Start Reading:" + Instant.now());
		DataManager rdm = new DataManager("ratings.dat", "movies.dat");
		System.out.println("End Reading:" + Instant.now());
		
		System.out.println("Start DataManager:" + Instant.now());
		Map<User, Map<Movie, Double>> usersAndRatings = rdm.getUsersAndRatings();
		Map<Movie, Map<User, Double>> moviesAndRatings = rdm.getMoviesAndRatings();
		System.out.println("End DataManager:" + Instant.now());
		
		int counter = 0;
		for (User user : usersAndRatings.keySet()) {
			Map<Movie, Double> movieRatings = new HashMap<>();
			movieRatings = usersAndRatings.get(user);
			System.out.println(user.toString());
			System.out.println("# of Ratings: " + movieRatings.size());
			System.out.println("Ratings: " + movieRatings.toString());
			System.out.println("=========");
			counter++;
			if (counter > 0) {
				break;
			}
		}
		
		counter = 0;
		for (Movie movie : moviesAndRatings.keySet()) {
			Map<User, Double> userRatings = new HashMap<>();
			userRatings = moviesAndRatings.get(movie);
			System.out.println(movie.toString());
			System.out.println("# of Ratings: " + userRatings.size());
			System.out.println("Ratings: " + userRatings.toString());
			System.out.println("=========");
			counter++;
			if (counter > 2) {
				break;
			}
		}
	}
}
