package ie.ucd.test;

import java.io.IOException;
import java.util.ArrayList;


import ie.ucd.game.*;

public class BuildHousesTest {
	public static void main(String[] args) throws IOException { 
	
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
		 
		 Game.playerList.add(p1);
		 Game.playerList.add(p2);
		 Game.playerList.add(p3);
		//test 1
		 
		/* p1.addMoney(10000);
		 for(int i=0; i<properties.size();i++) {
		 properties.get(i).buy(p1, plist);
		 }*/
		 
		 
		 
		/*for(int k=0; k<5; k++) {
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
		
		
		 ((Property) p1.getPropertyList().get(0)).buildHotel(p1);
		
		
		/* for(int k=0; k<5; k++) {
			 for(int i=0; i<properties.size();i++) {
				 ((Property) p1.getPropertyList().get(i)).sellHouses(p1, false, false);
				 if(Game.getRemainingHouses()==32) {
					 break;
				 }
			 }
			 if(Game.getRemainingHouses()==0) {
				 break;
			 }
			}*/
		 properties.get(0).buy(p1);
		 ((Property) p1.getPropertyList().get(0)).sellHouses(p1, false, false);
		
		 
	
	}
	}

