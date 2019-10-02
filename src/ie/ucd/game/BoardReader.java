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
import java.io.*;
import java.util.*;



public class BoardReader {

	private static ArrayList<Property> properties = new ArrayList<Property>(22);
	private static ArrayList<Utility> utilities = new ArrayList<Utility>(6);
	private static ArrayList<Special> specials = new ArrayList<Special>(12);
	private static ArrayList<CommunityChest> communityChests = new ArrayList<CommunityChest>();
	private static ArrayList<Chance> chances = new ArrayList<Chance>();
	
	
    public void readProperties() throws FileNotFoundException { 	
    	try {
    		Player bank = new Player("BANK","BANK",0);
    		//define properties list to hold the .properties file
    		Properties prop = new Properties();
    		//define the location of the prop file
    		String propFileName = "ie/ucd/gameConfigurations/property.properties";
    		//define the input stream for the prop file
    		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
    		
    		//check that the prop file location is valid
    		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
    	//recursively loop to organize all property date into respective class variables and place them in an array list
    	for(int i=0; i<=21; i++) {
    		int[] rentIntArray = Arrays.stream(prop.getProperty(("rents"+i)).split(",")).mapToInt(Integer::parseInt).toArray();
    		Property temp = new Property(Integer.parseInt(prop.getProperty(("squareNum"+i))),prop.getProperty(("squareColour"+i)),prop.getProperty(("title"+i)),Integer.parseInt((prop.getProperty(("priceBuy"+i)))),rentIntArray, Integer.parseInt(prop.getProperty(("housePrice"+i))), Integer.parseInt(prop.getProperty(("mortgage"+i))), bank, 0,0);
    		properties.add(temp);
    	}
    	} 
    	catch (Exception e) {
    		System.out.println("Exception: " + e);
    		}
    	}
    
    public void readUtilities() throws FileNotFoundException {
    	try {
    		Properties prop = new Properties();
    		String propFileName = "ie/ucd/gameConfigurations/utilities.properties";
    		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
    		
    		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
    	
    	for(int i=0; i<=5; i++) {
    		int[] rentIntArray = Arrays.stream(prop.getProperty(("rents"+i)).split(",")).mapToInt(Integer::parseInt).toArray();
    		Utility temp = new Utility(prop.getProperty(("title"+i)), Integer.parseInt(prop.getProperty(("squareNum"+i))), Integer.parseInt(prop.getProperty(("priceBuy"+i))), Integer.parseInt(prop.getProperty(("mortgage"+i))), rentIntArray, null);    	
    		utilities.add(temp);    	
    	}
    	
    	} catch (Exception e) {
			System.out.println("Exception: " + e);
			}
    	}
    
    public void readSpecialSquares() throws FileNotFoundException{
    	try {
    		//define properties list to hold properties file  
    		Properties prop = new Properties();
    		//location of prop file
    		String propFileName = "ie/ucd/gameConfigurations/specialSquares.properties";
    		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
    		
    		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
    	
    	for(int i=0; i<=11; i++) {
    		Special temp = new Special(prop.getProperty(("squareName"+i)),Integer.parseInt(prop.getProperty(("squareNum"+i))),false, prop.getProperty("squareType"+i),Integer.parseInt(prop.getProperty("value"+i)));
    		specials.add(temp);  	
    	}
    	} catch (Exception e) {
			System.out.println("Exception: " + e);
			}
    	
    }
    
    public void readCommunityChests() throws FileNotFoundException {
    	try {
    		//define properties list to hold properties file  
    		Properties prop = new Properties();
    		//location of prop file
    		String propFileName = "ie/ucd/gameConfigurations/communityChest.properties";
    		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
    		
    		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
    	
    	for(int i=0; i<=15; i++) {
    		
    		CommunityChest temp = new CommunityChest(prop.getProperty(("type"+i)),prop.getProperty(("card"+i)),Integer.parseInt(prop.getProperty(("value"+i))));
    		communityChests.add(temp);
    	}
    	} catch (Exception e) {
			System.out.println("Exception: " + e);
			}
    }
    
    public void readChances() throws FileNotFoundException {
    	try {
    		//define properties list to hold properties file  
    		Properties prop = new Properties();
    		//location of prop file
    		String propFileName = "ie/ucd/gameConfigurations/chance.properties";
    		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
    		
    		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
    	
    	for(int i=0; i<=15; i++) {
    		Chance temp = new Chance(prop.getProperty(("type"+i)),prop.getProperty(("card"+i)),Integer.parseInt(prop.getProperty(("value"+i))));
    		chances.add(temp);
    	}
    	} catch (Exception e) {
			System.out.println("Exception: " + e);
			}
    }
    public ArrayList<Utility> getUtilities(){
    	return utilities;
    	
    }
    public ArrayList<Property> getProperties(){
    	return properties;
    	
    }
    public ArrayList<Special> getSpecials(){
    	return specials;
    }
    public static ArrayList<CommunityChest> getCommunityChests(){
    	return communityChests;
    }
    public static ArrayList<Chance> getChances(){
    	return chances;
    }
}
    

