package ie.ucd.squares;

import ie.ucd.cards.TitleDeed;
import ie.ucd.game.Player;
import ie.ucd.operations.InputOutput;

import java.io.BufferedReader;

/**
 * This is the CanOwn class. This is to do with Squares that can be owned by a player such as Properties, Train Stations and Utilities.
 * They have an associated Title Deed card and you can see that functionality in TitleDeed.java.
 * The method in this class are mortgage(),demortgage() and buy() as well as setters and getters for the Title Deed card of a CanOwn object
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public abstract class CanOwn extends Square {
	//Class variable of a title deed card
	private TitleDeed titleDeedCard;

	/**
	 * The constructor of CanOwn is the same as Square (its parent class). It takes the name of the Square, the location of it
	 * on the board and the type such as SquareType.PROPERTY or SquareType.TRAIN which is an enum located in the Square class
	 * @param name The name of the CanOwn Object such as a Property
	 * @param indexLocation The location of the CanOwn object on the board (its square number)
	 * @param type The type of the CanOwn object, given in the SquareType enum form (SquareType.PROPERTY)
	 */
	public CanOwn(String name, int indexLocation, Square.SquareType type) {
		super(name, indexLocation, true, type);
	}

	/**
	 * This is the getter for the Title Deed card of the CanOwn object and it returns a TitleDeed card object
	 * @return titleDeedCard, a TitleDeed object of the CanOwn object
	 */

	public TitleDeed getTitleDeedCard(){
		return this.titleDeedCard;
	}
	/**
	 * Setting the Title Deed card of the CanOwn object by giving a TitleDeed object as an argument
	 * @param card TitleDeed object you want to set this CanOwn object's Title Deed card to
	 */
	public void setTitleDeedCard(TitleDeed card){
		this.titleDeedCard = card;
	}
	//FIXME PLEASE Consider whether it should take an argument or not, Refer to Trello
	public abstract void buy(Player player, BufferedReader userInput);
	//The list of players is so you can use the auction method which will be made by Ciaran Nolan
	/**
	 * Abstract method for buying a CanOwn property and this is implemented in the child classes
	 * @param player The player who wishes to buy a CanOwn object
	 */
	public abstract void buy(Player player);


	/**
	 * This is the mortgage method for a CanOwn object. It first of all detects whether the object to be mortgaged is a Property and that the
	 * Player object given as argument is, in fact, the owner of this CanOwn object.
	 * It checks if it is mortgaged already and then checks if the 2nd argument of bankruptcy is true to see if this property is being mortgaged
	 * because of this player's bankruptcy.
	 * It prompts the user for input to see if they still want to mortgage the property, adds the money to their Player object if they say yes
	 * and sets the mortgage status of the CanOwn object's Title Deed card to be mortgaged.
	 * It will sell off houses and hotels to the Bank at half their price if they are present on the square
	 *
	 * It follows the same method for the Utilities and Train Stations
	 *
	 * @param player The player who is wishing to mortgage a CanOwn object
	 * @param bankruptcy A boolean to see if the property is being mortgaged because of the Player's bankruptcy
	 */
	public void mortgage(Player player, boolean bankruptcy) {
		//check that the player owns the property
		if (this.titleDeedCard.getOwner() == player) {
			//ensure selected type of CanOwn is an instance of property
			if(this instanceof Property) {
				//check if the property is upgraded
				if(((Property)this).getNumHouses() == 0 && ((Property)this).getNumHotels() == 0) {
					//check if the property is already mortgaged
					if(this.titleDeedCard.getMortgageStatus()){
						System.err.println("This property is already mortgaged!");
					}
					//check if the mortgage is due to bankruptcy
					else if(bankruptcy){
						this.titleDeedCard.setMortgageStatus(true);
						player.addMoney(this.titleDeedCard.getMortgage());
						System.out.println("Successfully mortgaged "+this.getName());
					}
					//confirm mortgage
					else if(InputOutput.yesNoInput("This property is unimproved: "+this.getName()+"\nWould you still like to mortgage this property? (y/n)", player, null)) {
						this.titleDeedCard.setMortgageStatus(true);
						player.addMoney(this.titleDeedCard.getMortgage());
						System.out.println("Successfully mortgaged "+this.getName());
					}
					//cancel mortgage
					else {
						System.out.println("You have chosen not to mortgage this property");
						this.titleDeedCard.setMortgageStatus(false);
					}
				}
				else{
					//It has houses or hotels on it so you must sell all of them
					//Go through property list. Get the colour of the property and sell all of them from there
					//To mortgage it first, you must sell the houses
					if(InputOutput.yesNoInput("This property is improved: "+this.getName()
							+"\nMortgaging this property will sell all houses/hotels in this colour group: "+((Property) this).getSquareColour()
							+"\nWould you still like to mortgage this property? (y/n))", player,null)) {

						((Property) this).sellHouses(player, true, false);
						((Property) this).sellHotels(player, true, false);
						//Once all of the houses and hotels are sold on each site, you will need to
						this.titleDeedCard.setMortgageStatus(true);
					}
					else System.out.println("Property has not been mortgaged");
				}
				//If its an instance of a property, it can be improved so check that it is unimproved before mortgaging it
			}
			//not an instance of property, cannot be upgraded so immediately mortgage
			else {
				if((this instanceof Utility || this instanceof Train) && !this.titleDeedCard.getMortgageStatus()) {
					this.titleDeedCard.setMortgageStatus(true);
					player.addMoney(this.titleDeedCard.getMortgage());
					System.out.println("Successfully mortgaged "+this.getName());
				}
				else {
					System.out.println("This property is non-ownable or has been mortgaged already!");
				}
			}
		}
		//player does not own the property
		else {
			System.err.println("You cannot mortgage this property as you do not own it!");
		}
	}

	//boolean argument ensures that the a buyer of a mortgaged property immediately pays the 10% charge

	/**
	 * This method is used to remove the mortgage on a property. It first of all checks if the mortgage is present on the CanOwn object.
	 * Reduces the money for the owner depending on whether they demortgage on sale or not.
	 * It then sets the mortgage status to false and prints to the screen
	 * It follows the same type of pattern for the property if it has not been sold and is being un-mortgaged after a number of rounds
	 * @param demortgageOnSale This is to check if the property is being de-mortgaged immediately or not which determines the charge
	 */
	public void demortgage(boolean demortgageOnSale) {
		//Check property is mortgaged
		if(this.getTitleDeedCard().getMortgageStatus()) {
			//if the property must be de-mortgaged on sale
			//Pay 10% plus value of mortgage
			if (demortgageOnSale) {
				//Automatically set 10% interest to paying
				this.titleDeedCard.getOwner().reduceMoney((int) (0.1 * this.titleDeedCard.getMortgage()), null);
				//If they don't have enough money to pay off mortgage
				if (this.titleDeedCard.getMortgage() > this.titleDeedCard.getOwner().getMoney()) {
					System.err.println("You don't have enough money to demortgage this property now!");
				} 
				else {
					this.titleDeedCard.getOwner().reduceMoney(this.titleDeedCard.getMortgage(), null);
					this.titleDeedCard.setMortgageStatus(false);
					System.out.println(this.titleDeedCard.getCardDesc() + " has been successfully demortgaged.");
				}
			}
			//property has not been sold, just demortgaged
			else {
				//check owner has required funds to demortgage
				if ((this.titleDeedCard.getMortgage() + 0.1 * this.titleDeedCard.getMortgage()) > this.titleDeedCard.getOwner().getMoney()) {
					System.err.println("You don't have enough money to demortgage this property now!");
				}
				else {
					this.titleDeedCard.getOwner().reduceMoney(this.titleDeedCard.getMortgage(), null); //Paying price of mortgage
					this.titleDeedCard.getOwner().reduceMoney((int) (0.1 * this.titleDeedCard.getMortgage()), null); //Paying interest
					this.titleDeedCard.setMortgageStatus(false);
					System.out.println(this.titleDeedCard.getCardDesc() + " has been successfully demortgaged.");
				}
			}
		}
		else {
			System.out.println("This property is not currently mortgaged");
		}
	}
}
