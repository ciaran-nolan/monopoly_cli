/**
 * Class to read from properties file 
 * References: BoardReader example, cs moodle 
 * 
 */

package ie.ucd.game;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;



public class BoardReader {

    public void readProperties() throws IOException {

    	BufferedReader input = new BufferedReader(new FileReader("ie/ucd/gameConfigurations/property.properties"));
    	String line;
    	while((line = input.readLine()) != null){
			System.out.println(line);		
		}
    	
 
      
    }

}
