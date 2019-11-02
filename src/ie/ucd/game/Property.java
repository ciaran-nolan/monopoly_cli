package ie.ucd.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Property extends CanOwn {
	private String squareColour;
	private int[] rents;
	private int housePrice;
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
	
	public int getHousePrice() {
		return this.housePrice;
	}
	public String getSquareColour() {
		return this.squareColour;
	}
	
	public int[] getRent() {
		return this.rents;
	}
	
	public Property(int squareNum, String squareColour, String title, int priceBuy, int[] rents, int housePrice, int mortgage) {
		
		//owner will always be null at constructor since a property starts without an owner
		super(title, squareNum, mortgage, priceBuy, null, Square.SquareType.PROPERTY);
		
		this.squareColour = squareColour;
		this.rents = rents;
		this.housePrice = housePrice;
		this.numHotels = 0;
		this.numHouses = 0;
	}
	
	public void buy(Player player) {
		
			//check user has enough funds to purchase 
			if(!Checks.enoughFunds(player, this.getPrice())) {
				System.out.println("You do not have the necessary funds to purchase this property.\nYour Funds: "+player.getMoney()+"\nProperty Price: "+this.getPrice());
				//player does not have enough funds to buy property, automatically enter auction
				this.playerAuction();
				return;
			}
			else if(!(Checks.canBuy((CanOwn) this, player))){
				System.out.println("This property is already owned!");
			}
			else if(InputOutput.yesNoInput(player.getName()+", would you like to purchase "+this.getName()+"?", player)) {
				if(!Checks.enoughFunds(player, this.getPrice())) {
					System.err.println("You do not have enough money to purchase this Utility! Raise money please");
				}
				else {
					//user has passed all necessary checks to purchase a property, reduce the price from users funds
					player.reduceMoney(this.getPrice(), null);
					//add property to users property list
					player.addPurchasedCard(this);
					System.out.println("You have purchased "+this.getName()+" for "+this.getPrice()+"\nRemaining Funds: "+player.getMoney());
				}
			}
			else this.playerAuction();
			
			}
	
	public void sell(Player player, CanOwn siteToSell, List<Player> listPlayers) {
		ArrayList<Property> colourGroup = Checks.ownAllColour(player, this);
		if(colourGroup!=null) {
			if(InputOutput.yesNoInput("If any of the properties in this colour group are improved, selling "+this.getName()+" will result in all houses and hotels being sold. Do you wish to continue? (y/n)", player));
			{
				//the isMortgage parameter will provide the same outcome by selling all houses
				this.sellHouses(player, true, false);
				this.sellHotels(player, true, false);
			}
			
		}
	}
	
	public static void buildHousesHotels(Player player) {
		Scanner input = new Scanner(System.in);
		boolean isHotel = false;
		
		System.out.println("Please enter the name of the property you wish to purchase houses/hotels for");
		String propName = input.nextLine();
		Property propToBuild = Checks.isValidProp(propName, player);
		ArrayList<Property> colourGroup = Checks.ownAllColour(player, propToBuild);

		int canBuildStatus = Checks.canBuildHousesHotels(propToBuild, player);

		switch(canBuildStatus){
			case -1:
				buildHousesHotels(player);
			case -2:
				System.out.println("No houses or hotels have been built");
				return;
			default:
				break;
		}

		System.out.println("Would you like to build houses or a hotel?\n0 for houses\n1 for hotel");
		int choiceInput =  InputOutput.integerMenu(0, 1);
		if(choiceInput==1) {
			isHotel=true;
		}

		//inform the player of the current house distribution
		System.out.println("Current Houses Distribution for colour group "+ propToBuild.getSquareColour()+":\n\n");
		
		//print house distribution to screen
		for (Property property : colourGroup) {
			System.out.println(property.getName() + ": " + property.getNumHouses() + "\n");
		}
		
		if(isHotel) {
			if(Game.getRemainingHotels()==0) {
				System.out.println("The maximum number of hotels on the board has been reached, a hotel cannot currently be purchased for "+propToBuild.getName());
				return;
			}
			else if(propToBuild.numHouses!=4) {
				System.out.println("You must have 4 houses on this property to purchase a hotel");
				return;
			}
			else if(Checks.evenHouseDistribution(Checks.ownAllColour(player, propToBuild), propToBuild, true)){
				//remove all houses from attribute as hotel is being built
				propToBuild.numHouses=0;
				//add the 4 houses being replaced by the hotel back into the available pool
				Game.setRemainingHouses(Game.getRemainingHouses()+4);
				//update the hotel attribute of the property
				propToBuild.numHotels=1;
				//update hotel number
				Game.setRemainingHotels((Game.getRemainingHotels())-1);
				//price of hotel is price of additional house
				player.reduceMoney(propToBuild.housePrice, null);
				System.out.println("Successfully purchased hotel for "+propToBuild.getName());
			}
			else System.out.println("Building a hotel on "+propToBuild.getName()+" will result in an uneven distribution in this colour group");
		}
		else {
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
					if (propToBuild.getHousePrice() > player.getMoney()) {
						System.err.println("You cannot afford to purchase a house");
					} else {
						propToBuild.numHouses++;
						//update the remaining available houses
						Game.setRemainingHouses(Game.getRemainingHouses() - 1);
						player.reduceMoney(propToBuild.housePrice, null);
						System.out.println("Successfully purchased house for " + propToBuild.getName() + "\nCurrent house count: " + propToBuild.numHouses);
					}
				}
			} else
				System.out.println("Building a house on " + propToBuild.getName() + " will result in an uneven house distribution in this colour group");
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
				player.addMoney((property.getNumHouses() * this.housePrice) / 2);
			}
			valOfSoldHouses += (property.getNumHouses() * this.housePrice) / 2;
			Game.setRemainingHouses(Game.getRemainingHouses() + property.getNumHouses());
			property.numHouses = 0;
		}
		return valOfSoldHouses;
	}
		// specify false to indicate to checks method you wish to SELL houses
	else if(this.numHouses==0) {
		System.out.println("There are no houses on "+this.getName());
		return 0;
	}

	else if(Checks.evenHouseDistribution(colourGroup, this, false)) {
			
		player.addMoney(this.housePrice/2);
		this.numHouses--;
		Game.setRemainingHouses(Game.getRemainingHouses()+1);
		System.out.println("House successfully sold.\n\nCurrent Houses Distribution for colour group "+ this.getSquareColour()+":\n\n");
			
		//print house distribution to screen
		for (Property property : colourGroup) {
			System.out.println(property.getName() + ": " + property.getNumHouses() + "\n");
		}
			//check if they would like to sell another house
		if(InputOutput.yesNoInput("Would you like to sell another house? (y/n)", player)){
			sellHouses(player, false, false);
		}
		return this.housePrice/2;
		}
			
	else {
		System.out.println("The current distribution of your houses do not allow you to sell a house on "+this.getName());
		for (Property property : colourGroup) {
			System.out.println(property.getName() + ": " + property.getNumHouses() + "\n");
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
					player.addMoney(((this.numHotels) * (5 * this.housePrice)) / 2);
				}
				valOfSoldHouses += ((this.numHotels) * (5 * this.housePrice)) / 2;
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
			player.addMoney(this.housePrice/2);
			this.numHotels=0;
			//return house number to 4
			this.numHouses=4;
			Game.setRemainingHouses(Game.getRemainingHouses()-4);
			return this.housePrice/2;	
		}
	}
	
	//method to determine the value of a player's houses/hotels
	public static int playerHouseHotelEval(Player player) {
		int houseHotelVal=0;
		for(CanOwn ownable:player.getPropertyList()) {
			if(ownable instanceof Property) {
				if(((Property) ownable).getNumHotels()==0){
					//if there are no houses on the property, this will not increment by anything
				houseHotelVal+=((((Property) ownable).getHousePrice())*((Property) ownable).getNumHouses())/2;
				}
				else {
					houseHotelVal+=((((Property) ownable).getHousePrice())*(5))/2;
				}
			}
		}
		return houseHotelVal;
		
	}
}
	
	

