package ie.ucd.game;
import java.util.*;
import ie.ucd.game.Checks;

public abstract class CanOwn extends Square {
	private int mortgage;
	private int buyPrice;
	private boolean mortgaged = false; //Whether property is mortgaged
	Player owner;
	
	public CanOwn(String name, int indexLocation, int mortgage, int buyPrice, Player owner, Square.SquareType type) {
		super(name, indexLocation, true, type);
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
	public abstract void buy(Player player);
	//The list of players is so you can use the auction method which will be made by Ciaran Nolan
	
	public void playerAuction() {
	
		ArrayList<Player> biddingPlayers = new ArrayList<>(Game.playerList);
		int[] currentAuctionDetails = new int[] {0,0};
		Scanner auctionScanner = new Scanner(System.in);
		int biddingPoolSize = Game.playerList.size();
		
		while(biddingPoolSize > 1){
			//update the bidding pool size 
			for (int i = 0; i< biddingPoolSize; i++) {
				
				//check user has enough funds to create a larger bid than the current highest
				if(biddingPlayers.get(i).getMoney()<= currentAuctionDetails[0]) {
					//user does not have enough funds to make a winning bid, remove them from the auction pool
					System.out.println(biddingPlayers.get(i).getName()+" does not have enough funds to make a winning bid on "+this.getName()+"\nCurrent bid: "+currentAuctionDetails[0]+"\nYour Funds: "+biddingPlayers.get(i).getMoney());
					biddingPlayers.remove(i);
					//reduce index as pool size has decreased
					i --;
					//update building pool size for while loop
					biddingPoolSize = biddingPlayers.size();
					//index of user with winning needs to be reduced by one
					currentAuctionDetails[1] -= 1;
					
					//if there is only one player remaining, the auction is over and they win, so break from loop
					if(currentAuctionDetails[0] > 0 && biddingPoolSize == 1) {
						break;
					}
					//if there is multiple remaining players left, return to beginning of 
					else {
						continue;	
					}
				}
					
				//if a previous bid has already been made, display to user 
				if(currentAuctionDetails[0] > 0) {
					System.out.println("Current bid: "+currentAuctionDetails[0]+" by "+biddingPlayers.get(currentAuctionDetails[1]).getName());
				}
				
				//user has indicated intention to bid
				if(InputOutput.yesNoInput((biddingPlayers.get(i).getName()+" would you like to place a bid on "+this.getName()+"? (y/n)"), biddingPlayers.get(i))) {
					//prompt user to enter a bid
					System.out.println(biddingPlayers.get(i).getName() + " please enter your bid:");
					
					//read in user bid
					int temporaryBid = auctionScanner.nextInt();
					
					//check the user's bid is greater than current highest bid or that they do not have enough money to make the specified bid
					while(temporaryBid <= currentAuctionDetails[0] || biddingPlayers.get(i).getMoney() < temporaryBid) {
						//bid is less than current highest bid, prompt for intention to re input bid
						if(temporaryBid <= currentAuctionDetails[0]) {
							System.out.println(biddingPlayers.get(i).getName() + " your bid must be greater than the current bid of: "+currentAuctionDetails[0]);
						}
						//user does not have the specified funds to make bid, prompt for intention to rebid 
						else if(biddingPlayers.get(i).getMoney() < temporaryBid) {
							System.out.println(biddingPlayers.get(i).getName() + " you do not have enough funds to make this bid.");
							System.out.println("\nYour bid: "+temporaryBid+"\nYour Funds:"+biddingPlayers.get(i).getMoney()+"\nCurrent winning bid: "+currentAuctionDetails[0]+" by: "+biddingPlayers.get(currentAuctionDetails[1]).getName());
							
						}
						
						//check if user has confirmed intention to bid again
						if(InputOutput.yesNoInput("\nWould you like to make another bid? (y/n)", biddingPlayers.get(i))) {
							System.out.println(biddingPlayers.get(i).getName() + " please enter your bid:");
							//read in new bid
							temporaryBid = auctionScanner.nextInt();
						}
						//user has declared intention to NOT bid again, remove from list of current users in auction
						else {
							biddingPlayers.remove(i);
							i --;
							//reset temporary bid back to highest bid so it is not overwritten
							temporaryBid = currentAuctionDetails[0];
							break;
						}
						}
					//update the current highest bid to most recent successful bid
					currentAuctionDetails[0] = temporaryBid;
					//update the index of the user who has made the highest bid
					currentAuctionDetails[1] = i;
					
				}
				//user has indicated intention to not make a bid, remove from pool and update
				else {
					biddingPlayers.remove(i);
					i --;
					}
				//updating bidding pool size
				biddingPoolSize = biddingPlayers.size();
			}
			//only one player remaining in bid pool, assign property to winner 
			if (biddingPoolSize == 1) {
				System.out.println(biddingPlayers.get(0).getName()+" has successfully won "+this.getName()+" at auction for: "+currentAuctionDetails[0]);
				biddingPlayers.get(0).addPurchasedCard(this);
				biddingPlayers.get(0).reduceMoney(currentAuctionDetails[0], null);
				break;
			}
			}
		//no player made an intention to bid, property remains with a null owner 
		if(this.getOwner() == null) {
			System.out.println("There was no winning bid. "+this.getName()+" remains unpurchased");
		}
	}

	
	public abstract void sell(Player player, CanOwn siteToSell, List<Player> listPlayers);
	
	public void mortgage(Player player) {
		if (this.getOwner() == player) {
			if(this instanceof Property) {
				if(((Property)this).getNumHouses() == 0 && ((Property)this).getNumHotels() == 0) {
					if(this.mortgaged == true){
						System.err.println("This property is already mortgaged!");
					}
					else if(InputOutput.yesNoInput("This property is unimproved: "+this.getName()+"\nWould you still like to mortgage this property? (y/n))", player)) {
						this.mortgaged = true;
						player.addMoney(this.getMortgage());
						System.out.println("Successfully mortgaged "+this.getName()+"\nCurrent Funds: £"+player.getMoney());
					}
					else {
						System.out.println("You have chosen not to mortgage this property");
						this.mortgaged = false;
					}
				}
				else{
					//boolean toBeMortgaged = true;
					//It has houses or hotels on it so you must sell all of them
					//Go through property list. Get the colour of the property and sell all of them from there
					//sellHouses(numHouses.getNumHouses, Player player1)
					//To mortgage it first, you must sell the houses
					if(InputOutput.yesNoInput("This property is improved: "+this.getName()
							+"\nMortgaging this property will sell all houses/hotels in this colour group: "+((Property) this).getSquareColour()
							+"\nWould you still like to mortgage this property? (y/n))", player)) {

						((Property) this).sellHouses(player, true, false);
						((Property) this).sellHotels(player, true, false);
						//Once all of the houses and hotels are sold on each site, you will need to
						this.mortgaged = true;
					}
					else System.out.println("Property has not been mortgaged");
				}
				//If its an instance of a property, it can be improved so I need to check that it is unimproved before mortgaging it
				
			}
			else {
				if(this instanceof PublicSquare && this.getMortgageStatus() == false) {
					this.mortgaged = true;
					player.addMoney(this.getMortgage());
					System.out.println("Successfully mortgaged "+this.getName()+"\nCurrent Funds: £"+player.getMoney());
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
		//the new owner now owns it or not. and can demortgage at any time

		//FIXME why reduce the money without chec
		this.getOwner().reduceMoney((int)(0.01*this.getMortgage()), null); //We will automatically set 10% interest to paying
		//Then this is for if the user decides to demortgage on sale or not
		if(demortgageOnSale) {
			//If they dont have enough money to pay off mortgage
			if(this.getMortgage() > this.getOwner().getMoney()) {
				System.err .println("You don't have enough money to demortgage this property now!");
			}
			else {
				this.getOwner().reduceMoney(this.getMortgage(), null);
			}
		}
		else {
			if((this.getMortgage() + 0.01*this.getMortgage()) > this.getOwner().getMoney()) {
				System.err.println("You don't have enough money to demortgage this property now!");
			}
			else {
				//The mortgage will be removed at a later stage
				this.getOwner().reduceMoney(this.getMortgage(), null); //Paying price of mortgage
				this.getOwner().reduceMoney((int)(0.01*this.getMortgage()), null); //Paying interest
			}
		}
	}
}
