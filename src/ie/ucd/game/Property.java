package ie.ucd.game;

import java.util.List;

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
		if(siteToBuy.owner.getName() == "BANK") {
			System.out.println(player.getName()+", would you like to purchase "+siteToBuy.getName()+"?");
			
		}
	}

	public void sell(Player player, CanOwn siteToSell, List<Player> listPlayers) {
		
	}
	
	
}
