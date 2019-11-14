package ie.ucd.game;
import java.util.*;


public abstract class CanOwn extends Square {
	private TitleDeed titleDeedCard;

	public CanOwn(String name, int indexLocation, Square.SquareType type) {
		super(name, indexLocation, true, type);
	}

	public TitleDeed getTitleDeedCard(){
		return this.titleDeedCard;
	}

	public void setTitleDeedCard(TitleDeed card){
		this.titleDeedCard = card;
	}
	//FIXME PLEASE Consider whether it should take an argument or not, Refer to Trello
	public abstract void buy(Player player);
	//The list of players is so you can use the auction method which will be made by Ciaran Nolan
	

	public abstract void sell(Player player, CanOwn siteToSell, List<Player> listPlayers);
	
	public void mortgage(Player player) {
		if (this.titleDeedCard.getOwner() == player) {
			if(this instanceof Property) {
				if(((Property)this).getNumHouses() == 0 && ((Property)this).getNumHotels() == 0) {
					if(this.titleDeedCard.getMortgageStatus() == true){
						System.err.println("This property is already mortgaged!");
					}
					else if(InputOutput.yesNoInput("This property is unimproved: "+this.getName()+"\nWould you still like to mortgage this property? (y/n)", player)) {
						this.titleDeedCard.setMortgageStatus(true);
						player.addMoney(this.titleDeedCard.getMortgage());
						System.out.println("Successfully mortgaged "+this.getName());
					}
					else {
						System.out.println("You have chosen not to mortgage this property");
						this.titleDeedCard.setMortgageStatus(false);
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
						this.titleDeedCard.setMortgageStatus(true);
					}
					else System.out.println("Property has not been mortgaged");
				}
				//If its an instance of a property, it can be improved so I need to check that it is unimproved before mortgaging it
				
			}
			else {
				if(this instanceof PublicSquare && this.titleDeedCard.getMortgageStatus() == false) {
					this.titleDeedCard.setMortgageStatus(true);
					player.addMoney(this.titleDeedCard.getMortgage());
					System.out.println("Successfully mortgaged "+this.getName()+"\nCurrent Funds: £"+player.getMoney());
				}
				else {
					System.out.println("This property is non-ownable or has been mortgaged already!");
					this.titleDeedCard.setMortgageStatus(false); //FIXME Double check whether this is actually needed
				}
			}
		}
		else {
			System.err.println("You cannot mortgage this property as you do not own it!");
		}
	}
	
	public void demortgage(boolean demortgageOnSale) {
		//the new owner now owns it or not. and can demortgage at any time
		//Then this is for if the user decides to demortgage on sale or not
		if(demortgageOnSale) {
			this.titleDeedCard.getOwner().reduceMoney((int)(0.01*this.titleDeedCard.getMortgage()), null); //We will automatically set 10% interest to paying
			//If they dont have enough money to pay off mortgage
			if(this.titleDeedCard.getMortgage() > this.titleDeedCard.getOwner().getMoney()) {
				System.err .println("You don't have enough money to demortgage this property now!");
			}
			else {
				this.titleDeedCard.getOwner().reduceMoney(this.titleDeedCard.getMortgage(), null);
			}
		}
		//property has not been sold, is just being demortgaged
		else {
			if((this.titleDeedCard.getMortgage() + 0.01*this.titleDeedCard.getMortgage()) > this.titleDeedCard.getOwner().getMoney()) {
				System.err.println("You don't have enough money to demortgage this property now!");
			}
			else {
				//The mortgage will be removed at a later stage
				this.titleDeedCard.getOwner().reduceMoney(this.titleDeedCard.getMortgage(), null); //Paying price of mortgage
				this.titleDeedCard.getOwner().reduceMoney((int)(0.01*this.titleDeedCard.getMortgage()), null); //Paying interest
				System.out.println(this.titleDeedCard.getCardDesc()+" has been successfully demortgaged.");
			}
		}
	}
}
