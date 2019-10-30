package ie.ucd.game;

import java.util.*;
//This will be the file that reads the configuration of the board and asks for user input
//It is a main file
public class Game {
	private static int remainingHouses=32;
	private static int remainingHotels=12;
	public static ArrayList<Player> playerList = new ArrayList<Player>();
	ArrayList<Square> board = BoardReader.board;
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
		
		while(numPlayersBankrupt < 2) {
			//This is the loop that consistently loop through the players of the game
			for(Player currentPlayer:playerList) {
				
				boolean doubleRoll = true;
				//This is here for doubles being rolled so you can do as much things as you want
				while(doubleRoll) {
					//This is where to get a correct input from the user
					InputOutput.handleUserOption(currentPlayer, doubleRoll);
					//check if they are finished before rolling dice
					if(InputOutput.yesNoInput("Would you like to do an additional action before rolling the dice? (y/n)", currentPlayer)) {
						continue;
					}
					
					//Roll the dice regardless after they have done all of their things
					//This will hopefully update the dice roll and allow it to see if a double has been rolled
					//this will both roll the dice and check if a double has been rolled
					doubleRoll=Dice.rollDice();
					
					if(doubleRoll) {
						Dice.incrementDuplicateRollCounter();
					}
					else {
						doubleRoll= false;
					}
					//condition for jail
					if(Dice.getDuplicateRollCounter()==3) {
						currentPlayer.goToJail();
						Dice.resetDuplicateRollCounter();
						break;
					}
					
					currentPlayer.movePlayer(Dice.getDieVals());
					//Checks will implement everything in there that is needed such as working on special squares etc or going to jail
					//It needs to see what square it has to know what to do next 
					//FIXME We could have a switch statement and the checksquare returns a value to the main
					//Checks.checkSquare(currentPlayer.getLocation());
					
					//Need to implement an input function which will take a parameter of whether they are allowed to rollDice again or not and then the switch statement
					// will change as a result
					
			
					//If asked to finish and didnt roll double, break
					while(!InputOutput.yesNoInput("Are you done with your turn?(y/n)", currentPlayer)) {
						System.out.println("Here!");
						InputOutput.handleUserOption(currentPlayer, doubleRoll);
					}
					if(doubleRoll) {
						break;
					}
					else {
						continue;
					}
					
				}	
			}
			//You can build at any time on whatever square
			//Do you want to Roll dice? 
			//IF it was a roll, i need to move player
			//Check the square
			//Double -Roll again after the decision has been made -> Cannot roll again if it is jail
			//At end of turn, do you want to mortgage properties, buy and sell hotels/houses
			//Check square needs to check if they are on a chance card and them implementSpecialSquare will be able to implement what is happening
			//Ask them if they are finished with their turn
			//increment the playerlist and then do it all again
			//have at bottom of for loop that 
			/*for{
				if (numPlayersBankrupt)
				break;
			}*/
			
		}
		Checks.checkWinner();
		//Will check the winner and finish the game
		
	}
}