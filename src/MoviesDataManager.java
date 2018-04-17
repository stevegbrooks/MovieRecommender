import java.util.List;
/**
 * This class will create HashMap's for each of the movies 
 * coming out of the MovieReader.
 * 
 * The HashMap's key-value pair will be Movie and Integer in order to 
 * represent all the ratings that the movie has received. 
 * 
 * @author sgb
 *
 */
public class MoviesDataManager implements DataManager {
	private MovieReader movieReader;
	private List<Movie> movies;
	
	public MoviesDataManager(String fileName) {
		movieReader = new MovieDATReader(fileName);
		movies = movieReader.read();
		createDataLayer();
	}
	
	@Override
	public void createDataLayer() {
		// TODO Auto-generated method stub
	}

}
