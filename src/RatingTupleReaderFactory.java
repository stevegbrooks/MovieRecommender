
public class RatingTupleReaderFactory {
	private RatingTupleReader reader;
	private ReaderUtility utils;
	
	public RatingTupleReaderFactory() {
		utils = new ReaderUtility();
	}
	
	public RatingTupleReader createReader(String ratingsFileName) {
		//determine the file type
		String fileType = utils.parseWord("\\.[a-zA-Z0-9]*$", ratingsFileName);
		//build the correct reader
		if (fileType.equalsIgnoreCase(".dat")) {
			reader = new RatingTupleDATReader(ratingsFileName);
		}
		return reader;
	}

}
