import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * This class provides helper functions for other Reader classes. 
 * Generally helps with parsing data.
 * 
 * @author sgb
 */
public class ReaderUtility {

	/**
	 * This method attempts to parse a String
	 * as Double, and can account for missing data
	 * by coding it as '99'.
	 * @param text line to be parsed into Double
	 * @return a Double
	 */
	public Double tryParseAsDouble(String text) {
		final double MISSING = 99;
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException e) {
			return MISSING;
		}
	}
	/**
	 * This method converts an Array of type String
	 * into an Array of type int. Used for parsing data.
	 * @param lineArray an Array of type String
	 * @return an Array of type int.
	 */
	public int[] convertToIntArray(String[] lineArray) {
		int[] intArray = new int[lineArray.length];
		for (int i = 0; i < lineArray.length; i++) {
			intArray[i] = Integer.parseInt(lineArray[i]);
		}
		return intArray;
	}
	/**
	 * This method returns an Array of type String split on quotations
	 * based on the input from a String line.
	 * @param line the String with quotations to be split
	 * @return an Array of type String split on quotation marks.
	 */
	public String[] removeQuotations(String line) {
		String[] noQuotesArr = line.split("\"");
		return noQuotesArr;
	}
	/**
	 * This method finds a number of any length in a String 
	 * supplied by the user and returns it as a String.
	 * @param line that has a number in it
	 * @return the number as a String.
	 */
	public String parseNumberAsString(String line) {
		String match = new String();
		Matcher matcher = Pattern.compile("[0-9]+").matcher(line);
		if (matcher.find()) {
			match = matcher.group();
		}
		return match;
	}
	/**
	 * This method uses a set RegEx to parse dash delimited dates
	 * @param line a line containing a date
	 * @return the date
	 */
	public String parseDate(String line) {
		String match = new String();
		Matcher matcher = Pattern.compile("[0-9]*\\-[0-9]*\\-[0-9]*").matcher(line);
		if (matcher.find()) {
			match = matcher.group();
		}
		return match;
	}
	/**
	 * This method takes in a RegEx and a line of text and returns 
	 * a match if the RegEx matches something in the text.
	 * @param regex a regular expression
	 * @param line a line of text
	 * @return the match
	 */
	public String parseWord(String regex, String line) {
		String match = new String();
		Matcher matcher = Pattern.compile(regex).matcher(line);
		if (matcher.find()) {
			match = matcher.group();
		}
		return match;
	}

}
