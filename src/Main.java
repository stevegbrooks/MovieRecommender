
public class Main {

	public static void main(String[] args) {
		while (true) {
			try {
				UserInterface ui = new UserInterface("ratingsSmallTest.xlsx", "movies.sdf");
				ui.menu();
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
		}
		
	}

}
