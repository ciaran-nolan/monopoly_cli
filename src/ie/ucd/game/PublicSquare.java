package ie.ucd.game;
import java.util.*;

public abstract class PublicSquare extends CanOwn {
	//private int rent; //Rent in this case is different to rent in the case of properties with colours

	public PublicSquare(String name, int indexLocation, Square.SquareType type) {
		super(name, indexLocation, type);
	}

	public void buy(Player player, CanOwn publicProperty, List<Player> listPlayers){

	}
	
	public void sell(Player player, CanOwn publicProperty, List<Player> listPlayers) {

	}
}
/*
package ie.ucd.game;
		import java.util.*;

public interface PublicSquare {
	//private int rent; //Rent in this case is different to rent in the case of properties with colours
	int[] getRent();

	void setRent(int[] rent);

	void buy(Player player, CanOwn publicProperty, List<Player> listPlayers);

	void sell(Player player, CanOwn publicProperty, List<Player> listPlayers) ;
}
*/