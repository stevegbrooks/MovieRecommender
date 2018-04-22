import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserInterface {

	public static void main(String[] args) {
		MovieRatingsPredictor mrp = new MovieRatingsPredictor("ratingsTest.dat", "movies.dat");
		Set<User> usersAndRatings = mrp.getUsersAndRatings();
		
		int counter = 0;
		User userOfInterest = null;
		Movie movieOfInterest = null;
		for (User user :usersAndRatings) {
			if (counter == 1) {
				userOfInterest = user;
			}

			Map<Movie, Double> movieRatings = new HashMap<>();
			movieRatings = user.getMovieRatings();
			
			if (counter == 0) {
				for (Movie movie : movieRatings.keySet()) {
					movieOfInterest = movie;
				}
			}
			
			System.out.println(user.toString());
			System.out.println("Avg rating: " + user.getAvgRating());
			System.out.println("# of Ratings: " + movieRatings.size());
			System.out.println("Ratings: " + movieRatings.toString());
			System.out.println("=========");
			counter++;
			if (counter > 2) {
				break;
			}
		}
		
		System.out.println(mrp.getPrediction(userOfInterest, movieOfInterest, 20));
		

		
		
		
//		
//		
//		System.out.println(moviesAndRatings.size());
//		for (Movie movie : moviesAndRatings) {
//			Map<User, Double> userRatings = new HashMap<>();
//			userRatings = movie.getUserRatings();
//			System.out.println(movie.toString());
//			System.out.println("# of Ratings: " + userRatings.size());
//			System.out.println("Ratings: " + userRatings.toString());
//			System.out.println("=========");
//		}
	}
}
