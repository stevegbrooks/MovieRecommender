import java.util.Map;
/**
 * A MovieNameMapper does one thing. It reads a file and returns
 * a Map with Movie as the key and the name of the movie as the value.
 * @author sgb
 *
 */
public abstract class MovieNameMapper {
	
	public abstract Map<Movie, String> map();

}
