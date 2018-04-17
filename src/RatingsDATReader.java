import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RatingsDATReader extends RatingsReader {
	private String fileName;
	
	public RatingsDATReader(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public List<Rating> read() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			List<Rating> ratings = new ArrayList<Rating>();
			String line = new String();
			while ((line = br.readLine()) != null) {
				String[] ls = line.split("::");
				int userID = Integer.parseInt(ls[0]);
				int movieID = Integer.parseInt(ls[1]);
				double rating = Double.parseDouble(ls[2]);
				ratings.add(new Rating(userID, movieID, rating));
			}
			br.close();
			return ratings;
		} catch (IOException e) {
			System.out.println("ERROR: IO Exception on movie dat input file - " + fileName);
			return null;
		}
	}

}
