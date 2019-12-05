package cards;

import java.io.BufferedReader;

import game.Player;

/**
 * The Card class defines structure of all Card types of which there are a number extended from this abstract class.
 * This class is for any classes that are physical cards in the game of Monopoly. These include Community Chest, Chance and Title Deed Cards
 *
 * @author Robert Keenan & Ciaran Nolan
 *
 */


public abstract class Card {
	private String cardType;
	private String cardDesc;
	private int cardValue;
	
	/**
	 *	Class Constructor which takes as argument the Card type, the Card description and the Card value and sets the class variables to these values
	 *
	 *	@param cardType The type of the card: Title Deed, MOVE, JAIL, PAY, CHOICE, INCOME, GET_OUT_OF_JAIL
	 *	@param cardDesc A description of what the card is or is doing such as a chance card telling you to pay players
	 *	@param cardValue The value of the card. Example if it was MOVE, the value would specify how many places to move
	 */
	public Card (String cardType, String cardDesc, int cardValue) {
		this.cardType = cardType;
		this.cardDesc = cardDesc;
		this.cardValue = cardValue;	
	}
	
	//Getters for each class variable
	public String getCardType() {
		return this.cardType;
	}
	
	public String getCardDesc() {
		return this.cardDesc;
	}
	
	public int getCardValue() {
		return this.cardValue;
	}
	
	//Setters for each class variable
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public void setCardDesc(String cardDesc) {
		this.cardDesc = cardDesc;
	}
	
	public void setCardValue(int cardValue) {
		this.cardValue = cardValue;
	}

    /**
     * Abstract method which is implemented in either Community Chest or Chance which is handles the type of card and its cardType as specified above
     * @param player1 Takes a Player object as input
     */
	public abstract void dealWithCard(Player player1, BufferedReader userInput);



	
	//Print the details of the card
	public String toString() {
		return "Card Type: "+this.cardType+", Card Description: "+this.cardDesc+", Card Value: "
				+this.cardValue;
	}
}
	