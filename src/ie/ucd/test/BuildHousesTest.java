package ie.ucd.test;

import java.io.IOException;
import java.util.ArrayList;


import ie.ucd.game.*;

public class BuildHousesTest {
	public static void main(String[] args) throws IOException { 
		BoardReader b1 = new BoardReader();
		BoardReader.readProperties();
		BoardReader.readSpecialSquares();
		BoardReader.readUtilities();
		BoardReader.readCommunityChests();
		BoardReader.readChances();
		
		 ArrayList<Property> properties = BoardReader.getProperties();
		 
		 Player p1 = new Player("p1", "thing1");
		 Player p2 = new Player("p2", "thing2");
		 Player p3 = new Player("p3", "thing3");
		 
		 ArrayList<Player> plist = new ArrayList<Player>();
		 plist.add(p1);
		 plist.add(p2);
		 plist.add(p3);
		 
		 p1.addMoney(10000);
		 for(int i=0; i<properties.size();i++) {
		 properties.get(i).buy(p1, plist);
		 }
		for(int k=0; k<5; k++) {
		 for(int i=0; i<properties.size();i++) {
			 ((Property) p1.getPropertyList().get(i)).buildHouses(p1, Checks.ownAllColour(p1, (Property) p1.getPropertyList().get(i)));
			 if(Game.getRemainingHouses()==0) {
				 break;
			 }
		 }
		 if(Game.getRemainingHouses()==0) {
			 break;
		 }
		}
		
		 System.out.println(((Property) p1.getPropertyList().get(0)).getSquareColour());
		
		 ((Property) p1.getPropertyList().get(0)).buildHouses(p1, Checks.ownAllColour(p1, (Property) p1.getPropertyList().get(0)));
		 ((Property) p1.getPropertyList().get(1)).buildHouses(p1, Checks.ownAllColour(p1, (Property) p1.getPropertyList().get(1)));
		
		 System.out.println(Game.getRemainingHouses());
		 
		 ((Property) p1.getPropertyList().get(0)).buildHouses(p1, Checks.ownAllColour(p1, (Property) p1.getPropertyList().get(0)));
		 ((Property) p1.getPropertyList().get(1)).buildHouses(p1, Checks.ownAllColour(p1, (Property) p1.getPropertyList().get(1)));
		 
		 ((Property) p1.getPropertyList().get(0)).buildHouses(p1, Checks.ownAllColour(p1, (Property) p1.getPropertyList().get(0)));
		 ((Property) p1.getPropertyList().get(1)).buildHouses(p1, Checks.ownAllColour(p1, (Property) p1.getPropertyList().get(1)));
		 
		 ((Property) p1.getPropertyList().get(0)).buildHouses(p1, Checks.ownAllColour(p1, (Property) p1.getPropertyList().get(0)));
		// ((Property) p1.getPropertyList().get(1)).buildHouses(p1, Checks.ownAllColour(p1, (Property) p1.getPropertyList().get(1)));
		 
		 ((Property) p1.getPropertyList().get(0)).buildHotel(p1, Checks.ownAllColour(p1, (Property) p1.getPropertyList().get(0)));
		// ((Property) p1.getPropertyList().get(1)).buildHotel(p1, Checks.ownAllColour(p1, (Property) p1.getPropertyList().get(1)));
		 
		 
		
		
		 
		 
	
	
	}
	}

