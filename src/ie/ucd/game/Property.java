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
				this.buildHotel(player);
			}
		}
		else if(Checks.evenHouseDistribution(colourGroup, this)) {
			
		}
	}
		
		// need to add check for: all colours in range && number of houses is being spread evenly
		/*if(player.getMoney() < this.housePrice) {
			System.out.println("You do not have enough funds to purchase any houses for "+this.getName()+"\nYour funds: "+player.getMoney()+"\nHouse Price: "+this.housePrice);
		}
		//the player owns all of the houses (Maybe do this check elsewhere)
		else if(this.numHouses==4) {
			if(Checks.yesNoInput("You have built the maximum number of houses, would you like to build a hotel? (y/n)", player)) {
				this.buildHotel(player);
			}
			else {
				System.out.println("You have decided against building a hotel. Number of houses for "+this.getName()+" remains at 4.");
				return;
			}
					
		}
		else {
			while(Checks.yesNoInput("Your funds: "+player.getMoney()+"\nHouse Price: "+this.housePrice+"Do you want to build a house? (y/n)", player){
				
			
			Scanner houseScanner = new Scanner(System.in);
			System.out.println("You have decided to build houses.\nYour funds: "+player.getMoney()+"\nHouse Price: "+this.housePrice);
			System.out.println("How many houses do you want to build?");
			
			int houseNum = houseScanner.nextInt();
			
			while(player.getMoney() < houseNum*this.housePrice || (houseNum > 0 && (houseNum+this.numHouses) <= 4 )) {
				
				
				if(player.getMoney() < houseNum*this.housePrice && Checks.yesNoInput("You do not have enough funds to purchase "+houseNum+" houses for "+this.getName()+"\nYour funds: "+player.getMoney()+"\nPrice of "+houseNum+" houses: "+4*this.housePrice+"\n\nWould you like to make a different choice? (y/n)", player)) {
					this.buildHouses(player);
				}
				else {
					System.out.println("You have decided against building additional houses. Current house count on "+this.getName()+": "+this.numHouses);
					return;
				}
				if((houseNum < 0 && (houseNum+this.numHouses) >= 4 ) && Checks.yesNoInput("You have chosen an inappropriate number of houses. Current number of houses: "+this.numHouses+". Your choice: "+houseNum+"\n\nWould you like to make a different choice? (y/n)", player)) {
					this.buildHouses(player);
				}
				
			}
			
			this.numHouses += houseNum;
			player.reduceMoney(houseNum*this.housePrice);
			houseScanner.close();
			
		}
		}
	}*/
	public void buildHotel(Player player) {
		if(player.getMoney() < this.housePrice) {
			System.out.println("You do not have enough funds to purchase a hotel for "+this.getName()+"\nYour funds: "+player.getMoney()+"\nHotel Price: "+this.housePrice);
			return;
		}
		else {
			this.numHouses=0;
			this.numHotels=1;
			//price of hotel is price of additional house
			player.reduceMoney(this.housePrice);
		}
		
	}
	
	
	
	
}
