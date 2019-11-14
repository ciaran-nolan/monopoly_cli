package ie.ucd.game;
import java.util.*;

public class Chance extends Card {
	
	public Chance(String cardType, String cardDesc, int cardValue) {
		super(cardType, cardDesc, cardValue);
	}
	
	public void dealWithCard(Player player) {
		//From here I need to deal with a card produced from a deck of cards

		ArrayList<TitleDeed> titleDeedList = player.getTitleDeedList();
		System.out.println("The chance card reads: "+this.getCardDesc());
		switch(this.getCardType()) {
			case "MOVE":
				//case where players move backwards and not to a specific property
				if(this.getCardValue()<0){
					player.moveToSquare(player.getLocation()+this.getCardValue());
					Checks.checkSquare(player.getLocation(), player);
				}
				//player1.setLocation(this.getCardValue());
				player.moveToSquare(this.getCardValue());
				break;
			case "JAIL":
				Jail.sendToJail(player);
				break;
			case "PAY":
				if(this.getCardDesc().contains("repairs")) {
					//In this case I need to get how many houses or hotels are on each site
					for(TitleDeed titleDeed : titleDeedList) {
						CanOwn property = titleDeed.getOwnableSite();
						if(property instanceof Property) {
							//This will get the card value and multiply the number of houses or hotels depending on if its of Property Class
							player.reduceMoney(this.getCardValue()*((Property) property).getNumHouses(),null);
							player.reduceMoney(4*this.getCardValue()*((Property) property).getNumHotels(),null);
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
					player.reduceMoney(this.getCardValue(), null);
					break;
				}
			case "INCOME":
				player.addMoney(this.getCardValue());
				break;
			case "GET_OUT_OF_JAIL":
				player.addJailCard(this);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + this.getCardType());
		}
	}
}
