import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInterface {

	public static void main(String[] args) {
		DataManager rdm = new DataManager("ratingsTest.dat", "moviesTest.dat");
		UserSimilarityEngine use = new UserSimilarityEngine();
		
		
		Map<User, Map<Movie, Double>> usersAndRatings = rdm.getUsersAndRatings();
		Map<Movie, Map<User, Double>> moviesAndRatings = rdm.getMoviesAndRatings();
		
		
		List<User> users = new ArrayList<>();
		int counter = 0;
		for (User user : usersAndRatings.keySet()) {
			users.add(user);
			Map<Movie, Double> movieRatings = new HashMap<>();
			movieRatings = usersAndRatings.get(user);
			System.out.println(user.toString());
			System.out.println("User's mean rating: " + user.getMeanRating());
			System.out.println("# of Ratings: " + movieRatings.size());
			System.out.println("Ratings: " + movieRatings.toString());
			System.out.println("=========");
		}
		
		
		counter = 0;
		System.out.println(moviesAndRatings.size());
		for (Movie movie : moviesAndRatings.keySet()) {
			Map<User, Double> userRatings = new HashMap<>();
			userRatings = moviesAndRatings.get(movie);
			System.out.println(movie.toString());
			System.out.println("# of Ratings: " + userRatings.size());
			System.out.println("Ratings: " + userRatings.toString());
			System.out.println("=========");
			counter++;
		}
	}
}
