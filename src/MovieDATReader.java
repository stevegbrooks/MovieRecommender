import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieDATReader extends MovieReader {
	private String fileName;
	
	public MovieDATReader(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public List<Movie> read() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			List<Movie> movies = new ArrayList<Movie>();
			String line = new String();
			while ((line = br.readLine()) != null) {
				String[] ls = line.split("::");
				int movieID = Integer.parseInt(ls[0]);
				String movieName = ls[1];
				movies.add(new Movie(movieID, movieName));
			}
			br.close();
			return movies;
		} catch (IOException e) {
			System.out.println("ERROR: IO Exception on movie dat input file - " + fileName);
			return null;
		}
	}

}
