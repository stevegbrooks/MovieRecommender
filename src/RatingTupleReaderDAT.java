import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * This concrete RatingTupleReader reads DAT files.
 * @author sgb
 *
 */
public class RatingTupleReaderDAT extends RatingTupleReader {
	private String fileName;
	private Log log;
	
	public RatingTupleReaderDAT(String fileName) {
		this.fileName = fileName;
		log = Log.getInstance();
		log.print("RatingDATReader::RatingDATReader instantiated.");
		log.close();
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
				
				ratings.add(new RatingTuple(new User(currentUserID), 
						new Movie(currentMovieID), currentRating));
			}
			br.close();
			log.print("RatingDATReader::Ratings file successfully read.");
			log.close();
			return ratings;
		} catch (IOException e) {
			System.out.println("ERROR: IO Exception on movie dat input file - " + fileName);
			return null;
		}
	}

}
