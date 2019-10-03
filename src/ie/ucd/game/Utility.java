package ie.ucd.game;

import java.util.List;
import java.util.Scanner;

public class Utility extends PublicSquare {
	private int[] rent; //Rent is size 2
	//The rents will be dealt with at a level above

	public Utility(String name, int indexLocation, int buyPrice, int mortgage, int[] rent, Player owner) {
		super(name, indexLocation, buyPrice, mortgage, owner);
		this.rent = rent;
	}
	
	public int[] getRent() {
		return this.rent;
	}
	
	public void setRent(int[] rent) {
		this.rent = rent;
	}

	
	public void buy(Player player, List<Player> listPlayers) {
		Scanner buyScanner = new Scanner(System.in);
		String buyAcknowledgement;
		if(this.owner == null) {
			
			//check user has enough funds to purchase 
			if(player.getMoney() < this.getPrice()) {
				System.out.println("You do not have the necessary funds to purchase this Utility.\nYour Funds: "+player.getMoney()+"\nUtility Price: "+this.getPrice());
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
				
			player.reduceMoney(this.getPrice());
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
}
