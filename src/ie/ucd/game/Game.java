package ie.ucd.game;
//This will be the file that reads the configuration of the board and asks for user input
//It is a main file
public class Game {
	private static int remainingHouses=32;
	private static int remainingHotels=12;
	public Game() {
		
	}
	
	public static int getRemainingHouses() {
		return remainingHouses;
	}
	
	public static int getRemainingHotels() {
		return remainingHotels;
	}
	
	public static void setRemainingHouses(int newHouseCount) {
		remainingHouses = newHouseCount;
	}
	public static void setRemainingHotels(int newHotelCount) {
		remainingHotels = newHotelCount;
	}
	public static void main(String[] args){
		
	}
}