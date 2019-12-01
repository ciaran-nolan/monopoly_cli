package ie.ucd.game;
import java.util.*;
/*
 * This is the class for representing a player and contains all of the code and methods necessary to represent a player
 */
public class Player {
	private String name; 	// The name of the player
	private String token; 	//Token that represents the player
	private int money; 		// The amount of money that the Player will have
	private int indexLocation; //The index of the square at which the player is on the board
	private ArrayList<TitleDeed> titleDeedCardList = new ArrayList<TitleDeed>(); //A list of the properties being owned by the player
	private boolean inJail = false; //If they are in jail or not
	private ArrayList<Card> jailCards = new ArrayList<Card>();
	private int jailMoves = 0;

	
	public Player(String name, String token) {
		this.name = name;
		this.token = token;
		this.money = 1500; //The starting amount of money you start with
		this.indexLocation = 0;
	}
	
	//GETTERS
	public String getName() {
		return this.name;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public int getLocation() {
		return this.indexLocation;
	}
	
	public ArrayList<TitleDeed> getTitleDeedList(){
		return this.titleDeedCardList;
	}

	public ArrayList<Card> getJailCard() {
		return this.jailCards;
	}
	
	public void addJailCard(Card card) {
		this.jailCards.add(card);

	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setMoney(int money){
		this.money = money;
	}

	public int getJailMoves(){ return this.jailMoves;}
	public void setJailMoves(int numJailMoves){ this.jailMoves=numJailMoves;}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void addMoney(int money) {
		this.money+= money;
		if(money>0) System.out.println(this.getName()+ ", remaining Funds : £"+this.money);
	}
	
	public void setLocation(int index) {
		this.indexLocation = index;
	}

	//Takes a playerOwed to see if the square is a player owed or the bank (in case of taxes and cards)
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
					if (saveFromBankruptcy(money - this.money)) {
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
	//moves is the amount of the 
	public void movePlayer(int moves) {
		//FIXME change this 39 to be the GO SQUARE configuration
		if((this.getLocation()+ moves) >= 40) {
			//In this they are either on the square or they have now passed it
			this.indexLocation += (moves-40);
			this.addMoney(200); //Add $200 to the player's money because they have passed it
			System.out.println("You have passed go, you collect £200\n\nYour funds: "+this.getMoney());
		}
		else this.indexLocation = this.indexLocation + moves; //This moves the index location by moves
	}
	
	public void moveToSquare(int squareNum) {
		if(this.getLocation() > squareNum || this.indexLocation == 0) {
		    System.out.println("You have passed go, collect £200.");
			this.addMoney(200); //This implies that they have passed GO
		}
		this.indexLocation = squareNum;
	}


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
			this.addJailCard(pickedCard);
		}
		//This will implement the card
		pickedCard.dealWithCard(this);
	}
	
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
			this.addJailCard(pickedCard);
		}
		//This will implement the card
		pickedCard.dealWithCard(this);
	}
	
	public boolean isInJail() {
		return inJail; //Return inJail status
	}
	public void setInJail(boolean jailStatus){
	    this.inJail = jailStatus;
    }
	

	public static ArrayList<Player> createListPlayers(){
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		ArrayList<String> tokenList = new ArrayList<String>(Arrays.asList("blue", "red", "green" , "black", "orange", "yellow"));
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
			System.out.println("How many players will be playing the game(In range 2-6)?");
			int numPlayers = InputOutput.integerMenu(2, 6);
			System.out.println("You have specified "+numPlayers+" players to play the game");
			//Get the players to be entered by the user -> Have a loop that asks for that number of players.
			if(numPlayers >= 2 && numPlayers <= 6) {
				for(int i=0; i < numPlayers; i++) {
					String line;
					String[] lineVector = new String[2];
					String name, token;
					while(true) {
						System.out.println("Please enter the relevant details for each player in the format below:");
						System.out.println("\t\t\tName, Token");
						System.out.println("Token picked must be one of the following: "+tokenList+
								"\n----------------------------------------------------------------");
						line = scanner.nextLine();
						//Trim the whitespace first before splitting
						if(!line.contains(","))	System.err.println("Please use a comma (,) to separate input");
						else{
							lineVector = line.split(",");
							name = lineVector[0].trim();
							token = lineVector[1].trim();
							if (!tokenList.contains(token)) {
								System.err.println("Token not part of list. Enter details of Player again!");
								continue;
							}
							else if (name.length() == 0) {
								System.err.println("Name of Player has not been entered or is not valid. Enter details of Player again!\n");
								continue;
							}
							else {
								//Remove token from the array
								tokenList.remove(token);
								//Do I need to access them using the index though. I suppose I will go through them in
								listPlayers.add(new Player(name, token)); //This will add a new player to the list of players
								//I have added each player in the range of numPlayers to be in the listPlayers array.
								//Running this will return the list of players which I can set in main
								break;
							}
						}
					}
				}
			}
			
		
		//Inside of the main, we will return this list of players
		return listPlayers;
	}
	
	//
	public void bankrupt(Player playerOwed) {
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
				if (this.jailCards.size() > 0) {
					//Need to transfer the get out of jail card to the playerOwed
					for (Card card : this.jailCards) {
						//Remove it and then send to the new owner
						this.jailCards.remove(card);
						if (card instanceof CommunityChest) {
							Board.communityChests.add((CommunityChest) card);
						} else {
							Board.chances.add((Chance) card);
						}
						//Need to check that the card was actually removed
						System.out.println("Bankrupt player Jail Card array now of size: " + this.jailCards.size());
					}
				}
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
					property.getTitleDeedCard().playerAuction(null);
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
		

	public void addPurchasedTitleDeed(TitleDeed purchasedProperty) {
		this.titleDeedCardList.add(purchasedProperty);
		purchasedProperty.setOwner(this);
	}
	
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
	
	public void payRent(CanOwn ownableSquare) {
		//Going to take the ownable square and work with it from there
		//Ask the player who owns it first whether they want to pay rent or not
		Player owner = ownableSquare.getTitleDeedCard().getOwner();
		TitleDeed titleDeedCard = ownableSquare.getTitleDeedCard();
		if(titleDeedCard.getMortgageStatus()) {
			System.out.println("This square is mortgaged and so no rent can be claimed on it!");
		}
		else {
			if(InputOutput.yesNoInput("Player, "+owner.getName()+", would you like the current player, "+this.name+", to pay rent?", owner)) {
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
	

	//This function will be used to save a person from bankruptcy using the amount of money in argument as what is needed to raise to pay off any off debts from 
	//the reduceMoney() function
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

	public void completeBankruptcyTrade(){
		for(TitleDeed currentTitleDeed: titleDeedCardList){
			if(!currentTitleDeed.getBankruptcyTradeStatus().isEmpty()){
				this.reduceMoney((int)currentTitleDeed.getBankruptcyTradeStatus().keySet().toArray()[0],null);
				currentTitleDeed.setOwner(currentTitleDeed.getBankruptcyTradeStatus().get(currentTitleDeed.getBankruptcyTradeStatus().keySet().toArray()[0]));
				System.out.println(currentTitleDeed.getOwner().getName());
				currentTitleDeed.getBankruptcyTradeStatus().clear();
			}
		}
	}
	
	public boolean saveFromBankruptcy(int moneyNeedToRaise) {
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
                    "\nWould you like to attempt to trade items with other players in order to raise additional funds? (y/n)", this)){
			    boolean continueTrade = true;
			    while(continueTrade) {

                    Transactions.saveFromBankruptcyTrade(this);
                    valOfMortgage = Checks.checkMortgagingValue(this);

                    int valOfBankruptcyTrade = Checks.checkBankruptcyTradeValue(this);

                    if(valOfHouseHotels+valOfMortgage+valOfBankruptcyTrade>moneyNeedToRaise){
                    	System.out.println("With the preliminary trades you have made, you have raised enough money to avoid bankruptcy");
                    	continueTrade=false;
                    	savedFromBankruptcy = true;
					}
                    else if (!InputOutput.yesNoInput("You still do not have enough funds to prevent bankruptcy."+
							"\nOutstanding Balance: "+(moneyNeedToRaise-(valOfBankruptcyTrade+valOfHouseHotels+valOfMortgage))+
							"\nWould you like to make another trade? (y/n)", this)) {

                    	continueTrade=false;
                    }
                    else continue;
                }
            }
			if(savedFromBankruptcy){
				bankruptcySellHousesHotels(moneyNeedToRaise);
				bankruptcyMortgage(moneyNeedToRaise);
				completeBankruptcyTrade();
				return true;
			}
			else {
				System.out.println("You were unable to save yourself from bankruptcy");
				clearBankruptcyTradeStatus();
				return false;
			}
		}
	}
	public void clearBankruptcyTradeStatus(){
		for(TitleDeed currentTitleDeed: this.titleDeedCardList){
			if(!currentTitleDeed.getBankruptcyTradeStatus().isEmpty()){
				//give money back
				currentTitleDeed.getBankruptcyTradeStatus().get(currentTitleDeed.getBankruptcyTradeStatus().keySet().toArray()[0]).addMoney((int)currentTitleDeed.getBankruptcyTradeStatus().keySet().toArray()[0]);
				currentTitleDeed.getBankruptcyTradeStatus().clear();
			}
		}
	}

	public String toString() {
		return "Details of: "+this.name+
				"\nToken: "+this.token+"\nMoney: "+this.money+"\nSquare Location: "+this.indexLocation+
				"\nIs In Jail?: "+this.inJail+"\n Title Deed Card List"+this.titleDeedCardList;
	}
	
}
