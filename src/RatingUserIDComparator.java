import java.util.Comparator;

public class RatingUserIDComparator implements Comparator<RatingTuple> {

	@Override
	public int compare(RatingTuple o1, RatingTuple o2) {
		return o1.getUser().compareTo(o2.getUser());
	}

}
