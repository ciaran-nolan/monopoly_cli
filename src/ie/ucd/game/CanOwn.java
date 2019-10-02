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
	
	public void playerAuction(CanOwn auctionableCard, List<Player> listPlayers, Player auctioningPlayer) {
	
		List<Player> biddingPlayers = listPlayers;
		
		int currentAuctionPrice = 0;
		Scanner auctionScanner = new Scanner(System.in);
		
		while(true){
			//update the bidding pool size 
			int biddingPoolSize = biddingPlayers.size();
			for (int i = 0; i<= biddingPoolSize; i++) {
				
				System.out.println(biddingPlayers.get(i).getName()+" would you like to place a bid on"+auctionableCard.getName()+"? (y/n)");
				System.out.println("Current bid: "+currentAuctionPrice+" by "+biddingPlayers.get(i-1).getName());
				String bidAcknowledgement = auctionScanner.next();
				
				while(bidAcknowledgement != "Y" || bidAcknowledgement != "y" || bidAcknowledgement != "N" || bidAcknowledgement != "n") {
					System.out.println(biddingPlayers.get(i).getName()+", please enter a valid response (y/n)");
					bidAcknowledgement = auctionScanner.next();
				}
				
				if(bidAcknowledgement == "y" || bidAcknowledgement == "Y") {
					System.out.println(biddingPlayers.get(i).getName() + " please enter your bid:");
					int temporaryBid = auctionScanner.nextInt();
					while(temporaryBid <= currentAuctionPrice) {
						System.out.println(biddingPlayers.get(i).getName() + " your bid must be greater than the current bid of: "+currentAuctionPrice+". Please enter a new bid: ");
						temporaryBid = auctionScanner.nextInt();
					}
					currentAuctionPrice = temporaryBid;
				}
				else {
					biddingPlayers.remove(i);
					}
				}
			}
		}

	
	public abstract void sell(Player player, CanOwn siteToSell, List<Player> listPlayers);
}
