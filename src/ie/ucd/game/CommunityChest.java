package ie.ucd.game;
import java.util.*;

//This is how to sort out the handling of a community chest card that is picked from a deck
//It is not to denote a communitychest square. That is handled by the Special.java file
public class CommunityChest extends Card {
	
	//
	//ArrayList<Card> communityChestDeck = new ArrayList<Card>();
	
	CommunityChest(String cardType, String cardDesc, int cardValue) {
		super(cardType, cardDesc, cardValue);
	}
	
	public void dealWithCard(Player player1) {
		//deal with a community chest card
		//rit details of card to player
		System.out.println("The community chest card reads: "+this.getCardDesc());
		switch(this.getCardType()) {
			case "MOVE":
				player1.moveToSquare(this.getCardValue());
				break;
			case "JAIL":
				Jail.sendToJail(player1);
				break;
			case "PAY":
				player1.reduceMoney(this.getCardValue(), null);
				break;
			case "INCOME":
				player1.addMoney(this.getCardValue());
				break;
			case "GET_OUT_OF_JAIL":
				player1.addJailCard(this); //Increments the amount of jail free cards by 1
				break;
			case "CHOICE":
				//ask user for choice
				System.out.println(player1.getName()+": Would you like to: "+this.getCardDesc()+"?");
				System.out.println("Please use the integers provided to select\n FINE[0]\nCHANCE[1]");
				int choice = InputOutput.integerMenu(0,1);
				//implement choice
				if(choice==0){
					player1.reduceMoney(this.getCardValue(), null);
				}
				else{
					player1.pickChanceCard();
				}
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + this.getCardType());
		}
	}
}
