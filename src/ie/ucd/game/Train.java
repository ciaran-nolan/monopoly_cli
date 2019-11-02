package ie.ucd.game;

import java.util.List;

public class Train extends PublicSquare {
	private int[] rent; //Rent for a train is Size 4 
	
	public Train(String name, int indexLocation, int buyPrice, int mortgage, int[] rent, Player owner) {
		super(name, indexLocation, buyPrice, mortgage, owner, Square.SquareType.TRAIN);
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
		if(!Checks.enoughFunds(player, this.getPrice())) {
			System.out.println("You do not have the necessary funds to purchase this train.\nYour Funds: "+player.getMoney()+"\nProperty Price: "+this.getPrice());
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

}
