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
				player1.setLocation(this.getCardValue());
				if(this.getCardValue() == 0) {
					player1.addMoney(200); //Add money as the user has gone to GO
				}
			case "JAIL":
				player1.goToJail();
			case "PAY":
				if(this.getCardDesc().contains("repairs")) {
					//In this case I need to get how many houses or hotels are on each site
					for(CanOwn property : propertyList) {
						//player1.reduceMoney(value*property.getNumHouses());
						//player1.reduceMoney(4*value*property.getNumHotels());
						//FIXME
						System.out.println(property.getLocation());
					}
				}
				else {
					player1.reduceMoney(this.getCardValue());
				}
			case "INCOME":
				player1.addMoney(this.getCardValue());
			case "GET_OUT_OF_JAIL":
				player1.setJailFree(); //Increments the amount of jail free cards by 1
			case "CHOICE":
				//We will need to take user input here
				System.out.println(player1.getName()+": Would you like to: "+this.getCardDesc()+"?");
				System.out.println("Please enter either FINE or CHANCE!");
				//Have the input taken in ignoring the case
				/*while(true) {
					if(input == "FINE") {
						player1.reduceMoney(this.getValue());
						break;
					}
					else if(input == "CHANCE") {
						//pick up a chance card
						break;
					}
					else {
						System.err.println("You have not chosen an option. Choose again!");
					}
				}*/
		}
	

	}
}
