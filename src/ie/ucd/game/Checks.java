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
		int colourCounter = 0;
		boolean twoPropGroup = false;
		ArrayList<Property> propertyList = new ArrayList<Property>(); //A local list of the properties of the particular colour being owned by the player

		
		//check if the property group is two or three cards 
		if(property.getSquareColour().equals("\"Purple\"")  || property.getSquareColour().equals("\"Dark Blue\"")) {
			twoPropGroup = true;
		}

		for(CanOwn currentOwnable: player.getPropertyList()) {
			if(currentOwnable instanceof Property) {
				if(((Property) currentOwnable).getSquareColour().equals(property.getSquareColour())) {	
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
	
	public static boolean evenHouseDistribution(ArrayList<Property> colourGroup, Property propertyToBuildHouse) {
		
		for(int i=0; i<colourGroup.size();i++) {
			
	}
	} 
}
		
		

