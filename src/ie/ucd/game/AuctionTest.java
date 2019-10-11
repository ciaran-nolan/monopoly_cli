package ie.ucd.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ie.ucd.game.*;

public class AuctionTest {
	public static void main(String[] args) throws IOException {
		
		//Property testProp = new Property(int 0,"Red","Test",int 50,int [10,20,50], 25, 25,)
	BoardReader.readProperties();
	ArrayList<Property> properties = BoardReader.getProperties();
	 
	Player p1 = new Player("p1", "thing1");
	Player p2 = new Player("p2", "thing2");
	Player p3 = new Player("p3", "thing3");
	 
	ArrayList<Player> plist = new ArrayList<Player>();
	plist.add(p1);
	plist.add(p2);
	plist.add(p3);
	p1.reduceMoney(1490);
	p3.reduceMoney(1000);
	 
	 properties.get(1).buy(p1, plist);
	 if(p2.getPropertyList().get(0) instanceof Property) {
		 System.out.println(((Property) p2.getPropertyList().get(0)).getSquareColour());	 
	 }
	 
	 
	}
}
