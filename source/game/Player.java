package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import cards.Card;
import cards.Chance;
import cards.CommunityChest;
import cards.TitleDeed;
import operations.Checks;
import operations.InputOutput;
import operations.Transactions;
import squares.*;
/*
 * This is the class for representing a player and contains all of the code and methods necessary to represent a player
 */
/**
 * This class describes a player object which are essential to the correct implementation of the game of Monopoly.
 * It handles all of the interaction with player movement, going to and leaving jail, purchasing of Properties and other squares and most importantly,
 * bankruptcy and how to save the player object from bankruptcy depending on a number of conditions
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public class Player {
	private String name; 	// The name of the player
	private String token; 	//Token that represents the player
	private int money; 		// The amount of money that the Player will have
	private int indexLocation; //The index of the square at which the player is on the board
	private ArrayList<TitleDeed> titleDeedCardList = new ArrayList<TitleDeed>(); //A list of the Title Deed cards being owned by the player
	private boolean inJail = false; //If they are in jail or not
	private ArrayList<Card> jailCards = new ArrayList<Card>(); //The array list which stores their title deed cards
	private int jailMoves = 0;				//How many moves they have been in jail for

	/**
	 * Class constructor. Initially the player receives £1500 as their money, they are set at location 0 or Go on the square
	 * @param name The name of the player
	 * @param token The token they will be represented on the board with. This is from an array of colours
	 */
	public Player(String name, String token) {
		this.name = name;
		this.token = token;
		this.money = 1500; //The starting amount of money you start with
		this.indexLocation = 0;
	}
	
	/**
	 * Gets the name of Player object
	 * @return this.name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Gets token of player object
	 * @return this.token
	 */
	public String getToken() {
		return this.token;
	}
	/**
	 * Gets the money of the player
	 * @return this.money
	 */
	public int getMoney() {
		return this.money;
	}
	/**
	 * Gets the player's current location
	 * @return this.indexlocation
	 */
	public int getLocation() {
		return this.indexLocation;
	}
	/**
	 * Get the title deed list of the player which shows what CanOwn objects they own
	 * @return this.titleDeedCardList
	 */
	public ArrayList<TitleDeed> getTitleDeedList(){
		return this.titleDeedCardList;
	}
	/**
	 * Get the jail card array which shows how many get out of jail free cards you have
	 * @return this.jailCards
	 */
	public ArrayList<Card> getJailCard() {
		return this.jailCards;
	}
	/**
	 * THis adds a get out of jail card to the jail card array list which you can use later to leave jail
	 * @param card The get out of jail free card you want to add to the ArrayList
	 */
	public void addJailCard(Card card) {
		this.jailCards.add(card);
	}
	/**
	 * Set the name of the Player object
	 * @param name Name as a String
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Setting the Player's money
	 * @param money The money in integer form which you want to set
	 */
	public void setMoney(int money){
		this.money = money;
	}
	/**
	 * Returns the number of moves they have been in jail for
	 * @return this.jailMoves
	 */
	public int getJailMoves(){
		return this.jailMoves;
	}
	/**
	 * Sets the amout of moves they have been in jail for
	 * @param numJailMoves The number of moves they have been in jail for
	 */
	public void setJailMoves(int numJailMoves){
		this.jailMoves=numJailMoves;
	}
	/**
	 * Setting the token of the player
	 * @param token Must be a colour in a specific array provided in String form
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * Add money to the player. Used as an income function
	 * @param money The amount of money to add to their accoutn
	 */
	public void addMoney(int money) {
		this.money+= money;
		if(money>0) System.out.println(this.getName()+ ", remaining Funds : £"+this.money);
	}
	/**
	 * Setting the player's location on the board
	 * @param index The location of the player on the board
	 */
	public void setLocation(int index) {
		this.indexLocation = index;
	}

	//Takes a playerOwed to see if the square is a player owed or the bank (in case of taxes and cards)
	/**
	 * This the method which reduces the amount of money they owe somebody or the Bank. The playerOwed is used to determine if the debt is due to another
	 * player such as rent and is set to null if the player owes the bank in terms of taxes etc.
	 * If the player cannot pay another player, then they are automatically bankrupt and this calls the bankrupt() method from this class which sells off the current
	 * player's assets.
	 * If the player cannot pay the bank, then this function calls saveFromBankruptcy() where we attempt to sell assets to obtain money to pay the debt
	 * If the player has enough money, then it reduces their balance
	 *
	 * @param money The money to reduce your balance by
	 * @param playerOwed The player object owed, null if owed to the bank
	 */
	public void reduceMoney(int money, Player playerOwed) {
		//Reduce money needs to see if there is enough money to do everything
		//This is if you are owing money to the bank......if you can raise enough, you can get around it
		//if there is no money to add, do nothing
		if(money!=0) {
			//check who is owed first
			if (playerOwed == null) {
				//can the player afford to pay the bank
				if (money > this.money) {
					//save from bankruptcy handles the reduction of money
					if (saveFromBankruptcy(money - this.money, null)) {
						System.out.println(this.name + ", remaining Funds: £" + this.money);
					}
					//cant afford, player is bankrupt
					else this.bankrupt(null);
				} else {
					//can afford, pay the amount
					this.money -= money;
					System.out.println(this.name + ", remaining Funds: £" + this.money);
				}
			}
			//a player is owed
			else {
				//can the player afford to pay the payee
				if (money > this.money) {
					//they are bankrupt to another player
					this.bankrupt(playerOwed);
				} else {
					//can afford to pay, reduce payer's money
					this.money -= money;
					System.out.println(this.name + ", remaining Funds: £" + this.money);
					//increment payees money
					playerOwed.addMoney(money);
				}
			}
		}
	}
	
	//This function will increment the location of the player
	/**
	 * This handles the movement of a player when they roll a dice or are told to move forward a certain amount of places.
	 * It handles the payment of £200 if the player passes or lands on Go
	 * @param moves The amount of squares to move forward
	 */
	public void movePlayer(int moves) {
		//FIXME change this 39 to be the GO SQUARE configuration
		if((this.getLocation()+ moves) >= 40) {
			//In this they are either on the square or they have now passed it
			this.indexLocation += (moves-40);
			this.addMoney(200); //Add �200 to the player's money because they have passed it
			System.out.println("You have passed go, you collect £200\n\nYour funds: "+this.getMoney());
		}
		else this.indexLocation = this.indexLocation + moves; //This moves the index location by moves
	}
	/**
	 * Moving to a particular square. Handles payment at Go also
	 *
	 * @param squareNum The square number on the board to move to
	 */
	public void moveToSquare(int squareNum) {
		if(this.getLocation() > squareNum || this.indexLocation == 0) {
		    System.out.println("You have passed go, collect £200.");
			this.addMoney(200); //This implies that they have passed GO
		}
		this.indexLocation = squareNum;
	}

	/**
	 * Picking a Community Chest card from the top of the deck or the start of the communityChests ArrayList
	 * If it is a get out of jail card, it removes it from the deck and places it in the Player's JailCard ArrayList.
	 * It runs the dealWithCard method from CommunityChest.java then to execute actions on the given card type
	 */
	public void pickCommChestCard() {
		//The card deck will be shuffled and so I will need to take this card and then call the 
		CommunityChest pickedCard = Board.communityChests.get(0);
		//If it is a get out of jail card, keep it and don't return to the pile
		if(!pickedCard.getCardType().equals("GET_OUT_OF_JAIL")) {
			Board.communityChests.remove(0);
			Board.communityChests.add(pickedCard);
		}
		else {
			//Remove the get out of jail card from the pile
			Board.communityChests.remove(0);
		}
		//This will implement the card
		pickedCard.dealWithCard(this, null);
	}
	/**
	 * Picking a Chance card from the top of the deck or the start of the chances ArrayList
	 * If it is a get out of jail card, it removes it from the deck and places it in the Player's JailCard ArrayList.
	 * It runs the dealWithCard method from Chance.java then to execute actions on the given card type
	 */
	public void pickChanceCard() {
		//The card deck will be shuffled and so I will need to take this card and then call the 
		Chance pickedCard = Board.chances.get(0);
		//If it is a get out of jail card, keep it and don't return to the pile
		if(!pickedCard.getCardType().equals("GET_OUT_OF_JAIL")) {
			Board.chances.remove(0);
			Board.chances.add(pickedCard);
		}
		else {
			//Remove the get out of jail card from the pile
			Board.chances.remove(0);
		}
		//This will implement the card
		pickedCard.dealWithCard(this, null);
	}
	/**
	 * Used to see if the player is in jail
	 * @return inJail, jail status
	 */




	public boolean isInJail() {
		return inJail; //Return inJail status
	}
	/**
	 * Set the player in jail
	 * @param jailStatus Boolean to determine if they should be put in jail
	 */
	public void setInJail(boolean jailStatus){
	    this.inJail = jailStatus;
    }



	public void bankrupt(Player playerOwed) {
		BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
		//Need to check if it is a player that you owe money to. 
		//If it is a player, turn over all of value to that player
		Game.playerList.remove(this);
		if(!Checks.checkIfValidGame()){
			Checks.checkWinner();
		}
		else {
			if (playerOwed == null) {
				//Bank owed
				//Get rid of jail free card
				while (this.jailCards.size() > 0) {

						Card tempJail = this.jailCards.get(0);
						this.jailCards.remove(0);
						if (tempJail instanceof CommunityChest) {
							Board.communityChests.add((CommunityChest) tempJail);
						} else {
							Board.chances.add((Chance) tempJail);
						}
						//Need to check that the card was actually removed
				}
				System.out.println("Bankrupt player Jail Card array now of size: " + this.jailCards.size());
				for (int i = 0; i < titleDeedCardList.size(); i++) {
					CanOwn property = titleDeedCardList.get(i).getOwnableSite();
					if (property instanceof Property) {
						if (((Property) property).getNumHouses() > 0) {
							Game.setRemainingHouses(Game.getRemainingHouses() + ((Property) property).getNumHouses());
							((Property) property).setNumHouses(0);
						} else if (((Property) property).getNumHotels() > 0) {
							Game.setRemainingHotels(Game.getRemainingHotels() + ((Property) property).getNumHotels());
							((Property) property).setNumHotels(0);
						} else {
							System.out.println("There is no hotels or houses on this property to be sold!");
						}
					}

					//Remove the property from their List of Owned properties and now the bank will auction the property
					this.titleDeedCardList.get(i).setOwner(null);
					this.titleDeedCardList.remove(i);
					i--;
					System.out.println("Property will now be auctioned");
					property.getTitleDeedCard().playerAuction(null, userInput);
				}

				System.out.println("Bankrupt player, " + this.getName() + ", has retired from the game!");
				Game.numPlayersBankrupt++; //Increase the number of players bankrupt

			} else {
				System.out.println("You are bankrupt to " + playerOwed.getName());
				if (this.money > 0) {
					playerOwed.addMoney(this.money);
				}
				if (this.titleDeedCardList.size() > 0) {
					for (TitleDeed currentTitleDeed : this.titleDeedCardList) {
						this.titleDeedCardList.remove(currentTitleDeed);
						playerOwed.addPurchasedTitleDeed(currentTitleDeed);
					}
				}
				if (this.jailCards.size() > 0) {
					for (Card jailCard : this.jailCards) {
						playerOwed.addJailCard(jailCard);
					}
				}
				Checks.checkPlayerStatus(playerOwed);
			}
		}
	}

	/**
	 * Adding a purchased title deed card to the title deed card list of the Player objet and setting the owner of the Title Deed
	 * card to be the player object
	 * @param purchasedProperty The TitleDeed card object of the purchased property
	 */
	public void addPurchasedTitleDeed(TitleDeed purchasedProperty) {
		this.titleDeedCardList.add(purchasedProperty);
		purchasedProperty.setOwner(this);
	}
	
	/**
	 * Removing a title deed card in their title deed card list
	 * @param titleDeed The TitleDeed card object to remove
	 */
	public void removeOwnedTitleDeed(TitleDeed titleDeed) {
		int index = this.titleDeedCardList.indexOf(titleDeed);
		//Detects that the property object is contained in Property List
		if(index >= 0) {
			this.titleDeedCardList.remove(index);
		}
		else {
			System.out.println("You cannot remove a Property when it is not in your Property List!");
		}
	}
	/**
	 * This is the payRent function which prompts the owner of the CanOwn object to ask the current player on the square to pay rent.
	 * They can either ask for rent or not. The player on the square must then act accordingly .
	 * This method calculates how much rent a player will pay depending on how many houses or hotels or properties of the same colour are owned
	 * If the current player cannot pay, then they go bankrupt
	 * @param ownableSquare The CanOwn object to pay rent on
	 */
	public void payRent(CanOwn ownableSquare) {
		//Going to take the ownable square and work with it from there
		//Ask the player who owns it first whether they want to pay rent or not
		Player owner = ownableSquare.getTitleDeedCard().getOwner();
		System.out.println(owner.getName());
		TitleDeed titleDeedCard = ownableSquare.getTitleDeedCard();
		if(titleDeedCard.getMortgageStatus()) {
			System.out.println("This square is mortgaged and so no rent can be claimed on it!");
		}
		else {
			if(InputOutput.yesNoInput("Player, "+owner.getName()+", would you like the current player, "+this.name+", to pay rent?", owner,null)) {
				//This means i need to claim rent from the user
				//Need to check what type of property it is followed by how much rent they will need to pay
				if(ownableSquare instanceof Property) {
					int numHouses = ((Property)ownableSquare).getNumHouses();
					int numHotels = ((Property)ownableSquare).getNumHotels();
					//Below is for if you own all of the properties but they are not improved,
					//Charge double rent
					if(Checks.ownAllColour(this, (Property)ownableSquare) != null && numHouses + numHotels == 0) {
						this.reduceMoney(2*(titleDeedCard.getRents()[0]), titleDeedCard.getOwner());
					}
					else if(numHotels == 0) {
						//This is for if you own all the properties in a colour group but you have houses
						this.reduceMoney(titleDeedCard.getRents()[numHouses], titleDeedCard.getOwner());
					}
					else {
						//This is for if there is a hotel on the site. Max 1
						this.reduceMoney(titleDeedCard.getRents()[5], titleDeedCard.getOwner());
					}	
				}
				else if(ownableSquare instanceof Train) {
					//Rent for a train is size 4
					//Check the amount of trains that an owner has
					int numTrains=0;
					for(TitleDeed titleDeedTrain:owner.getTitleDeedList()) {
						CanOwn ownedSquare = titleDeedTrain.getOwnableSite();
						if(ownedSquare instanceof Train) {
							numTrains++;
						}
						else {
							continue;
						}
					}
					this.reduceMoney(titleDeedCard.getRents()[numTrains-1], titleDeedCard.getOwner());
				}
				else {
					//Rent payment for a utility
					//Check the amount of utilities that an owner has
					int numUtilities=0;
					System.out.println(owner.getTitleDeedList().size());
					for(TitleDeed titleDeedUtility:owner.getTitleDeedList()) {
						
						CanOwn ownedSquare = titleDeedUtility.getOwnableSite();
						if(ownedSquare instanceof Utility) {
							numUtilities++;
						}
						else {
							continue;
						}
					}
					this.reduceMoney((titleDeedCard.getRents()[numUtilities-1])*Dice.getDieVals(), titleDeedCard.getOwner());
				}
			}
			else {
				System.out.println("Owner of Square does not require rent to be paid");
			}
		}
	}
	

	/**
	 * Used to mortgage a CanOwn object when the player goes bankrupt to the bank
	 * @param moneyNeedToRaise The amount of money needed to raise to pay the debt
	 */
	public void bankruptcyMortgage(int moneyNeedToRaise) {
		for(TitleDeed toMortgage:this.titleDeedCardList) {
			if(toMortgage.getBankruptcyTradeStatus().isEmpty() && !toMortgage.getMortgageStatus()) {
				CanOwn ownable = toMortgage.getOwnableSite();
				ownable.mortgage(this,true);
				if (this.money - moneyNeedToRaise > 0) {
					//exit the method as soon as the limit has been reached
					return;
				}
			}
		}
	}
	/**
	 * Selling houses and hotels when bankrupt to bank
	 * @param moneyNeedToRaise The amount of money needed to raise to pay the debt
	 */
	public void bankruptcySellHousesHotels(int moneyNeedToRaise) {
		for(TitleDeed titleDeed:this.titleDeedCardList) {
			CanOwn ownable = titleDeed.getOwnableSite();
			if(ownable instanceof Property) {
				this.addMoney(((Property)ownable).sellHouses(this,false,true));
				this.addMoney(((Property)ownable).sellHotels(this,false,true));
			}
			//If they have raised sufficient money from sellingHouses
			if(this.money - moneyNeedToRaise > 0) {
				return;
			}
		}
	}

	/**
	 * Complete the trade of a CanOwn object's TitleDeed card when in bankruptcy in order to raise money
	 * @param userInput
	 */
	public void completeBankruptcyTrade(BufferedReader userInput){
		if(userInput==null){userInput = new BufferedReader(new InputStreamReader(System.in));}
		for(TitleDeed currentTitleDeed: titleDeedCardList){
			if(!currentTitleDeed.getBankruptcyTradeStatus().isEmpty()){
				this.addMoney((int)currentTitleDeed.getBankruptcyTradeStatus().keySet().toArray()[0]);
				currentTitleDeed.getBankruptcyTradeStatus().get(currentTitleDeed.getBankruptcyTradeStatus().keySet().toArray()[0]).addPurchasedTitleDeed(currentTitleDeed);

				if(currentTitleDeed.getMortgageStatus()){
					Transactions.handleMortgagedTitledeed(currentTitleDeed.getBankruptcyTradeStatus().get(currentTitleDeed.getBankruptcyTradeStatus().keySet().toArray()[0]),currentTitleDeed,userInput);
				}
				currentTitleDeed.getBankruptcyTradeStatus().clear();
			}
		}
	}
	
	/**
	 * This method tries to save a player from bankruptcy when they cannot pay a debt to the bank.
	 * It values the houses and hotels on the CanOwn objects and tells you the value they have.
	 * It will then make preliminary trades with other Players in the player list. It will enter into a player to player trade.
	 * The other player will bid a price and then that value will be stored temporarily but the CanOwn object's TitleDeed card will not change ownership until it is
	 * confirmed that the bankrupt player can be saved from bankruptcy. It will continue to ask you to make more trades if you need to make up more money.
	 *
	 * If you were saved from bankruptcy, then it completes the trades, completes the mortgaging of CanOwn objects and selling of houses and hotels.
	 * If not, it clears the bankruptcy status and everything is handled by the bank in terms of selling houses/hotels and then auctioning of CanOwn TitleDeed cards
	 * @return true if saved, false if not
	 */
	public boolean saveFromBankruptcy(int moneyNeedToRaise, BufferedReader userInput) {
		if(userInput==null){userInput = new BufferedReader(new InputStreamReader(System.in));}
		boolean savedFromBankruptcy = false;
		boolean mustSellHouseHotels=false;
		boolean mustMortgage=false;

		int valOfHouseHotels = Checks.checkHouseHotelValue(this);
		System.out.println(valOfHouseHotels +" "+ moneyNeedToRaise);
		if(valOfHouseHotels > moneyNeedToRaise) mustSellHouseHotels = true;

		int valOfMortgage = Checks.checkMortgagingValue(this);
		System.out.println(valOfMortgage);
		if (valOfHouseHotels + valOfMortgage > moneyNeedToRaise) mustMortgage = true;

		if(mustSellHouseHotels) {
			bankruptcySellHousesHotels(moneyNeedToRaise);
			return true;
		}
		else if(mustMortgage) {
			bankruptcySellHousesHotels(moneyNeedToRaise);
			bankruptcyMortgage(moneyNeedToRaise);
			return true;
		}
		else {
			if(InputOutput.yesNoInput("The combined value of mortgaging all properties ("+valOfMortgage
					+") and selling all houses ("+valOfHouseHotels+") is insufficient to cover your debt ("+moneyNeedToRaise+")"+
                    "\nWould you like to attempt to trade items with other players in order to raise additional funds? (y/n)", this, userInput)){
			    boolean continueTrade = true;
			    while(continueTrade) {

                    Transactions.saveFromBankruptcyTrade(this, userInput);
                    valOfMortgage = Checks.checkMortgagingValue(this);

                    int valOfBankruptcyTrade = Checks.checkBankruptcyTradeValue(this);

                    if(valOfHouseHotels+valOfMortgage+valOfBankruptcyTrade>moneyNeedToRaise){
                    	System.out.println("With the preliminary trades you have made, you have raised enough money to avoid bankruptcy");
                    	continueTrade=false;
                    	savedFromBankruptcy = true;
					}
                    else if (!InputOutput.yesNoInput("You still do not have enough funds to prevent bankruptcy."+
							"\nOutstanding Balance: "+(moneyNeedToRaise-(valOfBankruptcyTrade+valOfHouseHotels+valOfMortgage))+
							"\nWould you like to make another trade? (y/n)", this, userInput)) {

                    	continueTrade=false;
                    }
                    else continue;
                }
            }
			if(savedFromBankruptcy){
				bankruptcySellHousesHotels(moneyNeedToRaise);
				bankruptcyMortgage(moneyNeedToRaise);
				completeBankruptcyTrade(userInput);
				return true;
			}
			else {
				System.out.println("You were unable to save yourself from bankruptcy");
				clearBankruptcyTradeStatus();
				return false;
			}
		}
	}
	/**
	 * Clears all of the preliminary trades made for the bankruptcy trades
	 */
	public void clearBankruptcyTradeStatus(){
		for(TitleDeed currentTitleDeed: this.titleDeedCardList){
			if(!currentTitleDeed.getBankruptcyTradeStatus().isEmpty()){
				//give money back
				currentTitleDeed.getBankruptcyTradeStatus().get(currentTitleDeed.getBankruptcyTradeStatus().keySet().toArray()[0]).addMoney((int)currentTitleDeed.getBankruptcyTradeStatus().keySet().toArray()[0]);
				currentTitleDeed.getBankruptcyTradeStatus().clear();
			}
		}
	}

	/**
	 * Prints out the values of the Player
	 */
	public String toString() {
		return "Details of: "+this.name+
				"\nToken: "+this.token+"\nMoney: "+this.money+"\nSquare Location: "+this.indexLocation+
				"\nIs In Jail?: "+this.inJail+"\n Title Deed Card List"+this.titleDeedCardList;
	}
}
