/**
 * Class to read from properties file 
 * References: BoardReader example, cs moodle 
 * 
 */

package ie.ucd.game;
import java.io.*;
import java.util.*;



public class Board {

	public static ArrayList<Property> properties = new ArrayList<>(22);
	static ArrayList<CommunityChest> communityChests = new ArrayList<>();
	static ArrayList<Chance> chances = new ArrayList<>();
	public static ArrayList<Square> board = new ArrayList<>(Collections.nCopies(40, null));
	//define properties list to hold the .properties file
	private static Properties prop = new Properties();

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

	//Methods read in each square type on the monopoly board into their respective arraylists

	//read properties
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
    
    public static void readUtilities() {
    	try {
    		String propFileName = "ie/ucd/gameConfigurations/utilities.properties";
    		InputStream inputStream = setupInputStream(propFileName);

    		//recursively read utilities
    		for(int i=0; i<=1; i++) {
    			int[] rentIntArray = Arrays.stream(prop.getProperty(("rents"+i)).split(",")).mapToInt(Integer::parseInt).toArray();
    			Utility temp = new Utility(prop.getProperty(("title"+i)), Integer.parseInt(prop.getProperty(("squareNum"+i))));
				TitleDeed tempDeed = new TitleDeed("Title Deed", prop.getProperty(("title"+i)),0,
						null, Integer.parseInt((prop.getProperty(("priceBuy"+i)))),
						rentIntArray, 0, Integer.parseInt(prop.getProperty(("mortgage"+i))),null, temp);
    			temp.setTitleDeedCard(tempDeed);
    			board.set(temp.getLocation(),temp);
    		}
    	}
    	catch (Exception e) {
			System.out.println("Exception: " + e);
    	}
	}
    
    public static void readSpecialSquares() {
    	try {
    		//location of prop file
    		String propFileName = "ie/ucd/gameConfigurations/specialSquares.properties";
    		setupInputStream(propFileName);

    		//loop through each special square
    		for(int i=0; i<=11; i++) {
    			Special temp = new Special(prop.getProperty(("squareName"+i)),Integer.parseInt(prop.getProperty(("squareNum"+i))),false, prop.getProperty("type"+i),Integer.parseInt(prop.getProperty("value"+i)),Square.SquareType.SPECIAL);
    			board.set(temp.getLocation(),temp);
    		}
    	}
    	catch (Exception e) {
			System.out.println("Exception: " + e);
    	}
    }
    
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
   
    public static void readTrains(){
    	try {
    		String trainFileName = "ie/ucd/gameConfigurations/train.properties";
    		setupInputStream(trainFileName);
    	
    		for(int i=0; i<=3; i++) {
    			int[] rentIntArray = Arrays.stream(prop.getProperty(("rents"+i)).split(",")).mapToInt(Integer::parseInt).toArray();
    			Train temp = new Train(prop.getProperty(("title"+i)), Integer.parseInt(prop.getProperty(("squareNum"+i))));
				TitleDeed tempDeed = new TitleDeed("Title Deed", prop.getProperty(("title"+i)),0, null, Integer.parseInt((prop.getProperty(("priceBuy"+i)))), rentIntArray, 0, Integer.parseInt(prop.getProperty(("mortgage"+i))),null, temp);
    			temp.setTitleDeedCard(tempDeed);
    			board.set(temp.getLocation(),temp);
    		}
    	}
    	catch (Exception e) {
			System.out.println("Exception: " + e);
    	}
    }

    //set up the board for the game
    public static void initialiseBoard() {
		try {
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
}