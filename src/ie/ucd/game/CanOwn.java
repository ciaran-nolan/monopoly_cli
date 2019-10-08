package ie.ucd.game;
import java.util.*;
import ie.ucd.game.Checks;

public abstract class CanOwn extends Square {
	private int mortgage;
	private int buyPrice;
	private boolean mortgaged = false; //Whether property is mortgaged
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
	
	public boolean getMortgageStatus() {
		return this.mortgaged;
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
	public abstract void buy(Player player, List<Player> listPlayers);
	//The list of players is so you can use the auction method which will be made by Ciaran Nolan
	
	public void playerAuction(List<Player> listPlayers) {
	
		List<Player> biddingPlayers = listPlayers;
		int[] currentAuctionPrice = new int[] {0,0};
		Scanner auctionScanner = new Scanner(System.in);
		int biddingPoolSize = listPlayers.size();
		
		while(biddingPoolSize > 1){
			//update the bidding pool size 
			for (int i = 0; i< biddingPoolSize; i++) {
				
				//check user has enough funds to create a larger bid than the current highest
				if(biddingPlayers.get(i).getMoney()<= currentAuctionPrice[0]) {
					//user does not have enough funds to make a winning bid, remove them from the auction pool
					System.out.println(biddingPlayers.get(i).getName()+" does not have enough funds to make a winning bid on "+this.getName()+"\nCurrent bid: "+currentAuctionPrice[0]+"\nYour Funds: "+biddingPlayers.get(i).getMoney());
					biddingPlayers.remove(i);
					//reduce index as pool size has decreased
					i -= 1;
					//update building pool size for while loop
					biddingPoolSize = biddingPlayers.size();
					//index of user with winning needs to be reduced by one
					currentAuctionPrice[1] -= 1;
					
					//if there is only one player remaining, the auction is over and they win, so breaqk from loop
					if(currentAuctionPrice[0] > 0 && biddingPoolSize == 1) {
						break;
					}
					//if there is multiple remaining players left, return to beginning of 
					else {
						continue;	
					}
				}
					
				//if a previous bid has already been made, display to user 
				if(currentAuctionPrice[0] > 0) {
					System.out.println("Current bid: "+currentAuctionPrice[0]+" by "+biddingPlayers.get(currentAuctionPrice[1]).getName());
				}
				
				//user has indicated intention to bid
				if(Checks.yesNoInput((biddingPlayers.get(i).getName()+" would you like to place a bid on "+this.getName()+"? (y/n)"), biddingPlayers.get(i))) {
					//prompt user to enter a bid
					System.out.println(biddingPlayers.get(i).getName() + " please enter your bid:");
					
					//read in user bid
					int temporaryBid = auctionScanner.nextInt();
					
					//check the user's bid is greater than current highest bid or that they do not have enough money to make the specified bid
					while(temporaryBid <= currentAuctionPrice[0] || biddingPlayers.get(i).getMoney() < temporaryBid) {
						//bid is less than current highest bid, prompt for intention to re input bid
						if(temporaryBid <= currentAuctionPrice[0]) {
							System.out.println(biddingPlayers.get(i).getName() + " your bid must be greater than the current bid of: "+currentAuctionPrice[0]);
						}
						//user does not have the specified funds to make bid, prompt for intention to rebid 
						else if(biddingPlayers.get(i).getMoney() < temporaryBid) {
							System.out.println(biddingPlayers.get(i).getName() + " you do not have enough funds to make this bid.");
							System.out.println("\nYour bid: "+temporaryBid+"\nYour Funds:"+biddingPlayers.get(i).getMoney()+"\nCurrent winning bid: "+currentAuctionPrice[0]+" by: "+biddingPlayers.get(currentAuctionPrice[1]).getName());
							
						}
						
						//check if user has confirmed intention to bid again
						if(Checks.yesNoInput("\nWould you like to make another bid? (y/n)", biddingPlayers.get(i))) {
							System.out.println(biddingPlayers.get(i).getName() + " please enter your bid:");
							//read in new bid
							temporaryBid = auctionScanner.nextInt();
						}
						//user has declared intention to NOT bid again, remove from list of current users in auction
						else {
							biddingPlayers.remove(i);
							i -= 1;
							//reset temporary bid back to highest bid so it is not overwritten
							temporaryBid = currentAuctionPrice[0];
							break;
						}
						}
					//update the current highest bid to most recent successful bid
					currentAuctionPrice[0] = temporaryBid;
					//update the index of the user who has made the highest bid
					currentAuctionPrice[1] = i;
					
				}
				//user has indicated intention to not make a bid, remove from pool and update
				else {
					biddingPlayers.remove(i);
					i -= 1;
					}
				//updating bidding pool size
				biddingPoolSize = biddingPlayers.size();
			}
			//only one player remaining in bid pool, assign property to winner 
			if (biddingPoolSize == 1) {
				System.out.println(biddingPlayers.get(0).getName()+" has successfully won "+this.getName()+" at auction for: "+currentAuctionPrice[0]);
				biddingPlayers.get(0).addPurchasedCard(this);
				this.setOwner(biddingPlayers.get(0));
				biddingPlayers.get(0).reduceMoney(currentAuctionPrice[0]);
				break;
			}
			}
		//no player made an intention to bid, property remains with a null owner 
		if(this.getOwner() == null) {
			System.out.println("There was no winning bid. "+this.getName()+" remains unpurchased");
		}
	}

	
	public abstract void sell(Player player, CanOwn siteToSell, List<Player> listPlayers);
	
	public void mortgage(Player player1) {
		if (this.getOwner() == player1) {
			ArrayList<CanOwn> propertyList = owner.getPropertyList(); //I can now check if they own this property 
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			if(this instanceof Property) {
				if(((Property)this).getNumHouses() == 0 && ((Property)this).getNumHotels() == 0) {
					System.out.println("This property is unimproved: "+this.getName());
					System.out.println("Would you still like to mortgage this property? [Y/N]");
					String mortgageAnswer = scanner.nextLine().toLowerCase();
					if(mortgageAnswer == "y" && this.mortgaged == false) {
						this.mortgaged = true;
					}
					else {
						System.out.println("You have chosen not to mortgage this property or there is already a mortgage on it!");
						this.mortgaged = false;
					}
				}
				else {
					boolean toBeMortgaged = true;
					//It has houses or hotels on it so you must sell all of them
					//Go through property list. Get the colour of the property and sell all of them from there
					//sellHouses(numHouses.getNumHouses, Player player1)
					//To mortgage it first, you must sell the houses
					((Property)this).sellHouses(((Property)this).getNumHouses(), player1, true);
					((Property)this).sellHotels(((Property)this).getNumHotels(), player1, true);
					//Once all of the houses and hotels are sold on each site, you will need to 
					this.mortgaged = true;
				}
				//If its an instance of a property, it can be improved so I need to check that it is unimproved before mortgaging it
				
			}
			else {
				if(this instanceof PublicSquare && this.getMortgageStatus() == false) {
					this.mortgaged = true;
				}
				else {
					System.out.println("This property is non-ownable or has been mortgaged already!");
					this.mortgaged = false; //FIXME Double check whether this is actually needed
				}
			}
		}
		else {
			System.err.println("You cannot mortgage this property as you do not own it!");
		}
	}
	
	public void demortgage(boolean demortgageOnSale) {
		
	}
}
