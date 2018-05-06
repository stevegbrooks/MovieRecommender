import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observer;
/**
 * This concrete MovieNameMapper reads DAT files.
 * @author sgb
 *
 */
public class MovieNamerDAT extends MovieNamer {
	private String fileName;
	private Log log;
	private LinkedList<Observer> observers;
	private Map<Movie, String> moviesAndNames;
	private boolean state;

	public MovieNamerDAT(String fileName) {
		this.fileName = fileName;
		moviesAndNames = new HashMap<>();
		observers = new LinkedList<>();
		state = false;

		log = Log.getInstance();
		log.print("MovieNamesDATMapper::MovieNamesDATMapper instantiated.");
		log.close();
	}

	@Override
	public Map<Movie, String> nameMovies() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			String line = new String();
			while ((line = br.readLine()) != null) {
				String[] ls = line.split("::");
				int movieID = Integer.parseInt(ls[0]);
				String movieName = ls[1];
				moviesAndNames.put(new Movie(movieID), movieName);
			}
			br.close();
			log.print("MovieNamesDATMapper::Movie names file successfully read.");
			log.close();
			state = true;
			notifyObservers();
			return moviesAndNames;
		} catch (IOException e) {
			System.out.println("ERROR: IO Exception on movie dat input file - " + fileName);
			return null;
		}
	}

	@Override
	public void addObserver(Observer obs) {
		observers.add(obs);
	}

	@Override
	public void deleteObserver(Observer obs) {
		observers.remove(obs);
	}

	@Override
	public boolean hasChanged() {
		return state;
	}

	@Override
	public void notifyObservers(Object obj) {
		if (hasChanged()) {
			for (Observer obs : observers) {
				obs.update(this, obj);
			}
		}
	}

	@Override
	public void notifyObservers() {
		if (hasChanged()) {
			for (Observer obs : observers) {
				obs.update(this, moviesAndNames);
			}
		}
	}
}

