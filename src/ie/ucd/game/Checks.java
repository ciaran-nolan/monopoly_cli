package ie.ucd.game;

//import java.util.ArrayList;
//import java.util.Scanner;
import java.util.*;

import ie.ucd.game.*;


public class Checks {
	
	public static void checkSquare(int index, Player player) {
		Square currentSquare = BoardReader.board.get(index);
		
		switch(currentSquare.getSquareType()) {
		case PROPERTY:
			if(null==((Property)currentSquare).getTitleDeedCard().getOwner()){
				((Property)currentSquare).buy(player);
			}
			else {
				player.payRent((CanOwn)currentSquare);
			}
			break;
		case SPECIAL:
			((Special)currentSquare).implementSpecialSquare(player);
			break;
		case TRAIN:
			if(null==((Train)currentSquare).getTitleDeedCard().getOwner()){
				((Train)currentSquare).buy(player);
			}
			//FIXME Type mismatch, can't pay rent for train or utility as input expects type CanOwn
			else {
				player.payRent((CanOwn)currentSquare);
			}
			
			break;
		case UTILITY:
			if(null==((Utility)currentSquare).getTitleDeedCard().getOwner()){
				((Utility)currentSquare).buy(player);
			}
			//FIXME Type mismatch, cant pay rent for train or utility as input expects type CanOwn
			else {
				player.payRent((CanOwn)currentSquare);
			}
			break;
		default:
			break;	
		}
	}
	public static boolean hasPassedGo(int startIndex, int endIndex) {
        return startIndex > endIndex;
    }
	
	public static boolean enoughFunds(Player player, int price) {
        return player.getMoney() >= price;
    }
	
	public static void playerStatus(Player player) {
		System.out.println(player.getName()+": You are currently at square "+player.getLocation()+", you have:\n\n"+player.getJailFreeNum()
		+" Jail Free Cards\n"+player.getTitleDeedList().size()+" ownable properties\n"+player.getMoney()+" in cash \n\n");
	}

	public static boolean canBuy(CanOwn ownableCard, Player player) {
        return null == ownableCard.getTitleDeedCard().getOwner();
		}
	
