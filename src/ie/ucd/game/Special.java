package ie.ucd.game;
/*
 * This Special Square contains the methods for Tax, Jail, Go and Free Parking 
 * It will work off the index of the properties file inside of specialSquares.configurations
 * We can then use the individual configuration files for Chance and Community Chest.
 */
public class Special extends Square {
	private String type; //This defines the type of the square
	private int value; //This defines the value if they have any......like Tax, or Go
	
	//private String description; //FIXME This has been passed up to the Square level
	
	public Special(String name, int indexLocation, boolean canBuy, String type, int value) {
		super(name, indexLocation, canBuy); //Nobody owns it
		this.type = type;
		this.value = value;
		
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setType(String squareType) {
		this.type = squareType;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	//For special square, the community chest and chance ones will implement
	//the dealWithCard(Player) function from which a card can be produced from the deck
	// and then used to perform a function
	public void implementSpecialSquare(Player player1) {
		switch(type) {
			case "TAX":
				player1.reduceMoney(value); //Reducing the money in a players account using the value given
				break; //Break the switch statement
			case "GO":
				player1.addMoney(value);
				break;
			case "COMMUNITY_CHEST":
				player1.pickCommChestCard(BoardReader.getCommunityChests());
			case "CHANCE":
				player1.pickChanceCard(BoardReader.getChances());
			case "FREE":
				break; //THIS IS DONE FOR FREE PARKING
			default:
				player1.goToJail();
				break; //FIXME Will need to be looking at the chance and community cards also
		}
	}
	
	
	
	
	
	
	
	
	
	//THIS WILL BE IMPLEMENTED IN SQUARE
	/*public int getLocation() {
		return super.indexLocation;
	}
	
	public void setLocation(int location) {
		this.indexLocation = location;
	}*/
	
	
	
	
	
}
