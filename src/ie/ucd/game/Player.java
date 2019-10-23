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
	private ArrayList<CanOwn> propertyList = new ArrayList<CanOwn>(); //A list of the properties being owned by the player
	private int jailFreeCard; // This is used to see if the Player has a Get out of Jail Free card which can be used
	private boolean inJail = false; //If they are in jail or not
	private ArrayList<Card> jailCards = new ArrayList<Card>();
	//FIXME CONSIDER Perhaps a card Array to show what cards you have
	
	public Player(String name, String token) {
		this.name = name;
		this.token = token;
		this.money = 1500; //The starting amount of money you start with
		this.indexLocation = 0;
		this.jailFreeCard = 0;
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
	
	public ArrayList<CanOwn> getPropertyList(){
		return this.propertyList;
	}
	
	public int getJailFreeNum() {
		return this.jailFreeCard;
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
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void addMoney(int money) {
		this.money+= money;
	}
	
	public void setLocation(int index) {
		this.indexLocation = index;
	}
	
	public void setJailFree() {
		this.jailFreeCard++;
	}
	//Takes a playerOwed to see if the square is a player owed or the bank (in case of taxes and cards)
	public void reduceMoney(int money, Player playerOwed) {
		//Reduce money needs to see if there is enough money to do everything
		//This is if you are owing money to the bank......if you can raise enough, you can get around it
		if(money > this.money && playerOwed == null) {
			//It will take an argument of the amount of money needed raise
			if(this.saveFromBankruptcy(money-this.money)) {
				//I have saved from bankruptcy and so I can now reduce the money
				this.money-=money;
			}
			else {
				//If its null it is paying the bank. If it is not null, it owes a player money on that square
				this.isBankrupt(playerOwed);
			}
		}
		//You owe a player and if 
		else if(money > this.money && playerOwed != null) {
			this.isBankrupt(playerOwed);
		}
		//Else you have enough money for paying the bill and so you can just reduce the money
		else {
			this.money-=money;
		}
	}
	
	//This function will increment the location of the player
	//moves is the amount of the 
	public void movePlayer(int moves) {
		//FIXME change this 39 to be the GO SQUARE configuration
		if((this.getLocation()+ moves) >= 39) {
			//In this they are either on the square or they have now passed it 
			this.addMoney(200); //Add $200 to the player's money because they have passed it
		}
		this.indexLocation = this.indexLocation + moves; //This moves the index location by moves 
	}
	
	public void moveToSquare(int squareNum) {
		if(this.getLocation() > squareNum) {
			this.addMoney(200); //This implies that they have passed GO
		}
		this.indexLocation = squareNum;
	}
	
	public boolean goToJail() {
		ArrayList<Chance> chanceDeck = BoardReader.getChances();
		ArrayList<CommunityChest> commChestDeck = BoardReader.getCommunityChests();
		//You need to check if the location of the Player is at the index location of the square for that and whether they have gotten a go to jail card
		if(this.jailFreeCard < 1) {
			this.indexLocation = 10; //FIXME CHANGE TO THE INDEX OF JAIL.....OR EVEN ENUMERATE THE INDEX
			return this.inJail = true;
		}
		else {
			int currPos = 0;
			this.jailFreeCard--;
			//I now need to add the card back into the relevant array. I can see that by the Array that is less than 16 
			for(Card card:this.jailCards){
				if(card instanceof CommunityChest) {
					this.jailCards.remove(currPos);
					commChestDeck.add((CommunityChest)card);
				}
				if(card instanceof Chance) {
					this.jailCards.remove(currPos);
					chanceDeck.add((Chance)card);
				}
				currPos++;
			}
			//Check if they still have one card
			if(this.jailFreeCard > 0) {
				System.out.println("You have a get out of jail free card still");
				return this.inJail = false;
			}
			else {
				this.indexLocation = 10; 
				return this.inJail = true;
			}
			//RETURN THE CARD TO THE LIST as the user has now used it
			//For the jail card, add it and then delete it from the array of cards. When you are done with it and want to return it, 
			//just add it to the end of the card 
		}
	
	}
	
	public void pickCommChestCard() {
		//The card deck will be shuffled and so I will need to take this card and then call the 
		CommunityChest pickedCard = BoardReader.communityChests.get(0);
		//If it is a get out of jail card, keep it and don't return to the pile
		if(pickedCard.getCardType() != "GET_OUT_OF_JAIL") {
			BoardReader.communityChests.remove(0);
			BoardReader.communityChests.add(pickedCard);
		}
		else {
			//Remove the get out of jail card from the pile
			BoardReader.communityChests.remove(0);
			this.addJailCard(pickedCard);
		}
		//This will implement the card
		pickedCard.dealWithCard(this);
	}
	
	public void pickChanceCard() {
		//The card deck will be shuffled and so I will need to take this card and then call the 
		Chance pickedCard = BoardReader.chances.get(0);
		//If it is a get out of jail card, keep it and don't return to the pile
		if(pickedCard.getCardType() != "GET_OUT_OF_JAIL") {
			BoardReader.chances.remove(0);
			BoardReader.chances.add(pickedCard);
		}
		else {
			//Remove the get out of jail card from the pile
			BoardReader.chances.remove(0);
			this.addJailCard(pickedCard);
		}
		//This will implement the card
		pickedCard.dealWithCard(this);
	}
	
	public boolean isInJail() {
		return inJail; //Return inJail status
	}
	
	public int leftInPrison() {
		return 1;
		//return turns left in prison FIXME
	}
	public boolean leavePrison() {
		return true;
		//FIXME Checks whether they can leave prison or not
	}
	//Functions to consider: FIXME
		//Remove player from the game 
		//isBankrupt function 
		//buying a square from another player
		//toString
	
	public void removePlayer(ArrayList<Player> playerList) {
		 int indexPlayer = playerList.indexOf(this);
		 if(indexPlayer >= 0) {
			 System.out.println("Removing Player at Index "+indexPlayer+" from game!");
			 playerList.remove(indexPlayer);
		 }
		 else {
			 System.out.println("This player is not part of the Player List");
		 }
	}
	
	public void addPlayer(ArrayList<Player> playerList) {
		if(!playerList.contains(this)) {
			System.out.println("Adding Player to Player List");
			playerList.add(this);
		}
		else {
			System.out.println("Player is already a member of the Player List");
		}
	}

	public static ArrayList<Player> createListPlayers(){
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		ArrayList<String> tokenList = new ArrayList<String>(Arrays.asList("blue", "red", "green" , "black", "orange", "yellow"));
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println("How many players will be playing the game(In range 2-6)?");
			int numPlayers = scanner.nextInt();
			System.out.println("You have specified "+numPlayers+" players to play the game");
			//Get the players to be entered by the user -> Have a loop that asks for that number of players.
			if(numPlayers >= 2 && numPlayers <= 6) {
				for(int i=0; i < numPlayers; i++) {
					String line;
					String[] lineVector;
					String name, token;
					while(true) {
						System.out.println("Please enter the relevant details for each player in the format below");
						System.out.println("Name, Token");
						System.out.println("Token must be one of the following: "+tokenList);
						line = scanner.nextLine();
						lineVector = line.split(",");
						name = lineVector[0];
						token = lineVector[1];
						if(!tokenList.contains(token)) {
							System.out.println("Token not part of list. Enter details of Player again.");
							continue;
						}
						else {
							//Remove the index of the token from the array
							tokenList.remove(tokenList.indexOf(token));
							//Do I need to access them using the index though. I suppose I will go through them in 
							listPlayers.add(new Player(name, token)); //This will add a new player to the list of players
							//I have added each player in the range of numPlayers to be in the listPlayers array. 
							//Running this will return the list of players which I can set in main
							break;
						}
					}
				}
				//When got through all of the players, break the infinite loop
				break;
			}
			else {
				System.out.println("Please enter a number of players between 2 and 6!");
				continue;
			}
		}
		//Inside of the main, we will return this list of players
		return listPlayers;
	}
	
	//This function will see if a player is bankrupt.
	//FIXME this is very much a temporary thing until we get the bankrupt functions looking to see if they can pay rent the whole time etc
	public boolean isBankrupt(Player playerOwed) {
		//Need to check if it is a player that you owe money to. 
		//If it is a player, turn over all of value to that player
		if(playerOwed == null) {
			//Bank owed
			//Get rid of jail free card
			int currPos = 0;
			int currPosJail = 0;
			if(this.jailCards.size()> 0) {
				//Need to transfer the get out of jail card to the playerOwed
				for(Card card:this.jailCards) {
					//Remove it and then send to the new owner
					Card temp = this.jailCards.remove(currPosJail);
					if(temp instanceof CommunityChest) {
						BoardReader.communityChests.add((CommunityChest)temp);
					}
					else {
						BoardReader.chances.add((Chance)temp);
					}
					//Need to check that the card was actually removed
					System.out.println("Bankrupt player Jail Card array now of size: "+this.jailCards.size());
					currPosJail++;
				}
			}
			//Sell off any properties and buildings
			
			for(CanOwn property:this.propertyList) {
				//Sell off all of the houses at no price
				if(property instanceof Property) {
					if(((Property)property).getNumHouses()>0) {
						Game.setRemainingHouses(Game.getRemainingHouses()+((Property)property).getNumHouses());
						((Property)property).setNumHouses(0);
					}
					else if (((Property)property).getNumHotels()>0) {
						Game.setRemainingHotels(Game.getRemainingHotels()+((Property)property).getNumHotels());
						((Property)property).setNumHotels(0);
					}
					else {
						System.out.println("There is no hotels or houses on this property to be sold!");
					}	
				}
				//Remove the property from their List of Owned properties and now the bank will auction the property
				this.propertyList.remove(currPos);
				System.out.println("Property will now be auctioned");
				property.playerAuction(); //FIXME Need a list of players to be global
				currPos++;
			}
			Game.playerList.remove(this); //FIXME need to remove the player from the game
			System.out.println("Bankrupt player, "+this.getName()+", has retired from the game!");
			return true; //FIXME can see if this is needed
		}
		else {
			//You owe a player for all of the loans
			//FIXME needs to be implemented for a player
			return true;
		}
	
	}
		

	public void addPurchasedCard(CanOwn purchasedProperty) {
		this.propertyList.add(purchasedProperty);
	}
	
	public void removeOwnedProperty(CanOwn property) {
		int index = this.propertyList.indexOf(property);
		//Detects that the property object is contained in Property List
		if(index >= 0) {
			this.propertyList.remove(index);
		}
		else {
			System.out.println("You cannot remove a Property when it is not in your Property List!");
		}
	}
	
	public void payRent(CanOwn ownableSquare) {
		//Going to take the ownable square and work with it from there
		//Ask the player who owns it first whether they want to pay rent or not
		Player owner = ownableSquare.getOwner();
		if(ownableSquare.getMortgageStatus() == true) {
			System.out.println("This square is mortgaged and so no rent can be claimed on it!");
		}
		else {
			System.out.println("Player, "+owner.getName()+", would you like the current player, "+this.name+", to pay rent?");
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			String answer = input.next();
			
			if(answer.toLowerCase() == "y") {
				//This means i need to claim rent from the user
				//Need to check what type of property it is followed by how much rent they will need to pay
				if(ownableSquare instanceof Property) {
					int numHouses = ((Property)ownableSquare).getNumHouses();
					int numHotels = ((Property)ownableSquare).getNumHotels();
					//Below is for if you own all of the properties but they are not improved,
					//Charge double rent
					if(Checks.ownAllColour(this, (Property)ownableSquare) != null && numHouses + numHotels == 0) {
						this.reduceMoney(2*((Property) ownableSquare).getRent()[0], ownableSquare.getOwner());
					}
					else if(numHotels == 0) {
						//This is for if you own all the properties in a colour group but you have houses
						this.reduceMoney(((Property) ownableSquare).getRent()[numHouses], ownableSquare.getOwner());
					}
					else {
						//This is for if there is a hotel on the site. Max 1
						this.reduceMoney(((Property) ownableSquare).getRent()[5], ownableSquare.getOwner());
					}	
				}
				else if(ownableSquare instanceof Train) {
					//Rent for a train is size 4
					//Check the amount of trains that an owner has
					int numTrains=0;
					for(CanOwn train:owner.getPropertyList()) {
						if(train instanceof Train) {
							numTrains++;
						}
						else {
							continue;
						}
					}
					this.reduceMoney(((Train) ownableSquare).getRent()[numTrains-1], ownableSquare.getOwner());
				}
				else {
					//Rent payment for a utility
					//Check the amount of utilities that an owner has
					int numUtilities=0;
					for(CanOwn utility:owner.getPropertyList()) {
						if(utility instanceof Utility) {
							numUtilities++;
						}
						else {
							continue;
						}
					}
					this.reduceMoney((((Utility) ownableSquare).getRent()[numUtilities-1])*Dice.getDieVals(), ownableSquare.getOwner());
				}
			}
			else {
				System.out.println("Owner of Square does not require rent to be paid");
			}
		}
	}
	

	//This function will be used to save a person from bankruptcy using the amount of money in argument as what is needed to raise to pay off any off debts from 
	//the reduceMoney() function
	
	public boolean saveFromBankruptcy(int moneyNeedToRaise) {
		//Need to check if I can raise money by any method......
		//I also need to continually check that the money raised is larger than the money to be paid
		while(true) {
			//Need to sell off all of the things in here
			if(this.propertyList.size() > 0) {
				//Step 1: Sell off the houses and hotels
		
				for(CanOwn ownable:this.propertyList) {
					if(ownable instanceof Property) {
						this.addMoney(((Property)ownable).sellHouses()); //FIXME confirm parameters
						this.addMoney(((Property)ownable).sellHotels());
					}
					//If they have raised sufficient money from sellingHouses
					if(this.money - moneyNeedToRaise > 0) {
						break;
					}
				}
				//Step 2: Mortgage the properties
				for(CanOwn ownable:this.propertyList) {
					ownable.mortgage(this);
					if(this.money - moneyNeedToRaise > 0) {
						break;
					}
				}
				//Step 3: Sell off some properties
				for(CanOwn ownable:this.propertyList) {
					ownable.playerAuction();
					if(this.money - moneyNeedToRaise > 0) {
						break;
					}
				}
				return false;
			}
			//They own no physical assets and so cannot raise any money
			else {
				return false;
			}
		}
		return true;
	}
	
	//method to initiate a player to player transaction

	public void playerToPlayerTransaction() {
		ArrayList<Player> tradeList = new ArrayList<Player>(2);
		tradeList.add(this);
		
		int traderOneJailFree = 0;
		int traderOneCash = 0;
		HashMap<String,Property> traderOnePropsToTrade = new HashMap<String, Property>();
		
		//trade details of player two
		int traderTwoJailFree = 0;
		int traderTwoCash = 0;
		HashMap<String,Property> traderTwoPropsToTrade = new HashMap<String, Property>();
		
		
		//prompt the user who has initiated the desire to trade, to select who they wish to trade with
		System.out.println("Please indicate the player who you wish to initiate a transaction with, using their name");
		Scanner input = new Scanner(System.in);
		String transactionChoice;
		
		transactionChoice= input.next();
		Player chosenPlayer = Checks.isValidPlayer(transactionChoice);
		
		while(null==chosenPlayer) {
			System.out.println(this.name+", please enter a valid name");
			transactionChoice= input.next();
			chosenPlayer = Checks.isValidPlayer(transactionChoice);
		}
		
		tradeList.add(chosenPlayer);
	

		//iterate over both players 
		for(int i =0; i<2; i++){
		
			boolean finishedTrade = false;
			while(!finishedTrade) {
				input.nextLine();
				System.out.println(tradeList.get(i).getName()+", you have:\n\n"+tradeList.get(i).jailFreeCard+" Jail Free Cards\n"
						+tradeList.get(i).propertyList.size()+" ownable properties\n"+tradeList.get(i).money+" in cash \n\n");
				System.out.println("Please select what you wish to trade:\n[0]Cancel Trade\n[1]Jail Free Card\n[2]Property\n[3]Cash");
				
				transactionChoice=input.next();
				
				switch(transactionChoice) {			
				case "0":
					System.out.println(tradeList.get(i).getName()+" is exiting without trade. Nothing has been exchanged");
					return;
				case "1":
					if(tradeList.get(i).getJailFreeNum()==0) {
						if(Checks.yesNoInput("You do not have any get out of jail free cards.\n\nWould you like to trade something else? (y/n)",  tradeList.get(i))) {
							continue;
						}
						else{
							finishedTrade=true;
							break;
						}
					}
					else if(i==0) {
						if(tradeList.get(i).getJailFreeNum()==traderOneJailFree) {
							if(Checks.yesNoInput("You do not have any more get out of jail free cards to add to your trade.\n\nWould you like to trade something else? (y/n)",  tradeList.get(i))) {
								continue;
							}
							else {
								finishedTrade=true;
								break;
							}
						}
						traderOneJailFree++;
					}
					else {
						if(tradeList.get(i).getJailFreeNum()==traderTwoJailFree) {
							if(Checks.yesNoInput("You do not have any more get out of jail free cards to add to your trade.\n\nWould you like to trade something else? (y/n)",  tradeList.get(i))) {
								continue;
							}
							else {
								finishedTrade=true;
								break;
							}
						}
						traderTwoJailFree++;
					}
				case "2":
					System.out.println("Please enter the name of the property you wish to include in the trade");
					transactionChoice = input.next();
					Property propToTrade = Checks.isValidProp(transactionChoice,  tradeList.get(i));
					if(null==propToTrade){
						if(Checks.yesNoInput("The property you have entered is either invalid or not owned by you. Would you like to trade something else? (y/n)", tradeList.get(i))) {
							continue;
						}
						else {
							finishedTrade=true;
							break;
						}
					}
					//for the first player
					else if(i==0) {
						if(traderOnePropsToTrade.containsKey(propToTrade.getName())){
							if(Checks.yesNoInput("The property you have entered is already in your list of items to trade. Would you like to trade something else? (y/n)", tradeList.get(i))) {
								continue;
							}
							else {
								finishedTrade=true;
								break;
							}
						}
						else {
							traderOnePropsToTrade.put(propToTrade.getName().toLowerCase(), propToTrade);
						}
					}
					//for the second player
					else {
						if(traderTwoPropsToTrade.containsKey(propToTrade.getName())){
							if(Checks.yesNoInput("The property you have entered is already in your list of items to trade. Would you like to trade something else? (y/n)", tradeList.get(i))) {
								continue;
							}
							else {
								finishedTrade=true;
								break;
							}
						}
						else {
							traderTwoPropsToTrade.put(propToTrade.getName(), propToTrade);
						}
					}
				
				case "3":
					System.out.println("Please specify the amount of cash you would like to include in this trade:");
					int cashToTrade = input.nextInt();
					if(cashToTrade>tradeList.get(i).money) {
						if(Checks.yesNoInput("You have specified more cash than you currently have. Would you like to trade something else? (y/n)", tradeList.get(i))) {
							continue;
						}
						else {
							finishedTrade=true;
							break;
						}
					}
					if(i==0) {
						traderOneCash+=cashToTrade;
						break;
					}
					else {
						traderTwoCash+=cashToTrade;
						break;
					}
				}
				
				if(finishedTrade==false) {
					if(Checks.yesNoInput(tradeList.get(i).getName()+" are you finished making your trade? (y/n)", tradeList.get(i))) {
						finishedTrade=true;
					}
				}
			}
		}
	
	//show each players trades
		
	//Player One
	
		System.out.println("Terms of trade:\n\n"+tradeList.get(0).getName()+"\n\nJail free Cards: "+traderOneJailFree+"\nProperties:");
		for(String key: traderOnePropsToTrade.keySet()) {
			System.out.println(key);
		}
		System.out.println("Cash: "+traderOneCash);

		//PLayer Two
		System.out.println("\n"+tradeList.get(1).getName()+"\n\nJail free Cards: "+traderTwoJailFree+"\nProperties:");
		for(String key: traderTwoPropsToTrade.keySet()) {
			System.out.println(key);
		}
		System.out.println("Cash: "+traderTwoCash);
	
		//Trade Acceptance
		if(Checks.yesNoInput(tradeList.get(0).getName()+" do you accept the terms of trade? (y/n)", tradeList.get(0))&& Checks.yesNoInput(tradeList.get(1).getName()+" do you accept the terms of trade? (y/n)", tradeList.get(1))) {
			//trade property Lists
			for(Property currentProperty: traderOnePropsToTrade.values()) {
				tradeList.get(0).removeOwnedProperty(currentProperty);
				tradeList.get(1).addPurchasedCard(currentProperty);
			}
		
			for(Property currentProperty: traderTwoPropsToTrade.values()) {
				tradeList.get(1).removeOwnedProperty(currentProperty);
				tradeList.get(0).addPurchasedCard(currentProperty);
			}
			
			//trade money
			tradeList.get(0).reduceMoney(traderOneCash, tradeList.get(1));
			tradeList.get(1).reduceMoney(traderTwoCash, tradeList.get(0));
		
			//trade Jail Free Cards
			tradeList.get(0).jailFreeCard+=(traderTwoJailFree-traderOneJailFree);
			tradeList.get(1).jailFreeCard+=(traderOneJailFree-traderTwoJailFree);

		}
		else {
			System.out.println("Trade has not been accepted by both parties");
			return;
		}
	}
	
	public boolean checkBankrupt() {
		return false;
	}
	
	public String toString() {
		return "Details of: "+this.name+
				"\nToken: "+this.token+"\nMoney: "+this.money+"\nSquare Location: "+this.indexLocation+
				"\nIs In Jail?: "+this.inJail+"\n PropertyList"+this.propertyList;
	}
	
}
