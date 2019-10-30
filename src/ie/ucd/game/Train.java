package ie.ucd.game;

import java.util.List;

public class Train extends PublicSquare {
	private int[] rent; //Rent for a train is Size 4 
	
	public Train(String name, int indexLocation, int buyPrice, int mortgage, int[] rent, Player owner) {
		super(name, indexLocation, buyPrice, mortgage, owner, Square.SquareType.TRAIN);
		this.rent = rent;
	}
	
	public int[] getRent() {
		return this.rent;
	}
	
	public void setRent(int[] rent) {
		this.rent = rent;
	}

	public void buy(Player player, List<Player> listPlayers) {
		
		
	}

	@Override
	public void buy(Player player) {
		// TODO Auto-generated method stub
		
	}

}
