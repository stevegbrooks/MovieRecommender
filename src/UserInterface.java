import java.util.Map;
import java.util.Set;

public class UserInterface {

	public static void main(String[] args) {
		RatingDataManager rdm = new RatingDataManager("ratings.dat");
		//MoviesDataManager mdm = new MoviesDataManager("movies.dat", "ratings.dat");
		
		Set<User> users = rdm.getUsersDataLayer();
		//Set<Movie> movies = mdm.getMoviesDataLayer();
		//Set<Rating> ratings = mdm.getRatingsDataLayer();
		
		int counter = 0;
		for (User user : users) {
			Map<Integer, Double> movieRatings = user.getMovieRatings();
			System.out.println(user.getUserID() + ":");
			System.out.println("==========");
			System.out.println(movieRatings.toString());
			counter += 1;
			if (counter == 10) {
				break;
			}
		}
	}
}
