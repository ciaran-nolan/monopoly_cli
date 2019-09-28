package ie.ucd.game;
import java.util.*;

//This is how to sort out the handling of a community chest card that is picked from a deck
//It is not to denote a communitychest square. That is handled by the Special.java file
public class CommunityChest extends Special {
	
	ArrayList<Card> communityChestDeck = new ArrayList<Card>();
	
	public CommunityChest(boolean canBuy, String name, String type, int value) {
		super(canBuy, name, type, value);
	}
	
	

}
