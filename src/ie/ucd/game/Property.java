package ie.ucd.game;

import java.util.List;
import java.util.Scanner;

public class Property extends CanOwn {
	private String squareColour;
	private int[] rents;
	private int housePrice;
	private String Owner;
	private int numHouses;
	private int numHotels;
	
	public int getNumHotels() {
		return this.numHotels;
	}
	
	public int getNumHouses() {
		return this.numHouses;
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
		Scanner buyScanner = new Scanner(System.in);
		String buyAcknowledgement;
		if(this.owner == null) {
			
			//check user has enough funds to purchase 
			if(player.getMoney() < this.getPrice()) {
				System.out.println("You do not have the necessary funds to purchase this property.\nYour Funds: "+player.getMoney()+"\nProperty Price: "+this.getPrice());
				this.playerAuction(listPlayers);
				buyScanner.close();
				return;
			}else{
				System.out.println(player.getName()+", would you like to purchase "+this.getName()+"?");
				buyAcknowledgement = buyScanner.next();
			}
			
			
			while(!(buyAcknowledgement.equalsIgnoreCase("y") || buyAcknowledgement.equalsIgnoreCase("n"))) {
				System.out.println(player.getName()+", please enter a valid response (y/n)");
				buyAcknowledgement = buyScanner.next();
				System.out.println(buyAcknowledgement);
			}
			if(buyAcknowledgement.equalsIgnoreCase("y")) {
			
			//user has passed all necessary checks to purchase a property, reduce the price from users funds
			player.reduceMoney(this.getPrice());
			//add property to users property list
			player.addPurchasedCard(this);
			System.out.println("You have purchased "+this.getName()+" for "+this.getPrice()+"\nRemaining Funds: "+player.getMoney());
					
				}
			else if(buyAcknowledgement.equalsIgnoreCase("n")) {
				this.playerAuction(listPlayers);
			}
			buyScanner.close();
			}
		else {
			
			//include code for player-to-player transactions
		}
		}

	
	
	public void buildHouses(Player player) {
		
		// need to add check for: all colours in range && number of houses is being spread evenly
		
		
		
		if(player.getMoney() < this.housePrice) {
			System.out.println("You do not have enough funds to purchase any houses for "+this.getName()+"\nYour funds: "+player.getMoney()+"\nHouse Price: "+this.housePrice);
		}
		//the player owns all of the houses (Maybe do this check elsewhere)
		else if(this.numHouses==4) {
				System.out.println("You have built the maximum number of houses, would you like to build a hotel? (y/n)");
				Scanner houseScanner = new Scanner(System.in);
				String hotelAcknowledgement = houseScanner.next();
				
				//check correct bid acknowledgement (y/n) has been made
				while(!(hotelAcknowledgement.equalsIgnoreCase("y") || hotelAcknowledgement.equalsIgnoreCase("n"))) {
					System.out.println(player.getName()+", please enter a valid response (y/n)");
					hotelAcknowledgement = houseScanner.next();
				}
				
				//check if user has confirmed intention to bid again
				if(hotelAcknowledgement.equalsIgnoreCase("y")) {
					this.buildHotel(player);
				}
				houseScanner.close();
		}
		else {
			Scanner houseScanner = new Scanner(System.in);
			System.out.println("You have decided to build houses.\nYour funds: "+player.getMoney()+"\nHouse Price: "+this.housePrice);
			System.out.println("How many houses do you want to build?");
			
			int houseNum = houseScanner.nextInt();
			
			while(player.getMoney() < houseNum*this.housePrice) {
				System.out.println("You do not have enough funds to purchase "+houseNum+" houses for "+this.getName()+"\nYour funds: "+player.getMoney()+"\nPrice of "+houseNum+" houses: "+4*this.housePrice+"\n\nWould you like to make a different choice? (y/n)");

				String houseAcknowledgement = houseScanner.next();
				//check correct bid acknowledgement (y/n) has been made
				while(!(houseAcknowledgement.equalsIgnoreCase("y") || houseAcknowledgement.equalsIgnoreCase("n"))) {
					System.out.println(player.getName()+", please enter a valid response (y/n)");
					houseAcknowledgement = houseScanner.next();
				}
				
				//check if user has confirmed intention to bid again
				if(houseAcknowledgement.equalsIgnoreCase("y")) {
					this.buildHouses(player);
				}
				else {
					System.out.println("You have decided against building additional houses. Current house count on "+this.getName()+": "+this.numHouses);
					houseScanner.close();
					return;
				}
			}
			
			this.numHouses += houseNum;
			player.reduceMoney(houseNum*this.housePrice);
			houseScanner.close();
			
		}	
	}
	public void buildHotel(Player player) {
		if(player.getMoney() < this.housePrice) {
			System.out.println("You do not have enough funds to purchase a hotel for "+this.getName()+"\nYour funds: "+player.getMoney()+"\nHotel Price: "+this.housePrice);
			return;
		}
		else {
			this.numHouses=0;
			this.numHotels=1;
			player.reduceMoney(this.housePrice);
		}
		
	}
	public void sell(Player player, CanOwn siteToSell, List<Player> listPlayers) {
		
	}
	
	
}