	public static boolean isPlayerOwner(CanOwn ownableCard, Player player){
		if(null == ownableCard.getTitleDeedCard().getOwner()){
			return false;
		}
		if(ownableCard.getTitleDeedCard().getOwner().getName().equals(player.getName())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Property isValidProp(String name, Player player) {
		for(Property currentProp: BoardReader.properties) {
				//compare square colour with the specified property
				if(currentProp.getName().equals(name)){
					return currentProp;
				}
		}
		return null;
	}

	public static Player isValidPlayer(String playerName) {
		for(int i=0; i<Game.playerList.size(); i++) {
			if(Game.playerList.get(i).getName().equalsIgnoreCase(playerName)) {
				return Game.playerList.get(i);
			}
		}
		return null;
	}
	
	//method to check if a player owns all the properties in a given colour group.
	public static ArrayList<Property> ownAllColour(Player player, Property property) {
		if(null == property){
			return null;
		}
		//counter to track how many of type colour
		int colourCounter = 0;
		//boolean to track that the particular property group has either two or three instances on the board
		boolean twoPropGroup = false;
		//A list of the properties of the particular colour being owned by the player to be returned if all are owned
		ArrayList<Property> propertyList = new ArrayList<>();

		
		//check if the property group is two or three cards 
		if(property.getSquareColour().equals("Purple")  || property.getSquareColour().equals("Dark Blue")) {
			twoPropGroup = true;
		}

		//loop through the players property list
		for(TitleDeed titleDeed: player.getTitleDeedList()) {
			//only analyse the type property
			CanOwn currentOwnable = titleDeed.getOwnableSite();
			if(currentOwnable instanceof Property) {
				//compare square colour with the specified property
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
		int[] houseDifferentialBounds = new int[2];
		if(buyOrSell) {
			//when buying a house the must only be a difference of 0 or -1 between the chosen property and all other property's house numbers
			houseDifferentialBounds[0]=-1;
			houseDifferentialBounds[1]=0;
		}
		else {
			//when selling it can be 1 or 0
			houseDifferentialBounds[0] = 0;
			houseDifferentialBounds[1] = 1;
		}
		
		for(int i=0; i<colourGroup.size();i++) {
			if(colourGroup.get(i).getName().equals(propertyToAlterHouses.getName())) {
                for (Property property : colourGroup) {
                    if (!((colourGroup.get(i).getNumHouses() - property.getNumHouses() >= houseDifferentialBounds[0]) && (colourGroup.get(i).getNumHouses() - property.getNumHouses() <= houseDifferentialBounds[1]))) {
                        return false;
                    }
                }
			}
		}
		return true;
	}

	public static boolean canBuildHouses(CanOwn ownableCard) {
		//only cards of type property can have houses
        return ownableCard instanceof Property;
	}
	
	public static int canBuildHousesHotels(Property propToBuild, Player player){

		// Status codes:
		// - 1 - attempt again
		// - 2 - exit without building
		// return 0 - valid
		if(null==propToBuild) {
			if (InputOutput.yesNoInput("The property you have entered is invalid, would you like to try again? (y/n)", player)) {
				return -1;
			}
			else return -2;
		}
		else if(!(Checks.isPlayerOwner(propToBuild, player))){
			if (InputOutput.yesNoInput("You do not own the property you have entered, would you like to try again? (y/n)", player)) {
				//restart pre-dice roll options
				return -1;
			} else return -2;
		}
		else if(player.getMoney() < propToBuild.getTitleDeedCard().getHousePrice()) {
			System.out.println("You do not have enough funds to purchase any houses/hotels for " + propToBuild.getName()
					+ "\nYour funds: " + player.getMoney() + "\nHouse Price: " + propToBuild.getTitleDeedCard().getHousePrice());
			return -2;
		}

		if(null == Checks.ownAllColour(player,propToBuild)) {
			if (InputOutput.yesNoInput("You do not own all properties in this colour group, would you like to try again? (y/n)", player)) {
				//restart pre-dice roll options
				return -1;
			} else return -2;
		}
		else {
			return 0;
		}
	}
	
	public static int checkHouseHotelValue(Player player) {
		int valOfHousesHotels = 0;
		
		for(TitleDeed titleDeed : player.getTitleDeedList()) {
			//only analyse the type property
			CanOwn currentOwnable = titleDeed.getOwnableSite();
			if(currentOwnable instanceof Property) {
				valOfHousesHotels += ((titleDeed.getHousePrice()*((Property) currentOwnable).getNumHouses())/2);
				valOfHousesHotels += ((titleDeed.getHousePrice()*((Property) currentOwnable).getNumHotels()*5)/2);
			}
		}
		return valOfHousesHotels;
	}
	
	public static int checkMortgagingValue(Player player) {
		int mortgageValue = 0;
		
		for(TitleDeed titleDeed : player.getTitleDeedList()) {
			//only add the value of properties that are not currently mortgaged
			CanOwn currentOwnable = titleDeed.getOwnableSite();
			if(!titleDeed.getMortgageStatus()) {
				mortgageValue += (titleDeed.getPriceBuy() / 2);
			}
		}
		return mortgageValue;
	}
	
	//This function will check the winner of the game by looping through the player list and checking who has the most money
	//Will return the player object that is the winner and then the main class will finish the game
	//Check winner will be called when the 2nd bankruptcy of the group of players occurs
	public static void checkWinner() {
		int totalValue=0;
		ArrayList<Integer> valueArray = new ArrayList<>();
		int maxValue, maxIndex;
		
		for(Player player:Game.playerList) {
			totalValue += player.getMoney();
			for(TitleDeed titleDeed:player.getTitleDeedList()) {
				CanOwn ownable = titleDeed.getOwnableSite();
				if(ownable instanceof Property) {
					totalValue += ((Property)ownable).getNumHouses()*titleDeed.getHousePrice();
					totalValue += ((Property)ownable).getNumHotels()*titleDeed.getHousePrice()*5; //A Hotel is 5 times the price of a house
				}
				totalValue+= titleDeed.getPriceBuy();
				valueArray.add(totalValue);
				System.out.println("Player: "+player.getName()+" has Total Asset value of �"+totalValue);
			}
			//Zero the local variable for use with next player
			totalValue=0;
		}
		maxValue = Collections.max(valueArray);
		maxIndex = valueArray.indexOf(maxValue);
		System.out.println("The richest player and winner of the game is: "+(Game.playerList.get(maxIndex).getName()+" with a Total Asset Value of �"+maxValue));
		System.out.println("The game has been won! It is now over");
		System.exit(1);
	}
	
}
		
		

