import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RatingDATReader extends Reader<RatingTuple> {
	private String fileName;
	private Logger log;
	
	public RatingDATReader(String fileName) {
		this.fileName = fileName;
		log = Logger.getInstance();
		log.setMessage("RatingDATReader::RatingDATReader instantiated.");
		log.printToLog();
	}
	
	@Override
	public List<RatingTuple> read() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			List<RatingTuple> ratings = new ArrayList<RatingTuple>();
			
			String line = new String();
			while ((line = br.readLine()) != null) {
				
				String[] ls = line.split("::");
				int currentUserID = Integer.parseInt(ls[0]);
				int currentMovieID = Integer.parseInt(ls[1]);
				double currentRating = Double.parseDouble(ls[2]);
				
				ratings.add(new RatingTuple(new User(currentUserID), new Movie(currentMovieID), currentRating));
			}
			br.close();
			log.setMessage("RatingDATReader::Ratings file successfully read.");
			log.printToLog();
			return ratings;
		} catch (IOException e) {
			System.out.println("ERROR: IO Exception on movie dat input file - " + fileName);
			return null;
		}
	}

}
