package cards;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import game.Jail;
import game.Player;
import operations.Checks;
import squares.CanOwn;
import squares.Property;

/**
 * The Chance class extends the Card class as its parent. This Class implements the abstract method dealWithCard for Chance cards
 * of different types
 * @author Robert Keenan & Ciaran Nolan
 *
 */

public class Chance extends Card {
	
	/**
	 * This is the Constructor which has the same arguments of its parent class of type, description and value
	 * @param cardType The type of card such as MOVE, PAY, INCOME
	 * @param cardDesc The actual text on the card
	 * @param cardValue The value of the card such as how many spaces to move or how much money you have received
	 */
	public Chance(String cardType, String cardDesc, int cardValue) {
		super(cardType, cardDesc, cardValue);
	}

	/**
	 * Deal with card function which takes an input of player object and either reduces the money of player (PAY), gives money
	 * to player (INCOME), sends them to Jail (JAIL) or gives them a Get out of Jail card (GET_OUT_OF_JAIL) depending on a switch statement
	 * determined by the card's type
	 *
	 * @param player A player object which these actions can be applied to
	 */

	//method to deal with a card from one of the game's decks
	public void dealWithCard(Player player, BufferedReader userInput) {
		if(userInput==null) userInput = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<TitleDeed> titleDeedList = player.getTitleDeedList();

		//print card details for the user
		System.out.println("The chance card reads: "+this.getCardDesc());
		//implement the card
		switch(this.getCardType()) {
			case "MOVE":
				//cover case where players move backwards
				if(this.getCardValue()<0){
					player.moveToSquare(player.getLocation()+this.getCardValue());
					Checks.checkSquare(player.getLocation(), player, userInput);
				}
				//move player forwards
				else {
					player.moveToSquare(this.getCardValue());
					if(player.getLocation()==0){
						break;
					}
					else Checks.checkSquare(player.getLocation(), player, userInput);
				}
				break;
			case "JAIL":
				//send player to jail
				Jail.sendToJail(player);
				break;
			case "PAY":
				//pay a fine
				//check if the fine is per house/hotel
				if(this.getCardDesc().contains("repairs")) {
					//Check number of houses and hotels to accumulate in the payment for each ownable site
					for(TitleDeed titleDeed : titleDeedList) {
						CanOwn property = titleDeed.getOwnableSite();
						if(property instanceof Property) {
							//This will get the card value and multiply the number of houses or hotels depending on if its of Property Class
							player.reduceMoney(this.getCardValue()*((Property) property).getNumHouses(),null);
							player.reduceMoney(4*this.getCardValue()*((Property) property).getNumHotels(),null);
							//If there is no hotels, it will not take any money away at all
						}
					}
					break;
				}
				else {
					//reduce the amount of the fine
					player.reduceMoney(this.getCardValue(), null);
					break;
				}
			case "INCOME":
				//add money to the player
				player.addMoney(this.getCardValue());
				break;
			case "GET_OUT_OF_JAIL":
				//add get out of jail free card
				player.addJailCard(this);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + this.getCardType());
		}
	}
}
