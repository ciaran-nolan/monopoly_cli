# sw_engineering_monopoly
## Software Engineering Final Project Description
## Team members: Robert Keenan & Ciaran Nolan
## Team ID: 11

* The aim of this assignment is to create a fully functional version of the board game Monopoly using Java
* This repository will contain all the functional code required to run this game
* The objective of this variant of Monopoly is to own as much land as possible and be the richest person in the game
* Please see our JavaDoc in our repository for more in-depth details into the structure of our project and the implementation for all methods and classes
### Structure of the Repository
* The repository is firstly split into 2 main folders:
	* source: Contains all of the source code in a number of packages.
	* tests: Contains all of our unit tests for each one of our source code classes in the source/ folder.
#### Source Code 
* Each one of these 2 main folders is split into a number of packages which are described below in terms of why we structured them that way:
	* **cards: The *cards* package contains all of the relevant cards to play the game. These are the cards that will be physically used in the game and the Card parent class**
		* **Card.java**: Card abstract class (parent class).
		* **Chance.java**: The implementation of Chance cards.
		* **CommunityChest.java**: The implementation of Community Chest cards.
		* **TitleDeed.java**: The Title Deed cards that are associated with a site or square on the board that can be bought by a player. Players trade and buy/sell Title Deed cards rather than the Square on the board itself. This means that we have implemented a method similar to how Monopoly is played in reality.
	
	* **game: The *game* package contains all of the relevant classes for the game physically such as the Main Game file, the board and other classes as shown below**
		* **Board.java**: This contains all of the source code to set up the board. It reads the configuration of the board from the gameConfiguration files and initialises the board. 
		* **Dice.java**: The dice file which contains all the functionality for the 2 Die used in the game of Monopoly such as rolling the dice, counting double rolls, etc.
		* **Game.java**: This is our Main file where the game is played
		* **Jail.java**: Handles all of a player's interactions with jail
		* **Player.java**: A large class which handles all of a player's interactions with the game such as paying rent, reducing/adding money to their account, moving around the board and all of the operations involved in bankruptcy
	
	* **gameConfigurations**: This file is unique as we import all of the data for properties, train stations, utilities, chance cards, community chest cards and special squares. They are all in a set structure so somebody could use the American version of Monopoly's properties with our code and it would work provided the structure of the configuration files was the same. We did this to improve the extensibility of our design.
	
	* **operations: This package contains a number of operations files which we thought would be best kept seperate. These include methods that are checking various outputs and class variables, Input/Output functionality and a class for transactions between players**
		* **Checks.java**: A number of methods that are used to check various aspects of the game such as players, distribution of houses and whether you own all properties of a certain colour for example.
		* **InputOutput.java**: We found we were repeating a lot of the same input and output functionality so we made a class out of a number of methods such as yes no inputs and integer menus
		* **Transactions.java**: Covers a wide array of Transactions between players
	* **squares: This covers all of the squares that will be on the board and their attributes on the board such as their location or name/description**
		* **CanOwn.java**: A CanOwn object is a Square on the board that can be owned such as a Property, Train station or Utility
		* **Property.java**: A square which can have houses and hotels built on it
		* **Special.java**: A special square is one such as Free Parking, Tax, Income, Chance or Commnunity Chest card square, Jail or Go
		* **Square.java**: The overall parent class to all squares
		* **Train.java**: Train station class
		* **Utility.java**: Utility class

#### Test Code
* The test code is stored in a separate folder (tests) at the project level (it is not in the source folder). 
* We proposed this as we could keep our test and source code separate for when we were examining coverage analysis in Eclipse and also when creating the JAR file
* The tests folder contains the same 4 packages as our source code and this was done as we could keep any methods and variables **package private** in our source code and not need to make them public just for testing.
* This is desirable as you don't want the tests to interfere or jeopardise the integrity of your code structure
#### UML Diagrams 
* UML Diagrams are located in the folder at the top level of the project called "UML Diagrams". Inside of this folder, you can find the following diagrams:
	* Class Diagram: For the whole structure of our code
	* Sequence Diagram: FIXME
	* Use Case Diagram: FIXME
#### Reason for Configuration files
* We implemented configuration files in a set format or structure as we wanted our code and project to be as transferable as possible so that a user could perhaps use their own configuration files for their own custom version of Monopoly. This could include our code being used for the Irish, UK and US versions of Monopoly with only a few simple changes needed. We used this as a motivation to demonstrate the extensibility and user-friendliness of our code base. 



### How to play the game

		
### Teamwork
* Ciaran and I initially set up a number of ways to communicate and make sure that we were working on different aspects of the project that would lead to minimal interference in pushed code.
* We started off with a number of white board meetings in the Library to decide on our class structure, class variables and initial class methods for each.
* We use Trello to assign ourselves debugging tasks, merge requests from other branches (also assigned through Gitlab) and setting out tasks for each of us.
* Through Trello, we can gain an insight into what each other is doing but also ensures that we are not doing the same tasks twice.
* We also consistently meet up in the Engineering building to see what each other has done and how we can progress with the project as a whole.
* Once we were finished with a class, we would meet up and review the code. From here, we could make judgements on areas where we could improve or optimise the speed of our program. 



		
