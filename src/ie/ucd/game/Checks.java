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
			if(null==((Property)currentSquare).getOwner()){
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
			if(null==((Train)currentSquare).getOwner()){
				((Train)currentSquare).buy(player);
			}
			//FIXME Type mismatch, can't pay rent for train or utility as input expects type CanOwn
			else {
				player.payRent((CanOwn)currentSquare);
			}
			
			break;
		case UTILITY:
			if(null==((Utility)currentSquare).getOwner()){
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
		if(startIndex>endIndex) {
			return true;
		}
		return false;
	}
	
	public static boolean enoughFunds(Player player, int price) {
		if(player.getMoney() < price) {
			return false;
		}
		return true;
	}
	
	public static void playerStatus(Player player) {
		System.out.println(player.getName()+": You are currently at square "+player.getLocation()+", you have:\n\n"+player.getJailFreeNum()
		+" Jail Free Cards\n"+player.getPropertyList().size()+" ownable properties\n"+player.getMoney()+" in cash \n\n");
	}

	public static boolean canBuy(CanOwn ownableCard, Player player) {
		if(ownableCard.getOwner() == null) {
			return true;
		}
		else {
			return false;
		}
		}
	
	public static boolean isPlayerOwner(CanOwn ownableCard, Player player){
		if(ownableCard.getOwner().getName().equals(player.getName())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Property isValidProp(String name, Player player) {
		for(CanOwn currentOwnable: player.getPropertyList()) {
			//only analyse the type property
			if(currentOwnable instanceof Property) {
				System.out.println(currentOwnable.getName());
				//compare square colour with the specified property
				if(((Property) currentOwnable).getName().equalsIgnoreCase(name)){
					return ((Property) currentOwnable);
				}
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
		//counter to track how many of type colour
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
				for(int k = 0; k < colourGroup.size(); k++) {
					if(!((colourGroup.get(i).getNumHouses()-colourGroup.get(k).getNumHouses()>=houseDifferentialBounds[0])&&(colourGroup.get(i).getNumHouses()-colourGroup.get(k).getNumHouses()<=houseDifferentialBounds[1]))){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static int checkHouseHotelValue(Player player) {
		int valOfHousesHotels = 0;
		
		for(CanOwn currentOwnable: player.getPropertyList()) {
			//only analyse the type property
			if(currentOwnable instanceof Property) {
				valOfHousesHotels += ((((Property) currentOwnable).getHousePrice()*((Property) currentOwnable).getNumHouses())/2);
				valOfHousesHotels += ((((Property) currentOwnable).getHousePrice()*((Property) currentOwnable).getNumHotels()*5)/2);
			}
		}
		return valOfHousesHotels;
	}
	
	public static int checkMortgagingValue(Player player) {
		int mortgageValue = 0;
		
		for(CanOwn currentOwnable: player.getPropertyList()) {
			//only analyse the type property
				mortgageValue += (currentOwnable.getPrice()/2);
		}
		return mortgageValue;
	}
	
	//This function will check the winner of the game by looping through the player list and checking who has the most money
	//Will return the player object that is the winner and then the main class will finish the game
	//Check winner will be called when the 2nd bankruptcy of the group of players occurs
	public static void checkWinner() {
		int totalValue=0;
		ArrayList<Integer> valueArray = new ArrayList<Integer>();
		int maxValue, maxIndex;
		
		for(Player player:Game.playerList) {
			totalValue += player.getMoney();
			for(CanOwn ownable:player.getPropertyList()) {
				if(ownable instanceof Property) {
					totalValue += ((Property)ownable).getNumHouses()*((Property)ownable).getHousePrice();
					totalValue += ((Property)ownable).getNumHotels()*((Property)ownable).getHousePrice()*5; //A Hotel is 5 times the price of a house
				}
				totalValue+= ownable.getPrice();
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
		
		

