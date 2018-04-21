HW5 Readme

# How my design changed from Milestone 1 to Milestone 2:

When I initially made the Class Diagram for Milestone 1, I had not anticipated the need for retrieving Movie names from the 'movies.dat' file. In order to optimize runtime, I wanted to have the Reader class output a Set<Movie>, but I had already made an abstract reader class with a List<E> return type for its "read()" method. 

So the change is that there is another Reader class, but it does not extend my Reader abstract class.

The biggest change to my design was the creation of a DataManager class, which process the data from the Reader classes. I had not anticipated the usefulness of this design.

My DataManager class creates HashMap<User, Double> (all the ratings that a movie received) and HashMap<Movie, Double> (all the ratings that a user made), and then sets those as attributes for each Movie and User object, respectively.

The DataManager then separates the "MovieRatingPredictor" from the Reader classes, and provides all upstream classes with the necessary data layers for making predictions/recommendations.



