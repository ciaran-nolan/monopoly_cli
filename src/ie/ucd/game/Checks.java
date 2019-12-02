package ie.ucd.game;

//import java.util.ArrayList;
//import java.util.Scanner;
import java.util.*;


public class Checks {

	//check if the game can still continue
	public static boolean checkIfValidGame(){
		return Game.playerList.size() != 1 && Game.numPlayersBankrupt < 2;
	}

	//check the square the player has landed on
	public static void checkSquare(int index, Player player) {
		//get the current square
		Square currentSquare = Board.board.get(index);
		//check the square type
		switch(currentSquare.getSquareType()) {
		case PROPERTY:
			//property has no owner, offer to buy
			if(canBuy(((Property)currentSquare).getTitleDeedCard())){
				((Property)currentSquare).buy(player);
			}
			//player already owns the property
			else if(isPlayerOwner(((Property) currentSquare).getTitleDeedCard(),player)) {
				System.out.println("You own this property.");
			}
			//property owned by a different player, must pay rent
			else player.payRent((CanOwn)currentSquare);
			break;
		case SPECIAL:
			//implement the special square
			((Special)currentSquare).implementSpecialSquare(player);
			break;
		case TRAIN:
			//no owner, offer to buy
			if(canBuy(((Train)currentSquare).getTitleDeedCard())){
				((Train)currentSquare).buy(player);
			}
			//player is owner, no action required
			else if(isPlayerOwner(((Train) currentSquare).getTitleDeedCard(),player)) {
				System.out.println("You own this property.");
			}
			//different player is owner, must pay rent
			else player.payRent((CanOwn)currentSquare);
			break;
		case UTILITY:
			//no owner, offer to buy
			if(canBuy(((Utility)currentSquare).getTitleDeedCard())){
				((Utility)currentSquare).buy(player);
			}
			//current player is owner, no action is required
			else if(isPlayerOwner(((Utility) currentSquare).getTitleDeedCard(),player)) {
				System.out.println("You own this property.");
			}
			//another player is owner, must pay rent
			else player.payRent((CanOwn)currentSquare);
			break;
		default:
			break;	
		}
	}

	//check if the player has enough funds to complete a particular action
	public static boolean enoughFunds(Player player, int price) {
        return player.getMoney() >= price;
    }

    //reveal the current status of a player
    public static void checkPlayerStatus(Player player) {
		System.out.println(player.getName()+": You are currently at square "+player.getLocation()+", you have:\n\n"+player.getJailCard().size()
		+" Jail Free Cards\n"+player.getTitleDeedList().size()+" ownable properties\n"+player.getMoney()+" in cash \n\n");
	}
	//reveal the status of a players CanOwn list
	public static void checkPlayerCanOwnStatus(Player player){
	    System.out.println(player.getName() + " - Property Status: \n");
	    for(TitleDeed currentTitleDeed: player.getTitleDeedList()){
	        System.out.println(currentTitleDeed.getCardDesc());
	        if(currentTitleDeed.getOwnableSite() instanceof Property) {
                System.out.println("("+currentTitleDeed.getSquareColour()+")\n"+"Number of houses: "+((Property)currentTitleDeed.getOwnableSite()).getNumHouses()+
                        "\nNumber of Hotels: "+((Property)currentTitleDeed.getOwnableSite()).getNumHotels());
	        }
        }
    }
	//check if a particular property is available for purchase
	public static boolean canBuy(TitleDeed titleDeed) {
        return null == titleDeed.getOwner();
		}

	public static boolean isPlayerOwner(TitleDeed ownableCard, Player player){
		if(null == ownableCard.getOwner()){
			return false;
		}
		return ownableCard.getOwner().getName().equals(player.getName());
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
					propertyList.add((Property)currentOwnable);
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

	//check that the distribution of houses remains even in the case of buying or selling a house
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
		//loop through the colour group in question
		for(int i=0; i<colourGroup.size();i++) {
			if(colourGroup.get(i).getName().equals(propertyToAlterHouses.getName())) {
                for (Property property : colourGroup) {
                    if (!(((colourGroup.get(i).getNumHouses() - property.getNumHouses() >= houseDifferentialBounds[0])
							&& (colourGroup.get(i).getNumHouses() - property.getNumHouses() <= houseDifferentialBounds[1])))) {
                        return false;
                    }
                }
			}
		}
		return true;
	}

	//check if a property is eligible to be improved
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
		else if(!(Checks.isPlayerOwner(propToBuild.getTitleDeedCard(), player))){
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

	//check the combined value of all houses and hotels for a player
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

	//check the value that mortgaging all properties of a player will return
	public static int checkMortgagingValue(Player player) {
		int mortgageValue = 0;
		
		for(TitleDeed titleDeed : player.getTitleDeedList()) {
			//only add the value of properties that are not currently mortgaged or are in a provisional trade
			if(!titleDeed.getMortgageStatus() && titleDeed.getBankruptcyTradeStatus().isEmpty()) {
				mortgageValue += (titleDeed.getPriceBuy() / 2);
			}
		}
		return mortgageValue;
	}
	//check the value of the current negotiated provisional trades a bankrupt player has
	public static int checkBankruptcyTradeValue(Player player){
		int valOfTrades = 0;
		for(TitleDeed currentTitleDeed: player.getTitleDeedList()){
			if(!currentTitleDeed.getBankruptcyTradeStatus().isEmpty()){
				valOfTrades += (int)currentTitleDeed.getBankruptcyTradeStatus().keySet().toArray()[0];
			}
		}
		return  valOfTrades;
	}
	//This function will check the winner of the game by looping through the player list and checking who has the most money
	//Will return the player object that is the winner and then the main class will finish the game
	//Check winner will be called when the 2nd bankruptcy of the group of players occurs
	public static void checkWinner() {
		if(Game.playerList.size()==1){
			System.out.println(Game.playerList.get(0).getName()+" has won the game");
		}
		else {
			int totalValue = 0;
			ArrayList<Integer> valueArray = new ArrayList<>();
			int maxValue, maxIndex;

			for (Player player : Game.playerList) {
				totalValue += player.getMoney();
				for (TitleDeed titleDeed : player.getTitleDeedList()) {
					CanOwn ownable = titleDeed.getOwnableSite();
					if (ownable instanceof Property) {
						totalValue += ((Property) ownable).getNumHouses() * titleDeed.getHousePrice();
						totalValue += ((Property) ownable).getNumHotels() * titleDeed.getHousePrice() * 5; //A Hotel is 5 times the price of a house
					}
					totalValue += titleDeed.getPriceBuy();
					valueArray.add(totalValue);
					System.out.println("Player: " + player.getName() + " has Total Asset value of �" + totalValue);
				}
				//Zero the local variable for use with next player
				totalValue = 0;
			}
			maxValue = Collections.max(valueArray);
			maxIndex = valueArray.indexOf(maxValue);
			System.out.println("The richest player and winner of the game is: " + (Game.playerList.get(maxIndex).getName() + " with a Total Asset Value of �" + maxValue));
			System.out.println("The game has been won! It is now over");
			System.exit(1);
		}
	}
	
}
		
		

