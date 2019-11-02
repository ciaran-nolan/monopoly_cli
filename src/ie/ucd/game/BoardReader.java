/**
 * Class to read from properties file 
 * References: BoardReader example, cs moodle 
 * 
 */

package ie.ucd.game;
import ie.ucd.game.Property;
import ie.ucd.game.Utility;
import ie.ucd.game.Special;
import ie.ucd.game.CommunityChest;
import ie.ucd.game.Chance;
import ie.ucd.game.Train;
import java.io.*;
import java.util.*;



public class BoardReader {

	public static ArrayList<Property> properties = new ArrayList<Property>(22);
	public static ArrayList<Utility> utilities = new ArrayList<Utility>(6);
	public static ArrayList<Special> specials = new ArrayList<Special>(12);
	public static ArrayList<CommunityChest> communityChests = new ArrayList<CommunityChest>();
	public static ArrayList<Chance> chances = new ArrayList<Chance>();
	public static ArrayList<Train> trains = new ArrayList<Train>(2);
	public static ArrayList<Square> board = new ArrayList<Square>(Collections.nCopies(40, null));
	
	
	
	
    public static void readProperties() throws FileNotFoundException { 	
    	try {
    		Properties prop = new Properties();
    		//define properties list to hold the .properties file
    		
    		//define the location of the prop file
    		String propFileName = "ie/ucd/gameConfigurations/property.properties";
    		//define the input stream for the prop file
    		InputStream inputStream = BoardReader.class.getClassLoader().getResourceAsStream(propFileName);
    		
    		//check that the prop file location is valid
    		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
    	//recursively loop to organize all property date into respective class variables and place them in an array list
    	for(int i=0; i<=21; i++) {
    		
    		int[] rentIntArray = Arrays.stream(prop.getProperty(("rents"+i)).split(",")).mapToInt(Integer::parseInt).toArray();
    		Property temp = new Property(Integer.parseInt(prop.getProperty(("squareNum"+i))),prop.getProperty(("squareColour"+i)),prop.getProperty(("title"+i)),Integer.parseInt((prop.getProperty(("priceBuy"+i)))),rentIntArray, Integer.parseInt(prop.getProperty(("housePrice"+i))), Integer.parseInt(prop.getProperty(("mortgage"+i))));
    		properties.add(temp);
    		board.set(temp.getLocation(),temp);
    		
    	}
    	
    	inputStream.close();
    	} 
    	catch (Exception e) {
    		System.out.println("Exception: " + e);
    		}
    	}
    
