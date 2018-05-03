import java.util.Comparator;
/**
 * This comparator sorts RatingTuples based on their Movie ID# in ascending order.
 * @author sgb
 *
 */
public class RatingTupleComparator_Movie implements Comparator<RatingTuple>{

	@Override
	public int compare(RatingTuple o1, RatingTuple o2) {
		return o1.getMovie().compareTo(o2.getMovie());
	}

}
