package squares;

import game.Jail;
import game.Player;


/*
 * This Special Square contains the methods for Tax, Jail, Go and Free Parking 
 * It will work off the index of the properties file inside of specialSquares.configurations
 * We can then use the individual configuration files for Chance and Community Chest.
 */
/**
 * This is the Special Square class. A special square class defines the squares of the board 
 * that are not Properties, Train Stations or Utilities but rather have types such as TAX, INCOME, JAIL, CHANCE, COMMUNITY CHEST
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public class Special extends Square implements Printable {
	private String type; //This defines the type of the square
	private int value; //This defines the value if they have any......like Tax, or Go

	/**
	 * Special Square constructor takes a number of arguments
	 * @param name The name of the square such as Jail in String form
	 * @param indexLocation The location of the square on the board as an integer
	 * @param canBuy A boolean to determine if you are able to buy the square or not
	 * @param type The type of the square given as a capitalised string such as TAX, GO, JAIL, FREE, CHANCE, COMMUNITY CHEST
	 * @param value The value of the square such as a TAX
	 * @param squareType The type of square which is defined by SquareType enum which is SquareType.SPECIAL
	 */
	public Special(String name, int indexLocation, boolean canBuy, String type, int value, SquareType squareType) {
		super(name, indexLocation, canBuy, squareType); //Nobody owns it
		this.type = type;
		this.value = value;
	}
	/**
	 * Get the type of the square
	 * @return this.type The type of the square
	 */
	public String getType() {
		return this.type;
	}
	/**
	 * Get the value of the square
	 * @return this.value An integer of the value of the square
	 */
	public int getValue() {
		return this.value;
	}
	/**
	 * Setting the type of the Special Square
	 * @param type A string of the type in capitals like TAX, INCOME
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Setting the value of a square
	 * @param value A value as a positive integer
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * This implements the special square whenever a player lands on one. 
	 * It detects the square type whether it is TAX, GO, COMMUNITY CHEST, etc and implements the relevant methods for these
	 * @param player The player who is on the square
	 */
	public void implementSpecialSquare(Player player) {

		switch(this.type) {
			case "TAX":
				System.out.println("You must pay tax of "+"£"+this.value);
				//Null as you owe the bank if you cant pay
				player.reduceMoney(this.value, null); //Reducing the money in a players account using the value given
				break; //Break the switch statement
			case "GO":
				System.out.println("You have landed on GO, collect "+"£"+this.value);
				player.addMoney(this.value);
				break;
			case "COMMUNITY_CHEST":
				player.pickCommChestCard();
				break;
			case "CHANCE":
				player.pickChanceCard();
				break;
			case "FREE":
				break; //THIS IS DONE FOR FREE PARKING
			default:
				//In Jail
				if(this.getLocation()==10){
					break;
				}
				//Go to jail square
				else {
					Jail.sendToJail(player);
					break;
				}
		}
	}
	/**
	 * Print the instance data using the Interface Printable
	 */
	@Override
	public void printInstanceData() {
		System.out.println("Special, "+this.getName());
	}
	/**
	 * Whether you can buy this Square
	 */
	@Override
	public boolean canBuy() {
		return false;
	}
}

