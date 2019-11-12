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
		TitleDeed titleDeedCard = this.getTitleDeedCard();
		//The player doesn't have enough money to purchase it
		if(!Checks.enoughFunds(player, titleDeedCard.getPriceBuy())) {
			System.err.println("You do not have the necessary funds to purchase this train.\nYour Funds: "+player.getMoney()+"\nProperty Price: "+titleDeedCard.getPriceBuy());
			//player does not have enough funds to buy property, automatically enter auction
			this.playerAuction();
		}
		//Property is already owned
		else if(!(Checks.canBuy((CanOwn) this, player))){
			System.err.println("This property is already owned!");
		}
		//They can purchase it
		else if(InputOutput.yesNoInput(player.getName()+", would you like to purchase "
				+this.getName()+" for £"+titleDeedCard.getPriceBuy()+"?", player)) {
			//user has passed all necessary checks to purchase a property, reduce the price from users funds
			System.out.println("You have purchased "+this.getName()+" for "+titleDeedCard.getPriceBuy());
			player.reduceMoney(titleDeedCard.getPriceBuy(), null);
			//add property to users property list
			player.addPurchasedCard(this);
		}
		//Send to auction
		else {
			this.playerAuction();
		}
		
	}

}
