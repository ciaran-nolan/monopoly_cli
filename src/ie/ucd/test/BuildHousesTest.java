package ie.ucd.test;

import java.io.IOException;
import java.util.ArrayList;

import ie.ucd.game.*;

public class BuildHousesTest {
	public static void main(String[] args) throws IOException { 
		BoardReader b1 = new BoardReader();
		b1.readProperties();
		b1.readSpecialSquares();
		b1.readUtilities();
		b1.readCommunityChests();
		b1.readChances();
		
		 ArrayList<Property> properties = b1.getProperties();
		 
		 Player p1 = new Player("p1", "thing1");
		 Player p2 = new Player("p2", "thing2");
		 Player p3 = new Player("p3", "thing3");
		 
		 ArrayList<Player> plist = new ArrayList<Player>();
		 plist.add(p1);
		 plist.add(p2);
		 plist.add(p3);
		 
		 System.out.println(p1.getPropertyList().size());
		 properties.get(0).buy(p1, plist);
		 System.out.println(p1.getPropertyList().get(0).getName());
		
		 //properties.get(1).buildHouses(p1);
		 
	
	
	}
	}

