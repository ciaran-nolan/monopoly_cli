package ie.ucd.game;
//This will be the file that reads the configuration of the board and asks for user input
//It is a main file
public class Game {
	private int maxNumHouses;
	private int maxNumHotels;
	public Game() {
		this.maxNumHouses=32;
		this.maxNumHotels=12;
	}
	
	public int getMaxNumHouses() {
		return this.maxNumHouses;
	}
	
	public int getMaxNumHotels() {
		return this.maxNumHotels;
	}
	public static void main(String[] args){
		
	}
}