package ie.ucd.game;

import java.util.*;
//This will be the file that reads the configuration of the board and asks for user input
//It is a main file
public class Game {
	private static int remainingHouses=32;
	private static int remainingHotels=12;
    public static ArrayList<Player> playerList = new ArrayList<Player>();
	public static int numPlayersBankrupt=0;
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
	
	
	//This function will check the winner of the game by looping through the player list and checking who has the most money
	//Will return the player object that is the winner and then the main class will finish the game
	//Check winner will be called when 
	
	public static void main(String[] args){
		//We will loop through the board arraylist which is listed at 
		//BoardReader.board
		playerList = Player.createListPlayers();
		BoardReader.initialiseBoard();
		//game ends after two players go bankrupt mso only need to go to
		while(numPlayersBankrupt < 2) {
			//This is the loop that consistently loop through the players of the game
			for(Player currentPlayer:playerList) {

                //even if doubles are rolled to get out of jail, the player does not make another turn, so this does not need to be inside the while loop
                if (currentPlayer.isInJail()) {
                    Jail.handleJailMove(currentPlayer);
                }
                else {
                    boolean doubleRoll = true;
                    //This is here for doubles being rolled so you can do as much things as you want
                    while (doubleRoll) {
                        //This is where to get a correct input from the user
                        InputOutput.handleUserOption(currentPlayer, doubleRoll);

                        //Roll the dice regardless after they have done all of their things
                        //this will both roll the dice and check if a double has been rolled
                        doubleRoll=Dice.handlePlayerRoll(currentPlayer);
                        if(!currentPlayer.isInJail()){
							currentPlayer.movePlayer(Dice.getDieVals());
							//Display information about the square
							InputOutput.squareInformation(currentPlayer.getLocation());
							//handle the required action on the square
							Checks.checkSquare(currentPlayer.getLocation(), currentPlayer);
						}

                        //If asked to finish and didnt roll double, break
                        while (!InputOutput.yesNoInput("Are you done with your turn?(y/n)", currentPlayer)) {
                            InputOutput.handleUserOption(currentPlayer, doubleRoll);
                        }
                        if (!doubleRoll) {
                            break;
                        } else {
                            System.out.print("\n"+currentPlayer.getName() + ", you have rolled doubles, you will roll again");
                        }
                    }
                }
            }
		}
        //Will check the winner and finish the game
		Checks.checkWinner();
	}
}