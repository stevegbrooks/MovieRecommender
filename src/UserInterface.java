import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserInterface {

	public static void main(String[] args) {
		DataManager rdm = new DataManager("ratingsTest.dat", "moviesTest.dat");		
		
		Set<User> usersAndRatings = rdm.getUsersAndRatings();
		Set<Movie> moviesAndRatings = rdm.getMoviesAndRatings();
		
		
		for (User user :usersAndRatings) {
			Map<Movie, Double> movieRatings = new HashMap<>();
			movieRatings = user.getMovieRatings();
			System.out.println(user.toString());
			System.out.println("# of Ratings: " + movieRatings.size());
			System.out.println("Ratings: " + movieRatings.toString());
			System.out.println("=========");
		}
		
		
		System.out.println(moviesAndRatings.size());
		for (Movie movie : moviesAndRatings) {
			Map<User, Double> userRatings = new HashMap<>();
			userRatings = movie.getUserRatings();
			System.out.println(movie.toString());
			System.out.println("# of Ratings: " + userRatings.size());
			System.out.println("Ratings: " + userRatings.toString());
			System.out.println("=========");
		}
	}
}
