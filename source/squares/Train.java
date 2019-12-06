package squares;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cards.TitleDeed;
import game.Player;
import operations.Checks;
import operations.InputOutput;

/**
 * The class that describes a Train Station object. This class file contains the method buy() which implements the abstract method from CanOwn class
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public class Train extends CanOwn implements Printable {
	/**
	 * Class constructor which takes 2 arguments and passes them up to Square.java overall parent class
	 * @param name The name of the train station as a String
	 * @param indexLocation The location of the Train station on the board
	 */
	public Train(String name, int indexLocation) {
		super(name, indexLocation, Square.SquareType.TRAIN);
	}
    /**
     * This is the buy method for a train station which takes the argument of a player to buy the train station.
     * It checks whether you have funds to purchase it, if you don't it goes to auction.
     * It then checks if the property is already owned.
     * And then checks if you can purchase it, deducts the price from the player object's money and adds the purchased title deed card.
     * If none of these cases are satisfied, it goes to auction.
     * @param player The player object that wants to buy the train station
     * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
     */
	public void buy(Player player, BufferedReader userInput) {
		if(userInput==null){
			userInput = new BufferedReader(new InputStreamReader(System.in));
		}
		//check user has enough funds to purchase
		TitleDeed titleDeedCard = this.getTitleDeedCard();
		//The player doesn't have enough money to purchase it
		if(!Checks.enoughFunds(player, titleDeedCard.getPriceBuy())) {
			System.err.println("You do not have the necessary funds to purchase this train.\nYour Funds: "+player.getMoney()+"\nProperty Price: £"+titleDeedCard.getPriceBuy());
			//player does not have enough funds to buy property, automatically enter auction
			this.getTitleDeedCard().playerAuction(null, userInput);
		}
		//Property is already owned
		else if(!this.canBuy()){
			System.err.println("This property is already owned!");
		}
		//They can purchase it
		else if(InputOutput.yesNoInput(player.getName()+", would you like to purchase "
				+this.getName()+" for £"+titleDeedCard.getPriceBuy()+"?", player, userInput)) {
			//user has passed all necessary checks to purchase a property, reduce the price from users funds
			System.out.println("You have purchased "+this.getName()+" for £"+titleDeedCard.getPriceBuy());
			player.reduceMoney(titleDeedCard.getPriceBuy(), null);
			//add property to users property list
			player.addPurchasedTitleDeed(this.getTitleDeedCard());
		}
		//Send to auction
		else {
			this.getTitleDeedCard().playerAuction(null, userInput);
		}
	}
	/**
	 * Printing the Instance data using Interface Printable
	 */
	@Override
	public void printInstanceData() {
		System.out.println("Train, "+this.getName()+": \nLocation: Square "+this.getLocation()+"\nMortgage Status: ");
		this.getTitleDeedCard().printInstanceData();
	}
	/**
	 * Whether you can buy the Square
	 */
	@Override
	public boolean canBuy() {
		 return this.getTitleDeedCard().canBuy();
	}
}
