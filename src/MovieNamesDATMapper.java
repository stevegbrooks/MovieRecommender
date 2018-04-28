import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MovieNamesDATMapper {
	private String fileName;
	private Logger log;
	
	public MovieNamesDATMapper(String fileName) {
		this.fileName = fileName;
		log = Logger.getInstance();
		log.setMessage("MovieNamesDATMapper::MovieNamesDATMapper instantiated.");
		log.printToLog();
	}

	public Map<Movie, String> read() {
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