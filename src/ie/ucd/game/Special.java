package ie.ucd.game;
/*
 * This Special Square contains the methods for Tax, Jail, Go and Free Parking 
 * It will work off the index of the properties file inside of specialSquares.configurations
 * We can then use the individual configuration files for Chance and Community Chest.
 */
public class Special extends Square {
	private String type; //This defines the type of the square
	private int value; //This defines the value if they have any......like Tax, or Go
	//private String description;
	
	public Special(String name, int indexLocation, int buyCost, boolean canBuy, Player owner, String type, int value) {
		//super(name, indexLocation, buyCost, false, null); //Nobody owns it
		//this.indexLocation = indexLocation;
		this.type = type;
		this.value = value;
		//this.description = description;
	}

	public void implementSpecialSquare(Player player1) {
		switch(type) {
			case "TAX":
				player1.reduceMoney(value); //Reducing the money in a players account using the value given
				break; //Break the switch statement
			case "GO":
				player1.addMoney(value);
				break;
			case "COMMUNITY_CHEST":
				//player1.
			case "CHANCE":
				//player1.
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
