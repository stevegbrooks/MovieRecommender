import java.util.Set;

public abstract class RatingsReader {
	public abstract Set<Rating> read();
	public abstract Set<User> getUsers();
}
