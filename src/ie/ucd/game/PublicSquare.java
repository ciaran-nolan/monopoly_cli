package ie.ucd.game;


public abstract class PublicSquare extends CanOwn {
	//private int rent; //Rent in this case is different to rent in the case of properties with colours

	public PublicSquare(String name, int indexLocation, Square.SquareType type) {
		super(name, indexLocation, type);
	}

	public void buy(){ }
}