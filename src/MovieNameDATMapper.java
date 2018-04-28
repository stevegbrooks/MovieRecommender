import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * This concrete MovieNameMapper reads DAT files.
 * @author sgb
 *
 */
public class MovieNameDATMapper extends MovieNameMapper {
	private String fileName;
	private Logger log;
	
	public MovieNameDATMapper(String fileName) {
		this.fileName = fileName;
		log = Logger.getInstance();
		log.setMessage("MovieNamesDATMapper::MovieNamesDATMapper instantiated.");
		log.printToLog();
	}

	@Override
	public Map<Movie, String> map() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			Map<Movie, String> movies = new HashMap<>();
			String line = new String();
			while ((line = br.readLine()) != null) {
				String[] ls = line.split("::");
				int movieID = Integer.parseInt(ls[0]);
				String movieName = ls[1];
				movies.put(new Movie(movieID), movieName);
			}
			br.close();
			log.setMessage("MovieNamesDATMapper::Movie names file successfully read.");
			log.printToLog();
			return movies;
		} catch (IOException e) {
			System.out.println("ERROR: IO Exception on movie dat input file - " + fileName);
			return null;
		}
	}
}
