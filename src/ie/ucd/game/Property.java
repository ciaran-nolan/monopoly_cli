package ie.ucd.game;

import java.util.List;
import java.util.Scanner;

public class Property extends CanOwn {
	private String squareColour;
	private int[] rents;
	private int housePrice;
	private String Owner;
	
	public Property(int squareNum, String squareColour, String title, int priceBuy, int[] rents, int housePrice, int mortgage, Player owner) {
		super(title, squareNum, mortgage, priceBuy, owner);
		
		this.squareColour = squareColour;
		this.rents = rents;
		this.housePrice = housePrice;
		
	}
	
	public void buy(Player player, CanOwn siteToBuy, List<Player> listPlayers) {
		Scanner buyScanner = new Scanner(System.in);
		String buyAcknowledgement;
		if(siteToBuy.owner.getName() == "BANK") {
			System.out.println(player.getName()+", would you like to purchase "+siteToBuy.getName()+"?");
			buyAcknowledgement = buyScanner.next();
			
			while(buyAcknowledgement != "Y" || buyAcknowledgement != "y" || buyAcknowledgement != "N" || buyAcknowledgement != "n") {
				System.out.println(player.getName()+", please enter a valid response (y/n)");
				buyAcknowledgement = buyScanner.next();
			}
			if(buyAcknowledgement == "y" || buyAcknowledgement == "Y") {
				if(player.getMoney() < siteToBuy.getPrice()) {
					System.out.println("You do not have the necessary funds to purchase this property.\nYour Funds: "+player.getMoney()+"\nProperty Price: "+siteToBuy.getPrice());
				}
				else {
					player.reduceMoney(siteToBuy.getPrice());
					System.out.println("You have purchased "+siteToBuy.getName()+" for "+siteToBuy.getPrice()+"\nRemaining Funds: "+player.getMoney());
					}
				}
			else {
				siteToBuy.playerAuction(listPlayers);
			}
			}
		}

	public void sell(Player player, CanOwn siteToSell, List<Player> listPlayers) {
		
	}
	
	
}
