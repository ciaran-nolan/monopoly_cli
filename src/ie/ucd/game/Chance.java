package ie.ucd.game;
import java.util.*;

public class Chance extends Card {
	
	public Chance(String cardType, String cardDesc, int cardValue) {
		super(cardType, cardDesc, cardValue);
	}
	
	public void dealWithCard(Player player1) {
		//From here I need to deal with a card produced from a deck of cards
		ArrayList<CanOwn> propertyList = player1.getPropertyList();
		
		switch(this.getCardType()) {
			case "MOVE":
				//player1.setLocation(this.getCardValue());
				player1.moveToSquare(this.getCardValue());
			case "JAIL":
				player1.goToJail();
			case "PAY":
				if(this.getCardDesc().contains("repairs")) {
					//In this case I need to get how many houses or hotels are on each site
					for(CanOwn property : propertyList) {
						if(property instanceof Property) {
							//This will get the card value and multiply the number of houses or hotels depending on if its of Property Class
							player1.reduceMoney(this.getCardValue()*((Property) property).getNumHouses(),null);
							player1.reduceMoney(4*this.getCardValue()*((Property) property).getNumHotels(),null);
							//If there is no hotels, it will not take any money away at all
							//FIXME
							System.out.println(property.getLocation());
						}
						else {
							continue;
						}
					}
				}
				else {
					player1.reduceMoney(this.getCardValue(), null);
				}
			case "INCOME":
				player1.addMoney(this.getCardValue());
			case "GET_OUT_OF_JAIL":
				player1.setJailFree(); //Increments the amount of jail free cards by 1
		}
	

	}
}
