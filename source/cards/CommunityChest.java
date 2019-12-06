package cards;

/**
 * This Class describes the interaction of a Community Chest card which is very similar to the Chance Card functionality
 * @author Robert Keenan & Ciaran Nolan
 *
 */
import java.io.BufferedReader;

import game.Jail;
import game.Player;
import operations.InputOutput;

//This is how to sort out the handling of a community chest card that is picked from a deck
//It is not to denote a communitychest square. That is handled by the Special.java file
public class CommunityChest extends Card {

	/**
	 * This is the Constructor which has the same arguments of its parent class of type, description and value
	 * @param cardType The type of card such as MOVE, PAY, INCOME
	 * @param cardDesc The actual text on the card
	 * @param cardValue The value of the card such as how many spaces to move or how much money you have received
	 */
	public CommunityChest(String cardType, String cardDesc, int cardValue) {
		super(cardType, cardDesc, cardValue);
	}
    /**
     * Deal with card function which takes an input of player object and either reduces the money of player (PAY), gives money
     * to player (INCOME), sends them to Jail (JAIL) or gives them a Get out of Jail card (GET_OUT_OF_JAIL) depending on a switch statement
     * determined by the card's type
     * CHOICE is the difference between Chance and this. CHOICE card gives you the option of a fine or to pick a Chance card
     *
     * @param player1 A player object which these actions can be applied to
     */
	public void dealWithCard(Player player1, BufferedReader userInput) {

		// deal with a community chest card
		// print details of card to player
		System.out.println("The community chest card reads: " + this.getCardDesc());
		switch (this.getCardType()) {
		case "MOVE":
			player1.moveToSquare(this.getCardValue());
			break;
		case "JAIL":
			Jail.sendToJail(player1);
			break;
		case "PAY":
			player1.reduceMoney(this.getCardValue(), null);
			break;
		case "INCOME":
			player1.addMoney(this.getCardValue());
			break;
		case "GET_OUT_OF_JAIL":
			player1.addJailCard(this); // Increments the amount of jail free cards by 1
			break;
		case "CHOICE":
			// ask user for choice
			System.out.println(player1.getName() + ": Would you like to: " + this.getCardDesc() + "?");
			System.out.println("Please use the integers provided to select\n FINE[0]\nCHANCE[1]");
			int choice = InputOutput.integerMenu(0, 1, null);
			// implement choice
			if (choice == 0) {
				player1.reduceMoney(this.getCardValue(), null);
			} else {
				player1.pickChanceCard();
			}
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + this.getCardType());
		}
	}
}
