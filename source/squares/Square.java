package squares;
/*
 * This is the class that defines a Square. 
 * I will need to populate the derived classes such as Property, Utility and Special Squares later (Incl. Community Chest 
 * Chance cards
 */

/**
 * This is the Square class. It defines the structure and basic setter and getter methods for a general square type. It is abstract and contains
 * an enum called SquareType which is either PROPERTY, UTILITY, TRAIN, SPECIAL, PUBLIC.
 * @author Robert Keenan and Ciaran Nolan
 *
 */
public abstract class Square  {
	private int indexLocation;  //Location on board
	private boolean canBuy;		//Whether you can buy it or not
	private String name; 	    //The description of the square
	
	public enum SquareType {
		PROPERTY, UTILITY, TRAIN, SPECIAL
	}
	
	private SquareType squareType;
	/**
	 * The constructor for the Square class which takes the following arguments as seen and sets the class variables
	 * @param name The name of the square 
	 * @param indexLocation The location of the square on the board in integer form
	 * @param canBuy A boolean determining if the square can be bought or not
	 * @param squareType The SquareType enum
	 */
	public Square(String name , int indexLocation, boolean canBuy, SquareType squareType) {
		this.indexLocation = indexLocation;
		this.canBuy = canBuy;
		this.name = name;
		this.squareType = squareType;
	}
	/**
	 * Getter for location
	 * @return this.location
	 */
	public int getLocation() {
		return this.indexLocation;
	}
	/**
	 * Returns the buy status and whether the square can be bought
	 * @return this.canBuy
	 */
	boolean getBuyStatus() {
		return this.canBuy;
	}
	/**
	 * Getting the name of the square
	 * @return this.name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Getting the square type
	 * @return this.squareType
	 */
	public SquareType getSquareType() {
		return this.squareType;
	}
	/**
	 * Setting the square type
	 * @param squareType Of type squareType enum
	 */
	void setSquareType(SquareType squareType) {
		this.squareType = squareType;
	}
	/**
	 * Setting the location on the board
	 * @param location Integer for the location
	 */
	public void setLocation(int location) {
		this.indexLocation = location;
	}
	/**
	 * Setting the buy status of the square
	 * @param canBuy Boolean to determine if the square can be bought or not
	 */
	void setBuyStatus(boolean canBuy) {
		this.canBuy = canBuy;
	}
	/**
	 * Setting the name of the Square
	 * @param name Name of the square as a string
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
