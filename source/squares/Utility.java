package squares;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import game.Player;
import operations.InputOutput;

/**
 * The class that describes a Utility object object such as Water Works.
 * This class file contains the method buy() which implements the abstract method from CanOwn class
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public class Utility extends CanOwn {
	/**
	 * Class constructor which takes 2 arguments and passes them up to Square.java overall parent class
	 * @param name The name of the utility as a String
	 * @param indexLocation The location of the utility on the board
	 */
	public Utility(String name, int indexLocation) {
		super(name, indexLocation, Square.SquareType.UTILITY);
	}

	public void buy(Player player, BufferedReader userInput) {
		if(userInput==null){
			userInput = new BufferedReader(new InputStreamReader(System.in));
		}
	/**
	 * This is the buy method for a utility which takes the argument of a player to buy the utility.
	 * It checks whether you have funds to purchase it, if you don't it goes to auction.
	 * It then checks if the property is already owned.
	 * Then checks if you can purchase it, deducts the price from the player object's money and adds the purchased title deed card.
	 * If none of these cases are satisfied, it goes to auction.
	 * @param player The player object that wants to buy the utility
	 */
		//check user has enough funds to purchase
		if(player.getMoney() < this.getTitleDeedCard().getPriceBuy()) {
			System.err.println("You do not have the necessary funds to purchase this property.\nYour Funds: "
					+player.getMoney()+"\nProperty Price: "+this.getTitleDeedCard().getPriceBuy());
			//player does not have enough funds to buy property, automatically enter auction
			this.getTitleDeedCard().playerAuction(null, userInput);
		}
		else if(InputOutput.yesNoInput(player.getName()+", would you like to purchase "+this.getName()
				+" for €"+this.getTitleDeedCard().getPriceBuy()+"?", player, userInput)) {
				//user has passed all necessary checks to purchase a property, reduce the price from users funds
				player.reduceMoney(this.getTitleDeedCard().getPriceBuy(), null);
				//add property to users property list
				player.addPurchasedTitleDeed(this.getTitleDeedCard());
				System.out.println("You have purchased "+this.getName()+" for "+this.getTitleDeedCard().getPriceBuy());
		}
		else{
			this.getTitleDeedCard().playerAuction(null, userInput);
		}
	}
}
