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
	
	public void reduceMoney(int money) {
		this.money-=money;
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
			this.indexLocation = 3; //FIXME CHANGE TO THE INDEX OF JAIL.....OR EVEN ENUMERATE THE INDEX
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
				this.indexLocation = 3; 
				return this.inJail = true;
			}
			//RETURN THE CARD TO THE LIST as the user has now used it
			//For the jail card, add it and then delete it from the array of cards. When you are done with it and want to return it, 
			//just add it to the end of the card 
		}
	
	}
	
	public void pickCommChestCard(ArrayList<CommunityChest> cardDeck) {
		//The card deck will be shuffled and so I will need to take this card and then call the 
		CommunityChest pickedCard = cardDeck.get(0);
		//If it is a get out of jail card, keep it and don't return to the pile
		if(pickedCard.getCardType() != "GET_OUT_OF_JAIL") {
			cardDeck.remove(0);
			cardDeck.add(pickedCard);
		}
		else {
			//Remove the get out of jail card from the pile
			cardDeck.remove(0);
			this.addJailCard(pickedCard);
		}
		//This will implement the card
		pickedCard.dealWithCard(this);
	}
	
	public void pickChanceCard(ArrayList<Chance> cardDeck) {
		//The card deck will be shuffled and so I will need to take this card and then call the 
		Chance pickedCard = cardDeck.get(0);
		//If it is a get out of jail card, keep it and don't return to the pile
		if(pickedCard.getCardType() != "GET_OUT_OF_JAIL") {
			cardDeck.remove(0);
			cardDeck.add(pickedCard);
		}
		else {
			//Remove the get out of jail card from the pile
			cardDeck.remove(0);
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

	public ArrayList<Player> createListPlayers(){
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
	public boolean isBankrupt() {
		return true;
		//Return true for now FIXME
	}
	
	
	public String toString() {
		return "Details of: "+this.name+
				"\nToken: "+this.token+"\nMoney: "+this.money+"\nSquare Location: "+this.indexLocation+
				"\nIs In Jail?: "+this.inJail+"\n PropertyList"+this.propertyList;
	}
	
	
	public void addPurchasedCard(CanOwn purchasedProperty) {
		this.propertyList.add(purchasedProperty);
	}
	
}
