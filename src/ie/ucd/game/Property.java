package ie.ucd.game;

public class Property {
	private int squareNum;
	private String squareColour;
	private String title;
	private int priceBuy;
	private int[] rents;
	private int housePrice;
	private int mortgage;
	
	public Property(int squareNum, String squareColour, String title, int priceBuy, int[] rents, int housePrice, int mortgage) {
		this.squareNum = squareNum;
		this.squareColour = squareColour;
		this.title = title;
		this.priceBuy = priceBuy;
		this.rents = rents;
		this.housePrice = housePrice;
		this.mortgage = mortgage;
	}
	
}
