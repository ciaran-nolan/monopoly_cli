package ie.ucd.game;
import java.util.*;

public class Chance extends Special {
	
	public Chance(String name, int indexLocation, String type, int value) {
		super(name, indexLocation, false, type, value);
	}
	
	public void dealWithCard(Player player1) {
		//From here I need to deal with a card produced from a deck of cards
		ArrayList<CanOwn> propertyList = player1.getPropertyList();
		
		switch(this.getType()) {
			case "MOVE":
				player1.setLocation(this.getValue());
				if(this.getValue() == 0) {
					player1.addMoney(200); //Add money as the user has gone to GO
				}
			case "JAIL":
				player1.goToJail();
			case "PAY":
				if(this.getName().contains("repairs")) {
					//In this case I need to get how many houses or hotels are on each site
					for(CanOwn property : propertyList) {
						//player1.reduceMoney(value*property.getNumHouses());
						//player1.reduceMoney(value*property.getNumHotels());
						
					}
				}
				else {
					player1.reduceMoney(this.getValue());
				}
			case "INCOME":
				player1.addMoney(this.getValue());
			case "GET_OUT_OF_JAIL":
				player1.setJailFree(); //Increments the amount of jail free cards by 1
			case "CHOICE":
				//We will need to take user input here
				System.out.println(player1.getName()+": Would you like to: "+this.getName()+"?");
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
