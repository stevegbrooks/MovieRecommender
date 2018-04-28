import java.util.List;
/**
 * A RatingTupleReader does one thing. It reads a file and returns
 * a List of type RatingTuple.
 * @author sgb
 *
 */
public abstract class RatingTupleReader {
	
	public abstract List<RatingTuple> read();

}
