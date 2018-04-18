import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingDATReader extends RatingReader {
	private String fileName;
	private Map<User, Map<Movie, Rating>> users;
	
	public RatingDATReader(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public List<Rating> read() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			List<Rating> ratings = new ArrayList<Rating>();
			users = new HashMap<>();
			Map<Movie, Rating> movieRatings = new HashMap<>();
			int userID = 0;
			String line = new String();
			while ((line = br.readLine()) != null) {
				
				String[] ls = line.split("::");
				int currentUserID = Integer.parseInt(ls[0]);
				int currentMovieID = Integer.parseInt(ls[1]);
				double currentRating = Double.parseDouble(ls[2]);
				ratings.add(new Rating(currentUserID, currentMovieID, currentRating));
				
				if (currentUserID == userID) {
					movieRatings.put(new Movie(currentMovieID), new Rating(currentRating));
				} else {
					if (users.isEmpty()) {
						users.put(new User(currentUserID), movieRatings);
					} else {
						users.put(new User(userID), movieRatings);
					}
					movieRatings = new HashMap<>();
					userID = currentUserID;
				}
				
			}
			br.close();
			return ratings;
		} catch (IOException e) {
			System.out.println("ERROR: IO Exception on movie dat input file - " + fileName);
			return null;
		}
	}
	@Override
	public Map<User, Map<Movie, Rating>> getUsersDataLayer() {
		return users;
	}

}
