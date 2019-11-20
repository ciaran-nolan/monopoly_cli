package ie.ucd.game;

public class Utility extends PublicSquare {
	//private int[] rent; //Rent is size 2
	//The rents will be dealt with at a level above

	public Utility(String name, int indexLocation) {
		super(name, indexLocation, Square.SquareType.UTILITY);
		//this.rent = rent;
	}

	public void buy(Player player) {
		//check user has enough funds to purchase 
		if(player.getMoney() < this.getTitleDeedCard().getPriceBuy()) {
			System.err.println("You do not have the necessary funds to purchase this property.\nYour Funds: "
					+player.getMoney()+"\nProperty Price: "+this.getTitleDeedCard().getPriceBuy());
			//player does not have enough funds to buy property, automatically enter auction
			this.getTitleDeedCard().playerAuction(null);
		}
		else if(InputOutput.yesNoInput(player.getName()+", would you like to purchase "+this.getName()
				+" for £"+this.getTitleDeedCard().getPriceBuy()+"?", player)) {
				//user has passed all necessary checks to purchase a property, reduce the price from users funds
				player.reduceMoney(this.getTitleDeedCard().getPriceBuy(), null);
				//add property to users property list
				player.addPurchasedTitleDeed(this.getTitleDeedCard());
				System.out.println("You have purchased "+this.getName()+" for "+this.getTitleDeedCard().getPriceBuy());
		}
		else{
			this.getTitleDeedCard().playerAuction(null);
		}
	}
}
