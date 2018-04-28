HW5 README

# Implemented Design Patterns

##Three-Tier Architecture

This project uses a Three-Tier Architecture. The Data tier consists of the RatingTupleReaders, the RatingTupleReaderFactory, the MovieRatingsDataStore, the MovieNameMappers, and the ReaderUtility classes, as well as the POJOs that those classes use (RatingTuple, User, and Movie).

The Controller tier consists of the MovieRatingsPredictor, and the SimilarityEngine, as well as the POJOs that those classes use (Neighbor and Recommendation).

The Presentation tier consists of the UserInterface class, which is accessed through the Main class. The Presentation tier only needs to know about the Controller tier, and the Controller tier only needs to know about the Data tier, thereby defining this projects' three-tier architecture.

## Concrete Factory

I implemented a concrete factory pattern using the 'RatingTupleReaderFactory' class. This class is called upon by the 'MovieRatingsDataStore', which acts as the Controller class between the Data and Presentation layers in the Three-Tier Architecture of this project.

The user of the program will pass in a file path which contains the movie ratings data. The 'MovieRatingsDataStore' instantiates a 'RatingTupleReaderFactory' and calls the 'createReader()' method, passing in the file path string. The Factory then determines the correct Reader to pass back to the DataStore based on the file type.

The 'RatingTupleReaderFactory' creates a concrete instantiation of the RatingTupleReader class based on file type. Currently, there is only one concrete class that extends the abstract RatingTupleReader class - RatingTupleDATReader. However, as more functionality is added to the program (to accept .csv or .xlsx files, for example), then one would simply need to create the necessary concrete Readers, and add a line to the Factory's 'createReader()' method, thereby allowing for independence between the collections of data that the rest of the program relies on and the file reading.

## Abstract Factory

This class implements an Abstract Factory pattern by virtue of the separation between the Presentation, Controller, and Data tiers of the Three-Tier Architecture. 

The Presentation tier passes along file paths for the ratings data and the movie names data through to the Controller tier (MovieRatingsPredictor). The Controller tier then passes those Strings along to the DataStore, which instantiates the necessary classes for it to create the data layers that the Controller tier relies on. 

In this way, there is no concrete factory. Rather, the factory is abstract because it exists in the separation between the Controller and the Data.  All the Controller tier knows about are the names of the data files that the Presentation tier has passed to it. It then passes these Strings along to the Data tier, which instantiates all the dependencies allowing the Controller to be blissfully unaware of the work being done - it receives the data layers it needs (Set<User>, Set<Movie>, etc...) without having to worry about specifying anything except 'new MovieRatingsDataStore()' and then calling the DataStore's methods.

