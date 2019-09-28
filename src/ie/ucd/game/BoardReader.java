/**
 * Class to read from properties file 
 * References: BoardReader example, cs moodle 
 * 
 */

package ie.ucd.game;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;



public class BoardReader {

	private ArrayList<String> propertyNames = new ArrayList<String>(22);  

    public void readProperties() throws IOException {

    	/*BufferedReader input = new BufferedReader(new FileReader("src/ie/ucd/gameConfigurations/property.properties"));
    	String line;
    	while((line = input.readLine()) != null){
			
    		
    		
    		System.out.println(line);		
		}*/
    	
    	try {
    		Properties prop = new Properties();
    		
    		String propFileName = "ie/ucd/gameConfigurations/property.properties";
    		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
    		
    		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
    		
    	for(int i=0; i<=22; i++) {
    		propertyNames.add(prop.getProperty("squareName0")); 
    	}
    		
    	} catch (Exception e) {
			System.out.println("Exception: " + e);
    	}
    }
    
    public ArrayList<String> getPropertyNames(){
    	return this.propertyNames;
    	
    }
    }

