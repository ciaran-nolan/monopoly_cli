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
	

	
	public String getSquareColour() {
		return this.squareColour;
	}
	
	public int[] getRent() {
		return this.rents;
	}
	
	public Property(int squareNum, String squareColour, String title, int priceBuy, int[] rents, int housePrice, int mortgage) {
		
		//owner will always be null at constructor since a property starts without an owner
		super(title, squareNum, mortgage, priceBuy, null);
		
		this.squareColour = squareColour;
		this.rents = rents;
		this.housePrice = housePrice;
		this.numHotels = 0;
		this.numHouses = 0;
	}
	
	public void buy(Player player, List<Player> listPlayers) {
		
			//check user has enough funds to purchase 
			if(player.getMoney() < this.getPrice()) {
				System.out.println("You do not have the necessary funds to purchase this property.\nYour Funds: "+player.getMoney()+"\nProperty Price: "+this.getPrice());
				//player does not have enough funds to buy property, automatically enter auction
				this.playerAuction(listPlayers);
				return;
			}
			else if(Checks.yesNoInput(player.getName()+", would you like to purchase "+this.getName()+"?", player)) {
				
			//user has passed all necessary checks to purchase a property, reduce the price from users funds
			player.reduceMoney(this.getPrice());
			//add property to users property list
			player.addPurchasedCard(this);
			System.out.println("You have purchased "+this.getName()+" for "+this.getPrice()+"\nRemaining Funds: "+player.getMoney());}
			else{
				this.playerAuction(listPlayers);
			}
			}
	public void sell(Player player, CanOwn siteToSell, List<Player> listPlayers) {
		
	}
	
	
	public void buildHouses(Player player, ArrayList<Property> colourGroup) {
		
		//we know they own all houses in property group
		System.out.println("You have chosen to purchase houses for: "+this.getName());
		
		if(player.getMoney() < this.housePrice) {
			System.out.println("You do not have enough funds to purchase any houses for "+this.getName()+"\nYour funds: "+player.getMoney()+"\nHouse Price: "+this.housePrice);
			return;
		}

		System.out.println("Current Houses Distribution for colour group "+ this.getSquareColour()+":\n\n");
		
		//print house distribution to screen
		for(int i=0; i<colourGroup.size(); i++) {
			System.out.println(colourGroup.get(i).getName()+": "+colourGroup.get(i).getNumHouses()+"\n");
		}
		
		if(player.getMoney() < this.housePrice) {
			System.out.println("You do not have enough funds to purchase any houses for "+this.getName()+"\nYour funds: "+player.getMoney()+"\nHouse Price: "+this.housePrice);
			return;
		}
		
		else if(this.numHouses==4) {
			if(Checks.yesNoInput("You have built the maximum number of houses, would you like to build a hotel? (y/n)", player)) {
				this.buildHotel(player, colourGroup);
			}
		}
		else if(Checks.evenHouseDistribution(colourGroup, this, true)) {
			if(Checks.yesNoInput("Please confirm you wish to purchase a house for "+this.getName()+" (y/n)", player)) {
				this.numHouses++;
				player.reduceMoney(this.housePrice);
				System.out.println("Successfully purchased house for "+this.getName()+"\nCurrent house count: "+this.numHouses);
				}
			}
		else {
			System.out.println("Building a house on "+this.getName()+" will result in an uneven house distribution in this colour group");
		}
	}
		
	public void buildHotel(Player player, ArrayList<Property> colourGroup) {
		if(player.getMoney() < this.housePrice) {
			System.out.println("You do not have enough funds to purchase a hotel for "+this.getName()+"\nYour funds: "+player.getMoney()+"\nHotel Price: "+this.housePrice);
			return;
		}
		else if(Checks.yesNoInput("Would you like to build a hotel for: "+this.getName()+"? (y/n)", player)){
			
			if(Checks.evenHouseDistribution(colourGroup, this, true)){
		
			this.numHouses=0;
			this.numHotels=1;
			//price of hotel is price of additional house
			player.reduceMoney(this.housePrice);
			System.out.println("Sucessfully purchased hotel for "+this.getName());
		}
			else {
				System.out.println("Building a hotel on "+this.getName()+" will result in an uneven distribution in this colour group");
			}
		}
		
	}
	
	public void sellHouses(Player player, boolean isMortgage) {
		ArrayList<Property> colourGroup = Checks.ownAllColour(player, this);
		if(isMortgage) {
			for (int i = 0; i< colourGroup.size(); i++) {
				player.addMoney((colourGroup.get(i).getNumHouses()*this.housePrice)/2);
				colourGroup.get(i).numHouses=0;
			}
		}
		// specify false to indicate to checks method you wish to SELL houses
		else if(Checks.evenHouseDistribution(colourGroup, this, false)) {
			player.addMoney(this.housePrice/2);
			this.numHouses--;
			
			System.out.println("Current Houses Distribution for colour group "+ this.getSquareColour()+":\n\n");
			
			//print house distribution to screen
			for(int i=0; i<colourGroup.size(); i++) {
				System.out.println(colourGroup.get(i).getName()+": "+colourGroup.get(i).getNumHouses()+"\n");
			}
			
			if(Checks.yesNoInput("Would you like to sell another house? (y/n)", player)){
				sellHouses(player, false);
			}
			
		}
		else {
			System.out.println("The current distribution of your houses do not allow you to sell a house on "+this.getName());
			for(int i=0; i<colourGroup.size(); i++) {
				System.out.println(colourGroup.get(i).getName()+": "+colourGroup.get(i).getNumHouses()+"\n");
			}
		}
	}
	
	public void sellHotels (Player player, boolean isMortgage) {
		ArrayList<Property> colourGroup = Checks.ownAllColour(player, this);
		if(isMortgage) {
			for (int i = 0; i< colourGroup.size(); i++) {
				//selling a hotel is the equivalent of selling five houses
				player.addMoney((5*this.housePrice)/2);
				colourGroup.get(i).numHotels=0;
			}
		}
	}
	
	
}
