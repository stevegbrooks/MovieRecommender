import java.util.Map;
import java.util.Scanner;

public class UserInterface {
	private MovieRatingsPredictor2 mrp2;
	private Scanner in;
	private Map<Integer, User> userIDToUserMap;
	private Map<Integer, Movie> movieIDToMovieMap;
	private Logger log;
	
	public UserInterface(String ratingsFileName, String movieNamesFileName) {
		System.out.println("Please be patient - reading files into memory...");
		mrp2 = new MovieRatingsPredictor2(ratingsFileName, movieNamesFileName);
		userIDToUserMap = mrp2.getUserIDToUserMap();
		movieIDToMovieMap = mrp2.getMovieIDToMovieMap();
		log = Logger.getInstance();
		log.setMessage("UserInterface::UserInterface instantiated.");
		log.printToLog();
	}
	
	public void getMenu() {
		System.out.println("Welcome to the Movie Ratings Predictor.");
		System.out.println("Enter 1 to get a predicted rating for a given user and movie.");
		System.out.println("Enter 2 to get a list of recommended movies for a user.");
		System.out.println("Enter 3 to quit.");
		in = new Scanner(System.in);
		boolean quit = false;
		while (!quit) {
			int menuChoice;
			try {
				menuChoice = getMenuChoice();
				if (menuChoice < 1 || menuChoice > 3) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				System.out.println("ERROR: please make a valid menu choice (1, 2, or 3)");
				continue;
			}
			switch (menuChoice) {
				case 1:
					handleGetPrediction();
					break;
				case 2:
				case 3:
					quit = true;
					break;
			}
		}
		System.out.println("Goodbye");
		in.close();
	}
	
	private int getMenuChoice() {
		String input = in.nextLine();
		return Integer.parseInt(input);
	}
	
	private void handleGetPrediction() {
		Double prediction = null;
		while(true) {
			System.out.println("Enter User ID#:");
			String userInput = in.nextLine();
			System.out.println("Enter the name of a movie:");
			String movieInput = in.nextLine();
			System.out.println("Enter neighborhood size for prediction algorithm:");
			String size = in.nextLine();
			int neighborhoodSize = Integer.parseInt(size);
			
			User userOfInterest = getUserObjectFromString(userInput);
			Movie movieOfInterest = getMovieObjectFromString(movieInput);
			
			if (userOfInterest == null || movieOfInterest == null) {
				System.out.println("ERROR: Invalid arguments to prediction algorithm. "
						+ "Please try again.");
			} else {
				try {
					prediction = mrp2.getPrediction(userOfInterest, movieOfInterest, neighborhoodSize);
				} catch (IllegalArgumentException iae) {
					System.out.println(iae.getMessage());
				}
				if (prediction != null) {
					break;
				} else {
					System.out.println("ERROR: Prediction algorithm failed. "
							+ "Please try again.");
				}
			}
		}
		System.out.println("The user is predicted to give a " + (double) Math.round(prediction * 100)/100 + " to the movie.");
	}
	
	private User getUserObjectFromString(String userInput) {
		User output = null;
		int userID = Integer.parseInt(userInput);
		output = userIDToUserMap.get(userID);
		return output;
	}
	
	private Movie getMovieObjectFromString(String movieInput) {
		Movie output = null;
		int movieID = Integer.parseInt(movieInput);
		output = movieIDToMovieMap.get(movieID);
		return output;
	}
}
