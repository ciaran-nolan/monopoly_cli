package ie.ucd.squares;

import ie.ucd.squares.Square.SquareType;

public abstract class PublicSquare extends CanOwn {

	public PublicSquare(String name, int indexLocation, Square.SquareType type) {
		super(name, indexLocation, type);
	}

	public void buy(){ }
}