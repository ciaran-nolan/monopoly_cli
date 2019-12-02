# sw_engineering_monopoly
## Software Engineering Final Project Description
## Team members: Robert Keenan & Ciaran Nolan
## Team ID: 11

* The aim of this assignment is to create a fully functional version of the board game Monopoly using Java
* This repository will contain all the functional code required to run this game
* The objective of this variant of Monopoly is to own as much land as possible and be the richest person in the game

### Structure of the Repository
* The repository is split into different packages as listed below:
	* ie.ucd.game : Contains all Java source files and classes including our main
	* ie.ucd.gameConfigurations : Contains all of the configuration files for cards, special and ownable squares.
	* ie.ucd.userInterface : Contains all of the relevant code for the user interface or GUI to play the game. 
	
### How to play the game

### Board Structure

* We will aim to implement each of the squares in terms of name, value, rent, mortgage by using the .properties files inside of the ie.ucd.gameConfigurations. This means that anyone can use our source code and put in their own custom version of Monopoly like the US, Irish and UK versions. 
* We will split this into Utilities, Properties/Streets and then there is Chance/Community Squares.
* Each configuration file has a list of different objects of the type with such data fields such as location, value and description. These data fields are used when parsing the data into a List of that type.

### Class Structure

* We have another of class structures which relate to different parts of the project's design:
	* Card Classes:
	    * Chance : Implements Chance cards from the chance configuration file which are implemented whenb the player lands on a chance square
	    * Community Chest : Implements the actions of a Community chest card. 
	    * The reason we have not combined these classes for functionality is because some of the Community Chest and Chance cards are slightly different in the way they act and present the Player with some different choices to be made
	* Square Classes:
	    * Square defines any square type on the board. It has a number of child classes: Special, CanOwn (Property, PublicSquare(Train, Utility))
		
### Notes on Teamwork
* Ciaran and I initially set up a number of ways to communicate and make sure that we were working on different aspects of the project that would lead to minimal interference in pushed code.
* We started off with a number of white board meetings in the Library to decide on our class structure, class variables and initial class methods for each.
* We use Trello to assign ourselves debugging tasks, merge requests from other branches (also assigned through Gitlab) and setting out tasks for each of us.
* Through Trello, we can gain an insight into what each other is doing but also ensures that we are not doing the same tasks twice.
* We also consistently meet up in the Engineering building to see what each other has done and how we can progress with the project as a whole.
* Once we were finished with a class, we would meet up and review the code. From here, we could make judgements on areas where we could improve or optimise the speed of our program. 



		
