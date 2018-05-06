/**
 * This concrete factory class has the responsibility of 
 * determining which RatingTupleReader class to create, as well as creating it. 
 * 
 * It also throws an exception if the file type is not supported.
 * 
 * @author sgb
 *
 */
public class RatingTupleReaderFactory {
	private RatingTupleReader reader;
	private ParsingUtilities utils;
	private Log log;
	
	public RatingTupleReaderFactory() {
		utils = new ParsingUtilities();
		log = Log.getInstance();
		log.print("RatingTupleFactory::RatingTupleFactory instantiated.");
		log.close();
	}
	
	public RatingTupleReader createReader(String ratingsFileName) {
		String fileType = utils.parseWord("\\.[a-zA-Z0-9]*$", ratingsFileName);

		if (fileType.equalsIgnoreCase(".dat")) {
			reader = new RatingTupleReaderDAT(ratingsFileName);
			log.print("RatingTupleFactory::RatingTupleFactory created a DAT RatingTupleReader.");
			log.close();
		} else {
			throw new IllegalArgumentException("Sorry, but only '.dat' files are currently supported.");
		}
		return reader;
	}

}
