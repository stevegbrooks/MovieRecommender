import java.util.Comparator;
/**
 * This comparator sorts a List of RatingTuples by User, which means
 * that the List gets sorted ascending by user ID#.
 * @author sgb
 *
 */
public class RatingUserIDComparator implements Comparator<RatingTuple> {

	@Override
	public int compare(RatingTuple o1, RatingTuple o2) {
		return o1.getUser().compareTo(o2.getUser());
	}

}
