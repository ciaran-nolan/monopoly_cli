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
	//FIXME CONSIDER Perhaps a card Array to show what cards you have
	
	public Player(String name, String token, int money) {
		this.name = name;
		this.token = token;
		this.money = money;
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
		//You need to check if the location of the Player is at the index location of the square for that and whether they have gotten a go to jail card
		if(this.jailFreeCard < 1) {
			this.indexLocation = 3; //FIXME CHANGE TO THE INDEX OF JAIL.....OR EVEN ENUMERATE THE INDEX
			return this.inJail = true;
			//WIP REPLACE WITH AN ENUM FIXME 
		}
		else {
			this.jailFreeCard--;
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
	
	public void pickCard(ArrayList<Card> cardDeck) {
		//The card deck will be shuffled and so I will need to take this card and then call the 
		Card pickedCard = cardDeck.get(0);
		//If it is a get out of jail card, keep it and dont return to the pile
		if(pickedCard.getCardType() != "GET_OUT_OF_JAIL") {
			cardDeck.remove(0);
			cardDeck.add(pickedCard);
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
	
	
}
