package ie.ucd.squares;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ie.ucd.cards.TitleDeed;
import ie.ucd.game.Game;
import ie.ucd.game.Player;
import ie.ucd.operations.Checks;
import ie.ucd.operations.InputOutput;
/**
 * The Property class is used to implement a Property Square Object. It extends its parent class of CanOwn which means it can be owned by a Player object.
 * Each Property object has its own associated Title Deed Card which is assigned inside the Title Deed constructor and the class variables are
 * the colour of the square and the number of houses and hotels on the site.
 * The methods are getters and setters, buying a Property, building Houses or Hotels, selling houses and selling hotels.
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public class Property extends CanOwn {
	//Class variables
	private String squareColour;	//The colour of the property square
	private int numHouses;			//The number of houses currently on the site
	private int numHotels;			//The number of hotels currently on the site
	
	/**
	 * The Property object constructor takes 3 variables as input. These are then pushed up to the Super class of CanOwn and when an initial Property object
	 * is created, the variables of numHotels and numHouses are both set to zero.
	 *
	 * @param squareNum The index location of the property on the board
	 * @param squareColour The colour of the square which denotes the property group it is in
	 * @param title The name of the Property object such as "Park Lane" provided as a string
	 */
	public Property(int squareNum, String squareColour, String title) {
		//owner will always be null at constructor since a property starts without an owner
		super(title, squareNum, Square.SquareType.PROPERTY);
		this.squareColour = squareColour;
		this.numHotels = 0;
		this.numHouses = 0;
	}
	
	//Getters and setters
	public int getNumHotels() {
		return this.numHotels;
	}
	//Get number of houses
	public int getNumHouses() {
		return this.numHouses;
	}
	//set the num of houses
	public void setNumHouses(int numHouses) {
		this.numHouses = numHouses;
	}
	//set the num of hotels
	public void setNumHotels(int numHotels) {
		this.numHotels = numHotels;
	}
	//get the square colour
	public String getSquareColour() {
		return this.squareColour;
	}

	/**
	 * This is the buy method for buying a Property object. It checks if the argument of a player object has enough money to buy a property
	 * and if not, the property goes to auction. If the property is already owned, it prints to the screen.
	 * It will then ask if the player wants to buy the property if the above conditions are not satisfied, it will deduct the money from he player and
	 * add the title deed card of the property to the list of their title deed cards. The owner inside the Title Deed object will now change to the player.
	 *
	 * If none of these cases are satisfied, the property goes to auction.
	 * @param player A player object who wants to buy the Property object
	 */

	public void buy(Player player, BufferedReader userInput) {
		if(userInput==null){userInput = new BufferedReader(new InputStreamReader(System.in));}
			//check user has enough funds to purchase
			TitleDeed titleDeedCard = this.getTitleDeedCard();
			if(!Checks.enoughFunds(player, titleDeedCard.getPriceBuy())) {
				System.out.println("You do not have the necessary funds to purchase this property.\nYour Funds: "
                        +player.getMoney()+"\nProperty Price: "+titleDeedCard.getPriceBuy());
				//player does not have enough funds to buy property, automatically enter auction
				titleDeedCard.playerAuction(null, userInput);

			}
			//the case of the owner should be handled in check square
			else if(!(Checks.canBuy(this.getTitleDeedCard()))){
				System.out.println("This property is already owned!");
			}
			else if(InputOutput.yesNoInput(player.getName()+", would you like to purchase "
                    +this.getName()+" for €"+titleDeedCard.getPriceBuy()+"?", player, userInput)) {
				//user has passed all necessary checks to purchase a property, reduce the price from users funds
				System.out.println("You have purchased "+this.getName()+" for "+titleDeedCard.getPriceBuy());

				player.reduceMoney(titleDeedCard.getPriceBuy(), null);
				//add property to users property list
				player.addPurchasedTitleDeed(titleDeedCard);
			}
			else titleDeedCard.playerAuction(null, userInput);
			}

	/**
	 * This is the method for building either houses or hotels on a Property object or square. It firstly asks what property you want to build on, obtains the property object,
	 * and then checks whether they can be built or not.
	 * It also checks whether the distribution of hotels or houses are even across the same colour group of properties.
	 * Once a hotel or house is built, it reduces the global amount of houses and hotels by 1.
	 * @param player A player who wants to build a house or hotel on the site
	 */
	public static void buildHousesHotels(Player player) {
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		boolean isHotel = false;

		//System.out.println("Please enter the name of the property you wish to purchase houses/hotels for");
		//String propName = input.nextLine();


		//Property propToBuild = Checks.isValidProp(propName, player);
		TitleDeed titleDeedToBuild = InputOutput.titleDeedOperationMenu(player, "purchase houses/hotels for", true, userInput);
		if(null == titleDeedToBuild){
			System.out.println("Cancelling operation.");
		}
		else {
			Property propToBuild = ((Property)titleDeedToBuild.getOwnableSite());
			ArrayList<Property> colourGroup = Checks.ownAllColour(player, propToBuild);
			//InputOutput.titleDeedOperationMenu(player, "purchase houses/hotels for", true);

			int canBuildStatus = Checks.canBuildHousesHotels(propToBuild, player);

			switch (canBuildStatus) {
				case -1:
					buildHousesHotels(player);
				case -2:
					System.out.println("No houses or hotels have been built");
					return;
				default:
					break;
			}

			System.out.println("Would you like to build houses or a hotel?\n0 for houses\n1 for hotel");
			int choiceInput = InputOutput.integerMenu(0, 1, userInput);
			if (choiceInput == 1) {
				isHotel = true;
			}

			//inform the player of the current house distribution
			System.out.println("Current Houses Distribution for colour group " + propToBuild.getSquareColour() + ":\n\n");

			//print house distribution to screen
			for (Property currentProperty : colourGroup) {
				System.out.println(currentProperty.getName() + ": " + currentProperty.numHouses + "\n");
			}

			if (isHotel) {
				if (Game.getRemainingHotels() == 0) {
					System.out.println("The maximum number of hotels on the board has been reached, a hotel cannot currently be purchased for " + propToBuild.getName());
				} else if (propToBuild.numHouses != 4) {
					System.out.println("You must have 4 houses on this property to purchase a hotel");
				} else if (Checks.evenHouseDistribution(Checks.ownAllColour(player, propToBuild), propToBuild, true)) {
					//remove all houses from attribute as hotel is being built
					propToBuild.numHouses = 0;
					//add the 4 houses being replaced by the hotel back into the available pool
					Game.setRemainingHouses(Game.getRemainingHouses() + 4);
					//update the hotel attribute of the property
					propToBuild.numHotels = 1;
					//update hotel number
					Game.setRemainingHotels((Game.getRemainingHotels()) - 1);
					//price of hotel is price of additional house
					player.reduceMoney(propToBuild.getTitleDeedCard().getHousePrice(), null);
					System.out.println("Successfully purchased hotel for " + propToBuild.getName());
				} else
					System.out.println("Building a hotel on " + propToBuild.getName() + " will result in an uneven distribution in this colour group");
			}
			else {
				if (Game.getRemainingHouses() == 0) {
					System.out.println("The maximum number of houses on the board has been reached, a house cannot currently be purchased for " + propToBuild.getName());
				}

				//If there are four houses, they have reached the max number. Offer to purchase a hotel
				else if (propToBuild.numHouses == 4) {
					if (InputOutput.yesNoInput("You have built the maximum number of houses, would you like to build a hotel? (y/n)", player, userInput)) {
						buildHousesHotels(player);
					}
				}
				//use the house distribution method to check that building a house on the specified property will keep the colour group evenly distributed with houses
				else if (Checks.evenHouseDistribution(colourGroup, propToBuild, true)) {
					// y/n input to confirm intention to build house
					if (InputOutput.yesNoInput("Please confirm you wish to purchase a house for " + propToBuild.getName() + " (y/n)", player, userInput)) {
						if (propToBuild.getTitleDeedCard().getHousePrice() > player.getMoney()) {
							System.err.println("You cannot afford to purchase a house");
						} else {
							propToBuild.numHouses++;
							//update the remaining available houses
							Game.setRemainingHouses(Game.getRemainingHouses() - 1);
							player.reduceMoney(propToBuild.getTitleDeedCard().getHousePrice(), null);
							System.out.println("Successfully purchased house for " + propToBuild.getName() + "\nCurrent house count: " + propToBuild.numHouses);
						}
					}
				} else
					System.out.println("Building a house on " + propToBuild.getName() + " will result in an uneven house distribution in this colour group");
			}
		}
	}
		
	
	/**
	 * This is the method for selling houses which takes the arguments shown and returns the value of the houses sold if they are sold
	 * or returns 0/half the house price.
	 * It first of all checks if the property is mortgaged or bankrupt and as a result, it sells off all of the houses in one go at half their
	 * buy price and returns this value.
	 * If not, it checks if there are houses on a property while continually checking the house distribution in a colour group to make sure
	 * there is no bigger difference than 1 between all of them.
	 * @param player The player object wishing to sell houses on a property
	 * @param isMortgage Whether the property is mortgaged or not
	 * @param isBankrupt Whether the property is having houses sold because of the player object's bankruptcy
	 * @return valOfSoldHouses if houses sold, 0 or the house price/2 otherwise
	 */
	public int sellHouses(Player player, boolean isMortgage, boolean isBankrupt) {
	
		ArrayList<Property> colourGroup = Checks.ownAllColour(player, this);
		if(colourGroup==null) {
			System.out.println("You do not own all the properties in this colour");
			return 0;
		}
		//Checks if the property is mortgaged or bankrupt
		else if(isMortgage||isBankrupt) {
			int valOfSoldHouses = 0;
			for (Property property : colourGroup) {
				if (isMortgage) {
					player.addMoney((property.numHouses * this.getTitleDeedCard().getHousePrice()) / 2);
				}
				else {
					valOfSoldHouses += (property.numHouses * this.getTitleDeedCard().getHousePrice()) / 2;
				}
				//This sells the houses and hotels on the property for half their buy price because of bankruptcy or mortgage
				System.out.println(property.numHouses+" house(s) sold for: "+property.getName());
				Game.setRemainingHouses(Game.getRemainingHouses() + property.numHouses);
				property.numHouses = 0;
			}
			return valOfSoldHouses;
		}
		else if(this.numHouses==0) {
			System.out.println("There are no houses on "+this.getName());
			return 0;
		}
		else if(Checks.evenHouseDistribution(colourGroup, this, false)) {
			player.addMoney(this.getTitleDeedCard().getHousePrice()/2);
			this.numHouses--;
			Game.setRemainingHouses(Game.getRemainingHouses()+1);
			System.out.println("House successfully sold.\n\nCurrent Houses Distribution for colour group "+ this.getSquareColour()+":\n");
				
			//print house distribution to screen
			for (Property property : colourGroup) {
				System.out.println(property.getName() + ": " + property.numHouses + "\n");
			}
			//check if they would like to sell another house
			if(InputOutput.yesNoInput("Would you like to sell another house? (y/n)", player, null))	{
				sellHouses(player, false, false);
			}
			return this.getTitleDeedCard().getHousePrice()/2;
		}		
		else {
			System.out.println("The current distribution of your houses do not allow you to sell a house on "+this.getName());
			for (Property property : colourGroup) {
				System.out.println(property.getName() + ": " + property.numHouses + "\n");
			}
			return 0;
		}
	}
	/**
	 * This is the method for selling houses which takes the arguments shown and returns the value of the houses sold if they are sold
	 * or returns 0/half the house price.
	 * It first of all checks if the property is mortgaged or bankrupt and as a result, it sells off all of the houses in one go at half their
	 * buy price and returns this value.
	 * If not, it checks if there are houses on a property while continually checking the house distribution in a colour group to make sure
	 * there is no bigger difference than 1 between all of them.
	 * @param player The player object wishing to sell houses on a property
	 * @param isMortgage Whether the property is mortgaged or not
	 * @param isBankrupt Whether the property is having houses sold because of the player object's bankruptcy
	 * @return valOfSoldHouses if houses sold, 0 or the house price/2 otherwise
	 */
	public int sellHotels(Player player, boolean isMortgage, boolean isBankrupt) {
		ArrayList<Property> colourGroup = Checks.ownAllColour(player, this);
		if(isMortgage||isBankrupt) {
			int valOfSoldHouses = 0;
			for (Property property : colourGroup) {
				//selling a hotel is the equivalent of selling five houses
				//this will add 0 if there is no hotel
				if (isMortgage) {
					//ensure the player is not bankrupt before increasing their money
					player.addMoney(((this.numHotels) * (5 * this.getTitleDeedCard().getHousePrice())) / 2);
				}
				valOfSoldHouses += ((this.numHotels) * (5 * this.getTitleDeedCard().getHousePrice())) / 2;
				Game.setRemainingHotels(Game.getRemainingHotels() + this.numHotels);
				property.numHotels = 0;
			}
			return valOfSoldHouses;
		}
		//first check if there is enough houses to break the hotel down into houses
		else if(Game.getRemainingHouses()<4) {
			System.out.println("There are insufficeint houses "+Game.getRemainingHouses()+" to exchange your hotel. In order to sell this hotel directly, all housese/hotels in this colour group will be sold.");
			if(InputOutput.yesNoInput("Would you like to proceed (y/n)", player, null)) {
				//sell all hotels directly 
				return (sellHotels(player, true, false)+this.sellHouses(player, true,false));
			}
			else{
				//they have decided against selling anything so return 0
				return 0;
			}	
		}
		else {
			player.addMoney(this.getTitleDeedCard().getHousePrice()/2);
			this.numHotels=0;
			//return house number to 4
			this.numHouses=4;
			Game.setRemainingHouses(Game.getRemainingHouses()-4);
			return this.getTitleDeedCard().getHousePrice()/2;
		}
	}
}
	
	

