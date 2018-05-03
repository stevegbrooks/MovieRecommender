import java.util.Map;
import java.util.Observable;
import java.util.Observer;
/**
 * A MovieNameMapper does one thing. It reads a file and returns
 * a Map with Movie as the key and the name of the movie as the value.
 * 
 * Its also observable. This allows the MovieNamer to update the classes
 * that observe it (eg Movie) with a name.
 * @author sgb
 *
 */
public abstract class MovieNamer extends Observable{
	
	public abstract Map<Movie, String> nameMovies();
	
	public abstract void addObserver(Observer obs);
	
	public abstract void deleteObserver(Observer obs);
	
	public abstract boolean hasChanged();
	
	public abstract void notifyObservers();
	
	public abstract void notifyObservers(Object obj);
	
	
}
