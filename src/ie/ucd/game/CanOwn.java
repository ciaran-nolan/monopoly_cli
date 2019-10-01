package ie.ucd.game;
import java.util.*;

public abstract class CanOwn extends Square {
	private int mortgage;
	private int buyPrice;
	Player owner;
	
	public CanOwn(String name, int indexLocation, int mortgage, int buyPrice, Player owner) {
		super(name, indexLocation, true);
		this.mortgage = mortgage;
		this.buyPrice = buyPrice;
		this.owner = owner;
	}
	
	public int getMortgage() {
		return this.mortgage;
	}
	
	public int getPrice() {
		return this.buyPrice;
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public void setMortgage(int mortgage) {
		this.mortgage = mortgage;
	}
	
	public void setPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	public void setOwner(Player player) {
		this.owner = player;
	}
	
	//FIXME PLEASE Consider whether it should take an argument or not, Refer to Trello
	public abstract void buy(Player player, CanOwn siteToBuy, List<Player> listPlayers);
	//The list of players is so you can use the auction method which will be made by Ciaran Nolan
	
	public abstract void sell(Player player, CanOwn siteToSell, List<Player> listPlayers);
}
