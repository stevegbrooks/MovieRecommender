HW5 README

#Instructions for using this program

Open the Main class, and enter the file names for the movie ratings data (as the first argument to the UserInterface constructor) and the movie names data (as the second argument).

This data should be structured in a specific way, which is the structure of the data as given to us by the 'MovieLens' data files.

Then run Main.java and follow the instructions in the console.

# Design Patterns

## Three-Tier Architecture

This project uses a Three-Tier Architecture. The Data tier consists of the RatingTupleReader, the RatingTupleReaderFactory, the DataStore, the MovieNamer, and the ParsingUtilities classes, as well as the POJOs that those classes use and have (RatingTuple, User, and Movie).

The Controller tier consists of the Predictor, the SimilarityEngine, as well as the POJOs that those classes use and have (Neighbor and Recommendation).

The Presentation tier consists of the UserInterface class, which is accessed through the Main class. The UserInterface only needs to know about the Predictor's interface, and the Predictor only needs to know about the DataStore's interface.

## Factory Pattern

I implemented a concrete factory pattern using the 'RatingTupleReaderFactory' class. This class is called upon by the 'DataStore', which acts as the Controller class between the Data and Presentation layers in the Three-Tier Architecture of this project. Perspective is important here, as this could be considered an Abstract Factory pattern as well. I consider it a Concrete Factory pattern from the perspective of the DataStore, which is the client that uses the Factory.

The user of the program will pass in a file path which contains the movie ratings data. The 'DataStore' instantiates a 'RatingTupleReaderFactory' and calls the 'createReader()' method, passing in the file path string. The Factory then determines the correct Reader to pass back to the DataStore based on the file type.

The 'RatingTupleReaderFactory' creates a concrete instantiation of the RatingTupleReader class based on file type. Currently, there is only one concrete class that extends the abstract RatingTupleReader class - RatingTupleDATReader. However, as more functionality is added to the program (to accept .csv or .xlsx files, for example), then one would simply need to create the necessary concrete Readers, and add a line to the Factory's 'createReader()' method.

## Observer Pattern

I utilized the Observer pattern through the use of Java's built-in Observable class and Observer interface. 

The Movie class implements the Observer interfact, and its 'update()' method changes its 'movieName' instance variable. The MovieNamer class extends Observable, and calls 'notifyObservers()' when it runs its main function, map(). This is all controlled by the DataStore class, which creates the MovieNamer, and adds each Movie as an observer of MovieNamer while it is processing the data from RatingTupleReader.

## Singleton Pattern

There is a Logger class which creates a log in the project's directory and records the steps that the program is taking as it runs.
