package ie.ucd.game;

import java.util.ArrayList;
import java.util.Scanner;

import ie.ucd.game.*;


public class Checks {
	
	

	public static boolean canBuy(CanOwn ownableCard, Player player) {
		if(ownableCard.getOwner() == null) {
			return true;
		}
		else {
			return false;
		}
		}
	
	
	
	public static boolean yesNoInput(String message,Player player) {
		Scanner yesNoScanner = new Scanner(System.in);
		System.out.println(message);
		String acknowledgement = yesNoScanner.next();
		
		while(!(acknowledgement.equalsIgnoreCase("y") || acknowledgement.equalsIgnoreCase("n"))) {
			System.out.println(player.getName()+", please enter a valid response (y/n)");
			acknowledgement = yesNoScanner.next();
		}
		if(acknowledgement.equalsIgnoreCase("y")){
			return true;
		}
		else {
			return false;
			}
	}
	
	public static boolean canBuildHouses(CanOwn ownableCard) {
		//only cards of type property can have houses
		if(ownableCard instanceof Property) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//method to check if a player owns all the properties in a given colour group.
	public static ArrayList<Property> ownAllColour(Player player, Property property) {
		//counter to track how mnay of type colour
		int colourCounter = 0;
		//boolean to track that the particular property group has either two or three instances on the board
		boolean twoPropGroup = false;
		//A list of the properties of the particular colour being owned by the player to be returned if all are owned
		ArrayList<Property> propertyList = new ArrayList<Property>();

		
		//check if the property group is two or three cards 
		if(property.getSquareColour().equals("\"Purple\"")  || property.getSquareColour().equals("\"Dark Blue\"")) {
			twoPropGroup = true;
		}

		//loop through the players property list
		for(CanOwn currentOwnable: player.getPropertyList()) {
			//only analyse the type property
			if(currentOwnable instanceof Property) {
				//compare sqyuare colour with the specified property
				if(((Property) currentOwnable).getSquareColour().equals(property.getSquareColour())) {	
					//add the property to the list if there is a colour group match
					propertyList.add((Property) currentOwnable);
					colourCounter++;
					}
				}
			//matching cases for both two and three house groups
			if(((twoPropGroup) && colourCounter == 2) || colourCounter == 3) {
				return propertyList;
				}
			}
		return null;
		}
	
	public static boolean evenHouseDistribution(ArrayList<Property> colourGroup, Property propertyToAlterHouses, boolean buyOrSell) {
		int houseDifferentialBound;
		if(buyOrSell) {
			houseDifferentialBound = 0;
		}
		else {
			houseDifferentialBound = 1;
				}
		
		for(int i=0; i<colourGroup.size();i++) {
			if(colourGroup.get(i).getName().equals(propertyToAlterHouses.getName())) {
				for(int k = 0; k < colourGroup.size(); k++) {
					if(!(colourGroup.get(i).getNumHouses()-colourGroup.get(k).getNumHouses()<=houseDifferentialBound)){
						return false;
						}
					}
				}
			}
		
		return true;
	} 
}
		
		

