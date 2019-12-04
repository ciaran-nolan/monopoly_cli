package ie.ucd.game;
/*
 * This is the class that defines a Square. 
 * I will need to populate the derived classes such as Property, Utility and Special Squares later (Incl. Community Chest 
 * Chance cards
 */


public abstract class Square  {
	private int indexLocation;
	private boolean canBuy;
	private String name; //Could also be the description
	
	public enum SquareType {
		PROPERTY, UTILITY, TRAIN, SPECIAL, PUBLIC
	}
	
	private SquareType squareType;
	
	
	public Square(String name , int indexLocation, boolean canBuy, SquareType squareType) {
		this.indexLocation = indexLocation;
		this.canBuy = canBuy;
		this.name = name;
		this.squareType = squareType;
	}
	
	public int getLocation() {
		return this.indexLocation;
	}
	
	public boolean getBuyStatus() {
		return this.canBuy;
	}
	
	public String getName() {
		return this.name;
	}
	
	public SquareType getSquareType() {
		return this.squareType;
	}
	
	public void setSquareType(SquareType squareType) {
		this.squareType = squareType;
	}
	
	public void setLocation(int location) {
		this.indexLocation = location;
	}
	
	public void setBuyStatus(boolean canBuy) {
		this.canBuy = canBuy;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	//FIXME Please consult this first and see whether it should be renamed
	//Used for handling the special square landed on but could be implemented for both
	//public abstract void implementSpecialSquare(Player player1);
	
}
