package ie.ucd.game;

public class Card {
	private String cardType;
	private String cardDesc;
	private int cardValue;
	
	public Card (String cardType, String cardDesc, int cardValue) {
		this.cardType = cardType;
		this.cardDesc = cardDesc;
		this.cardValue = cardValue;
	}
	
	public String getCardType() {
		return this.cardType;
	}
	
	public String getCardDesc() {
		return this.cardDesc;
	}
	
	public int getCardValue() {
		return this.cardValue;
	}
	
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public void setCardDesc(String cardDesc) {
		this.cardDesc = cardDesc;
	}
	
	public void setCardValue(int cardValue) {
		this.cardValue = cardValue;
	}
	
	//Following the format of using a toString 
	public String toString() {
		return "Card Type: "+this.cardType+", Card Description: "+this.cardDesc+", Card Value: "
				+this.cardValue;
				
	}

}
	