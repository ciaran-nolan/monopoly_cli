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
	
	
	public Property(int squareNum, String squareColour, String title, int priceBuy, int[] rents, int housePrice, int mortgage, Player owner, int numHouses, int numHotels) {
		super(title, squareNum, mortgage, priceBuy, owner);
		
		this.squareColour = squareColour;
		this.rents = rents;
		this.housePrice = housePrice;
		this.numHotels = numHotels;
		this.numHouses = numHouses;
	}
	
	public void buy(Player player, List<Player> listPlayers) {
		Scanner buyScanner = new Scanner(System.in);
		String buyAcknowledgement;
		if(this.owner.getName() == "BANK") {
			System.out.println(player.getName()+", would you like to purchase "+this.getName()+"?");
			buyAcknowledgement = buyScanner.next();
			
			while(!(buyAcknowledgement.equalsIgnoreCase("y") || buyAcknowledgement.equalsIgnoreCase("n"))) {
				System.out.println(player.getName()+", please enter a valid response (y/n)");
				buyAcknowledgement = buyScanner.next();
				System.out.println(buyAcknowledgement);
			}
			if(buyAcknowledgement.equalsIgnoreCase("y")) {
				if(player.getMoney() < this.getPrice()) {
					System.out.println("You do not have the necessary funds to purchase this property.\nYour Funds: "+player.getMoney()+"\nProperty Price: "+this.getPrice());
				}
				else {
					player.reduceMoney(this.getPrice());
					System.out.println("You have purchased "+this.getName()+" for "+this.getPrice()+"\nRemaining Funds: "+player.getMoney());
					}
				}
			else if(buyAcknowledgement.equalsIgnoreCase("n")) {
				this.playerAuction(listPlayers);
			}
			}
		else {
			//include code for player-to-player transactions
		}
		}

	public void sell(Player player, CanOwn siteToSell, List<Player> listPlayers) {
		
	}
	
	
}
