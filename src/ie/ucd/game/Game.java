package ie.ucd.game;

import java.util.*;
//This will be the file that reads the configuration of the board and asks for user input
//It is a main file
public class Game {
	private static int remainingHouses=32;
	private static int remainingHotels=12;
    public static ArrayList<Player> playerList = new ArrayList<Player>();
	static int numPlayersBankrupt=0;
	static int getRemainingHouses() {
		return remainingHouses;
	}
	static int getRemainingHotels() {
		return remainingHotels;
	}
	static void setRemainingHouses(int newHouseCount) {
		remainingHouses = newHouseCount;
	}
	static void setRemainingHotels(int newHotelCount) {
		remainingHotels = newHotelCount;
	}
	

	public static void main(String[] args){

		//Create player list
		playerList = Player.createListPlayers();
		//initialise board
		Board.initialiseBoard();

		//end game when 2 players have become bankrupt
		while(Checks.checkIfValidGame()) {
			//Loop through the players of the game to handle their turn
			for(Player currentPlayer:playerList) {

                //even if doubles are rolled to get out of jail, the player does not make another turn,
                if (currentPlayer.isInJail()) {
                    Jail.handleJailMove(currentPlayer);
                }
                else {
                	//handle a 'non-jail' move
					//marker to track double rolls
                    boolean doubleRoll = true;
                    //Loop to maintain a player's turn if they roll a double
                    while(true) {
                        //Check user status and handle input
						Checks.checkPlayerStatus(currentPlayer);
                        InputOutput.handleUserOption(currentPlayer, doubleRoll);

                        //roll the dice once they have finished their operations
                        //check if a double has been rolled
                        doubleRoll=Dice.handlePlayerRoll(currentPlayer);
                        //move player
						currentPlayer.movePlayer(Dice.getDieVals());
						//Display information about the square
						InputOutput.squareInformation(currentPlayer.getLocation());
						//handle the required action on the square
						Checks.checkSquare(currentPlayer.getLocation(), currentPlayer);
                        //Check if further operations after dice roll are required
                        while (!InputOutput.yesNoInput("Are you done with your turn?(y/n)", currentPlayer)) {
                            InputOutput.handleUserOption(currentPlayer, false);
                        }
                        //end turn if doubles not rolled, repeat if doubles rolled
                        if (!doubleRoll) {
                            break;
                        } else {
                            System.out.println("\n"+currentPlayer.getName() + ", you have rolled doubles, you will roll again");
                        }
                    }
                }
            }
		}
        //Check the winner and finish the game
		Checks.checkWinner();
	}
}