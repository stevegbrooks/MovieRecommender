import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RatingsDATReader extends RatingsReader {
	private String fileName;
	private Set<User> users;
	
	public RatingsDATReader(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public Set<Rating> read() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			Set<Rating> ratings = new HashSet<Rating>();
			users = new HashSet<User>();
			String line = new String();
			while ((line = br.readLine()) != null) {
				String[] ls = line.split("::");
				int userID = Integer.parseInt(ls[0]);
				int movieID = Integer.parseInt(ls[1]);
				double rating = Double.parseDouble(ls[2]);
				ratings.add(new Rating(userID, movieID, rating));
				users.add(new User(userID));
			}
			br.close();
			return ratings;
		} catch (IOException e) {
			System.out.println("ERROR: IO Exception on movie dat input file - " + fileName);
			return null;
		}
	}

	/**
	 * @return the users
	 */
	@Override
	public Set<User> getUsers() {
		return users;
	}

}
