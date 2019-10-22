package ie.ucd.test;

import java.io.IOException;
import java.util.ArrayList;

import ie.ucd.game.BoardReader;
import ie.ucd.game.Game;
import ie.ucd.game.Player;
import ie.ucd.game.Property;

public class PlayerToPlayerTest {
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
		 Game.playerList.add(p1);
		 Game.playerList.add(p2);
		 Game.playerList.add(p3);
		 
		 ArrayList<Player> plist = new ArrayList<Player>();
		 plist.add(p1);
		 plist.add(p2);
		 plist.add(p3);
		 
		 properties.get(0).buy(p1);
		 properties.get(1).buy(p2);
		 
		 p1.playerToPlayerTransaction();
		 
	}
}
