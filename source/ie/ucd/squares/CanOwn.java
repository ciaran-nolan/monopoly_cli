package ie.ucd.squares;

import ie.ucd.cards.TitleDeed;
import ie.ucd.game.Player;
import ie.ucd.operations.InputOutput;

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

	//morgage a property
	public void mortgage(Player player, boolean bankruptcy) {
		//check that the player owns the property
		if (this.titleDeedCard.getOwner() == player) {
			//ensure selected type of CanOwn is an instance of property
			if(this instanceof Property) {
				//check if the property is upgraded
				if(((Property)this).getNumHouses() == 0 && ((Property)this).getNumHotels() == 0) {
					//check if the property is already mortgaged
					if(this.titleDeedCard.getMortgageStatus()){
						System.err.println("This property is already mortgaged!");
					}
					//check if the mortgage is due to bankruptcy
					else if(bankruptcy){
						this.titleDeedCard.setMortgageStatus(true);
						player.addMoney(this.titleDeedCard.getMortgage());
						System.out.println("Successfully mortgaged "+this.getName());
					}
					//confirm mortgage
					else if(InputOutput.yesNoInput("This property is unimproved: "+this.getName()+"\nWould you still like to mortgage this property? (y/n)", player)) {
						this.titleDeedCard.setMortgageStatus(true);
						player.addMoney(this.titleDeedCard.getMortgage());
						System.out.println("Successfully mortgaged "+this.getName());
					}
					//cancel mortgage
					else {
						System.out.println("You have chosen not to mortgage this property");
						this.titleDeedCard.setMortgageStatus(false);
					}
				}
				else{
					//It has houses or hotels on it so you must sell all of them
					//Go through property list. Get the colour of the property and sell all of them from there
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
				//If its an instance of a property, it can be improved so check that it is unimproved before mortgaging it
			}
			//not an instanc eof property, cannot be upgraded so immediately mortgage
			else {
				if((this instanceof Utility || this instanceof Train) && !this.titleDeedCard.getMortgageStatus()) {
					this.titleDeedCard.setMortgageStatus(true);
					player.addMoney(this.titleDeedCard.getMortgage());
					System.out.println("Successfully mortgaged "+this.getName());
				}
				else {
					System.out.println("This property is non-ownable or has been mortgaged already!");
				}
			}
		}
		//player does not own the property
		else {
			System.err.println("You cannot mortgage this property as you do not own it!");
		}
	}

	//boolean argument ensures that the a buyer of a mortgaged property immediately pays the 10% charge
	public void demortgage(boolean demortgageOnSale) {
		//Check property is mortgaged
		if(this.getTitleDeedCard().getMortgageStatus()) {
			//if the property must be demortgaged on sale
			//Pay 10% plus value of mortgage
			if (demortgageOnSale) {
				//Automatically set 10% interest to paying
				this.titleDeedCard.getOwner().reduceMoney((int) (0.1 * this.titleDeedCard.getMortgage()), null);
				//If they dont have enough money to pay off mortgage
				if (this.titleDeedCard.getMortgage() > this.titleDeedCard.getOwner().getMoney()) {
					System.err.println("You don't have enough money to demortgage this property now!");
				} 
				else {
					this.titleDeedCard.getOwner().reduceMoney(this.titleDeedCard.getMortgage(), null);
					this.titleDeedCard.setMortgageStatus(false);
					System.out.println(this.titleDeedCard.getCardDesc() + " has been successfully demortgaged.");
				}
			}
			//property has not been sold, just demortgaged
			else {
				//check owner has required funds to demortgage
				if ((this.titleDeedCard.getMortgage() + 0.1 * this.titleDeedCard.getMortgage()) > this.titleDeedCard.getOwner().getMoney()) {
					System.err.println("You don't have enough money to demortgage this property now!");
				}
				else {
					this.titleDeedCard.getOwner().reduceMoney(this.titleDeedCard.getMortgage(), null); //Paying price of mortgage
					this.titleDeedCard.getOwner().reduceMoney((int) (0.1 * this.titleDeedCard.getMortgage()), null); //Paying interest
					this.titleDeedCard.setMortgageStatus(false);
					System.out.println(this.titleDeedCard.getCardDesc() + " has been successfully demortgaged.");
				}
			}
		}
		else System.out.println("This property is not currently mortgaged");
	}
}
