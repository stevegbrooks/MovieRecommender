/**
 * This concrete factory class has the responsibility of 
 * determining which MovieNameMapper class to create, as well as creating it. 
 * 
 * It also throws an exception if the file type is not supported.
 * 
 * @author sgb
 *
 */
public class MovieNameMapperFactory {
	private MovieNameMapper mapper;
	private ReaderUtility utils;
	
	public MovieNameMapperFactory() {
		utils = new ReaderUtility();
	}
	
	public MovieNameMapper createMapper(String movieNameFile) {
		//determine the file type
		String fileType = utils.parseWord("\\.[a-zA-Z0-9]*$", movieNameFile);
		//build the correct reader
		if (fileType.equalsIgnoreCase(".dat")) {
			mapper = new MovieNameDATMapper(movieNameFile);
		} else {
			throw new IllegalArgumentException("Sorry, but only '.dat' files are currently supported.");
		}
		return mapper;
	}
}