    public static void readUtilities() throws FileNotFoundException {
    	try {
    		Properties utilProp = new Properties();
    		String propFileName = "ie/ucd/gameConfigurations/utilities.properties";
    		InputStream inputStream = BoardReader.class.getClassLoader().getResourceAsStream(propFileName);
    		
    		if (inputStream != null) {
    			utilProp.load(inputStream);
				
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
    	
    	for(int i=0; i<=1; i++) {
    		int[] rentIntArray = Arrays.stream(utilProp.getProperty(("rents"+i)).split(",")).mapToInt(Integer::parseInt).toArray();
    		Utility temp = new Utility(utilProp.getProperty(("title"+i)), Integer.parseInt(utilProp.getProperty(("squareNum"+i))), Integer.parseInt(utilProp.getProperty(("priceBuy"+i))), Integer.parseInt(utilProp.getProperty(("mortgage"+i))), rentIntArray, null);    	
    		utilities.add(temp);
    		board.set(temp.getLocation(),temp);
    	}
    	
    	} catch (Exception e) {
			System.out.println("Exception: " + e);
			}
    	}
    
    public static void readSpecialSquares() throws FileNotFoundException{
    	try {
    		Properties prop = new Properties();
    		//define properties list to hold properties file  
    		
    		//location of prop file
    		String propFileName = "ie/ucd/gameConfigurations/specialSquares.properties";
    		InputStream inputStream = BoardReader.class.getClassLoader().getResourceAsStream(propFileName);
    		
    		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
    	
    	for(int i=0; i<=11; i++) {
    		Special temp = new Special(prop.getProperty(("squareName"+i)),Integer.parseInt(prop.getProperty(("squareNum"+i))),false, prop.getProperty("type"+i),Integer.parseInt(prop.getProperty("value"+i)),Square.SquareType.SPECIAL);
    		specials.add(temp);  
    		board.set(temp.getLocation(),temp);
    	}
    	
    	} catch (Exception e) {
			System.out.println("Exception: " + e);
			}
    	
    }
    
    public static void readCommunityChests() throws FileNotFoundException {
    	try {
    		Properties prop = new Properties();
    		//define properties list to hold properties file  
    		
    		//location of prop file
    		String propFileName = "ie/ucd/gameConfigurations/communityChest.properties";
    		InputStream inputStream = BoardReader.class.getClassLoader().getResourceAsStream(propFileName);
    		
    		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
    	
    	for(int i=0; i<=15; i++) {
    		
    		CommunityChest temp = new CommunityChest(prop.getProperty(("type"+i)),prop.getProperty(("card"+i)),Integer.parseInt(prop.getProperty(("value"+i))));
    		communityChests.add(temp);
    	}
    	Collections.shuffle(communityChests);
    	
    	} catch (Exception e) {
			System.out.println("Exception: " + e);
			}
    }
    
    public static void readChances() throws FileNotFoundException {
    	try {
    		Properties prop = new Properties();
    		//define properties list to hold properties file  
    		
    		//location of prop file
    		String propFileName = "ie/ucd/gameConfigurations/chance.properties";
    		InputStream inputStream = BoardReader.class.getClassLoader().getResourceAsStream(propFileName);
    		
    		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
    	
    	for(int i=0; i<=15; i++) {
    		Chance temp = new Chance(prop.getProperty(("type"+i)),prop.getProperty(("card"+i)),Integer.parseInt(prop.getProperty(("value"+i))));
    		chances.add(temp);
    	}
    	Collections.shuffle(chances);
    
    	} catch (Exception e) {
			System.out.println("Exception: " + e);
			}
    }
   
    public static void readTrains() throws FileNotFoundException{
    	try {
    		Properties prop = new Properties();
    		String trainFileName = "ie/ucd/gameConfigurations/train.properties";
    		InputStream inputStream = BoardReader.class.getClassLoader().getResourceAsStream(trainFileName);
    		
    		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + trainFileName + "' not found in the classpath");
			}
    	
    	for(int i=0; i<=3; i++) {
    		int[] rentIntArray = Arrays.stream(prop.getProperty(("rents"+i)).split(",")).mapToInt(Integer::parseInt).toArray();
    		Train temp = new Train(prop.getProperty(("title"+i)), Integer.parseInt(prop.getProperty(("squareNum"+i))), Integer.parseInt(prop.getProperty(("priceBuy"+i))), Integer.parseInt(prop.getProperty(("mortgage"+i))), rentIntArray, null);    	
    		trains.add(temp);   
    		board.set(temp.getLocation(),temp);
    		//System.out.println(board.get(temp.getLocation()).getName());
    	}
    	
    	} catch (Exception e) {
			System.out.println("Exception: " + e);
			}
    }
    
    public static void initialiseChanceComChestSquares() {
    	
    }
    public static void initialiseBoard() {
		try {
			readProperties();
			readUtilities();
			readSpecialSquares();
			readCommunityChests();
			readChances();
			readTrains();
		}catch (Exception e) {
			System.out.println("Exception: " + e);
			}
    	
		
	}
    
    public static void outputBoard() {
    	for(int i=0;i<board.size();i++) {
    		System.out.println(board.get(i).getName());
    	}
    }
    
    public static ArrayList<Utility> getUtilities(){
    	return utilities;
    	
    }
    public static ArrayList<Property> getProperties(){
    	return properties;
    	
    }
    public static ArrayList<Special> getSpecials(){
    	return specials;
    }
    public static ArrayList<CommunityChest> getCommunityChests(){
    	return communityChests;
    }
    public static ArrayList<Chance> getChances(){
    	return chances;
    }
}