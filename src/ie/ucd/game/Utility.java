package ie.ucd.game;

import java.util.List;
import java.util.Scanner;

public class Utility extends PublicSquare {
	private int[] rent; //Rent is size 2
	//The rents will be dealt with at a level above

	public Utility(String name, int indexLocation, int buyPrice, int mortgage, int[] rent, Player owner) {
		super(name, indexLocation, buyPrice, mortgage, owner, Square.SquareType.UTILITY);
		this.rent = rent;
	}
	
	public int[] getRent() {
		return this.rent;
	}
	
	public void setRent(int[] rent) {
		this.rent = rent;
	}

	
	public void buy(Player player) {
		//check user has enough funds to purchase 
		if(player.getMoney() < this.getPrice()) {
			System.err.println("You do not have the necessary funds to purchase this property.\nYour Funds: "+player.getMoney()+"\nProperty Price: "+this.getPrice());
			//player does not have enough funds to buy property, automatically enter auction
			this.playerAuction();
		}
		else if(InputOutput.yesNoInput(player.getName()+", would you like to purchase "+this.getName()
				+" for £"+this.getPrice()+"?", player)) {
				//user has passed all necessary checks to purchase a property, reduce the price from users funds
				player.reduceMoney(this.getPrice(), null);
				//add property to users property list
				player.addPurchasedCard(this);
				System.out.println("You have purchased "+this.getName()+" for "+this.getPrice());
		}
		else{
			this.playerAuction();
		}
	}
}
