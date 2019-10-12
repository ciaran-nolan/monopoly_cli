package ie.ucd.game;
import java.util.*;

public abstract class PublicSquare extends CanOwn {
	//private int rent; //Rent in this case is different to rent in the case of properties with colours

	public PublicSquare(String name, int indexLocation, int buyPrice, int mortgage, Player owner, Square.SquareType type) {
		super(name, indexLocation, mortgage, buyPrice, owner, type);
		//this.rent = rent;
	}
	
	public abstract int[] getRent();
	
	public abstract void setRent(int[] rent);
		
	public void buy(Player player, CanOwn publicProperty, List<Player> listPlayers) {
		//Please fill in
		//FIXME
		//Yes: Add to the persons list of properties, give them the title deed card/Reduce the money
		//No: Auction it, Bank auctions it which means that we just ask each player......
		//		If nobody wants to buy it, move onto the next turn 
	}
	
	public void sell(Player player, CanOwn publicProperty, List<Player> listPlayers) {
		//FIXME
		//if(unimproved/utilities) of all the same colour
		//		Ask user do they want to sell 
		// Else Bank sells all of the buildings on the site first back to the player...at 1/2 the original value
	}
}
