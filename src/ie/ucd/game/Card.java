package ie.ucd.game;

public abstract class Card {
	private String cardType;
	private String cardDesc;
	private int cardValue;
	
	
	public Card (String cardType, String cardDesc, int cardValue) {
		this.cardType = cardType;
		this.cardDesc = cardDesc;
		this.cardValue = cardValue;	
	}
	
	String getCardType() {
		return this.cardType;
	}
	
	public String getCardDesc() {
		return this.cardDesc;
	}
	
	int getCardValue() {
		return this.cardValue;
	}
	
	void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	void setCardDesc(String cardDesc) {
		this.cardDesc = cardDesc;
	}
	
	void setCardValue(int cardValue) {
		this.cardValue = cardValue;
	}
	
	public abstract void dealWithCard(Player player1);
	
	
	//Following the format of using a toString 
	public String toString() {
		return "Card Type: "+this.cardType+", Card Description: "+this.cardDesc+", Card Value: "
				+this.cardValue;
				
	}

}
	