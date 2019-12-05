/**
 * Class to read from properties file 
 * References: BoardReader example, cs moodle 
 * 
 */

package ie.ucd.game;
import java.io.*;
import java.util.*;

import ie.ucd.cards.Chance;
import ie.ucd.cards.CommunityChest;
import ie.ucd.cards.TitleDeed;
import ie.ucd.squares.Property;
import ie.ucd.squares.Special;
import ie.ucd.squares.Square;
import ie.ucd.squares.Train;
import ie.ucd.squares.Utility;


/**
 * This class reads the configuration for the properties, trains, utilities, special squares and cards from the configuration files 
 * in the gameConfigurations package which we built to self contain the project. 
 * It reads in all of the data, creates instances of each class from the particular configuration file and adds these to static ArrayList's 
 * As a result, the property "Park Lane" for example is created once and only once once we use the function initialiseBoard from this class. 
 * It then creates the Board structure using these ArrayLists so that when a player moves, they are on the correct square. 
 * 
 * We used the BoardReader example from CSMoodle as inspiration for this class and its implementation.
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public class Board {

	public static ArrayList<Property> properties = new ArrayList<>(22); 	//Properties list
	public static ArrayList<Train> trains = new ArrayList<>(4);				//Trains list
	public static ArrayList<Utility> utilities = new ArrayList<>(2);		//Utilities list
	public static ArrayList<Special> specialSquares = new ArrayList<>(12);	//Special Squares list
	public static ArrayList<CommunityChest> communityChests = new ArrayList<>(16);		//Community Chest list
	public static ArrayList<Chance> chances = new ArrayList<>(16);						//Chances list
	public static ArrayList<Square> board = new ArrayList<>(Collections.nCopies(40, null)); //The board itself
	
	private static Properties prop = new Properties();
	
	/**
	 * The input stream is setup for a reading in particular configuration file
	 * @param propFileName The Relative path to the configuration file from this Board.java file
	 * @return inputStream
	 */

	//set up input stream for each property file
	private static InputStream setupInputStream(String propFileName) {
		try {
			//define the input stream for the prop file
			InputStream inputStream = Board.class.getClassLoader().getResourceAsStream(propFileName);
			//check that the prop file location is valid
			prop.load(inputStream);
			return inputStream;
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		return null;
	}

	//Methods read in each square type on the monopoly board into their respective Arraylists

	/**
	 * Reads in the Properties' configurations from their respective configuration file.
	 * It then creates a new Property instance for each of these and their corresponding Title Deed card.
	 * It then puts the respective property into the static properties Array list which is used to populate the board. 
	 */
    public static void readProperties(){
    	try {
    		//define the location of the prop file
    		String propFileName = "ie/ucd/gameConfigurations/property.properties";
    		setupInputStream(propFileName);

    		//recursively loop to organize all property date into respective class variables and place them in an array list
    		for(int i=0; i<=21; i++) {
    		
    			int[] rentIntArray = Arrays.stream(prop.getProperty(("rents"+i)).split(",")).mapToInt(Integer::parseInt).toArray();
    			Property temp = new Property(Integer.parseInt(prop.getProperty(("squareNum"+i))),prop.getProperty(("squareColour"+i)),prop.getProperty(("title"+i)));
    			TitleDeed tempDeed = new TitleDeed("Title Deed", prop.getProperty(("title"+i)),0, prop.getProperty(("squareColour"+i)),Integer.parseInt((prop.getProperty(("priceBuy"+i)))),rentIntArray, Integer.parseInt(prop.getProperty(("housePrice"+i))),Integer.parseInt(prop.getProperty(("mortgage"+i))),null, temp);
    			temp.setTitleDeedCard(tempDeed);
    			properties.add(temp);
    			board.set(temp.getLocation(),temp);
    		}
    	}
    	catch (Exception e) {
    		System.out.println("Exception: " + e);
    	}
    }
    /**
	 * Reads in the Utilities' configurations from their respective configuration file.
	 * It then creates a new Utility instance for each of these and their corresponding Title Deed card.
	 * It then puts the respective utility into the static utilities Array list which is used to populate the board. 
	 */
    public static void readUtilities() {
    	try {
    		String propFileName = "ie/ucd/gameConfigurations/utilities.properties";
    		setupInputStream(propFileName);

    		//recursively read utilities
    		for(int i=0; i<=1; i++) {
    			int[] rentIntArray = Arrays.stream(prop.getProperty(("rents"+i)).split(",")).mapToInt(Integer::parseInt).toArray();
    			//Create new instance utility object
    			Utility temp = new Utility(prop.getProperty(("title"+i)), Integer.parseInt(prop.getProperty(("squareNum"+i))));
				TitleDeed tempDeed = new TitleDeed("Title Deed", prop.getProperty(("title"+i)),0,
						null, Integer.parseInt((prop.getProperty(("priceBuy"+i)))),
						rentIntArray, 0, Integer.parseInt(prop.getProperty(("mortgage"+i))),null, temp);
    			//Set the title deed card for the property
				temp.setTitleDeedCard(tempDeed);
				//Add to the arraylist
    			utilities.add(temp);
    			//Set in board arraylist
    			board.set(temp.getLocation(),temp);
    		}
    	}
    	catch (Exception e) {
			System.out.println("Exception: " + e);
    	}
	}
    /**
	 * Reads in the Special Squares' configurations from their respective configuration file.
	 * It then creates a new special square instance for each of these.
	 * It then puts the respective special square into the static specialSquares Array list which is used to populate the board. 
	 */
    public static void readSpecialSquares() {
    	try {
    		//location of prop file
    		String propFileName = "ie/ucd/gameConfigurations/specialSquares.properties";
    		setupInputStream(propFileName);

    		//loop through each special square
    		for(int i=0; i<=11; i++) {
    			Special temp = new Special(prop.getProperty(("squareName"+i)),Integer.parseInt(prop.getProperty(("squareNum"+i))),false, prop.getProperty("type"+i),Integer.parseInt(prop.getProperty("value"+i)),Square.SquareType.SPECIAL);
    			board.set(temp.getLocation(),temp);
    			specialSquares.add(temp);
    		}
    	}
    	catch (Exception e) {
			System.out.println("Exception: " + e);
    	}
    }
    /**
	 * Reads in the Community Chests' configurations from their respective configuration file.
	 * It then creates a new community chest card instance for each of these.
	 * It then puts the respective community chest card into the static communityChests Array list which is shuffled when it is populated
	 */
    public static void readCommunityChests(){
    	try {
    		//location of prop file
    		String propFileName = "ie/ucd/gameConfigurations/communityChest.properties";
    		setupInputStream(propFileName);
    	
    		for(int i=0; i<=15; i++) {
    			CommunityChest temp = new CommunityChest(prop.getProperty(("type"+i)),prop.getProperty(("card"+i)),Integer.parseInt(prop.getProperty(("value"+i))));
    			communityChests.add(temp);
    		}
    		//shuffle collection to ensure unique set each game
    		Collections.shuffle(communityChests);
    	}
    	catch (Exception e) {
			System.out.println("Exception: " + e);
    	}
    }
    
    /**
	 * Reads in the Chance cards' configurations from their respective configuration file.
	 * It then creates a new chance card instance for each of these.
	 * It then puts the respective chance card into the static chances Array list which is shuffled when it is populated
	 */
    
    public static void readChances() {
    	try {
    		//location of prop file
    		String chanceFileName = "ie/ucd/gameConfigurations/chance.properties";
    		setupInputStream(chanceFileName);

    		//loop through chances
    		for(int i=0; i<=15; i++) {
    			Chance temp = new Chance(prop.getProperty(("type"+i)),prop.getProperty(("card"+i)),Integer.parseInt(prop.getProperty(("value"+i))));
    			chances.add(temp);
    		}
    		Collections.shuffle(chances);
    	}
    	catch (Exception e) {
			System.out.println("Exception: " + e);
    	}
    }
    
    /**
	 * Reads in the Trains' configurations from their respective configuration file.
	 * It then creates a new Train instance for each of these and their corresponding Title Deed card.
	 * It then puts the respective utility into the static trains Array list which is used to populate the board. 
	 */
   
    public static void readTrains(){
    	try {
    		String trainFileName = "ie/ucd/gameConfigurations/train.properties";
    		setupInputStream(trainFileName);
    	
    		for(int i=0; i<=3; i++) {
    			int[] rentIntArray = Arrays.stream(prop.getProperty(("rents"+i)).split(",")).mapToInt(Integer::parseInt).toArray();
    			Train temp = new Train(prop.getProperty(("title"+i)), Integer.parseInt(prop.getProperty(("squareNum"+i))));
				TitleDeed tempDeed = new TitleDeed("Title Deed", prop.getProperty(("title"+i)),0, null, Integer.parseInt((prop.getProperty(("priceBuy"+i)))), rentIntArray, 0, Integer.parseInt(prop.getProperty(("mortgage"+i))),null, temp);
    			temp.setTitleDeedCard(tempDeed);
    			trains.add(temp);
    			board.set(temp.getLocation(),temp);
    		}
    	}
    	catch (Exception e) {
			System.out.println("Exception: " + e);
    	}
    }

    /**
     * This sets up the board for a game by running all of the set up methods inside of the Board.java class
     */
    public static void initialiseBoard() {
		try {
			//Runs all of the methods inside of class
			readProperties();
			readUtilities();
			readSpecialSquares();
			readCommunityChests();
			readChances();
			readTrains();
		}
		catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
	public static void clearBoard(){
		properties.clear();
		specialSquares.clear();
		utilities.clear();
		communityChests.clear();
		chances.clear();
		trains.clear();
	}
}