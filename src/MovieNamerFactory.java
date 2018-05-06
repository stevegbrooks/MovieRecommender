/**
 * This concrete factory class has the responsibility of 
 * determining which MovieNameMapper class to create, as well as creating it. 
 * 
 * It also throws an exception if the file type is not supported.
 * 
 * @author sgb
 *
 */
public class MovieNamerFactory {
	private MovieNamer mapper;
	private ParsingUtilities utils;
	private Log log;
	
	public MovieNamerFactory() {
		utils = new ParsingUtilities();
		log = Log.getInstance();
		log.print("MovieNamerFactory::MovieNamerFactory instantiated.");
		log.close();
	}
	
	public MovieNamer createNamer(String movieNameFile) {
		
		String fileType = utils.parseWord("\\.[a-zA-Z0-9]*$", movieNameFile);

		if (fileType.equalsIgnoreCase(".dat")) {
			mapper = new MovieNamerDAT(movieNameFile);
			log.print("MovieNamerFactory::MovieNamerFactory created a DAT version of the MovieNamer.");
			log.close();
		} else {
			throw new IllegalArgumentException("Sorry, but only '.dat' files are currently supported.");
		}
		return mapper;
	}
}
