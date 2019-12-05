package operations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import cards.TitleDeed;
import game.Board;
import game.Game;
import game.Player;
import squares.CanOwn;
import squares.Property;
import squares.Special;
import squares.Square;
import squares.Train;
import squares.Utility;

/**
 * This is the Checks class. We built this class as we had a lot of checking being made on inputs and different conditions inside methods of core classes.
 * We found that it would be better to locate them here instead. 
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public class Checks {

	//check if the game can still continue
	/**
	 * Method to check if a game can still continue
	 * @return 1 if playerList.size()>1 and numPlayersBankrupt < 2, 0 otherwise
	 */
	public static boolean checkIfValidGame(){
		return Game.playerList.size() != 1 && Game.numPlayersBankrupt < 2;
	}

	//check the square the player has landed on
	/**
	 * This is used to check the square that a player object lands on and handles whether you can buy it, if they are the owner already or 
	 * needing to pay rent.
	 * If it's a Special Square, it uses the implementSpecialSquare function, and Property, Train and Utility have similar functionality in terms of rent,
	 * building (Property only) and mortgaging
	 * @param index The index location on the board of the square
	 * @param player The Player object currently on that square
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 */
	public static void checkSquare(int index, Player player, BufferedReader userInput) {
		if(userInput==null){
			userInput = new BufferedReader(new InputStreamReader(System.in));
		}
		//get the current square
		Square currentSquare = Board.board.get(index);
		//check the square type
		switch(currentSquare.getSquareType()) {
		case PROPERTY:
			//property has no owner, offer to buy
			if(((Property)currentSquare).canBuy()){
				((Property)currentSquare).buy(player, userInput);
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
			if(((Train)currentSquare).canBuy()){
				((Train)currentSquare).buy(player, userInput);
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
			if(((Utility)currentSquare).canBuy()){
				((Utility)currentSquare).buy(player, userInput);
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

	/**
	 * Method to check if a player has enough funds to complete a specific action
	 * @param player The Player object in question
	 * @param price The amount of money needed to pay for the action
	 * @return 1 if they can pay, 0 if they can't
	 */
	public static boolean enoughFunds(Player player, int price) {
        return player.getMoney() >= price;
    }

    //reveal the current status of a player
	/**
	 * Current status of a player which is printed to the screen. It prints the location of the player, how many jail free cards they have, owned properties and cash.
	 *
	 * @param player The Player object which will have its data printed to the screen
	 */
    public static void checkPlayerStatus(Player player) {
		System.out.println(player.getName()+": You are currently at square "+player.getLocation()+", you have:\n\n"+player.getJailCard().size()
				+" Jail Free Cards\n"+player.getTitleDeedList().size()+" ownable properties\n"+player.getMoney()+" in cash \n\n");
	}
	//reveal the status of a players CanOwn list
	//FIXME
    /**
     * Prints out the status of a Player object's CanOwn list or Title Deed list. These are the CanOwn object's that the Player owns due to them holding
     * the TitleDeed card.
     * If a property is in their list of TitleDeed cards, it also presents how many houses and hotels are on the Properties.
     * @param player
     */
	public static void checkPlayerCanOwnStatus(Player player){
	    System.out.println(player.getName() + " - Title deed Status: \n");
	    for(TitleDeed currentTitleDeed: player.getTitleDeedList()){
	    	switch(currentTitleDeed.getOwnableSite().getClass().getSimpleName()){
				case "Property":
					((Property)currentTitleDeed.getOwnableSite()).printInstanceData();
					break;
				case "Train":
					((Train)currentTitleDeed.getOwnableSite()).printInstanceData();
					break;
				case "Utility":
					((Utility)currentTitleDeed.getOwnableSite()).printInstanceData();
					break;
				default:
					break;
			}
        }
    }



	/**
	 * This method is used to check if the TitleDeed card provided is owned by the Player object given as an argument
	 * @param ownableCard TitleDeed card which is being checked
	 * @param player The Player object being checked 
	 * @return true if player is owner, false if ownableCard has no owner or player is not owner
	 */
	public static boolean isPlayerOwner(TitleDeed ownableCard, Player player){
		if(null == ownableCard.getOwner()){
			return false;
		}
		return ownableCard.getOwner().getName().equals(player.getName());
	}

	//method to check if a player owns all the properties in a given colour group.
	/**
	 * This method is used to check if the Player object owns all Properties of a certain colour group which can be used for obtaining higher rent
	 * and building on these properties.
	 * It loops through the Player object's TitleDeed card searching for each owned site and their colour.
	 * @param player The Player Object being checked for owning all of a colour group
	 * @param property The type of property which is being checked which can be used to find the right colours
	 * @return null if no property provided or counter not satisifed, propertyList if they own all colours
	 */
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
	/**
	 * This checks for an even house distribution across the colour group of Properties. There cannot be greater than 1 house difference between properties 
	 * in the same colour group and this method checks that this is the case. 
	 * @param colourGroup The Colour group of Property objects
	 * @param propertyToAlterHouses A Property object which you want to alter 
	 * @param buyOrSell Buying or selling houses boolean
	 * @return true if even house distribution, false if not
	 */
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
	/**
	 * To check if a Property object can be built on with houses and hotels. It checks whether the Property is owned by the Player and whether the Player 
	 * has enough money. It then has to check if the Player owns all the Properties in a colour group 
	 * @param propToBuild The Property object to build on
	 * @param player The Player object who wants to build on propToBuild
	 * @return -1 attempt again, -2 exit without building anything, 0 you can build on this
	 */
	public static int canBuildHousesHotels(Property propToBuild, Player player){
		// Status codes:
		// - 1 - attempt again
		// - 2 - exit without building
		// return 0 - valid
		if(null==propToBuild) {
			if (InputOutput.yesNoInput("The property you have entered is invalid, would you like to try again? (y/n)", player, null)) {
				return -1;
			}
			else return -2;
		}
		else if(!(Checks.isPlayerOwner(propToBuild.getTitleDeedCard(), player))){
			if (InputOutput.yesNoInput("You do not own the property you have entered, would you like to try again? (y/n)", player, null)) {
				//restart pre-dice roll options
				return -1;
			} else return -2;
		}
		else if(player.getMoney() < propToBuild.getTitleDeedCard().getHousePrice()) {
			System.out.println("You do not have enough funds to purchase any houses/hotels for " + propToBuild.getName()
					+ "\nYour funds: " + player.getMoney() + "\nHouse Price: " + propToBuild.getTitleDeedCard().getHousePrice());
			return -2;
		}

		else if(null == Checks.ownAllColour(player,propToBuild)) {
			if (InputOutput.yesNoInput("You do not own all properties in this colour group, would you like to try again? (y/n)", player, null)) {
				//restart pre-dice roll options
				return -1;
			} else return -2;
		}
		else {
			return 0;
		}
	}

	//check the combined value of all houses and hotels for a player
	/**
	 * This method checks all of the combined value of hotels and houses and is particularly useful at the end of the game when we are checking
	 * who won the game because of asset value
	 * @param player The Player object who we are checking the house and hotel value for 
	 * @return valOfHousesHotels, the total value of all houses and hotels on owned sites
	 */
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
	/**
	 * Check the value of mortgaging CanOwn objects in the Player object's title deed list. 
	 * Useful for when we are checking how much money a player object needs to raise to get out of bankruptcy. 
	 * @param player Player who we are checking the value of their CanOwn objects' mortgages
	 * @return mortgageValue, total mortgage value of all of these
	 */
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
	/**
	 * This is used in the bankruptcy operation when a Player object is trying to obtain more money to pay off debts. This method
	 * checks the total value of a currently negotiated provisional trade with another player
	 * @param player Player object who has negotiated a provisional trade
	 * @return valOfTrades, total value of provisional trades
	 */
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
	//Check winner will be called when the 2nd bankruptcy of the group of players occurs
	/**
	 * This method determines the winner of the game when 2 players in the game have gone bankrupt. 
	 * If there is only one player left in the player list, then that player is declared the winner automatically. 
	 * If not, the method runs through each player in the player list and combines the value of all of their houses and hotels on sites 
	 * at their original buy price and also the value of all CanOwn objects owned (TitleDeed cards of these).
	 * 
	 * It will then compare the other players' net worth and whoever is highest wins the game and their total asset value is printed to the screen
	 */
	public static void checkWinner() {
		if(Game.playerList.size()==1){
			System.out.println(Game.playerList.get(0).getName()+" has won the game!");
		}
		//More than one player left
		else {
			int totalValue = 0;
			ArrayList<Integer> valueArray = new ArrayList<>();
			int maxValue, maxIndex;
			//Loop through remaining players and calculate their net worth
			for (Player player : Game.playerList) {
				totalValue += player.getMoney();
				for (TitleDeed titleDeed : player.getTitleDeedList()) {
					CanOwn ownable = titleDeed.getOwnableSite();
					if (ownable instanceof Property) {
						//Value of houses and hotels
						totalValue += ((Property) ownable).getNumHouses() * titleDeed.getHousePrice();
						totalValue += ((Property) ownable).getNumHotels() * titleDeed.getHousePrice() * 5; //A Hotel is 5 times the price of a house
					}
					//Value of CanOwn objects
					totalValue += titleDeed.getPriceBuy();
					valueArray.add(totalValue);
					System.out.println("Player: " + player.getName() + " has Total Asset value of £" + totalValue);
				}
				//Zero the local variable for use with next player
				totalValue = 0;
			}
			//Find max Asset value
			maxValue = Collections.max(valueArray);
			maxIndex = valueArray.indexOf(maxValue);
			//Winner determined
			System.out.println("The richest player and winner of the game is: " + (Game.playerList.get(maxIndex).getName() + " with a Total Asset Value of £" + maxValue));
			System.out.println("The Game has been won! It is now over!");
			System.exit(1);
		}
	}
	
}
		
		

