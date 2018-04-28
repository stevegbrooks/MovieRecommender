import java.util.ArrayList;
import java.util.List;
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
		System.out.println("===========================================================");
	}
	
	public void menu() {
		System.out.println("Welcome to the Movie Ratings Predictor.");
		System.out.println("===========================================================");
		in = new Scanner(System.in);
		boolean quit = false;
		while (!quit) {
			System.out.println("Enter 1 to get a predicted rating for a given user and movie.");
			System.out.println("Enter 2 to get a list of recommended movies for a user.");
			System.out.println("Enter 3 to quit.");
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
					handleGetMovieRecommendations();
					break;
				case 3:
					quit = true;
					break;
			}
		}
		in.close();
	}
	
	private int getMenuChoice() {
		String input = in.nextLine();
		return Integer.parseInt(input);
	}
	
	private void handleGetPrediction() {
		Double prediction = null;
		Integer neighborhoodSize = null;
		User userOfInterest = null;
		Movie movieOfInterest = null;
		while (true) {
			System.out.println("Enter User ID#:");
			String userInput = in.nextLine();
			System.out.println("Enter Movie ID#:");
			String movieInput = in.nextLine();
			System.out.println("Enter neighborhood size for prediction algorithm:");
			String size = in.nextLine();
			
			try {
				neighborhoodSize = Integer.parseInt(size);
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR: Please enter an integer for 'neighborhood size'!");
				System.out.println("===========================================================");
				break;
			}
			
			userOfInterest = getUserObjectFromString(userInput);
			movieOfInterest = getMovieObjectFromString(movieInput);
			
			if (userOfInterest == null) {
				System.out.println("ERROR: User was not found. Returning to menu.");
				System.out.println("===========================================================");
				break;
			} else if (movieOfInterest == null) {
				System.out.println("ERROR: Movie was not found. Returning to menu.");
				System.out.println("===========================================================");
				break;
			} else {
				try {
					boolean isInternalCall = false;
					prediction = mrp2.getPrediction(userOfInterest, movieOfInterest, neighborhoodSize, isInternalCall);
				} catch (IllegalArgumentException iae) {
					System.out.println(iae.getMessage());
					System.out.println("===========================================================");
					break;
				}
				if (prediction != null) {
					break;
				} else {
					System.out.println("ERROR: Prediction algorithm failed. Returning to menu.");
					System.out.println("===========================================================");
					break;
				}
			}
		}
		if (prediction != null) {
			System.out.println("The user is predicted to give a " + (double) Math.round(prediction * 100)/100 + " to the movie.");
			System.out.println("===========================================================");
		}
	}
	
	private void handleGetMovieRecommendations() {
		List<Recommendation> recommendations = new ArrayList<>();
		Integer threshold = null;
		User userOfInterest = null;
		while (true) {
			System.out.println("Enter User ID#:");
			String userInput = in.nextLine();
			System.out.println("Enter threshold:");
			String thresholdString = in.nextLine();
			
			try {
				threshold = Integer.parseInt(thresholdString);
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR: Please enter an integer for 'threshold'!");
				System.out.println("===========================================================");
				break;
			}
			
			userOfInterest = getUserObjectFromString(userInput);
			
			if (userOfInterest == null) {
				System.out.println("ERROR: Can't find that user! Returning to menu.");
				System.out.println("===========================================================");
				break;
			} else {
				try {
					recommendations = mrp2.getMovieRecommendations(userOfInterest, threshold);
				} catch (IllegalArgumentException iae) {
					System.out.println(iae.getMessage());
					System.out.println("===========================================================");
					break;
				}
				if (recommendations != null) {
					break;
				} else {
					System.out.println("ERROR: Recommendations algorithm failed. Returning to menu.");
					System.out.println("===========================================================");
					break;
				}
			}
		}
		if (recommendations != null && userOfInterest != null && threshold != null) {
			System.out.println("Here are the movies that we recommend:");
			System.out.println("===========================================================");
			for (Recommendation recommendation : recommendations) {
				System.out.println(recommendation.toString());
			}
			System.out.println("===========================================================");
		}
	}
	
	private User getUserObjectFromString(String userInput) {
		User output = null;
		Integer userID = null;
		try {
			userID = Integer.parseInt(userInput);
		} catch (NumberFormatException nfe) {
			System.out.println("Please enter an integer for User ID#");
		}
		output = userIDToUserMap.get(userID);
		return output;
	}
	
	private Movie getMovieObjectFromString(String movieInput) {
		Movie output = null;
		Integer movieID = null;
		try {
			movieID = Integer.parseInt(movieInput);
		} catch (NumberFormatException nfe) {
			System.out.println("Please enter an integer for Movie ID#");
		}
		output = movieIDToMovieMap.get(movieID);
		return output;
	}
}
