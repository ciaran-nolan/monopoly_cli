package ie.ucd.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Property extends CanOwn {
	private String squareColour;
	private int numHouses;
	private int numHotels;
	
	public int getNumHotels() {
		return this.numHotels;
	}
	
	public int getNumHouses() {
		return this.numHouses;
	}
	
	public void setNumHouses(int numHouses) {
		this.numHouses = numHouses;
	}
	
	public void setNumHotels(int numHotels) {
		this.numHotels = numHotels;
	}

	public String getSquareColour() {
		return this.squareColour;
	}

	public Property(int squareNum, String squareColour, String title) {
		//owner will always be null at constructor since a property starts without an owner
		super(title, squareNum, Square.SquareType.PROPERTY);
		this.squareColour = squareColour;
		this.numHotels = 0;
		this.numHouses = 0;
	}
	
	public void buy(Player player) {
			//check user has enough funds to purchase
			TitleDeed titleDeedCard = this.getTitleDeedCard();
			if(!Checks.enoughFunds(player, titleDeedCard.getPriceBuy())) {
				System.out.println("You do not have the necessary funds to purchase this property.\nYour Funds: "
                        +player.getMoney()+"\nProperty Price: "+titleDeedCard.getPriceBuy());
				//player does not have enough funds to buy property, automatically enter auction
				titleDeedCard.playerAuction( null);

			}
			//the case of the owner should be handled in check square
			else if(!(Checks.canBuy(this.getTitleDeedCard(), player))){
				System.out.println("This property is already owned!");
			}
			else if(InputOutput.yesNoInput(player.getName()+", would you like to purchase "
                    +this.getName()+" for £"+titleDeedCard.getPriceBuy()+"?", player)) {
				//user has passed all necessary checks to purchase a property, reduce the price from users funds
				System.out.println("You have purchased "+this.getName()+" for "+titleDeedCard.getPriceBuy());

				player.reduceMoney(titleDeedCard.getPriceBuy(), null);
				//add property to users property list
				player.addPurchasedTitleDeed(titleDeedCard);
			}
			else titleDeedCard.playerAuction(null);
			}

	
	public static void buildHousesHotels(Player player) {
		boolean isHotel = false;

		//System.out.println("Please enter the name of the property you wish to purchase houses/hotels for");
		//String propName = input.nextLine();


		//Property propToBuild = Checks.isValidProp(propName, player);
		TitleDeed titleDeedToBuild = InputOutput.titleDeedOperationMenu(player, "purchase houses/hotels for", true);
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
			int choiceInput = InputOutput.integerMenu(0, 1);
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
					return;
				} else if (propToBuild.numHouses != 4) {
					System.out.println("You must have 4 houses on this property to purchase a hotel");
					return;
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
			} else {
				if (Game.getRemainingHouses() == 0) {
					System.out.println("The maximum number of houses on the board has been reached, a house cannot currently be purchased for " + propToBuild.getName());
					return;
				}

				//If there are four houses, they have reached the max number. Offer to purchase a hotel
				else if (propToBuild.numHouses == 4) {
					if (InputOutput.yesNoInput("You have built the maximum number of houses, would you like to build a hotel? (y/n)", player)) {
						buildHousesHotels(player);
					}
				}
				//use the house distribution method to check that building a house on the specified property will keep the colour group evenly distributed with houses
				else if (Checks.evenHouseDistribution(colourGroup, propToBuild, true)) {
					// y/n input to confirm intention to build house
					if (InputOutput.yesNoInput("Please confirm you wish to purchase a house for " + propToBuild.getName() + " (y/n)", player)) {
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
		
	
	//method to sell houses on a property
	public int sellHouses(Player player, boolean isMortgage, boolean isBankrupt) {
	
		ArrayList<Property> colourGroup = Checks.ownAllColour(player, this);
	if(colourGroup==null) {
		System.out.println("You do not own all the properties in this colour");
		return 0;
	}
	else if(isMortgage||isBankrupt) {
		int valOfSoldHouses = 0;
		for (Property property : colourGroup) {
			if (isMortgage) {
				player.addMoney((property.numHouses * this.getTitleDeedCard().getHousePrice()) / 2);
			}
			else{
				valOfSoldHouses += (property.numHouses * this.getTitleDeedCard().getHousePrice()) / 2;
			}
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
		if(InputOutput.yesNoInput("Would you like to sell another house? (y/n)", player)){
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
	
	public int sellHotels (Player player, boolean isMortgage, boolean isBankrupt) {
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
			if(InputOutput.yesNoInput("Would you like to proceed (y/n)", player)) {
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
	
	

