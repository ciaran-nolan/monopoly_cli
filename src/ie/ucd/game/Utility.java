package ie.ucd.game;

public class Utility extends PublicSquare {
	private int[] rent; //Rent is size 2
	//The rents will be dealt with at a level above

	public Utility(String name, int indexLocation, int buyPrice, int mortgage, int[] rent, Player owner) {
		super(name, indexLocation, buyPrice, mortgage, owner);
		this.rent = rent;
	}
	
	public int[] getRent() {
		return this.rent;
	}
	
	public void setRent(int[] rent) {
		this.rent = rent;
	}
}
