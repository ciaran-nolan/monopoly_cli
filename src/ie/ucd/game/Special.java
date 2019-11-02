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
	
	public Special(String name, int indexLocation, boolean canBuy, String type, int value, SquareType squareType) {
		super(name, indexLocation, canBuy, squareType); //Nobody owns it
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
	public void implementSpecialSquare(Player player) {
		System.out.println("Special"+this.type);
		switch(this.type) {
			case "TAX":
				System.out.println("You must pay tax of "+"£"+this.value);
				//Null as you owe the bank if you cant pay
				player.reduceMoney(this.value, null); //Reducing the money in a players account using the value given
				System.out.println("Your remaining funds £"+player.getMoney());
				break; //Break the switch statement
			case "GO":
				System.out.println("You you have landed on GO, collect "+"£"+this.value);
				player.addMoney(this.value);
				break;
			case "COMMUNITY_CHEST":
				System.out.println("Com Chest");
				player.pickCommChestCard();
				break;
			case "CHANCE":
				System.out.println("Chance");
				player.pickChanceCard();
				break;
			case "FREE":
				break; //THIS IS DONE FOR FREE PARKING
			default:
				System.out.println("Jail");
				player.goToJail();
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

